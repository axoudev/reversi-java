package reversi.domains;

import reversi.main.MiniMax;

public abstract class Player {

	private final int ID;
	private int nbPawns;
	private char typeOfPlayer;
	protected int lastMoveRetournedPawns;
	
	/**
	 * Constructeur d'objet de type Player
	 * @param givenID correspond à l'id du joueur
	 * @param typeOfPlayer correspond au type du joueur
	 */
	
	public Player(int givenID, char typeOfPlayer) {
		this.ID = givenID;
		this.nbPawns = 2;
		this.typeOfPlayer = typeOfPlayer;
	}
	
	/**
	 * 
	 * @return l'ID du joueur
	 */
	
	public int getID() {
		return this.ID;
	}
	
	/**
	 * 
	 * @return le type du joueur
	 */
	
	public char getTypeOfPlayer() {
		return this.typeOfPlayer;
	}
	
	/**
	 * 
	 * @return le nombre de pions du joueur
	 */
	
	public int getNbPawns() {
		return this.nbPawns;
	}
	
	/**
	 * Met à jour le nombre de pions du joueur
	 * @param nbPawns nouveau nombre de pions du joueur
	 */
	
	public void setNbPawns(int nbPawns) {
		this.nbPawns = nbPawns;
	}
	
	/**
	 * Méthode qui sera utilisée par un objet de type Human pour placer un pion
	 * @param gameBoard correspond à l'objet Board sur lequel le pion sera posé
	 * @param X correspond à la position en X du futur pion
	 * @param Y correspond à la position en Y du futur pion
	 * @return l'objet de type Cell sur lequel le pion sera posé
	 */
	
	public Cell placePlayerCell(Board gameBoard, int X, int Y) {
		return null;	
	}
	
	/**
	 * Méthode qui sera utilisée par un objet de type Computer pour placer un pion
	 * @param gameBoard correspond à l'objet Board sur lequel le pion sera posé
	 * @param minimax correspond à un objet de type Minimax qui utilisera son algorithme
	 * @return l'objet de type Cell sur lequel le pion sera posé
	 */
	
	public Cell placePlayerCell(Board gameBoard, MiniMax minimax) {
		return null;
	}
	
	/**
	 * 
	 * @return le nombre de pions retournés par le dernier coup joué
	 */
	
	public int getLastMoveReturnedPawns() {
		return this.lastMoveRetournedPawns;
	}
	
}
