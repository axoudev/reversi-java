package reversi.main;

public class GameStats {
	private int remainingPawns;
	private long gameTime;
	private int player1Pawns;
	private int player2Pawns;
	
	/**
	 * 
	 * @return le nombre de pions restants
	 */
	
	public int getRemainingPawns() {
		return this.remainingPawns;
	}
	
	/**
	 * 
	 * @return le temps en millisecondes
	 */
	
	public long getGameTime() {
		return this.gameTime;
	}
	
	/**
	 * 
	 * @return le nombre de pions du joueur 1
	 */
	
	public int getPlayer1Pawns() {
		return this.player1Pawns;
	}
	
	/**
	 * 
	 * @return le nombre de pions du joueur 2
	 */
	
	public int getPlayer2Pawns() {
		return this.player2Pawns;
	}
	
	/**
	 * Met à jour le nombre de pions restants
	 * @param remainingPawns
	 */
	
	public void setRemainingPawns(int remainingPawns) {
		this.remainingPawns = remainingPawns;
	}
	
	/**
	 * Met à jour le temps de jeu
	 * @param gameTime correspond au nouveau temps de jeu
	 */
	
	public void setGameTime(long gameTime) {
		this.gameTime = gameTime;
	}
	
	/**
	 * Met à jour le nombre de pions du joueur 1
	 * @param nbPawns correspond au nouveau nombre de pions du joueur 1
	 */
	
	public void setPlayer1Pawns(int nbPawns) {
		this.player1Pawns = nbPawns;
	}
	
	/**
	 * Met à jour le nombre de pions du joueur 2
	 * @param nbPawns
	 */
	
	public void setPlayer2Pawns(int nbPawns) {
		this.player2Pawns = nbPawns;
	}
}
