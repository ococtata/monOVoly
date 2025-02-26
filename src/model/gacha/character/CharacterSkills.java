package model.gacha.character;

import model.block.PropertyBlock;
import model.entity.Entity;

public interface CharacterSkills {
    default void createFortress(Entity entity, PropertyBlock property) {}
    default int armorOfMalice(int rent) { return rent; }
    default void bloodTribute(Entity entity, Entity opponent) {}
    default void infernalStrategy(Entity owner, PropertyBlock property) {}
    default void glacialImprisonment(Entity entity, Entity opponent) {}
    default int falseFortune(int rent) { return rent; }
}