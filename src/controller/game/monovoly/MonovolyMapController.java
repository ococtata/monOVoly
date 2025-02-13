package controller.game.monovoly;

import manager.GameManager;
import view.game.monovoly.MonovolyMap;

public class MonovolyMapController {
	private MonovolyMap monovolyMap;
	
	public MonovolyMapController(MonovolyMap monovolyMap) {
		this.monovolyMap = monovolyMap;
		GameManager.getInstance().pausePlayerMovementThread();
	}
}
