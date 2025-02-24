package model.block;

import java.util.InputMismatchException;

import config.BoardConfig;
import config.GameConfig;
import controller.game.monovoly.MonovolyMapController;
import manager.GameManager;
import model.entity.Enemy;
import model.entity.Entity;
import model.gacha.character.AinzOoalGown;
import model.gacha.character.Albedo;
import model.gacha.character.Cocytus;
import model.gacha.character.Demiurge;
import model.gacha.character.PandorasActor;
import model.gacha.character.ShalltearBloodfallen;
import utility.Random;
import utility.Scanner;
import utility.TextUtil;

public class PropertyBlock extends GenericBlock implements Random, Scanner {
    private int price;
    private int constructPrice;
    private int buildingLevel;
    private Entity owner;
    private boolean hasLandmark;
    private String landmarkName;
    private String landmarkDesc;
    private boolean isFestival;
    private int festivalDuration;

    public PropertyBlock(String name, String desc, int index, String landmarkName, String landmarkDesc) {
        super(name, desc, index);
        setType("Property");
        this.hasLandmark = false;
        this.isFestival = false;
        this.landmarkName = landmarkName;
        this.landmarkDesc = landmarkDesc;
        initializePrices();
    }

    private void initializePrices() {
        int randomPrice = GameConfig.PROPERTY_RANDOM_PRICE_MIN +
                (rand.nextInt(((GameConfig.PROPERTY_RANDOM_PRICE_MAX - GameConfig.PROPERTY_RANDOM_PRICE_MIN)
                        / GameConfig.PROPERTY_RANDOM_PRICE_INTERVAL) + 1) * GameConfig.PROPERTY_RANDOM_PRICE_INTERVAL);
        this.price = GameConfig.PROPERTY_BASE_PRICE + randomPrice;

        int randomConstructPrice = GameConfig.PROPERTY_RANDOM_CONSTRUCTION_MIN +
                (rand.nextInt(((GameConfig.PROPERTY_RANDOM_CONSTRUCTION_MAX - GameConfig.PROPERTY_RANDOM_CONSTRUCTION_MIN)
                        / GameConfig.PROPERTY_RANDOM_CONSTRUCTION_INTERVAL) + 1) * GameConfig.PROPERTY_RANDOM_CONSTRUCTION_INTERVAL);
        this.constructPrice = GameConfig.PROPERTY_BASE_CONSTRUCTION_COST + randomConstructPrice;
    }

    @Override
    public void onLand(Entity piece) {
        if (owner == null) {
        	handleNonMaxLevelProperty(piece);
        } 
        else if (owner != piece) {
            handleOpponentLanding(piece);
        } 
        else {
            handleOwnerLanding(piece);
        }
        
        System.out.println(" This property is " + (this.owner == null ? " unowned" : "owned by " + owner.getName()));
    }

    private void handleEnemyBuy(Entity piece) {
        if (piece.getMoney() >= price) {
            buy(piece);
        } 
        else {
            System.out.println(" " + piece.getName() + " chose not to buy " + this.getName() + " (not enough money).");
            TextUtil.pressEnter();
        }
    }

    private void handlePlayerBuy(Entity piece) {
        int choice;
        do {
            System.out.println(" " + this.getName() + " is UNOWNED. Do you want to buy it?");
            System.out.println(" 1. Yes");
            System.out.println(" 2. No");
            System.out.print(" >> ");
            try {
                choice = scan.nextInt();
                scan.nextLine();
                if (choice < 1 || choice > 2) {
                    System.out.println(" Invalid choice! Please enter 1 or 2.");
                    TextUtil.pressEnter();
                }
            } catch (InputMismatchException e) {
                scan.nextLine();
                System.out.println(" Invalid input!");
                TextUtil.pressEnter();
                choice = -1;
            }
        } while (choice < 1 || choice > 2);

        if (choice == 1) {
            buy(piece);
        } 
        else {
            System.out.println(" " + piece.getName() + " chose not to buy " + this.getName());
        }
    }
    
