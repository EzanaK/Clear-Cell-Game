package model;

import java.util.Random;

/**
 * This class extends GameModel and implements the logic of the clear cell game.
 * We define an empty cell as BoardCell.EMPTY. An empty row is defined as one
 * where every cell corresponds to BoardCell.EMPTY.
 * 
 * @author Department of Computer Science, UMCP
 */

public class ClearCellGame extends Game {
	
	private Random random;
	private int score;

	/**
	 * Defines a board with empty cells. It relies on the super class constructor to
	 * define the board. The random parameter is used for the generation of random
	 * cells. The strategy parameter defines which clearing cell strategy to use
	 * (for this project it will be 1). For fun, you can add your own strategy by
	 * using a value different that one.
	 * 
	 * @param maxRows
	 * @param maxCols
	 * @param random
	 * @param strategy
	 */
	public ClearCellGame(int maxRows, int maxCols, Random random, int strategy) {
		super(maxRows, maxCols);
		this.random = random;
		score = 0;
	}

	/**
	 * The game is over when the last board row (row with index board.length -1) is
	 * different from empty row.
	 */
	public boolean isGameOver() {
		int lastRow = board.length - 1;
		int col;
		for (col = 0; col < board[lastRow].length; col++) {
			if (!board[lastRow][col].getName().equals(".")) {
				return true;
			}
		}
		return false;
	}

	public int getScore() {
		return score;
	}

	/**
	 * This method will attempt to insert a row of random BoardCell objects if the
	 * last board row (row with index board.length -1) corresponds to the empty row;
	 * otherwise no operation will take place.
	 */
	public void nextAnimationStep() {
		if (!isGameOver()) {
			int row, col;
			for (row = board.length - 1; row > 0 ;row--) {
				board[row] = board[row - 1];
			}
			BoardCell[] newRow = new BoardCell[board[0].length];
			for (col = 0; col < board[0].length; col++) {
				newRow[col] = BoardCell.getNonEmptyRandomBoardCell(random);
			}
			board[0] = newRow;
		}
	}

	/**
	 * This method will turn to BoardCell.EMPTY the cell selected and any adjacent
	 * surrounding cells in the vertical, horizontal, and diagonal directions that
	 * have the same color. The clearing of adjacent cells will continue as long as
	 * cells have a color that corresponds to the selected cell. Notice that the
	 * clearing process does not clear every single cell that surrounds a cell
	 * selected (only those found in the vertical, horizontal or diagonal
	 * directions).
	 * 
	 * IMPORTANT: Clearing a cell adds one point to the game's score.<br />
	 * <br />
	 * 
	 * If after processing cells, any rows in the board are empty,those rows will
	 * collapse, moving non-empty rows upward. For example, if we have the following
	 * board (an * represents an empty cell):<br />
	 * <br />
	 * RRR<br />
	 * GGG<br />
	 * YYY<br />
	 * * * *<br/>
	 * <br />
	 * then processing each cell of the second row will generate the following
	 * board<br />
	 * <br />
	 * RRR<br />
	 * YYY<br />
	 * * * *<br/>
	 * * * *<br/>
	 * <br />
	 * IMPORTANT: If the game has ended no action will take place.
	 * 
	 * 
	 */
	public void processCell(int rowIndex, int colIndex) {
		if (!isGameOver() || !isIndexOffTheBoard(board, rowIndex, colIndex)) {
			BoardCell cellType = board[rowIndex][colIndex];
			emptyVertical(board, cellType, rowIndex, colIndex);
			emptyHorizontal(board, cellType, rowIndex, colIndex);
			emptyDiagonal(board, cellType, rowIndex, colIndex);
			int row;
			for (row = 0; row < board.length; row++) {
				if (isRowEmpty(board, row)) {
					board = collapseRow(board, row);
				}
			}
		}
	}
	
	private void emptyVertical(BoardCell[][] board, BoardCell cellType, int rowIndex, int colIndex) {
		int row;
		for (row = rowIndex; row >= 0; row--) {
			if (board[row][colIndex] != cellType) {
				break;
			} else {
				board[row][colIndex] = BoardCell.EMPTY;
				score++;
			}
		}
		for (row = rowIndex + 1; row < board.length; row++) {
			if (board[row][colIndex] != cellType) {
				break;
			} else {
				board[row][colIndex] = BoardCell.EMPTY;
				score++;
			}
		}
	}
	
	private void emptyHorizontal(BoardCell[][] board, BoardCell cellType, int rowIndex, int colIndex) {
		int col;
		for (col = colIndex - 1; col >= 0; col--) {
			if (board[rowIndex][col] != cellType) {
				break;
			} else {
				board[rowIndex][col] = BoardCell.EMPTY;
				score++;
			}
		}
		for (col = colIndex + 1; col < board[0].length; col++) {
			if (board[rowIndex][col] != cellType) {
				break;
			} else {
				board[rowIndex][col] = BoardCell.EMPTY;
				score++;
			}
		}
	}
	
	private void emptyDiagonal(BoardCell[][] board, BoardCell cellType, int rowIndex, int colIndex) {
		int row, col;
		for (row = rowIndex - 1, col = colIndex - 1; row >= 0 && col >= 0; row--, col--) {
			if (board[row][col] != cellType) {
				break;
			} else {
				board[row][col] = BoardCell.EMPTY;
				score++;
			}
		}

		for (row = rowIndex - 1, col = colIndex + 1; row >= 0 && col < board[0].length; row--, col++) {
			if (board[row][col] != cellType) {
				break;
			} else {
				board[row][col] = BoardCell.EMPTY;
				score++;
			}
		}
		for (row = rowIndex + 1, col = colIndex - 1; row < board.length && col >= 0; row++, col--) {
			if (board[row][col] != cellType) {
				break;
			} else {
				board[row][col] = BoardCell.EMPTY;
				score++;
			}
		}
		for (row = rowIndex + 1, col = colIndex + 1; row < board.length && col < board[0].length; row++, col++) {
			if (board[row][col] != cellType) {
				break;
			} else {
				board[row][col] = BoardCell.EMPTY;
				score++;
			}
		}
	}

	private boolean isIndexOffTheBoard(BoardCell[][] board, int rowIndex, int colIndex) {
		return (rowIndex > board.length - 1 || rowIndex < 0 || colIndex > board[0].length - 1 || colIndex < 0);
	}
	
	private BoardCell[][] collapseRow(BoardCell[][] board, int collapsedRowIndex) {
		int boardHeight = board.length;
		int boardWidth = board[0].length;
		BoardCell[][] newBoard = new BoardCell[boardHeight][boardWidth];
		int row;
		for (row = 0; row < collapsedRowIndex; row++) {
			newBoard[row] = board[row];
		}
		for (row = boardHeight - 2; row >= collapsedRowIndex; row--) {
			newBoard[row] = board[row + 1]; 
		}
		newBoard[boardHeight - 1] = board[collapsedRowIndex];
		return newBoard;
	}
	
	private boolean isRowEmpty(BoardCell[][] board, int rowIndex) {
		int col;
		for (col = 0; col < board[0].length; col++) {
			if (board[rowIndex][col] != BoardCell.EMPTY) {
				return false;
			}
		}
		return true;
	}
	
}