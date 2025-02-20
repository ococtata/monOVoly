package controller.game.map;

import config.MapConfig;
import manager.GameManager;
import manager.MapManager;
import model.Position;
import model.entity.Player;
import view.BaseView;
import view.TitleScreenView;

public abstract class BaseMapViewController {
	
	protected Player player = GameManager.getInstance().getPlayer();
	
	public abstract boolean checkCollision(int newX, int newY);

    public boolean handleMovement(char input) {
        Position pos = player.getMapPosition();
        int oldX = pos.getX();
        int oldY = pos.getY();
        int newX = oldX;
        int newY = oldY;

        switch (input) {
            case 'w':
                newY--;
                break;
            case 's':
                newY++;
                break;
            case 'a':
                newX--;
                break;
            case 'd':
                newX++;
                break;
            case 'q':
                TitleScreenView titleScreenView = new TitleScreenView();
                titleScreenView.show();
                return false;
            default:
                break;
        }

        Position newPos = new Position(newX, newY);
        BaseView currentViewBeforeMovement = GameManager.getInstance().getCurrentView(); 

        if (checkCollision(newX, newY)) { 
            BaseView currentViewAfterMovement = GameManager.getInstance().getCurrentView(); 

            if (currentViewAfterMovement != currentViewBeforeMovement) {
                return true; 
            } 
            else {
                updateMap(new Position(oldX, oldY), newPos);
                player.setMapPosition(newPos);
                currentViewAfterMovement.show(); 
                return true;
            }
        } 
        else {
            return false;
        }
    }
    
    protected boolean checkBasicCollision(char[][] map, int newX, int newY) {
    	if(map[newY][newX] == MapConfig.WALL) {
    		return true;
    	}
		return false;
    }

    private void updateMap(Position oldPos, Position newPos) {
        char[][] map = MapManager.getInstance().getCurrentMap();
        map[oldPos.getY()][oldPos.getX()] = ' ';
        map[newPos.getY()][newPos.getX()] = MapConfig.PLAYER;
        MapManager.getInstance().setCurrentMap(map);
    }
}
