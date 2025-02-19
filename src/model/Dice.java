package model;

import config.ColorConfig;
import utility.Random;
import utility.TextUtil;

public class Dice implements Random {
    private static final String RED_DOT = ColorConfig.LIGHT_RED + "." + ColorConfig.RESET;

    private static final String[] DICE_FACES = {
        String.format(" ------- \n|       |\n|   %s   |\n|       |\n ------- ", RED_DOT),
        String.format(" ------- \n| %s     |\n|       |\n|     %s |\n ------- ", RED_DOT, RED_DOT),
        String.format(" ------- \n| %s     |\n|   %s   |\n|     %s |\n ------- ", RED_DOT, RED_DOT, RED_DOT),
        String.format(" ------- \n| %s   %s |\n|       |\n| %s   %s |\n ------- ", RED_DOT, RED_DOT, RED_DOT, RED_DOT),
        String.format(" ------- \n| %s   %s |\n|   %s   |\n| %s   %s |\n ------- ", RED_DOT, RED_DOT, RED_DOT, RED_DOT, RED_DOT),
        String.format(" ------- \n| %s   %s |\n| %s   %s |\n| %s   %s |\n ------- ", RED_DOT, RED_DOT, RED_DOT, RED_DOT, RED_DOT, RED_DOT)
    };

    public Dice() {}

    public int[] roll(String mode) {
        int roll1, roll2;

        for (int i = 0; i < 5; i++) {
            TextUtil.clearScreen();
            roll1 = rand.nextInt(6);
            roll2 = rand.nextInt(6);
            printDice(roll1, roll2);
            sleep(350);
        }

        do {
            roll1 = rand.nextInt(6) + 1;
            roll2 = rand.nextInt(6) + 1;
        } 
        while (
            (mode.equals("ODD") && (roll1 + roll2) % 2 == 0) || 
            (mode.equals("EVEN") && (roll1 + roll2) % 2 != 0)
        );

        TextUtil.clearScreen();
        printDice(roll1 - 1, roll2 - 1);
        
        int[] rolls = {roll1, roll2};
        return rolls;
    }


    private void printDice(int roll1, int roll2) {
        String[] dice1Lines = DICE_FACES[roll1].split("\n");
        String[] dice2Lines = DICE_FACES[roll2].split("\n");

        for (int i = 0; i < dice1Lines.length; i++) {
            System.out.println(" \t\t" + dice1Lines[i] + "   " + dice2Lines[i]);
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
