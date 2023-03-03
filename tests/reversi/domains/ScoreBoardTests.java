package reversi.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ScoreBoardTests {
	
	Human p1 = new Human(1);
	Human p2 = new Human(2);
	
	ScoreBoard sb = new ScoreBoard(new Vector2d(100, 150), p1, p2);

	@Test
	void createScoreBoard() {
		assertNotNull(sb);
	}
	
	@Test
	void returnTheRightPlayer() {
		assertEquals(p1, sb.getPlayer(1));
		assertEquals(p2, sb.getPlayer(2));
	}
	
	@Test
	void doesItSetTheRightPlayerChoice() {
		sb.setPlayerChoice("A1");
		assertEquals("A1", sb.getCurrentPlayerChoice());
		sb.setPlayerChoice("B3");
		assertEquals("B3", sb.getCurrentPlayerChoice());
	}
	
	@Test
	void doesItSetTheLastMove() {
		sb.setLastMove("B3");
		assertEquals("B3", sb.getLastMove());
		sb.setLastMove("C2");
		assertEquals("C2", sb.getLastMove());
	}
	
	@Test
	void returnTheRightPositions() {
		assertEquals(100, sb.getX());
		assertEquals(150, sb.getY());
	}
}
