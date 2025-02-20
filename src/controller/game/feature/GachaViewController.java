package controller.game.feature;

import manager.GameManager;
import model.gacha.GachaCharacter;
import view.game.feature.GachaView;

public class GachaViewController {
	private GachaView gachaView;
	
	public GachaViewController(GachaView gachaView) {
		this.gachaView = gachaView;
	}
	
	public void exitGachaView() {
		GameManager.getInstance().setCurrentView(GameManager.getInstance().getCurrentView().getPreviousView()); 
        GameManager.getInstance().getCurrentView().show();
	}
}
