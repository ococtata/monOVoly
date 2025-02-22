package view.game.map;

import config.DataConfig;
import controller.game.map.MapGachaViewController;
import controller.game.map.MapSpawnViewController;
import manager.GameManager;
import manager.MapManager;
import utility.TextUtil;
import view.BaseView;
import view.PlayerGUI;

public class MapGachaView extends BaseView implements PlayerGUI{
	private MapGachaViewController mapGachaViewController;
	
	public MapGachaView() {
		this.mapGachaViewController = new MapGachaViewController(this);
		MapManager.getInstance().loadMap(DataConfig.FILE_MAP_GACHA);
		map = MapManager.getInstance().getCurrentMap();
	}
	
	@Override
	public void show() {
        TextUtil.clearScreen();
        printTopBar();
        MapManager.getInstance().printMap(MapManager.getInstance().getCurrentMap());
        printControls();
    }

	public MapGachaViewController getMapGachaViewController() {
		return mapGachaViewController;
	}
}
