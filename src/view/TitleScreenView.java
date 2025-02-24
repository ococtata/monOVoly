package view;

import config.ColorConfig;
import config.GeneralConfig;
import utility.ExitUtil;
import utility.TextUtil;
import view.credentials.LoginView;
import view.credentials.RegisterView;

public class TitleScreenView extends BaseView{
	
	public TitleScreenView() {
		while(active) {
			TextUtil.clearScreen();
			show();
		}
	}

	@Override
    public void show() {
        String title = GeneralConfig.TITLE;

        title = title.replace("GOLD", ColorConfig.GOLD);
        title = title.replace("RESET", ColorConfig.LIGHT_GREEN);

        String coloredTitle = ColorConfig.LIGHT_GREEN + title + ColorConfig.RESET;
        System.out.println(coloredTitle);

        int borderLength = 0;
        String[] lines = GeneralConfig.TITLE.split("\r\n");
        for (String line : lines) {
            borderLength = Math.max(borderLength, line.replaceAll("GOLD|RESET", "").length());
        }

        TextUtil.printHorizontalBorder(borderLength);
        System.out.println(" 1. Login");
        System.out.println(" 2. Register");
        System.out.println(" 3. Exit");
        System.out.println();
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
                    deactivateView();
                    ExitUtil.exitGame();
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
