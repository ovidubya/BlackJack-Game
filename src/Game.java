import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * This is a simple blackjack program for SER 215.
 * 
 * 
 * @author Paul Hood, Aaron Monahan Ovadia Shalom, Christopher Vasquez
 * @version 2/24/2017
 */
public class Game {
	static Dealer dealer = new Dealer();
	static Player player = new Player("player1", 2000);
	static Scanner scan = new Scanner(System.in);
	static int topOfDeck = 51;
	static Deck dk = new Deck();
	static Card[] deck = dk.getShuffledDeck();
	static boolean canDouble;
	public static void main(String[] args) {
		
		//game on
		while(true)
		{
			
			//if the deck has less than or equal to 10 cards
			//a. get a new shuffled deck
			//b. reset the index to 51
			//c. reset the count back to 0
			//d. print out a shuffling message
			if(topOfDeck <= 10)
			{
				deck = dk.getShuffledDeck();
				topOfDeck = 51;
				dk.resetCount();
				System.out.println("Shuffling...");
			}
			//if the player has less than 0 in his balance
			//the game is finished, break from loop
			if(player.getBalance() <= 0)
			{
				System.out.println("Game over");
				break;
			}
			
			//print the amount of cards in the deck
			//print the balance of the player
			//print the count
			//prompt user for a wager value, and check if the wager
			//value is a legal
			System.out.println("The amount of cards in the deck: " + (topOfDeck + 1));
			System.out.println("Your balance is " + "$" + player.getBalance());
			System.out.println("The count is  " + dk.getCount());
			System.out.println("Enter a bet value");
			double wager = scan.nextInt();
			wager = checkWager(wager);
			
			//Sets up the players hand and prints the cards
			//Gets the count of each card the player has in his hand
			player.getHand(deck[topOfDeck], deck[topOfDeck-1]);
			player.printHand(deck[topOfDeck], deck[topOfDeck-1]);
			dk.valueOfCount(deck[topOfDeck]);
			dk.valueOfCount(deck[topOfDeck-1]);
			topOfDeck-=2;
			
			//Set up dealer's hand and print
			//gets the count of each card in dealers hand
			dealer.getHand(deck[topOfDeck], deck[topOfDeck-1]);
			dealer.printHand(deck[topOfDeck], deck[topOfDeck-1]);
			dk.valueOfCount(deck[topOfDeck]);
			dk.valueOfCount(deck[topOfDeck-1]);
			topOfDeck-=2;
			
			
			//Print messages that is avaible to the player.
			//Player has up to 4 options depending on players hand.
			String message = "You can either: " + "hit " + "or " + "stand"; //default message, a player always has the right to hit or stand
			if(player.getBalance() >= wager*2) //if the dealer has enough money in balance he can double down
			{
				message += " or dd";
				player.setDoubleDown(true); //sets true for the player to double down
			}
			if(player.isSplitable() == true && player.getBalance() >= wager*2) //if the player has two times the wager
			{																   //and has two cards of the same name
				message += " or split";										   //he can split
			}
			if(player.isBlackJack() == true) //if the player has a blackjack
			{
				message = "You got a BlackJack!"; //change message
			}
			System.out.println(message); //print message
			
			
			if(player.isBlackJack() == true) //if he has a blackjack
			{								 // need to check that dealer doesn't tie with player
				while(dealer.isSeventeen() == false) //loop until dealer has hit 17 or more
				{
					dealer.hit(deck[topOfDeck]); //dealer hit
					dk.valueOfCount(deck[topOfDeck]); //count of card
					topOfDeck--; //index shift
				}
				dealer.printHandUnhidden(); //print hand of dealer
				int x = checkWinner(player.returnHand(), dealer.returnHand()); //check who won
				if(x == 1) //player won
				{
					wager = (wager * .5) + wager;
					System.out.println("Player wins first hand!");
					double newBalance = player.getBalance() + wager;
					player.setBalance(newBalance);
				}
				else //player ties with dealer
				{
					System.out.println("Player ties with Dealer");
				}
				System.out.println("");
				System.out.println("");
				player.setBlackJack(false); //set blackjack to false since this round was finished
				continue; //go back to the beginning of the loop
			}
			
			while(player.isBust(player.returnHand()) == false) //check to see if the player busted
			{
				String a1 = scan.next(); //get input from player
				a1 = checkCommand(a1, wager); //check input and wager if matches up
				if(player.isDoubleDown() == false && a1.equals("dd")) //checks to see if the player wants to double down on second card
				{
					System.out.println("Invalid input: please enter a new command");
					continue; 
				}
				
				if(a1.equals("split")) 
				{
					player.split();
					playerSplit();
					while(dealer.isSeventeen() == false)
					{
						dealer.hit(deck[topOfDeck]);
						dk.valueOfCount(deck[topOfDeck]);
						topOfDeck--;
					}
					dealer.printHandUnhidden();
					int x = checkWinner(player.returnHand(), dealer.returnHand());
					int y = checkWinner(player.returnSecondHand(), dealer.returnHand());
					if(x == 1)
					{
						System.out.println("Player wins first hand!");
						double newBalance = player.getBalance() + wager;
						player.setBalance(newBalance);
					}
					else if(x == 2)
					{
						System.out.println("Player loses first hand");
						double newBalance = player.getBalance() - wager;
						player.setBalance(newBalance);
					}
					else
					{
						System.out.println("Player Ties with first hand");
					}
					if(y == 1)
					{
						System.out.println("Player wins second hand!");
						double newBalance = player.getBalance() + wager;
						player.setBalance(newBalance);
					}
					else if(y == 2)
					{
						System.out.println("Player loses second hand");
						double newBalance = player.getBalance() - wager;
						player.setBalance(newBalance);
					}
					else
					{
						System.out.println("Player Ties with second hand");
					}
					player.setSplit(false);
					break;
				}
				
				if(a1.equals("dd"))
				{
					wager = wager * 2;
					player.hit(player.returnHand() ,deck[topOfDeck]);
					dk.valueOfCount(deck[topOfDeck]);
					topOfDeck--;
					
					if(player.isBust(player.returnHand()) == true)
					{
						double newBalance = player.getBalance() - wager;
						player.setBalance(newBalance);
						System.out.println("Busted...");
						break;
					}
					while(dealer.isSeventeen() == false)
					{
						dealer.hit(deck[topOfDeck]);
						dk.valueOfCount(deck[topOfDeck]);
						topOfDeck--;
					}
					if(dealer.isSeventeen() == true)
					{
						dealer.printHandUnhidden();
						int x = checkWinner(player.returnHand(), dealer.returnHand()); //was checkWinner()2
						if(x == 1)
						{
							System.out.println("Player win!");
							double newBalance = player.getBalance() + wager;
							player.setBalance(newBalance);
							break;
						}
						else if(x == 2)
						{
							System.out.println("Dealer wins");
							double newBalance = player.getBalance() - wager;
							player.setBalance(newBalance);
							break;
						}
						else
						{
							System.out.println("Tie");
							break;
						}
					}
				}
				if(a1.equals("hit"))
				{
					player.hit(player.returnHand(), deck[topOfDeck]);
					dk.valueOfCount(deck[topOfDeck]);
					topOfDeck--;
					if(player.isBust(player.returnHand()) == true)
					{
						double newBalance = player.getBalance() - wager;
						player.setBalance(newBalance);
						System.out.println("Busted..");
					}
				}
				if(a1.equals("stand")) //dealer hits and calculuates score
				{
					
					while(dealer.isSeventeen() == false)
					{
						dealer.hit(deck[topOfDeck]);
						dk.valueOfCount(deck[topOfDeck]);
						topOfDeck--;
					}
					if(dealer.isSeventeen() == true)
					{
						dealer.printHandUnhidden();
						int x = checkWinner(player.returnHand(), dealer.returnHand()); //was checkWinner()2
						if(x == 1)
						{
							System.out.println("Player win!");
							double newBalance = player.getBalance() + wager;
							player.setBalance(newBalance);
							break;
						}
						else if(x == 2)
						{
							System.out.println("Dealer wins");
							double newBalance = player.getBalance() - wager;
							player.setBalance(newBalance);
							break;
						}
						else
						{
							System.out.println("Tie");
							break;
						}
					}
				}
				player.setDoubleDown(false);
				player.setSplit(false);
			}
			System.out.println("");
			System.out.println("");
		}
		
		
	}
	/**
	 * The method returns a int. If the number is 1 than the player won.
	 * if the number is 2, then the dealer won. 3 tie.
	 * @return	1 for player and 2 for dealer 3 for tie game
	 */
	public static int checkWinner(int[] playerHand, int[] dealerHand)
	{
		/*
		//Get hand
		int[] playerHand = player.returnHand();
		int[] dealerHand = dealer.returnHand();
		*/
		//Get highest card
		int playerScore = Math.max(playerHand[0], playerHand[1]);
		int dealerScore = Math.max(dealerHand[0], dealerHand[1]);
		
		if(playerScore > 21)
		{
			return 2;
		}
		else
		{
			if(playerScore <= 21 && dealerScore > 21)
			{
				return 1;
			}
			else if(playerScore <= 21 && (playerScore > dealerScore))
			{
				return 1;
			}
			else if(playerScore <= 21 && (playerScore < dealerScore))
			{
				return 2;
			}
			else if(playerScore == dealerScore)
			{
				return 3;
			}
		}
		return 0;
	}
	/**
	 * Player interface when split function is called
	 * 
	 */
	public static void playerSplit()
	{
		int[] playerHand = player.returnHand();
		int[] playerSecondHand = player.returnSecondHand();
		System.out.println("Your first hand: " + playerHand[0] + "/" + playerHand[1]);
		System.out.println("You second hand: " + playerSecondHand[0] + "/" + playerSecondHand[1]);
		while(true) //first hand
		{
			System.out.println("What would you like to do for your first hand? hit or stand");
			String a1 = scan.next();
			if(a1.equals("hit"))
			{
				player.hit(playerHand, deck[topOfDeck]);
				dk.valueOfCount(deck[topOfDeck]);
				topOfDeck--;
			}
			if(a1.equals("stand"))
			{
				System.out.println("Players 1st is " + playerHand[0] + "/" + playerHand[1]);
				break;
			}
			if(player.isBust(playerHand) == true)
			{
				System.out.println("Players 1st hand: " + playerHand[0] + "/" + playerHand[1]);
				System.out.println("Busted..");
				break;
			}
			System.out.println("Players 1st hand is " + playerHand[0] + "/" + playerHand[1]);
			
		}
		while(true)
		{
			System.out.println("What would you like to do for your second hand? hit or stand");
			String a1 = scan.next();
			if(a1.equals("hit"))
			{
				player.hit(playerSecondHand, deck[topOfDeck]);
				dk.valueOfCount(deck[topOfDeck]);
				topOfDeck--;
			}
			if(a1.equals("stand"))
			{
				System.out.println("Players 2nd hand is " + playerSecondHand[0] + "/" + playerSecondHand[1]);
				break;
			}
			if(player.isBust(playerSecondHand) == true)
			{
				System.out.println("Players 2nd hand: " + playerSecondHand[0] + "/" + playerSecondHand[1]);
				System.out.println("Busted..");
				break;
			}
			System.out.println("Players 2nd hand is " + playerSecondHand[0] + "/" + playerSecondHand[1]);
		}
		player.setHand(playerHand);
		player.setSecondHand(playerSecondHand);
	}
	/**
	 * Checks to see if the wager value is appropiate with the player
	 * @param wagerValue	corrected wager value if illegial
	 * @return	returns wager value
	 * @throws InputMismatchException 
	 */
	public static double checkWager(double wagerValue) throws InputMismatchException
	{
		while(true)
		{
			if(player.getBalance() - wagerValue < 0) //if wager results in a negative balance
			{
				System.out.println("Invalid wager amount, please enter again");
			}
			else if(wagerValue <= 0) //if a wager is negative or 0
			{
				System.out.println("Invalid wager amount, please enter again");
			}
			else 
			{
				break;
			}
			wagerValue = scan.nextDouble();
		}
		return wagerValue;
	}
	/**
	 * 
	 * @param command	string of either hit stand dd or split
	 * @param playerWager	checks players wager with command
	 * @return	new command if illegal command was entered
	 */
	public static String checkCommand(String command, double playerWager)
	{
		while(true)
		{
			String validCommand1 = "";
			String validCommand2 = "";
			String validCommand3 = "hit";
			String validCommand4 = "stand";
			if(player.isSplitable() == true && player.getBalance() >= playerWager*2)
			{
				validCommand1 = "split";
			}
			if(player.getBalance() >= playerWager*2)
			{
				validCommand2 = "dd";
			}
			if(command.equals(validCommand1) && command.length() >= 2)
			{
				return "split";
			}
			if(command.equals(validCommand2) && command.length() >= 2)
			{
				return "dd";
			}
			if(command.equals(validCommand3))
			{
				return "hit";
			}
			if(command.equals(validCommand4))
			{
				return "stand";
			}
			System.out.println("Invalid command: Please enter again");
			command = scan.next();
		}
	}
}
