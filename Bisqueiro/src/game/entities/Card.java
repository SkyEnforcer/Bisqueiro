package game.entities;

public class Card {
	
	//Find a way to make a static object with CardProperties set to ERROR
	//public static final Card GENERIC_CARD(CardProperties.Figure.ERROR, CardProperties.Suit.ERROR);
	
	private CardProperties.Figure figure;
	private CardProperties.Suit suit;
	private int numericRep;
	
	public Card(CardProperties.Figure figure, CardProperties.Suit suit) {
		this.figure = figure; //Why can't we implicit cast from CardProperties.Figure to CardProperties?
								//Talvez porque o CardProperties.Figure é um field do CardProperties MAS
								//NÃO EXTENDE o CardProperties
		this.suit = suit;
		numericRep = figure.getNumericRepresentation();
	}
	
	public CardProperties.Figure getFigure() {
		return figure;
	}
	
	public CardProperties.Suit getSuit() {
		return suit;
	}
	
	public String getName() {
		return figure.toString() + " de " + suit.toString();
	}
	
	public int getNumberRepresentation() {
		return numericRep;
	}
	
}
