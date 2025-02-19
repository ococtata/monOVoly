package model.card;

import model.entity.Entity;
import utility.Random;
import model.block.PropertyBlock;

import java.util.ArrayList;
import java.util.List;

import config.GameConfig;

public class FestivalTimeCard extends GenericCard implements Random {

    private int festivalDuration;

    public FestivalTimeCard() {
        setName("Festival Time");
        setDesc(String.format("Randomly choose one of your properties have increased toll (x%.1f) for %d turns.",
                GameConfig.PROPERTY_FESTIVAL_MULTIPLIER, GameConfig.CARD_FESTIVAL_DURATION));

        this.festivalDuration = GameConfig.CARD_FESTIVAL_DURATION;
    }

    @Override
    public void onTrigger(Entity piece) {
        List<PropertyBlock> properties = piece.getOwnedProperties();
        if (properties.isEmpty()) {
            System.out.println(" " + piece.getName() + " has no properties to set a festival.");
            return;
        }

        List<PropertyBlock> availableProperties = new ArrayList<>(properties);

        PropertyBlock property = null;
        boolean festivalSet = false;

        while (!festivalSet && !availableProperties.isEmpty()) {
            property = availableProperties.get(rand.nextInt(availableProperties.size()));

            if (!property.isFestival()) {
                property.startFestival();
                double multiplier = GameConfig.PROPERTY_FESTIVAL_MULTIPLIER;
                System.out.printf(" Festival started at %s! Toll is increased by x%.1f for %d turns.\n",
                        property.getName(), multiplier, festivalDuration);
                festivalSet = true;
            } else {
                availableProperties.remove(property); 
            }
        }

        if (!festivalSet) {
        	System.out.println(" All owned properties already have a festival active."); 
        }
    }
}