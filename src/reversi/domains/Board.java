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
	 * M�thode qui permet de cr�er un objet de type Cell � chaque position dans le tableau � deux dimensions "cases[][]"
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
	 * M�thode qui permet de mettre � jour l'�tat des 4 cases centrales du game board, afin d'afficher les pions de d�part (2 noirs et
	 * 2 blancs).
	 * @param givenI correspond au num�ro de ligne d'un tableau
	 * @param givenJ correspond au num�ro de colonne d'un tableau
	 */
	
	public void setPawnOnDefaultPosition(int givenI, int givenJ) {
		if(givenI == 3 && givenJ == 3 || givenI == 4 && givenJ == 4) {
			this.cases[givenI][givenJ].setState(2);
		} else if (givenI == 3 && givenJ == 4 || givenI == 4 && givenJ == 3) {
			this.cases[givenI][givenJ].setState(1);
		}
	}
	
	/**
	 * M�thode qui permet de changer de couleur selon celle de l'objet de type Cell en param�tre
	 * @param givenCell correspond � l'objet Cell dont la couleur va �tre compar�e
	 * @return la couleur oppos�e � celle de l'objet Cell en param�tre
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
	 * M�thode qui permet de placer un pion
	 * @param givenCell correspond � l'objet Cell qui va subir un changement d'�tat
	 * @param givenPlayer correspond au joueur courant, duquel d�pendra la couleur du pion � poser
	 */
	
	public void placePawn(Cell givenCell, Player givenPlayer) {
		givenCell.setState(givenPlayer.getID());
	}
	
	/**
	 * M�thode qui permet de calculer le nombre de pions retourn�s apr�s le coup du joueur courant
	 * @param cell correspond � un objet de type Cell, duquel la 
	 * @param playerId correspond � l'ID du joueur courant
	 * @return le nombre de pions retourn�s
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
	 * M�thode qui permet de rafraichir le plateau de jeu en mettant � jour les cases qui correspondent � des legal move, afin de ne pas
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
	 * M�thode qui permet de d�tecter si les positions donn�es en param�tre se trouvent sur le plateau de jeu, elle sera utilis�e
	 * pour savoir si le curseur de la souris du joueur courant se trouvera, ou non, sur ce m�me plateau de jeu
	 * @param posX correspond � une position en X
	 * @param posY correspond � une position en Y
	 * @return une valeur bool�enne selon si les positions se trouvent sur le plateau de jeu ou non
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
	 * M�thode qui permet de r�cup�rer un objet de type Cell selon les positions donn�es en param�tres, en v�rifiant si celles-ci
	 * sont comprises dans les positions d'un objet de type Cell ou non
	 * @param posX correspond � une position en X
	 * @param posY correspond � une position en Y
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
