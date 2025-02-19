package config;

public class BoardConfig {
	public static final String BOARD_BORDER = "#";
	
	public static final int BOARD_WIDTH = 9;
	public static final int BOARD_HEIGHT = 9;
	public static final int BLOCK_WIDTH = 5;
	public static final int BLOCK_HEIGHT = 3;
	
	public static final String PLAYER_PIECE = "O";
	public static final String ENEMY_PIECE = "V";
	
	public static final String CLEAR_SCREEN = "\033[H\033[2J";
    public static final String RESET_CURSOR = "\033[H";
    public static final String MOVE_CURSOR = "\033[%d;%dH";
}
