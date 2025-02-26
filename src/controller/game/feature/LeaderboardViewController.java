package controller.game.feature;

import java.io.File;
import java.util.List;

import config.DataConfig;
import manager.GameManager;
import utility.FileUtil;
import utility.Random;
import view.game.feature.LeaderboardView;

public class LeaderboardViewController implements Random{
	private LeaderboardView leaderboardView;
	
	public LeaderboardViewController(LeaderboardView leaderboardView) {
		this.leaderboardView = leaderboardView;
	}
	
	public String stripAnsiCodes(String input) {
	    return input.replaceAll("\u001B\\[[;\\d]*m", "");
	}
}
