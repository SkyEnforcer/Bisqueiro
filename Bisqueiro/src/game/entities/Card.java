package game.entities;

public class Card {
	
	private CardProperties.Figure figure;
	private CardProperties.Suit suit;
	
	public Card(CardProperties.Figure figure, CardProperties.Suit suit) {
		this.figure = figure; //Why can't we implicit cast from CardProperties.Figure to CardProperties?
								//Talvez porque o CardProperties.Figure é um field do CardProperties MAS
								//NÃO EXTENDE o CardProperties
		this.suit = suit;
	}
	
	public CardProperties.Figure getFigure() {
		return figure;
	}
	
	public CardProperties.Suit getSuit() {
		return suit;
	}
	
}
