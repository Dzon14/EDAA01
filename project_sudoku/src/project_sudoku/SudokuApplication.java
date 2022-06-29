package project_sudoku;

public class SudokuApplication {

	public static void main(String[] args) {
		SudokuSolver solver = new SudokuSolver();
		SudokuSolverController controller = new SudokuSolverController(solver);
	}

}
