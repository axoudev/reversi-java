package reversi.domains;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.newdawn.slick.Color;

class CellTests {
	
	Cell cellTest = new Cell(new Vector2d(100, 150), Color.black, 2, 3);
	
	@Test
	void createCells() {
		final Cell cell1 = new Cell(new Vector2d(100, 150), Color.black, 2, 3);
		final Cell cell2 = new Cell(new Vector2d(200, 100), Color.white, 3, 2);
		assertNotNull(cell1);
		assertNotNull(cell2);
		assertNotSame(cell1, cell2);
	}

	@Test
	void returnTheRightPositions() {
		assertEquals(100, cellTest.getX());
		assertEquals(150, cellTest.getY());
	}
	
	@Test
	void returnTheRightColor() {
		assertEquals(Color.black, cellTest.getColor());
	}
	
	@Test
	void setANewState() {
		cellTest.setState(1);
		assertEquals(1, cellTest.getState());
	}
	
	@Test
	void updateTheState() {
		cellTest.setState(2);
		assertEquals(2, cellTest.getState());
	}
	
	@Test
	void returnTheRightRowAndColumn() {
		assertEquals(2, cellTest.getRow());
		assertEquals(3, cellTest.getColumn());
	}
	
	@Test
	void returnTheRightWidthAndHeight() {
		assertEquals(50, cellTest.getWidth());
		assertEquals(50, cellTest.getHeight());
	}
}
