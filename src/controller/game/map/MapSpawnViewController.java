package controller.game.map;

import config.MapConfig;
import manager.GameManager;
import manager.MapManager;
import view.game.feature.NoticeView;
import view.game.map.MapTradeView;
import view.game.map.MapGachaView;
import view.game.map.MapShopView;
import view.game.map.MapSpawnView;
import view.game.monovoly.MonovolyMap;

public class MapSpawnViewController extends BaseMapViewController {
	private MapSpawnView mapSpawnView;
	
	public MapSpawnViewController(MapSpawnView mapSpawnView) {
		this.mapSpawnView = mapSpawnView;
		GameManager.getInstance().startThreads();
	}

	public boolean checkCollision(int newX, int newY) {
        char[][] map = MapManager.getInstance().getCurrentMap();
		
        if(checkBasicCollision(map, newX, newY)) {
        	return false;
        }
        
		if(map[newY][newX] == MapConfig.NOTICE) {
			NoticeView noticeView = new NoticeView();
            noticeView.setPreviousView(mapSpawnView);
            GameManager.getInstance().setCurrentView(noticeView);
            
			return false;
		}
		else if(map[newY][newX] == MapConfig.GATE) {
			MonovolyMap monovolyMap = new MonovolyMap();
			monovolyMap.setPreviousView(mapSpawnView);
			GameManager.getInstance().setCurrentView(monovolyMap);
			
			GameManager.getInstance().setFirstTurn(true);
			GameManager.getInstance().setPlayerTurn(false);
			
			return false;
		}
		else if(newX == 0 && map[newY][newX] == ' ') {
			MapGachaView mapGachaView = new MapGachaView();
			mapGachaView.setPreviousView(mapSpawnView);
			GameManager.getInstance().setCurrentView(mapGachaView);
            
			return false;
		}
		else if(newX == map[0].length - 1 && map[newY][newX] == ' ') {
			MapTradeView mapBarracksView = new MapTradeView();
			mapBarracksView.setPreviousView(mapSpawnView);
			GameManager.getInstance().setCurrentView(mapBarracksView);
            
			return false;
		}
		else if(newY == map.length - 1 && map[newY][newX] == ' ') {
			MapShopView mapShopView = new MapShopView();
			mapShopView.setPreviousView(mapSpawnView);
			GameManager.getInstance().setCurrentView(mapShopView);
			
			return false;
		}
		return true;
	}
}
