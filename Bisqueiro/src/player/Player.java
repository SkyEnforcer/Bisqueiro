package player;

import java.util.List;
import java.util.ArrayList;
import game.entities.Card;
import game.entities.Deck;

public class Player {
	
	public static final int HANDSIZE = 3;
	
	private List<Card> hand;
	
	public Player(Deck deck) {
		hand = new ArrayList<Card>(HANDSIZE);
		for(int i = 0; i < HANDSIZE; i++) {
			hand.set(i, drawCard(deck));
		}
	}
	
	public Card drawCard(Deck deck) {
		return deck.getTopCard();
	}
	
	public List<Card> getHand() {
		return hand;
	}
	
	public List<String> getHandNames() {
		List<String> handNames = new ArrayList<String>(HANDSIZE);
		
		for(int i = 0; i < HANDSIZE; i++) {
			handNames.set(i, hand.get(i).getName());
		}
		
		return handNames;
		
	}
	
	public Card playCard(int i) {
		Card card = hand.get(i);
		hand.remove(i);
		return card;
	}
	
}
