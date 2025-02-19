package model.gacha.material;

import config.GachaConfig;

public class CharacterMaterial {
	private GachaConfig.Rarity rarity; 
   	private String name;
   	private String forCard;
   	
   	public CharacterMaterial(GachaConfig.Rarity rarity, String name, String forCard) {
        this.rarity = rarity;
        this.name = name;
        this.forCard = forCard;
    }

    public GachaConfig.Rarity getRarity() {
        return rarity;
    }

    public String getName() {
        return name;
    }

    public String getForCard() {
        return forCard;
    }
}
