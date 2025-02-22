package view.game.feature;

import java.util.ArrayList;
import java.util.List;

import config.ColorConfig;
import config.GachaConfig;
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
	        System.out.println(" No materials found.");
	        TextUtil.pressEnter();
	        return;
	    }

	    List<BaseCharacter> characters = tradeViewController.getCharactersForTrade();
	    if (characters.isEmpty()) {
	        System.out.println(" No characters available for trade.");
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
	            System.out.print("    Required Materials:\n");
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
        List<CharacterMaterial> materials = tradeViewController.getMaterials(player);
        List<BaseCharacter> characters = tradeViewController.getCharactersForTrade();

        while (true) {
            TextUtil.clearScreen();
            System.out.println(" Trade Materials for Higher Rarity:\n");

            for (int i = 0; i < characters.size(); i++) {
                BaseCharacter character = characters.get(i);
                String charColor = character.isTradable() ? character.getNameColor() : ColorConfig.RESET;
                System.out.printf(" %d. %s%s%s\n", i + 1, charColor, character.getName(), ColorConfig.RESET);
            }

            System.out.printf(" %d. Back\n", characters.size() + 1);
            System.out.print(" >> ");

            int charChoice;
            try {
                charChoice = scan.nextInt();
                scan.nextLine();
            } catch (Exception e) {
                scan.nextLine();
                System.out.println(" Invalid input.");
                TextUtil.pressEnter();
                continue;
            }

            if (charChoice >= 1 && charChoice <= characters.size()) {
                BaseCharacter selectedChar = characters.get(charChoice - 1);
                tradeForHigherRarityForCharacter(player, selectedChar, materials);
                return;
            } 
            else if (charChoice == characters.size() + 1) {
                return;
            } 
            else {
                System.out.println(" Invalid choice.");
                TextUtil.pressEnter();
            }
        }
    }

    private void tradeForHigherRarityForCharacter(Player player, BaseCharacter character, List<CharacterMaterial> materials) {
        while (true) {
            TextUtil.clearScreen();
            System.out.println(" Trade Materials for Higher Rarity for " + character.getName() + ":\n");

            List<CharacterMaterial> requiredMaterials = character.getRequiredMaterials();

            System.out.print(" Required Materials:\n");
            for (GachaConfig.Rarity rarity : GachaConfig.Rarity.values()) {
                CharacterMaterial reqMat = null;
                for (CharacterMaterial mat : requiredMaterials) {
                    if (mat.getRarity() == rarity) {
                        reqMat = mat;
                        break;
                    }
                }

                if (reqMat != null) {
                    int ownedAmount = 0;
                    for (CharacterMaterial mat : materials) {
                        if (mat.getId().equals(reqMat.getId())) {
                            ownedAmount = mat.getAmount();
                            break;
                        }
                    }
                    	
                    boolean hasEnough = ownedAmount >= reqMat.getAmount();
	                String statusColor = hasEnough ? ColorConfig.RESET : ColorConfig.RED;
	                String statusText = hasEnough ? "" : " (Not enough!)";

	                System.out.printf(" - %s%s%s (%dx/%dx)%s\n", reqMat.getRarity().getColor(), reqMat.getName(), ColorConfig.RESET, 
	                        ownedAmount, reqMat.getAmount(), statusColor + statusText + ColorConfig.RESET);
                }
            }
            System.out.println();
            TextUtil.printHorizontalBorder(WIDTH);
            System.out.println();
            System.out.println(" What do you want to trade?");

            List<String> tradeOptions = new ArrayList<String>();

            int maxNameLength = 0;
            for (CharacterMaterial mat : requiredMaterials) {
                maxNameLength = Math.max(maxNameLength, mat.getName().length());
            }

            for (int i = 0; i < GachaConfig.Rarity.values().length - 1; i++) {
                GachaConfig.Rarity fromRarity = GachaConfig.Rarity.values()[i];
                GachaConfig.Rarity toRarity = GachaConfig.Rarity.values()[i + 1];

                CharacterMaterial fromMat = null, toMat = null;
                for (CharacterMaterial mat : requiredMaterials) {
                    if (mat.getRarity() == fromRarity) fromMat = mat;
                    if (mat.getRarity() == toRarity) toMat = mat;
                }

                if (fromMat != null && toMat != null) {
                    int tradeAmount = getTradeAmount(fromRarity);

                    String fromName = fromMat.getRarity().getColor() + fromMat.getName() + ColorConfig.RESET;
                    String toName = toMat.getRarity().getColor() + toMat.getName() + ColorConfig.RESET;

                    String format = " %d. %-4d %-"+ (maxNameLength + 10) +"s -> 1 %-"+ (maxNameLength + 10) +"s";

                    String tradeOption = String.format(format, i + 1, tradeAmount, fromName, toName);
                    System.out.println(tradeOption);
                    tradeOptions.add(tradeOption);
                }
            }

            System.out.println(" " +(GachaConfig.Rarity.values().length) + ". Back");
            System.out.print(" >> ");

            int tradeChoice;
            try {
                tradeChoice = scan.nextInt();
                scan.nextLine();
            } 
            catch (Exception e) {
                scan.nextLine();
                System.out.println(" Invalid input.");
                TextUtil.pressEnter();
                continue;
            }

            if (tradeChoice >= 1 && tradeChoice <= tradeOptions.size()) {
                CharacterMaterial fromMat = null, toMat = null;

                for (CharacterMaterial mat : requiredMaterials) {
                    if (mat.getRarity() == GachaConfig.Rarity.values()[tradeChoice - 1]) {
                        fromMat = mat;
                    }
                    if (mat.getRarity() == GachaConfig.Rarity.values()[tradeChoice]) {
                        toMat = mat;
                    }
                }

                if (fromMat == null || toMat == null) {
                    System.out.println("Invalid trade selection.");
                    TextUtil.pressEnter();
                    continue;
                }

                int tradeAmount = getTradeAmount(fromMat.getRarity());

                if (tradeViewController.canTradeRarity(player, fromMat, tradeAmount)) {
                    tradeViewController.performTrade(player, fromMat, toMat, tradeAmount);
                    TextUtil.pressEnter();
                } else {
                    System.out.println(" Not enough materials to trade.");
                    TextUtil.pressEnter();
                }
            }
            else if (tradeChoice == GachaConfig.Rarity.values().length) {
                break; 
            } 
            else {
                System.out.println(" Invalid choice.");
                TextUtil.pressEnter();
            }
        }
    }

    private int getTradeAmount(GachaConfig.Rarity fromRarity) {
        switch (fromRarity) {
            case COMMON:
                return 20;
            case RARE:
                return 10;
            case EPIC:
                return 5;
            default:
                return 0; 
        }
    }
}
