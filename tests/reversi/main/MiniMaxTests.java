package reversi.main;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import reversi.domains.Board;
import reversi.domains.Computer;
import reversi.domains.Player;

class MiniMaxTests {

	Board gb = new Board();
	Player m = new Computer(2);
	
	MiniMax minimax = new MiniMax();
	
	@Test
	void getEasyCellTest() {
		gb.getCases()[3][3].setState(1);
		gb.getCases()[2][3].setState(1);
		gb.setLegalCases(m.getID());
		
		assertEquals(minimax.getEasyCell(gb, m), gb.getCases()[4][2]);
		
	}
	
}