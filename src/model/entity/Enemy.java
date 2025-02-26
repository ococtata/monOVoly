package model.entity;

import java.util.List;

import config.BoardConfig;
import config.ColorConfig;
import manager.CharacterLoaderManager;
import manager.GameManager;
import model.Position;
import model.block.PropertyBlock;
import model.entity.inventory.EnemyInventory;
import model.entity.inventory.PlayerInventory;
import model.gacha.character.BaseCharacter;
import utility.Random;

public class Enemy extends Entity implements Random {

	public Enemy(String name, int money) {
        super(name, money);
        setColor(ColorConfig.LIGHT_RED);
        setPiece(getColor() + BoardConfig.ENEMY_PIECE + ColorConfig.RESET);
        setName(ColorConfig.RED + getRandomName() + ColorConfig.RESET);
        setInventory(new EnemyInventory());

        equipRandomCharacter();
    }

	private String getRandomName() {
        List<Player> players = GameManager.getInstance().getPlayers();
        if (players == null || players.size() <= 1) {
            return "Enemy";
        }

        String currentPlayerName = stripAnsiCodes(GameManager.getInstance().getPlayer().getName());
        String randomName;
        do {
            int randomIndex = rand.nextInt(players.size());
            randomName = stripAnsiCodes(players.get(randomIndex).getName());
        } while (randomName.equals(currentPlayerName)); 

        return randomName;
    }

	private void equipRandomCharacter() {
        PlayerInventory playerInventory = (PlayerInventory) GameManager.getInstance().getPlayer().getInventory();
        List<BaseCharacter> playerCharacters = playerInventory.getCharacterList();
        List<BaseCharacter> allCharacters = CharacterLoaderManager.getInstance().getCharacterList();
        if (playerCharacters.size() > 0) {
            int randomIndex = rand.nextInt(allCharacters.size());
            BaseCharacter randomCharacter = allCharacters.get(randomIndex);
            int playerLevel = GameManager.getInstance().getPlayer().getEquippedCharacter().getCurrentLevel();
            int randomLevelGap = rand.nextInt(5) - 2;
            int randomLevel = Math.max(1, playerLevel + randomLevelGap);
            randomCharacter.setCurrentLevel(randomLevel);
            setEquippedCharacter(randomCharacter);
        }
    }

    @Override
    public PropertyBlock chooseProperty() {
        PropertyBlock chosen = null;
        int highestPrice = -1;

        for (PropertyBlock property : getOwnedProperties()) {
            if (property.getPrice() > highestPrice) {
                highestPrice = property.getPrice();
                chosen = property;
            }
        }
        return chosen;
    }

    @Override
    public Entity getEnemy() {
        return GameManager.getInstance().getPlayer();
    }
    
    private String stripAnsiCodes(String input) {
	    return input.replaceAll("\u001B\\[[;\\d]*m", "");
	}
}
