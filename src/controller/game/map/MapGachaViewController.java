package controller.game.map;

import config.MapConfig;
import manager.GameManager;
import manager.MapManager;
import model.Position;
import model.gacha.GachaCharacter;
import view.BaseView;
import view.game.feature.GachaView;
import view.game.feature.LeaderboardView;
import view.game.map.MapGachaView;

public class MapGachaViewController extends BaseMapViewController{
	private MapGachaView mapGachaView;
	
	public MapGachaViewController(MapGachaView mapGachaView) {
		this.mapGachaView = mapGachaView;
	}

	@Override
	public boolean checkCollision(int newX, int newY) {
		char[][] map = MapManager.getInstance().getCurrentMap();
		
		if(checkBasicCollision(map, newX, newY)) {
        	return false;
        }
		
		if(map[newY][newX] == MapConfig.GACHA) {
			GachaView gachaView = new GachaView();
			gachaView.setPreviousView(mapGachaView);
			GameManager.getInstance().setCurrentView(gachaView);
			gachaView.show();
			
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
