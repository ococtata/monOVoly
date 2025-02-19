package model.card;

import model.block.PropertyBlock;
import model.entity.Entity;
import utility.Random;
import utility.TextUtil;

import java.util.List;

public class TornadoDisasterCard extends GenericCard implements Random {

    public TornadoDisasterCard() {
        setName("Tornado Disaster");
        setDesc("Downgrade one of your properties by 1 level. If it's already at the lowest level, you lose ownership of it.");
    }

    @Override
    public void onTrigger(Entity piece) {
        List<PropertyBlock> ownedProperties = piece.getOwnedProperties();

        if (ownedProperties.isEmpty()) {
            System.out.println(" " + piece.getName() + " have no properties for the tornado to destroy.");
            TextUtil.pressEnter();
            return;
        }

        PropertyBlock property = ownedProperties.get(rand.nextInt(ownedProperties.size()));

        if (property.getBuildingLevel() > 0) {
            property.downgrade();
            System.out.printf(" Tornado hit %s's %s! Downgraded by one level.\n", 
            		piece.getName(), property.getName());
        } else {
            piece.removeProperty(property);
            property.setOwner(null);
            System.out.println(" Tornado hit " + property.getName() + "!");
            System.out.printf(" It was already at the lowest level, so %s lost ownership of %s!\n", 
            		piece.getName(), property.getName());
        }
    }
}
