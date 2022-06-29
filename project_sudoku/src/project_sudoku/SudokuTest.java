package project_sudoku;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SudokuTest {
	private SudokuSolver solver;

	@BeforeEach
	void setUp() throws Exception {
		this.solver = new SudokuSolver();
	}

	@AfterEach
	void tearDown() throws Exception {
		this.solver = null;
	}

	@Test
	void testEmpty() {
		solver.clear();
		assertTrue(solver.solve(), "Empty sudoku, should be solvable");
	}

	@Test
	void testSolvable() {
		int[][] sud = { { 0, 0, 8, 0, 0, 9, 0, 6, 2 }, { 0, 0, 0, 0, 0, 0, 0, 0, 5 }, { 1, 0, 2, 5, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 2, 1, 0, 0, 9, 0 }, { 0, 5, 0, 0, 0, 0, 6, 0, 0 }, { 6, 0, 0, 0, 0, 0, 0, 2, 8 },
				{ 4, 1, 0, 6, 0, 8, 0, 0, 0 }, { 8, 6, 0, 0, 3, 0, 1, 0, 0 }, { 0, 0, 0, 0, 0, 0, 4, 0, 0 } };
		solver.setMatrix(sud);
		assertTrue(solver.solve(), "The sudoku is not solvable");
	}

	@Test
	void testUnsolvableRow() {
		solver.add(5, 2, 5);
		solver.add(5, 3, 5);
		assertFalse(solver.solve(), "Sudoku is solvable for the unsolvable row");
	}

	@Test
	void testUnsolvableColumn() {
		solver.add(5, 2, 5);
		solver.add(4, 2, 5);
		assertFalse(solver.solve(), "Sudoku is solvable for the unsolvable row");

	}

	@Test
	void testUnsolvableSubsection() {
		solver.add(1, 1, 5);
		solver.add(2, 2, 5);
		assertFalse(solver.solve(), "Sudoku is solvable for the unsolvable subsection");
	}

	@Test
	void testAddRemoveNumber() {
		solver.add(1, 1, 1);
		assertEquals(1, solver.get(1, 1), "Wrong number");
		solver.remove(1, 1);
		assertEquals(0, solver.get(1, 1), "Number not removed");
	}

	@Test
	void testClear() {
		solver.add(0, 1, 5);
		solver.add(5, 4, 5);
		solver.clear();

		int dim = 9;
		for (int r = 0; r < dim; r++) {
			for (int c = 0; c < dim; c++) {
				assertEquals(0, solver.get(r, c), "Board not cleared");
			}
		}
	}

	@Test
	void testGetMatrix() {
		int[][] temp = solver.getMatrix();

		int dim = 9;

		for (int r = 0; r < dim; r++) {
			for (int c = 0; c < dim; c++) {
				assertEquals(0, temp[r][c]);
			}
		}
	}

	@Test
	void testIsValid() {
		solver.add(1, 1, 9);
		solver.add(1, 5, 7);
		assertTrue(solver.isValid(), "Input is invalid");

		solver.add(1, 2, 9);
		assertFalse(solver.isValid(), "Input is valid");

	}
}
