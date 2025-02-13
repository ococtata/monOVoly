package manager.thread;

import model.entity.Player;

public class EnergyManager {
	private static EnergyManager instance;
	private Thread energyManagerThread;
	private EnergyManagerLogic energyManagerLogic;
	
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
        if (!energyManagerThread.isAlive()) {
    		energyManagerThread = new Thread(energyManagerLogic);
    		energyManagerThread.setDaemon(true);
    		energyManagerThread.start();
        }
    }

    public void stop() {
    	energyManagerLogic.deactivate();	
    	energyManagerThread.interrupt();
    }
    
    public Thread getEnergyManagerThread() {
    	return energyManagerThread;
    }

	public EnergyManagerLogic getEnergyManagerLogic() {
		return energyManagerLogic;
	}
	
}
