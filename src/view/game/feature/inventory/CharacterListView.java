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
import model.gacha.material.CharacterMaterial;
import utility.Scanner;
import utility.TextUtil;
import view.BaseView;

public class CharacterListView extends BaseView implements Scanner {
	private CharacterListViewController characterListViewController;
	private int borderLength = 104;
	
	private int sortBy = 1;
    private boolean ascending = true;
    
	public CharacterListView() {
		this.characterListViewController = new CharacterListViewController(this);
	}
	
	@Override
    public void show() {
        List<BaseCharacter> characters = characterListViewController.getCharacters();
        int pageSize = 10;
        int currentPage = 0;

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

            int startIndex = currentPage * pageSize;
            int endIndex = Math.min((currentPage + 1) * pageSize, characters.size());

            System.out.println(" Your Unlocked Characters (Page " + (currentPage + 1) + " of " + (int) Math.ceil((double) characters.size() / pageSize) + "):");

            showTable(startIndex, endIndex, characters);

            if (endIndex < characters.size()) {
                System.out.println(" 1. Previous Page");
                System.out.println(" 2. Next Page");
                System.out.println(" 3. Equip Character");
                System.out.println(" 4. Back to Inventory");
            } else if (currentPage > 0) {
                System.out.println(" 1. Previous Page");
                System.out.println(" 3. Equip Character");
                System.out.println(" 4. Back to Inventory");
            } else {
                System.out.println(" 3. Equip Character");
                System.out.println(" 4. Back to Inventory");
            }

            System.out.print(" >> ");

            int choice;
            try {
                choice = scan.nextInt();
                scan.nextLine();
            } catch (InputMismatchException e) {
                scan.nextLine();
                System.out.println(" Invalid input. Please enter a number.");
                TextUtil.pressEnter();
                continue;
            }

            switch (choice) {
                case 1:
                    if (currentPage > 0) {
                        currentPage--;
                    } else {
                        System.out.println(" You are already on the first page.");
                        TextUtil.pressEnter();
                    }
                    break;
                case 2:
                    if (endIndex < characters.size()) {
                        currentPage++;
                    } else {
                        System.out.println(" You are already on the last page.");
                        TextUtil.pressEnter();
                    }
                    break;
                case 3:
                	equipOrUnequipCharacter(characters, startIndex, endIndex);
                    break;
                case 4:
                    GameManager.getInstance().setCurrentView(getPreviousView());
                    getPreviousView().show();
                    return;
                default:
                    System.out.println(" Invalid choice.");
                    TextUtil.pressEnter();
            }
        }
    }

	private void equipOrUnequipCharacter(List<BaseCharacter> characters, int startIndex, int endIndex) {
        System.out.print(" Enter the number of the character to equip/unequip: ");
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

        if (characterNumber < startIndex + 1 || characterNumber > endIndex) {
            System.out.println(" Invalid character number.");
            TextUtil.pressEnter();
            return;
        }

        Entity player = GameManager.getInstance().getPlayer();
        BaseCharacter selectedCharacter = characters.get(characterNumber - 1);

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

	private void showTable(int startIndex, int endIndex, List<BaseCharacter> characters) {
	    int maxNoLength = "No.".length();
	    int maxNameLength = "Name".length();
	    int maxTitleLength = "Title".length();
	    int maxSkillNameLength = "Skill Name".length();
	    int maxSkillDescLength = "Skill Desc".length();

	    List<String> displayNames = new ArrayList<>();

	    for (int i = startIndex; i < endIndex; i++) {
	        BaseCharacter character = characters.get(i);
	        String nameColor = character.getNameColor();
	        String equippedStatus = "";
	        if (GameManager.getInstance().getPlayer().getEquippedCharacter() == character) {
	            equippedStatus = ColorConfig.GREEN + " (Equipped)" + ColorConfig.RESET;
	        }
	        String displayName = nameColor + character.getName() + ColorConfig.RESET + equippedStatus;
	        displayNames.add(displayName);

	        maxTitleLength = Math.max(maxTitleLength, character.getTitle().length());
	        maxSkillNameLength = Math.max(maxSkillNameLength, character.getSkillName().length());
	        maxSkillDescLength = Math.max(maxSkillDescLength, character.getSkillDesc().length());
	    }

	    for (String displayName : displayNames) {
	        String cleanName = displayName.replaceAll("\\e\\[[\\d;]*m", "");
	        maxNameLength = Math.max(maxNameLength, cleanName.length());
	    }

	    int borderLength = 21 + maxNameLength + maxTitleLength + maxSkillNameLength + maxSkillDescLength;

	    TextUtil.printHorizontalBorder(borderLength);
	    System.out.printf(" | %-5s | %-" + maxNameLength + "s | %-" + maxTitleLength + "s | %-" + maxSkillNameLength + "s | %-" + maxSkillDescLength + "s |\n",
	            "No.", "Name", "Title", "Skill Name", "Skill Desc");
	    TextUtil.printHorizontalBorder(borderLength);

	    for (int i = startIndex; i < endIndex; i++) {
	        BaseCharacter character = characters.get(i);
	        System.out.printf(" | %-5s | %-" + maxNameLength + "s | %-" + maxTitleLength + "s | %-" + maxSkillNameLength + "s | %-" + maxSkillDescLength + "s |\n",
	                i + 1, displayNames.get(i - startIndex),
	                character.getTitle(), character.getSkillName(), character.getSkillDesc());
	        TextUtil.printHorizontalBorder(borderLength);
	    }
	}
}
