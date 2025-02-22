package manager.thread;

import manager.GameManager;
import model.entity.Player;

public class EnergyManager {
	private static EnergyManager instance;
	private Thread energyManagerThread;
	private EnergyManagerLogic energyManagerLogic;
	private boolean isRunning = false;
	
	private EnergyManager(Player player) {
		energyManagerLogic = new EnergyManagerLogic(player);
		energyManagerThread = new Thread(energyManagerLogic);
		energyManagerThread.setDaemon(true);
	}
	
	public static EnergyManager getInstance(Player player) {
		if(instance == null) instance = new EnergyManager(player);
		return instance;
	}
	
	public void start() {
		if (!isRunning) {
            energyManagerLogic = new EnergyManagerLogic(GameManager.getInstance().getPlayer());
            energyManagerThread = new Thread(energyManagerLogic);
            energyManagerThread.setDaemon(true);
            energyManagerThread.start();
            isRunning = true;
        } else {
            energyManagerLogic.activate();
        }
    }

	public void stop() {
        if (isRunning) { 
            energyManagerLogic.deactivate();
            isRunning = false;
        }
    }
    
    public Thread getEnergyManagerThread() {
    	return energyManagerThread;
    }

	public EnergyManagerLogic getEnergyManagerLogic() {
		return energyManagerLogic;
	}
	
}
