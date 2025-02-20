package view.credentials;

import controller.credentials.LoginViewController;
import manager.SaveLoadManager;
import utility.TextUtil;
import view.BaseView;
import view.TitleScreenView;
import view.game.map.MapSpawnView;

public class LoginView extends BaseView{
	private LoginViewController loginViewController;
	
	public LoginView() {
		this.loginViewController = new LoginViewController(this);
		while(active) {
			TextUtil.clearScreen();;
			show();			
		}
	}

	@Override
	public void show() {
		boolean valid = false;
		TextUtil.printHeader("LOGIN", WIDTH, HEIGHT);
		System.out.println();
		if(!loginViewController.thereIsUserRegistered()) {
			System.out.println(" No users are registered!\n");  
			System.out.println(" Redirecting to the registration page...");
			TextUtil.pressEnter();
			
			deactivateView();
			new RegisterView();
			return;
		}
		else {
			System.out.print(" Email [0 to exit]: ");
			String email = scan.nextLine();
			
			if(email.equals("0")) {
				deactivateView();
				new TitleScreenView();
				return;
			}
			
			System.out.print(" Password [0 to exit]: ");
			String password = scan.nextLine();
			
			System.out.println();
			
			if(password.equals("0")) {
				deactivateView();
				new TitleScreenView();
				return;
			}
			
			if(email.equals("") || password.equals("")) {
				System.out.println(" Email or Password cannot be empty!");
				TextUtil.pressEnter();
			}
			else {			
				valid = loginViewController.loginUser(email, password);
				if(valid) {
					TextUtil.pressEnter();
					deactivateView();
					MapSpawnView view = new MapSpawnView();
					view.show();
				}
				else {
					System.out.println(" Incorrect credentials!");
					TextUtil.pressEnter();
				}
			}
		}
	}
}
