package model.gacha.material;

import config.CharacterConfig;
import config.GachaConfig;
import config.GachaConfig.Rarity;
import model.gacha.character.AinzOoalGown;
import model.gacha.character.Albedo;
import model.gacha.character.BaseCharacter;
import model.gacha.character.Cocytus;
import model.gacha.character.Demiurge;
import model.gacha.character.ShalltearBloodfallen;

public class CharacterMaterial {
	private GachaConfig.Rarity rarity; 
   	private String name;
   	private String forCard;
   	private int amount;
   	
   	public int getAmount() {
		return amount;
	}

	private BaseCharacter character;
   	
	public CharacterMaterial(GachaConfig.Rarity rarity, String name, String forCard) {
        this.rarity = rarity;
        this.name = name;
        this.forCard = forCard;
        this.amount = 1;
        
        switch (forCard) {
			case CharacterConfig.AINZ_NAME:
				this.character = new AinzOoalGown();
				break;
			case CharacterConfig.SHALLTEAR_NAME:
				this.character = new ShalltearBloodfallen();
				break;
			case CharacterConfig.ALBEDO_NAME:
				this.character = new Albedo();
				break;
			case CharacterConfig.COCYTUS_NAME:
				this.character = new Cocytus();
				break;
			case CharacterConfig.DEMIURGE_NAME:
				this.character = new Demiurge();
				break;
			default:
				break;
		}
    }
	
	public void increaseAmount() {
		this.amount++;
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
    
    public BaseCharacter getCharacter() {
		return character;
	}

	public void setCharacter(BaseCharacter character) {
		this.character = character;
	}
}
