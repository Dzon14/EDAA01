package project_sudoku;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.text.Position;

public class SudokuSolverController {
	private SudokuSolver solver;
	private JTextField[][] fields;
	JPanel board;
	private int dim;
	int countTest;
	private boolean illegalInput;

	public SudokuSolverController(SudokuSolver solver) {
		this.dim = 9;
		int size = 100 * dim;
		this.fields = new JTextField[dim][dim];
		this.solver = solver;
		this.countTest = 0;
		SwingUtilities.invokeLater(() -> createWindow("SudokuSolver", size, size));
	}

	private void createWindow(String title, int width, int height) {

		// Creating a frame
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container pane = frame.getContentPane();

		// Controls
		GridLayout grid = new GridLayout(9, 9);
		this.board = new JPanel(grid);
		JPanel controlsPanel = new JPanel();

		buildBoard(9);

		// JButtons
		JButton solveBtn = new JButton("Solve");
		JButton clearBtn = new JButton("Clear");
		JButton loadBtn = new JButton("Load");

		solveBtn.setPreferredSize(new Dimension(200, 50));
		clearBtn.setPreferredSize(new Dimension(200, 50));
		clearBtn.setPreferredSize(new Dimension(200, 50));

		controlsPanel.add(solveBtn);
		controlsPanel.add(clearBtn);
		controlsPanel.add(loadBtn);

		clearBtn.addActionListener((e) -> {
			solver.clear();
			clear(9);
		});

		solveBtn.addActionListener((e) -> {
			solver.setMatrix(updateBoard());
			if (illegalInput) {
				JOptionPane.showMessageDialog(board, "Illegal input! Has to be integer between 1-9");
			} else {
				// if(solver.isValid()) {
				boolean isSolved = solver.solve();
				int[][] t1 = solver.getMatrix();
				for (int r = 0; r < dim; r++) {
					for (int c = 0; c < dim; c++) {
						if (t1[r][c] == 0) {
							fields[r][c].setText(null);
						} else {
							fields[r][c].setText(String.valueOf(t1[r][c]));
						}
					}
				}

				if (isSolved) {
					JOptionPane.showMessageDialog(board, "The sudoku has been solved");
				} else {
					JOptionPane.showMessageDialog(board, "The sudoku was unsolvable");
				}
			}

		});

		loadBtn.addActionListener((e) -> {
			cycleTestCases();
			solver.setMatrix(updateBoard());
			if (countTest < 3) { // To prevent crash
				countTest++;
			} else {
				countTest = 0;
			}
		});

		// Add
		pane.add(board, BorderLayout.CENTER);
		pane.add(controlsPanel, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
	}

	private void buildBoard(int dim) {
		for (int r = 0; r < dim; r++) {
			for (int c = 0; c < dim; c++) {
				JTextField field = new JTextField();
				field.setPreferredSize(new Dimension(50, 50));
				field.setHorizontalAlignment(JTextField.CENTER);
				Color bg = squareBackground(3, r, c); // ändra 3 senare
				field.setBackground(bg);
				field.setFont(new Font("SansSerif", Font.BOLD, 20));
				board.add(field);
				fields[r][c] = field;
			}
		}
	}

	private Color squareBackground(int size, int row, int col) {
		int gridRow = (row / size) * size;
		int gridCol = (col / size) * size;

		if (gridRow % 2 == 0 && gridCol % 2 == 0 || (gridRow == size && gridCol == size)) {
			return Color.ORANGE; // ändra färg om vi pallar japp
		}

		return Color.white;
	}

	private void cycleTestCases() {
		int[][] t1 = getTestCases().get(countTest);
		for (int r = 0; r < dim; r++) {
			for (int c = 0; c < dim; c++) {
				if (t1[r][c] == 0) {
					fields[r][c].setText(null);
				} else {
					fields[r][c].setText(Integer.toString(t1[r][c]));
				}
			}
		}
	}

	private void clear(int dim) {
		for (int r = 0; r < dim; r++) {
			for (int c = 0; c < dim; c++) {
				fields[r][c].setText(null);
			}
		}
		solver.clear();
	}

	private int[][] updateBoard() {
		int[][] newBoard = new int[dim][dim];
		illegalInput = false;
		for (int r = 0; r < dim; r++) {
			for (int c = 0; c < dim; c++) {
				if (fields[r][c].getText().equals("")) {
					newBoard[r][c] = 0;
				} else {
					try {
						int val = Integer.parseInt(fields[r][c].getText());
						if (val > 0 && val < 10) {
							newBoard[r][c] = val;
						} else {
							fields[r][c].setText(null);
							illegalInput = true;
						}
					} catch (Exception e) {
						fields[r][c].setText(null);
						illegalInput = true;
					}
				}
			}
		}
		return newBoard;
	}

	private ArrayList<int[][]> getTestCases() {
		ArrayList<int[][]> testCases = new ArrayList<int[][]>();
		int[][] t1 = { { 8, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 3, 6, 0, 0, 0, 0, 0 }, { 0, 7, 0, 0, 9, 0, 2, 0, 0 },
				{ 0, 5, 0, 0, 0, 7, 0, 0, 0 }, { 0, 0, 0, 0, 4, 5, 7, 0, 0 }, { 0, 0, 0, 1, 0, 0, 0, 3, 0 },
				{ 0, 0, 1, 0, 0, 0, 0, 6, 8 }, { 0, 0, 8, 5, 0, 0, 0, 1, 0 }, { 0, 9, 0, 0, 0, 0, 4, 0, 0 } };
		int[][] t2 = { { 0, 0, 2, 1, 0, 4, 3, 0, 0 }, { 0, 4, 0, 8, 0, 7, 0, 5, 0 }, { 8, 0, 1, 0, 9, 0, 7, 2, 0 },
				{ 2, 0, 5, 0, 0, 3, 0, 0, 0 }, { 0, 8, 0, 0, 0, 1, 0, 0, 3 }, { 0, 1, 0, 4, 0, 0, 0, 0, 0 },
				{ 1, 0, 0, 0, 0, 0, 5, 0, 2 }, { 0, 0, 4, 0, 1, 5, 6, 0, 0 }, { 0, 0, 8, 0, 3, 0, 0, 0, 7 } };

		int[][] t3 = { { 0, 0, 8, 0, 0, 9, 0, 6, 2 }, { 0, 0, 0, 0, 0, 0, 0, 0, 5 }, { 1, 0, 2, 5, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 2, 1, 0, 0, 9, 0 }, { 0, 5, 0, 0, 0, 0, 6, 0, 0 }, { 6, 0, 0, 0, 0, 0, 0, 2, 8 },
				{ 4, 1, 0, 6, 0, 8, 0, 0, 0 }, { 8, 6, 0, 0, 3, 0, 1, 0, 0 }, { 0, 0, 0, 0, 0, 0, 4, 0, 0 } };

		int[][] t4 = { { 0, 0, 0, 0, 0, 0, 3, 8, 6 }, { 6, 4, 0, 0, 0, 0, 0, 0, 9 }, { 8, 0, 0, 0, 0, 0, 0, 2, 4 },
				{ 2, 0, 5, 9, 0, 0, 0, 4, 1 }, { 4, 8, 9, 5, 0, 0, 0, 7, 3 }, { 0, 0, 0, 4, 0, 0, 9, 6, 5 },
				{ 1, 3, 6, 0, 0, 0, 0, 0, 0 }, { 9, 0, 0, 0, 1, 5, 0, 0, 0 }, { 5, 0, 0, 0, 3, 9, 4, 1, 7 } };

		testCases.add(t1);
		testCases.add(t2);
		testCases.add(t3);
		testCases.add(t4);

		return testCases;
	}

}
