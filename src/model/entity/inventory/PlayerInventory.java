package model.entity.inventory;

import java.util.ArrayList;
import java.util.List;

import manager.MaterialLoaderManager;
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
	
	public void addMaterial(String materialId, int amount) {
        CharacterMaterial materialToAdd = MaterialLoaderManager.getInstance().getMaterialById(materialId);

        for (CharacterMaterial existingMaterial : materialList) {
            if (existingMaterial.getId().equals(materialToAdd.getId())) {
                existingMaterial.increaseAmount();
                return;
            }
        }

        CharacterMaterial newMaterial = new CharacterMaterial(
            materialToAdd.getId(),
            materialToAdd.getRarity(),
            materialToAdd.getName(),
            materialToAdd.getForCard()
        );
        
        newMaterial.setAmount(amount);
        this.materialList.add(newMaterial);
    }

    public CharacterMaterial findMaterialById(String materialId) {
        for (CharacterMaterial material : materialList) {
            if (material.getId().equals(materialId)) {
                return material;
            }
        }
        return null;
    }

	public void decreaseMaterial(CharacterMaterial mat, int amount) {
		for(CharacterMaterial material : materialList) {
			if(mat.getId().equals(material.getId())) {
				material.decrease(amount);
				
				if(material.getAmount() == 0) {
					materialList.remove(material);
				}
				break;
			}
		}
		
	}
    
    
}
