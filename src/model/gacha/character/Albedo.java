package model.gacha.character;

import config.BoardConfig;
import config.CharacterConfig;
import config.ColorConfig;
import manager.MaterialLoaderManager;
import model.entity.Entity;
import utility.Random;
import utility.TextUtil;

public class Albedo extends BaseCharacter implements Random {

	public Albedo() {
        setName(CharacterConfig.ALBEDO_NAME);
        setTitle(CharacterConfig.ALBEDO_TITLE);
        setSkillName(CharacterConfig.ALBEDO_SKILL_NAME);
        setSkillDesc(CharacterConfig.ALBEDO_SKILL_DESC);
        setNameColor(ColorConfig.WHITE);
        setId(CharacterConfig.ALBEDO_ID);
    }
	
    public int useSkill(Entity entity, int amount) {
    	super.useSkill(entity);
        
        return absoluteDefense(amount);
    }
    
    @Override
    public int absoluteDefense(int rent) {
    	String name = getNameColor() + getName() + ColorConfig.RESET;
        if (rand.nextInt(100) < CharacterConfig.ALBEDO_IGNORE_CHANCE_PERCENTAGE) {
            if (rand.nextInt(100) < CharacterConfig.ALBEDO_REDUCE_CHANCE_PERCENTAGE) { 
                System.out.println(" " + name + "'s skill prevented the rent payment!");
                return 0;
            } 
            else {
                int reducedRent = rent / 2;
                System.out.println(" " + name + "'s skill reduced the rent to $" + reducedRent + "!");
                return reducedRent;
            }
        }
        return rent;
    }

}
