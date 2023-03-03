package reversi.domains;

public class Vector2d {

	private final int POS_X;
	private final int POS_Y;
	
	/**
	 * Constructeur d'objet de type Vector2d
	 * @param givenPosX correspond à la position en X
	 * @param givenPosY correspond à la position en Y
	 */
	
	public Vector2d(int givenPosX, int givenPosY) {
		this.POS_X = givenPosX;
		this.POS_Y = givenPosY;
	}
	
	/**
	 * 
	 * @return la position en X
	 */
	
	public int getX() {
		return this.POS_X;
	}
	
	/**
	 * 
	 * @return la position en Y
	 */
	
	public int getY() {
		return this.POS_Y;
	}
	
}
