package view.game.map;

import config.DataConfig;
import controller.game.map.MapBarracksViewController;
import manager.GameManager;
import manager.MapManager;
import utility.TextUtil;
import view.BaseView;
import view.PlayerGUI;

public class MapBarracksView extends BaseView implements PlayerGUI{
	private MapBarracksViewController mapBarracksViewController;
	
	public MapBarracksView() {
		this.mapBarracksViewController = new MapBarracksViewController(this);
		MapManager.getInstance().loadMap(DataConfig.FILE_MAP_BARRACKS);		
		map = MapManager.getInstance().getCurrentMap();
	}

	@Override
	public void show() {
		TextUtil.clearScreen();
		printTopBar();
		MapManager.getInstance().printMap(MapManager.getInstance().getCurrentMap());
		System.out.println(" Use W/A/S/D to move, or Q to quit!");	
		GameManager.getInstance().runThreads();
		while(true) {
			if(!active) break;
		}
	}

	public MapBarracksViewController getMapBarracksViewController() {
		return mapBarracksViewController;
	}
}
