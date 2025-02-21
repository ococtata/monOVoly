package controller.game.map;

import config.MapConfig;
import manager.GameManager;
import manager.MapManager;
import model.Position;
import view.BaseView;
import view.game.feature.TradeView;
import view.game.map.MapTradeView;

public class MapTradeViewController extends BaseMapViewController{
	private MapTradeView mapTradeView;
	
	public MapTradeViewController(MapTradeView mapTradeView) {
		this.mapTradeView = mapTradeView;
	}

	@Override
	public boolean checkCollision(int newX, int newY) {
		char[][] map = MapManager.getInstance().getCurrentMap();
		
		if(checkBasicCollision(map, newX, newY)) {
        	return false;
        }
		
		if(map[newY][newX] == MapConfig.TRADE) {
			TradeView tradeView = new TradeView();
			tradeView.setPreviousView(mapTradeView);
			GameManager.getInstance().setCurrentView(tradeView);
			tradeView.show();
			return false;
		}
		else if(newX == 0 && map[newY][newX] == ' ') {
			BaseView previousView = mapTradeView.getPreviousView();
			GameManager.getInstance().setCurrentView(previousView);
            previousView.show();
            
			return false;
		}
		return true;
		
	}
}
