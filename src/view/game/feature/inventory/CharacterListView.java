package view.game.feature.inventory;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import config.ColorConfig;
import config.GeneralConfig;
import controller.game.feature.inventory.CharacterListViewController;
import manager.GameManager;
import model.entity.Entity;
import model.gacha.character.BaseCharacter;
import utility.Scanner;
import utility.TextUtil;
import view.BaseView;

public class CharacterListView extends BaseView implements Scanner {
	private CharacterListViewController characterListViewController;
    
	public CharacterListView() {
		this.characterListViewController = new CharacterListViewController(this);
	}
	
	@Override
    public void show() {
        List<BaseCharacter> characters = characterListViewController.getCharacters();

        while (true) {
            TextUtil.clearScreen();
            TextUtil.printHeader("Character List", GeneralConfig.WIDTH, GeneralConfig.HEIGHT);
            System.out.println();

            if (characters == null || characters.isEmpty()) {
                System.out.println(" You don't have any characters unlocked!\n");
                TextUtil.pressEnter();
                GameManager.getInstance().setCurrentView(getPreviousView());
                getPreviousView().show();
                return;
            }

            System.out.println(" Your Unlocked Characters:");

            showTable(characters);

            System.out.println(" 1. Equip Character");
            System.out.println(" 2. Back to Inventory");

            System.out.print(" >> ");

            int choice;
            try {
                choice = scan.nextInt();
                scan.nextLine();
            } 
            catch (InputMismatchException e) {
                scan.nextLine();
                System.out.println(" Invalid input. Please enter a number.");
                TextUtil.pressEnter();
                continue;
            }

            switch (choice) {
                case 1:
                    equipOrUnequipCharacter(characters);
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

    private void equipOrUnequipCharacter(List<BaseCharacter> characters) {
        System.out.print(" Enter the number of the character to equip/unequip [0 to go back]: ");
        int characterNumber;
        try {
            characterNumber = scan.nextInt();
            scan.nextLine();
        } catch (InputMismatchException e) {
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

        Entity player = GameManager.getInstance().getPlayer();
        BaseCharacter selectedCharacter = characters.get(characterNumber - 1);
        System.out.println();
        if (player.getEquippedCharacter() == selectedCharacter) {
            player.setEquippedCharacter(null);
            System.out.println(" " + selectedCharacter.getName() + " unequipped!");
        } 
        else {
            player.setEquippedCharacter(selectedCharacter);
            System.out.println(" " + selectedCharacter.getNameColor() +
                    selectedCharacter.getName() + ColorConfig.RESET
                    + " equipped!");
        }
        System.out.println();
        TextUtil.pressEnter();
    }

    private void showTable(List<BaseCharacter> characters) {
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
