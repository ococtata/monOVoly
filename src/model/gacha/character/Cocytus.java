package model.gacha.character;

import config.CharacterConfig;
import model.entity.Entity;

public class Cocytus extends BaseCharacter implements CharacterSkills {

	public Cocytus() {
		setName(CharacterConfig.COCYTUS_NAME);
        setTitle(CharacterConfig.COCYTUS_TITLE);
        setSkillName(CharacterConfig.COCYTUS_SKILL_NAME);
        setSkillDesc(CharacterConfig.COCYTUS_SKILL_DESC);
	}
	
	public boolean useSkill(Entity entity, Entity opponent) {
		this.useSkill(entity);
		
		return glacialExecution(opponent);
	}
	
	@Override
    public boolean glacialExecution(Entity opponent) {
        System.out.println(" " + getName() + " used Glacial Execution! Opponent's next turn is skipped.");
        return true;
    }
}
