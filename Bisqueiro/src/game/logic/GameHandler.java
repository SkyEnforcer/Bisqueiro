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
	private boolean gameOver, tie;
	
	//numberOfPlayers >= 2
	public GameHandler(int numberOfPlayers, List<String> playerNames) {
		deck = new Deck();
		players = new ArrayList<Player>(numberOfPlayers);
		initPlayers(numberOfPlayers, playerNames);
		turnNumber = 1;
		gameOver = false;
		tie = false;
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
		int index = 0;
		
		for(; index < cardsOnTheTable.size(); ++index) {
			Card card = cardsOnTheTable.get(index);
			if(card.equals(winningCard)) { //Will .equals() work as expeted?
				break;
			}
		}
		
		for(int n = index; n < players.size(); ++n) {
			newPlayerList.add(players.get(n));
		}
		
		for(int k = 0; k < index; ++k) {
			newPlayerList.add(players.get(k));
		}
		
		//Sets players to have the new order
		players = newPlayerList;
		
		//Prints the round over message
		System.out.printf(" ROUND OVER!%n  Player [%s] wins round %d with [%s].%n%n", 
				players.get(0).getName(), turnNumber++, winningCard.getName());
		
		//Add points to each player
		for(Card card : cardsOnTheTable) {
			players.get(0).addPoints(card.getValue());
		}
		
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
	
	public String getTrump() {
		return deck.getTrumpCard().getName(); //previosuly getSuit().toString();
	}
	
	public Player getWinningPlayer() {
		Player winningPlayer = players.get(0);
		
		for(Player player : players) {
			if(player.getPoints() > winningPlayer.getPoints()) {
				winningPlayer = player;
			}
			
			//if there's a tie then no one wins
			if(player.getPoints() == winningPlayer.getPoints()) {
				tie = true;
				break;
			}
		}
		
		return winningPlayer;
	}
	
	public boolean gameIsATie() {
		return tie;
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
		if(winningCard.getWorth() > currentCard.getWorth()) {
			return winningCard;
		} else {
			return currentCard;
		}
	}

	private void initPlayers(int numberOfPlayers, List<String> playerNames) {
		for(int i = 0; i < numberOfPlayers; i++) {
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
		
		System.out.printf(" Player: %s%n  Your cards:%n", player.getName());
		for(int i = 0; i < numberOfCards; i++) {
			System.out.println("    " + i + " - " + cardNames.get(i));
		}
		System.out.println();
		
		return numberOfCards;
	}
	
	private int chooseCard(Scanner in, Player player, int numberOfCards, List<Card> cardsOnTheTable) {
		int chosenCard = -1;
		do{
			System.out.print(" Please choose a card: ");
			try {
				chosenCard = in.nextInt();
			} catch(InputMismatchException e) {
				chosenCard = -1;
			} finally {
				in.nextLine();
			}
		} while((chosenCard < 0 || chosenCard >= numberOfCards) || !validSuit(player, cardsOnTheTable, chosenCard));
		//check for same suit
		
		System.out.println();
		
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
					System.out.println(" That's not a valid suit!");
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
