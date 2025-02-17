package model.block;

import model.entity.Entity;

public class CardBlock extends GenericBlock{

	public CardBlock(String name, String desc) {
		super(name, desc);
		setType("Card");
	}

	@Override
	public void onLand(Entity piece) {
		// TODO Auto-generated method stub
		
	}

}
