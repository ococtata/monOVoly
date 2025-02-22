package view;

import config.ColorConfig;
import manager.GameManager;

public interface PlayerGUI {
	default String getName() {
		String name = GameManager.getInstance().getPlayer().getName();
		return name;
	}
	
	default String getEnergy() {
		String energy = String.format("Energy: %s%d%s/%s%d%s", 
				ColorConfig.YELLOW,
				GameManager.getInstance().getPlayer().getCurrentEnergy(),
				ColorConfig.RESET, ColorConfig.YELLOW,
				GameManager.getInstance().getPlayer().getMaxEnergy(), 
				ColorConfig.RESET);
		return energy;
	}
	
	default int getLevel() {
		int level = GameManager.getInstance().getPlayer().getLevel();
		return level;
	}
	
	default int getGems() {
		int gems = GameManager.getInstance().getPlayer().getGems();
		return gems;
	}
	
	default void printTopBar() {
		String topBar = String.format(" %s lvl. %d | %s", getName(), getLevel(), getEnergy());
		System.out.println(" " + topBar);
		System.out.println("  Gems: " + getGems());
	}
	
	default void printGachaTopBar() {
		String topBar = String.format(" %s lvl. %d \t| \tGems: %d", getName(), getLevel(), getGems());
		System.out.println(" " + topBar);
	}
}	
