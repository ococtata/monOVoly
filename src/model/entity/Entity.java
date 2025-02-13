package model.entity;

import model.Position;

public abstract class Entity {
	private String name;
	private int money;
	private int totalAssest;
	private int rank;
	private Position position;
	private String piece;
	
	public Entity(String name, int money, Position position) {
		super();
		this.name = name;
		this.money = money;
		this.position = position;
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
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
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
}
