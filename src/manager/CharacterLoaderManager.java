package manager;

import java.util.ArrayList;
import java.util.List;

import model.gacha.character.AinzOoalGown;
import model.gacha.character.Albedo;
import model.gacha.character.BaseCharacter;
import model.gacha.character.Cocytus;
import model.gacha.character.Demiurge;
import model.gacha.character.ShalltearBloodfallen;

public class CharacterLoaderManager {
	private static CharacterLoaderManager instance;
	private List<BaseCharacter> characterList;
	
	private CharacterLoaderManager() {
		this.characterList = loadAllCharacters();
	}
	
	public static CharacterLoaderManager getInstance() {
		if(instance == null) instance = new CharacterLoaderManager();
		return instance;
	}
	
	 public List<BaseCharacter> loadAllCharacters() {
        List<BaseCharacter> allCharacters = new ArrayList<BaseCharacter>();
        allCharacters.add(new AinzOoalGown());
        allCharacters.add(new Albedo());
        allCharacters.add(new Cocytus());
        allCharacters.add(new Demiurge());
        allCharacters.add(new ShalltearBloodfallen());
        
        for(BaseCharacter character : allCharacters) {
        	character.loadRequiredMaterials();
        }
        
        return allCharacters;
    }

	public List<BaseCharacter> getCharacterList() {
		return characterList;
	}	 
}
