package controller.game.feature.inventory;

import java.util.List;

import manager.GameManager;
import model.entity.inventory.PlayerInventory;
import model.gacha.character.BaseCharacter;
import model.gacha.material.CharacterMaterial;
import view.game.feature.inventory.CharacterListView;

public class CharacterListViewController {
	private CharacterListView characterListView;
	
	private List<BaseCharacter> characters;
	
	public CharacterListViewController(CharacterListView characterListView) {
		updateCharacters();
		this.characterListView = characterListView;
	}
	
	public void updateCharacters() {
		PlayerInventory inventory = (PlayerInventory) GameManager.getInstance().getPlayer().getInventory();
		this.characters = inventory.getCharacterList();
	}

	public List<BaseCharacter> getCharacters() {
		return characters;
	}
}
