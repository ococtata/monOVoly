package manager;

import java.util.List;

import manager.thread.EnergyManager;
import model.entity.Enemy;
import model.entity.Player;
import model.game.GameBoard;
import utility.Scanner;
import view.BaseView;
import view.TitleScreenView;
import view.game.map.MapGachaView;
import view.game.map.MapShopView;
import view.game.map.MapSpawnView;
import view.game.map.MapTradeView;

public class GameManager implements Scanner{
	
	private static GameManager instance;
	private Player player;
	private Enemy enemy;
	private BaseView currentView;
	private boolean isActive = true;

	private Thread energyManagerThread;
	
	private boolean isPlayerTurn = false;
	private boolean isFirstTurn = true;
	private GameBoard gameBoard;
	
	private List<Player> players;

	private GameManager() {
		
	}

	public static GameManager getInstance() {
		if(instance == null) instance = new GameManager();
		return instance;
	}
	
	private void initializeGame() {
		SaveLoadManager.getInstance().loadAll();
		MapManager.getInstance().loadAllMaps();
	}
	
	public void startGame() {
		isActive = true;
		initializeGame();
		new TitleScreenView();
	}
	
	private void initializeThreads() {
		energyManagerThread = EnergyManager.getInstance(player).getEnergyManagerThread();
	}
	
	public void runGame() {
		while (isActive) {
            BaseView currentView = getCurrentView();
            if (currentView != null) { 
                currentView.show();

                if (scan.hasNextLine()) { 
                    String input = scan.nextLine();
                    if (!input.isEmpty()) { 
                    	char firstChar = input.charAt(0);
                    	if (currentView instanceof MapSpawnView) {
                			((MapSpawnView) currentView).getMapSpawnViewController().handleMovement(firstChar);
                		} 
                		else if(currentView instanceof MapGachaView) {
                			((MapGachaView) currentView).getMapGachaViewController().handleMovement(firstChar);
                		}
                		else if(currentView instanceof MapShopView) {
                			((MapShopView) currentView).getMapShopViewController().handleMovement(firstChar);
                		}
                		else if(currentView instanceof MapTradeView) {
                			((MapTradeView) currentView).getMapTradeViewController().handleMovement(firstChar);
                		}
                    		
                    	// make take multiple input (prone to error cuz of map not updating fast enough before the check collision)
//                    	for(char firstChar : input.toCharArray()) {
//                    		if(firstChar == '\n') continue;
//                    		if (currentView instanceof MapSpawnView) {
//                    			((MapSpawnView) currentView).getMapSpawnViewController().handleMovement(firstChar);
//                    		} 
//                    		else if(currentView instanceof MapGachaView) {
//                    			((MapGachaView) currentView).getMapGachaViewController().handleMovement(firstChar);
//                    		}
//                    		else if(currentView instanceof MapShopView) {
//                    			((MapShopView) currentView).getMapShopViewController().handleMovement(firstChar);
//                    		}
//                    		else if(currentView instanceof MapTradeView) {
//                    			((MapTradeView) currentView).getMapTradeViewController().handleMovement(firstChar);
//                    		}
//                    		try {
//								Thread.sleep(150);
//							} catch (InterruptedException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//                    	}
                    }
                }
            }
		}
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
		initializeThreads();
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
		EnergyManager.getInstance(player).start();
	}

	public Thread getEnergyManagerThread() {
		return energyManagerThread;
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
	
	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}
}
