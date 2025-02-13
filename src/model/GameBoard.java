package model;

import java.util.ArrayList;
import java.util.List;

import config.BoardConfig;
import model.block.GenericBlock;
import model.block.PropertyBlock;

public class GameBoard {
	private int height = BoardConfig.BOARD_HEIGHT;
	private int width = BoardConfig.BOARD_WIDTH;
	private List<GenericBlock> blockList;
	
	public GameBoard() {
        this.blockList = new ArrayList<>();
        initializeBoard();
    }
	
	 private void initializeBoard() {
        for (int i = 0; i < BoardConfig.BLOCK_AMOUNT; i++) {
        	blockList.add(new PropertyBlock("test", "test"));
        }
    }

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public List<GenericBlock> getBlockList() {
		return blockList;
	}

	public void setBlockList(List<GenericBlock> blockList) {
		this.blockList = blockList;
	}	
	
	
}
