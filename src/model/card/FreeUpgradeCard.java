package model.card;

import java.util.List;

import config.GameConfig;
import model.block.PropertyBlock;
import model.entity.Entity;
import utility.Random;

public class FreeUpgradeCard extends GenericCard implements Random {

    public FreeUpgradeCard() {
        setName("Free Upgrade");
        setDesc("Upgrade one of your owned properties for free.");
    }

    @Override
    public void onTrigger(Entity piece) {
        List<PropertyBlock> properties = piece.getOwnedProperties();
        if (properties.isEmpty()) {
            System.out.println(" " + piece.getName() + " has no properties to upgrade.");
            return;
        }

        PropertyBlock property = null;
        boolean upgraded = false;

        while (!upgraded) {
            property = properties.get(rand.nextInt(properties.size()));

            if (property.getBuildingLevel() < GameConfig.PROPERTY_MAX_BUILDING_LEVEL) {
                property.construct(piece);
                System.out.println(" " + piece.getName() + " upgraded " + property.getName() + " for free!");
                upgraded = true;
            } 
            else {
                System.out.println(" " + property.getName() + " is already fully upgraded. Trying another property...");
                properties.remove(property);
            }
        }

        if (!upgraded) {
            System.out.println(" All owned properties are fully upgraded.");
        }
    }
}