package Simplexe;


public class Simplexe {
    private double[][] tableau;
    private int[] basis;
    private int rows;
    private int cols;

    public Simplexe(double[] objective, double[][] constraints, double[] b) {
        this.rows = constraints.length;
        this.cols = constraints[0].length;
        this.tableau = new double[this.rows + 1][this.cols + this.rows + 1];
        this.basis = new int[this.rows];

        // Fill in the tableau
        for (int i = 0; i < this.cols; i++) {
            this.tableau[0][i] = -objective[i]; // Negate because we're maximizing
        }
        for (int i = 0; i < this.rows; i++) {
            this.basis[i] = this.cols + i;
            for (int j = 0; j < this.cols; j++) {
                this.tableau[i + 1][j] = constraints[i][j];
            }
            this.tableau[i + 1][this.cols + i] = 1; // Identity matrix for basis
            this.tableau[i + 1][this.cols + this.rows] = b[i];
        }
    }

    public void solve() {
        while (true) {
            int pivotCol = findPivotColumn();
            if (pivotCol == -1) break; // No negative coefficients in objective row, so we're done

            int pivotRow = findPivotRow(pivotCol);
            if (pivotRow == -1) {
                System.out.println("Unbounded solution");
                return;
            }

            pivot(pivotRow, pivotCol);
            this.basis[pivotRow - 1] = pivotCol;
        }

        // Print the solution
        System.out.println("Optimal solution: " + -this.tableau[0][this.cols + this.rows]);
        for (int i = 0; i < this.rows; i++) {
            System.out.println("x" + (this.basis[i] + 1) + " = " + this.tableau[i + 1][this.cols + this.rows]);
        }
    }

    private int findPivotColumn() {
        for (int i = 0; i < this.cols + this.rows; i++) {
            if (this.tableau[0][i] < 0) {
                return i;
            }
        }
        return -1;
    }

    private int findPivotRow(int pivotCol) {
        double minRatio = Double.POSITIVE_INFINITY;
        int pivotRow = -1;
        for (int i = 1; i <= this.rows; i++) {
            if (this.tableau[i][pivotCol] <= 0) continue;
            double ratio = this.tableau[i][this.cols + this.rows] / this.tableau[i][pivotCol];
            if (ratio < minRatio) {
                minRatio = ratio;
                pivotRow = i;
            }
        }
        return pivotRow;
    }

    private void pivot(int pivotRow, int pivotCol) {
        double pivotValue = this.tableau[pivotRow][pivotCol];
        for (int i = 0; i <= this.cols + this.rows; i++) {
            this.tableau[pivotRow][i] /= pivotValue;
        }
        for (int i = 0; i <= this.rows; i++) {
            if (i == pivotRow) continue;
            double multiplier = this.tableau[i][pivotCol];
            for (int j = 0; j <= this.cols + this.rows; j++) {
                this.tableau[i][j] -= multiplier * this.tableau[pivotRow][j];
            }
        }
    }
    
    public static void main(String[] args) {
		double[] objective = {7, 9};
		double[][] constraints = {{-1, 3}, {7, 1}};
		double[] b = {6, 35};
		Simplexe simplex = new Simplexe(objective, constraints, b);
		simplex.solve();
		
	}
}