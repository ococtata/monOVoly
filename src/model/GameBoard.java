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
    private int blockWidth, blockHeight;

    public GameBoard() {
        this.blockList = new ArrayList<>();
        this.blockWidth = BoardConfig.BLOCK_WIDTH;
        this.blockHeight = BoardConfig.BLOCK_HEIGHT;
        initializeBoard();
    }

    private void initializeBoard() {
        int amount = 2 * (height + width);
        for(int i = 0; i < amount; i++) {
        	blockList.add(new PropertyBlock("test", "desc"));
        }
    }
    
    public void printBoard() {
    	
    }
    
    private void initializePieces() {
    	
    }
}