package model.block;

import config.GameConfig;
import manager.GameManager;
import model.entity.Enemy;
import model.entity.Entity;

public class StartBlock extends GenericBlock{
	private int income;

	public StartBlock(String name, String desc) {
		super(name, desc);
		this.income = GameConfig.BLOCK_START_BASE_INCOME;
		initializePieces();
		setType("Start");
	}

	@Override
	public void onLand(Entity piece) {
		increaseMoney(piece, income);
	}
	
	private void initializePieces() {
		GameManager.getInstance().getPlayer().setBoardIndex(0);
        getPiecesOnBlock().add(GameManager.getInstance().getPlayer());
        getPiecesOnBlock().add(GameManager.getInstance().getEnemy());
    }
}
