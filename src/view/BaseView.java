package view;

import config.GeneralConfig;
import manager.GameManager;
import utility.Scanner;

public abstract class BaseView implements Scanner {
	protected int HEIGHT = GeneralConfig.HEIGHT;
	protected int WIDTH = GeneralConfig.WIDTH;
	
	protected char[][] map;
	
	protected BaseView previousView;
	protected BaseView pendingPreviousView;

	public BaseView() {
		GameManager.getInstance().setCurrentView(this);
		activateView();
	}
	
	protected boolean active = false;
	
	protected void printControls() {
		System.out.println();
		System.out.println("  ═════════════════════════");
		System.out.println("  |        Controls       |");
		System.out.println("  |  W/A/S/D -> Move      |");
		System.out.println("  |  I       -> Inventory |");
		System.out.println("  |  Q       -> Quit      |");
		System.out.println("  ═════════════════════════");

	}
	public abstract void show();
	
	public void deactivateView() {
		this.active = false;
	}
	
	protected void activateView() {
		this.active = true;
	}
	
	public BaseView getPreviousView() {
		return previousView;
	}

	public void setPreviousView(BaseView previousView) {
		this.previousView = previousView;
	}
	
	public char[][] getMap() {
		return map;
	}
}
