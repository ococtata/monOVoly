package model.gacha.material;

import config.CharacterConfig;
import config.GachaConfig;
import model.gacha.character.AinzOoalGown;
import model.gacha.character.Albedo;
import model.gacha.character.BaseCharacter;
import model.gacha.character.Cocytus;
import model.gacha.character.Demiurge;
import model.gacha.character.ShalltearBloodfallen;

public class CharacterMaterial {
private static int materialCounter = 0;
    
    private String id;
    private GachaConfig.Rarity rarity; 
    private String name;
    private String forCard;
    private int amount;
    private BaseCharacter character;

    public CharacterMaterial(String id, GachaConfig.Rarity rarity, String name, String forCard) {
        this.id = id;
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
                this.character = null;
                break;
        }
    }

    public void increaseAmount() {
        this.amount++;
    }

    public String getId() {
        return id;
    }

    public GachaConfig.Rarity getRarity() {
        return rarity;
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

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void decrease(int amount) {
		setAmount(getAmount() - amount); 	
	}
	
	
	
}
