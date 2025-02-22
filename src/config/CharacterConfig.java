package config;

public class CharacterConfig {

	public static final int AINZ_REVIVE_MONEY_PERCENTAGE = 50;
	public static final int ALBEDO_IGNORE_CHANCE_PERCENTAGE = 50;
	public static final int ALBEDO_REDUCE_CHANCE_PERCENTAGE = 100 - ALBEDO_IGNORE_CHANCE_PERCENTAGE;
	public static final int SHALLTEAR_TRIBUTE_PERCENTAGE = 10;
	
    public static final String AINZ_NAME = "Ainz Ooal Gown";
    public static final String AINZ_TITLE = "Undead Overlord";
    public static final String AINZ_SKILL_NAME = "Dark Resurrection";
    public static final String AINZ_SKILL_DESC = String.format("If you go bankrupt, revive with %d%% of your original money once per game. (Prevents losing immediately)", AINZ_REVIVE_MONEY_PERCENTAGE);

    public static final String ALBEDO_NAME = "Albedo";
    public static final String ALBEDO_TITLE = "Guardian Overseer";
    public static final String ALBEDO_SKILL_NAME = "Absolute Defense";
    public static final String ALBEDO_SKILL_DESC = String.format("When landing on an opponent's property, %d%% chance to ignore rent or reduce it by 50%%. (Defense against expensive properties)", ALBEDO_IGNORE_CHANCE_PERCENTAGE);

    public static final String SHALLTEAR_NAME = "Shalltear Bloodfallen";
    public static final String SHALLTEAR_TITLE = "The Bloody Valkyrie";
    public static final String SHALLTEAR_SKILL_NAME = "Blood Tribute";
    public static final String SHALLTEAR_SKILL_DESC = String.format("When an opponent lands on your property, steal %d%% of their money instead of just rent. (Boosts passive income)", SHALLTEAR_TRIBUTE_PERCENTAGE);

    public static final String DEMIURGE_NAME = "Demiurge";
    public static final String DEMIURGE_TITLE = "Tactical Genius";
    public static final String DEMIURGE_SKILL_NAME = "Infernal Strategy";
    public static final String DEMIURGE_SKILL_DESC = "When passing the start block, choose a player to teleport to any property you own. (Strategic positioning)";

    public static final String COCYTUS_NAME = "Cocytus";
    public static final String COCYTUS_TITLE = "Ruler of Glaciers";
    public static final String COCYTUS_SKILL_NAME = "Glacial Execution";
    public static final String COCYTUS_SKILL_DESC = "If an opponent lands on your property, they skip their next turn due to being 'frozen'. (Crowd control effect)";
}
