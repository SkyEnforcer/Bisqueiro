package game.entities;

public enum CardProperties {
	;//Required even though CardProperties doesn't have any fields of itself
	
	public enum Figure {
		ERROR("ERRO", -1), ACE("Ás", 14), TWO("Dois", 2), THREE("Três", 3),
		FOUR("Quatro", 4), FIVE("Cinco", 5), SIX("Seis", 6), SEVEN("Sete", 13),
		JACK("Valete", 10), QUEEN("Rainha", 11), KING("Rei", 12);
		
		private String title;
		private int value;
		
		private Figure(String title, int value) {
			this.title = title;
			this.value = value;
		}
		
		//@Override
		public String toString() {
			return title;
		}
		
		public int getNumericRepresentation() {
			return value;
		}
		
	}
	
	public enum Suit {
		ERROR("ERRO"), SPADES("Espadas"), HEARTS("Copas"),
		DIAMONDS("Ouros"), CLUBS ("Paus");
		
		private String title;
		
		private Suit(String title) {
			this.title = title;
		}
		
		//@Override
		public String toString() {
			return title;
		}
	}

}
