package game.entities;

/**
 * The <code>CardProperties</code> enum has the information pool
 * required to construct a regular playing card, such as possible
 * suits, figures, point values and relative "worths".
 * 
 * @author SexoBode
 * @version 1.0
 */
public enum CardProperties {
	;//Since CardProperties is an enum, it expects to have some fields of itself,
	//so despite none being needed, it will refuse to compile unless we terminate
	//its (non-existent) fields with a ;

	/**
	 * The <code>Figure</code> enum has the information pool
	 * related to a card's figure, such as the figure itself,
	 * its point value and worth.
	 */
	public enum Figure {
		ERROR("ERRO", -1, -1), ACE("Ás", 14, 11), TWO("Dois", 2, 0), THREE("Três", 3, 0),
		FOUR("Quatro", 4, 0), FIVE("Cinco", 5, 0), SIX("Seis", 6, 0), SEVEN("Sete", 13, 10),
		JACK("Valete", 10, 3), QUEEN("Rainha", 11, 2), KING("Rei", 12, 4);
		
		private String title;
		//Note that worth represents the worth of a figure (an Ace beats a Jack),
		//while value represents the amount of points a card with a certain figure
		//is worth in a game of Bisca
		private int worth, value;
		
		/**
		 * Constructs a <code>Figure</code> object with a given 
		 * title, worth and suit.
		 * 
		 * @param <code>title</code> - The figure's title.
		 * @param <code>worth</code> - The figure's worth.
		 * @param <code>value</code> - The figure's point value.
		 */
		private Figure(String title, int worth, int value) {
			this.title = title;
			this.worth = worth;
			this.value = value;
		}
		
		/**
		 * @return This figure's name.
		 */
		public String getName() {
			return title;
		}
		/**	
		 * Returns a numeric representation of this figure 
		 * to enable easy comparisons between cards (for example: 
		 * Would return 2 for a Two).
		 * <p>
		 * <b>Not to be confused with {@link game.entities.CardProperties.Figure#getValue() getValue()}!</b>
		 * 
		 * @return This card's worth.
		 */
		public int getWorth() {
			return worth;
		}
		
		/**
		 * Returns the point value of this figure in a
		 * typical game of Bisca (for example: Would return
		 * 11 for an Ace).
		 * <p>
		 * <b>Not to be confused with {@link game.entities.CardProperties.Figure#getWorth() getWorth()}!</b>
		 * 
		 * @return This card's point value.
		 */
		public int getValue() {
			return value;
		}
		
	}
	
	/**
	 * The <code>Suit</code> enum has the information pool
	 * related to a card's suit.
	 */
	public enum Suit {
		ERROR("ERRO"), SPADES("Espadas"), HEARTS("Copas"),
		DIAMONDS("Ouros"), CLUBS ("Paus");
		
		private String title;
		
		/**
		 * Constructs a <code>Suit</code> object with a given title
		 * 
		 * @param <code>title</code> - The suit's title.
		 */
		private Suit(String title) {
			this.title = title;
		}
		
		/**
		 * @return This suit's name.
		 */
		public String getName() {
			return title;
		}
	}

}
