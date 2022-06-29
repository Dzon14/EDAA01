package project_sudoku;

import java.util.ArrayList;

public class SudokuSolver implements SudokuSolverInterface {
	private int[][] board;
	private int dim;
	private int size;
	private int recCount;

	public SudokuSolver() {
		this.recCount = 0;
		this.dim = 9;
		this.size = 9;
		board = new int[size][size];
	}

	/**
	 * Solves the sudoku
	 * 
	 * @return true if sudoku is solvable
	 */
	@Override
	public boolean solve() {
		return isValid() ? solve(0, 0) : false;
	}

	/**
	 * Recursive private method for solve. Uses backtracking
	 * 
	 * @param row: row to check [0 -> 8]
	 * @param col: column to check [0 -> 8]
	 * @return true if sudoku is solvable. Returns the method recursively
	 */
	private boolean solve(int row, int col) {
		recCount++;
		if (col >= dim) {
			col = 0;
			row++;
		}

		if (row >= dim) {
			System.out.println(recCount);
			return true;
		}

		if (board[row][col] == 0) {

			for (int i = 1; i <= size; i++) {

				board[row][col] = i;

				if (validNumber(row, col)) {

					if (solve(row, col + 1)) {
						return true;
					}
				}
			}

			board[row][col] = 0;
			return false;
		}

		return solve(row, col + 1);

	}

	/**
	 * adds digit into sudoku
	 * 
	 * @param row:   [0 -> 8]
	 * @param col:   column [0 -> 8]
	 * @param digit: The digit to insert
	 * @throws IllegalArgumentException if digit is outside the range (validInput
	 *                                  method is used)
	 */
	@Override
	public void add(int row, int col, int digit) {
		validInput(row);
		validInput(col);
		validInput(digit);
		board[row][col] = digit;

	}

	/**
	 * Removes number from sudoku
	 * 
	 * @param row: to remove from [0 -> 8]
	 * @param col: the column to remove from [0 -> 8]
	 */
	@Override
	public void remove(int row, int col) {
		validInput(row);
		validInput(col);
		board[row][col] = 0;

	}

	/**
	 * Get number from specific matrix
	 * 
	 * @param row: row to get
	 * @param col: column to get
	 * @return number (int)
	 */
	@Override
	public int get(int row, int col) {
		validInput(row);
		validInput(col);
		return this.board[row][col];
	}

	/**
	 * Checks if numbers in sudoku is valid by calling more specific private methods
	 * 
	 * @return true or false, whether number is valid
	 */
	@Override
	public boolean isValid() {
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {

				if (validInput(board[r][c])) {
					// if the input in the cell is not valid, return false. Otherwise keep searching
					if (!validNumber(r, c)) {
						return false;
					}
				}
			}
		}
		// there was no input
		return true;
	}

	/**
	 * Private method to check if argument is inside the range [0 -> 9]
	 * 
	 * @param arg: argument
	 * @throws IllegalArgumentException if digit is outside the range (validInput
	 *                                  method is used)
	 * @return true if input is valid, otherwise false
	 */
	private boolean validInput(int arg) {
		if (arg > 0 || arg <= size) { // kke ändra till 1 sen
			return true;
		}
		return false;
	}

	/**
	 * Checks if the digit is valid by calling all methods that manages validity
	 * 
	 * @param row: row to to check
	 * @param col: column to check
	 * @return true if number is valid, otherwise false
	 */
	private boolean validNumber(int row, int col) {
		int number = board[row][col];
		// System.out.println(number);
		board[row][col] = 0; // Don't check for itself

		boolean isNumberAllowed = validRow(row, number) && validCol(col, number) && validSubsection(row, col, number);

		board[row][col] = number;

		return isNumberAllowed;
	}

	/**
	 * Check validity of row
	 * 
	 * @param row: row to check
	 * @param col: col to check
	 * @return true if row is valid, otherwise false
	 */
	private boolean validRow(int row, int num) {
		for (int i = 0; i < size; i++) {
			if (num == board[row][i] && num != 0) {
				// System.out.println("Number (num) already exist in this row");
				return false;
			}
		}
		return true;
	}

	/**
	 * Check validity of column
	 * 
	 * @param row: row to check
	 * @param col: col to check
	 * @return true if column is valid, otherwise false
	 */
	private boolean validCol(int col, int num) {
		for (int i = 0; i < size; i++) {
			if (num == board[i][col] && num != 0) {
				// System.out.println("Number (num) already exist in this column");
				return false;
			}
		}
		return true;
	}

	/**
	 * Check validity of subsection
	 * 
	 * @param row: row to check
	 * @param col: col to check
	 * @return true if subsection is valid, otherwise false
	 */
	private boolean validSubsection(int row, int col, int num) {

		int gridSize = (int) Math.sqrt(size);
		int startRow = (row / gridSize) * gridSize;
		int startCol = (col / gridSize) * gridSize; // Ex: 4/3 = 1. 1 * 3 = 3
		int endRow = startRow + gridSize;
		int endCol = startCol + gridSize;

		// loop through all columns and rows
		for (int r = startRow; r < endRow; r++) {
			for (int c = startCol; c < endCol; c++) {
				if (num == board[r][c] && num != 0) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Clears the whole sudoku board
	 */
	@Override
	public void clear() {
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				remove(r, c);
			}
		}

	}

	/**
	 * Fills the grid with the digits in m. The digit 0 represents an empty box.
	 * 
	 * @param m the matrix with the digits to insert
	 * @throws IllegalArgumentException if m has the wrong dimension or contains
	 *                                  values outside the range [0..9]
	 */
	@Override
	public void setMatrix(int[][] m) throws IllegalArgumentException {
		for (int r = 0; r < dim; r++) {
			for (int c = 0; c < dim; c++) {
				if (m[r][c] < 0 || m[r][c] > this.dim) {
					throw new IllegalArgumentException("m is outside of range");
				} else if (m[r].length > dim || m[c].length > dim) {
					throw new IllegalArgumentException("m has wrong dimensions");
				}
				board[r][c] = m[r][c];
			}
		}
	}

	/**
	 * Check validity of row
	 * 
	 * @return the Matrix
	 */
	@Override
	public int[][] getMatrix() {
		return this.board;
	}
}
