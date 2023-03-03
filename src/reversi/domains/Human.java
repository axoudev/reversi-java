package reversi.domains;

public class Human extends Player{
	
	/**
	 * Constructeur d'objet de type Human
	 * @param givenID correspond � l'id du joueur
	 */

	public Human(int givenID) {
		super(givenID, 'H');
	}
	
	/**
	 * M�thode qui appelle la m�thode place placePawnByClick pour le joueur humain
	 */

	@Override
	public Cell placePlayerCell(Board gameBoard, int X, int Y) {
		return placePawnByClick(gameBoard, X, Y);
		
	}
	
	/**
	 * M�thode qui permet de placer un pion sur le plateau de jeu 
	 * @param gameBoard correspond � l'objet de type Board sur lequel le pion va �tre placer
	 * @param posX correspond � la position en X du futur pion
	 * @param posY correspond � la position en Y du futur pion
	 * @return l'objet de type Cell sur lequel le pion sera pos�
	 */
	
	public Cell placePawnByClick(Board gameBoard, int posX, int posY) {
		if(gameBoard.isMouseOnBoard(posX, posY)) {
			Cell mouseCell = gameBoard.getMouseCell(posX, posY);
			if(mouseCell != null) {
				if(mouseCell.getState() == 3) {
					this.setNbPawns(this.getNbPawns() + 1);
					gameBoard.placePawn(mouseCell, this);
					this.lastMoveRetournedPawns = gameBoard.checkAllDirection(mouseCell, this.getID());
					
					return mouseCell;
				}else{
					return null;			
				}
			}else {
				return null;
			}
		}
		return null;
	}


}
