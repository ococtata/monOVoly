package controller.game.map;

import config.MapConfig;
import manager.GameManager;
import manager.MapManager;
import view.BaseView;
import view.game.map.MapShopView;

public class MapShopViewController extends BaseMapViewController{
	private MapShopView mapShopView;
	
	public MapShopViewController(MapShopView mapShopView) {
		this.mapShopView = mapShopView;
		
	}

	@Override
	public boolean checkCollision(int newX, int newY) {
		char[][] map = MapManager.getInstance().getCurrentMap();
		
		if(checkBasicCollision(map, newX, newY)) {
        	return false;
        }
		
		if (newY == 0 && map[newY][newX] == ' ') {
            BaseView previousView = mapShopView.getPreviousView();
            GameManager.getInstance().setCurrentView(previousView);
            previousView.show();
            
            return true;
        }
		else if(map[newY][newX] == MapConfig.SHOP) {
			return false;
		}
		
		return true;
	}
}
