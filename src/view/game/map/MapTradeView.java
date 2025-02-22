package view.game.map;

import config.DataConfig;
import controller.game.map.MapTradeViewController;
import manager.GameManager;
import manager.MapManager;
import utility.TextUtil;
import view.BaseView;
import view.PlayerGUI;

public class MapTradeView extends BaseView implements PlayerGUI{
	private MapTradeViewController mapTradeViewController;
	
	public MapTradeView() {
		this.mapTradeViewController = new MapTradeViewController(this);
		MapManager.getInstance().loadMap(DataConfig.FILE_MAP_BARRACKS);		
		map = MapManager.getInstance().getCurrentMap();
	}

	@Override
	public void show() {
        TextUtil.clearScreen();
        printTopBar();
        MapManager.getInstance().printMap(MapManager.getInstance().getCurrentMap());
        printControls();
    }

	public MapTradeViewController getMapTradeViewController() {
		return mapTradeViewController;
	}
}
