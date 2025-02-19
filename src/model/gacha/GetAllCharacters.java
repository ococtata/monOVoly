package model.gacha;

import java.util.ArrayList;
import java.util.List;

import model.gacha.character.AinzOoalGown;
import model.gacha.character.Albedo;
import model.gacha.character.BaseCharacter;
import model.gacha.character.Cocytus;
import model.gacha.character.Demiurge;
import model.gacha.character.ShalltearBloodfallen;

public interface GetAllCharacters {
	
	default List<BaseCharacter> getAllCharacters() {
		List<BaseCharacter> characterList = new ArrayList<BaseCharacter>();
		characterList.add(new AinzOoalGown());
		characterList.add(new Albedo());
		characterList.add(new Cocytus());
		characterList.add(new Demiurge());
		characterList.add(new ShalltearBloodfallen());		
		
		return characterList;
	}
}
