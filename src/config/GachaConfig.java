package config;

public class GachaConfig {
	public enum Rarity {
        COMMON(ColorConfig.LIGHT_GREY),
        RARE(ColorConfig.LIGHT_BLUE),
        EPIC(ColorConfig.LIGHT_PURPLE),
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
}
