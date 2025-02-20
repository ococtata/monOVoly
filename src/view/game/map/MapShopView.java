package view.game.map;

import config.DataConfig;
import controller.game.map.MapShopViewController;
import manager.GameManager;
import manager.MapManager;
import utility.TextUtil;
import view.BaseView;
import view.PlayerGUI;

public class MapShopView extends BaseView implements PlayerGUI{
	private MapShopViewController mapShopViewController;
	
	public MapShopView() {
		this.mapShopViewController = new MapShopViewController(this);
		MapManager.getInstance().loadMap(DataConfig.FILE_MAP_SHOP);
		map = MapManager.getInstance().getCurrentMap();
	}
	@Override
	public void show() {
        TextUtil.clearScreen();
        printTopBar();
        MapManager.getInstance().printMap(MapManager.getInstance().getCurrentMap());
        System.out.println(" Use W/A/S/D to move, or Q to quit!");
    }
	
	public MapShopViewController getMapShopViewController() {
		return mapShopViewController;
	}
	
}
