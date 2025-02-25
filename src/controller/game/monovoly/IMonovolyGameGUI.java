package controller.game.monovoly;

import config.BoardConfig;
import config.ColorConfig;
import manager.GameManager;
import model.block.CardBlock;
import model.block.GenericBlock;
import model.block.JailBlock;
import model.block.PropertyBlock;
import model.entity.Entity;
import model.gacha.character.BaseCharacter;
import model.gacha.character.Cocytus;
import model.gacha.character.ShalltearBloodfallen;
import model.game.GameBoard;
import utility.TextUtil;

public interface IMonovolyGameGUI {
	default void moveWithAnimation(Entity entity, int steps) {
	    GameBoard gameBoard = GameManager.getInstance().getGameBoard();
	    int currentIndex = entity.getBoardIndex();

	    for (int i = 0; i < steps; i++) {
	        gameBoard.getBlockList().get(currentIndex).removePiece(entity);
	        int nextIndex = (currentIndex + 1) % gameBoard.getBlockList().size();
	        gameBoard.getBlockList().get(nextIndex).addPiece(entity);

	        entity.setBoardIndex(nextIndex);

	        printGameInfo();

	        try {
	            Thread.sleep(350);
	        } 
	        catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }

	        currentIndex = nextIndex;

	        Entity opponent = entity.getEnemy();
	        if (opponent != null && opponent.getBoardIndex() == currentIndex && entity.getEquippedCharacter() instanceof ShalltearBloodfallen) {
	            ShalltearBloodfallen shalltear = (ShalltearBloodfallen) entity.getEquippedCharacter();
	            shalltear.useSkill(entity, opponent);
	        }
	        else if (opponent != null && opponent.getBoardIndex() == currentIndex && entity.getEquippedCharacter() instanceof Cocytus) {
	            Cocytus cocytus = (Cocytus) entity.getEquippedCharacter();
	            cocytus.useSkill(entity, opponent);
	        }
	    }

	    GenericBlock landedBlock = gameBoard.getBlockList().get(currentIndex);
	    printGameInfo();

	    try {
	        Thread.sleep(700);
	    } catch (InterruptedException e) {
	        Thread.currentThread().interrupt();
	    }
	    
	    showBlockInfo(entity, landedBlock);
	    
	    if(entity.isInJail() && landedBlock instanceof JailBlock && !landedBlock.getPiecesOnBlock().contains(entity)) {
	    	landedBlock.addPiece(entity);
	    }
	    landedBlock.onLand(entity);	    	
	}
	
	default void showBlockInfo(Entity piece, GenericBlock block) {
		System.out.println(" " + piece.getName() + " landed on a '" + block.getType() + "' block!");
		System.out.println();

		if (!(block instanceof CardBlock)) {
			System.out.println(" Name: " + block.getName());
			System.out.println(" Desc: " + block.getDesc());
			System.out.println();

			if (block instanceof PropertyBlock) {
				PropertyBlock propertyBlock = (PropertyBlock) block;
				Entity owner = propertyBlock.getOwner();

				if (owner == null) {
					System.out.println(" Owner\t\t: None");
					System.out.println(" Price\t\t: $" + propertyBlock.getPrice());
				} else {
					System.out.println(" Owner\t\t: " + owner.getName());
					System.out.println(" Price\t\t: $" + propertyBlock.getPrice());
					System.out.println(" Building Level\t: " + propertyBlock.getBuildingLevel());

					if (propertyBlock.hasLandmark()) {
						System.out.print(" Landmark\t: ");
						showLandmarkInfo(propertyBlock);
					} else {
						System.out.println(" Landmark\t: (NO)");
					}
				}
			}
		}
		TextUtil.printHorizontalBorder(
				BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));
	}
	
	default void printGameInfo() {
		TextUtil.clearScreen();
		GameManager.getInstance().getGameBoard().printBoard();
		TextUtil.printHorizontalBorder(
				BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));
		showStats();
		TextUtil.printHorizontalBorder(
				BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));
	}
	
	default void showStats() {
		Entity entity = GameManager.getInstance().isPlayerTurn() ? GameManager.getInstance().getPlayer()
				: GameManager.getInstance().getEnemy();
		printStatsWithCharacter(entity);
	}

	default void printStatsWithCharacter(Entity entity) {
	    System.out.print(" " + (entity.getRank() == 1 ? "(1st)" : "(2nd)"));
	    System.out.print("\t " + entity.getName());
	    String money = ColorConfig.GREEN + "$ " + entity.getMoney() + ColorConfig.RESET;
	    String total = ColorConfig.GOLD + entity.getTotalAssest() + ColorConfig.RESET;
	    System.out.println("\t\t\t " + money + "\t\t Total: " + total);

	    BaseCharacter equippedCharacter = entity.getEquippedCharacter();
	    if (equippedCharacter != null) {
	        System.out.println(" \t\t\t Character: \t " + equippedCharacter.getNameColor() + equippedCharacter.getName()
	                + ColorConfig.RESET + " (Lvl. " + equippedCharacter.getCurrentLevel() + ")");
	    } else {
	        System.out.println(" \t\t\t Character: \t None");
	    }
	}
	
	default void showLandmarkInfo(PropertyBlock property) {
		System.out.println(property.getLandmarkName());
		System.out.println(" Landmark Desc  : " + property.getLandmarkDesc());
	}
}
