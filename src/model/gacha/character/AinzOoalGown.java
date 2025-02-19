package model.gacha.character;

import config.CharacterConfig;
import config.GameConfig;
import model.entity.Entity;

public class AinzOoalGown extends BaseCharacter {

    private boolean hasResurrected = false; 
    
    public AinzOoalGown() {
        setName(CharacterConfig.AINZ_NAME);
        setTitle(CharacterConfig.AINZ_TITLE);
        setSkillName(CharacterConfig.AINZ_SKILL_NAME);
        setSkillDesc(CharacterConfig.AINZ_SKILL_DESC);
    }
	
    public void useSkill(Entity entity) {
		super.useSkill(entity);
        
        darkResurrection(entity);
    }

	@Override
    public void darkResurrection(Entity entity) {
        if (!hasResurrected) {
            int originalMoney = GameConfig.STARTING_MONEY;
            int reviveAmount = (originalMoney * CharacterConfig.AINZ_REVIVE_MONEY_PERCENTAGE) / 100;
            entity.setMoney(reviveAmount);
            entity.updateTotalAssets();
            hasResurrected = true;
            System.out.println(" " + entity.getName() + " revived with $" + reviveAmount + "!");
        }
    }
    
    public boolean hasResurrected() {
        return hasResurrected;
    }
}
