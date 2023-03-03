package reversi.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class BoardTests {
	
	Board gb = new Board();
	Board gb2 = new Board();
	
	Human h1 = new Human(1);
	Human h2 = new Human(2);

	@Test
	void createBoard() {
		assertNotNull(gb);
		assertNotEquals(gb, gb2);
	}
	
	@Test
	void putThePawnsOnDefaultPositions() {
		assertEquals(2, gb.getCases()[3][3].getState());
		assertEquals(2, gb.getCases()[4][4].getState());
		assertEquals(1, gb.getCases()[4][3].getState());
		assertEquals(1, gb.getCases()[3][4].getState());
	}
	
	@Test
	void returnTheGoodPositions() {
		assertEquals(100, gb.getPosition().getX());
		assertEquals(135, gb.getPosition().getY());
		assertEquals(535, gb.getPosition().getY() + 400);
	}
	
	@Test
	void aPlayerCanPlaceAPawn() {
		gb.placePawn(gb.getCases()[0][0], h1);
		assertEquals(1, gb.getCases()[0][0].getState());
	}
	
	@Test
	void doesItRefreshLegalMoves() {
		gb.getCases()[3][3].setState(3);
		gb.refreshLegalCases();
		assertNotEquals(3, gb.getCases()[3][3].getState());
	}
	
	@Test
	void isTheMouseOnBoard() {
		assertEquals(true, gb.isMouseOnBoard(150, 110));
		assertEquals(false, gb.isMouseOnBoard(0, 0));
		assertEquals(false, gb.isMouseOnBoard(130, 2000));
	}
	
	@Test
	void isItAValidDirection() {

		assertTrue(gb.validDirection(-1, -1, 4, 3, h1.getID()));
		assertTrue(gb.validDirection(-1, -1, 3, 3, h2.getID()));
		assertTrue(gb.validDirection(-1, 1, 4, 3, h1.getID()));
		assertTrue(gb.validDirection(-1, 0, 4, 3, h1.getID()));
		assertTrue(gb.validDirection(-1, 0, 3, 3, h2.getID()));
		assertTrue(gb.validDirection(1, 0, 4, 3, h1.getID()));
		assertTrue(gb.validDirection(1, 0, 3, 3, h2.getID()));
		assertTrue(gb.validDirection(1, -1, 4, 3, h1.getID()));
		assertTrue(gb.validDirection(1, -1, 3, 3, h2.getID()));
		assertTrue(gb.validDirection(1, 1, 4, 3, h1.getID()));
		assertTrue(gb.validDirection(1, 1, 3, 3, h2.getID()));
		assertFalse(gb.validDirection(-1, 1, 3, 3, h1.getID()));
	
	}
	
	@Test
	void checkAllDirectionTest() {
		Cell cell; 
		cell = gb.getCases()[3][2];
		assertEquals(1, gb.checkAllDirection(cell, h1.getID()));
		cell = gb.getCases()[2][4];
		assertEquals(1, gb.checkAllDirection(cell, h2.getID()));
	}
	
	
	@Test
	void validMoveTest() {
		assertTrue(!gb.validMove(-1, -1, 3, 6, h1.getID()));
		assertTrue(!gb.validMove(-1, -1, 4, 6, h1.getID()));
		assertTrue(!gb.validMove(-1, 1, 5, 6, h1.getID()));
		assertTrue(!gb.validMove(-1, 0, 6, 6, h1.getID()));
		assertTrue(!gb.validMove(-1, 0, 7, 6, h2.getID()));
		assertTrue(!gb.validMove(1, 0, 4, 3, h1.getID()));
		assertTrue(!gb.validMove(1, 0, 3, 3, h2.getID()));
		assertTrue(!gb.validMove(1, -1, 4, 3, h1.getID()));
		assertTrue(!gb.validMove(1, -1, 3, 3, h2.getID()));
		assertTrue(!gb.validMove(1, 1, 4, 3, h1.getID()));
		assertTrue(!gb.validMove(1, 1, 3, 3, h2.getID()));
		assertTrue(!gb.validMove(-1, 1, 3, 3, h1.getID()));
	}
	
	@Test
	void setLegalCases() {
		assertEquals(4,gb.setLegalCases(h1.getID()));
	}
	
	@Test
	void setCells() {
		Cell[][] cells = new Cell[8][8];
		
		for(int i = 0; i < cells.length; i++) {
			for(int j = 0; j < cells[i].length; j++) {
				cells[i][j] = new Cell(null, null, i, j);
			}
		}
		
		gb.setCells(cells);
		
		assertTrue(Arrays.equals(gb.getCases(), cells));
		
	}
}