package game.entities;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Deck {
	public static final int SIZE = 40;
	
	//card at index 0 is the bottom card, card at index deck.size() is the top card
	private List<Card> deck;
	private Card trumpCard;
	
	public Deck() {
		deck = new ArrayList<Card>(SIZE);
		populate();
		shuffle(); shuffle(); shuffle(); //shuffles thrice just to be sure
		trumpCard = deck.get(0);
	}

	//This is a destructive operation, meaning it will destroy the card that's returned
	//Returns the card at the top of the deck
	public Card getTopCard() {
		int cardIndex = deck.size() - 1;
		Card card = deck.get(cardIndex);
		deck.remove(cardIndex);
		return card;
	}
	
	//This operation only shows the card at the bottom of the deck, but does not destroy it
	public Card getTrumpCard() {
		return trumpCard;
	}
	
	public int getSize() {
		return deck.size();
	}
	
	//will throw IndexOutOfBoundsException if more than 40 cards are added to the deck
	//lambda expressions or anonymous classes might help make this more syntactic
	//Populates the deck with every card, in order
	private void populate() {
		//Iterates over the suits
		for(int i = 0; i < 4; i++) {
			CardProperties.Suit suit;
			
			//Chooses a suit
			switch(i) {
			case 0: suit = CardProperties.Suit.SPADES;
				break;
			case 1: suit = CardProperties.Suit.HEARTS;
				break;
			case 2: suit = CardProperties.Suit.DIAMONDS;
				break;
			case 3: suit = CardProperties.Suit.CLUBS;
				break;
			default: suit = CardProperties.Suit.ERROR;
				break;
			}
			
			chooseFigure(suit);
		}
	}
	
	//lambda expressions or anonymous classes in populate() would invalidate the need for this method
	private void chooseFigure(CardProperties.Suit suit) {
		for(int n = 0; n < 10; ++n) {
			CardProperties.Figure figure;
			
			switch(n) {
			case 0: figure = CardProperties.Figure.ACE;
				break;
			case 1: figure = CardProperties.Figure.TWO;
				break;
			case 2: figure = CardProperties.Figure.THREE;
				break;
			case 3: figure = CardProperties.Figure.FOUR;
				break;
			case 4: figure = CardProperties.Figure.FIVE;
				break;
			case 5: figure = CardProperties.Figure.SIX;
				break;
			case 6: figure = CardProperties.Figure.SEVEN;
				break;
			case 7: figure = CardProperties.Figure.JACK;
				break;
			case 8: figure = CardProperties.Figure.QUEEN;
				break;
			case 9: figure = CardProperties.Figure.KING;
				break;
			default: figure = CardProperties.Figure.ERROR;
				break;
			}
			
			//Create and add new card
			deck.add(new Card(figure, suit));
		}
	}
	
	//Shuffles the deck
	private void shuffle() {
		Random rnd = new Random();
		//o elemento na posição 0 nunca tentar TROCAR com nenhum, mas pode ser TROCADO com algum
		for(int i = SIZE - 1; i > 0; --i) {
			swapCards(i, rnd.nextInt(i));
		}
		
		//Garantir que a carta 0 ao menos tenta trocar com alguma
		swapCards(0, rnd.nextInt(deck.size() - 1));
	}
	
	//Swaps two cards
	//0 < index1 < deck.size() && 0 < index2 < deck.size()
	private void swapCards(int index1, int index2) {
		Card firstCard = deck.get(index1);
		Card secondCard = deck.get(index2);
		deck.set(index1, secondCard);
		deck.set(index2, firstCard);
	}
	
}
