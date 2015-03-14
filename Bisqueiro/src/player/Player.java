package player;

import java.util.List;
import java.util.ArrayList;
import game.entities.Card;
import game.entities.Deck;

public class Player {
	
	public static final int STARTING_HANDSIZE = 3;
	
	private List<Card> hand;
	private Deck deck;
	
	public Player(Deck deck) {
		this.deck = deck;
		
		hand = new ArrayList<Card>(STARTING_HANDSIZE);
		for(int i = 0; i < STARTING_HANDSIZE; i++) {
			hand.set(i, drawCard());
		}
	}
	
	public Card drawCard() {
		return deck.getTopCard();
	}
	
	public List<Card> getHand() {
		return hand;
	}
	
	public List<String> getHandNames() {
		List<String> handNames = new ArrayList<String>(STARTING_HANDSIZE);
		
		for(int i = 0; i < STARTING_HANDSIZE; i++) {
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
