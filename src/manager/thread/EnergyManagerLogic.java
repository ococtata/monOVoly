package manager.thread;

import config.PlayerStatConfig;
import model.entity.Player;

public class EnergyManagerLogic implements Runnable {
	private volatile boolean active = true;
	private Player player;
	
	public EnergyManagerLogic(Player player) {
		this.player = player;
	}
	
	public void deactivate() {
        active = false;
    }
	
	public void activate() {
		active = true;
	}

	@Override
	public void run() {
		while (true) { 
            if (active) {
            	try {
					Thread.sleep(PlayerStatConfig.ENERGY_REGEN_TICK_SPEED);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

                player.increaseEnergy(calculateEnergyRegen());
            } else {
            	synchronized (this) {
            		try {
            			wait();
            		} catch (InterruptedException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		}					
				}
            }
        }
	}
	
	private int calculateEnergyRegen() {
		int finalEnergy = PlayerStatConfig.ENERGY_REGEN_BASE_AMOUNT;
		return finalEnergy;
	}	
}
