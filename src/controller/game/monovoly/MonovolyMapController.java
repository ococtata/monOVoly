package controller.game.monovoly;

import config.BoardConfig;
import manager.GameManager;
import model.Dice;
import model.GameBoard;
import model.block.GenericBlock;
import model.block.PropertyBlock;
import model.card.CardFirstTurn;
import model.entity.Entity;
import utility.Scanner;
import utility.TextUtil;
import view.game.monovoly.MonovolyMap;

public class MonovolyMapController implements Scanner {
    private MonovolyMap monovolyMap;

    public MonovolyMapController(MonovolyMap monovolyMap) {
        this.monovolyMap = monovolyMap;
        GameManager.getInstance().pausePlayerMovementThread();
    }
    
    public void startGame() {
    	if(GameManager.getInstance().isFirstTurn()) {
			firstTurn();
		}
		else {
			beginTurn();							
			switchTurn();
		}
    }
    
    private void switchTurn() {
    	GameManager.getInstance().setPlayerTurn(!GameManager.getInstance().isPlayerTurn());
    }

    private void firstTurn() {
        CardFirstTurn cardFirstTurn = new CardFirstTurn();
        cardFirstTurn.selectFirst();
        GameManager.getInstance().setFirstTurn(false);
    }

    private void beginTurn() {
    	Dice dice = new Dice();
    	
        if (GameManager.getInstance().isPlayerTurn()) {
            gameMenu();
            String input = scan.nextLine();

            try {
                int choice = Integer.parseInt(input);
                int rollResult = -1;

                switch (choice) {
                    case 1: 
                        rollResult = dice.roll("ODD");
                        System.out.println(" You rolled an ODD number: " + rollResult);
                        break;
                    case 2: 
                        rollResult = dice.roll("ANY");
                        System.out.println(" You rolled: " + rollResult);
                        break;
                    case 3: 
                        rollResult = dice.roll("EVEN");
                        System.out.println(" You rolled an EVEN number: " + rollResult);
                        break;
                    default:
                        System.out.println(" Input must be 1-3!");
                        TextUtil.pressEnter();
                        break;
                }
                
                TextUtil.pressEnter();
                
                if(rollResult != -1) {
                	moveWithAnimation(GameManager.getInstance().getPlayer(), rollResult);
                }
            } catch (NumberFormatException e) {
                System.out.println(" Input must be an integer!");
                TextUtil.pressEnter();
            }
        }
        else {
        	int rollResult = dice.roll("ANY");
        	System.out.println(" Enemy rolled: " + rollResult);
        	TextUtil.pressEnter();
        	moveWithAnimation(GameManager.getInstance().getEnemy(), rollResult);
        }
        
    }

    private void gameMenu() {
        System.out.println(" 1. Roll dice (ODD)");
        System.out.println(" 2. Roll dice (ANY)");
        System.out.println(" 3. Roll dice (EVEN)");
        System.out.print(" >> ");
    }
    
    private void moveWithAnimation(Entity entity, int steps) {
        GameBoard gameBoard = GameManager.getInstance().getGameBoard();
        int currentIndex = entity.getBoardIndex();
        
        for (int i = 0; i < steps; i++) {
        	TextUtil.clearScreen();
        	gameBoard.getBlockList().get(currentIndex).removePiece(entity); 
            int nextIndex = (currentIndex + 1) % gameBoard.getBlockList().size(); 
            gameBoard.getBlockList().get(nextIndex).addPiece(entity);
            
            entity.setBoardIndex(nextIndex);
            
            gameBoard.printBoard(); 
            
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            currentIndex = nextIndex;
        }
        
        GenericBlock landedBlock = gameBoard.getBlockList().get(currentIndex);
        landedBlock.onLand(entity);
        TextUtil.printHorizontalBorder(BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));
        showBlockInfo(landedBlock);
    }
    
    private void showBlockInfo(GenericBlock block) {
        System.out.println(" Block Name: " + block.getName());
        System.out.println(" Block Type: " + block.getType());
        System.out.println(" Desc: " + block.getDesc());
        System.out.println();

        if (block instanceof PropertyBlock) {
            PropertyBlock propertyBlock = (PropertyBlock) block;
            Entity owner = propertyBlock.getOwner();
            System.out.println(" Owner: " + (owner != null ? owner.getName() : "None"));
            if(owner != null) {
            	System.out.println(" Price: " + propertyBlock.getPrice());      
            	System.out.println(" Building Level: " + propertyBlock.getBuildingLevel());
            	System.out.println(" Landmark " + (propertyBlock.isHasLandmark() ? "(YES)" : "(NO)"));
            }
        }
    }

}