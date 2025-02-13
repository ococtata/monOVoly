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

	@Override
	public void run() {
		while(active) {
			try {
				Thread.sleep(PlayerStatConfig.ENERGY_REGEN_TICK_SPEED);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			player.increaseEnergy(calculateEnergyRegen());
		}
	}
	
	private int calculateEnergyRegen() {
		int finalEnergy = PlayerStatConfig.ENERGY_REGEN_BASE_AMOUNT * 
				(1 + player.getBarracksUpgradeLevel());
		return finalEnergy;
	}	
}
