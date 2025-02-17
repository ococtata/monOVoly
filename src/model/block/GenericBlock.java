package model.block;

import java.util.ArrayList;
import java.util.List;

import model.entity.Entity;

public abstract class GenericBlock {
	private int number; 
    private String name; 
    private String desc;
    private List<Entity> piecesOnBlock;
    private String blockColor;
	
    public GenericBlock(String name, String desc) {
		this.name = name;
		this.desc = desc;
		this.piecesOnBlock = new ArrayList<Entity>();
	}
    
    abstract void onLand(Entity piece);

	public List<Entity> getPiecesOnBlock() {
		return piecesOnBlock;
	}

	public void addPiece(Entity piece) {
		piecesOnBlock.add(piece);
	}
	
	public void removePiece(Entity piece) {
		piecesOnBlock.remove(piece);
	}

	public String getDesc() {
		return desc;
	}

	public String getName() {
		return name;
	}

	public String getBlockColor() {
		return blockColor;
	}

	public void setBlockColor(String blockColor) {
		this.blockColor = blockColor;
	}

	public int getNumber() {
		return number;
	}
	
	public void deductMoney(Entity piece, int amount) {
		int money = piece.getMoney() - amount;
		if(money < 0) {
			money = 0;
		}
		
		piece.setMoney(money);
	}
	
	public void increaseMoney(Entity piece, int amount) {
		int money = piece.getMoney() + amount;
		piece.setMoney(money);
	}

	protected void setPiecesOnBlock(List<Entity> piecesOnBlock) {
		this.piecesOnBlock = piecesOnBlock;
	}
}
