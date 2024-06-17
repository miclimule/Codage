package Simplexe;

import java.util.*;

public class SimplexeNiv3 {
    private double[][] tableau;
    private int[] basis;
    private double[] objective;
    private double[] rightHandSide;
    private int numVariables;
    private int numConstraints;
    
    public double getObjectiveValue() {
        return -tableau[numConstraints][numVariables + numConstraints];
    }
    
    public double[][] getTableau() {
		return tableau;
	}

	public void setTableau(double[][] tableau) {
		this.tableau = tableau;
	}

	public int[] getBasis() {
		return basis;
	}

	public void setBasis(int[] basis) {
		this.basis = basis;
	}

	public double[] getObjective() {
		return objective;
	}

	public void setObjective(double[] objective) {
		this.objective = objective;
	}

	public double[] getRightHandSide() {
		return rightHandSide;
	}

	public void setRightHandSide(double[] rightHandSide) {
		this.rightHandSide = rightHandSide;
	}

	public int getNumVariables() {
		return numVariables;
	}

	public void setNumVariables(int numVariables) {
		this.numVariables = numVariables;
	}

	public int getNumConstraints() {
		return numConstraints;
	}

	public void setNumConstraints(int numConstraints) {
		this.numConstraints = numConstraints;
	}
	
	public double getVariableValue(int variableIndex) {
        int basisIndex = -1;
        for (int i = 0; i < numConstraints; i++) {
            if (basis[i] == variableIndex) {
                basisIndex = i;
                break;
            }
        }
        if (basisIndex == -1) {
            return 0;
        }
        return tableau[basisIndex][numVariables + numConstraints];
    }

	// Constructor
    public SimplexeNiv3(double[] objective, double[][] constraints, double[] rightHandSide) {
        this.numVariables = objective.length;
        this.numConstraints = constraints.length;
        this.objective = objective;
        this.rightHandSide = rightHandSide;
        this.basis = new int[numConstraints];
        this.tableau = new double[numConstraints + 1][numVariables + numConstraints + 1];

        // Initialize tableau with constraints and right hand side
        for (int i = 0; i < numConstraints; i++) {
            for (int j = 0; j < numVariables; j++) {
                tableau[i][j] = constraints[i][j];
            }
            tableau[i][numVariables + i] = 1.0; // Artificial variable
            tableau[i][numVariables + numConstraints] = rightHandSide[i];
        }

        // Initialize objective function
        for (int j = 0; j < numVariables; j++) {
            tableau[numConstraints][j] = -objective[j];
        }
    }

    // Method to solve the linear programming problem using the two-phase simplex method
    public void solve() {
        // Phase 1: Find initial basic feasible solution
        phaseOne();

        // Phase 2: Optimize the solution
        phaseTwo();
    }

    // Phase 1 of the simplex method
    private void phaseOne() {
        // Solve the linear programming problem using the simplex method
        while (!isOptimal()) {
            int enteringVariable = findEnteringVariable();
            int leavingVariable = findLeavingVariable(enteringVariable);
            pivot(leavingVariable, enteringVariable);
        }

        // Check if the initial basic feasible solution is valid
        if (tableau[numConstraints][numVariables + numConstraints] != 0) {
            throw new IllegalStateException("No feasible solution exists");
        }

        // Remove the artificial variables from the basis and the tableau
        for (int i = 0; i < numConstraints; i++) {
            if (basis[i] >= numVariables) {
                basis[i] = -1;
            }
        }
        tableau = Arrays.stream(tableau)
            .map(row -> Arrays.copyOfRange(row, 0, numVariables + numConstraints))
            .toArray(double[][]::new);
    }

    // Phase 2 of the simplex method
    private void phaseTwo() {
        // Set the objective function to the original objective function
        for (int j = 0; j < numVariables; j++) {
            tableau[numConstraints][j] = -objective[j];
        }

        // Solve the linear programming problem using the simplex method
        while (!isOptimal()) {
            int enteringVariable = findEnteringVariable();
            int leavingVariable = findLeavingVariable(enteringVariable);
            pivot(leavingVariable, enteringVariable);
        }
    }

    // Method to check if the current solution is optimal
    private boolean isOptimal() {
        for (int j = 0; j < numVariables + numConstraints; j++) {
            if (tableau[numConstraints][j] < 0) {
                return false;
            }
        }
        return true;
    }

    // Method to find the entering variable for the next pivot operation
    private int findEnteringVariable() {
        int enteringVariable = 0;
        for (int j = 1; j < numVariables + numConstraints; j++) {
            if (tableau[numConstraints][j] < tableau[numConstraints][enteringVariable]) {
                enteringVariable = j;
            }
        }
        return enteringVariable;
    }

    // Method to find the leaving variable for the next pivot operation
    private int findLeavingVariable(int enteringVariable) {
        int leavingVariable = 0;
        double minRatio = Double.POSITIVE_INFINITY;
        for (int i = 0; i < numConstraints; i++) {
            if (tableau[i][enteringVariable] > 0) {
                double ratio = tableau[i][numVariables + numConstraints] / tableau[i][enteringVariable];
                if (ratio < minRatio) {
                    minRatio = ratio;
                    leavingVariable = i;
                }
            }
        }
        return leavingVariable;
    }

    // Method to perform the pivot operation on the tableau
    private void pivot(int leavingVariable, int enteringVariable) {
        double pivotValue = tableau[leavingVariable][enteringVariable];
        for (int j = 0; j < numVariables + numConstraints + 1; j++) {
            tableau[leavingVariable][j] /= pivotValue;
        }
        for (int i = 0; i < numConstraints + 1; i++) {
            if (i != leavingVariable) {
                double multiplier = tableau[i][enteringVariable];
                for (int j = 0; j < numVariables + numConstraints + 1; j++) {
                    tableau[i][j] -= multiplier * tableau[leavingVariable][j];
                }
            }
        }
        basis[leavingVariable] = enteringVariable;
    }
}
