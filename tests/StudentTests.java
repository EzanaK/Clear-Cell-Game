package tests;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import model.BoardCell;
import model.ClearCellGame;
import model.Game;

/* The following directive executes tests in sorted order */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class StudentTests {
	
	@Test
	public void testingConstructorsAndGameGetMethods() {
		Game ccGame = new ClearCellGame(4, 5, new Random(1L), 1);
		assertTrue(ccGame.getMaxRows() == 4);
		assertTrue(ccGame.getMaxCols() == 5);
		int row, col;
		for (row = 0; row < 4; row++) {
			for (col = 0; col < 5; col++) {
				assertTrue(ccGame.getBoardCell(row, col) == BoardCell.EMPTY);
			}
		}
		String answer = getBoardStr(ccGame);
		System.out.println(answer);
	}
	
	@Test
	public void testingSetBoardCell() {
		Game ccGame = new ClearCellGame(8, 3, new Random(1L), 1);
		ccGame.setBoardCell(3, 1, BoardCell.RED);
		assertTrue(ccGame.getBoardCell(3, 1) == BoardCell.RED);
		ccGame.setBoardCell(7, 0, BoardCell.GREEN);
		assertTrue(ccGame.getBoardCell(7, 0) == BoardCell.GREEN);
		String answer = getBoardStr(ccGame);
		System.out.println(answer);
	}
	
	@Test
	public void testingSetRowWithColor() {
		Game ccGame = new ClearCellGame(6, 5, new Random(1L), 1);
		ccGame.setRowWithColor(2, BoardCell.YELLOW);
		int col;
		for (col = 0; col < 5; col++) {
			assertTrue(ccGame.getBoardCell(2, col) == BoardCell.YELLOW);
		}
		String answer = getBoardStr(ccGame);
		System.out.println(answer);
	}
	
	@Test
	public void testingSetColWithColor() {
		Game ccGame = new ClearCellGame(3, 3, new Random(1L), 1);
		ccGame.setColWithColor(1, BoardCell.BLUE);
		int row;
		for (row = 0; row < 3; row++) {
			assertTrue(ccGame.getBoardCell(row, 1) == BoardCell.BLUE);
		}
		String answer = getBoardStr(ccGame);
		System.out.println(answer);
	}
	
	@Test
	public void testingSetBoardWithColor() {
		Game ccGame = new ClearCellGame(9, 9, new Random(1L), 1);
		ccGame.setBoardWithColor(BoardCell.RED);
		int row, col;
		for (row = 0; row < 9; row++) {
			for (col = 0; col < 9; col++) {
				assertTrue(ccGame.getBoardCell(row, col) == BoardCell.RED);
			}
		}
		String answer = getBoardStr(ccGame);
		System.out.println(answer);
	}
	
	@Test
	public void testingIsGameOver() {
		Game ccGame = new ClearCellGame(5, 4, new Random(1L), 1);
		assertFalse(ccGame.isGameOver());
		String answer = getBoardStr(ccGame);
		System.out.println(answer);
		ccGame = new ClearCellGame(5, 4, new Random(1L), 1);
		ccGame.setRowWithColor(4, BoardCell.YELLOW);
		assertTrue(ccGame.isGameOver());
		answer = getBoardStr(ccGame);
		System.out.println(answer);
		ccGame = new ClearCellGame(5, 4, new Random(1L), 1);
		ccGame.setBoardCell(4, 1, BoardCell.RED);
		ccGame.setBoardCell(4, 3, BoardCell.BLUE);
		assertTrue(ccGame.isGameOver());
		answer = getBoardStr(ccGame);
		System.out.println(answer);
	}
	
	@Test
	public void testinGetScore() {
		Game ccGame = new ClearCellGame(5, 5, new Random(1L), 1);
		ccGame.setBoardWithColor(BoardCell.BLUE);
		String answer = getBoardStr(ccGame);
		System.out.println("Before Processing Cells:\n" + answer);
		ccGame.processCell(2, 2);
		answer = getBoardStr(ccGame);
		System.out.println("After Processing Cells:\n" + answer);
		System.out.println("Score: " + ccGame.getScore());
		assertTrue(ccGame.getScore() == 17);
	}
	
	@Test
	public void testingNextAnimationStep() {
		Game ccGame = new ClearCellGame(3, 3, new Random(1L), 1);
		ccGame.nextAnimationStep();
		String answer = getBoardStr(ccGame);
		System.out.println("After first animation step:\n" + answer);
		ccGame.nextAnimationStep();
		answer = getBoardStr(ccGame);
		System.out.println("After second animation step:\n" + answer);
		ccGame.nextAnimationStep();
		assertTrue(ccGame.isGameOver());
		answer = getBoardStr(ccGame);
		System.out.println("After third animation step:\n" + answer);
	}
	
	@Test
	public void testingProcessCells() {
		Game ccGame = new ClearCellGame(6, 6, new Random(1L), 1);
		ccGame.setBoardWithColor(BoardCell.BLUE);
		ccGame.setBoardCell(1, 2, BoardCell.YELLOW);
		ccGame.setBoardCell(5, 2, BoardCell.YELLOW);
		ccGame.setBoardCell(3, 0, BoardCell.YELLOW);
		ccGame.setBoardCell(3, 4, BoardCell.YELLOW);
		ccGame.setBoardCell(0, 5, BoardCell.YELLOW);
		ccGame.setBoardCell(5, 4, BoardCell.YELLOW);
		String answer = getBoardStr(ccGame);
		System.out.println("Before Processing Cells:\n" + answer);
		ccGame.processCell(3, 2);
		answer = getBoardStr(ccGame);
		System.out.println("After Processing Cells:\n" + answer);

	}
	
	@Test
	public void testingCollapsingRows() {
		Game ccGame = new ClearCellGame(7, 7, new Random(1L), 1);
		ccGame.setBoardWithColor(BoardCell.GREEN);
		ccGame.setRowWithColor(3, BoardCell.EMPTY);
		ccGame.setRowWithColor(5, BoardCell.BLUE);
		ccGame.setBoardCell(4, 4, BoardCell.BLUE);
		ccGame.setBoardCell(3, 5, BoardCell.BLUE);
		String answer = getBoardStr(ccGame);
		System.out.println("Before Processing Cells:\n" + answer);
		ccGame.processCell(5, 3);
		answer = getBoardStr(ccGame);
		System.out.println("After Processing Cells:\n" + answer);
	}
	
	/* Support methods */
	private static String getBoardStr(Game game) {
		int maxRows = game.getMaxRows(), maxCols = game.getMaxCols();

		String answer = "Board(Rows: " + maxRows + ", Columns: " + maxCols + ")\n";
		for (int row = 0; row < maxRows; row++) {
			for (int col = 0; col < maxCols; col++) {
				answer += game.getBoardCell(row, col).getName();
			}
			answer += "\n";
		}

		return answer;
	}
}
