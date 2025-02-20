package model.block;

import java.util.List;

import manager.GameManager;
import model.entity.Entity;
import utility.Random;

public class WorldTravelBlock extends GenericBlock implements Random {

	public WorldTravelBlock(String name, String desc, int index) {
		super(name, desc, index);
		setType("World Travel");
	}

	@Override
	public void onLand(Entity piece) {
        List<GenericBlock> blockList = GameManager.getInstance().getGameBoard().getBlockList();
		
        int randomIndex = rand.nextInt(blockList.size());
        GenericBlock randomBlock = blockList.get(randomIndex);
        
        piece.move(this, randomBlock);
        System.out.println(" " + piece.getName() + " was teleported to " + randomBlock.getName() + "!");
        randomBlock.onLand(piece);
	}

}
