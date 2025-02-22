package model.block;

import java.util.List;

import config.BoardConfig;
import manager.GameManager;
import model.entity.Entity;
import utility.Random;
import utility.TextUtil;

public class WorldTravelBlock extends GenericBlock implements Random {

	public WorldTravelBlock(String name, String desc, int index) {
		super(name, desc, index);
		setType("World Travel");
	}

	@Override
	public void onLand(Entity piece) {
        List<GenericBlock> blockList = GameManager.getInstance().getGameBoard().getBlockList();
		
        int randomIndex = rand.nextInt(blockList.size());
        GenericBlock randomBlock = blockList.get(randomIndex);
        
        TextUtil.pressEnter();
        piece.move(this, randomBlock);
        
        TextUtil.clearScreen();
        GameManager.getInstance().getGameBoard().printBoard();
        TextUtil.printHorizontalBorder(BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));

        System.out.println(" " + piece.getName() + " was teleported to " + randomBlock.getName() + "!\n");
        TextUtil.printHorizontalBorder(BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));

        randomBlock.onLand(piece);
	}

}
