package view.game.feature;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import config.ColorConfig;
import config.GameConfig;
import config.GeneralConfig;
import controller.game.feature.ShopViewController;
import manager.GameManager;
import model.entity.Player;
import model.gacha.character.BaseCharacter;
import utility.TextUtil;
import view.BaseView;
import view.PlayerGUI;

public class ShopView extends BaseView implements PlayerGUI {
	private ShopViewController shopViewController;
	private Player player;
	
	public ShopView() {
		this.shopViewController = new ShopViewController(this);
		this.player = GameManager.getInstance().getPlayer();
	}

	@Override
    public void show() {
        while (true) {
            List<BaseCharacter> characters = shopViewController.getCharacters();

            TextUtil.clearScreen();
            TextUtil.printHeader("Shop", WIDTH, HEIGHT);
            printGachaTopBar();
            TextUtil.printHorizontalBorder(GeneralConfig.WIDTH);

            if (characters == null || characters.isEmpty()) {
                System.out.println(" You don't have any characters unlocked!\n");
                TextUtil.pressEnter();
                GameManager.getInstance().setCurrentView(getPreviousView());
                getPreviousView().show();
                return;
            }

            System.out.println(" 1. Level Up Character");
            System.out.println(" 2. Back");
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
                    levelUpCharacter(characters);
                    break;
                case 2:
                    GameManager.getInstance().setCurrentView(getPreviousView());
                    getPreviousView().show();
                    return;
                default:
                    System.out.println(" Invalid choice.");
                    TextUtil.pressEnter();
            }
        }
    }

    private void levelUpCharacter(List<BaseCharacter> characters) {
        TextUtil.clearScreen();
    	if (characters.isEmpty()) {
            System.out.println(" You don't have any characters!");
            TextUtil.pressEnter();
            return;
        }
    	
        System.out.println(" Level Up Character\n");
        TextUtil.printHorizontalBorder(GeneralConfig.WIDTH);
        printGachaTopBar();
        TextUtil.printHorizontalBorder(GeneralConfig.WIDTH);
        showTable(characters);

        System.out.print(" Enter the number of the character to equip/unequip [0 to go back]: ");
        int characterNumber;
        try {
            characterNumber = scan.nextInt();
            scan.nextLine();
        } 
        catch (InputMismatchException e) {
            scan.nextLine();
            System.out.println(" Invalid input. Please enter a number.");
            TextUtil.pressEnter();
            return;
        }

        if (characterNumber == 0) {
            return;
        }

        if (characterNumber < 1 || characterNumber > characters.size()) {
            System.out.println(" Invalid character number.");
            TextUtil.pressEnter();
            return;
        }

        BaseCharacter character = characters.get(characterNumber - 1);

        if (character.getCurrentLevel() >= character.getMaxLevel()) {
            System.out.println(" " + character.getName() + " is already at max level.");
            System.out.println();
            TextUtil.pressEnter();
            return;
        }

        int baseLevelUpCost = GameConfig.BASE_CHARACTER_LEVEL_UP_COST;
        int levelUpCostIncrement = GameConfig.CHARACTER_LEVEL_UP_INCREMENT;
        int levelUpCost = baseLevelUpCost + (character.getCurrentLevel() * levelUpCostIncrement);
        
        System.out.println();
        if (player.getGems() >= levelUpCost) {
            player.setGems(player.getGems() - levelUpCost);
            character.setCurrentLevel(character.getCurrentLevel() + 1);
            System.out.println(" " + character.getNameColor() + character.getName() + ColorConfig.RESET + " leveled up to level " + ColorConfig.GOLD + character.getCurrentLevel() + ColorConfig.RESET + "!");
            System.out.println(" Cost: " + ColorConfig.CYAN + levelUpCost + ColorConfig.RESET + " gems.");
            System.out.println();
            TextUtil.pressEnter();
        } 
        else {
            System.out.println(" You don't have enough gems to level up " + character.getNameColor() + character.getName() + ColorConfig.RESET + ".");
            System.out.println(" Cost: " + ColorConfig.CYAN + levelUpCost + ColorConfig.RESET + " gems.");
            System.out.println();
            TextUtil.pressEnter();
        }
    }

    private void showTable(List<BaseCharacter> characters) {
        int maxNoLength = "No.".length();
        int maxNameLength = "Name".length();
        int maxLevelLength = "Level".length();
        int maxTitleLength = "Title".length();
        int maxSkillNameLength = "Skill Name".length();
        int maxSkillDescLength = "Skill Desc".length();
        int maxChanceLength = "Skill Chance".length();

        List<String> displayNames = new ArrayList<>();
        List<String> rawNames = new ArrayList<>();

        for (int i = 0; i < characters.size(); i++) {
            BaseCharacter character = characters.get(i);
            String nameColor = character.getNameColor();
            
            String equippedStatus = "";
            String equippedStatusRaw = "";
            if (GameManager.getInstance().getPlayer().getEquippedCharacter() == character) {
                equippedStatus = ColorConfig.GREEN + " (Equipped)" + ColorConfig.RESET;
                equippedStatusRaw = " (Equipped)";
            }
            
            String displayName = nameColor + character.getName() + ColorConfig.RESET + equippedStatus;
            String rawName = character.getName() + equippedStatusRaw;
            
            displayNames.add(displayName);
            rawNames.add(rawName);

            maxLevelLength = Math.max(maxLevelLength, String.valueOf(character.getCurrentLevel()).length());
            maxTitleLength = Math.max(maxTitleLength, character.getTitle().length());
            maxSkillNameLength = Math.max(maxSkillNameLength, character.getSkillName().length());
            maxSkillDescLength = Math.max(maxSkillDescLength, character.getSkillDesc().length());

            int baseChance = character.getBaseSkillChance();
            int levelBonus = character.getCurrentLevel() - 1;
            int totalChance = baseChance + levelBonus;
            String chanceString = baseChance + "% + " + levelBonus + "% = " + totalChance + "%";

            maxChanceLength = Math.max(maxChanceLength, chanceString.length());
        }

        for (String rawName : rawNames) {
            maxNameLength = Math.max(maxNameLength, rawName.length());
        }

        int borderLength = 27 + maxNameLength + maxLevelLength + maxTitleLength + maxSkillNameLength + maxSkillDescLength + maxChanceLength;

        TextUtil.printHorizontalBorder(borderLength);
        System.out.printf(" | %-5s | %-" + maxNameLength + "s | %-" + maxLevelLength + "s | %-" + maxTitleLength + "s | %-" + maxSkillNameLength + "s | %-" + maxSkillDescLength + "s | %-" + maxChanceLength + "s |\n",
                "No.", "Name", "Level", "Title", "Skill Name", "Skill Desc", "Skill Chance");
        TextUtil.printHorizontalBorder(borderLength);

        for (int i = 0; i < characters.size(); i++) {
            BaseCharacter character = characters.get(i);

            int baseChance = character.getBaseSkillChance();
            int levelBonus = character.getCurrentLevel() - 1;
            int totalChance = baseChance + levelBonus;
            String chanceString = baseChance + "% + " + levelBonus + "% = " + totalChance + "%";

            String formattedName = String.format("%-" + maxNameLength + "s", rawNames.get(i));
            String equippedStatus = (GameManager.getInstance().getPlayer().getEquippedCharacter() == character)
            	    ? ColorConfig.GREEN + " (Equipped)" + ColorConfig.RESET
            	    : "";

            String coloredName = character.getNameColor() + character.getName() + ColorConfig.RESET + equippedStatus
                    + " ".repeat(maxNameLength - rawNames.get(i).length());
            
            System.out.printf(" | %-5s | %s | %-" + maxLevelLength + "d | %-" + maxTitleLength + "s | %-" + maxSkillNameLength + "s | %-" + maxSkillDescLength + "s | %-" + maxChanceLength + "s |\n",
                    i + 1, coloredName, character.getCurrentLevel(),
                    character.getTitle(), character.getSkillName(), character.getSkillDesc(), chanceString);
            TextUtil.printHorizontalBorder(borderLength);
        }
    }
}
