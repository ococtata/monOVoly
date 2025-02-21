package controller.game.feature;

import java.util.ArrayList;
import java.util.List;

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

    public boolean canTradeRarity(Player player, String fromRarity, String toRarity, int amount) {
        List<CharacterMaterial> materials = getMaterials(player);
        PlayerInventory inventory = (PlayerInventory) player.getInventory();
        int fromCount = 0;

        for (CharacterMaterial material : materials) {
            if (material.getRarity().toString().equals(fromRarity)) { 
                fromCount += inventory.findMaterialById(material.getId()).getAmount();
            }
        }

        return fromCount >= amount;
    }

    public void performTradeRarity(Player player, String fromRarity, String toRarity, int amount) {
        List<CharacterMaterial> materials = getMaterials(player);
        List<CharacterMaterial> toRemove = new ArrayList<CharacterMaterial>();
        PlayerInventory inventory = (PlayerInventory) player.getInventory();
        
        int count = 0;

        for (CharacterMaterial material : materials) {
            if (material.getRarity().toString().equals(fromRarity) && count < amount) { 
                int amountToRemove = Math.min(inventory.findMaterialById(material.getId()).getAmount(), amount - count);
                inventory.decreaseMaterial(material, amountToRemove);
                count += amountToRemove;
                toRemove.add(material);
            }
        }

        CharacterMaterial newMaterial = MaterialLoaderManager.getInstance().getMaterialById(toRarity);
        if (newMaterial != null) {
        	inventory.addMaterial(newMaterial.getId(), 1);
        }
    }
}
