package model.block;

import java.util.ArrayList;
import java.util.List;

import model.card.FestivalTimeCard;
import model.card.FreeUpgradeCard;
import model.card.GenericCard;
import model.card.SendToJailCard;
import model.card.SendToPropertyCard;
import model.card.TornadoDisasterCard;
import model.entity.Entity;
import utility.Random;
import utility.TextUtil;

public class CardBlock extends GenericBlock implements Random {
	
	private List<GenericCard> cardList;

	public CardBlock(String name, String desc, int index) {
		super(name, desc, index);
		setType("Card");
		this.cardList = initializeCards();
	}

	@Override
    public void onLand(Entity piece) {
		int randomIndex = rand.nextInt(cardList.size());
        GenericCard card = cardList.get(2);
        setName(card.getName());
        setDesc(card.getDesc());
        
        System.out.println(" Card Name: " + getName());
        System.out.println(" Card Desc: " + getDesc());
        System.out.println();
        TextUtil.pressEnter();
        System.out.println();
        card.onTrigger(piece); 
    }
	
	private List<GenericCard> initializeCards() {
		List<GenericCard> cards = new ArrayList<GenericCard>();
		
		cards.add(new FestivalTimeCard());
		cards.add(new FreeUpgradeCard());
		cards.add(new SendToJailCard());
		cards.add(new SendToPropertyCard());
		cards.add(new TornadoDisasterCard());
		
		return cards;
	}

}
