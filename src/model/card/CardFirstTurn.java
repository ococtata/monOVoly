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
    	if(scan.hasNextLine()) {
    		System.out.println(" Press ENTER to start...");
    		scan.nextLine(); 
    		TextUtil.clearScreen();
    	}
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

        animateCardFlip(choice);

        int selectedCard = (choice == 1) ? card1 : card2;
        System.out.println(selectedCard == 1 ? " Player moves first!" : " Enemy moves first!");
        if (selectedCard == 1) {
            GameManager.getInstance().setPlayerTurn(true);
        }
    }


    private void printHiddenCards() {
        String cardHidden = " ------- \n|       |\n|   ?   |\n|       |\n ------- ";
        
        String[] leftLines = cardHidden.split("\n");
        String[] rightLines = cardHidden.split("\n");

        for (int i = 0; i < leftLines.length; i++) {
            System.out.println(" " + leftLines[i] + "   " + rightLines[i]);
        }
    }

    private void animateCardFlip(int choice) {
        int revealedCard = (choice == 1) ? card1 : card2;

        String cardRevealed = String.format(
            " ------- \n|       |\n|   %s%d%s   |\n|       |\n ------- ", 
            ColorConfig.GOLD, revealedCard, ColorConfig.RESET
        );
        
        String cardHidden = " ------- \n|       |\n|   ?   |\n|       |\n ------- ";

        System.out.println("\n Revealing your card...\n");
        sleep(500);

        String[] leftLines = (choice == 1) ? cardRevealed.split("\n") : cardHidden.split("\n");
        String[] rightLines = (choice == 2) ? cardRevealed.split("\n") : cardHidden.split("\n");

        for (int i = 0; i < leftLines.length; i++) {
            System.out.println(" " + leftLines[i] + "   " + rightLines[i]);
        }
        System.out.println();
    }

    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