    private void handleOwnerLanding(Entity piece) {
    	handleNonMaxLevelProperty(piece);
    }
    
    private void offerLandmarkUpgrade(Entity piece) {
        int choice;
        do {
            System.out.println(" " + this.getName() + " is at max level. Do you want to build a landmark here?");
            
            System.out.println(" Landmark Name: " + this.landmarkName);
            System.out.println(" Landmark Desc: " + this.landmarkDesc);
            
            System.out.println(" 1. Yes");
            System.out.println(" 2. No");
            System.out.print(" >> ");
            try {
                choice = scan.nextInt();
                scan.nextLine();
                if (choice < 1 || choice > 2) {
                    System.out.println(" Invalid choice! Please enter 1 or 2.");
                    TextUtil.pressEnter();
                }
            } catch (InputMismatchException e) {
                scan.nextLine();
                System.out.println(" Invalid input!");
                TextUtil.pressEnter();
                choice = -1;
            }
        } while (choice < 1 || choice > 2);

        if (choice == 1) {
            buildLandmark(piece);
        } 
        else {
            construct(piece);
        }
    }

    private void handleOpponentLanding(Entity piece) {
        int toll = payToll(piece); 
        
        piece.updateTotalAssets();
        this.owner.updateTotalAssets();
        showStats();
        handleNonMaxLevelProperty(piece);
    }
    
    public void buildLandmark(Entity piece) {
        int landmarkCost = GameConfig.PROPERTY_BASE_LANDMARK_PRICE;
        if (piece.getMoney() >= landmarkCost) {
        	piece.pay(null, landmarkCost);
            hasLandmark = true;
            this.price += landmarkCost;
            piece.updateTotalAssets();
            System.out.println(" " + piece.getName() + " built a landmark on " + this.getName() + "for " + landmarkCost + "!");
            showStats();
        } else {
            System.out.println(" " + piece.getName() + " doesn't have enough money to build a landmark.");
        }
    }

    private void handleNonMaxLevelProperty(Entity piece) {
        if (piece instanceof Enemy) {
            handleEnemyAction(piece);
        } 
        else {
            handlePlayerAction(piece);
        }
    }

    private void handleEnemyAction(Entity piece) {
        if (owner == null) {
            if (piece.getMoney() >= constructPrice) { 
                handleEnemyBuy(piece);
            } 
            else {
                System.out.println(" " + piece.getName() + " chose not to buy " + this.getName() + " (not enough money).");
                TextUtil.pressEnter();
            }
        } 
        else if (owner != piece) { 
            if (piece.getMoney() >= price * GameConfig.PROPERTY_OVERTAKE_MULTIPLIER * 2) {
                overtake(piece);
            } 
            else {
                System.out.println(" " + piece.getName() + " chooses to do nothing on " + this.getName());
                TextUtil.pressEnter();
            }
        } 
        else if (owner == piece) { 
            if (buildingLevel < GameConfig.PROPERTY_MAX_BUILDING_LEVEL && piece.getMoney() >= constructPrice) {
                construct(piece);
            } 
            else if(!this.hasLandmark) {
            	buildLandmark(piece);
            }
            else {
                System.out.println(" " + piece.getName() + " chooses to do nothing on " + this.getName());
                TextUtil.pressEnter();
            }
        } 
        else {
            System.out.println(" " + piece.getName() + " chooses to do nothing on " + this.getName());
            TextUtil.pressEnter();
        }
    }

