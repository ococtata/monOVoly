package model.entity;

import config.BoardConfig;
import config.ColorConfig;
import model.Position;

public class Enemy extends Entity {

	public Enemy(String name, int money) {
		super(name, money);
		setColor(ColorConfig.LIGHT_RED);
		setPiece(getColor() + BoardConfig.ENEMY_PIECE + ColorConfig.RESET);
		setName("Enemy");
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}
}
