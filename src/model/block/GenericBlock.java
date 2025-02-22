package model.block;

import java.util.ArrayList;
import java.util.List;

import model.entity.Entity;

public abstract class GenericBlock {
	private int index; 
    private String name; 
    private String desc;
    private String type;

	private List<Entity> piecesOnBlock;
    private String blockColor;
	
    public GenericBlock(String name, String desc, int index) {
		this.name = name;
		this.desc = desc;
		this.piecesOnBlock = new ArrayList<Entity>();
		this.index = index;
	}
    
    public abstract void onLand(Entity piece);

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

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
	
	public void increaseMoney(Entity piece, int amount) {
		int money = piece.getMoney() + amount;
		piece.setMoney(money);
	}

	public void setPiecesOnBlock(List<Entity> piecesOnBlock) {
		this.piecesOnBlock = piecesOnBlock;
	}
	
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
