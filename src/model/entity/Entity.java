package model.entity;

import model.Position;

public abstract class Entity {
	private String name;
	private int money;
	private int totalAssest;
	private int rank;

	private String piece;
	private String color;
	
	private int boardIndex;
	
	public Entity(String name, int money) {
		super();
		this.name = name;
		this.money = money;
	}
	
	protected String getColor() {
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
}
