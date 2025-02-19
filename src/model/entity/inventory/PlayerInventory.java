package model.entity.inventory;

import java.util.ArrayList;
import java.util.List;

import model.gacha.material.CharacterMaterial;

public class PlayerInventory extends BaseInventory {

	private List<CharacterMaterial> materialList;
	public PlayerInventory() {
		super();
		this.materialList = new ArrayList<CharacterMaterial>();
	}
	public List<CharacterMaterial> getMaterialList() {
		return materialList;
	}	
	
	public void addMaterial(CharacterMaterial material) {
		this.materialList.add(material);
	}
}
