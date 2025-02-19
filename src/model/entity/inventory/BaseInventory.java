package model.entity.inventory;

import java.util.ArrayList;
import java.util.List;

import model.gacha.character.BaseCharacter;

public abstract class BaseInventory {

	private List<BaseCharacter> characterList;
	
	public BaseInventory() {
		this.characterList = new ArrayList<BaseCharacter>();
	}

	public List<BaseCharacter> getCharacterList() {
		return characterList;
	}
	
	public void addCharacter(BaseCharacter character) {
		this.characterList.add(character);
	}

	protected void setCharacterList(List<BaseCharacter> characterList) {
		this.characterList = characterList;
	}
}


