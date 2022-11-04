/*Date: January 20, 2022
 *Author: An Ha
 *Description: The program simulates BlackJack.
 */

import java.util.Scanner;
import java.util.Random;
import java.text.DecimalFormat; // to format dollar values as per tayla's feedback

public class ICS3U_FP_Code_An_Ha {
	public static void main(String args[]) {
		//variable declaration
		int loop = 0;
		int validation = 0;
		int validationFunds = 0;
		double totalAmount = 0; // double instead of int to prevent users from entering too big of a number, as per parantap's feedback
		double moreMoney = 0;
		String repeat = "";
		String extraFunds = "";
		Scanner input = new Scanner (System.in);
		DecimalFormat rounding = new DecimalFormat("#,###"); // to format dollar values as per tayla's feedback

		//prints the introduction and depending on user input, also prints the rules
		introduction(input);

		// makes sure the user doesn't bet zero or a negative number of dollars, as per parantap's feedback
		while (validation == 0) {
			System.out.print("Please input the dollar amount you have to gamble this session:\n$ "); // puts user input beside $ sign as per tayla's feedback
			totalAmount = input.nextDouble();
			if (totalAmount < 1) {
				System.out.println("You cannot input less than one dollar.");
			} else {
				validation ++;
			}
		}

		// loops the entire program based on if the player wants to play another game
		while (loop == 0) {
			validation = 0;
			totalAmount = playBJ(totalAmount, input, rounding);
			if (totalAmount <= 0) { // allowed user to add more funds if they run out as per tayla's feedback
				System.out.println("\nIt looks like you can't play anymore because you don't have sufficient funds. \n"
						+ "Would you like to insert more funds? (Y/N)");
				while (validation == 0) {
					input = new Scanner(System.in);
					extraFunds = input.nextLine();
					extraFunds = extraFunds.trim().toUpperCase();
					if (extraFunds.equals("N")) {
						System.out.println("See you later then! Happy Gambling!");
						validation ++;
						loop ++;
					} else if (extraFunds.equals("Y")) {
						validationFunds = 0;
						while (validationFunds == 0) {
							System.out.print("Please input the dollar amount you want to gamble with this session:\n$ "); 
							moreMoney = input.nextDouble();
							if (moreMoney < 1) {
								System.out.println("You cannot input less than one dollar.");
							} else {
								totalAmount += moreMoney;
								System.out.println("You have added $" + rounding.format(moreMoney) + " to your account. \n"
										+ "You now have $" + rounding.format(totalAmount) + " and can continue playing!\n");
								validationFunds ++;
							}
						}
						validation ++;
					} else {
						System.out.println("Please input a valid answer, either [Y] for 'yes' or [N] for 'no'.");
					}
				}
			} else {
				validation = 0; // fixed the error where it would launch the user right into a new game as per tayla's feedback
				while (validation == 0) {
					System.out.println("\nWould you like to play again? (Y/N)");
					input = new Scanner(System.in);
					repeat = input.nextLine();
					repeat = repeat.trim().toUpperCase();
					if (repeat.equals("N")) {
						System.out.println("See you later! Happy Gambling!");
						validation ++;
						loop ++;
					} else if (repeat.equals("Y")) {
						validation ++;
					} else {
						System.out.println("Please input a valid answer, either [Y] for 'yes' or [N] for 'no'.");
					}
				}
			}
		}
		input.close();
	}

