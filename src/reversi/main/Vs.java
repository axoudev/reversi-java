package reversi.main;

import java.util.Objects;

import org.lwjgl.Sys;
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

import reversi.domains.Board;
import reversi.domains.Cell;
import reversi.domains.Player;
import reversi.domains.ScoreBoard;
import reversi.domains.Vector2d;

public class Vs extends BasicGameState {
	private Board gameBoard = new Board();
	private Player p1;
	private Player p2;
	private Player currentPlayer;
	private ScoreBoard scoreBoard;
	private int remainingPawns = 60;
	
	private Color messageColor = Color.white;
	private String message = "";
	
	private Image background;
	
	private String lastMove = "";
	private String currentMouseCell = "";
	
	private boolean gameOver = false;
	
	private long startTime;
	private long currentTime;
	
	private GameStats stats = new GameStats();
	private EventChannel eventChannel;
	
	private MiniMax minimax;
	
	/**
	 * Constructeur d'objet de type Vs
	 * @param p1 correspond au joueur 1
	 * @param p2 correspond au joueur 2
	 * @param eventChannel correspond à la classe qui va contenir tous les subscribers
	 */
	
	public Vs(Player p1, Player p2, EventChannel eventChannel) {
		this.p1 = p1;
		this.p2 = p2;
		this.currentPlayer = p1;
		this.scoreBoard = new ScoreBoard(new Vector2d(600,200),p1,p2);
		this.eventChannel = eventChannel;
		this.minimax = new MiniMax();
	}
	
	/**
	 * Méthode permet de récupérer l'état de la partie, si elle est finie ou non
	 * @return une valeur booléenne, true si la partie est terminé sinon false
	 */
	
	public boolean isGameOver() {
		return this.gameOver;
	}
	
	/**
	 * Méthode qui permet d'obtenir un objet de type Cell depuis le game board, et ce à une position donnée
	 * @param i correspond à une ligne du tableau
	 * @param j correspond à une colonne du tableau
	 * @return un objet de type Cell, dont la position dans le tableau équivaut à celle donnée en paramètre
	 */
	
	private Cell getCell(int i, int j) {
		return gameBoard.getCases()[i][j];
	}
	
	public void currentMouseCell(int posX, int posY) {
		Cell cell = gameBoard.getMouseCell(posX, posY);
		if(!Objects.isNull(cell)) {
			this.currentMouseCell = (cell.getRow()+1) + "" + (char)(cell.getColumn()+65);
		}else {
			this.currentMouseCell= "";
		}
	}
	
	/**
	 * Méthode qui permet de convertir le temps, de milliseconde(s) en format mm:ss
	 * @param milliSeconds correspond au temps en milliseconde(s) qui faut convertir
	 * @return un String correspondant au temps au format mm:ss
	 */
	
	private String toMs(long milliSeconds) {
		long totalSeconds = milliSeconds/1000;
		long minutes = (totalSeconds % 3600) / 60;
		long seconds = (totalSeconds % 60);
		
		return String.format("%02d:%02d", minutes, seconds);
	}
	
	/**
	 * Méthode qui permet d'obtenir le temps du système au moment où une partie a été lancée
	 */
	
	public void startTime() {
		this.startTime = Sys.getTime();
	}
	
	/**
	 * Méthode qui permet d'obtenir le temps écoulé depuis qu'une partie a été lancée
	 */
	
	public void currentTime() {
		this.currentTime = Sys.getTime() - startTime;
	}
	
	public void placePlayerPawn(int x, int y) {
		Cell cell;
		if(currentPlayer.getTypeOfPlayer() == 'H') {
			cell = currentPlayer.placePlayerCell(gameBoard, x, y);
		}else {
			cell = currentPlayer.placePlayerCell(gameBoard, minimax);
		}	
		
		if(currentPlayer.getLastMoveReturnedPawns() > 0 && !Objects.isNull(cell)) {
			updatePlayerPawns(currentPlayer.getLastMoveReturnedPawns());
			switchPlayer();
			setStringPos(cell.getRow(), cell.getColumn());
			remainingPawns --;
			if(remainingPawns <= 0) {
				gameOver = true;
			}
		}else {
			this.messageColor = Color.decode("#ff4757");
			this.message = "Vous ne pouvez pas placer votre pion à cet endroit";
		}
		
	}
	
	public void setStringPos(int row, int column) {
		this.lastMove = (row+1) + "" + (char)(column+65);
	}
	
	/**
	 * Méthode qui permet de changer de joueur
	 */

	public void switchPlayer() {
		if(currentPlayer.equals(p1)) {
			currentPlayer = p2;
		}else {
			currentPlayer = p1;
		}
	}
	
	/**
	 * Méthode qui permet de faire joueur l'IA
	 */
	
