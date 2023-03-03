package reversi.main;

import org.newdawn.slick.Color;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class MainMenu extends BasicGameState{

	private Music music;

	/**
	 * Méthode permetant d'initialiser et de lancer une musique.
	 * @throws SlickException
	 */
	private void initMusic() throws SlickException {
		music = new Music("resources/ffvi.ogg");
		if(music.playing() == false) {
			music.play();
			music.setVolume(0.2f);
		}
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
			initMusic();
	}
	
	

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		Image mainBackground = new Image("resources/mainBackground.png");
		arg2.drawImage(mainBackground, 0,0);
		arg2.setColor(Color.white);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame arg1, int arg2) throws SlickException {
		if(container.getInput().isKeyDown(Input.KEY_H)) {
			arg1.enterState(1,new FadeOutTransition(),new FadeInTransition());
		}else if(container.getInput().isKeyDown(Input.KEY_M) || container.getInput().isKeyDown(Input.KEY_C)){
			arg1.enterState(2,new FadeOutTransition(),new FadeInTransition());
		}else if(container.getInput().isKeyDown(Input.KEY_Q)) {
			container.exit();
		}
		
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}
}
