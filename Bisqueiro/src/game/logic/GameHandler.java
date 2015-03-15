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
		List<Card> cardsOnTheTable = new ArrayList<Card>(numberOfPlayers);
		
		int indexOfPlayer = 0;
		for(Player player : players) {
			startPlayerTurn(in, player, cardsOnTheTable, indexOfPlayer++);
		}
		
		//Assume that one of the players is already selected to play first
		
		/* Go through each player's turn:
		 * ~!~[XX]~!~ Show them their cards
		 * ~!~[XX]~!~ Have them choose a valid card (must be the same suit as the one the first player played)
		 * """~!~[XX]~!~""" Record the card they chose, as well as who they are
		 */
		
		/* At the end of every player's turn, check for who wins
		 * Print who wins
		 * Give them priority for the next turn	
		 */
		
		System.out.printf("Turn %d is over!%n", turnNumber++);
		
	}
	
	private void initPlayers(int numberOfPlayers) {
		for(int i = 0; i < numberOfPlayers; i++) {
			players.set(i, new Player(deck));
		}
	}
	
	//numberOfCards is a bit redundant, since .size() will yield the both result for cardsOwned and cardNames
	private void startPlayerTurn(Scanner in, Player player, List<Card> cardsOnTheTable, int indexOfPlayer) {
		//Shows the player his cards and returns how many there are
		int numberOfCards = displayCards(player);
		
		//Lets the player choose a card and returns its index
		int chosenCard = chooseCard(in, player, numberOfCards, cardsOnTheTable);
		
		//Plays the card chosen previously
		cardsOnTheTable.set(indexOfPlayer, player.playCard(chosenCard));
	}
	
	private int displayCards(Player player) {
		List<String> cardNames = player.getHandNames();

		int numberOfCards = cardNames.size();
		
		System.out.println("Your cards are:");
		for(int i = 0; i < numberOfCards; i++) {
			System.out.println(i + " - " + cardNames.get(i));
		}
		System.out.println();
		
		return numberOfCards;
	}
	
	private int chooseCard(Scanner in, Player player, int numberOfCards, List<Card> cardsOnTheTable) {
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
		} while(chosenCard < 1 && chosenCard > numberOfCards && !validSuit(player, cardsOnTheTable.get(0), chosenCard));
		
		//check for same suit
		
		return chosenCard;
	}
	
	private boolean validSuit(Player player, Card firstCard, int cardChosen) {
		List<Card> cardsOwned = player.getHand();
		
		//First checks if the card the player played is the same suit as the first card played
		//Or if he's the first one playing if the firstCard is null
		if(cardsOwned.get(cardChosen).getSuit().equals(firstCard.getSuit()) || firstCard == null) {
			return true;
		} else {
			for(Card card : cardsOwned) {
				//If the player didn't match the first card's suit, but could do so, will return false
				//Otherwise will return true because he could play any suit
				if(card.getSuit().equals(firstCard.getSuit())) {
					return false;
				}
			}
			
			return true;
		}
	}
	
}