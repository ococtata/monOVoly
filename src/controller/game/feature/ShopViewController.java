package controller.game.feature;

import java.util.List;

import manager.GameManager;
import model.entity.Player;
import model.entity.inventory.PlayerInventory;
import model.gacha.character.BaseCharacter;
import view.game.feature.ShopView;

public class ShopViewController {
	private ShopView shopView;
    private List<BaseCharacter> characters;

    public ShopViewController(ShopView shopView) {
        updateCharacters();
        this.shopView = shopView;
    }

    public void updateCharacters() {
        PlayerInventory inventory = (PlayerInventory) GameManager.getInstance().getPlayer().getInventory();
        this.characters = inventory.getCharacterList();
    }

    public List<BaseCharacter> getCharacters() {
        return characters;
    }

    public boolean levelUpCharacter(BaseCharacter character, int levelUpCost) {
        Player player = GameManager.getInstance().getPlayer();
        if (player.getGems() >= levelUpCost) {
            player.setGems(player.getGems() - levelUpCost);
            character.setCurrentLevel(character.getCurrentLevel() + 1);
            return true;
        }
        return false;
    }
}
