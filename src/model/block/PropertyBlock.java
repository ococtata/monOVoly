package model.block;

import java.util.InputMismatchException;

import config.GameConfig;
import model.entity.Enemy;
import model.entity.Entity;
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

    public PropertyBlock(String name, String desc, String landmarkName, String landmarkDesc) {
        super(name, desc);
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
            System.out.println(" " + this.getName() + " is unowned. Do you want to buy it?");
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
    	payToll(piece);
    	piece.updateTotalAssets();
    	this.owner.updateTotalAssets();
        
    	handleNonMaxLevelProperty(piece);
    }
    
    private void buildLandmark(Entity piece) {
        int landmarkCost = GameConfig.PROPERTY_BASE_LANDMARK_PRICE;
        if (piece.getMoney() >= landmarkCost) {
            deductMoney(piece, landmarkCost);
            hasLandmark = true;
            this.price += landmarkCost;
            piece.updateTotalAssets();
            System.out.println(" " + piece.getName() + " built a landmark on " + this.getName() + "!");
        } else {
            System.out.println(" " + piece.getName() + " doesn't have enough money to build a landmark.");
        }
    }

    private void handleMaxLevelProperty(Entity piece) {
        if (hasLandmark) {
            System.out.println(" This property is a landmark and cannot be further upgraded.");
        } 
        else {
            System.out.println(" Max building level reached!");
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
        do {
            if (owner == null) {
                handlePlayerBuy(piece);
                return;
            }

            System.out.println(" What do you want to do with " + this.getName() + "?");

            if (this.owner != piece) {
                System.out.println(" 1. Overtake");
            }

            if (owner == piece && buildingLevel < GameConfig.PROPERTY_MAX_BUILDING_LEVEL) {
                System.out.println((this.owner != piece) ? " 2. Construct" : " 1. Construct");
            }
            else if (owner == piece && buildingLevel == GameConfig.PROPERTY_MAX_BUILDING_LEVEL && !this.hasLandmark) {
            	System.out.println((this.owner != piece) ? " 2. Build Landmark" : " 1. Build Landmark");
            }
            else if (owner == piece && buildingLevel == GameConfig.PROPERTY_MAX_BUILDING_LEVEL && this.hasLandmark) {
            	System.out.println((this.owner != piece) ? " 2. Nothing" : " 1. Nothing");
            }

            System.out.print(" >> ");
            try {
                choice = scan.nextInt();
                scan.nextLine();

                int maxChoice = (this.owner != piece) ? 1 : 1; 

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
        } while (choice < 1 || choice > ((this.owner != piece) ? 1 : 1));

        if (choice == 1 && this.owner != piece) {
            overtake(piece);
        } else if (choice == 1 && owner == piece && 
        		buildingLevel < GameConfig.PROPERTY_MAX_BUILDING_LEVEL) {
            construct(piece);
        }
        else if (choice == 1 && owner == piece && 
        		buildingLevel == GameConfig.PROPERTY_MAX_BUILDING_LEVEL && !this.hasLandmark) {
        	offerLandmarkUpgrade(piece);
        }
        else if (choice == 1 && owner == piece && 
        		buildingLevel == GameConfig.PROPERTY_MAX_BUILDING_LEVEL && this.hasLandmark) {
        	handleMaxLevelProperty(piece);
        }
    }
    
    private void buy(Entity piece) {
        if (piece.getMoney() >= this.price) {
            deductMoney(piece, this.price);
            setOwner(piece);
            piece.addProperty(this);
            piece.updateTotalAssets();
            System.out.println(" " + piece.getName() + " bought " + this.getName() + " for $" + this.price);
        } 
        else {
            System.out.println(" " + piece.getName() + " doesn't have enough money to buy " + this.getName());
        }
    }

    private void overtake(Entity piece) {
        int overtakePrice = price * GameConfig.PROPERTY_OVERTAKE_MULTIPLIER;
        if (piece.getMoney() >= overtakePrice) {
            deductMoney(piece, overtakePrice);
            Entity previousOwner = owner;
            setOwner(piece);
            piece.addProperty(this);
            piece.updateTotalAssets();
            if (previousOwner != null) {
            	increaseMoney(previousOwner, overtakePrice);
            	previousOwner.removeProperty(this);
                previousOwner.updateTotalAssets();
                System.out.println(" " + piece.getName() + " overtook " + this.getName() + " from " + previousOwner.getName() + " for $" + overtakePrice);
            } 
            else {
                System.out.println(" " + piece.getName() + " overtook " + this.getName() + " for $" + overtakePrice);
            }

        } 
        else {
            System.out.println(" " + piece.getName() + " doesn't have enough money to overtake " + this.getName());
        }
    }

    private void construct(Entity piece) {
        if (buildingLevel < GameConfig.PROPERTY_MAX_BUILDING_LEVEL) {
            if (piece.getMoney() >= constructPrice) {
                deductMoney(piece, constructPrice);
                buildingLevel++;
                constructPrice += GameConfig.PROPERTY_BASE_CONSTRUCTION_COST;
                price += GameConfig.PROPERTY_BASE_CONSTRUCTION_COST;
                piece.updateTotalAssets();
                System.out.println(piece.getName() + " constructed a building on " + this.getName() + ". Level: " + buildingLevel);
            } 
            else {
                System.out.println(piece.getName() + " doesn't have enough money to construct on " + this.getName());
            }
        }
    }
    
    private void payToll(Entity piece) {
        if (owner != null && owner != piece) {
            int amount = calculateRent(this);
            if (piece.getMoney() >= amount) {
                deductMoney(piece, amount);
                owner.setMoney(owner.getMoney() + amount);
                System.out.println(" " + piece.getName() + " paid $" + amount + " rent to " + owner.getName() + ".");
            } 
            else {
                System.out.println(" " + piece.getName() + " doesn't have enough money to pay rent!");
                
            }
        }
    }

    public int calculateRent(PropertyBlock property) {
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


    public Entity getOwner() {
        return owner;
    }

    private void setOwner(Entity owner) {
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