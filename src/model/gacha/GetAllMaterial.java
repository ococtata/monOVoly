package model.gacha;

import java.util.ArrayList;
import java.util.List;

import config.DataConfig;
import config.GachaConfig;
import model.gacha.material.CharacterMaterial;
import utility.FileUtil;

public interface GetAllMaterial {
	default List<CharacterMaterial> getAllMaterial() {
        List<CharacterMaterial> list = new ArrayList<>();
        List<String> materialStrings = FileUtil.readFile(DataConfig.FILE_DATA_GACHA_MATERIAL);

        if (materialStrings != null) {
            for (String materialString : materialStrings) {
                String[] parts = materialString.split("#");
                if (parts.length == 3) {
                    try {
                        CharacterMaterial material = createMaterialFromParts(parts);
                        if (material != null) {
                            list.add(material);
                        }
                    } catch (IllegalArgumentException e) {
                        System.err.println(" Error parsing material string: " + materialString + ". Error: " + e.getMessage());
                    }
                } else {
                    System.err.println(" Invalid material string format: " + materialString);
                }
            }
        }

        return list;
    }

    private CharacterMaterial createMaterialFromParts(String[] parts) {
        try {
            String rarityStr = parts[0].trim().toUpperCase();
            GachaConfig.Rarity rarity = GachaConfig.Rarity.valueOf(rarityStr);

            String name = parts[1].trim();
            String forCard = parts[2].trim();

            return new CharacterMaterial(rarity, name, forCard);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(" Invalid rarity: " + parts[0], e);
        }
    }
}
