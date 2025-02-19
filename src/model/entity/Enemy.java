package model.entity;

import config.BoardConfig;
import config.ColorConfig;
import manager.GameManager;
import model.Position;
import model.block.PropertyBlock;
import model.entity.inventory.EnemyInventory;

public class Enemy extends Entity {

	public Enemy(String name, int money) {
		super(name, money);
		setColor(ColorConfig.LIGHT_RED);
		setPiece(getColor() + BoardConfig.ENEMY_PIECE + ColorConfig.RESET);
		setName("Enemy");
		setInventory(new EnemyInventory());
	}

	@Override
	public PropertyBlock chooseProperty() {
		PropertyBlock chosen = null;
		int highestPrice = -1;
		
        for (PropertyBlock property : getOwnedProperties()) {
            if (property.getPrice() > highestPrice) {
                highestPrice = property.getPrice();
                chosen = property;
            }
        }
        return chosen;
	}

	@Override
	public Entity getEnemy() {
		Player player = GameManager.getInstance().getPlayer();
		return player;
	}
}