	public static void introduction (Scanner input) {
		// variable declaration
		int validation = 0;

		System.out.println("Welcome to BlackJack!");

		// if the player does not enter a valid answer, it will ask them to input again
		while (validation == 0) {
			System.out.println("Would you like to review the rules of this game? (Y/N)");
			input = new Scanner(System.in);
			String userinput = input.nextLine();
			userinput = userinput.trim().toUpperCase();

			if (userinput.equals("Y")) {
				System.out.println("The player’s goal in BlackJack is to draw cards where the sum of all card values is equal to \n"
						+ "or under the number 21, while also having a higher sum than the dealer. \n"
						+ "When the game starts, both player and dealer are dealt two cards. \n"
						+ "The player can see one of the dealer's cards face up, and both of their own face up. \n"
						+ "They can choose to either “hit” and draw another card to get a higher sum, \n"
						+ "or “stand” and keep the sum they currently have. \n"
						+ "At the end of the player's turn (when they stand), the rest of the dealer’s cards will be revealed. \n"
						+ "If the dealer’s total is over 17, they are forced to stand. \n" 
						+ "If the player’s total surpasses 21, or is less than the dealer’s, then the player will lose. \n" 
						+ "Otherwise, if the player scores a higher total than the dealer without going over 21, \n"
						+ "gets exactly 21, or the dealer goes over 21, then they claim the victory. \n"
						+ "Each card’s value is equivalent to its shown number, with the exception of the Jack, Queen, and King equalling 10. \n"
						+ "As well as that, the Ace that is counted as 11 \n"
						+ "unless it brings the player’s total sum to over 21, where then it only counts as a 1. \n");
				System.out.println("Now, let's begin! \n");
				validation ++;
			} else if (userinput.equals("N")) {
				validation ++;
			} else {
				System.out.println("Please input a valid answer, either [Y] for 'yes' or [N] for 'no'.");
			}
		}

	}

	public static String[] shuffledDeck () {
		// variable declaration
		String newCard;
		String [] cardDeck = {"A♠", "A♣", "A♥", "A♦", 
				"2♠", "2♣", "2♥", "2♦", 
				"3♠", "3♣", "3♥", "3♦",
				"4♠", "4♣", "4♥", "4♦",
				"5♠", "5♣", "5♥", "5♦",
				"6♠", "6♣", "6♥", "6♦",
				"7♠", "7♣", "7♥", "7♦",
				"8♠", "8♣", "8♥", "8♦",
				"9♠", "9♣", "9♥", "9♦",
				"10♠", "10♣", "10♥", "10♦",
				"J♠", "J♣", "J♥", "J♦",
				"Q♠", "Q♣", "Q♥", "Q♦",
				"K♠", "K♣", "K♥", "K♦"}; //all 52 cards in a deck
		Random rand = new Random();

		// shuffles the deck by switches elements in the array
		for (int i = 0; i < cardDeck.length; i++) {
			int random = rand.nextInt(cardDeck.length);
			newCard = cardDeck[random];
			cardDeck[random] = cardDeck[i];
			cardDeck[i] = newCard;
		}
		// returns a new, shuffled deck
		return cardDeck;
	}

	public static String[] drawCard (boolean playing, int cardsInHand, String [] shuffledDeckPlayer, String [] shuffledDeckDealer) {
		// variable declaration
		String [] hand = new String [cardsInHand];
		String [] deck = shuffledDeckPlayer;

		// if it's not the player's turn, switch over to the dealer's deck so they can draw cards
		if (!playing) {
			deck = shuffledDeckDealer;
		}

		// new array represents the "hand", to where the number of the cards they have in their hand corresponds to how many cards they take from the deck
		for (int i = 0; i < cardsInHand; i++) {
			hand [i] = deck[i];
		}
		return hand;
	}

	public static void printDealerFirst (String [] shuffledDeckDealer) {
		// variable declaration
		String dealerHand;
		String [] deck = shuffledDeckDealer;

		dealerHand = deck[0]; // gets the dealer's first card 

		int length = (dealerHand.length()) - 1; // the index of the cards suit at the end of the string to print in the ascii

		System.out.println("The dealer's hand:");

		// prints the dealer's first card, as well as a blank one to re-create a "face-down card"
		System.out.println("┌─────┐┌─────┐");
		if (dealerHand.startsWith("10")) { // makes space to fit the card format if the dealer drew a 10
			System.out.println("│" + dealerHand.substring(0, 2) + "   ││     │");
		} else {
			System.out.println("│" + dealerHand.charAt(0) + "    ││     │");
		}
		System.out.println("│  "  + dealerHand.charAt(length) + "  ││     │");
		if (dealerHand.startsWith("10")) {
			System.out.println("│   " + dealerHand.substring(0, 2) + "││     │");
		} else {
			System.out.println("│    " + dealerHand.charAt(0) + "││     │");
		}
		System.out.println("└─────┘└─────┘");
	}

