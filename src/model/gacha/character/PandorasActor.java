package model.gacha.character;

import config.CharacterConfig;
import config.ColorConfig;
import model.entity.Entity;
import utility.Random;

public class PandorasActor extends BaseCharacter implements CharacterSkills, Random {
	private int freeRentCount = 3;
	
	public PandorasActor() {
		setName(CharacterConfig.PANDORAS_ACTOR_NAME);
        setTitle(CharacterConfig.PANDORAS_ACTOR_TITLE);
        setSkillName(CharacterConfig.PANDORAS_ACTOR_SKILL_NAME);
        setSkillDesc(CharacterConfig.PANDORAS_ACTOR_SKILL_DESC);
        setNameColor(ColorConfig.ORANGE);
        setId(CharacterConfig.PANDORAS_ACTOR_ID);
        setBaseSkillChance(CharacterConfig.PANDORAS_BASE_SKILL_CHANCE);
        setCurrentLevel(1);
	}
	
    public int useSkill(int amount, Entity entity) {
        super.useSkill(entity, getBaseSkillChance());
        
        return falseFortune(amount);
    }
	
	@Override
	public int falseFortune(int amount) {
		String name = getNameColor() + getName() + ColorConfig.RESET;
		if (freeRentCount > 0) {
            freeRentCount--;
            System.out.println(" " + name + "'s False Fortune prevented the rent payment!");

            int chance = getBaseSkillChance() + (getCurrentLevel() - 1);

            if (rand.nextInt(100) < chance) {
                freeRentCount++;
                System.out.println(" False Fortune counter not reduced!");
            }
            
            System.out.println(" False Fortune counter: " + getFreeRentCount());
            return 0;
        } 
		else {
            System.out.println(" False Fortune has been exhausted!");

            return amount;
        }
	}
	
	public int getFreeRentCount() {
        return freeRentCount;
    }

    public void setFreeRentCount(int freeRentCount) {
        this.freeRentCount = freeRentCount;
    }
}
