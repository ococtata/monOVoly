package controller.game.feature;

import manager.GameManager;
import model.gacha.GachaCharacter;
import view.game.feature.GachaView;

public class GachaViewController {
	private GachaView gachaView;
	private GachaCharacter gachaCharacter;
	
	public GachaViewController(GachaView gachaView) {
		this.gachaView = gachaView;
		GameManager.getInstance().pausePlayerMovementThread();
	}
	
	public void showCharacterGachaMenu() {
        gachaCharacter.showMenu();
    }

	public void showPendantGachaMenu() {
		
	}
	
	public void exitGachaView() {
		GameManager.getInstance().unPausePlayerMovementThread();
		GameManager.getInstance().setCurrentView(GameManager.getInstance().getCurrentView().getPreviousView()); 
        GameManager.getInstance().getCurrentView().show();
	}
}
