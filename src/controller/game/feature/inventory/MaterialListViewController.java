package controller.game.feature.inventory;

import java.util.List;

import manager.GameManager;
import model.entity.inventory.PlayerInventory;
import model.gacha.material.CharacterMaterial;
import view.game.feature.inventory.MaterialListView;

public class MaterialListViewController {
	private MaterialListView materialListView;
	
	private List<CharacterMaterial> materials;
	
	public MaterialListViewController(MaterialListView materialListView) {
		updateMaterials();
		this.materialListView = materialListView;
	}

	public void updateMaterials() {
        PlayerInventory inventory = (PlayerInventory) GameManager.getInstance().getPlayer().getInventory();
        this.materials = inventory.getMaterialList();
    }
	
	public List<CharacterMaterial> getMaterials() {
        return materials;
    }

    public void sortMaterials(int sortBy, boolean ascending) {
        int n = materials.size();
        
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                boolean swap = false;
                
                CharacterMaterial mat1 = materials.get(j);
                CharacterMaterial mat2 = materials.get(j + 1);

                switch (sortBy) {
                    case 1: 
                        swap = ascending ? mat1.getName().compareTo(mat2.getName()) > 0 
                        		: mat1.getName().compareTo(mat2.getName()) < 0;
                        break;
                    case 2: 
                        swap = ascending ? mat1.getRarity().compareTo(mat2.getRarity()) > 0 
                        		: mat1.getRarity().compareTo(mat2.getRarity()) < 0;
                        break;
                    case 3:
                        swap = mat1.getAmount() < mat2.getAmount();
                        break;
                    case 4: 
                        swap = ascending ? mat1.getForCard().compareTo(mat2.getForCard()) > 0 
                        		: mat1.getForCard().compareTo(mat2.getForCard()) < 0;
                        break;
                }

                if (swap) {
                    materials.set(j, mat2);
                    materials.set(j + 1, mat1);
                }
            }
        }
    }
}
