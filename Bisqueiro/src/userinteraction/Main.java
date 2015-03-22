package userinteraction;

import java.util.Scanner;
import java.util.InputMismatchException;
import game.logic.GameHandler;
import java.util.List;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		mainMenu(in);

		in.close();
		System.out.println("Ending game session");
	}

	private static void mainMenu(Scanner in) {
		boolean exitFlag = false;
		
		do {
			System.out.printf(" ~{-~~}}  || Bisqueiro - v0.1 ||  {{~~-}~%n%n");
			System.out.printf("  Options:%n    1 - Start game%n    2 - Settings%n    3 - Exit%n%n");
			int option = optionChooser(in);
			
			exitFlag = optionChoiceMenu(in, option);
		} while(!exitFlag);
	}
	
	private static int optionChooser(Scanner in) {
		int option = -1;
		
		do{
			try {
				option = in.nextInt();
				if(option < 1 || option > 3) {
					option = -1;
				}
			} catch(InputMismatchException ex) {
				option = -1;
			} finally {
				in.nextLine();
				if(option == -1) {
					System.out.printf("That's not a valid option%n");
				}
			}
		} while(option == -1);
		
		return option;
	}
	
	private static boolean optionChoiceMenu(Scanner in, int option) {
		boolean exitFlag = false;
		
		switch(option) {
		case 1: option1(in);
			break;
		case 2:
			break;
		case 3: exitFlag = true;
			break;
		}
		
		return exitFlag;
	}
	
	private static void option1(Scanner in) {
		List<String> playerNames = playerSet(in);
		GameHandler game = new GameHandler(2, playerNames);
		
		while(!game.gameIsOver()) {
			System.out.printf(" Round %d || Trump: %s%n", game.getTurn(), game.getTrump());
			game.nextTurn(in);
		}
	}
	
	private static List<String> playerSet(Scanner in) {
		List<String> playerNames = new ArrayList<String>(2);
		
		System.out.printf(" Enter a name for player 1: ");
		String player1 = in.nextLine();
		
		String player2;
		do {
			System.out.print(" Enter a name for player 2: ");
			player2 = in.nextLine();
		} while(player2.equals(player1));
		
		System.out.printf(" Player 1: %s     \\VERSUS//     Player 2: %s%n%n", player1, player2);
		
		playerNames.add(player1);
		playerNames.add(player2);
		return playerNames;
	}
}
