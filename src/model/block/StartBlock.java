package model.block;

import model.entity.Entity;

public class StartBlock extends GenericBlock{
	private int income;

	public StartBlock(String name, String desc) {
		super(name, desc);
		// TODO Auto-generated constructor stub
	}

	@Override
	void onLand() {
		for(Entity piece : getPiecesOnBlock()){
			increaseMoney(piece, income);
		}
	}

}
