package view;

import config.GeneralConfig;
import utility.TextUtil;
import view.credentials.LoginView;
import view.credentials.RegisterView;

public class TitleScreenView extends BaseView{
	
	private int borderLength = 56;
	
	public TitleScreenView() {
		while(active) {
			TextUtil.clearScreen();
			show();
		}
	}

	@Override
	public void show() {
		System.out.println(GeneralConfig.TITLE);
		TextUtil.printHorizontalBorder(borderLength);
		System.out.println(" 1. Login");
		System.out.println(" 2. Register");
		System.out.println(" 3. Exit");
		System.out.print(" >> ");
		
		String input = scan.nextLine();
		try {
		    int choice = Integer.parseInt(input);
		    switch (choice) {
		        case 1:
		            TextUtil.clearScreen();
		            deactivateView();
		            new LoginView();
		            break;
		        case 2:
		            TextUtil.clearScreen();
		            deactivateView();
		            new RegisterView();
		            break;
		        case 3:
		            System.out.println(" Exit");
		            deactivateView();
		            break;
		        default:
		            System.out.println(" Input must be 1-3!");
		            TextUtil.pressEnter();
		            break;
		    }
		} catch (NumberFormatException e) {
		    System.out.println(" Input must be an integer!");
		    TextUtil.pressEnter();
		}
	}
}
