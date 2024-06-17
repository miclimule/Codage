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

}

