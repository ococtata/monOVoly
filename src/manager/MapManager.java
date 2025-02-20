package manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import config.DataConfig;
import config.MapConfig;
import model.Position;
import utility.FileUtil;
import utility.TextUtil;

public class MapManager {
    private static MapManager instance;

    private final List<char[][]> mapList = new ArrayList<>();
    private static char[][] currentMap;

    private MapManager() {}

    public static MapManager getInstance() {
        if (instance == null) instance = new MapManager();
        return instance;
    }

    public void loadAllMaps() {
        File folder = new File(DataConfig.FOLDER_MAP);
        File[] files = folder.listFiles();

        for (File file : files) {
            char[][] map = convertToCharArray(FileUtil.readFile(file.getPath()));
            mapList.add(map);
        }
    }

    public void loadMap(String path) {
    	currentMap = convertToCharArray(FileUtil.readFile(path));
    }

    public void printMap(char[][] currentMap) {
        for (int i = 0; i < currentMap.length; i++) {
        	System.out.print(" ");
            for (int j = 0; j < currentMap[i].length; j++) {
                System.out.print(currentMap[i][j]);
                if(currentMap[i][j] == MapConfig.PLAYER) {
                	GameManager.getInstance().getPlayer().setMapPosition(new Position(j, i));
                }
            }
            System.out.println();
        }
    }
    public char[][] getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(char[][] currentMap) {
        MapManager.currentMap = currentMap;
    }

    public List<char[][]> getMapList() {
        return mapList;
    }

    private char[][] convertToCharArray(List<String> map) {
        int rows = map.size();
        int cols = map.get(0).length();
        char[][] mapArray = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            mapArray[i] = map.get(i).toCharArray();
        }

        return mapArray;
    }
}