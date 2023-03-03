package reversi.domains;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Vector2dTests {
	
	Vector2d v1 = new Vector2d(100, 150);
	Vector2d v2 = new Vector2d(200, 100);

	@Test
	public void createVector2d() {
		
		assertNotNull(v1);
		assertNotNull(v2);
		assertNotSame(v1, v2);
	}
	
	@Test
	public void returnTheRightPositions() {
		assertEquals(100, v1.getX());
		assertEquals(150, v1.getY());
		assertEquals(200, v2.getX());
		assertEquals(100, v2.getY());
	}

}
