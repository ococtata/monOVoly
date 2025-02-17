package model.block;

import config.BoardConfig;
import config.ColorConfig;
import model.entity.Entity;

public class PropertyBlock extends GenericBlock{
	private int price;
	private int constructPrice;
	private int toll;
	private Entity owner;
	private int buildingLevel;
	private int baseUpgradeCost = BoardConfig.BLOCK_PROPERTY_BASE_UPGRADE_COST;
	private boolean hasLandmark;
	private String landmarkName;
	private String landmarkDesc;
	private boolean isFestival;

	public PropertyBlock(String name, String desc, String landmarkName, String landmarkDesc) {
		super(name, desc);
		setType("Property");
		this.hasLandmark = false;
		this.isFestival = false;
		this.constructPrice = BoardConfig.BLOCK_PROPERTY_BASE_CONSTRUCTION_PRICE;
	}

	@Override
	public void onLand(Entity piece) {
		// TODO Auto-generated method stub
	}
	
	public void buy(Entity piece) {
		if(piece.getMoney() >= price) {
			deductMoney(piece, price);
			setOwner(piece);	
		}
	}
	
	public void overtake(Entity piece) {
		int overtakePrice = price * 2;
		if(piece.getMoney() >= overtakePrice) {
			deductMoney(piece, overtakePrice);
			setOwner(piece);
		}
	}
	
	public void construct(Entity piece) {
		if(piece.getMoney() >= constructPrice) {
			deductMoney(piece, constructPrice);
			buildingLevel++;
			constructPrice += (buildingLevel * baseUpgradeCost);
		}
	}

	public Entity getOwner() {
		return owner;
	}

	private void setOwner(Entity owner) {
		this.owner = owner;
	}

	public int getConstructPrice() {
		return constructPrice;
	}

	public int getToll() {
		return toll;
	}

	public int getBuildingLevel() {
		return buildingLevel;
	}

	public boolean isHasLandmark() {
		return hasLandmark;
	}

	public int getPrice() {
		return price;
	}

	public String getLandmarkName() {
		return landmarkName;
	}

	public void setLandmarkName(String landmarkName) {
		this.landmarkName = landmarkName;
	}

	public String getLandmarkDesc() {
		return landmarkDesc;
	}

	public void setLandmarkDesc(String landmarkDesc) {
		this.landmarkDesc = landmarkDesc;
	}

	public boolean isFestival() {
		return isFestival;
	}

	public void setFestival(boolean isFestival) {
		this.isFestival = isFestival;
	}

	public void setHasLandmark(boolean hasLandmark) {
		this.hasLandmark = hasLandmark;
	}
}
