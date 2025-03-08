package model.entity;

import java.awt.Color;

import config.BoardConfig;
import config.ColorConfig;
import config.PlayerStatConfig;
import manager.GameManager;
import model.Position;
import model.block.PropertyBlock;
import model.entity.inventory.PlayerInventory;
import utility.Scanner;
import utility.TextUtil;

public class Player extends Entity implements Scanner {
	
	private String id;
	private String username;
	private String email;
	private String password;
	private int gems;
	
	private int level;
	private int maxEnergy;
	private int currentEnergy;
	private int currentExp;
	
	private Position mapPosition;

	public Player(String id, String name, String email, String password, int money, int level,
            int gems, int currentExp, int currentEnergy) {
        super(name, money);
        this.id = id;
        this.level = level;
        this.gems = gems;
        this.currentExp = currentExp;
        this.currentEnergy = currentEnergy;

        this.email = email;
        this.password = password;
        this.mapPosition = new Position(0, 0);
        this.maxEnergy = calculateMaxEnergy();

        setColor(ColorConfig.LIGHT_GREEN);
        setPiece(getColor() + BoardConfig.PLAYER_PIECE + ColorConfig.RESET);
        setInventory(new PlayerInventory());
        setName(ColorConfig.GREEN + name + ColorConfig.RESET);
    }
	
	private int calculateMaxEnergy() {
		float modifier = (float) (1 + (level * 0.1));
		return (int) (PlayerStatConfig.STAT_PLAYER_BASE_MAX_ENERGY * modifier);
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
	
	public void reduceGems(int amount) {
		this.gems -= amount;
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

	@Override
	public PropertyBlock chooseProperty() {
	    PropertyBlock chosen = null;

	    int pageSize = 3;
	    int currentPage = 0;

	    while (true) {
	        TextUtil.clearScreen();
	        int startIndex = currentPage * pageSize;
	        int endIndex = Math.min((currentPage + 1) * pageSize, getOwnedProperties().size());

	        System.out.println(" Your Properties (Page " + (currentPage + 1) + " of " + (int) Math.ceil((double) getOwnedProperties().size() / pageSize) + "):");

	        for (int i = startIndex; i < endIndex; i++) {
	            PropertyBlock property = getOwnedProperties().get(i);
	            TextUtil.printHorizontalBorder(BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));
	            System.out.println(" " + (i + 1) + ". " + property.getName());
	            TextUtil.printHorizontalBorder(BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));
	            System.out.println("    Value: $" + property.getPrice());
	            System.out.println("    Toll: $" + property.calculateToll(property));
	            System.out.println("    Building Level: " + property.getBuildingLevel());
	            System.out.println("    Has Landmark: " + (property.hasLandmark() ? "Yes" : "No"));
	            System.out.println();
	        }
	        TextUtil.printHorizontalBorder(BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));

	        if (endIndex < getOwnedProperties().size()) {
	            System.out.println(" 1. Previous Page");
	            System.out.println(" 2. Next Page");
	            System.out.println(" 3. Do Nothing");
	            System.out.println(" Type property name to choose.");
	        } else if (currentPage > 0) {
	            System.out.println(" 1. Previous Page");
	            System.out.println(" 3. Do Nothing");
	            System.out.println(" Type property name to choose.");
	        } else {
	        	System.out.println(" 3. Do Nothing");
	            System.out.println(" Type property name to choose.");
	        }

	        System.out.print(" >> ");

	        String input = scan.nextLine().trim();

	        try {
	            int choice = Integer.parseInt(input);
	            if (choice == 3) {
	                return null;
	            } 
	            else if (choice == 1) {
	                if (currentPage > 0) {
	                    currentPage--;
	                } 
	                else {
	                    System.out.println(" You are already on the first page.");
	                    TextUtil.pressEnter();
	                }
	            } 
	            else if (choice == 2) {
	                if (endIndex < getOwnedProperties().size()) {
	                    currentPage++;
	                } 
	                else {
	                    System.out.println(" You are already on the last page.");
	                    TextUtil.pressEnter();
	                }
	            } 
	            else {
	                System.out.println(" Invalid choice. Please enter 1, 2, 3, or a property name.");
	                TextUtil.pressEnter();
	            }
	        } 
	        catch (NumberFormatException e) {
	            for (PropertyBlock property : getOwnedProperties()) {
	                if (property.getName().equalsIgnoreCase(input)) {
	                    return property;
	                }
	            }
	            System.out.println(" Invalid property name. Try again or enter 0 to cancel.");
	            TextUtil.pressEnter();
	        }
	    }
	}


	@Override
	public Entity getEnemy() {
		Enemy enemy = GameManager.getInstance().getEnemy();
		return enemy;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public int getCurrentExp() {
        return currentExp;
    }

    public void setCurrentExp(int currentExp) {
        this.currentExp = currentExp;
    }

    public void addExp(int amount) {
        this.currentExp += amount;
        checkLevelUp();
    }

    private void checkLevelUp() {
        int expToLevelUp = level * 10;
        if (currentExp >= expToLevelUp) {
            level++;
            currentExp -= expToLevelUp;
            System.out.println("Congratulations! You leveled up to level " + level + "!");
            maxEnergy = calculateMaxEnergy();
            System.out.println("Your max energy is now " + maxEnergy);
        }
    }
}	