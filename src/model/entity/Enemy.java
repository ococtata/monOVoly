package model.entity;

import config.BoardConfig;
import config.ColorConfig;
import model.Position;

public class Enemy extends Entity {

	public Enemy(String name, int money) {
		super(name, money);
		setPiece(ColorConfig.LIGHT_RED + BoardConfig.ENEMY_PIECE + ColorConfig.RESET);
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub

	}

}
