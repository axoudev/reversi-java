package reversi.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HumanTests {
	
	Board gb = new Board();
	
	Human h1 = new Human(1);
	Human h2 = new Human(2);

	@Test
	void createHumans() {
		assertNotNull(h1);
		assertNotNull(h2);
		assertNotEquals(h1, h2);
	}
	
	@Test
	void returnNullIfMouseIsntOnBoard() {
		assertEquals(null, h1.placePlayerCell(gb, 2000, 2000));
	}
	
	@Test
	void returnNullIfMouseIsOnCellWithAStateEquals0() {
		assertEquals(null, h1.placePawnByClick(gb, 150, 150));
	}
	
	@Test
	void canPlacePawnByClick() {
		gb.getCases()[0][0].setState(3);
		assertEquals(gb.getCases()[0][0], h1.placePawnByClick(gb, 101, 136));
		assertEquals(null, h1.placePawnByClick(gb, 151, 186));
	}

}
