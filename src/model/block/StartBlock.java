package model.block;

import config.BoardConfig;
import manager.GameManager;
import model.Position;
import model.entity.Enemy;
import model.entity.Entity;

public class StartBlock extends GenericBlock{
	private int income;

	public StartBlock(String name, String desc) {
		super(name, desc);
		this.income = BoardConfig.BLOCK_START_BASE_INCOME;
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
        Enemy enemy = new Enemy("Enemy", 0);
        GameManager.getInstance().setEnemy(enemy);
        getPiecesOnBlock().add(GameManager.getInstance().getEnemy());
    }
}
