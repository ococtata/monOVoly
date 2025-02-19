package view.game.monovoly;

import config.BoardConfig;
import controller.game.monovoly.MonovolyMapController;
import manager.GameManager;
import model.game.Dice;
import model.game.GameBoard;
import utility.TextUtil;
import view.BaseView;

public class MonovolyMap extends BaseView{
	private MonovolyMapController monovolyMapController;
	private boolean firstTurn;
	public MonovolyMap() {
		this.monovolyMapController = new MonovolyMapController(this);
		GameManager.getInstance().setGameBoard(new GameBoard());
		this.firstTurn = true;
	}
	
	@Override
	public void show() {
		while(true) {
			TextUtil.clearScreen();
			GameManager.getInstance().getGameBoard().printBoard();
			
			TextUtil.printHorizontalBorder(BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));
			
			monovolyMapController.startGame();
			TextUtil.pressEnter();
		}
	}

}
