package reversi.domains;

import java.util.Arrays;

import org.newdawn.slick.Color;

import reversi.main.MiniMax;

public class Computer extends Player{
	
	/**
	 * Constructeur d'objet de type Computer
	 * @param givenID correspond � l'id du computer
	 */

	public Computer(int givenID) {
		super(givenID, 'M');
	}
	
	/**
	 * M�thode de la classe Player qui appelle une autre m�thode pour placer un pion
	 * @return la case sur laquelle le pion a �t� pos�
	 */

	@Override
	public Cell placePlayerCell(Board gameBoard, MiniMax minimax) {
		return placeComputerPawn(gameBoard, minimax);
		
	}
	
	/**
	 * M�thode qui permet � l'IA de placer un pion selon l'algorithme de MiniMax
	 * @param gameBoard correspond � l'objet Board sur lequel le pion sera pos�
	 * @param minimax correspond � l'objet de type MiniMax qui fera appel � l'algorithme
	 * @return la case sur laquelle le pion a �t� pos�
	 */
	
	public Cell placeComputerPawn(Board gameBoard, MiniMax minimax) {
		Cell computerCell =  getComputerCell(gameBoard, minimax);
		this.setNbPawns(this.getNbPawns() + 1);
		gameBoard.placePawn(computerCell, this);
		this.lastMoveRetournedPawns = gameBoard.checkAllDirection(computerCell, this.getID());
		
		return computerCell;
	}
	
	/**
	 * Fait appel � l'algorithme MiniMax pour voir sur quelle case il faut poser le pion
	 * @param gameBoard correspond � l'objet Board sur lequel le pion sera pos�
	 * @param minimax correspond � l'objet de type MiniMax qui fera appel � l'algorithme
	 * @return la case sur laquelle le pion va �tre pos� et qui rapportera le plus de pions
	 */
	
	public Cell getComputerCell(Board gameBoard, MiniMax minimax) {
		return minimax.getEasyCell(gameBoard, this);
	}
	

}
