package model.entity;

import config.BoardConfig;
import config.ColorConfig;
import config.PlayerStatConfig;
import model.Position;

public class Player extends Entity {
	
	private String id;
	private int coins;
	private int gems;
	
	private int level;
	private int maxEnergy;
	private int barracksUpgradeLevel;
	private int currentEnergy;
	
	private Position mapPosition;

	public Player(String name, int money, Position position, String id, int level,
			int coins, int gems, int barracksUpgradeLevel,int currentEnergy) {
		super(name, money, position);
		this.id = id;
		this.level = level;
		this.coins = coins;
		this.gems = gems;
		this.barracksUpgradeLevel = barracksUpgradeLevel;
		this.currentEnergy = currentEnergy;
		
		this.mapPosition = new Position(0, 0);
		this.maxEnergy = calculateMaxEnergy();
		
		setPiece(ColorConfig.LIGHT_GREEN + BoardConfig.PLAYER_PIECE + ColorConfig.RESET);
	}
	
	private int calculateMaxEnergy() {
		float modifier = (float) (1 + (level * 0.1));
		return (int) (PlayerStatConfig.STAT_PLAYER_BASE_MAX_ENERGY * modifier);
	}
	
	@Override
	public void move() {
		// TODO Auto-generated method stub

	}

	public int getGems() {
		return gems;
	}

	public void setGems(int gems) {
		this.gems = gems;
	}

	public int getMaxEnergy() {
		return maxEnergy;
	}

	public void setMaxEnergy(int maxEnergy) {
		this.maxEnergy = maxEnergy;
	}

	public int getCurrentEnergy() {
		return currentEnergy;
	}
	
	public void increaseEnergy(int amount) {
		int finalEnergy = currentEnergy + amount;
		if(currentEnergy == maxEnergy) return;
		
		if(finalEnergy > maxEnergy) {
			currentEnergy = maxEnergy;
		}
		else {
			currentEnergy = finalEnergy;
		}
	}

	public void setCurrentEnergy(int currentEnergy) {
		this.currentEnergy = currentEnergy;
	}

	public Position getMapPosition() {
		return mapPosition;
	}

	public void setMapPosition(Position mapPosition) {
		this.mapPosition = mapPosition;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public int getBarracksUpgradeLevel() {
		return barracksUpgradeLevel;
	}

	public void setBarracksUpgradeLevel(int barracksUpgradeLevel) {
		this.barracksUpgradeLevel = barracksUpgradeLevel;
	}
}
