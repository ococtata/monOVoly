package manager.thread;

import model.entity.Player;

public class PlayerMovementManager {
	private static PlayerMovementManager instance;
    private Thread playerMovementThread;
    private PlayerMovementLogic playerMovementLogic;

    private PlayerMovementManager(Player player) {
    	playerMovementLogic = new PlayerMovementLogic(player);
    	playerMovementThread = new Thread(playerMovementLogic);
    	playerMovementThread.setDaemon(true);
    }

    public static PlayerMovementManager getInstance(Player player) {
        if (instance == null) instance = new PlayerMovementManager(player);
        return instance;
    }

    public void start() {
        if (playerMovementThread == null || !playerMovementThread.isAlive()) {
            playerMovementThread = new Thread(playerMovementLogic);
            playerMovementThread.setDaemon(true);
            playerMovementThread.start();
        }
    }
    
    public Thread getPlayerMovementThread() {
    	return playerMovementThread;
    }
    
    public void pause() {
    	playerMovementLogic.setPaused(true);
    }
    
    public void unpause() {
    	playerMovementLogic.setPaused(false);
    }
}
