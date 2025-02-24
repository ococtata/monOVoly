package model.gacha.character;

import config.BoardConfig;
import config.CharacterConfig;
import config.ColorConfig;
import manager.GameManager;
import model.block.GenericBlock;
import model.block.PropertyBlock;
import model.entity.Entity;
import model.game.GameBoard;
import utility.Random;
import utility.TextUtil;

public class Demiurge extends BaseCharacter implements CharacterSkills, Random {

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
        PropertyBlock destination = entity.chooseProperty();
        GenericBlock currentBlock = gameBoard.getBlockList().get(target.getBoardIndex());

        if (target != null && destination != null) {
            int chance = getBaseSkillChance() + (getCurrentLevel() - 1);

            if (GameManager.getInstance().getGameBoard().getBlockList().indexOf(destination) == 0){
                chance = 100;
            }

            if (GameManager.getInstance().getGameBoard().getBlockList().indexOf(currentBlock) == 0){
                chance = 100;
            }

            if(GameManager.getInstance().getGameBoard().getBlockList().indexOf(currentBlock) == GameManager.getInstance().getGameBoard().getBlockList().indexOf(destination)){
                chance = 0;
            }

            if (GameManager.getInstance().getGameBoard().getBlockList().indexOf(destination) == 0 || GameManager.getInstance().getGameBoard().getBlockList().indexOf(currentBlock) == 0 || rand.nextInt(100) < chance) {
                target.move(currentBlock, destination);
                TextUtil.clearScreen();
                currentBlock.removePiece(target);
                GameManager.getInstance().getGameBoard().printBoard();
                System.out.println(" " + getName() + " used Infernal Strategy and teleported " + target.getName() + " to " + destination.getName() + "!");
                System.out.println();
                TextUtil.pressEnter();
                TextUtil.printHorizontalBorder(
                        BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));
                destination.onLand(target);
                TextUtil.pressEnter();
            } 
            else {
                System.out.println(" " + getName() + "'s Infernal Strategy failed.");
            }
        }
	}
}
