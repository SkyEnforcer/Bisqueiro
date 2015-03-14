package game.entities;

public enum CardProperties {
	;//<----- WHAT THE FUCK???????????????????????????????????????????
	
	public enum Figure {
		ERROR("ERRO"), ONE("Um"), TWO("Dois"), THREE("Três"),
		FOUR("Quatro"), FIVE("Cinco"), SIX("Seis"), SEVEN("Sete"),
		JACK("Valete"), QUEEN("Rainha"), KING("Rei");
		
		private String title;
		
		private Figure(String title) {
			this.title = title;
		}
		
		public String toString() {
			return title;
		}
		
	}
	
	public enum Suit {
		ERROR("ERRO"), SPADES("Espadas"), HEARTS("Copas"),
		DIAMONDS("Ouros"), CLUBS ("Paus");
		
		private String title;
		
		private Suit(String title) {
			this.title = title;
		}
		
		public String toString() {
			return title;
		}
	}

}
