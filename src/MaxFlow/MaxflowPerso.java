package MaxFlow;

import java.util.LinkedList;

public class MaxflowPerso {
    static final int V = 10; // Number of vertices

    boolean bfs(int rGraph[][], int s, int t, int parent[]) {
        // Create a visited array and mark all vertices as not visited
        boolean visited[] = new boolean[V];
        for (int i = 0; i < V; ++i)
            visited[i] = false;

        // Create a queue, enqueue source vertex and mark source vertex as visited
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(s);
        visited[s] = true;
        parent[s] = -1;

        // Standard BFS Loop
        while (queue.size() != 0) {
            int u = queue.poll();

            for (int v = 0; v < V; v++) {
                if (visited[v] == false && rGraph[u][v] > 0) {
                    if (v == t) {
                        parent[v] = u;
                        return true;
                    }
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }

        return false;
    }

    int fordFulkerson(int graph[][], int s, int t) {
        int u, v;

        int rGraph[][] = new int[V][V];

        for (u = 0; u < V; u++)
            for (v = 0; v < V; v++)
                rGraph[u][v] = graph[u][v];

        int parent[] = new int[V];

        int max_flow = 0;

        while (bfs(rGraph, s, t, parent)) {
            int path_flow = Integer.MAX_VALUE;
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                path_flow = Math.min(path_flow, rGraph[u][v]);
            }

            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                rGraph[u][v] -= path_flow;
                rGraph[v][u] += path_flow;
            }

            max_flow += path_flow;
        }

        return max_flow;
    }

    public static void main(String[] args) throws java.lang.Exception {
        int graph[][] = new int[][] {
            { 0, 5000, 4000, 3000, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 2000, 0, 3000, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 2000, 1000, 1000, 0, 0, 0 },
            { 0, 0, 2000, 0, 0, 0, 3000, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 2000, 0, 7000, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 2000, 1500, 0 },
            { 0, 0, 0, 0, 0, 3000, 0, 0, 1000, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 8000 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 4000 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        };
        MaxflowPerso m = new MaxflowPerso();

        int maxFlow = m.fordFulkerson(graph, 0, 9);
        System.out.println("The maximum possible flow is " + maxFlow);

        System.out.println("Flow passing through each channel:");
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (graph[i][j] > 0) {
                    int flow = graph[i][j] - graph[j][i];
                    System.out.println("From " + i + " to " + j + ": " + flow);
                }
            }
        }
    }
}
