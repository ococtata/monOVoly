package view.game.monovoly;

import controller.game.monovoly.MonovolyMapController;
import model.GameBoard;
import utility.TextUtil;
import view.BaseView;

public class MonovolyMap extends BaseView{
	private MonovolyMapController monovolyMapController;
	
	public MonovolyMap() {
		this.monovolyMapController = new MonovolyMapController(this);
	}
	
	@Override
	public void show() {
		TextUtil.clearScreen();
		GameBoard gameBoard = new GameBoard();
		gameBoard.printBoard();
	}

}
