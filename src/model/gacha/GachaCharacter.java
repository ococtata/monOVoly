package model.gacha;

import java.util.List;

import config.ColorConfig;
import config.GachaConfig;
import config.GachaConfig.Rarity;
import config.GeneralConfig;
import manager.GameManager;
import model.entity.Player;
import model.gacha.material.CharacterMaterial;
import utility.Scanner;
import utility.TextUtil;

public class GachaCharacter implements IGacha, GetAllMaterial, Scanner{
	
	public GachaCharacter() {
		
	}
	
	@Override
    public void showMenu() {
        while (true) {
        	TextUtil.clearScreen();
            TextUtil.printHeader("CHARACTER GACHA", GeneralConfig.WIDTH, GeneralConfig.HEIGHT);
            System.out.println(" 1. Roll");
            System.out.println(" 2. Go Back");
            System.out.println();
            System.out.print(" >> "); 

            String input = scan.nextLine(); 

            try {
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        roll();
                        return;
                    case 2:
                        return;
                    default:
                        System.out.println(" Input must be 1 or 2!");
                        TextUtil.pressEnter();
                        break; 
                }
            } catch (NumberFormatException e) {
                System.out.println(" Input must be an integer!");
                TextUtil.pressEnter();
            }
        }
    }
	
	@Override
    public void roll() {
		Player player = GameManager.getInstance().getPlayer();
		int cost = GachaConfig.CHARACTER_GACHA_COST;
        if (player.getGems() >= cost) {
            player.reduceGems(cost);
            List<CharacterMaterial> materials = getAllMaterial();
            animateMaterialReveal(materials);
        } else {
            System.out.println(" Not enough gems!");
            TextUtil.pressEnter();
        }
        showMenu();
    }
	
	 private void animateMaterialReveal(List<CharacterMaterial> materials) {
        System.out.println(" You received:");

        for (CharacterMaterial material : materials) {
            drawCardBox(material.getRarity());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        TextUtil.pressEnter();
    }

	 private void drawCardBox(Rarity rarity) {
        String color = rarity.getColor();
        String rarityLabel = rarity.toString().substring(0, 1);

        System.out.println(color + " _________");
        System.out.println("|         |");
        System.out.println("|   " + rarityLabel + "   |");
        System.out.println("|_________|" + ColorConfig.RESET);
        System.out.println();
    }
}
