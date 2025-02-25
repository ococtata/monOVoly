package model.gacha.character;

import config.CharacterConfig;
import config.ColorConfig;
import manager.GameManager;
import model.block.GenericBlock;
import model.block.GoToJailBlock;
import model.entity.Entity;
import model.game.GameBoard;
import utility.Random;
import utility.TextUtil;

public class Cocytus extends BaseCharacter implements CharacterSkills, Random {

	public Cocytus() {
		setName(CharacterConfig.COCYTUS_NAME);
        setTitle(CharacterConfig.COCYTUS_TITLE);
        setSkillName(CharacterConfig.COCYTUS_SKILL_NAME);
        setSkillDesc(CharacterConfig.COCYTUS_SKILL_DESC);
        setNameColor(ColorConfig.LIGHT_BLUE);
        setId(CharacterConfig.COCYTUS_ID);
        setBaseSkillChance(CharacterConfig.COCYTUS_BASE_SKILL_CHANCE);
        setCurrentLevel(1);
	}
	
	public void useSkill(Entity entity, Entity opponent) {
		this.useSkill(entity, getBaseSkillChance());
		
		glacialImprisonment(entity, opponent);
	}
	
	@Override
    public void glacialImprisonment(Entity entity, Entity opponent) {
        int chance = getBaseSkillChance() + (getCurrentLevel() - 1);
        String name = getNameColor() + getName() + ColorConfig.RESET;
        GameManager gameManager = GameManager.getInstance();
        GameBoard gameBoard = gameManager.getGameBoard();
        
        if (rand.nextInt(100) < chance) {
        	if(opponent.isInJail()) {
        		System.out.println(" " + opponent.getName() + " is already in jail!");
        		return;
        	}
        	
        	GenericBlock currentBlock = null;
            for (GenericBlock block : gameBoard.getBlockList()) {
                if (block.getPiecesOnBlock().contains(opponent)) {
                    currentBlock = block;
                    break;
                }
            }

            GoToJailBlock jailBlock = null;
            for (GenericBlock block : gameBoard.getBlockList()) {
                if (block instanceof GoToJailBlock) {
                    jailBlock = (GoToJailBlock) block;
                    break;
                }
            }
            
            System.out.println(" " + name + " used Glacial Imprisonment! " + opponent.getName() + " was sent to jail.");
            currentBlock.removePiece(opponent);
            opponent.setBoardIndex(jailBlock.getIndex());
            jailBlock.addPiece(opponent);
            jailBlock.onLand(opponent);
            
        } 
        else {
            System.out.println(" " + name + "'s Glacial Imprisonment failed.");
        }
        
        System.out.println();
        TextUtil.pressEnter();
    }
	
	
}
