package model.card;

import model.entity.Entity;
import model.game.GameBoard;
import utility.TextUtil;
import manager.GameManager;
import model.block.GenericBlock;
import model.block.GoToJailBlock;

public class SendToJailCard extends GenericCard {

    public SendToJailCard() {
        setName("Send to Jail");
        setDesc("Send your opponent to the 'Go to Jail' block immediately.");
    }

    @Override
    public void onTrigger(Entity piece) {
    	if(piece.getEnemy().isInJail()) {
    		System.out.println(" " + piece.getName() + " is already in jail!");
    		return;
    	}
        GameManager gameManager = GameManager.getInstance();
        GameBoard gameBoard = gameManager.getGameBoard();

        Entity target = (piece == gameManager.getPlayer()) ? gameManager.getEnemy() : gameManager.getPlayer();

        GenericBlock currentBlock = null;
        for (GenericBlock block : gameBoard.getBlockList()) {
            if (block.getPiecesOnBlock().contains(target)) {
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

        currentBlock.removePiece(target);

        System.out.println(" " + piece.getName() + " used 'Send to Jail' on " + target.getName() + "!");
        
        target.setBoardIndex(jailBlock.getIndex());
        jailBlock.addPiece(target);
        jailBlock.onLand(target);
    }
}
