package manager;

import manager.thread.EnergyManager;
import manager.thread.PlayerMovementManager;
import model.GameBoard;
import model.entity.Enemy;
import model.entity.Player;
import view.BaseView;
import view.TitleScreenView;

public class GameManager {
	
	private static GameManager instance;
	private Player player;
	private Enemy enemy;
	private BaseView currentView;
	private boolean isActive = true;

	private Thread playerMovementThread;
	private Thread energyManagerThread;
	
	private boolean isPlayerTurn = false;
	private boolean isFirstTurn = true;
	private GameBoard gameBoard;

	private GameManager() {
		
	}
	
	public static GameManager getInstance() {
		if(instance == null) instance = new GameManager();
		return instance;
	}
	
	private void initializeGame() {
		MapManager.getInstance().loadAllMaps();
	}
	
	public void startGame() {
		isActive = true;
		initializeGame();
		new TitleScreenView();
	}
	
	private void initializeThreads() {
		playerMovementThread = PlayerMovementManager.getInstance(player).getPlayerMovementThread();
		energyManagerThread = EnergyManager.getInstance(player).getEnergyManagerThread();
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
		initializeThreads();
	}

	public Thread getPlayerMovementThread() {
		return playerMovementThread;
	}
	
	public BaseView getCurrentView() {
		return currentView;
	}

	public void setCurrentView(BaseView currentView) {
		MapManager.getInstance().setCurrentMap(currentView.getMap());
		this.currentView = currentView;
	}
	
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public void startThreads() {
		playerMovementThread.start();
		energyManagerThread.start();
	}
	
	public void runThreads() {
		playerMovementThread.run();
		energyManagerThread.run();
	}

	public Thread getEnergyManagerThread() {
		return energyManagerThread;
	}
	
	public void pausePlayerMovementThread() {
		PlayerMovementManager.getInstance(player).pause();
	}	
	
	public void unPausePlayerMovementThread() {
		PlayerMovementManager.getInstance(player).unpause();
	}

	public boolean isPlayerTurn() {
		return isPlayerTurn;
	}

	public void setPlayerTurn(boolean isPlayerTurn) {
		this.isPlayerTurn = isPlayerTurn;
	}

	public boolean isFirstTurn() {
		return isFirstTurn;
	}

	public void setFirstTurn(boolean isFirstTurn) {
		this.isFirstTurn = isFirstTurn;
	}

	public Enemy getEnemy() {
		return enemy;
	}

	public void setEnemy(Enemy enemy) {
		this.enemy = enemy;
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}

	public void setGameBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}
}
