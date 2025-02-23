package config;

public class CharacterConfig {

    // Character IDs
    public static final String AINZ_ID = "CH001";
    public static final String ALBEDO_ID = "CH002";
    public static final String SHALLTEAR_ID = "CH003";
    public static final String DEMIURGE_ID = "CH004";
    public static final String COCYTUS_ID = "CH005";

    // Ainz Ooal Gown
    public static final int AINZ_REVIVE_MONEY_PERCENTAGE = 100;
    public static final String AINZ_NAME = "Ainz Ooal Gown";
    public static final String AINZ_TITLE = "Undead Overlord";
    public static final String AINZ_SKILL_NAME = "Dark Resurrection";
    public static final String AINZ_SKILL_DESC = String.format("If you go bankrupt, revive with %d%% of your original money once per game. (Prevents losing immediately)", AINZ_REVIVE_MONEY_PERCENTAGE);

    // Albedo
    public static final int ALBEDO_IGNORE_CHANCE_PERCENTAGE = 50;
    public static final int ALBEDO_REDUCE_CHANCE_PERCENTAGE = 100 - ALBEDO_IGNORE_CHANCE_PERCENTAGE;
    public static final String ALBEDO_NAME = "Albedo";
    public static final String ALBEDO_TITLE = "Guardian Overseer";
    public static final String ALBEDO_SKILL_NAME = "Absolute Defense";
    public static final String ALBEDO_SKILL_DESC = String.format("When landing on an opponent's property, %d%% chance to ignore toll or reduce it by 50%%. (Defense against expensive properties)", ALBEDO_IGNORE_CHANCE_PERCENTAGE);

    // Shalltear Bloodfallen
    public static final int SHALLTEAR_TRIBUTE_PERCENTAGE = 10;
    public static final String SHALLTEAR_NAME = "Shalltear Bloodfallen";
    public static final String SHALLTEAR_TITLE = "The Bloody Valkyrie";
    public static final String SHALLTEAR_SKILL_NAME = "Blood Tribute";
    public static final String SHALLTEAR_SKILL_DESC = String.format("When an opponent lands on your property, steal %d%% of their money in addition to the toll. (Boosts passive income)", SHALLTEAR_TRIBUTE_PERCENTAGE);

    // Demiurge
    public static final String DEMIURGE_NAME = "Demiurge";
    public static final String DEMIURGE_TITLE = "Tactical Genius";
    public static final String DEMIURGE_SKILL_NAME = "Infernal Strategy";
    public static final String DEMIURGE_SKILL_DESC = "Upon passing/landing on the start block, force an opponent to teleport to a property of your choice.. (Strategic positioning)";

    // Cocytus
    public static final String COCYTUS_NAME = "Cocytus";
    public static final String COCYTUS_TITLE = "Ruler of Glaciers";
    public static final String COCYTUS_SKILL_NAME = "Glacial Execution";
    public static final String COCYTUS_SKILL_DESC = "If an opponent lands on your property, they skip their next turn due to being 'frozen'. (Crowd control effect)";
}