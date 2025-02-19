package model.block;

import config.GameConfig;
import model.entity.Entity;
import utility.TextUtil;

public class TaxBlock extends GenericBlock{
	private int taxPercentage;

	public TaxBlock(String name, String desc) {
		super(name, desc);
		this.taxPercentage = GameConfig.BLOCK_TAX_BASE_DEDUCT_PERCENTAGE;
		setType("Tax");
	}

	@Override
	public void onLand(Entity piece) {
		int tax = (int) piece.getMoney() / taxPercentage;
		deductMoney(piece, tax);
		System.out.printf(" %s paid $%.2f in taxes.", piece.getName(), tax);
	}

}
