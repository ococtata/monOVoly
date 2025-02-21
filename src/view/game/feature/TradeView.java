package view.game.feature;

import java.util.List;

import config.ColorConfig;
import config.GeneralConfig;
import controller.game.feature.TradeViewController;
import manager.GameManager;
import model.entity.Player;
import model.gacha.character.BaseCharacter;
import model.gacha.material.CharacterMaterial;
import utility.Scanner;
import utility.TextUtil;
import view.BaseView;

public class TradeView extends BaseView implements Scanner {
	private TradeViewController tradeViewController;
	public TradeView() {
		this.tradeViewController = new TradeViewController(this);
	}

	@Override
    public void show() {
        Player player = GameManager.getInstance().getPlayer();
        if (player == null) return;

        while (true) {
            TextUtil.clearScreen();
            TextUtil.printHeader("Material Trade", GeneralConfig.WIDTH, GeneralConfig.HEIGHT);
            System.out.println();
            System.out.println(" 1. Trade Materials for Character");
            System.out.println(" 2. Trade Materials for Higher Rarity");
            System.out.println(" 3. Back");
            System.out.print(" >> ");

            int choice;
            try {
                choice = scan.nextInt();
                scan.nextLine();
            } catch (Exception e) {
                scan.nextLine();
                System.out.println(" Invalid input.");
                TextUtil.pressEnter();
                continue;
            }

            switch (choice) {
                case 1:
                    tradeForCharacter(player);
                    break;
                case 2:
                    tradeForHigherRarity(player);
                    break;
                case 3:
                    GameManager.getInstance().setCurrentView(getPreviousView());
                    getPreviousView().show();
                    return;
                default:
                    System.out.println(" Invalid choice.");
                    TextUtil.pressEnter();
            }
        }
    }

	private void tradeForCharacter(Player player) {
	    List<CharacterMaterial> materials = tradeViewController.getMaterials(player);
	    if (materials == null || materials.isEmpty()) {
	        System.out.println("No materials found.");
	        TextUtil.pressEnter();
	        return;
	    }

	    List<BaseCharacter> characters = tradeViewController.getCharactersForTrade();
	    if (characters.isEmpty()) {
	        System.out.println("No characters available for trade.");
	        TextUtil.pressEnter();
	        return;
	    }

	    final int PAGE_SIZE = 3;
	    int currentPage = 0;
	    int totalPages = (int) Math.ceil((double) characters.size() / PAGE_SIZE);

	    while (true) {
	        TextUtil.clearScreen();
	        System.out.println(" Trade Materials for Character:\n");

	        int start = currentPage * PAGE_SIZE;
	        int end = Math.min(start + PAGE_SIZE, characters.size());

	        for (int i = start; i < end; i++) {
	            BaseCharacter character = characters.get(i);
	            String charColor = character.isTradable() ? character.getNameColor() : ColorConfig.RESET;
	            System.out.printf(" %d. %s%s%s\n", i + 1, charColor, character.getName(), ColorConfig.RESET);

	            List<CharacterMaterial> requiredMaterials = character.getRequiredMaterials();
	            System.out.print("    Required:\n");
	            for (CharacterMaterial reqMat : requiredMaterials) {
	                int ownedAmount = 0;

	                for (CharacterMaterial playerMat : materials) {
	                    if (playerMat.getName().equals(reqMat.getName())) {
	                        ownedAmount = playerMat.getAmount();
	                        break;
	                    }
	                }

	                boolean hasEnough = ownedAmount >= reqMat.getAmount();
	                String statusColor = hasEnough ? ColorConfig.RESET : ColorConfig.RED;
	                String statusText = hasEnough ? "" : " (Not enough!)";

	                System.out.printf("    - %s%s%s (%dx/%dx)%s\n", reqMat.getRarity().getColor(), reqMat.getName(), ColorConfig.RESET, 
	                        ownedAmount, reqMat.getAmount(), statusColor + statusText + ColorConfig.RESET);
	            }
	            System.out.println("\n");
	        }
	        TextUtil.printHorizontalBorder(50);
	        System.out.println();

	        System.out.println(" 1. Previous Page");
	        System.out.println(" 2. Next Page");
	        System.out.println(" 3. Back");
	        System.out.print(" Enter Character Name to Trade: ");

	        String input = scan.nextLine().trim();

	        if (input.equals("1")) {
	        	if (currentPage > 0) {
	        		currentPage--;
	        	} 
	        	else {
	        		System.out.println(" Already on the first page.");
	        		TextUtil.pressEnter();
	        	}
	        } 
	        else if (input.equals("2")) {
	            if (currentPage < totalPages - 1) {
	            	currentPage++;
	            } 
	            else {
	            	System.out.println(" Already on the last page.");
	            	TextUtil.pressEnter();
	            }
	        } 
	        else if (input.equals("3")) {
	            return; 
	        } 
	        else {
	            BaseCharacter selectedChar = null;
	            for (BaseCharacter character : characters) {
	                if (character.getName().equalsIgnoreCase(input)) {
	                    selectedChar = character;
	                    break;
	                }
	            }

	            if (selectedChar == null) {
	                System.out.println(" Invalid character name.");
	                TextUtil.pressEnter();
	                continue;
	            }

	            if (tradeViewController.canTradeForCharacter(player, selectedChar)) {
	                tradeViewController.performTradeForCharacter(player, selectedChar);
	                System.out.printf(" Successfully traded for %s%s%s!\n", selectedChar.getNameColor(),
	                        selectedChar.getName(), ColorConfig.RESET);
	                TextUtil.pressEnter();
	                return;
	            } 
	            else {
	                System.out.println(" You don't have enough materials for this character.");
	                TextUtil.pressEnter();
	            }
	        }
	    }
	}

    private void tradeForHigherRarity(Player player) {
        while (true) {
            TextUtil.clearScreen();
            System.out.println(" Trade Materials for Higher Rarity:");
            
            System.out.printf(" 1. 20 %sCommon%s ->  1 %sRare%s\n", ColorConfig.LIGHT_GREY, ColorConfig.RESET, ColorConfig.BLUE, ColorConfig.RESET);
            System.out.printf(" 2. 10 %sRare%s -> 1 %sEpic%s\n", ColorConfig.BLUE, ColorConfig.RESET, ColorConfig.PURPLE, ColorConfig.RESET);
            System.out.printf(" 3. 5 %sEpic%s -> 1 %sLegendary%s\n", ColorConfig.PURPLE, ColorConfig.RESET, ColorConfig.GOLD, ColorConfig.RESET);
            System.out.println(" 4. Back");
            System.out.print(" >> ");

            int choice;
            try {
                choice = scan.nextInt();
                scan.nextLine();
            } catch (Exception e) {
                scan.nextLine();
                System.out.println(" Invalid input.");
                TextUtil.pressEnter();
                continue;
            }

            switch (choice) {
                case 1:
                    tradeRarity(player, "COMMON", "RARE", 20);
                    break;
                case 2:
                    tradeRarity(player, "RARE", "EPIC", 10);
                    break;
                case 3:
                    tradeRarity(player, "EPIC", "LEGENDARY", 5);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice.");
                    TextUtil.pressEnter();
            }
        }
    }

    private void tradeRarity(Player player, String fromRarity, String toRarity, int amount) {
        if (tradeViewController.canTradeRarity(player, fromRarity, toRarity, amount)) {
            tradeViewController.performTradeRarity(player, fromRarity, toRarity, amount);
            System.out.println("Successfully traded!");
        } 
        else {
            System.out.println("Not enough materials to trade.");
        }
        TextUtil.pressEnter();
    }

}
