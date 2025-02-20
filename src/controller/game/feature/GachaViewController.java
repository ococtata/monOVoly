package controller.game.feature;

import manager.GameManager;
import model.gacha.GachaCharacter;
import view.game.feature.GachaView;

public class GachaViewController {
	private GachaView gachaView;
	
	public GachaViewController(GachaView gachaView) {
		this.gachaView = gachaView;
		
		GameManager.getInstance().pausePlayerMovementThread();
	}

	public void showPendantGachaMenu() {
		
	}
	
	public void exitGachaView() {
		GameManager.getInstance().unPausePlayerMovementThread();
		GameManager.getInstance().setCurrentView(GameManager.getInstance().getCurrentView().getPreviousView()); 
        GameManager.getInstance().getCurrentView().show();
	}
}
