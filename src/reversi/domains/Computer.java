package reversi.domains;

import java.util.Arrays;

import org.newdawn.slick.Color;

import reversi.main.MiniMax;

public class Computer extends Player{
	
	/**
	 * Constructeur d'objet de type Computer
	 * @param givenID correspond à l'id du computer
	 */

	public Computer(int givenID) {
		super(givenID, 'M');
	}
	
	/**
	 * Méthode de la classe Player qui appelle une autre méthode pour placer un pion
	 * @return la case sur laquelle le pion a été posé
	 */

	@Override
	public Cell placePlayerCell(Board gameBoard, MiniMax minimax) {
		return placeComputerPawn(gameBoard, minimax);
		
	}
	
	/**
	 * Méthode qui permet à l'IA de placer un pion selon l'algorithme de MiniMax
	 * @param gameBoard correspond à l'objet Board sur lequel le pion sera posé
	 * @param minimax correspond à l'objet de type MiniMax qui fera appel à l'algorithme
	 * @return la case sur laquelle le pion a été posé
	 */
	
	public Cell placeComputerPawn(Board gameBoard, MiniMax minimax) {
		Cell computerCell =  getComputerCell(gameBoard, minimax);
		this.setNbPawns(this.getNbPawns() + 1);
		gameBoard.placePawn(computerCell, this);
		this.lastMoveRetournedPawns = gameBoard.checkAllDirection(computerCell, this.getID());
		
		return computerCell;
	}
	
	/**
	 * Fait appel à l'algorithme MiniMax pour voir sur quelle case il faut poser le pion
	 * @param gameBoard correspond à l'objet Board sur lequel le pion sera posé
	 * @param minimax correspond à l'objet de type MiniMax qui fera appel à l'algorithme
	 * @return la case sur laquelle le pion va être posé et qui rapportera le plus de pions
	 */
	
	public Cell getComputerCell(Board gameBoard, MiniMax minimax) {
		return minimax.getEasyCell(gameBoard, this);
	}
	

}
