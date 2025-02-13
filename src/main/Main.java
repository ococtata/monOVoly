package main;

import manager.GameManager;
import view.game.feature.NoticeView;

public class Main {
	
	public Main() {
		GameManager.getInstance().startGame();	
		//new NoticeView();
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
