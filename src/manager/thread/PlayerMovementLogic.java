package manager.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import config.MapConfig;
import manager.GameManager;
import manager.MapManager;
import model.Position;
import model.entity.Player;
import utility.Scanner;
import view.BaseView;
import view.TitleScreenView;
import view.game.feature.NoticeView;
import view.game.map.MapBarracksView;
import view.game.map.MapGachaView;
import view.game.map.MapShopView;
import view.game.map.MapSpawnView;

public class PlayerMovementLogic implements Runnable, Scanner {
	private boolean active = true;
    private Player player;
    private boolean paused = false;

    public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public PlayerMovementLogic(Player player) {
        this.player = player;
    }

    public void deactivate() {
        active = false;
    }

    @Override
    public void run() {
    	try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (active) {
            	if(paused) {
            		continue;
            	}
                if (System.in.available() > 0) {
                    char input = (char) System.in.read();
                    handleInput(input);
                }
                try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
            }
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void handleInput(char input) {
        Position pos = player.getMapPosition();
        int oldX = pos.getX();
        int oldY = pos.getY();
        int newX = oldX;
        int newY = oldY;

        switch(input) {
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
        	deactivate();
        	return;
        default:
            return;
		}

        Position newPos = new Position(newX, newY);
        if (handleCollision(newPos)) {
            updateMap(new Position(oldX, oldY), newPos);
            player.setMapPosition(newPos);
        }
        
        try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        BaseView currentView = GameManager.getInstance().getCurrentView();
        currentView.show();
    }

    private void updateMap(Position oldPos, Position newPos) {
        char[][] map = MapManager.getInstance().getCurrentMap();
        map[oldPos.getY()][oldPos.getX()] = ' ';
        map[newPos.getY()][newPos.getX()] = MapConfig.PLAYER;
        MapManager.getInstance().setCurrentMap(map);
    }

    private boolean handleCollision(Position pos) {
    	boolean valid = true;
        char[][] currentMap = MapManager.getInstance().getCurrentMap();
        BaseView currentView = GameManager.getInstance().getCurrentView();
        
        int x = pos.getX();
        int y = pos.getY();

        if (x < 0 || y < 0 || y >= currentMap.length || x >= currentMap[y].length
                || currentMap[y][x] == MapConfig.WALL) {
            valid = false;
        }
        else if(currentView instanceof MapSpawnView) {
        	valid = ((MapSpawnView) currentView).getMapSpawnViewController().checkCollision(x, y);
        }
        else if(currentView instanceof MapGachaView) {
        	valid = ((MapGachaView) currentView).getMapGachaViewController().checkCollision(x, y);
        }
        else if(currentView instanceof MapBarracksView) {
        	valid = ((MapBarracksView) currentView).getMapBarracksViewController().checkCollision(x, y);
        }
        else if(currentView instanceof MapShopView) {
        	valid = ((MapShopView) currentView).getMapShopViewController().checkCollision(x, y);
        }

        return valid;
    }
}