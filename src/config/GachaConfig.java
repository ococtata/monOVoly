package config;

public class GachaConfig {
	public enum Rarity {
        COMMON(ColorConfig.GREEN),
        RARE(ColorConfig.BLUE),
        EPIC(ColorConfig.PURPLE),
        LEGENDARY(ColorConfig.GOLD);

        private final String color;

        Rarity(String color) {
            this.color = color;
        }

        public String getColor() {
            return color;
        }
    }
	
	public static final int CHARACTER_GACHA_COST = 150;
	public static final int PENDANT_GACHA_COST = 100;
	public static final int NUM_CARDS_PER_ROLL = 4;
	
	public static final double LEGENDARY_CHANCE = 0.01;
    public static final double EPIC_CHANCE = 0.05;
    public static final double RARE_CHANCE = 0.15;
}
