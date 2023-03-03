package reversi.domains;

import java.util.Arrays;

import org.newdawn.slick.Color;

public class Board {
	
	private final Vector2d dir = new Vector2d(100,135);

	private Cell cases[][] = new Cell[8][8];
	
	/**
	 * Constructeur d'objet de type Board
	 */
	
	public Board() {
		createCells();
	}
	
	/**
	 * Méthode qui permet de créer un objet de type Cell à chaque position dans le tableau à deux dimensions "cases[][]"
	 * 
	 */
	
	private void createCells() {
		Color currentColor = Color.decode("#34495e");
		int x = dir.getX();
		int y = dir.getY();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				cases[i][j] = new Cell(new Vector2d(x,y), currentColor, i, j);
				x += cases[i][j].getWidth();
				currentColor = switchColor(cases[i][j]);
				setPawnOnDefaultPosition(i, j);

			}
			x = 100;
			y += cases[i][0].getHeight();
			currentColor = switchColor(cases[i][0]);
		}
	}
	
	/**
	 * Méthode qui permet de mettre à jour l'état des 4 cases centrales du game board, afin d'afficher les pions de départ (2 noirs et
	 * 2 blancs).
	 * @param givenI correspond au numéro de ligne d'un tableau
	 * @param givenJ correspond au numéro de colonne d'un tableau
	 */
	
	public void setPawnOnDefaultPosition(int givenI, int givenJ) {
		if(givenI == 3 && givenJ == 3 || givenI == 4 && givenJ == 4) {
			this.cases[givenI][givenJ].setState(2);
		} else if (givenI == 3 && givenJ == 4 || givenI == 4 && givenJ == 3) {
			this.cases[givenI][givenJ].setState(1);
		}
	}
	
	/**
	 * Méthode qui permet de changer de couleur selon celle de l'objet de type Cell en paramètre
	 * @param givenCell correspond à l'objet Cell dont la couleur va être comparée
	 * @return la couleur opposée à celle de l'objet Cell en paramètre
	 */
	
	public Color switchColor(Cell givenCell) {
		if(givenCell.getColor().equals(Color.decode("#34495e"))) {
			return Color.decode("#2ecc71");
		} else {
			return Color.decode("#34495e");
			
		}
	}
	
	//##########################################################################
	
	/**
	 * Méthode qui permet de placer un pion
	 * @param givenCell correspond à l'objet Cell qui va subir un changement d'état
	 * @param givenPlayer correspond au joueur courant, duquel dépendra la couleur du pion à poser
	 */
	
	public void placePawn(Cell givenCell, Player givenPlayer) {
		givenCell.setState(givenPlayer.getID());
	}
	
	/**
	 * Méthode qui permet de calculer le nombre de pions retournés après le coup du joueur courant
	 * @param cell correspond à un objet de type Cell, duquel la 
	 * @param playerId correspond à l'ID du joueur courant
	 * @return le nombre de pions retournés
	 */
	
	public int checkAllDirection(Cell cell, int playerId) {
		int nbReturnedPawns = 0;
		nbReturnedPawns += checkReturnable(-1, -1, cell.getRow(), cell.getColumn(), cell, playerId, false);
		nbReturnedPawns += checkReturnable(-1, 0, cell.getRow(), cell.getColumn(), cell, playerId, false);
		nbReturnedPawns += checkReturnable(-1, 1, cell.getRow(), cell.getColumn(), cell, playerId, false);
		nbReturnedPawns += checkReturnable(0, -1, cell.getRow(), cell.getColumn(), cell, playerId, false);
		nbReturnedPawns += checkReturnable(0, 1, cell.getRow(), cell.getColumn(), cell, playerId, false);
		nbReturnedPawns += checkReturnable(1, -1, cell.getRow(), cell.getColumn(), cell, playerId, false);
		nbReturnedPawns += checkReturnable(1, 0, cell.getRow(), cell.getColumn(), cell, playerId, false);
		nbReturnedPawns += checkReturnable(1, 1, cell.getRow(), cell.getColumn(), cell, playerId, false);
		
		return nbReturnedPawns;
		
	}
	
	public int checkReturnable(int rowDirection, int columnDirection, int currentRow, int currentColumn, Cell cell, int playerId, boolean atLeastOneOtherState) {
		int otherState,state, nbRetournedPawns;
		if(playerId == 2) {
			otherState = 1;
			state = 2;
		}
		else {
			otherState = 2;
			state = 1;
		}
		
		if(!(currentRow + rowDirection < 0) && !(currentRow + rowDirection > this.cases.length - 1)) {
			if(!(currentColumn + columnDirection < 0 )&& !(currentColumn + columnDirection > this.cases.length - 1)) {
				if(this.cases[currentRow + rowDirection][currentColumn + columnDirection].getState() == otherState) {
					return checkReturnable(rowDirection, columnDirection, currentRow + rowDirection, currentColumn + columnDirection, cell, playerId, true);
				}else if(this.cases[currentRow + rowDirection][currentColumn + columnDirection].getState() == playerId) {
					if(atLeastOneOtherState) {
						nbRetournedPawns = returnPawns(rowDirection, columnDirection, cell.getRow(), cell.getColumn(), otherState, state, 0);
						return nbRetournedPawns;
					}
				}
			}
		}
		return 0;
	}
	
	public int returnPawns(int rowDirection, int columnDirection, int currentRow, int currentColumn, int otherState, int state, int nbRetournedPawns) {
		if(this.cases[currentRow + rowDirection][currentColumn + columnDirection].getState() == otherState) {
			this.cases[currentRow + rowDirection][currentColumn + columnDirection].setState(state);
			return returnPawns(rowDirection, columnDirection, currentRow + rowDirection, currentColumn + columnDirection, otherState, state, nbRetournedPawns + 1);
		}else {
			return nbRetournedPawns;
		}
	}
	
	//##########################################################################
	
	/**
	 * Méthode qui permet de rafraichir le plateau de jeu en mettant à jour les cases qui correspondent à des legal move, afin de ne pas
	 * avoir des legal move qui ne devraient pas avoir lieu au prochain tour
	 */
	
	public void refreshLegalCases() {
		for(Cell[] row : this.cases) {
			for(Cell cell : row) {
				if(cell.getState() == 3) {
					cell.setState(0);
				}
			}
		}
	}
	
	
	
	public boolean validDirection(int rowDirection, int columnDirection, int currentRow, int currentColumn, int playerId) {
		
		if(cases[currentRow][currentColumn].getState() == playerId) return true;
		else if(cases[currentRow][currentColumn].getState() == 0 || cases[currentRow][currentColumn].getState() == 3) return false;
		if(currentRow + rowDirection < 0 || currentRow + rowDirection > this.cases.length -1) return false;
		if(currentColumn + columnDirection < 0 || currentColumn + columnDirection > this.cases.length - 1) return false;
		
		
		return validDirection(rowDirection, columnDirection, currentRow + rowDirection, currentColumn + columnDirection, playerId);
	}
	
	public boolean validMove(int rowDirection, int columnDirection, int currentRow, int currentColumn, int playerId) {
		int otherState;
		
		if(playerId == 2) otherState = 1;
		else otherState = 2;

		if(currentRow + rowDirection*2 < 0 || currentRow + rowDirection*2 > this.cases.length - 1) return false;
		else if(currentRow + rowDirection < 0 || currentRow + rowDirection > this.cases.length - 1) return false;
		if(currentColumn + columnDirection*2 < 0 || currentColumn + columnDirection*2 > this.cases.length - 1) return false;
		else if(currentColumn + columnDirection < 0 || currentColumn + columnDirection > this.cases.length - 1) return false;
		
		if(cases[currentRow + rowDirection][currentColumn + columnDirection].getState() != otherState) return false;
		
		return validDirection(rowDirection, columnDirection, currentRow + rowDirection*2, currentColumn + columnDirection*2, playerId);
	}
	
	public int setLegalCases(int playerId) {
		boolean nw, nn, ne, ww, ee, sw, ss, se;
		int nbLegalMoves = 0;
		
		refreshLegalCases();
		
		for(int i = 0; i < this.cases.length; i++) {
			for(int j = 0; j < this.cases[i].length; j++) {
				if(cases[i][j].getState() == 0) {
					nw = validMove(-1, -1, i, j, playerId);
					nn = validMove(-1, 0, i, j, playerId);
					ne = validMove(-1, 1, i, j, playerId);
					
					ww = validMove(0, -1, i, j, playerId);
					ee = validMove(0, 1, i, j, playerId);
					
					sw = validMove(1, -1, i, j, playerId);
					ss = validMove(1, 0, i, j, playerId);
					se = validMove(1, 1, i, j, playerId);
					
					if(nw || nn || ne || ww || ee || sw || ss || se) {
						this.cases[i][j].setState(3);
						nbLegalMoves++;
					}
				}
			}
		}
		return nbLegalMoves;
	}
	
	
	//##########################################################################
	
	/**
	 * Méthode qui permet de détecter si les positions données en paramètre se trouvent sur le plateau de jeu, elle sera utilisée
	 * pour savoir si le curseur de la souris du joueur courant se trouvera, ou non, sur ce même plateau de jeu
	 * @param posX correspond à une position en X
	 * @param posY correspond à une position en Y
	 * @return une valeur booléenne selon si les positions se trouvent sur le plateau de jeu ou non
	 */
	
	public boolean isMouseOnBoard(int posX, int posY) {
		if(posX > this.dir.getX() && posX < this.dir.getY() + (8 * 50)) {
			if(posY > this.dir.getX() && posY < this.dir.getY() + (8 * 50)) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
	
	/**
	 * Méthode qui permet de récupérer un objet de type Cell selon les positions données en paramètres, en vérifiant si celles-ci
	 * sont comprises dans les positions d'un objet de type Cell ou non
	 * @param posX correspond à une position en X
	 * @param posY correspond à une position en Y
	 * @return un objet de type Cell dont X et Y sont compris dans ses positions
	 */
	
	public Cell getMouseCell(int posX, int posY) {
		for(Cell[] row : this.cases) {
			for(Cell cell : row) {
				if(posX > cell.getX() && posX < cell.getX() + cell.getWidth()) {
					if(posY > cell.getY() && posY < cell.getY() + cell.getHeight()) {
						return cell;
					}
				}
			}
		}
		return null;
	}
	
	public void setCells(Cell[][] cells) {
		this.cases = Arrays.copyOf(cells, cells.length);
	}
	
	public Cell[][] getCases() {
		return this.cases;
	}
	
	public Vector2d getPosition() {
		return dir;
	}

}
