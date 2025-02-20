package model.gacha;

import java.util.ArrayList;
import java.util.List;

import config.ColorConfig;
import config.GachaConfig;
import config.GachaConfig.Rarity;
import config.GeneralConfig;
import manager.GameManager;
import model.entity.Player;
import model.entity.inventory.PlayerInventory;
import model.gacha.material.CharacterMaterial;
import utility.Random;
import utility.Scanner;
import utility.TextUtil;

public class GachaCharacter implements IGacha, GetAllMaterial, Scanner, Random{
	
	private int num_of_rolls = GachaConfig.NUM_CARDS_PER_ROLL;
	public GachaCharacter() {
		
	}
	
	@Override
    public void roll() {
        Player player = GameManager.getInstance().getPlayer();
        int cost = GachaConfig.CHARACTER_GACHA_COST;
        if (player.getGems() >= cost) {
            player.reduceGems(cost);
            List<CharacterMaterial> materials = generateMaterials();
            animateMaterialReveal(materials, (PlayerInventory) player.getInventory());
        } else {
            System.out.println(" Not enough gems!");
        }
        System.out.println();
        TextUtil.pressEnter();
    }

	private List<CharacterMaterial> generateMaterials() {
        List<CharacterMaterial> selectedMaterials = new ArrayList<>();

        for (int i = 0; i < num_of_rolls; i++) {
            Rarity rarity = getRandomRarity();
            CharacterMaterial material = getRandomMaterialForRarity(rarity);
            selectedMaterials.add(material);
        }

        return selectedMaterials;
    }

    
    private CharacterMaterial getRandomMaterialForRarity(Rarity rarity) {
        List<CharacterMaterial> allMaterials = getAllMaterial();
        List<CharacterMaterial> materialsForRarity = new ArrayList<>();

        for (CharacterMaterial material : allMaterials) {
            if (material.getRarity() == rarity) {
                materialsForRarity.add(material);
            }
        }

        if (materialsForRarity.isEmpty()) {
            return null;
        }

        int randomIndex = rand.nextInt(materialsForRarity.size());
        return materialsForRarity.get(randomIndex);
    }

    private Rarity getRandomRarity() {
        double chance = rand.nextDouble();
        if (chance <= GachaConfig.LEGENDARY_CHANCE) {
            return Rarity.LEGENDARY;
        } 
        else if (chance <= GachaConfig.EPIC_CHANCE + GachaConfig.LEGENDARY_CHANCE) {
            return Rarity.EPIC;
        } 
        else if (chance <= GachaConfig.RARE_CHANCE + GachaConfig.EPIC_CHANCE + GachaConfig.LEGENDARY_CHANCE) {
            return Rarity.RARE;
        } 
        else {
            return Rarity.COMMON;
        }
    }
	
    private void animateMaterialReveal(List<CharacterMaterial> materials, PlayerInventory inventory) {
        System.out.println("\n Drawing your cards...\n");

        printHiddenCards(materials.size());

        for (int i = 0; i < materials.size(); i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            revealCard(materials, i);
        }

        System.out.println("\n You got:");

        List<String> processedMaterials = new ArrayList<>();
        for (CharacterMaterial material : materials) {
            String name = material.getName();
            int count = 1;

            if (!processedMaterials.contains(name)) {
                for (CharacterMaterial otherMaterial : materials) {
                    if (otherMaterial != material && otherMaterial.getName().equals(name)) {
                        count++;
                    }
                }
                System.out.printf(" - %dx %-40s    %-10s %s - for %-20s%s\n",
                	    count,
                	    material.getRarity().getColor() + name,
                	    material.getRarity(), 
                	    ColorConfig.RESET, 
                	    material.getCharacter().getNameColor() + material.getForCard(),
                	    ColorConfig.RESET
                	);
                
                inventory.addMaterial(material);
                processedMaterials.add(name);
            }
        }
        
        System.out.println("\n All materials added to inventory!");
        
        for(CharacterMaterial material : inventory.getMaterialList()) {
        	System.out.printf(" %s x%d\n", material.getName(), material.getAmount());
        }
    }
    
    private void printHiddenCards(int num) {
        String[] cardHidden = {
            "  -----------------  ",
            " |                 | ",
            " |                 | ",
            " |        ?        | ",
            " |                 | ",
            " |                 | ",
            "  -----------------  "
        };

        for (String line : cardHidden) {
            for (int i = 0; i < num; i++) {
                System.out.print("  " + line + "  ");
            }
            System.out.println();
        }
    }

    private void revealCard(List<CharacterMaterial> materials, int revealIndex) {
        TextUtil.clearScreen();
        System.out.println("\n Revealing card...\n");

        String[][] cardRevealedArray = new String[materials.size()][];

        for (int i = 0; i < materials.size(); i++) {
            CharacterMaterial material = materials.get(i);
            String color = material.getRarity().getColor();
            String rarityLabel = material.getRarity().toString().substring(0, 1);

            if (i <= revealIndex) {
                cardRevealedArray[i] = new String[]{
                    color + "  -----------------  " + ColorConfig.RESET,
                    color + " |                 | " + ColorConfig.RESET,
                    color + " |                 | " + ColorConfig.RESET,
                    color + " |        " + rarityLabel + "        | " + ColorConfig.RESET,
                    color + " |                 | " + ColorConfig.RESET,
                    color + " |                 | " + ColorConfig.RESET,
                    color + "  -----------------  " + ColorConfig.RESET
                };
            } else {
                cardRevealedArray[i] = new String[] {
                        "  -----------------  ",
                        " |                 | ",
                        " |                 | ",
                        " |        ?        | ",
                        " |                 | ",
                        " |                 | ",
                        "  -----------------  "
                    };
            }
        }

        for (int lineIndex = 0; lineIndex < cardRevealedArray[0].length; lineIndex++) {
            for (String[] card : cardRevealedArray) {
                System.out.print("  " + card[lineIndex] + "  ");
            }
            System.out.println();
        }
    }
}
