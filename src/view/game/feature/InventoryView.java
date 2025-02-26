package view.game.feature;

import config.GeneralConfig;
import controller.game.feature.InventoryViewController;
import manager.GameManager;
import utility.Scanner;
import utility.TextUtil;
import view.BaseView;
import view.game.feature.inventory.CharacterListView;
import view.game.feature.inventory.MaterialListView;

public class InventoryView extends BaseView implements Scanner{
	private InventoryViewController inventoryViewController;

	public InventoryView() {
		this.inventoryViewController = new InventoryViewController(this);
	}
	@Override
	public void show() {
		while(true) {
			TextUtil.clearScreen();
            TextUtil.printHeader("INVENTORY", GeneralConfig.WIDTH, GeneralConfig.HEIGHT);
            System.out.println(" What do you want to see?");
            System.out.println(" 1. Owned Materials");
            System.out.println(" 2. Owned Characters");
            System.out.println(" 3. Back"); 
            System.out.println();
            System.out.print(" >> "); 
            
            String input = scan.nextLine();
            try {
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        MaterialListView materialListView = new MaterialListView();
                        materialListView.setPreviousView(this); 
                        GameManager.getInstance().setCurrentView(materialListView);
                        materialListView.show();
                        return; 
                    case 2:
                        CharacterListView characterListView = new CharacterListView();
                        characterListView.setPreviousView(this); 
                        GameManager.getInstance().setCurrentView(characterListView);
                        characterListView.show();
                        return; 
                    case 3:
                        GameManager.getInstance().setCurrentView(getPreviousView()); 
                        getPreviousView().show();
                        return;
                    default:
                        System.out.println(" Invalid choice. Please try again.");
                        TextUtil.pressEnter();
                }
            } catch (NumberFormatException e) {
                System.out.println(" Invalid input. Please enter a number.");
                TextUtil.pressEnter();
            }
		}
	}

}
