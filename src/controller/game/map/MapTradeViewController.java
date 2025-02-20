package controller.game.map;

import config.MapConfig;
import manager.GameManager;
import manager.MapManager;
import model.Position;
import view.BaseView;
import view.game.map.MapTradeView;

public class MapTradeViewController extends BaseMapViewController{
	private MapTradeView mapBarracksView;
	
	public MapTradeViewController(MapTradeView mapBarracksView) {
		this.mapBarracksView = mapBarracksView;
	}

	@Override
	public boolean checkCollision(int newX, int newY) {
		char[][] map = MapManager.getInstance().getCurrentMap();
		
		if(checkBasicCollision(map, newX, newY)) {
        	return false;
        }
		
		if(map[newY][newX] == MapConfig.TRADE) {
			return false;
		}
		else if(newX == 0 && map[newY][newX] == ' ') {
			BaseView previousView = mapBarracksView.getPreviousView();
			GameManager.getInstance().setCurrentView(previousView);
            previousView.show();
            
			return false;
		}
		return true;
		
	}
}
