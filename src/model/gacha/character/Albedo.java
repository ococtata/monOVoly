package model.gacha.character;

import config.CharacterConfig;
import config.ColorConfig;
import model.entity.Entity;
import utility.Random;

public class Albedo extends BaseCharacter implements Random {

	public Albedo() {
        setName(CharacterConfig.ALBEDO_NAME);
        setTitle(CharacterConfig.ALBEDO_TITLE);
        setSkillName(CharacterConfig.ALBEDO_SKILL_NAME);
        setSkillDesc(CharacterConfig.ALBEDO_SKILL_DESC);
        setNameColor(ColorConfig.WHITE);
        setId(CharacterConfig.ALBEDO_ID);
        setBaseSkillChance(CharacterConfig.ALBEDO_BASE_SKILL_CHANCE);
        setCurrentLevel(1);
    }
	
    public int useSkill(int amount, Entity entity) {
    	super.useSkill(entity, getBaseSkillChance());
        
    	return armorOfMalice(amount);
    }
    
    @Override
    public int armorOfMalice(int rent) {
        int chance = getBaseSkillChance() + (getCurrentLevel() - 1);
        String name = getNameColor() + getName() + ColorConfig.RESET;

        if (rand.nextInt(100) < chance) {
            System.out.println(" " + name + "'s Armor of Malice prevented the rent payment!");
            return 0;
        }
        return rent;
    }
}
