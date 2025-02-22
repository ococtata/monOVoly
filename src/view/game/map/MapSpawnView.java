package view.game.map;

import config.DataConfig;
import controller.game.map.MapSpawnViewController;
import manager.GameManager;
import manager.MapManager;
import utility.TextUtil;
import view.BaseView;
import view.PlayerGUI;

public class MapSpawnView extends BaseView implements PlayerGUI{
	private MapSpawnViewController mapSpawnViewController;
		
	public MapSpawnView() {
		this.mapSpawnViewController = new MapSpawnViewController(this);
		MapManager.getInstance().loadMap(DataConfig.FILE_MAP_SPAWN);
		map = MapManager.getInstance().getCurrentMap();
		GameManager.getInstance().runGame();
	}

	@Override
	public void show() {
        TextUtil.clearScreen();
        printTopBar();
        MapManager.getInstance().printMap(MapManager.getInstance().getCurrentMap());
        printControls();
    }

	public MapSpawnViewController getMapSpawnViewController() {
		return mapSpawnViewController;
	}
}
