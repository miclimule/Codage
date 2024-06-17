package Simplexe;


public class SimplexeNiv2 {
	private double[][] tableau;
    private int rows;
    private int cols;
    private int[] basis;

    public SimplexeNiv2(double[] objective, double[][] constraints, double[] b) {
        this.rows = constraints.length;
        this.cols = constraints[0].length;
        this.tableau = new double[rows + 1][cols + rows + 1];
        this.basis = new int[rows];

        // Fill objective row
        for (int j = 0; j < cols; j++) {
            tableau[0][j] = -objective[j];
        }

        // Fill constraints rows and basis
        for (int i = 0; i < rows; i++) {
            basis[i] = cols + i;
            for (int j = 0; j < cols; j++) {
                tableau[i + 1][j] = constraints[i][j];
            }
            tableau[i + 1][cols + i] = 1;
            tableau[i + 1][cols + rows] = b[i];
        }
    }

    public void solve() {
        while (true) {
            int pivotCol = findPivotColumn();
            if (pivotCol == -1) break;

            int pivotRow = findPivotRow(pivotCol);
            if (pivotRow == -1) {
                System.out.println("Unbounded solution");
                return;
            }

            pivot(pivotRow, pivotCol);
            basis[pivotRow - 1] = pivotCol;
        }
    }

    private int findPivotColumn() {
        int pivotCol = -1;
        for (int j = 0; j < cols + rows; j++) {
            if (tableau[0][j] < 0) {
                pivotCol = j;
                break;
            }
        }
        return pivotCol;
    }

    private int findPivotRow(int pivotCol) {
        double minRatio = Double.MAX_VALUE;
        int pivotRow = -1;
        for (int i = 1; i <= rows; i++) {
            if (tableau[i][pivotCol] <= 0) continue;

            double ratio = tableau[i][cols + rows] / tableau[i][pivotCol];
            if (ratio < minRatio) {
                minRatio = ratio;
                pivotRow = i;
            }
        }
        return pivotRow;
    }

    private void pivot(int pivotRow, int pivotCol) {
        double pivotValue = tableau[pivotRow][pivotCol];
        for (int j = 0; j <= cols + rows; j++) {
            tableau[pivotRow][j] /= pivotValue;
        }

        for (int i = 0; i <= rows; i++) {
            if (i == pivotRow) continue;

            double multiplier = tableau[i][pivotCol];
            for (int j = 0; j <= cols + rows; j++) {
                tableau[i][j] -= multiplier * tableau[pivotRow][j];
            }
        }
    }

    public double[] getSolution() {
        double[] solution = new double[cols];
        for (int i = 0; i < rows; i++) {
            if (basis[i] < cols) {
                solution[basis[i]] = tableau[i + 1][cols + rows];
            }
        }
        return solution;
    }

    public double getOptimalValue() {
        return tableau[0][cols + rows];
    }
}