    private void handlePlayerAction(Entity piece) {
        int choice = -1;
        int optionNumber = 1;
        do {
            if (owner == null) {
                handlePlayerBuy(piece);
                return;
            }
            System.out.println(" What do you want to do with " + this.getName() + "?");

            if (this.owner != piece) {
                System.out.println(" " + optionNumber++ + ". Overtake"); 
            }

            if (owner == piece && buildingLevel < GameConfig.PROPERTY_MAX_BUILDING_LEVEL) {
                System.out.println(" " + optionNumber++ + ". Construct");
            } 
            else if (owner == piece && buildingLevel == GameConfig.PROPERTY_MAX_BUILDING_LEVEL && !this.hasLandmark) {
                System.out.println(" " + optionNumber++ + ". Build Landmark");
            } 

            System.out.println(" " + optionNumber + ". Do Nothing"); 
            System.out.print(" >> ");

            try {
                choice = scan.nextInt();
                scan.nextLine();

                int maxChoice = optionNumber; 

                if (choice < 1 || choice > maxChoice) {
                    System.out.println(" Invalid choice!");
                    TextUtil.pressEnter();
                }
            } catch (InputMismatchException e) {
                scan.nextLine();
                System.out.println(" Invalid input!");
                TextUtil.pressEnter();
                choice = -1;
            }
        } while (choice < 1 || choice > optionNumber); 

        int option = 1;

        if (this.owner != piece) {
            if (choice == option) {
                overtake(piece);
            }
            option++;
        }

        if (owner == piece && buildingLevel < GameConfig.PROPERTY_MAX_BUILDING_LEVEL) {
            if (choice == option) {
                construct(piece);
            }
            option++;
        }

        if (owner == piece && buildingLevel == GameConfig.PROPERTY_MAX_BUILDING_LEVEL && !this.hasLandmark) {
            if (choice == option) {
                offerLandmarkUpgrade(piece);
            }
            option++;
        }

        if (choice == option) { 
            System.out.println(" " + piece.getName() + " chose to do nothing.");
        }
    }
    
    private void buy(Entity piece) {
        if (piece.getMoney() >= this.price) {
            piece.pay(null, this.price);
            setOwner(piece);
            piece.addProperty(this);
            piece.updateTotalAssets();
            System.out.println(" " + piece.getName() + " bought " + this.getName() + " for $" + this.price);
            showStats();
        } 
        else {
            System.out.println(" " + piece.getName() + " doesn't have enough money to buy " + this.getName());
        }
    }

    private void overtake(Entity piece) {
        int overtakePrice = price * GameConfig.PROPERTY_OVERTAKE_MULTIPLIER;
        if (piece.getMoney() >= overtakePrice) {
            piece.pay(piece.getEnemy(), overtakePrice);
            Entity previousOwner = owner;
            setOwner(piece);
            piece.addProperty(this);
            piece.updateTotalAssets();
            if (previousOwner != null) {
            	increaseMoney(previousOwner, overtakePrice);
            	previousOwner.removeProperty(this);
                previousOwner.updateTotalAssets();
                System.out.println(" " + piece.getName() + " overtook " + this.getName() 
                + " from " + previousOwner.getName() + " for $" + overtakePrice);
            } 
            else {
                System.out.println(" " + piece.getName() + " overtook " + this.getName() 
                + " for $" + overtakePrice);
            }
            showStats();
        } 
        else {
            System.out.println(" " + piece.getName() + " doesn't have enough money to overtake " + this.getName());
        }
    }

    public void construct(Entity piece) {
        if (buildingLevel < GameConfig.PROPERTY_MAX_BUILDING_LEVEL) {
            if (piece.getMoney() >= constructPrice) {
                piece.pay(null, constructPrice);
                buildingLevel++;
                price += constructPrice;
                piece.updateTotalAssets();
                System.out.println(" " + piece.getName() + " constructed a building on " + this.getName() + ". Level: " +
                        buildingLevel + " for $" + constructPrice + "!");
                showStats();
                constructPrice += GameConfig.PROPERTY_BASE_CONSTRUCTION_COST;

                if (piece.getEquippedCharacter() instanceof Demiurge) {
                    Demiurge demiurge = (Demiurge) piece.getEquippedCharacter();
                    demiurge.useSkill(piece); 
                }
                else if (piece.getEquippedCharacter() instanceof AinzOoalGown) {
                	AinzOoalGown ainz = (AinzOoalGown) piece.getEquippedCharacter();
                	ainz.useSkill(piece, this); 
                }

            } 
            else {
                System.out.println(piece.getName() + " doesn't have enough money to construct on " + this.getName());
            }
        }
    }
    
