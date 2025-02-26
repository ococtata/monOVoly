package model.game;

import java.util.ArrayList;
import java.util.List;

import config.BoardConfig;
import config.ColorConfig;
import config.DataConfig;
import model.block.CardBlock;
import model.block.GenericBlock;
import model.block.GoToJailBlock;
import model.block.JailBlock;
import model.block.PropertyBlock;
import model.block.StartBlock;
import model.block.TaxBlock;
import model.block.WorldTravelBlock;
import model.entity.Entity;
import utility.FileUtil;

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
        initializeBorders();
    }
    
    private void initializeBorders() {
    	borderTopBot = getTopBotBorder();
    }

    private void initializeBoard() {
        List<String> propertyBlockData = FileUtil.readFile(DataConfig.FILE_DATA_BLOCK);
        blockList.add(new StartBlock("Go!", "In the end, it all comes back to this.", 0));

        int amount = 2 * (boardHeight + boardWidth) - 4;
        int propertyCounter = 0;
        int cardCounter = 0;

        for (int i = 1; i <= amount - 1; i++) {
            if (propertyBlockData.size() <= i) break;
            String[] propertyData = propertyBlockData.get(i).split("#");
            String type = propertyData[0];
            String propertyName = propertyData[1];
            String propertyDesc = propertyData[2];
            String landmarkName = propertyData[3];
            String landmarkDesc = propertyData[4];

            PropertyBlock property = new PropertyBlock(propertyName, propertyDesc, i, landmarkName, landmarkDesc);
            blockList.add(property);
            propertyCounter++;

            if (i == boardWidth - 2) {
                blockList.add(new GoToJailBlock("Go to Jail", "You have been caught! Move directly to jail.", i));
                i++;
            } 
            else if (i == boardWidth + boardHeight - 3) {
                blockList.add(new WorldTravelBlock("World Travel", "Take a trip and teleport randomly to any block!", i));
                i++;
            } 
            else if (i == 2 * (boardWidth - 1) + (boardHeight - 2)) {
                blockList.add(new JailBlock("Jail", "A prison for those who've been naughty.", i));
                i++;
            } 
            else if (propertyCounter % 3 == 0 && cardCounter < amount / 3) {
                blockList.add(new CardBlock("Mystery Card", "Draw a card for a surprise event!", i));
                cardCounter++;
                i++;
            } 
            else if (i == amount - 3) {
                blockList.add(new TaxBlock("Tax Collection", "Pay 10% of your total wealth as tax. Glory to MonOVoly!", i));
                i++;
            }
        }
    }
    
    private String getTopBotBorder() {
    	String border = "";
    	
    	for(int i = 0; i < blockWidth; i++) {
    		border += BoardConfig.BOARD_BORDER;
    	}
    	
    	return border;
    }
    
    public void printBoard() {
        List<GenericBlock> orderedBlocks = getClockwiseBlocks();
        int blockIndex = 0;
        int labelIndex = 0;

        for (int rowBlock = 0; rowBlock < boardHeight; rowBlock++) {
            for (int colBlock = 0; colBlock < boardWidth; colBlock++) {
            	String empty = " ".repeat(blockWidth);
                if (isBorderBlock(rowBlock, colBlock, boardWidth, boardHeight)) {
                    System.out.print(" " +borderTopBot);
                } 
                else {
                    System.out.print(" "+empty);
                }
            }
            System.out.println();

            for (int row = 0; row < blockHeight - 1; row++) {
                for (int colBlock = 0; colBlock < boardWidth; colBlock++) {
                    if (isBorderBlock(rowBlock, colBlock, boardWidth, boardHeight)) {
                        if (row == 1) { 
                            String pieceString = getPieceOnBlockString(orderedBlocks.get(blockIndex));
                            System.out.printf(" # %-2s#", pieceString);
                            blockIndex++;
                        } 
                        else if(row == 0) {
                        	String blockLabel = getBlockLabel(orderedBlocks.get(labelIndex));
                        	System.out.printf(" # %-2s#", blockLabel);
                        	labelIndex++;
                        }
                        else {
                            System.out.print(" #   #");
                        }
                    } 
                    else {
                        System.out.print("      ");
                    }
                }
                System.out.println();
            }

            for (int colBlock = 0; colBlock < boardWidth; colBlock++) {
                if (isBorderBlock(rowBlock, colBlock, boardWidth, boardHeight)) {
                	System.out.print(" " +borderTopBot);
                } 
                else {
                    System.out.print("      ");
                }
            }
            System.out.println();
        }
    }
    
    private String getBlockLabel(GenericBlock block) {
        String label = block.getType();
        
        if(block instanceof PropertyBlock && ((PropertyBlock) block).hasLandmark()) {
        	label = "L";
        }
        else if (label.length() > 2) {
        	label = label.substring(0, 1).toUpperCase();
        }
        
        if (block instanceof PropertyBlock) {
            PropertyBlock propertyBlock = (PropertyBlock) block;
            Entity owner = propertyBlock.getOwner();
            
            
            if (owner != null) {
                String ownerColor = owner.getColor();
                if(propertyBlock.isFestival()) {
                	label += "F";
                	label = ownerColor + label + ColorConfig.RESET;
                }
                else {
                	label = ownerColor + label + ColorConfig.RESET;                   	
                }
            }
        }
        
        String color = getColorForBlock(block);

        return color + label + ColorConfig.RESET + " ";
    }
    
    private String getPieceOnBlockString(GenericBlock block) {
        String output = "";
        int len = block.getPiecesOnBlock().size();
        
        if (len == 0) {
            return "  ";  
        }
        
        for (int i = 0; i < len; i++) {
        	if(len == 1) {
        		output += " ";
        	}
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

	public List<GenericBlock> getBlockList() {
		return blockList;
	}

	public void setBlockList(List<GenericBlock> blockList) {
		this.blockList = blockList;
	}
	
	private String getColorForBlock(GenericBlock block) {
	    if (block instanceof StartBlock) {
	        return ColorConfig.DARK_GREY;
	    } 
	    else if (block instanceof CardBlock) {
	        return ColorConfig.GOLD; 
	    } 
	    else if (block instanceof TaxBlock) {
	        return ColorConfig.LIGHT_RED; 
	    } 
	    else if (block instanceof WorldTravelBlock) {
	        return ColorConfig.PURPLE; 
	    } 
	    else if (block instanceof GoToJailBlock) {
	        return ColorConfig.CYAN; 
	    } 
	    else if (block instanceof JailBlock) {
	        return ColorConfig.ORANGE; 
	    } 
	    else {
	        return ColorConfig.RESET;
	    }
	}

}