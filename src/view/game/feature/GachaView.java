package view.game.feature;

import config.GeneralConfig;
import controller.game.feature.GachaViewController;
import model.gacha.GachaCharacter;
import utility.Scanner;
import utility.TextUtil;
import view.BaseView;
import view.PlayerGUI;

public class GachaView extends BaseView implements Scanner, PlayerGUI{
	private GachaViewController gachaViewController;
	private GachaCharacter gachaCharacter;
	
	public GachaView() {
		this.gachaViewController = new GachaViewController(this);
		this.gachaCharacter = new GachaCharacter();
	}
	
	@Override
    public void show() {
		while (true) {
        	TextUtil.clearScreen();
            TextUtil.printHeader("CHARACTER GACHA", GeneralConfig.WIDTH, GeneralConfig.HEIGHT);
            printGachaTopBar();
            TextUtil.printHorizontalBorder(GeneralConfig.WIDTH);
            System.out.println(" 1. Roll");
            System.out.println(" 2. Go Back");
            System.out.println();
            System.out.print(" >> "); 

            String input = scan.nextLine(); 

            try {
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                    	gachaCharacter.roll();
                    	break;
                    case 2:
                		gachaViewController.exitGachaView();
                        return;
                    default:
                        System.out.println(" Input must be 1 or 2!");
                        TextUtil.pressEnter();
                        break; 
                }
            } catch (NumberFormatException e) {
                System.out.println(" Input must be an integer!");
                TextUtil.pressEnter();
            }
        }
	}
}
