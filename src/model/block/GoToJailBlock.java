package model.block;

import manager.GameManager;
import model.entity.Entity;
import model.game.GameBoard;

public class GoToJailBlock extends GenericBlock {

	public GoToJailBlock(String name, String desc, int index) {
		super(name, desc, index);
		setType("Go to Jail");
	}

	@Override
    public void onLand(Entity piece) {
        GameManager gameManager = GameManager.getInstance();
        GameBoard gameBoard = gameManager.getGameBoard();

        JailBlock jailBlock = null;
        for (GenericBlock block : gameBoard.getBlockList()) {
            if (block instanceof JailBlock) {
                jailBlock = (JailBlock) block;
                break;
            }
        }

        System.out.println(" " + piece.getName() + " was sent to jail!");
        piece.move(gameBoard.getBlockList().get(piece.getBoardIndex()), gameBoard.getBlockList().get(jailBlock.getIndex() + 1));
        piece.setInJail(true);
    }
}
