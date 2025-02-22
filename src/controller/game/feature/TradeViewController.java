package controller.game.feature;

import java.util.ArrayList;
import java.util.List;

import config.ColorConfig;
import manager.CharacterLoaderManager;
import manager.GameManager;
import manager.MaterialLoaderManager;
import model.entity.Player;
import model.entity.inventory.PlayerInventory;
import model.gacha.character.BaseCharacter;
import model.gacha.material.CharacterMaterial;
import view.game.feature.TradeView;

public class TradeViewController {
	private TradeView tradeView;
	public TradeViewController(TradeView tradeView) {
		this.tradeView = tradeView;
	}
	
	public List<CharacterMaterial> getMaterials(Player player) {
        PlayerInventory inventory = (PlayerInventory) player.getInventory();
        if (inventory == null) return new ArrayList<>();
        return inventory.getMaterialList();
    }

    public List<BaseCharacter> getCharactersForTrade() {
        List<BaseCharacter> allCharacters = CharacterLoaderManager.getInstance().getCharacterList();
        List<BaseCharacter> tradableCharacters = new ArrayList<BaseCharacter>();
        if (allCharacters != null) {
            for (BaseCharacter character : allCharacters) {
                if (character.isTradable()) {
                    tradableCharacters.add(character);
                }
            }
        }
        return tradableCharacters;
    }

    public boolean canTradeForCharacter(Player player, BaseCharacter character) {
        List<CharacterMaterial> materials = getMaterials(player);
        PlayerInventory inventory = (PlayerInventory) player.getInventory();

        for (CharacterMaterial requiredMaterial : character.getRequiredMaterials()) {
            CharacterMaterial playerMaterial = null;
            for (CharacterMaterial mat : materials) {
                if (mat.getId().equals(requiredMaterial.getId())) {
                    playerMaterial = mat;
                    break;
                }
            }
            if (playerMaterial == null || 
            		inventory.findMaterialById(playerMaterial.getId()).getAmount() < requiredMaterial.getAmount()) {
                return false;
            }
        }
        return true;
    }

    public void performTradeForCharacter(Player player, BaseCharacter character) {
        List<CharacterMaterial> materials = getMaterials(player);
        PlayerInventory inventory = (PlayerInventory) player.getInventory();
        
        for (CharacterMaterial requiredMaterial : character.getRequiredMaterials()) {
            for (CharacterMaterial mat : materials) {
                if (mat.getId().equals(requiredMaterial.getId())) {
                	inventory.decreaseMaterial(mat, requiredMaterial.getAmount());
                    break;
                }
            }
        }
        inventory.addCharacter(character);
    }

    public boolean canTradeRarity(Player player, CharacterMaterial fromMat, int amount) {
        if (fromMat == null || player == null) {
            return false;
        }

        PlayerInventory inventory = (PlayerInventory) player.getInventory();
        if (inventory == null) {
            return false;
        }

        int fromCount = inventory.findMaterialById(fromMat.getId()).getAmount();
        return fromCount >= amount;
    }

    public void performTrade(Player player, CharacterMaterial fromMat, CharacterMaterial toMat, int amount) {
        List<CharacterMaterial> materials = getMaterials(player);
        PlayerInventory inventory = (PlayerInventory) player.getInventory();

        if (inventory == null || fromMat == null || toMat == null) {
            System.out.println("I nvalid trade parameters.");
            return;
        }

        int totalAvailable = inventory.findMaterialById(fromMat.getId()).getAmount();
        
        if (totalAvailable < amount) {
            System.out.println(" Not enough materials to trade.");
            return;
        }

        inventory.decreaseMaterial(fromMat, amount);

        inventory.addMaterial(toMat.getId(), 1);

        System.out.println(" Successfully traded " + amount + " " 
        	    + fromMat.getRarity().getColor() + fromMat.getName() + ColorConfig.RESET 
        	    + " for 1 " 
        	    + toMat.getRarity().getColor() + toMat.getName() + ColorConfig.RESET);    
        }
}
