package model.block;

import model.entity.Entity;

public class JailBlock extends GenericBlock {

	public JailBlock(String name, String desc) {
		super(name, desc);
		setType("Jail");
	}

	@Override
	public void onLand(Entity piece) {
		// TODO Auto-generated method stub
		
	}
	
}
