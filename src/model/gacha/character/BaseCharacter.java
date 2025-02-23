package model.gacha.character;

import java.util.List;

import config.BoardConfig;
import manager.GameManager;
import manager.MaterialLoaderManager;
import model.entity.Entity;
import model.entity.inventory.PlayerInventory;
import model.gacha.material.CharacterMaterial;
import utility.TextUtil;

public abstract class BaseCharacter implements CharacterSkills {
    private String id;
    private String name;
    private String title;
    private String skillName;
    private String skillDesc;
    private String nameColor;
    
    private List<CharacterMaterial> requiredMaterials;

    public BaseCharacter() {
    }

    public void useSkill(Entity entity) {
        System.out.printf(" %s's %s uses %s!\n", entity.getName(), getName(), getSkillName());
        System.out.println();
        System.out.println(" Desc: " + getSkillDesc());
        System.out.println();
        TextUtil.pressEnter();
        System.out.println();
        TextUtil.printHorizontalBorder(
				BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));
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

	public boolean isTradable() {
		PlayerInventory inventory = (PlayerInventory) GameManager.getInstance().getPlayer().getInventory();
		for(BaseCharacter character : inventory.getCharacterList()) {
			if(character == this) {
				return false;
			}
		}
		return true;
	}

	public List<CharacterMaterial> getRequiredMaterials() {
		return requiredMaterials;
	}
	
	public void loadRequiredMaterials() {
	    this.requiredMaterials = MaterialLoaderManager.getInstance().getRequiredMaterials(this);
	}

	protected void setId(String id) {
		this.id = id;
	}
}
