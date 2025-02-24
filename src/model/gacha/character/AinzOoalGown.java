package model.gacha.character;

import config.CharacterConfig;
import config.ColorConfig;
import config.GameConfig;
import model.block.PropertyBlock;
import model.entity.Entity;
import utility.Random;

public class AinzOoalGown extends BaseCharacter implements Random{
    
    public AinzOoalGown() {
        setName(CharacterConfig.AINZ_NAME);
        setTitle(CharacterConfig.AINZ_TITLE);
        setSkillName(CharacterConfig.AINZ_SKILL_NAME);
        setSkillDesc(CharacterConfig.AINZ_SKILL_DESC);
        setNameColor(ColorConfig.PURPLE);
        setId(CharacterConfig.AINZ_ID);
        setCurrentLevel(1);
        setBaseSkillChance(CharacterConfig.AINZ_BASE_SKILL_CHANCE);
    }
	
    public void useSkill(Entity entity, PropertyBlock property) {
        super.useSkill(entity, getBaseSkillChance());
        createFortress(entity, property);
    }

    public void createFortress(Entity entity, PropertyBlock property) {
        int chance = getBaseSkillChance() + (getCurrentLevel() - 1);

        if (rand.nextInt(100) < chance) {
        	while (property.getBuildingLevel() < GameConfig.PROPERTY_MAX_BUILDING_LEVEL) {
                property.constructFree(entity);
            }
            property.buildLandmarkFree(entity);
            System.out.println(" " + getName() + " used Create Fortress to fully upgrade " + property.getName() + ".");
        } else {
            property.construct(entity);
            System.out.println(" " + getName() + " constructed a building.");
        }
    }
}
