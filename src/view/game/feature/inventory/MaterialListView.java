package view.game.feature.inventory;

import java.util.InputMismatchException;
import java.util.List;

import config.ColorConfig;
import config.GeneralConfig;
import controller.game.feature.inventory.MaterialListViewController;
import manager.GameManager;
import model.gacha.material.CharacterMaterial;
import utility.Scanner;
import utility.TextUtil;
import view.BaseView;

public class MaterialListView extends BaseView implements Scanner {
	private MaterialListViewController materialListViewController; 
	private int borderLength = 104;
	
	private int sortBy = 1;
    private boolean ascending = true;
    
	public MaterialListView() {
		this.materialListViewController = new MaterialListViewController(this);
	}

	@Override
    public void show() {
        List<CharacterMaterial> materials = materialListViewController.getMaterials();
        int pageSize = 10;
        int currentPage = 0;

        while (true) {
            TextUtil.clearScreen();
            TextUtil.printHeader("Material List", GeneralConfig.WIDTH, GeneralConfig.HEIGHT);
            System.out.println();
            
            if (materials == null || materials.isEmpty()) {
                System.out.println(" No materials found in your inventory.\n");
                TextUtil.pressEnter();
                GameManager.getInstance().setCurrentView(getPreviousView());
                getPreviousView().show();
                return;
            }
            
            int startIndex = currentPage * pageSize;
            int endIndex = Math.min((currentPage + 1) * pageSize, materials.size());

            System.out.println(" Your Materials (Page " + (currentPage + 1) + " of " + (int) Math.ceil((double) materials.size() / pageSize) + "):");
            
            showTable(startIndex, endIndex, materials);

            if (endIndex < materials.size()) {
                System.out.println(" 1. Previous Page");
                System.out.println(" 2. Next Page");
                System.out.println(" 3. Sort");
                System.out.println(" 4. Back to Inventory");
            } else if (currentPage > 0) {
                System.out.println(" 1. Previous Page");
                System.out.println(" 3. Sort");
                System.out.println(" 4. Back to Inventory");
            } else {
            	System.out.println(" 3. Sort");
            	System.out.println(" 4. Back to Inventory");
            }

            System.out.print(" >> ");

            int choice;
            try {
                choice = scan.nextInt();
                scan.nextLine(); 
            } catch (InputMismatchException e) {
                scan.nextLine(); 
                System.out.println(" Invalid input. Please enter a number.");
                TextUtil.pressEnter();
                continue; 
            }

            switch (choice) {
                case 1:
                    if (currentPage > 0) {
                        currentPage--;
                    } 
                    else {
                        System.out.println(" You are already on the first page.");
                        TextUtil.pressEnter();
                    }
                    break;
                case 2:
                    if (endIndex < materials.size()) {
                        currentPage++;
                    } 
                    else {
                        System.out.println(" You are already on the last page.");
                        TextUtil.pressEnter();
                    }
                    break;
                case 3:
                	pickSortBy();
                	materialListViewController.sortMaterials(sortBy, ascending);
                	break;
                case 4:
                    GameManager.getInstance().setCurrentView(getPreviousView());
                    getPreviousView().show();
                    return;
                default:
                    System.out.println(" Invalid choice.");
                    TextUtil.pressEnter();
            }
        } 
    }
	
	private void pickSortBy() {
        while (true) {
            TextUtil.clearScreen();
            System.out.println(" Pick a sorting option");
            TextUtil.printHorizontalBorder(50);
            System.out.println();
            System.out.println(" 1. Name");
            System.out.println(" 2. Rarity");
            System.out.println(" 3. Amount");
            System.out.println(" 4. For Character");
            System.out.println(" 5. Back");
            System.out.print(" >> ");

            int choice;
            try {
                choice = scan.nextInt();
                scan.nextLine();
            } 
            catch (InputMismatchException e) {
                scan.nextLine();
                System.out.println(" Invalid input.");
                TextUtil.pressEnter();
                continue;
            }

            if (choice >= 1 && choice <= 4) {
                sortBy = choice;
                System.out.println();
                System.out.println(" Sort order: ");
                System.out.println(" 1. Ascending");
                System.out.println(" 2. Descending");
                System.out.println(" 3. Back");
                System.out.print(" >> ");

                int orderChoice;
                try {
                    orderChoice = scan.nextInt();
                    scan.nextLine();
                } catch (InputMismatchException e) {
                    scan.nextLine();
                    System.out.println(" Invalid input.");
                    TextUtil.pressEnter();
                    continue;
                }
                
                if(orderChoice == 3) {
                	return;
                }

                ascending = (orderChoice == 1); 
                return;
            } 
            else if (choice == 5) {
                return; 
            } 
            else {
                System.out.println(" Invalid choice.");
                TextUtil.pressEnter();
            }
        }
    }
	
	private void showTable(int startIndex, int endIndex, List<CharacterMaterial> materials) {
		TextUtil.printHorizontalBorder(borderLength);

        System.out.printf(" | %-5s | %-40s | %-10s | %-10s | %-23s |\n", "No.", "Name", "Rarity", "Amount", "For Character");
        TextUtil.printHorizontalBorder(borderLength);

        for (int i = startIndex; i < endIndex; i++) {
            CharacterMaterial material = materials.get(i);
            String rarityColor = material.getRarity().getColor();
            System.out.printf(" | %-5d | %s%-40s%s | %s%-10s%s | %-10d | %-23s |\n", i + 1, rarityColor, material.getName(), ColorConfig.RESET,
                    rarityColor, material.getRarity(), ColorConfig.RESET, material.getAmount(), material.getForCard());
            TextUtil.printHorizontalBorder(borderLength);
        }
	}
}
