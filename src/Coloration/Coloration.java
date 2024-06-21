package Coloration;

import java.util.Arrays;

public class Coloration {
	
	private int[] color;
    private boolean[][] graph;
    private int numVertices;
    private int numColors;
    
    enum Point {
        A(0), B(1), C(2), D(3), E(4), F(5), G(6), H(7), I(8), J(9), K(10), L(11);

        private final int value;

        Point(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public Coloration(boolean[][] graph, int numColors) {
        this.graph = graph;
        this.numVertices = graph.length;
        this.numColors = numColors;
        this.color = new int[numVertices];
    }

    public void colorGraph() {
        if (tryColor(0)) {
            System.out.println(Arrays.toString(color));
        } else {
            System.out.println("No solution exists.");
        }
    }

    private boolean tryColor(int vertex) {
        if (vertex == numVertices) {
            return true;
        }

        for (int color = 1; color <= numColors; color++) {
            if (isSafe(vertex, color)) {
                this.color[vertex] = color;

                if (tryColor(vertex + 1)) {
                    return true;
                }

                this.color[vertex] = 0;
            }
        }

        return false;
    }

    private boolean isSafe(int vertex, int color) {
        for (int i = 0; i < numVertices; i++) {
            if (graph[vertex][i] && color == this.color[i]) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        boolean[][] graph = new boolean[5][5];

        graph[Point.A.getValue()][Point.B.getValue()] = graph[Point.B.getValue()][Point.A.getValue()] = true;

        graph[Point.A.getValue()][Point.C.getValue()] = graph[Point.C.getValue()][Point.A.getValue()] = true;

        graph[Point.A.getValue()][Point.E.getValue()] = graph[Point.E.getValue()][Point.A.getValue()] = true;

        graph[Point.B.getValue()][Point.D.getValue()] = graph[Point.D.getValue()][Point.B.getValue()] = true;

        graph[Point.B.getValue()][Point.E.getValue()] = graph[Point.E.getValue()][Point.B.getValue()] = true;

        graph[Point.C.getValue()][Point.E.getValue()] = graph[Point.E.getValue()][Point.C.getValue()] = true;

        Coloration gc = new Coloration(graph, 3); // 3 is the minimum number of colors needed
        gc.colorGraph();
    }
}
