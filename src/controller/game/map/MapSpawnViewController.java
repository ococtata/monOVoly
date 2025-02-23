package controller.game.map;

import config.ColorConfig;
import config.GameConfig;
import config.MapConfig;
import manager.GameManager;
import manager.MapManager;
import model.entity.Player;
import utility.TextUtil;
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
	    Player player = GameManager.getInstance().getPlayer();

	    if (checkBasicCollision(map, newX, newY)) {
	        return false;
	    }

	    if (map[newY][newX] == MapConfig.NOTICE) {
	        NoticeView noticeView = new NoticeView();
	        noticeView.setPreviousView(mapSpawnView);
	        GameManager.getInstance().setCurrentView(noticeView);
	        return false;
	    } 
	    else if (map[newY][newX] == MapConfig.GATE) {
	    	System.out.println();
	        TextUtil.printHorizontalBorder(50);
	        System.out.println(" Monovoly requires " + ColorConfig.YELLOW + GameConfig.ENERGY_COST_TO_PLAY + ColorConfig.RESET + " energy to play.\n");
	        System.out.println(" Would you like to enter " + ColorConfig.GOLD + "Monovoly" + ColorConfig.RESET + "?");
	        System.out.println(" 1. Enter");
	        System.out.println(" 2. Cancel");
	        System.out.print(" >> ");

	        String input = TextUtil.scan.nextLine().trim();

	        if (input.equals("1")) {
	            if (player.getCurrentEnergy() >= GameConfig.ENERGY_COST_TO_PLAY) {
	                player.setCurrentEnergy(player.getCurrentEnergy() - GameConfig.ENERGY_COST_TO_PLAY);
	                System.out.println(" " + ColorConfig.YELLOW + GameConfig.ENERGY_COST_TO_PLAY + ColorConfig.RESET + " energy consumed. Entering Monovoly!");
	                System.out.println();
	                
	                TextUtil.pressEnter();
	                
	                MonovolyMap monovolyMap = new MonovolyMap();
	                monovolyMap.setPreviousView(mapSpawnView);
	                GameManager.getInstance().setCurrentView(monovolyMap);
	                GameManager.getInstance().setFirstTurn(true);
	                GameManager.getInstance().setPlayerTurn(false);
	            } 
	            else {
	                System.out.println(" Insufficient energy. Return when you have enough.");
	                TextUtil.pressEnter();
	            }
	        } 
	        else if (input.equals("2")) {
	            return false;
	        } 
	        else {
	            System.out.println(" Invalid input. Please select 1 or 2.");
	            TextUtil.pressEnter();
	        }
	        return false;
	    } 
	    else if (newX == 0 && map[newY][newX] == ' ') {
	        MapGachaView mapGachaView = new MapGachaView();
	        mapGachaView.setPreviousView(mapSpawnView);
	        GameManager.getInstance().setCurrentView(mapGachaView);
	        return false;
	    } 
	    else if (newX == map[0].length - 1 && map[newY][newX] == ' ') {
	        MapTradeView mapBarracksView = new MapTradeView();
	        mapBarracksView.setPreviousView(mapSpawnView);
	        GameManager.getInstance().setCurrentView(mapBarracksView);
	        return false;
	    } 
	    else if (newY == map.length - 1 && map[newY][newX] == ' ') {
	        MapShopView mapShopView = new MapShopView();
	        mapShopView.setPreviousView(mapSpawnView);
	        GameManager.getInstance().setCurrentView(mapShopView);
	        return false;
	    }
	    return true;
	}
}
