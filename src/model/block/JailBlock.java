package model.block;

import java.util.ArrayList;
import java.util.List;

import model.entity.Entity;

public class JailBlock extends GenericBlock {

	public JailBlock(String name, String desc, int index) {
		super(name, desc, index);
		setType("Jail");
	}

	@Override
	public void onLand(Entity piece) {
		System.out.println(" Currently in jail: " + (!isThereAnyInJail() ? "no one" : getInJail()));
	}
	
	private boolean isThereAnyInJail() {
		for(Entity entity : getPiecesOnBlock()) {
			if(entity.isInJail()) {
				return true;
			}
		}
		return false;
	}
	
	private String getInJail() {
		String output = "";
		List<Entity> isJailedList = new ArrayList<Entity>();
		
		for(Entity entity : getPiecesOnBlock()) {
			if(entity.isInJail()) {
				isJailedList.add(entity);		
			}
		}
		
		int counter = 1;
		for(Entity entity : isJailedList) {
			output += entity.getName();
			
			if(counter != isJailedList.size()) {
				output += ", ";
			}
			counter++;
		}
		
		
		
		return output;
	}
	
}
