package model.gacha.character;

import config.CharacterConfig;
import config.ColorConfig;
import manager.GameManager;
import model.block.GenericBlock;
import model.block.PropertyBlock;
import model.entity.Entity;
import model.game.GameBoard;

public class Demiurge extends BaseCharacter implements CharacterSkills {

	public Demiurge() {
        setName(CharacterConfig.DEMIURGE_NAME);
        setTitle(CharacterConfig.DEMIURGE_TITLE);
        setSkillName(CharacterConfig.DEMIURGE_SKILL_NAME);
        setSkillDesc(CharacterConfig.DEMIURGE_SKILL_DESC);
        setNameColor(ColorConfig.DEVIL_RED);
    }
	
	@Override
	public void useSkill(Entity entity) {
		super.useSkill(entity);
        
		infernalStrategy(entity);
	}
	
	@Override
    public void infernalStrategy(Entity entity) {
		Entity target = entity.getEnemy();
		GameBoard gameBoard = GameManager.getInstance().getGameBoard();
		PropertyBlock destination = target.chooseProperty();
		GenericBlock currentBlock = gameBoard.getBlockList().get(target.getBoardIndex());
		
        if (target != null && destination != null) {
            target.move(currentBlock, destination);
            GameManager.getInstance().getGameBoard().printBoard();
            System.out.println(" " + getName() + " used Infernal Strategy and teleported " + target.getName() + " to " + destination.getName() + "!");
        }
    }
}
