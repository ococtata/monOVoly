package manager;

import manager.thread.EnergyManager;
import manager.thread.PlayerMovementManager;
import model.entity.Player;
import view.BaseView;
import view.TitleScreenView;

public class GameManager {
	
	private static GameManager instance;
	private Player player;
	private BaseView currentView;
	private boolean isActive = true;

	private Thread playerMovementThread;
	private Thread energyManagerThread;

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
	
}
