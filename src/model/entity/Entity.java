package model.entity;

import java.util.ArrayList;
import java.util.List;

import model.block.PropertyBlock;
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
	
	public Entity(String name, int money) {
		super();
		this.name = name;
		this.money = money;
		this.ownedProperties = new ArrayList<PropertyBlock>();
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
	
	public abstract void move();

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
}