	public void placeComputerPawn() {
		int nbRetournedPawns = 0;
		Cell computerCell =  getComputerCell();
		gameBoard.placePawn(computerCell, this.currentPlayer);
		nbRetournedPawns = gameBoard.checkAllDirection(computerCell, this.currentPlayer.getID());
		updatePlayerPawns(nbRetournedPawns);
		switchPlayer();
		setStringPos(computerCell.getRow(), computerCell.getColumn());
		remainingPawns--;
		if(remainingPawns <= 0) {
			gameOver = true;
		}
	}
	
	/**
	 * 
	 * @return la case sur laquelle l'IA va jouer
	 */
	
	public Cell getComputerCell() {
		return minimax.getEasyCell(gameBoard, currentPlayer);
	}
	
	/**
	 * Méthode qui permet de mettre à jour le nombre de pions que possède le joueur sur le plateau, en prenant en compte ceux retournés
	 * lors de son dernier coup joué
	 * @param nbRetournedPawns correspond au nouveau nombre de pions que le joueur courant possède
	 */
	
	public void updatePlayerPawns(int nbRetournedPawns) {
		currentPlayer.setNbPawns(currentPlayer.getNbPawns() + nbRetournedPawns);
		if(currentPlayer.getID() == 1) {
			p2.setNbPawns(p2.getNbPawns() - nbRetournedPawns);
		}else {
			p1.setNbPawns(p1.getNbPawns() - nbRetournedPawns);
		}
		
		this.messageColor = Color.decode("#3498db");
		this.message = "Le joueur " + currentPlayer.getID() + " a retouné " + nbRetournedPawns + " pions lors du dernier tour";
	}
	
	/**
	 * Méthode qui permet d'amener le game over lorsqu'il n'y a plus de coups possible à jouer
	 */

	public void setLegalCases() {
		if(gameBoard.setLegalCases(currentPlayer.getID()) == 0) {
			gameOver = true;
		}
	}
	
	/**
	 * Méthode qui permet de créer des statistiques de jeu en fonction du scoreboard
	 */
	
	public void createStats() {
		this.stats.setRemainingPawns(this.remainingPawns);
		this.stats.setGameTime(this.currentTime);
		this.stats.setPlayer1Pawns(p1.getNbPawns());
		this.stats.setPlayer2Pawns(p2.getNbPawns());
	}
	
	public void publish(EventChannel eventChannel) {
		eventChannel.publish(stats);
	}

	/**
	 * Méthode qui permet de dessiner et afficher le plateau de jeu
	 * @param canvas correspond à l'objet Graphics sur lequel dessiner le plateau de jeu
	 */
	
	public void renderGameBoard(Graphics canvas) {
		for (int i = 0; i < gameBoard.getCases().length ; i++) {
			for (int j = 0; j < gameBoard.getCases().length; j++) {
				canvas.setColor(getCell(i, j).getColor());
				canvas.fillRect(getCell(i, j).getX(), getCell(i, j).getY(), 50, 50);
			}
		}
	}
	
	/**
	 * Méthode qui permet d'afficher le scoreboard sur l'écran
	 * @param canvas
	 */
	
	public void renderScoreBoard(Graphics canvas) {
        drawScoreBoard(canvas);
    }
	
	/**
	 * Méthode qui permet de dessiner le scoreboard sur l'écran de jeu
	 * @param canvas correspond à l'objet Graphics sur lequel dessiner
	 */

    public void drawScoreBoard(Graphics canvas) {
        canvas.setColor(Color.white);
        canvas.drawString("Au tour du joueur : " + currentPlayer.getID(), scoreBoard.getX(), scoreBoard.getY());
        canvas.drawString("Nombre de pions du joueur 1 : "+scoreBoard.getPlayer(1).getNbPawns(), 
                scoreBoard.getX(), scoreBoard.getY() + 20);
        canvas.drawString("Nombre de pions du joueur 2 : "+scoreBoard.getPlayer(2).getNbPawns(), 
                scoreBoard.getX(), scoreBoard.getY() + 40);
        canvas.drawString("Votre choix : " + currentMouseCell, scoreBoard.getX(), scoreBoard.getY() + 60);
        canvas.drawString("Dernier coup : " + lastMove, scoreBoard.getX(), scoreBoard.getY() + 80);
        canvas.drawString("Nombre de pions restants " + remainingPawns, scoreBoard.getX(), scoreBoard.getY() + 100);
    }
    
    /**
     * Méthode qui permet de mettre à joueur le plateau de jeu en changeant l'état des cases
     * @param canvas correspond à l'objet Graphics sur lequel dessiner les cases
     */
	
