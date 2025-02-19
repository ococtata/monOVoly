package model.card;

import model.entity.Entity;

public abstract class GenericCard {
	private String name;
	private String desc;
	
	public GenericCard() {
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public abstract void onTrigger(Entity piece);
}
