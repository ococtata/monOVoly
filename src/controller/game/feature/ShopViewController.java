package controller.game.feature;

import java.util.List;

import manager.GameManager;
import model.entity.inventory.PlayerInventory;
import model.gacha.character.BaseCharacter;
import view.game.feature.ShopView;

public class ShopViewController {
	private ShopView shopView;
	
	private List<BaseCharacter> characters;
	
	public ShopViewController(ShopView shopView) {
		updateCharacters();
		this.shopView = shopView;
	}
	
	public void updateCharacters() {
		PlayerInventory inventory = (PlayerInventory) GameManager.getInstance().getPlayer().getInventory();
		this.characters = inventory.getCharacterList();
	}

	public List<BaseCharacter> getCharacters() {
		return characters;
	}
}
