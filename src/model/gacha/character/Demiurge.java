package model.gacha.character;

import config.BoardConfig;
import config.CharacterConfig;
import config.ColorConfig;
import controller.game.monovoly.IMonovolyGameGUI;
import manager.GameManager;
import model.block.GenericBlock;
import model.block.PropertyBlock;
import model.entity.Entity;
import model.game.GameBoard;
import utility.Random;
import utility.TextUtil;

public class Demiurge extends BaseCharacter implements CharacterSkills, Random, IMonovolyGameGUI {

	public Demiurge() {
        setName(CharacterConfig.DEMIURGE_NAME);
        setTitle(CharacterConfig.DEMIURGE_TITLE);
        setSkillName(CharacterConfig.DEMIURGE_SKILL_NAME);
        setSkillDesc(CharacterConfig.DEMIURGE_SKILL_DESC);
        setNameColor(ColorConfig.DEVIL_RED);
        setId(CharacterConfig.DEMIURGE_ID);
        setBaseSkillChance(CharacterConfig.DEMIURGE_BASE_SKILL_CHANCE);
        setCurrentLevel(1);
    }
	
	public void useSkill(Entity entity) {
		super.useSkill(entity, getBaseSkillChance());
        
		infernalStrategy(entity);
	}
	
	@Override
    public void infernalStrategy(Entity entity) {
        Entity target = entity.getEnemy();
        GameBoard gameBoard = GameManager.getInstance().getGameBoard();
        GenericBlock currentBlock = gameBoard.getBlockList().get(target.getBoardIndex());
        GenericBlock startBlock = gameBoard.getBlockList().get(0);

        if (target != null) {
            int chance = getBaseSkillChance() + (getCurrentLevel() - 1);

            if (GameManager.getInstance().getGameBoard().getBlockList().indexOf(currentBlock) == 0) {
                chance = 100;
            }

            if (rand.nextInt(100) < chance) {
                int stepsToStart = calculateStepsToStart(target.getBoardIndex(), gameBoard.getBlockList().size());
               moveWithAnimation(target, stepsToStart); 

                TextUtil.clearScreen();
                GameManager.getInstance().getGameBoard().printBoard();
                System.out.println(" " + getName() + " used Infernal Strategy and teleported " + target.getName() + " to the start!");
                System.out.println();
                TextUtil.pressEnter();
                TextUtil.printHorizontalBorder(
                        BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));
                startBlock.onLand(target);
                TextUtil.pressEnter();
            } else {
                System.out.println(" " + getName() + "'s Infernal Strategy failed.");
            }
        }
    }

    private int calculateStepsToStart(int currentIndex, int boardSize) {
        if (currentIndex == 0) {
            return 0;
        } else {
            return (boardSize - currentIndex); 
        }
    }
}
