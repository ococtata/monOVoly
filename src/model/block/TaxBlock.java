package model.block;

import config.BoardConfig;
import model.entity.Entity;

public class TaxBlock extends GenericBlock{
	private int taxPercentage;

	public TaxBlock(String name, String desc) {
		super(name, desc);
		this.taxPercentage = BoardConfig.BLOCK_TAX_BASE_DEDUCT_PERCENTAGE;
		// TODO Auto-generated constructor stub
	}

	@Override
	void onLand(Entity piece) {
		int tax = (int) piece.getMoney() / taxPercentage;
		deductMoney(piece, tax);
	}

}
