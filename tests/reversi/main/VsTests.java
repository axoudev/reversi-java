package reversi.main;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import reversi.domains.Board;
import reversi.domains.Human;

class VsTests {
	
	Human h1 = new Human(1);
	Human h2 = new Human(2);
	
	EventChannel ec = new EventChannel();
	
	Vs vs = new Vs(h1, h2, ec);
	
	@Test
	void createVersus() {
		assertNotNull(vs);
		assertNotNull(vs.getBoard());
	}
	
	@Test
	void doesTheSwitchPlayer() {
		assertEquals(h1, vs.getCurrentPlayer());
		vs.switchPlayer();
		assertEquals(h2, vs.getCurrentPlayer());
		vs.switchPlayer();
		assertEquals(h1, vs.getCurrentPlayer());
	}
	
	@Test
	void doesItReturnGameOver() {
		assertFalse(vs.isGameOver());
	}
	
	

}
