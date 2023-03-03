package reversi.main;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import reversi.domains.Player;

public class HumanVsHuman extends Vs{
	
	public HumanVsHuman(Player p1, Player p2, EventChannel eventChannel) {
		super(p1,p2,eventChannel);
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		super.init(arg0, arg1);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		arg2.setColor(Color.white);
		arg2.drawString("Human Vs Human", 50, 50);
		super.render(arg0, arg1, arg2);
	}

	@Override
	public void update(GameContainer container, StateBasedGame arg1, int arg2) throws SlickException {
		super.update(container, arg1, arg2);
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 1;
	}

}
