package model.entity;

import java.util.ArrayList;
import java.util.List;

import config.GameConfig;
import manager.GameManager;
import model.block.GenericBlock;
import model.block.PropertyBlock;
import model.entity.inventory.BaseInventory;
import model.gacha.character.BaseCharacter;
import utility.TextUtil;

public abstract class Entity {
	private String name;
	private int money;
	private int totalAssest;
	private int rank;

	private String piece;
	private String color;
	
	private int boardIndex;
	private List<PropertyBlock> ownedProperties;
	
	private boolean isInJail;
	private boolean isBankrupt;
	private boolean isFrozen;
	
	private BaseCharacter equippedCharacter;
	private BaseInventory inventory;
	
	public Entity(String name, int money) {
		super();
		this.name = name;
		this.money = money;
		this.ownedProperties = new ArrayList<PropertyBlock>();
		this.isInJail = false;
		this.isBankrupt = false;
		this.isFrozen = false;
	}
	
	public void setInventory(BaseInventory inventory) {
		this.inventory = inventory;
	}

	public void addProperty(PropertyBlock property) {
		ownedProperties.add(property);
	}
	
	public void removeProperty(PropertyBlock property) {
		ownedProperties.remove(property);
	}
	
	public void updateTotalAssets() {
	    int totalPropertyValue = 0;

	    for (PropertyBlock property : ownedProperties) {
	        totalPropertyValue += property.getPrice();
	    }

	    this.totalAssest = this.money + totalPropertyValue;
	}
	
	public void pay(Entity receiver, int amount) {
	    if (this.money >= amount) {
	        this.money -= amount;
	        if (receiver != null) {
	            receiver.setMoney(receiver.getMoney() + amount);
	        }
	        updateTotalAssets();
	    } 
	    else {
	        int moneyNeeded = amount - this.money;
	        System.out.println(" " + this.name + " doesn't have enough money. Need $" + moneyNeeded + " more.");

	        while (this.money < amount && !ownedProperties.isEmpty()) {
	            PropertyBlock propertyToSell = chooseProperty();
	            if (propertyToSell != null) {
	                if (receiver instanceof Enemy || receiver instanceof Player) {
	                    int sellPrice = (int) (propertyToSell.getPrice() * (1 - (GameConfig.SELL_PROPERTY_ENEMY_DISCOUNT_PERCENTAGE / 100.0)));
	                    if (receiver.getMoney() >= sellPrice) {
	                        sellProperty(propertyToSell, receiver);
	                    } 
	                    else {
	                        sellProperty(propertyToSell, null);
	                    }
	                } 
	                else {
	                    sellProperty(propertyToSell, null);
	                }
	            } 
	            else {
	                break;
	            }
	        }

	        if (this.money >= amount) {
	            this.money -= amount;
	            if (receiver != null) {
	                receiver.setMoney(receiver.getMoney() + amount);
	            }
	            updateTotalAssets();
	        } else {
	            System.out.println(" " + this.name + " couldn't pay even after selling all properties. They lose!");
	            declareBankrupt();
	        }
	    }
	}
	
	private void sellProperty(PropertyBlock property, Entity buyer) {
	    int sellPrice;

	    if (buyer instanceof Enemy || buyer instanceof Player) {
	        sellPrice = (int) (property.getPrice() * (1 - (GameConfig.SELL_PROPERTY_ENEMY_DISCOUNT_PERCENTAGE / 100.0)));
	    } 
	    else {
	        sellPrice = (int) (property.getPrice() * (1 - (GameConfig.SELL_PROPERTY_BANK_DISCOUNT_PERCENTAGE / 100.0)));
	    }

	    this.money += sellPrice;
	    removeProperty(property);

	    if (buyer instanceof Enemy || buyer instanceof Player) {
	        property.setOwner(buyer);
	    }

	    updateTotalAssets();

	    if (buyer instanceof Enemy || buyer instanceof Player) {
	        buyer.addProperty(property);
	        buyer.setMoney(buyer.getMoney() - sellPrice);
	        buyer.updateTotalAssets();
	    }

	    System.out.print(" " + this.name + " sold " + property.getName() + " for $" + sellPrice + " ");
	    if (buyer instanceof Enemy || buyer instanceof Player) {
	        System.out.println(" to " + buyer.getName() + " at a discount of " + GameConfig.SELL_PROPERTY_ENEMY_DISCOUNT_PERCENTAGE + "%!");
	    } 
	    else {
	        System.out.println(" to the Bank at a discount of " + GameConfig.SELL_PROPERTY_BANK_DISCOUNT_PERCENTAGE + "%!");
	    }
	    TextUtil.pressEnter();
	}
	
	public void move(GenericBlock origin, GenericBlock destination) {
        if (origin != null && destination != null) {
            origin.removePiece(this); 
            destination.addPiece(this);
            this.setBoardIndex(destination.getIndex());
        }
    }
	
	public abstract Entity getEnemy();
	
	public abstract PropertyBlock chooseProperty();

	public String getColor() {
		return color;
	}

	protected void setColor(String color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	
	public int getTotalAssest() {
		return totalAssest;
	}

	public void setTotalAssest(int totalAssest) {
		this.totalAssest = totalAssest;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getPiece() {
		return piece;
	}

	public void setPiece(String piece) {
		this.piece = piece;
	}

	public int getBoardIndex() {
		return boardIndex;
	}

	public void setBoardIndex(int boardIndex) {
		this.boardIndex = boardIndex;
	}

	public List<PropertyBlock> getOwnedProperties() {
		return ownedProperties;
	}

	public void setOwnedProperties(List<PropertyBlock> ownedProperties) {
		this.ownedProperties = ownedProperties;
	}

	public boolean isInJail() {
		return isInJail;
	}

	public void setInJail(boolean isInJail) {
		this.isInJail = isInJail;
	}

	public boolean isBankrupt() {
		return isBankrupt;
	}
	
	public void declareBankrupt() {
		this.isBankrupt = true;
	}

	public BaseCharacter getEquippedCharacter() {
		return equippedCharacter;
	}

	public BaseInventory getInventory() {
		return inventory;
	}

	public void setEquippedCharacter(BaseCharacter equippedCharacter) {
		this.equippedCharacter = equippedCharacter;
	}

	public boolean isFrozen() {
		return isFrozen;
	}

	public void setFrozen(boolean isFrozen) {
		this.isFrozen = isFrozen;
	}
}
