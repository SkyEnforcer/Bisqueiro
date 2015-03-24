package game.entities;

/**
 * The <code>Card</code> class maintains all the information
 * required to represent a regular playing card, such as its
 * suit, figure, point value, title and relative "worth".
 * 
 * @author SexoBode
 * @version 1.0
 */
public class Card {
	
	/**
	 * A generic Card object with every field set to a default <b><code>ERROR</code></b> value.
	 * 
	 * @see game.entities.CardProperties CardProperties
	 */
	public static final Card ERROR_CARD = new Card(CardProperties.Figure.ERROR, CardProperties.Suit.ERROR);
	
	private final CardProperties.Figure figure;
	private final CardProperties.Suit suit;
	//Note that worth represents the worth of a card (an Ace beats a Jack),
	//while value represents the amount of points a card is worth in a game of Bisca
	private int worth, value;
	
	/**
	 * Constructs a <code>Card</code> object with a given figure and suit
	 * 
	 * @param <code>figure</code> - The card's figure.
	 * @param <code>suit</code> - The card's suit.
	 * @see game.entities.CardProperties CardProperties
	 */
	public Card(CardProperties.Figure figure, CardProperties.Suit suit) {
		this.figure = figure;
		this.suit = suit;
		worth = figure.getWorth();
		value = figure.getValue();
	}
	
	/**
	 * @return This card's <code>figure</code>.
	 */
	public CardProperties.Figure getFigure() {
		return figure;
	}
	
	/**
	 * @return This card's <code>suit</code>.
	 */
	public CardProperties.Suit getSuit() {
		return suit;
	}
	
	/**
	 * @return This card's title as a <code>String</code> 
	 * (for example: "Ace of Spades").
	 */
	public String getName() {
		return figure.getName() + " de " + suit.getName();
	}
	
	/**
	 * Returns a numeric representation of this card's figure 
	 * to enable easy comparisons between cards (for example: 
	 * Would return 2 for a Two of any suit).
	 * <p>
	 * <b>Not to be confused with {@link game.entities.Card#getValue() getValue()}!</b>
	 * 
	 * @return This card's worth.
	 */
	public int getWorth() {
		return worth;
	}
	
	/**
	 * Returns the point value of this card's figure in a
	 * typical game of Bisca (for example: Would return
	 * 11 for an Ace of any suit).
	 * <p>
	 * <b>Not to be confused with {@link game.entities.Card#getWorth() getWorth()}!</b>
	 * 
	 * @return This card's point value.
	 */
	public int getValue() {
		return value;
	}
	
}
