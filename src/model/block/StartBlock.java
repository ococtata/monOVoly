package model.block;

import config.BoardConfig;
import model.entity.Entity;

public class StartBlock extends GenericBlock{
	private int income;

	public StartBlock(String name, String desc) {
		super(name, desc);
		this.income = BoardConfig.BLOCK_START_BASE_INCOME;
		// TODO Auto-generated constructor stub
	}

	@Override
	void onLand(Entity piece) {
		increaseMoney(piece, income);
	}

}