    public void constructFree(Entity piece) {
        if (buildingLevel < GameConfig.PROPERTY_MAX_BUILDING_LEVEL) {
            buildingLevel++;
            price += constructPrice;
            piece.updateTotalAssets();
            System.out.println(" " + piece.getName() + " constructed a building on " + this.getName() + ". Level: " +
                    buildingLevel + " for free!");
            constructPrice += GameConfig.PROPERTY_BASE_CONSTRUCTION_COST;
        }
    }
    
    public void buildLandmarkFree(Entity piece) {
        if (!hasLandmark) {
            hasLandmark = true;
            this.price += GameConfig.PROPERTY_BASE_LANDMARK_PRICE;
            piece.updateTotalAssets();
            System.out.println(" " + piece.getName() + " built a landmark on " + this.getName() + " for free!");
        }
    }
    
    private int payToll(Entity piece) {
        if (owner != null && owner != piece) {
            int amount = calculateToll(this);
            
            if (piece.getEquippedCharacter() instanceof PandorasActor) {
                amount = ((PandorasActor) piece.getEquippedCharacter()).useSkill(amount, piece);
            }
            else if (piece.getEquippedCharacter() instanceof Albedo) {
                amount = ((Albedo) piece.getEquippedCharacter()).useSkill(amount, piece);
            }

            if (piece.getMoney() >= amount) {
                piece.pay(piece.getEnemy(), amount);
                owner.setMoney(owner.getMoney() + amount);
                System.out.println(" " + piece.getName() + " paid $" + amount + " toll to " + owner.getName() + ".");
            } 
            else {
                System.out.println(" " + piece.getName() + " doesn't have enough money to pay the toll!");
            }
            return amount;
        }
        return 0;
    }

    public int calculateToll(PropertyBlock property) {
        int baseRent = (property.getPrice() * GameConfig.PROPERTY_BASE_TOLL_PERCENTAGE) / 100;
        double multiplier = 1.0; 

        if (property.isFestival()) {
            multiplier *= GameConfig.PROPERTY_FESTIVAL_MULTIPLIER;
        }

        if (property.hasLandmark()) {
            multiplier *= GameConfig.PROPERTY_LANDMARK_MULTIPLIER;
        }

        return (int) (baseRent * multiplier);
    }
    
    public void startFestival() {
        isFestival = true;
        festivalDuration = GameConfig.CARD_FESTIVAL_DURATION;
    }
    
    public void decrementFestivalDuration() {
        if (isFestival) {
            festivalDuration--;
            if (festivalDuration <= 0) {
                isFestival = false; 
                festivalDuration = 0;
                System.out.println(" Festival at " + this.getName() + " has ended.");
                TextUtil.pressEnter();
            }
        }
    }
    
    public void downgrade() {
    	this.buildingLevel -= 1;
    	this.price -= constructPrice;
    	this.constructPrice -= GameConfig.PROPERTY_BASE_CONSTRUCTION_PRICE_INCREASE;
    }
    
    private void showStats() {
    	System.out.println();
        TextUtil.printHorizontalBorder(BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));
    	MonovolyMapController.showStats();
        TextUtil.printHorizontalBorder(BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));
    }
    
    public Entity getOwner() {
        return owner;
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
    }

    public int getConstructPrice() {
        return constructPrice;
    }

    public int getBuildingLevel() {
        return buildingLevel;
    }

    public boolean hasLandmark() {
        return hasLandmark;
    }

    public int getPrice() {
        return price;
    }

    public String getLandmarkName() {
        return landmarkName;
    }

    public void setLandmarkName(String landmarkName) {
        this.landmarkName = landmarkName;
    }

    public String getLandmarkDesc() {
        return landmarkDesc;
    }

    public void setLandmarkDesc(String landmarkDesc) {
        this.landmarkDesc = landmarkDesc;
    }

    public boolean isFestival() {
        return isFestival;
    }

    public void setFestival(boolean isFestival) {
        this.isFestival = isFestival;
    }

    public void setHasLandmark(boolean hasLandmark) {
        this.hasLandmark = hasLandmark;
    }
}