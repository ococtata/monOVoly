package manager;

import java.util.ArrayList;
import java.util.List;

import config.DataConfig;
import config.GachaConfig.Rarity;
import model.gacha.material.CharacterMaterial;
import utility.FileUtil;

public class MaterialLoaderManager {
	private static MaterialLoaderManager instance;
	private static List<CharacterMaterial> materials = new ArrayList<CharacterMaterial>();
	 
	private MaterialLoaderManager() {
		loadMaterials();
	}
	
	public static MaterialLoaderManager getInstance() {
		if(instance == null) instance = new MaterialLoaderManager();
		return instance;
	}
	
	public static void loadMaterials() {
        materials.clear();
        List<String> lines = FileUtil.readFile(DataConfig.FILE_DATA_GACHA_MATERIAL);

        for (String line : lines) {
            String[] parts = line.split("#");
            if (parts.length == 4) {
                String id = parts[0];
                Rarity rarity = Rarity.valueOf(parts[1].toUpperCase());
                String name = parts[2];
                String forCard = parts[3];
                materials.add(new CharacterMaterial(id, rarity, name, forCard));
            }
        }

	}
	
	public List<CharacterMaterial> getAllMaterials() {
        return new ArrayList<>(materials);
    }

	public CharacterMaterial getMaterialById(String materialId) {
        for (CharacterMaterial material : materials) {
            if (material.getId().equals(materialId)) {
                return material;
            }
        }
        return null;
    }
}
