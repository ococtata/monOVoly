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
	}

	@Override
	void onLand(Entity piece) {
		increaseMoney(piece, income);
	}
	
	private void initializePieces() {
        getPiecesOnBlock().add(GameManager.getInstance().getPlayer());
        getPiecesOnBlock().add(new Enemy("Enemy", 0, new Position(0, 0)));
    }
}
