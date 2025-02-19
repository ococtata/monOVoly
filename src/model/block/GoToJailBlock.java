package model.block;

import model.entity.Entity;

public class GoToJailBlock extends GenericBlock {

	public GoToJailBlock(String name, String desc) {
		super(name, desc);
		setType("Go to Jail");
	}

	@Override
	public void onLand(Entity piece) {
		// TODO Auto-generated method stub
		
	}	
}
