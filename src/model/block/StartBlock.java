package model.block;

import config.BoardConfig;
import config.GameConfig;
import manager.GameManager;
import model.entity.Enemy;
import model.entity.Entity;
import model.entity.Player;
import utility.Scanner;
import utility.TextUtil;

import java.util.List;

public class StartBlock extends GenericBlock implements Scanner {
    private int income;

    public StartBlock(String name, String desc, int index) {
        super(name, desc, index);
        this.income = GameConfig.BLOCK_START_BASE_INCOME;
        initializePieces();
        setType("Start");
    }

    public void onPass(Entity piece) {
        increaseMoney(piece, income);
        System.out.println(" You passed Start and received $" + income + "!");
    }

    @Override
    public void onLand(Entity piece) {
        increaseMoney(piece, income);
        System.out.println(" You landed on Start and received $" + income + "!");
        upgradeProperty(piece);
    }

    private void initializePieces() {
        GameManager.getInstance().getPlayer().setBoardIndex(0);
        getPiecesOnBlock().add(GameManager.getInstance().getPlayer());
        getPiecesOnBlock().add(GameManager.getInstance().getEnemy());
    }

    private void upgradeProperty(Entity piece) {
        List<PropertyBlock> ownedProperties = piece.getOwnedProperties();

        if (ownedProperties.isEmpty()) {
            System.out.println(" You have no properties to upgrade.");
            TextUtil.pressEnter();
            return;
        }

        System.out.println(" You landed on Start! You can upgrade one of your properties.");
        System.out.println(" Select a property to upgrade:");

        PropertyBlock chosen = piece.chooseProperty();

        if (chosen != null) {
            if (chosen.getBuildingLevel() < GameConfig.PROPERTY_MAX_BUILDING_LEVEL) {
                chosen.construct(piece);
            } 
            else if (!chosen.hasLandmark()) {
                chosen.buildLandmark(piece);
            } 
            else {
                System.out.println(" " + chosen.getName() + " is already at max level and has a landmark.");
                TextUtil.pressEnter();
            }
        }
    }
}