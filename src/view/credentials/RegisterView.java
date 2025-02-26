package view.credentials;

import config.SecurityConfig;
import controller.credentials.RegisterViewController;
import utility.TextUtil;
import view.BaseView;
import view.TitleScreenView;

public class RegisterView extends BaseView {
    private RegisterViewController registerViewController;

    public RegisterView() {
        this.registerViewController = new RegisterViewController(this);
        while (active) {
            TextUtil.clearScreen();
            show();
        }
    }

    @Override
    public void show() {
        TextUtil.printHeader("REGISTER", WIDTH, HEIGHT);
        System.out.println();
        String username;
        while (true) {
            System.out.print(" Username [0 to exit]: ");
            username = scan.nextLine();
            if (username.equals("0")) {
                deactivateView();
                new TitleScreenView();
                return;
            }
            if (username.length() < SecurityConfig.MIN_USERNAME_LENGTH) {
                System.out.println(" Username must be at least " + 
                		 SecurityConfig.MIN_USERNAME_LENGTH + " characters long!");
                continue;
            }
            if (registerViewController.isUsernameRegistered(username)) {
                System.out.println(" Username is already taken!");
                continue;
            }
            break;
        }

        String email;
        while (true) {
            System.out.print(" Email [0 to exit]: ");
            email = scan.nextLine();
            if (email.equals("0")) {
                deactivateView();
                new TitleScreenView();
                return;
            }
            if (!registerViewController.isEmailFormatValid(email)) {
                System.out.println(" Invalid email format! Email must contain '@' and '.com'.");
                continue;
            }
            if (registerViewController.isEmailRegistered(email)) {
                System.out.println(" Email is already registered!");
                continue;
            }
            break;
        }

        String password;
        while (true) {
            System.out.print(" Password [0 to exit]: ");
            password = scan.nextLine();
            if (password.equals("0")) {
                deactivateView();
                new TitleScreenView();
                return;
            }
            if (password.length() < 6) {
                System.out.println(" Password must be at least " + 
                		SecurityConfig.MIN_PASSWORD_LENGTH +" characters long!");
                continue;
            }
            break;
        }

        String confirmPassword;
        while (true) {
            System.out.print(" Confirm Password [0 to exit]: ");
            confirmPassword = scan.nextLine();
            if (confirmPassword.equals("0")) {
                deactivateView();
                new TitleScreenView();
                return;
            }
            if (!password.equals(confirmPassword)) {
                System.out.println(" Password and Confirm Password do not match!");
                continue;
            }
            break;
        }
        
        registerViewController.registerUser(username, email, password);
        deactivateView();
        new TitleScreenView();
    }
    
}