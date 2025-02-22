package model.gacha.character;

import config.CharacterConfig;
import config.ColorConfig;
import model.entity.Entity;
import utility.TextUtil;

public class Cocytus extends BaseCharacter implements CharacterSkills {

	public Cocytus() {
		setName(CharacterConfig.COCYTUS_NAME);
        setTitle(CharacterConfig.COCYTUS_TITLE);
        setSkillName(CharacterConfig.COCYTUS_SKILL_NAME);
        setSkillDesc(CharacterConfig.COCYTUS_SKILL_DESC);
        setNameColor(ColorConfig.LIGHT_BLUE);
	}
	
	public boolean useSkill(Entity entity, Entity opponent) {
		this.useSkill(entity);
		
		return glacialExecution(opponent);
	}
	
	@Override
    public boolean glacialExecution(Entity opponent) {
        opponent.setFrozen(true);
        System.out.println(" " + getName() + " used Glacial Execution! " + opponent.getName() + "'s next turn is skipped.");
        return true;
    }
}