	public static void printCards (boolean playing, String [] playerHand, String [] dealerHand) {
		// variable declaration
		String [] hand = playerHand;
		// if it's not the player's turn, switch over to the dealer's hand so their cards are printed
		if (!playing) {
			hand = dealerHand;	
		}

		// prints the cards line by line according to how many cards are in their hand
		for (int i = 0; i < hand.length; i++) {
			System.out.print("┌─────┐");
		}
		System.out.println("");
		for (int i = 0; i < hand.length; i++) {
			if (hand[i].startsWith("10")) {
				System.out.print("│10   │");
			} else {
				System.out.print("│" + hand[i].charAt(0) + "    │");
			}
		}
		System.out.println("");
		for (int i = 0; i < hand.length; i++) {
			int length = (hand[i].length()) - 1;
			System.out.print("│  "  + hand[i].charAt(length) + "  │");
		}
		System.out.println("");
		for (int i = 0; i < hand.length; i++) {
			if (hand[i].startsWith("10")) {
				System.out.print("│   10│");
			} else {
				System.out.print("│    " + hand[i].charAt(0) + "│");
			}
		}
		System.out.println("");
		for (int i = 0; i < hand.length; i++) {
			System.out.print("└─────┘");
		}
		System.out.println("");
	}

	public static int currentSum (boolean playing, String [] playerHand, String [] dealerHand) {
		// variable declaration
		int currentSum = 0;
		String [] hand = playerHand;

		// if it's not the player's turn, switch over to the dealer's hand so their sum can be calculated
		if (!playing) {
			hand = dealerHand;	
		}

		for (int i = 0; i < hand.length; i++) {
			if (hand[i].startsWith("A") && (currentSum + 11) > 21) {
				currentSum += 1;
			} else if (hand[i].startsWith("2")) {
				currentSum += 2;
			} else if (hand[i].startsWith("3")) {
				currentSum += 3;
			} else if (hand[i].startsWith("4")) {
				currentSum += 4;
			} else if (hand[i].startsWith("5")) {
				currentSum += 5;
			} else if (hand[i].startsWith("6")) {
				currentSum += 6;
			} else if (hand[i].startsWith("7")) {
				currentSum += 7;
			} else if (hand[i].startsWith("8")) {
				currentSum += 8;
			} else if (hand[i].startsWith("9")) {
				currentSum += 9;
			} else if (hand[i].startsWith("10") 
					|| hand[i].startsWith("J") 
					|| hand[i].startsWith("Q") 
					|| hand[i].startsWith("K")) {
				currentSum += 10;
			} else if (hand[i].startsWith("A") ) {
				currentSum += 11;
			}
		}

		return currentSum;
	}

