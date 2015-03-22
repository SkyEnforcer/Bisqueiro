package game.logic;

import java.util.List;
import java.util.ArrayList;
import player.Player;
import game.entities.Deck;
import game.entities.Card;
import game.entities.CardProperties;
import java.util.Scanner;
import java.util.InputMismatchException;

//Needs to be subdivided into logic + game handling classes
public class GameHandler {

	private Deck deck;
	private List<Player> players;
	private int turnNumber;
	private boolean gameOver;
	
	//numberOfPlayers >= 2
	public GameHandler(int numberOfPlayers, List<String> playerNames) {
		deck = new Deck();
		players = new ArrayList<Player>(numberOfPlayers);
		initPlayers(numberOfPlayers, playerNames);
		turnNumber = 1;
		gameOver = false;
	}

	//For the first turn, player 1 will always start first
	//Needs to be subdivided again
	public void nextTurn(Scanner in) {
		
		int numberOfPlayers = players.size();
		List<Card> cardsOnTheTable = new ArrayList<Card>(numberOfPlayers);
		
		int indexOfPlayer = 0;
		for(Player player : players) {
			startPlayerTurn(in, player, cardsOnTheTable, indexOfPlayer++);
		}
		
		Card winningCard = checkWinningCard(cardsOnTheTable);
		
		//Will hold the new player list, ordered by their priority for the next turn
		List<Player> newPlayerList = new ArrayList<Player>(players.size());
		Player player = null; //We will always enter the for loop
		
		//Will find the player that played the winningCard and start saving him + all players after him, in order
		boolean foundFirstPlayer = false;
		for(int i = 0; i < players.size(); i++) {
			player = players.get(i);
			
			for(Card card : player.getHand()) {
				if(card.equals(winningCard)) {
					foundFirstPlayer = true;
					break;
				}
			}
			
			if(foundFirstPlayer) {
				//newPlayerList.set(i, player);
				newPlayerList.add(player);
			}
		}
		
		//Gets the number of players in the new list
		int filledPositions = newPlayerList.lastIndexOf(player) + 1;
		
		//Saves the rest of the players in order, starting at the previously first player
		for(int i = filledPositions, k = 0; i < newPlayerList.size(); i++, k++) {
			//newPlayerList.set(i, players.get(k));
			newPlayerList.add(players.get(k));
		}
		
		//Sets players to have the new order
		players = newPlayerList;
		
		//Prints the round over message
		System.out.printf("ROUND OVER!%nPlayer %s wins round %d with %s%n%n",
				/*Isto é que está a dar merda*/players.get(0).getName(),
				turnNumber++,
				winningCard.getName());
		
		if(deck.getSize() != 0) {
			//makes each player draw a card in order
			drawCards();
		} else if(players.get(0).getHand().size() == 0) {
			gameOver = true;
		}
	}
	
	public int getTurn() {
		return turnNumber;
	}
	
	public boolean gameIsOver() {
		return gameOver;
	}
	
	//Should be working at 100%, not sure
	private Card checkWinningCard(List<Card> cardsOnTheTable) {
		Card winningCard = cardsOnTheTable.get(0);
		
		for(int i = 1; i < cardsOnTheTable.size(); i++) {
			Card currentCard = cardsOnTheTable.get(i);
			
			if(winningCard.getSuit().equals(currentCard.getSuit())) {
				winningCard = highestFigure(winningCard, currentCard);
			} else {
				CardProperties.Suit trumpSuit = deck.getTrumpCard().getSuit();
				
				if(!winningCard.getSuit().equals(trumpSuit) && currentCard.getSuit().equals(trumpSuit)) {
					//currentCard matches the trump suit but winningCard doesn't
					winningCard = currentCard;
				}
				/* else if(winningCard.getSuit().equals(trumpSuit) && !currentCard.getSuit().equals(trumpSuit)) {
					//winningCard matches the trump suit but currentCard doesn't
					winningCard = winningCard;
				} else { //We have a guarantee that the cards' suits don't match and that none of them is the trump suit
					winningCard = winningCard; //returns the oldest card
				}*/
				//Supposedly doesn't matter having this code or not, since it would just set winningCard to itself
			}
		}
		
		return winningCard;
	}
	
	private Card highestFigure(Card winningCard, Card currentCard) {
		if(winningCard.getNumberRepresentation() > currentCard.getNumberRepresentation()) {
			return winningCard;
		} else {
			return currentCard;
		}
	}

	private void initPlayers(int numberOfPlayers, List<String> playerNames) {
		for(int i = 0; i < numberOfPlayers; i++) {
			//players.set(i, new Player(playerNames.get(i), deck));
			players.add(new Player(playerNames.get(i), deck));
		}
	}
	
	//numberOfCards is a bit redundant, since .size() will yield the both result for cardsOwned and cardNames
	private void startPlayerTurn(Scanner in, Player player, List<Card> cardsOnTheTable, int indexOfPlayer) {
		//Shows the player his cards and returns how many there are
		int numberOfCards = displayCards(player);
		
		//Lets the player choose a card and returns its index
		int chosenCard = chooseCard(in, player, numberOfCards, cardsOnTheTable);
		
		//Plays the card chosen previously
		//cardsOnTheTable.set(indexOfPlayer, player.playCard(chosenCard));
		cardsOnTheTable.add(player.playCard(chosenCard));
	}
	
	private int displayCards(Player player) {
		List<String> cardNames = player.getHandNames();

		int numberOfCards = cardNames.size();
		
		System.out.println(" Your cards are:");
		for(int i = 0; i < numberOfCards; i++) {
			System.out.println(" " + i + " - " + cardNames.get(i));
		}
		System.out.println();
		
		return numberOfCards;
	}
	
	private int chooseCard(Scanner in, Player player, int numberOfCards, List<Card> cardsOnTheTable) {
		int chosenCard = -1;
		do{
			System.out.print("Please choose a card: ");
			try {
				chosenCard = in.nextInt();
			} catch(InputMismatchException e) {
				chosenCard = -1;
			} finally {
				in.nextLine();
			}
		} while((chosenCard < 0 || chosenCard >= numberOfCards) || !validSuit(player, cardsOnTheTable, chosenCard));
		
		//check for same suit
		
		return chosenCard;
	}
	
	private boolean validSuit(Player player, List<Card> cardsOnTheTable, int cardChosen) {
		List<Card> cardsOwned = player.getHand();
		Card firstCard;
		
		//Then he's the first one playing
		if(cardsOnTheTable.size() == 0) {
			return true;
		}
		
		//Otherwise
		firstCard = cardsOnTheTable.get(0);
		
		//First checks if the card the player played is the same suit as the first card played
		//Or if he's the first one playing if the firstCard is null
		if(cardsOwned.get(cardChosen).getSuit().equals(firstCard.getSuit())) {
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
	
	private void drawCards() {
		for(Player player : players) {
			player.drawCard();
		}
	}
}
