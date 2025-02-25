package model.card;

import model.entity.Entity;
import model.game.GameBoard;
import utility.TextUtil;
import controller.game.monovoly.IMonovolyGameGUI;
import manager.GameManager;
import model.block.GenericBlock;
import model.block.GoToJailBlock;
import model.block.JailBlock;

public class SendToJailCard extends GenericCard implements IMonovolyGameGUI {

    public SendToJailCard() {
        setName("Send to Jail");
        setDesc("Send your opponent to the 'Go to Jail' block immediately.");
    }

    @Override
    public void onTrigger(Entity piece) {
        if (piece.getEnemy().isInJail()) {
            System.out.println(" " + piece.getEnemy().getName() + " is already in jail!");
            return;
        }

        GameManager gameManager = GameManager.getInstance();
        GameBoard gameBoard = gameManager.getGameBoard();
        
        Entity target = (piece == gameManager.getPlayer()) ? gameManager.getEnemy() : gameManager.getPlayer();

        int targetIndex = target.getBoardIndex();
        
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

        System.out.println(" " + piece.getName() + " used 'Send to Jail' on " + target.getName() + "!\n");
        TextUtil.pressEnter();
        
        int stepsToJail = calculateStepsToJail(targetIndex, jailIndex, gameBoard.getBlockList().size());
        moveWithAnimation(target, stepsToJail);

        target.setInJail(true);
    }

    private int calculateStepsToJail(int currentIndex, int jailIndex, int boardSize) {
        if (currentIndex <= jailIndex) {
            return jailIndex - currentIndex;
        } else {
            return (boardSize - currentIndex) + jailIndex + 1;
        }
    }
}
