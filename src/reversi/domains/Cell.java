package reversi.domains;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

public class Cell {

	private final int WIDTH = 50;
	private final int HEIGHT = 50;

	private final Vector2d dir;
	
	private final Color cellColor;
	
	private int state = 0;
	
	private final int row;
	private final int column;

	public Cell(Vector2d dir, Color givenColor, int row, int column) {
		this.dir = dir;
		this.cellColor = givenColor;
		this.row = row;
		this.column = column;
	}

	public Color getColor() {
		return this.cellColor;
	}

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}

	public int getX() {
		return this.dir.getX();
	}

	public int getY() {
		return this.dir.getY();
	}

	public void setState(int givenState) {
		this.state = givenState;
	}
	
	public int getState() {
		return this.state;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getColumn() {
		return this.column;
	}
	
}