	public static double playBJ (double totalAmount, Scanner input, DecimalFormat rounding) {
		// variable declaration
		int currentSum, dealerSum;
		int cardsInHand = 2;
		double betAmount = 0; // players can bet cents if they want to if it's a double rather than an integer
		int validation = 0;
		boolean playing = true;
		String action = "";
		String [] shuffledDeckPlayer = shuffledDeck(); // the player and the dealer get separate decks to draw from
		String [] shuffledDeckDealer = shuffledDeck(); 
		String [] dealerHand = drawCard (playing, cardsInHand, shuffledDeckPlayer, shuffledDeckDealer);

		// gets the amount the player is betting 
		// makes them input again if there's an invalid answer
		validation = 0;
		while (validation == 0) {
			System.out.print("How much are you betting:\n$ ");
			betAmount = input.nextDouble();
			if (betAmount > totalAmount) {
				System.out.println("You cannot bet more than you have.");
			} else if (betAmount < 1) {
				System.out.println("You cannot bet less than one dollar.");
			} else {
				validation ++;
			}
		}

		// prints the dealer's first two cards, one face-up and one face-down
		printDealerFirst(shuffledDeckDealer);

		// the player's turn to draw
		while (playing) {
			String [] playerHand = drawCard (playing, cardsInHand, shuffledDeckPlayer, shuffledDeckDealer);
			System.out.println("Your cards are:");
			printCards(playing, playerHand, playerHand); // prints ascii versions of the cards in their hand
			currentSum = currentSum(playing, playerHand, dealerHand); // calculates the sum of the player's cards
			System.out.println("Current Sum: " + currentSum); // then prints it
			validation = 0; // refreshes the validation check each time the user plays, as per parantap's feedback
			
			if (currentSum < 21) { // allows them to hit or stand if they haven't went over 21 yet
				while (validation == 0) {
					System.out.println("Would you like to [Hit] or [Stand]? (Please enter the word or the first letter of your desired action)");
					input = new Scanner(System.in);
					action = input.nextLine();
					action = action.trim().toLowerCase();
					if (action.equals("hit") || action.equals("h")) { // both "hit" or "h" works, as per parantap's feedback
						cardsInHand ++; // player draws another card if they hit
						validation ++;
					} else if (action.equals("stand") || action.equals("s")) { // the dealer goes on to draw if they stand
						playing = false;
						cardsInHand = 2;
						boolean drawing = true;

						System.out.println("The dealer's hand:"); // dealer's turn!
						while (drawing) {
							dealerHand = drawCard (playing, cardsInHand, shuffledDeckPlayer, shuffledDeckDealer);
							printCards(playing, playerHand, dealerHand);
							dealerSum = currentSum(playing, playerHand, dealerHand);
							if (dealerSum < 17) {
								cardsInHand ++; // the dealer is forced to hit as long as their sum is less than 17
							} else if (dealerSum >= 17 && dealerSum <= 21) { // if the dealer stands as well, both sums are compared to determine a winner
								drawing = false;
								if (dealerSum > currentSum) {
									System.out.println("Current Sum: " + currentSum); 
									System.out.println("Dealer Sum: " + dealerSum); // see comparisons between scores as per tayla's feedback
									System.out.println("Sorry! You lost because the dealer scored higher than you. You have lost $" + rounding.format(betAmount));
									totalAmount -= betAmount;
								} else if (dealerSum < currentSum){
									System.out.println("Current Sum: " + currentSum);
									System.out.println("Dealer Sum: " + dealerSum); 
									System.out.println("Congratulations! You won because you beat the dealer's score. You have received $" + rounding.format(betAmount) + "!");
									totalAmount += betAmount;
								} else {
									System.out.println("Current Sum: " + currentSum);
									System.out.println("Dealer Sum: " + dealerSum);
									System.out.println("It's a tie! You and the dealer both have the same score. You did not lose nor gain money.");
								}
							} else { // player gets an automatic win if the dealer surpasses 21
								System.out.println("Current Sum: " + currentSum);
								System.out.println("Dealer Sum: " + dealerSum);
								System.out.println("Congratulations! You won because the dealer busted. You have received $" + rounding.format(betAmount) + "!");
								totalAmount += betAmount;
								drawing = false;
							} 
							validation ++;
						}
					} else { // if the player does not enter a valid answer, it will ask them to input again
						System.out.println("Please input a valid answer, either [Hit] or [Stand].");
					}
				}
			} else if (currentSum > 21) { // automatic loss if the player surpasses 21 by hitting
				System.out.println("Sorry! You lost because you went over 21. You have lost $" + rounding.format(betAmount) + ".");
				totalAmount -= betAmount;
				playing = false;
			} else { // automatic win if they draw 2 cards that sum up to 21 off the bat
				System.out.println("Congratulations! You won because you hit exactly 21. You have received $" + rounding.format(betAmount) + "!");
				totalAmount += betAmount;
				playing = false;
			}
		}
		System.out.println("You now have $" + rounding.format(totalAmount) + ".");

		//returns the end standing of the player's money according to the result of their game
		return totalAmount;
	}
}