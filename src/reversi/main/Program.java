package reversi.main;


import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import reversi.domains.Computer;
import reversi.domains.Human;
import reversi.domains.Player;

public class Program extends StateBasedGame{
	
	public Program(String name) {
		super(name);
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Program("Reversi"));
		
		app.setDisplayMode(1100, 600, false);
		app.setAlwaysRender(true);
		app.start();
	}
	
	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		EventChannel eventChannel = new EventChannel();	
		this.addState(new MainMenu());
		this.addState(new HumanVsHuman(new Human(1), new Human(2), eventChannel));
		this.addState(new HumanVsMachine(new Human(1), new Computer(2), eventChannel));
		EndGame endGame = new EndGame();
		eventChannel.add(endGame);
		this.addState(endGame);
	}
	

}
