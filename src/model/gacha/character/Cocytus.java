package model.gacha.character;

import config.CharacterConfig;
import config.ColorConfig;
import controller.game.monovoly.IMonovolyGameGUI;
import manager.GameManager;
import model.block.GenericBlock;
import model.block.GoToJailBlock;
import model.block.JailBlock;
import model.entity.Entity;
import model.game.GameBoard;
import utility.Random;
import utility.TextUtil;

public class Cocytus extends BaseCharacter implements CharacterSkills, Random, IMonovolyGameGUI {

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
		if(opponent.isInJail()) {
			System.out.println(" " + opponent.getName() + " is already in jail!\n");
			TextUtil.pressEnter();
			return;
		}
		
        int chance = getBaseSkillChance() + (getCurrentLevel() - 1);
        String name = getNameColor() + getName() + ColorConfig.RESET;
        GameManager gameManager = GameManager.getInstance();
        GameBoard gameBoard = gameManager.getGameBoard();
        
        if (rand.nextInt(100) < chance) {
                        
            JailBlock jailBlock = null;
            int jailIndex = -1;
            for (GenericBlock block : gameBoard.getBlockList()) {
                if (block instanceof JailBlock) {
                    jailBlock = (JailBlock) block;
                    jailIndex = jailBlock.getIndex();
                    break;
                }
            }

            if (jailIndex == -1) {
                System.out.println(" Jail block not found!");
                return;
            }

            System.out.println(" " + name + " used Glacial Imprisonment! " + opponent.getName() + " was sent to jail.");
            TextUtil.pressEnter();
            
            int stepsToJail = calculateStepsToJail(opponent.getBoardIndex(), jailIndex, gameBoard.getBlockList().size());
            opponent.setInJail(true);
            moveWithAnimation(opponent, stepsToJail + 1);

            
        } 
        else {
            System.out.println(" " + name + "'s Glacial Imprisonment failed.");
        }
        
        System.out.println();
        TextUtil.pressEnter();
    }
	
	private int calculateStepsToJail(int currentIndex, int jailIndex, int boardSize) {
        if (currentIndex <= jailIndex) {
            return jailIndex - currentIndex;
        } else {
            return (boardSize - currentIndex) + jailIndex;
        }
    }
}
