package main;

import manager.GameManager;

public class Main {
	
	public Main() {
		GameManager.getInstance().startGame();	
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
