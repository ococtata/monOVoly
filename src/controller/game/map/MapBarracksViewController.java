package controller.game.map;

import config.MapConfig;
import manager.GameManager;
import manager.MapManager;
import model.Position;
import view.BaseView;
import view.game.map.MapBarracksView;

public class MapBarracksViewController extends BaseMapViewController{
	private MapBarracksView mapBarracksView;
	
	public MapBarracksViewController(MapBarracksView mapBarracksView) {
		this.mapBarracksView = mapBarracksView;
	}

	@Override
	public boolean checkCollision(int newX, int newY) {
		char[][] map = MapManager.getInstance().getCurrentMap();
		
		if(map[newY][newX] == MapConfig.BED) {
			// bed
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
