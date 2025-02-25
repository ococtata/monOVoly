package model.gacha.character;

import config.BoardConfig;
import config.CharacterConfig;
import config.ColorConfig;
import config.GameConfig;
import model.block.PropertyBlock;
import model.entity.Entity;
import utility.Random;
import utility.TextUtil;

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
    	System.out.println();
    	TextUtil.printHorizontalBorder(
				BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));
        super.useSkill(entity, getBaseSkillChance());
        
        createFortress(entity, property);
    }

    public void createFortress(Entity entity, PropertyBlock property) {
        int chance = getBaseSkillChance() + (getCurrentLevel() - 1);

        if (rand.nextInt(100) < chance) {
        	System.out.println(" " + getNameColor() + getName() + ColorConfig.RESET + " used Create Fortress to acquire and fully upgrade " + property.getName() + "!");
        	System.out.println();
        	property.setOwner(entity);
        	entity.addProperty(property);
        	while (property.getBuildingLevel() < GameConfig.PROPERTY_MAX_BUILDING_LEVEL) {
                property.constructFree(entity);
            }
        	System.out.println();
            property.buildLandmarkFree(entity);
            entity.updateTotalAssets();
            
        } 
        else {
        	System.out.println(" " + getNameColor() + getName() + ColorConfig.RESET + "'s Create Fortress failed.");
        	System.out.println();
        	
        	TextUtil.pressEnter();
        	TextUtil.printHorizontalBorder(
    				BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));
            if (entity.getMoney() >= property.getPrice()) {
                entity.pay(null, property.getPrice());
                property.setOwner(entity);
                entity.addProperty(property);
                entity.updateTotalAssets();
                System.out.println(" " + getNameColor() + getName() + ColorConfig.RESET + " bought " + property.getName() + " for $" + property.getPrice());
            } 
            else {
                System.out.println(" " + getNameColor() + getName() + ColorConfig.RESET + " doesn't have enough money to buy " + property.getName());
            }
        }
        System.out.println();
    }
}
