package model.gacha.character;

import model.entity.Entity;

public interface CharacterSkills {
	default void darkResurrection(Entity entity) {}
    default int absoluteDefense(int rent) { 
    	return rent;
    }
    default int bloodTribute(int rent, Entity opponent) { 
    	return rent;
    }
    default void infernalStrategy(Entity entity) {}
    default boolean glacialExecution(Entity opponent) {
		return false; 
	}
}
