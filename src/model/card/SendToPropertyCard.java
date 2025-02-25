package model.card;

import java.util.List;

import config.BoardConfig;
import controller.game.monovoly.IMonovolyGameGUI;
import manager.GameManager;
import model.block.GenericBlock;
import model.block.PropertyBlock;
import model.entity.Enemy;
import model.entity.Entity;
import model.entity.Player;
import utility.Scanner;
import utility.TextUtil;

public class SendToPropertyCard extends GenericCard implements Scanner, IMonovolyGameGUI{
	
	public SendToPropertyCard() {
		setName("Send to Property");
        setDesc("Send your opponent to one of your owned properties.");
	}

	@Override
    public void onTrigger(Entity piece) {
        if (piece.getEnemy().isInJail()) {
            System.out.println(" " + piece.getEnemy().getName() + " is in jail, cannot be moved!");
            return;
        }

        GameManager gameManager = GameManager.getInstance();
        Entity target = (piece == gameManager.getPlayer()) ? gameManager.getEnemy() : gameManager.getPlayer();
        List<PropertyBlock> ownedProperties = piece.getOwnedProperties();

        if (ownedProperties.isEmpty()) {
            System.out.println(" " + piece.getName() + " doesn't own any properties yet.");
            return;
        }

        PropertyBlock chosenProperty = piece.chooseProperty();

        GenericBlock currentBlock = null;
        for (GenericBlock block : gameManager.getGameBoard().getBlockList()) {
            if (block.getPiecesOnBlock().contains(target)) {
                currentBlock = block;
                break;
            }
        }

        if (currentBlock == null || chosenProperty == null) {
            return;
        }

        System.out.println(" " + piece.getName() + " used " + this.getName() + " on " + target.getName() + "!");
        TextUtil.pressEnter();

        int stepsToProperty = calculateStepsToProperty(target.getBoardIndex(), chosenProperty.getIndex(), gameManager.getGameBoard().getBlockList().size());
        moveWithAnimation(target, stepsToProperty);

        TextUtil.clearScreen();
        GameManager.getInstance().getGameBoard().printBoard();
        TextUtil.printHorizontalBorder(BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));

        chosenProperty.onLand(target);
    }

    private int calculateStepsToProperty(int currentIndex, int propertyIndex, int boardSize) {
        if (currentIndex <= propertyIndex) {
            return propertyIndex - currentIndex;
        } else {
            return (boardSize - currentIndex) + propertyIndex;
        }
    }
}
