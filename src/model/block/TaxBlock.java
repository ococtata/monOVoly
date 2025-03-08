package model.block;

import config.BoardConfig;
import config.GameConfig;
import controller.game.monovoly.IMonovolyGameGUI;
import model.entity.Entity;
import utility.TextUtil;

public class TaxBlock extends GenericBlock implements IMonovolyGameGUI{
	private double taxPercentage;

	public TaxBlock(String name, String desc, int index) {
		super(name, desc, index);
		this.taxPercentage = GameConfig.BLOCK_TAX_BASE_DEDUCT_PERCENTAGE;
		setType("Tax");
	}

	@Override
    public void onLand(Entity piece) {
        int tax = (int) (piece.getMoney() * taxPercentage);
        piece.pay(null, tax);
        System.out.printf(" %s paid $%d in taxes.\n", piece.getName(), tax);
        showStats();
    }

}
