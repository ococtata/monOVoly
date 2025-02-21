package manager;

import java.util.ArrayList;
import java.util.List;

import config.CredentialsConfig;
import config.DataConfig;
import config.StatDataConfig;
import model.entity.Player;
import model.entity.inventory.PlayerInventory;
import model.gacha.character.BaseCharacter;
import model.gacha.material.CharacterMaterial;
import utility.FileUtil;

public class SaveLoadManager {
	private static SaveLoadManager instance;
	
	private SaveLoadManager() {
	}
	
	public static SaveLoadManager getInstance() {
		if(instance == null) instance = new SaveLoadManager();
		return instance;
	}
	
	 public void saveAll() {
	        List<Player> players = GameManager.getInstance().getPlayers();
	        savePlayers(players);
	        savePlayerStats(players);
	        for (Player player : players) {
	            savePlayerInventory(player);
	        }
	    }

	    public void loadAll() {
	        List<Player> players = loadPlayers();
	        GameManager.getInstance().setPlayers(players); 

	        loadPlayerStats(players);
	        for (Player player : players) {
	            loadPlayerMaterials(player);
	            loadPlayerCharacters(player);
	        }
	    }
	
	private List<Player> loadPlayers() {
        List<Player> players = new ArrayList<Player>();
        List<String> lines = FileUtil.readFile(DataConfig.FILE_DATA_USER);

        for (String line : lines) {
            String[] parts = line.split("#");

            String playerId = parts[CredentialsConfig.ID_INDEX];
            String username = parts[CredentialsConfig.USERNAME_INDEX];
            String email = parts[CredentialsConfig.EMAIL_INDEX];
            String password = parts[CredentialsConfig.PASSWORD_INDEX];

            Player player = new Player(playerId, username, email, password, 0, 1, 0, 0, 0);
            players.add(player);
        }
        return players;
    }

	private void savePlayers(List<Player> players) {
        List<String> lines = new ArrayList<String>();
        for (Player player : players) {
            String line = player.getId() + "#" + player.getName() + "#" + player.getEmail() + "#" + player.getPassword();
            lines.add(line);
        }
        FileUtil.writeLinesToFile(DataConfig.FILE_DATA_USER, lines);
    }

	private List<Player> loadPlayerStats(List<Player> players) {
        List<String> lines = FileUtil.readFile(DataConfig.FILE_DATA_PLAYER_STAT);

        for (String line : lines) {
            String[] parts = line.split("#");

            String playerId = parts[StatDataConfig.ID_INDEX];
            int level = Integer.parseInt(parts[StatDataConfig.LEVEL_INDEX]);
            int coins = Integer.parseInt(parts[StatDataConfig.COINS_INDEX]);
            int gems = Integer.parseInt(parts[StatDataConfig.GEMS_INDEX]);
            int currentEnergy = Integer.parseInt(parts[StatDataConfig.CURRENT_ENERGY_INDEX]);
            
            for (Player player : players) {
                if (player.getId().equals(playerId)) {
                    player.setLevel(level);
                    player.setCoins(coins);
                    player.setGems(gems);
                    player.setCurrentEnergy(currentEnergy);
                    break;
                }
            }
        }
        return players;
    }

	private void savePlayerStats(List<Player> players) {
        List<String> lines = new ArrayList<String>();
        for (Player player : players) {
            String line = player.getId() + "#" + player.getLevel() + "#" + player.getCoins() + "#" +
                    player.getGems() + "#" + player.getCurrentEnergy();
            lines.add(line);
        }
        FileUtil.writeLinesToFile(DataConfig.FILE_DATA_PLAYER_STAT, lines);
    }

	private void loadPlayerMaterials(Player player) {
        PlayerInventory inventory = (PlayerInventory) player.getInventory();
        List<CharacterMaterial> allMaterials = MaterialLoaderManager.getInstance().getAllMaterials();
        List<String> lines = FileUtil.readFile(DataConfig.FILE_DATA_PLAYER_INVENTORY);

        if (lines == null) return;

        for (String line : lines) {
            String[] parts = line.split("#");
            if (parts.length != 3) continue;

            String playerId = parts[0];
            String materialId = parts[1];
            int amount = Integer.parseInt(parts[2]);

            if (player.getId().equals(playerId)) {
                for (CharacterMaterial material : allMaterials) {
                    if (material.getId().equals(String.valueOf(materialId))) { 
                        CharacterMaterial newMaterial = new CharacterMaterial(
                                material.getId(), material.getRarity(), material.getName(), material.getForCard());
                        inventory.addMaterial(newMaterial.getId(), amount);
                        break;
                    }
                }
            }
        }
    }
	
	private void savePlayerInventory(Player player) {
	    List<String> lines = new ArrayList<String>();
	    List<String> materials = savePlayerMaterials(player);
	    List<String> characters = savePlayerCharacters(player);
	    
	    lines.addAll(materials);
	    lines.addAll(characters);
	    
	    FileUtil.writeLinesToFile(DataConfig.FILE_DATA_PLAYER_INVENTORY, lines);
	}

	private List<String> savePlayerMaterials(Player player) {
        List<String> lines = new ArrayList<String>();
        PlayerInventory inventory = (PlayerInventory) player.getInventory();
        for (CharacterMaterial material : inventory.getMaterialList()) {
            String line = player.getId() + "#" + material.getId() + "#" + material.getAmount();
            lines.add(line);
        }
        
        return lines;
    }

	private void loadPlayerCharacters(Player player) {
        PlayerInventory inventory = (PlayerInventory) player.getInventory();
        List<BaseCharacter> allCharacters = CharacterLoaderManager.getInstance().getCharacterList(); 
        List<String> lines = FileUtil.readFile(DataConfig.FILE_DATA_PLAYER_INVENTORY);

        if (lines == null) return;

        for (String line : lines) {
            String[] parts = line.split("#");
            if (parts.length != 2) continue;

            String playerId = parts[0];
            String characterId = parts[1];

            if (player.getId().equals(playerId)) {
                for (BaseCharacter character : allCharacters) {
                    if (character.getId().equals(characterId)) {
                        inventory.addCharacter(character);
                        break;
                    }
                }
            }
        }
    }

	private List<String> savePlayerCharacters(Player player) {
        List<String> lines = new ArrayList<String>();
        PlayerInventory inventory = (PlayerInventory) player.getInventory();
        for (BaseCharacter character : inventory.getCharacterList()) {
            String line = player.getId() + "#" + character.getId();
            lines.add(line);
        }
        return lines;
    }
}
