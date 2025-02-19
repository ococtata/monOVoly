package model.card;

import java.util.List;

import config.BoardConfig;
import manager.GameManager;
import model.block.GenericBlock;
import model.block.PropertyBlock;
import model.entity.Enemy;
import model.entity.Entity;
import model.entity.Player;
import utility.Scanner;
import utility.TextUtil;

public class SendToPropertyCard extends GenericCard implements Scanner{
	
	public SendToPropertyCard() {
		setName("Send to Property");
        setDesc("Send your opponent to one of your owned properties.");
	}

	@Override
    public void onTrigger(Entity piece) {
		if(piece.getEnemy().isInJail()) {
    		System.out.println(" " + piece.getName() + " is in jail, cannot be moved!");
    		return;
    	}
        GameManager gameManager = GameManager.getInstance();
        Entity target = (piece == gameManager.getPlayer()) ? gameManager.getEnemy() : gameManager.getPlayer();
        
        List<PropertyBlock> ownedProperties = piece.getOwnedProperties();

        if (ownedProperties.isEmpty()) {
            System.out.println(" " + piece.getName() + " doesn't own any properties yet.");
            TextUtil.pressEnter();
            return;
        }
        
        PropertyBlock chosenProperty = chooseProperty(ownedProperties, piece);

        GenericBlock currentBlock = null;
        for (GenericBlock block : gameManager.getGameBoard().getBlockList()) {
            if (block.getPiecesOnBlock().contains(target)) {
                currentBlock = block;
                break;
            }
        }
        
        if (currentBlock != null) {
            currentBlock.removePiece(target);
        }

        System.out.println(" " + piece.getName() + " used " + this.getName() + " on " + target.getName() + "!");
        TextUtil.pressEnter();

        target.move(currentBlock, chosenProperty);
        
        TextUtil.clearScreen();
        GameManager.getInstance().getGameBoard().printBoard();
        chosenProperty.onLand(target);
    }
	
	private PropertyBlock chooseProperty(List<PropertyBlock> ownedProperties, Entity piece) {
	    PropertyBlock chosen = null;

	    if (piece instanceof Player) {
	        int pageSize = 3;
	        int currentPage = 0;

	        while (true) {
	            TextUtil.clearScreen();
	            int startIndex = currentPage * pageSize;
	            int endIndex = Math.min((currentPage + 1) * pageSize, ownedProperties.size());

	            System.out.println(" Your Properties (Page " + (currentPage + 1) + " of " + (int) Math.ceil((double) ownedProperties.size() / pageSize) + "):");
	            TextUtil.printHorizontalBorder(BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));

	            for (int i = startIndex; i < endIndex; i++) {
	                PropertyBlock property = ownedProperties.get(i);
	                System.out.println(" " + (i + 1) + ". " + property.getName()); 
	                TextUtil.printHorizontalBorder(BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));
	                System.out.println("   Value: $" + property.getPrice());
	                System.out.println("   Toll: $" + property.calculateToll(property));
	                System.out.println("   Building Level: " + property.getBuildingLevel());
	                System.out.println("   Has Landmark: " + (property.hasLandmark() ? "Yes" : "No"));
	                System.out.println();
	            }
	            TextUtil.printHorizontalBorder(BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));

	            if (endIndex < ownedProperties.size()) {
	                System.out.println(" 1. Previous Page");
	                System.out.println(" 2. Next Page");
	                System.out.println(" Type property name to choose"); 
	            } else if (currentPage > 0) {
	                System.out.println(" 1. Previous Page");
	                System.out.println(" Type property name to choose"); 
	            } else {
	                System.out.println(" Type property name to choose"); 
	            }

	            System.out.print(" >> ");

	            String input = scan.nextLine();

	            try {
	                int choice = Integer.parseInt(input);
	                if (choice == 1) {
	                    if (currentPage > 0) {
	                        currentPage--;
	                    } 
	                    else {
	                        System.out.println(" You are already on the first page.");
	                        TextUtil.pressEnter();
	                    }
	                } 
	                else if (choice == 2) {
	                    if (endIndex < ownedProperties.size()) {
	                        currentPage++;
	                    } 
	                    else {
	                        System.out.println(" You are already on the last page.");
	                        TextUtil.pressEnter();
	                    }
	                } else {
	                    System.out.println(" Invalid choice. Please enter 1 or 2, or a property name.");
	                    TextUtil.pressEnter();
	                }
	            } catch (NumberFormatException e) {
	                for (PropertyBlock property : ownedProperties) {
	                    if (property.getName().equalsIgnoreCase(input)) {
	                        chosen = property;
	                        return chosen;
	                    }
	                }
	                System.out.println(" Invalid input. Please enter 1 or 2, or a valid property name.");
	                TextUtil.pressEnter();
	            }
	        }
	    } 
	    else if (piece instanceof Enemy) {
	        int highestPrice = -1;
	        for (PropertyBlock property : ownedProperties) {
	            if (property.getPrice() > highestPrice) {
	                highestPrice = property.getPrice();
	                chosen = property;
	            }
	        }
	        return chosen;
	    }
	    return null;
	}
}
