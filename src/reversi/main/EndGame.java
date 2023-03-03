package reversi.main;

import java.util.ArrayList;
import java.util.Objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class EndGame extends BasicGameState implements Subscriber{
	private boolean release = false;
	private GameStats stats;
	private int totalP1Win = 0, totalP2Win = 0, totalDraw = 0;
	private int nbGamePlayed = 0;
	private long longerTime;
	private long lowerTime;
	private long averageTime;
	
	
	/**
	 * Méthodes qui transforme des milisecondes au format minutes:secondes
	 * @param milliSeconds  correspond aux nombre de millisecondes écoulées depuis l'entrée dans la partie
	 * 
	 * @return Une chaine de caractère représentant le temps écoulé pendant la partie
	 */
	private String toMinutesSecondes(long milliSeconds) {
		long totalSeconds = milliSeconds/1000;
		long minutes = (totalSeconds % 3600) / 60;
		long seconds = (totalSeconds % 60);
		
		return String.format("%02d minutes %02d secondes", minutes, seconds);
	}
	
	/**
	 * Méthode qui enregistre les données de toute les parties jouées
	 * 
	 * @param stats Correspond au statistiques de la partie
	 */
	private void setGlobalStats(GameStats stats) {
		
		nbGamePlayed ++;
		
		if(stats.getPlayer1Pawns() > stats.getPlayer2Pawns()) {
			totalP1Win ++;
		}else if(stats.getPlayer1Pawns() < stats.getPlayer2Pawns()) {
			totalP2Win ++;
		}else {
			totalDraw ++;
		}
		
		if(!(longerTime == 0 && lowerTime == 0)) {
			if(stats.getGameTime() > longerTime) longerTime = stats.getGameTime();
			else if(stats.getGameTime() < lowerTime) lowerTime = stats.getGameTime();
			averageTime = ((averageTime*(nbGamePlayed - 1)) + stats.getGameTime()) / nbGamePlayed;
		}else {
			longerTime = stats.getGameTime();
			lowerTime = stats.getGameTime();
			averageTime = stats.getGameTime();
		}
		
		
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		arg2.setColor(Color.decode("#40739e"));
		arg2.drawString("Resultats", 50, 50);

		arg2.drawString("Statistiques de cette partie : ", 50, 75);
		if(stats.getPlayer1Pawns() > stats.getPlayer2Pawns()) {
			arg2.drawString("Le joueur 1 à gagné la partie !", 50, 100);
		}else if(stats.getPlayer1Pawns() < stats.getPlayer2Pawns()) {
			arg2.drawString("Le joueur 2 à gagné la partie !", 50, 100);
		}else {
			arg2.drawString("Egalité", 50, 100);
		}
		
		arg2.drawString("Nombre de pions du joueur 1 : " + stats.getPlayer1Pawns(), 50, 130);
		arg2.drawString("Nombre de pions du joueur 2 : " + stats.getPlayer2Pawns(), 50, 145);
		
		arg2.drawString("La partie à duré : " + toMinutesSecondes(stats.getGameTime()), 50, 160);
		
		
		arg2.drawString("Statistiques de toutes les parties : ", 600, 75);
		arg2.drawString("Le joueur 1 à gagné " + this.totalP1Win + " fois ", 600, 95);
		arg2.drawString("Le joueur 2 à gagné " + this.totalP2Win + " fois ", 600, 110);
		arg2.drawString("Il y a eu " + this.totalDraw + " égalités ", 600, 125);
		arg2.drawString("Partie la plus longue :  " + toMinutesSecondes(this.longerTime), 600, 140);
		arg2.drawString("Partie la plus courte :  " + toMinutesSecondes(this.lowerTime), 600, 155);
		arg2.drawString("Les parties ont duré en moyenne :  " + toMinutesSecondes(this.averageTime), 600, 170);
	}

	@Override
	public void update(GameContainer container, StateBasedGame arg1, int arg2) throws SlickException {
		
		if(!container.getInput().isKeyDown(Input.KEY_SPACE)) {
			release = true;
		}else if(container.getInput().isKeyDown(Input.KEY_SPACE) && release) {
			release = false;
			container.reinit();
			arg1.enterState(0,new FadeOutTransition(),new FadeInTransition());
		}
		
		
	}
	/**
	 * Méthode permetant de reçevoir les statistiques de la partie
	 */
	@Override
	public void fire(GameStats stats) {
		this.stats = stats;
		setGlobalStats(stats);
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 3;
	}

}
