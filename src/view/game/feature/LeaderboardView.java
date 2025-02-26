package view.game.feature;

import java.util.InputMismatchException;
import java.util.List;

import config.GeneralConfig;
import controller.game.feature.LeaderboardViewController;
import manager.GameManager;
import model.entity.Player;
import utility.TextUtil;
import view.BaseView;

public class LeaderboardView extends BaseView {
	private LeaderboardViewController leaderboardViewController;
	private int borderLength = 55;
	public LeaderboardView() {
		this.leaderboardViewController = new LeaderboardViewController(this);
	}

	@Override
    public void show() {
        showLeaderboard();
    }

    public void showLeaderboard() {
        List<Player> players = GameManager.getInstance().getPlayers();

        players.sort((p1, p2) -> Integer.compare(p2.getTrophy(), p1.getTrophy()));

        int pageSize = 10;
        int totalPages = (int) Math.ceil((double) players.size() / pageSize);
        int currentPage = 1;

        while (true) {
            TextUtil.clearScreen();
            TextUtil.printHeader("Leaderboard", GeneralConfig.WIDTH, GeneralConfig.HEIGHT);
            System.out.println();
            int startIndex = (currentPage - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, players.size());

            System.out.println(" Leaderboard (Page " + currentPage + " of " + totalPages + "):");
            TextUtil.printHorizontalBorder(borderLength);
            System.out.printf(" | %-5s | %-30s | %-10s |\n", "Rank", "Name", "Trophies");
            TextUtil.printHorizontalBorder(borderLength);
            for (int i = startIndex; i < endIndex; i++) {
                Player player = players.get(i);
                String name = leaderboardViewController.stripAnsiCodes(player.getName());
                System.out.printf(" | %-5d | %-30s | %-10d |\n", i + 1, name, player.getTrophy());
            }
            TextUtil.printHorizontalBorder(borderLength);

            System.out.println(" 1. Previous Page");
            System.out.println(" 2. Next Page");
            System.out.println(" 3. Back");
            System.out.print(" >> ");

            int choice;
            try {
                choice = scan.nextInt();
                scan.nextLine();
            } catch (InputMismatchException e) {
                scan.nextLine();
                System.out.println(" Invalid input. Please enter a number.");
                TextUtil.pressEnter();
                continue;
            }

            switch (choice) {
                case 1:
                    if (currentPage > 1) {
                        currentPage--;
                    } 
                    else {
                        System.out.println(" You are already on the first page.");
                        TextUtil.pressEnter();
                    }
                    break;
                case 2:
                    if (currentPage < totalPages) {
                        currentPage++;
                    } 
                    else {
                        System.out.println(" You are already on the last page.");
                        TextUtil.pressEnter();
                    }
                    break;
                case 3:
                	GameManager.getInstance().setCurrentView(previousView);
                	GameManager.getInstance().getCurrentView().show();
                    return;
                default:
                    System.out.println(" Invalid choice.");
                    TextUtil.pressEnter();
            }
        }
    }
}
