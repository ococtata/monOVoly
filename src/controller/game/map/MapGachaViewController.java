package controller.game.map;

import config.MapConfig;
import manager.GameManager;
import manager.MapManager;
import model.Position;
import view.BaseView;
import view.game.feature.NoticeView;
import view.game.map.MapGachaView;

public class MapGachaViewController extends BaseMapViewController{
	private MapGachaView mapGachaView;
	
	public MapGachaViewController(MapGachaView mapGachaView) {
		this.mapGachaView = mapGachaView;
	}

	@Override
	public boolean checkCollision(int newX, int newY) {
		char[][] map = MapManager.getInstance().getCurrentMap();
		
		if(map[newY][newX] == MapConfig.GACHA) {
			// gacha roll here
			return false;
		}
		else if(newX == map[0].length - 1 && map[newY][newX] == ' ') {
			BaseView previousView = mapGachaView.getPreviousView();
			GameManager.getInstance().setCurrentView(previousView);
            previousView.show();
            
			return false;
		}
		return true;
	}
}
