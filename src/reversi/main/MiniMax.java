package reversi.main;

import java.util.Arrays;

import org.newdawn.slick.Color;

import reversi.domains.Board;
import reversi.domains.Cell;
import reversi.domains.Player;
import reversi.domains.Vector2d;

public class MiniMax {
	
	private Board gameBoardCopy = new Board();
	
	/**
	 * Méthode permettant d'obtenir la case la sur laquelle un pion retournera le plus de pions
	 * @param board Correspond au plateau de jeu
	 * @param computer Correspond au joueur de type ordinateur
	 * 
	 * @return la case la sur laquelle un pion retournera le plus de pions
	 */
	public Cell getEasyCell(Board board, Player computer) {
		gameBoardCopy.setCells(createCellsCopy(board.getCases()));
		
		int nbReturnedPawns = 0;
		int temp;
		Cell bestCell = null;
		for(Cell[] row : gameBoardCopy.getCases()) {
			for(Cell cell : row) {
				if(cell.getState() == 3) {
					gameBoardCopy.placePawn(cell, computer);
					
					temp = gameBoardCopy.checkAllDirection(cell, computer.getID());
					if(temp >= nbReturnedPawns) {
						bestCell = cell;
						nbReturnedPawns = temp;
					}
					gameBoardCopy.setCells(createCellsCopy(board.getCases()));
				}
			}
		}
		return board.getCases()[bestCell.getRow()][bestCell.getColumn()];
	}
	
	/**
	 * Méthode permetant de créer une copie des cases 
	 * @param originalCells cases originales
	 * 
	 * @return la copie des cases
	 */
	public Cell[][] createCellsCopy(Cell[][] originalCells){
		Cell[][] cellsCopy = new Cell[originalCells.length][originalCells.length];
		for(Cell[] row : originalCells) {
			for(Cell cell : row) {
				Cell cellCopy = new Cell(new Vector2d(cell.getX(), cell.getY()), cell.getColor(), cell.getRow(), cell.getColumn());
				cellCopy.setState(cell.getState());
				cellsCopy[cellCopy.getRow()][cellCopy.getColumn()] = cellCopy;
			}
		}
		
		return cellsCopy;
		
	}
	
}
