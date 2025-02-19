package model.block;

import config.GameConfig;
import model.entity.Entity;

public class TaxBlock extends GenericBlock{
	private int taxPercentage;

	public TaxBlock(String name, String desc, int index) {
		super(name, desc, index);
		this.taxPercentage = GameConfig.BLOCK_TAX_BASE_DEDUCT_PERCENTAGE;
		setType("Tax");
	}

	@Override
	public void onLand(Entity piece) {
		int tax = (int) piece.getMoney() / taxPercentage;
		piece.pay(piece.getEnemy(), tax);
		System.out.printf(" %s paid $%d in taxes.\n", piece.getName(), tax);
	}

}
