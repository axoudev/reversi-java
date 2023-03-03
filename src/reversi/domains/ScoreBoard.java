package reversi.domains;

public class ScoreBoard {

	private Vector2d sbDir;
	private Player p1;
	private Player p2;
	private String currentPlayerChoice;
	private String lastMove;
	
	/**
	 * Constructeur d'objet de type ScoreBoard
	 * @param givenDir correspond à sa position en X et Y
	 * @param givenP1 correspond au joueur 1
	 * @param givenP2 correspond au joueur 2
	 */
	
	public ScoreBoard(Vector2d givenDir, Player givenP1, Player givenP2) {
		this.sbDir = givenDir;
		this.p1 = givenP1;
		this.p2 = givenP2;
	}
	
	/**
	 * 
	 * @param nbPlayer correspond à l'ID du joueur qu'il faut retourner
	 * @return un objet de type Player dont l'ID correspond au paramètre
	 */
	
	public Player getPlayer(int nbPlayer) {
		if(nbPlayer == 1) {
			return p1;
		} else {
			return p2;
		}
	}
	
	/**
	 * 
	 * @return le choix actuel de positionnement de pion du joueur
	 */
	
	public String getCurrentPlayerChoice() {
		return this.currentPlayerChoice;
	}
	
	/**
	 * Met à jour la position de la cellule sur laquelle le joueur veut poser son pion
	 * @param givenChoice chaine de caractère qui correspond aux coordonnées d'une case
	 */
	
	public void setPlayerChoice(String givenChoice) {
		this.currentPlayerChoice = givenChoice;
	}
	
	/**
	 * 
	 * @return le dernier coup joué
	 */
	
	public String getLastMove() {
		return this.lastMove;
	}
	
	/**
	 * Met à joueur le dernier coup joué
	 * @param givenMove correspond au dernier coup joué
	 */
	
	public void setLastMove(String givenMove) {
		this.lastMove = givenMove;
	}
	
	/**
	 * 
	 * @return la position en X de l'objet destinataire
	 */
	
	public int getX() {
		return this.sbDir.getX();
	}
	
	/**
	 * 
	 * @return la position en Y de l'objet destinataire
	 */
	
	public int getY() {
		return this.sbDir.getY();
	}
	
	
}
