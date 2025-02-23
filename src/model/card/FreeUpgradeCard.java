package model.card;

import java.util.List;

import config.GameConfig;
import model.block.PropertyBlock;
import model.entity.Entity;
import utility.Random;

public class FreeUpgradeCard extends GenericCard implements Random {

    public FreeUpgradeCard() {
        setName("Free Upgrade");
        setDesc("Upgrade one of your owned properties for free (random).");
    }

    @Override
    public void onTrigger(Entity piece) {
        List<PropertyBlock> properties = piece.getOwnedProperties();
        if (properties.isEmpty()) {
            System.out.println(" " + piece.getName() + " has no properties to upgrade or build a landmark.");
            return;
        }

        PropertyBlock property = null;
        boolean upgradedOrLandmark = false;

        while (!upgradedOrLandmark && !properties.isEmpty()) {
            property = properties.get(rand.nextInt(properties.size()));

            if (property.getBuildingLevel() < GameConfig.PROPERTY_MAX_BUILDING_LEVEL) {
                property.constructFree(piece);
                upgradedOrLandmark = true;
            } else if (!property.hasLandmark()) {
                property.buildLandmarkFree(piece);
                upgradedOrLandmark = true;
            } else {
                System.out.println(" " + property.getName() + " is already fully upgraded and has a landmark. Trying another property...");
                properties.remove(property);
            }
        }

        if (!upgradedOrLandmark) {
            System.out.println(" All owned properties are fully upgraded and have landmarks.");
        }
    }
}