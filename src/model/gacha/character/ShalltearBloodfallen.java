package model.gacha.character;

import config.CharacterConfig;
import config.ColorConfig;
import model.entity.Entity;

public class ShalltearBloodfallen extends BaseCharacter implements CharacterSkills {

	public ShalltearBloodfallen() {
        setName(CharacterConfig.SHALLTEAR_NAME);
        setTitle(CharacterConfig.SHALLTEAR_TITLE);
        setSkillName(CharacterConfig.SHALLTEAR_SKILL_NAME);
        setSkillDesc(CharacterConfig.SHALLTEAR_SKILL_DESC);
        setNameColor(ColorConfig.LIGHT_RED);
        setId(CharacterConfig.SHALLTEAR_ID);
    }

    public int useSkill(Entity entity, int toll) {
    	super.useSkill(entity);
        
        return bloodTribute(toll, entity.getEnemy());
    }
	
    @Override
	public int bloodTribute(int rent, Entity opponent) {
        int tribute = (int) (opponent.getMoney() * 0.1);
        if (opponent.getMoney() >= tribute) {
            opponent.setMoney(opponent.getMoney() - tribute);
            String name = getNameColor() + getName() + ColorConfig.RESET;
            System.out.println(" " + name + " used Blood Tribute and stole $" + tribute + " from " + opponent.getName() + "!");
            System.out.println();
            System.out.println(" The total toll is now $" + (rent + tribute));
            
            return rent + tribute; 
        } 
        else {
            System.out.println(" " + opponent.getName() + " doesn't have enough money for Blood Tribute!");
            opponent.declareBankrupt();
            return rent;
        }
    }

}
