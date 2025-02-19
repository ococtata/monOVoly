package view.game.feature;

import config.GeneralConfig;
import controller.game.feature.GachaViewController;
import manager.GameManager;
import utility.Scanner;
import utility.TextUtil;
import view.BaseView;

public class GachaView extends BaseView implements Scanner{
	private GachaViewController gachaViewController;
	
	public GachaView() {
		this.gachaViewController = new GachaViewController(this);
	}
	
	@Override
    public void show() {
        while (true) {
        	TextUtil.clearScreen();
            TextUtil.printHeader("GACHA", GeneralConfig.WIDTH, GeneralConfig.HEIGHT);
            System.out.println(" What do you want to gacha?");
            System.out.println(" 1. Character");
            System.out.println(" 2. Pendant");
            System.out.println(" 3. Go Back"); 
            System.out.println();
            System.out.print(" >> "); 
            String input = scan.nextLine();

            try {
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        gachaViewController.showCharacterGachaMenu(); 
                        return;
                    case 2:
                        gachaViewController.showPendantGachaMenu(); 
                        return;
                    case 3:
                        gachaViewController.exitGachaView();
                        return;
                    default:
                        System.out.println(" Invalid choice. Please enter 1, 2 or 3.");
                        TextUtil.pressEnter();
                }
            } catch (NumberFormatException e) {
                System.out.println(" Invalid input. Please enter an integer.");
                TextUtil.pressEnter();
            }
        }
    }
}
