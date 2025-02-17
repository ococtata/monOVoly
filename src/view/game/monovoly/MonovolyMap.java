package view.game.monovoly;

import config.BoardConfig;
import controller.game.monovoly.MonovolyMapController;
import manager.GameManager;
import model.Dice;
import model.GameBoard;
import utility.TextUtil;
import view.BaseView;

public class MonovolyMap extends BaseView{
	private MonovolyMapController monovolyMapController;
	
	public MonovolyMap() {
		this.monovolyMapController = new MonovolyMapController(this);
		GameManager.getInstance().setGameBoard(new GameBoard());
	}
	
	@Override
	public void show() {
		while(true) {
			TextUtil.clearScreen();
			GameManager.getInstance().getGameBoard().printBoard();
			
			TextUtil.printHorizontalBorder(BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));
			System.out.println();
			
			monovolyMapController.startGame();
			TextUtil.pressEnter();
		}
	}

}