	public void renderStates(Graphics canvas) {
		for(Cell[] row : gameBoard.getCases()) {
			for(Cell cell : row) {
				if(cell.getState() == 1){
					drawPawn(canvas, Color.black, cell.getX(), cell.getY());
				}else if(cell.getState() == 2) {
					drawPawn(canvas, Color.white, cell.getX(), cell.getY());
				}else if(cell.getState() == 3) {
					drawLegalMove(canvas, Color.decode("#3498db"), cell.getX(), cell.getY());
				}
			}
		}
	}
	
	/**
	 * Méthode qui permet de dessiner et afficher des messages sur l'écran de jeu
	 * @param canvas correspond à l'objet Graphics sur lequel dessiner et afficher le message
	 */
	
	public void renderMessage(Graphics canvas) {
		canvas.setColor(this.messageColor);
		canvas.drawString(this.message, 600, 400);
	}
	
	/**
	 * Méthode qui permet de dessiner et afficher les coordonnées autour du plateau de jeu
	 * @param canvas correspond à l'objet Graphics sur lequel dessiner et afficher les coordonnées
	 */
	
	public void renderCoordinates(Graphics canvas) {
		int x = 120;
		int y = 110;
		for (int i = 0; i < 8; i++) {
			canvas.drawString(Character.toString((char)(i+65)), x, y);
			x += 50;
		}
		x = 80;
		y = 150;
		for (int i = 0; i < 8; i++) {
			canvas.drawString(""+(i+1), x, y);
			y += 50;
		}
		
	}
	
	/**
	 * Méthode qui permet de dessiner des pions
	 * @param canvas correspond à l'objet Graphics sur lequel dessiner le pion
	 * @param givenColor correspond à la couleur que possèdera le pion
	 * @param givenX correspond à la position en X du pion
	 * @param givenY correspond à la position en Y du pion
	 */
	
	private void drawPawn(Graphics canvas, Color givenColor, int givenX, int givenY) {
		canvas.setColor(givenColor);
		canvas.fillOval(givenX, givenY, 50, 50);
	}
	
	/**
	 * Méthode qui permet de dessiner des marqueurs sur des cases, ce qui avertira les joueurs qu'il s'agit des cases jouables
	 * @param canvas correspond à l'objet Graphics sur lequel dessiner le marqueur
	 * @param givenColor correspond à la couleur que possèdera le marqueur
	 * @param givenX correspond à la position en X du marqueur
	 * @param givenY correspond à la position en Y du marqueur
	 */
	
	private void drawLegalMove(Graphics canvas, Color givenColor, int givenX, int givenY) {
		canvas.setColor(givenColor);
		canvas.fillOval(givenX+20, givenY+20, 10, 10);
	}
	
	/**
	 * Méthode qui permet d'afficher un timer correspondant à la durée d'une partie
	 * @param canvas correspond à l'objet Graphics sur lequel dessiner le timer
	 */
	
	private void renderTimer(Graphics canvas) {
		canvas.setColor(Color.green);
        canvas.drawString(toMs(this.currentTime), 200, 20);
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		background = new Image("resources/background.png");
	}
	
	@Override
	public void enter(GameContainer arg0, StateBasedGame arg1) {
		startTime();
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		background.draw(0,0);
		renderCoordinates(arg2);
		renderGameBoard(arg2);
		renderStates(arg2);
		renderScoreBoard(arg2);
		renderMessage(arg2);
		currentTime();
		renderTimer(arg2);
	}

	@Override
	public void update(GameContainer container, StateBasedGame arg1, int arg2) throws SlickException {
		
		if(isGameOver() || container.getInput().isKeyDown(Input.KEY_SPACE)) {
			this.createStats();
			this.publish(eventChannel);
			arg1.enterState(3,new FadeOutTransition(),new FadeInTransition());
		}
		
		if(this.currentPlayer.getTypeOfPlayer() == 'H') {
			if(container.getInput().isMousePressed(0)) {
				//placePawnByClick(container.getInput().getMouseX(), container.getInput().getMouseY());	
				placePlayerPawn(container.getInput().getMouseX(), container.getInput().getMouseY());
			}
		}else {
			this.messageColor = Color.orange;
			this.message = "Appuyez sur Enter pour faire jouer l'ordinateur";
			if(container.getInput().isKeyDown(Input.KEY_ENTER)) {
				placePlayerPawn(container.getInput().getMouseX(), container.getInput().getMouseY());
			}
		}
		
		currentMouseCell(container.getInput().getMouseX(),container.getInput().getMouseY());
		setLegalCases();
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	// ========== GETTER & SETTER ==========
	
	public Board getBoard() {
		return this.gameBoard;
	}
	
	public Player getPlayer1() {
		return this.p1;
	}
	
	public Player getPlayer2() {
		return this.p2;
	}
	
	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}
	
	public ScoreBoard getScoreBoard() {
		return this.scoreBoard;
	}
	
	/**
	 * 
	 * @return le nombre de pions à jouer restants
	 */
	
	public int getRemainingPawns() {
		return this.remainingPawns;
	}
}
