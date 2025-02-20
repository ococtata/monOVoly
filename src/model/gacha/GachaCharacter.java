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
    }
	
	 private void animateMaterialReveal(List<CharacterMaterial> materials) {
        System.out.println(" You received:");

        for (CharacterMaterial material : materials) {
            drawCardBox(material.getRarity());

            try {
                Thread.sleep(2000);
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
