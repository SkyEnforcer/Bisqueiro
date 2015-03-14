package game.logic;

import java.util.List;
import java.util.ArrayList;
import player.Player;
import game.entities.Deck;
import game.entities.Card;
import java.util.Scanner;
import java.util.InputMismatchException;

public class GameHandler {

	private Deck deck;
	private List<Player> players;
	private int turnNumber;
	
	//numberOfPlayers > 2
	public GameHandler(int numberOfPlayers) {
		deck = new Deck();
		players = new ArrayList<Player>(numberOfPlayers);
		initPlayers(numberOfPlayers);
		turnNumber = 1;
	}

	public void nextTurn(Scanner in) {
		
		int numberOfPlayers = players.size();
		List<Card> cardsPlayed = new ArrayList<Card>(numberOfPlayers);
		
		int playerPlayPriority = 0;
		for(Player player : players) {
			List<String> cardNames = player.getHandNames();
			List<Card> cardsOwned = player.getHand();
			
			int numberOfCards = cardsOwned.size();
			
			System.out.println("Your cards are:");
			for(int i = 0; i < numberOfCards; i++) {
				System.out.println(i + " - " + cardNames.get(i));
			}
			System.out.println();
			
			int chosenCard = 0;
			do{
				System.out.print("Please choose a card: ");
				try {
					chosenCard = in.nextInt();
				} catch(InputMismatchException e) {
					chosenCard = 0;
				} finally {
					System.out.println();
				}
			}while(chosenCard < 1 && chosenCard > numberOfCards);
			
			//check for same suit
			cardsPlayed.set(playerPlayPriority++, player.playCard(chosenCard));
		}
		
		//check for who wins
		//
		//
		
		//print who won
		//
		//
		
		System.out.printf("Turn %d over!%n", turnNumber++);
		
	}
	
	private void initPlayers(int numberOfPlayers) {
		for(int i = 0; i < numberOfPlayers; i++) {
			players.set(i, new Player(deck));
		}
	}
	
}