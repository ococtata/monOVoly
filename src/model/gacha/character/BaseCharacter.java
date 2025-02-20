package model.gacha.character;

import model.entity.Entity;

public abstract class BaseCharacter implements CharacterSkills {
	private static int characterCounter = 0;

    private String id;
    private String name;
    private String title;
    private String skillName;
    private String skillDesc;
    private String nameColor;

    public BaseCharacter() {
        this.id = String.format("CH%03d", ++characterCounter);
    }

    public void useSkill(Entity entity) {
        System.out.printf(" %s's %s uses %s!\n", entity.getName(), getName(), getSkillName());
        System.out.println();
        System.out.println(" Desc: " + getSkillDesc());
        System.out.println();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSkillDesc() {
        return skillDesc;
    }

    public String getTitle() {
        return title;
    }

    public String getSkillName() {
        return skillName;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setSkillDesc(String skillDesc) {
        this.skillDesc = skillDesc;
    }

    protected void setTitle(String title) {
        this.title = title;
    }

    protected void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getNameColor() {
        return nameColor;
    }

    public void setNameColor(String nameColor) {
        this.nameColor = nameColor;
    }
}
