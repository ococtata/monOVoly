package model.card;

import config.ColorConfig;
import manager.GameManager;
import utility.Random;
import utility.Scanner;
import utility.TextUtil;

public class CardFirstTurn implements Random, Scanner{
    private int card1, card2;

    public CardFirstTurn() {
        shuffleCards();
    }

    private void shuffleCards() {
        if (rand.nextBoolean()) {
            card1 = 1;
            card2 = 2;
        } else {
            card1 = 2;
            card2 = 1;
        }
    }

    public void selectFirst() {
    	System.out.println(" Press ENTER to start...");
		scan.nextLine(); 
		TextUtil.clearScreen();
		
        System.out.println(" Pick a card to decide who moves first:");
        printHiddenCards();

        int choice = 0;
        boolean validChoice = false;
        
        while (!validChoice) {
            System.out.print(" Enter 1 (left card) or 2 (right card): ");
            String input = scan.nextLine();

            try {
                choice = Integer.parseInt(input);

                if (choice == 1 || choice == 2) {
                    validChoice = true; 
                } 
                else {
                    System.out.println(" Invalid choice!");
                }
            } 
            catch (NumberFormatException e) {
                System.out.println(" Input must be an integer!");
            }
        }

        revealCard(choice);

        int selectedCard = (choice == 1) ? card1 : card2;
        System.out.println(selectedCard == 1 ? " Player moves first!" : " Enemy moves first!");
        if (selectedCard == 1) {
            GameManager.getInstance().setPlayerTurn(true);
        }
    }


    private void printHiddenCards() {
    	String[] cardHidden = {
                "  -----------------  ",
                " |                 | ",
                " |                 | ",
                " |        ?        | ",
                " |                 | ",
                " |                 | ",
                "  -----------------  "
            };
    	
    	 for (String line : cardHidden) {
             for (int i = 0; i < 2; i++) {
                 System.out.print("  " + line + "  ");
             }
             System.out.println();
         }
    }

    private void revealCard(int choice) {
        int revealedCard = (choice == 1) ? card1 : card2;

        String[] cardRevealed = {
            ColorConfig.GOLD + "  -----------------  " + ColorConfig.RESET,
            ColorConfig.GOLD + " |                 | " + ColorConfig.RESET,
            ColorConfig.GOLD + " |                 | " + ColorConfig.RESET,
            ColorConfig.GOLD + " |        " + revealedCard + "        | " + ColorConfig.RESET,
            ColorConfig.GOLD + " |                 | " + ColorConfig.RESET,
            ColorConfig.GOLD + " |                 | " + ColorConfig.RESET,
            ColorConfig.GOLD + "  -----------------  " + ColorConfig.RESET,
        };

        String[] cardHidden = {
                "  -----------------  ",
                " |                 | ",
                " |                 | ",
                " |        ?        | ",
                " |                 | ",
                " |                 | ",
                "  -----------------  "
            };

        System.out.println("\n Revealing your card...\n");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String[] leftLines = (choice == 1) ? cardRevealed : cardHidden;
        String[] rightLines = (choice == 2) ? cardRevealed : cardHidden;

        for (int i = 0; i < leftLines.length; i++) {
            System.out.println(" " + leftLines[i] + "   " + rightLines[i]);
        }
        System.out.println();
    }
}
