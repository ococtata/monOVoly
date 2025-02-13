package view;

import manager.GameManager;

public interface PlayerGUI {
	default String getName() {
		String name = GameManager.getInstance().getPlayer().getName();
		return name;
	}
	
	default String getEnergy() {
		String energy = String.format("Energy: %d/%d", 
				GameManager.getInstance().getPlayer().getCurrentEnergy(),
				GameManager.getInstance().getPlayer().getMaxEnergy());
		return energy;
	}
	
	default int getLevel() {
		int level = GameManager.getInstance().getPlayer().getLevel();
		return level;
	}
	
	default void printTopBar() {
		String topBar = String.format(" %s lvl. %d | %s", getName(), getLevel(), getEnergy());
		System.out.println(" " + topBar);
	}
}	
