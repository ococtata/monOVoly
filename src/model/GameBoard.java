package model;

import java.util.ArrayList;
import java.util.List;

import config.BoardConfig;
import model.block.GenericBlock;
import model.block.PropertyBlock;
import model.block.StartBlock;

public class GameBoard {
    private int boardHeight, boardWidth;
    private List<GenericBlock> blockList;
    private int blockWidth, blockHeight;
    
    private String borderTopBot;

    public GameBoard() {
        this.blockList = new ArrayList<>();
        this.boardHeight = BoardConfig.BOARD_HEIGHT;
        this.boardWidth = BoardConfig.BOARD_WIDTH;
        
        this.blockWidth = BoardConfig.BLOCK_WIDTH;
        this.blockHeight = BoardConfig.BLOCK_HEIGHT;
        initializeBoard();
        initializePieces();
        initializeBorders();
    }
    
    private void initializeBorders() {
    	borderTopBot = getTopBotBorder();
    }

    private void initializeBoard() {
    	blockList.add(new StartBlock("start", "desc"));
        int amount = 2 * (boardHeight + boardWidth) - 4;
        for(int i = 1; i < amount - 1; i++) {
        	blockList.add(new PropertyBlock(""+i, "desc"));
        }
    }
    
    private String getTopBotBorder() {
    	String border = "";
    	
    	for(int i = 0; i < blockWidth; i++) {
    		border += BoardConfig.BOARD_BORDER;
    	}
    	
    	return border;
    }
    
    private String getInsideBorder(Boolean isEmpty, GenericBlock block) {
        String border = "# ";
        
        if (isEmpty) {
            border += BoardConfig.BOARD_BORDER.repeat(blockWidth - 3); 
        } else {
            int number = block.getNumber(); 
            String format = String.format("%%-%ds", blockWidth - 3);
            border += String.format(format, number);
        }
        
        border += "#";
        return border;
    }
    
    public void printBoard() {
        List<GenericBlock> orderedBlocks = getClockwiseBlocks();
        int blockIndex = 0;

        for (int rowBlock = 0; rowBlock < boardHeight; rowBlock++) {
            for (int colBlock = 0; colBlock < boardWidth; colBlock++) {
            	String empty = " ".repeat(blockWidth);
                if (isBorderBlock(rowBlock, colBlock, boardWidth, boardHeight)) {
                    System.out.print(" " +borderTopBot);
                } else {
                    System.out.print(" "+empty);
                }
            }
            System.out.println();

            for (int row = 0; row < blockHeight - 1; row++) {
                for (int colBlock = 0; colBlock < boardWidth; colBlock++) {
                    if (isBorderBlock(rowBlock, colBlock, boardWidth, boardHeight)) {
                        if (row == 1) { 
                            String blockName = orderedBlocks.get(blockIndex).getName();
                            System.out.printf(" # %-2s#", getPieceOnBlockString(orderedBlocks.get(blockIndex)));
                            blockIndex++;
                        } else {
                            System.out.print(" #   #");
                        }
                    } else {
                        System.out.print("      ");
                    }
                }
                System.out.println();
            }

            for (int colBlock = 0; colBlock < boardWidth; colBlock++) {
                if (isBorderBlock(rowBlock, colBlock, boardWidth, boardHeight)) {
                	System.out.print(" " +borderTopBot);
                } else {
                    System.out.print("      ");
                }
            }
            System.out.println();
        }
    }
    
    private String getPieceOnBlockString(GenericBlock block) {
    	String output = "";
    	int len = block.getPiecesOnBlock().size();
    	for(int i = 0; i < len; i++) {
    		output += block.getPiecesOnBlock().get(i).getPiece();
    	}
    	
    	return output;
    }
    
    private boolean isBorderBlock(int row, int col, int width, int height) {
        return row == 0 || row == height - 1 || col == 0 || col == width - 1;
    }

    private List<GenericBlock> getClockwiseBlocks() {
        List<GenericBlock> orderedBlocks = new ArrayList<>();

        for (int i = 0; i < boardWidth; i++) {
            orderedBlocks.add(blockList.get(i));
        }

        int rightColumnStart = boardWidth;
        int leftColumnStart = blockList.size() - 1;

        for (int i = 0; i < boardHeight - 2; i++) {
            orderedBlocks.add(blockList.get(leftColumnStart - i));
            orderedBlocks.add(blockList.get(rightColumnStart + i));
        }

        int bottomRowStart = boardWidth + (boardHeight - 2);
        for (int i = 0; i < boardWidth; i++) {
            orderedBlocks.add(blockList.get(bottomRowStart + boardWidth - 1 - i));
        }

        return orderedBlocks;
    }

    private void initializePieces() {
        
    }
}