package model.entity.inventory;

import model.gacha.GetAllCharacters;

public class EnemyInventory extends BaseInventory implements GetAllCharacters {

	public EnemyInventory() {
		super();
		setCharacterList(getAllCharacters());
	}

}
