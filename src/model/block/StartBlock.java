package model.block;

import config.BoardConfig;
import config.GameConfig;
import manager.GameManager;
import model.entity.Entity;
import utility.Scanner;
import utility.TextUtil;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class StartBlock extends GenericBlock implements Scanner {
    private int income;
    private Set<Entity> processedThisTurn;
    
    public StartBlock(String name, String desc, int index) {
        super(name, desc, index);
        this.income = GameConfig.BLOCK_START_BASE_INCOME;
        this.processedThisTurn = new HashSet<>();
        initializePieces();
        setType("Start");
    }
    
    @Override
    public void onLand(Entity piece) {
        if (processedThisTurn.contains(piece)) {
            return;
        }
        
        TextUtil.pressEnter();
        System.out.println();
        gainIncome(piece);
        upgradeProperty(piece);
        
        processedThisTurn.add(piece);
    }
    
    public void onPass(Entity piece) {
        if (!processedThisTurn.contains(piece)) {
            System.out.println(" " + piece.getName() + " passed through the Start Block!");
            System.out.println();
            gainIncome(piece);
            upgradeProperty(piece);
            
            processedThisTurn.add(piece);
        }
    }
    
    public void resetProcessedEntities() {
        processedThisTurn.clear();
    }
    
    private void initializePieces() {
        GameManager.getInstance().getPlayer().setBoardIndex(0);
        getPiecesOnBlock().add(GameManager.getInstance().getPlayer());
        getPiecesOnBlock().add(GameManager.getInstance().getEnemy());
    }
    
    private void upgradeProperty(Entity piece) {
        TextUtil.printHorizontalBorder(
                BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));
        List<PropertyBlock> ownedProperties = piece.getOwnedProperties();
        if (ownedProperties.isEmpty()) {
            System.out.println(" " + piece.getName() + " have no properties to upgrade.");
            TextUtil.pressEnter();
            return;
        }
        System.out.println(" " + piece.getName() + " can upgrade one of their properties.\n");
        TextUtil.pressEnter();
        TextUtil.printHorizontalBorder(
                BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));
        
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
        else {
            System.out.println(" " + piece.getName() + " decided to do nothing.");
            TextUtil.clearScreen();
        }
    }
    
    private void gainIncome(Entity piece) {
        TextUtil.printHorizontalBorder(
                BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));
        increaseMoney(piece, income);
        System.out.println(" " + piece.getName() + " gained $" + income + "!\n");
        TextUtil.pressEnter();
    }
}