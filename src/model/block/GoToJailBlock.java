package model.block;

import controller.game.monovoly.IMonovolyGameGUI;
import manager.GameManager;
import model.entity.Entity;
import model.game.GameBoard;
import utility.TextUtil;

public class GoToJailBlock extends GenericBlock implements IMonovolyGameGUI {

	public GoToJailBlock(String name, String desc, int index) {
		super(name, desc, index);
		setType("Go to Jail");
	}

	@Override
    public void onLand(Entity piece) {
        GameManager gameManager = GameManager.getInstance();
        GameBoard gameBoard = gameManager.getGameBoard();
        
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

        System.out.println(" " + piece.getName() + " was sent to jail!\n");
        TextUtil.pressEnter();
        this.removePiece(piece);
        int stepsToJail = calculateStepsToJail(piece.getBoardIndex(), jailIndex, gameBoard.getBlockList().size());
        piece.setInJail(true);
        moveWithAnimation(piece, stepsToJail + 1);

    }
	
	private int calculateStepsToJail(int currentIndex, int jailIndex, int boardSize) {
        if (currentIndex <= jailIndex) {
            return jailIndex - currentIndex;
        } else {
            return (boardSize - currentIndex) + jailIndex;
        }
    }
}
