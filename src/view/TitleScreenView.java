package view;

import config.ColorConfig;
import config.GeneralConfig;
import utility.ExitUtil;
import utility.TextUtil;
import view.credentials.LoginView;
import view.credentials.RegisterView;

public class TitleScreenView extends BaseView {
    private int selectedOption = 0;
    private final String[] options = {"Login", "Register", "Exit"};

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
        System.out.println();

        int centerPosition = borderLength / 2;
        int maxOptionLength = getMaxOptionLength();
        int baseLeftPadding = centerPosition - maxOptionLength;
        baseLeftPadding = Math.max(baseLeftPadding, 0);

        for (int i = 0; i < options.length; i++) {
            String option = options[i];
            String leftPadding = " ".repeat(baseLeftPadding);
            
            StringBuilder line = new StringBuilder();
            line.append(leftPadding);
            
            if (i == selectedOption) {
                line.append(">> ");
            } else {
                line.append("   ");
            }
            
            line.append(option);
            
            if (i == selectedOption) {
                line.append(" <<");
            } else {
                line.append("   ");
            }
            
            System.out.println(line);
        }

        handleKeyInput();
    }
    
    private int getMaxOptionLength() {
        int maxLength = 0;
        for (String option : options) {
            maxLength = Math.max(maxLength, option.length());
        }
        return maxLength;
    }

    private void handleKeyInput() {
        try {
            String input = scan.nextLine().toLowerCase();

            if (!input.isEmpty()) {
                char firstChar = input.charAt(0);
                
                switch (firstChar) {
                    case 'w':
                        selectedOption = (selectedOption - 1 + options.length) % options.length;
                        break;
                    case 's':
                        selectedOption = (selectedOption + 1) % options.length;
                        break;
                    default:
                        System.out.println(" \nPlease use 'w', 's', or Enter!");
                        TextUtil.pressEnter();
                        break;
                }
            } 
            else {
                executeSelectedOption();
            }
        } catch (Exception e) {
            System.out.println(" Error reading input: " + e.getMessage());
            TextUtil.pressEnter();
        }
    }

    private void executeSelectedOption() {
        TextUtil.clearScreen();
        deactivateView();

        switch (selectedOption) {
            case 0:
                new LoginView();
                break;
            case 1:
                new RegisterView();
                break;
            case 2:
                ExitUtil.exitGame();
                break;
        }
    }
}