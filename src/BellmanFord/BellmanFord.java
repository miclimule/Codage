package BellmanFord;

import java.util.*;

import BellmanFord.Edge.Point;

class Edge {
	public enum Point {
	    A, B, C, D, E, F, G, H
	}
	
    Point src;
	Point dest;
    int weight;
    Edge() {
        src = dest = null;
        weight = 0;
    }
}

class Graph {
    int V, E;
    Edge edge[];

    Graph(int v, int e) {
        V = v;
        E = e;
        edge = new Edge[e];
        for (int i = 0; i < e; ++i)
            edge[i] = new Edge();
    }

    void BellmanFord(Graph graph, Point src, Point dest) {
        int V = graph.V;
        int E = graph.E;
        int dist[] = new int[V];
        Point pred[] = new Point[V];

        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src.ordinal()] = 0;

        for (int i = 1; i < V; ++i) {
            for (int j = 0; j < E; ++j) {
                int u = graph.edge[j].src.ordinal();
                int v = graph.edge[j].dest.ordinal();
                int weight = graph.edge[j].weight;
                if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    pred[v] = graph.edge[j].src;
                }
            }
        }

        for (int j = 0; j < E; ++j) {
            int u = graph.edge[j].src.ordinal();
            int v = graph.edge[j].dest.ordinal();
            int weight = graph.edge[j].weight;
            if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
                System.out.println("Graph contains negative weight cycle");
                return;
            }
        }

        printPath(pred, dest);
    }

    void printPath(Point pred[], Point dest) {
        System.out.print("Shortest path from A to " + dest + ": ");
        Point curr = dest;
        while (curr != null) {
            System.out.print(curr + " ");
            curr = pred[curr.ordinal()];
        }
        System.out.println();
    }

    public static void main(String[] args) {
//		double[] objective = {3, 4, 1};
//		double[][] constraints = {{1, 2, 2}, {3, 2, 3}};
//		double[] b = {8.0/3, 7.0 / 3};
//		Simplexe simplex = new Simplexe(objective, constraints, b);
//		simplex.solve();
		
		int V = 5;  // Number of vertices in graph
        int E = 8;  // Number of edges in graph

        Graph graph = new Graph(V, E);

        // Adding edges one by one
        graph.edge[0].src = Point.A;
        graph.edge[0].dest = Point.B;
        graph.edge[0].weight = 8;

        graph.edge[1].src = Point.A;
        graph.edge[1].dest = Point.C;
        graph.edge[1].weight = 4;

        graph.edge[2].src = Point.B;
        graph.edge[2].dest = Point.C;
        graph.edge[2].weight = 4;

        graph.edge[3].src = Point.B;
        graph.edge[3].dest = Point.D;
        graph.edge[3].weight = 4;

        graph.edge[4].src = Point.B;
        graph.edge[4].dest = Point.E;
        graph.edge[4].weight = 4;

        graph.edge[5].src = Point.C;
        graph.edge[5].dest = Point.D;
        graph.edge[5].weight = 2;

        graph.edge[6].src = Point.D;
        graph.edge[6].dest = Point.B;
        graph.edge[6].weight = 8;

        graph.edge[7].src = Point.E;
        graph.edge[7].dest = Point.D;
        graph.edge[7].weight = 4;

        graph.BellmanFord(graph, Point.A, Point.D);
	}
}

