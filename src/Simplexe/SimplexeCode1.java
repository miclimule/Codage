package Simplexe;

import java.util.Arrays;

public class SimplexeCode1 {
    private static final double EPSILON = 1e-8;

    public static double[] solve(double[][] A, double[] b, double[] c) {
        int m = A.length;
        int n = A[0].length;
        double[][] tableau = new double[m + 1][n + m + 1];

        // Initialize tableau
        for (int i = 0; i < m; i++) {
            System.arraycopy(A[i], 0, tableau[i], 0, n);
            tableau[i][n + i] = 1;
            tableau[i][n + m] = b[i];
        }
        System.arraycopy(c, 0, tableau[m], 0, n);

        // Solve using Simplex algorithm
        while (true) {
            int pivotCol = findPivotColumn(tableau);
            if (pivotCol == -1) {
                break;
            }
            int pivotRow = findPivotRow(tableau, pivotCol);
            pivot(tableau, pivotRow, pivotCol);
        }

        // Extract solution
        double[] x = new double[n];
        for (int i = 0; i < n + m; i++) {
            if (tableau[m][i] == 1) {
                x[i] = tableau[m][n + m];
            }
        }
        return x;
    }

    private static int findPivotColumn(double[][] tableau) {
        int n = tableau[0].length - tableau.length;
        int pivotCol = -1;
        double minValue = Double.MAX_VALUE;
        for (int j = 0; j < n; j++) {
            if (tableau[tableau.length - 1][j] < minValue) {
                minValue = tableau[tableau.length - 1][j];
                pivotCol = j;
            }
        }
        return pivotCol;
    }

    private static int findPivotRow(double[][] tableau, int pivotCol) {
        int m = tableau.length - 1;
        int n = tableau[0].length - m - 1;
        int pivotRow = -1;
        double minRatio = Double.MAX_VALUE;
        for (int i = 0; i < m; i++) {
            if (tableau[i][pivotCol] > EPSILON) {
                double ratio = tableau[i][n + m] / tableau[i][pivotCol];
                if (ratio < minRatio) {
                    minRatio = ratio;
                    pivotRow = i;
                }
            }
        }
        return pivotRow;
    }

    private static void pivot(double[][] tableau, int pivotRow, int pivotCol) {
        int m = tableau.length - 1;
        int n = tableau[0].length - m - 1;
        double pivotValue = tableau[pivotRow][pivotCol];
        for (int j = 0; j < n + m + 1; j++) {
            tableau[pivotRow][j] /= pivotValue;
        }
        for (int i = 0; i < m + 1; i++) {
            if (i != pivotRow) {
                double multiplier = tableau[i][pivotCol];
                for (int j = 0; j < n + m + 1; j++) {
                    tableau[i][j] -= multiplier * tableau[pivotRow][j];
                }
            }
        }
    }

    public static void main(String[] args) {
        double[][] A = { { 1, 1 }, { 2, 1 }, { 1, 2 } };
        double[] b = { 4, 8, 5 };
        double[] c = { -1, -2 };
        double[] x = solve(A, b, c);
        System.out.println("Solution: " + Arrays.toString(x));
    }
}

