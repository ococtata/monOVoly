package model.block;

import model.entity.Entity;

public class WorldTravelBlock extends GenericBlock {

	public WorldTravelBlock(String name, String desc) {
		super(name, desc);
		setType("World Travel");
	}

	@Override
	public void onLand(Entity piece) {
		// TODO Auto-generated method stub
		
	}

}
