package player;

import java.util.List;
import java.util.ArrayList;
import game.entities.Card;
import game.entities.Deck;

public class Player {
	
	public static final int STARTING_HANDSIZE = 3;
	
	private List<Card> hand;
	private Deck deck;
	private String name;
	private int points;
	
	public Player(String name, Deck deck) {
		this.name = name;
		this.deck = deck;
		points = 0;
		
		hand = new ArrayList<Card>(STARTING_HANDSIZE);
		for(int i = 0; i < STARTING_HANDSIZE; i++) {
			drawCard();
		}
	}
	
	public void drawCard() {
		hand.add(deck.getTopCard());
	}
	
	public List<Card> getHand() {
		return hand;
	}
	
	public List<String> getHandNames() {
		List<String> handNames = new ArrayList<String>(hand.size());
		
		for(int i = 0; i < hand.size(); i++) {
			//handNames.set(i, hand.get(i).getName());
			handNames.add(hand.get(i).getName());
		}
		
		return handNames;
		
	}
	
	public Card playCard(int i) {
		Card card = hand.get(i);
		hand.remove(i);
		return card;
	}
	
	public String getName() {
		return name;
	}
	
	public int getPoints() {
		return points;
	}
	
	public void addPoints(int points) {
		this.points += points;
	}
	
}
