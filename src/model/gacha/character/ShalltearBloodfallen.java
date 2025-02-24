package model.gacha.character;

import config.CharacterConfig;
import config.ColorConfig;
import model.entity.Entity;
import utility.Random;

public class ShalltearBloodfallen extends BaseCharacter implements CharacterSkills, Random {

	public ShalltearBloodfallen() {
        setName(CharacterConfig.SHALLTEAR_NAME);
        setTitle(CharacterConfig.SHALLTEAR_TITLE);
        setSkillName(CharacterConfig.SHALLTEAR_SKILL_NAME);
        setSkillDesc(CharacterConfig.SHALLTEAR_SKILL_DESC);
        setNameColor(ColorConfig.LIGHT_RED);
        setId(CharacterConfig.SHALLTEAR_ID);
        setBaseSkillChance(CharacterConfig.SHALLTEAR_BASE_SKILL_CHANCE);
        setCurrentLevel(1);
    }

	public void useSkill(Entity entity, Entity opponent) {
        super.useSkill(entity, getBaseSkillChance());
        
        bloodTribute(entity, opponent);
    }
	
    @Override
    public void bloodTribute(Entity entity, Entity opponent) {
        int chance = getBaseSkillChance() + (getCurrentLevel() - 1);
        int tribute = (int) (opponent.getMoney() * 0.5);

        if (rand.nextInt(100) < chance) {
            if (opponent.getMoney() >= tribute) {
                opponent.setMoney(opponent.getMoney() - tribute);
                entity.setMoney(entity.getMoney() + tribute);
                String name = getNameColor() + getName() + ColorConfig.RESET;
                System.out.println(" " + name + " used Blood Tribute and stole $" + tribute + " from " + opponent.getName() + "!");
            } else {
                System.out.println(" " + opponent.getName() + " doesn't have enough money for Blood Tribute!");
                opponent.declareBankrupt();
            }
        } else {
            System.out.println(" " + getName() + "'s Blood Tribute failed.");
        }
    }

}
