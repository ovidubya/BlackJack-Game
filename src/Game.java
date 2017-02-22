import java.util.Scanner;
import java.lang.Math;
/**
 * This is a simple blackjack program for SER 215.
 * @author Paul Hood, Aaron Monahan Ovadia Shalom, Christopher Vasquez,
 * @version 2/16/2017
 */
public class Game {
	static Scanner scan = new Scanner(System.in);
	static int[] playerHand = new int[2];
	static int[] playerSecondHand = new int[2]; //for split function
	static int[] dealerHand = new int[2];
	static Deck dk = new Deck();
	static String[] deck = dk.getShuffledDeck();
	static int top = 51;
	static int balance = 100;
	static boolean canSplit = false;
	static boolean canDoubleDown = false;
	static int splitValue = 0;
	public static void main(String[] args) {
		
		while(true)
		{
			//draw
			System.out.println(top);
			System.out.println("Your balance is " + balance);
			System.out.println("The count is  " + dk.getCount());
			System.out.println("Enter a bet value");
			int wager = scan.nextInt();
			printPlayerHand(top);
			printDealerHand(top);
			System.out.print("You can either: " + "hit " + "or " + "hold");
			if(balance >= wager)
			{
				System.out.println(" or DD");
			}
			while(didPlayerBust() == false)
			{
				String a1 = scan.next();
				if(a1.equals("DD"))
				{
					wager = wager * 2;
					hit();
					if(didPlayerBust() == true)
					{
						balance -= wager;
						break;
					}
					while(didDealerSeventeen() == false)
					{
						hold();
					}
					if(didDealerSeventeen() == true)
					{
						int x = checkWinner(); //was checkWinner()2
						if(x == 1)
						{
							System.out.println("Player win!");
							balance += wager;
							break;
						}
						else if(x == 2)
						{
							System.out.println("Dealer wins");
							balance -= wager;
							System.out.println("Dealers cards after hit: " + dealerHand[0] + "/" + dealerHand[1]);
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
					hit();
					if(didPlayerBust() == true)
					{
						balance -= wager;
						break;
					}
				}
				if(a1.equals("hold"))
				{
					while(didDealerSeventeen() == false)
					{
						hold();
					}
					if(didDealerSeventeen() == true)
					{
						int x = checkWinner(); //was checkWinner()2
						if(x == 1)
						{
							System.out.println("Player win!");
							balance += wager;
							break;
						}
						else if(x == 2)
						{
							System.out.println("Dealer wins");
							balance -= wager;
							System.out.println("Dealers cards after hit: " + dealerHand[0] + "/" + dealerHand[1]);
							break;
						}
						else
						{
							System.out.println("Tie");
							break;
						}
					}
				}
			}
			System.out.println("");
			System.out.println("");
		}
		
	}
	/**
	 * Prints outs players hand by taking the top card of the deck and the second to top card
	 * Prints the name of the Cards aswell as the values
	 * @param i		takes the index of the top of the deck
	 */
	public static void printPlayerHand(int i)
	{
		//Records the count of players hand
		dk.valueOfCount(deck[i]);
		dk.valueOfCount(deck[i-1]);
		
		//if player is holding cards of equal value, he can split
		if(dk.getCardValue(deck[i]) == dk.getCardValue(deck[i-1]))
		{
			canSplit = true;
			splitValue = dk.getCardValue(deck[i]);
		}
		System.out.println(deck[i] + " -- " + deck[i-1]);
		if(dk.getCardValue(deck[i]) == 11 || dk.getCardValue(deck[i-1]) == 11) //one of the two cards is an ace
		{
			if(dk.getCardValue(deck[i]) == 11) //if the first card is an ace
			{
				System.out.println((1 + dk.getCardValue(deck[i-1])) + "/" + (11 + dk.getCardValue(deck[i-1])));
				playerHand[0] = 1 + dk.getCardValue(deck[i-1]);
				playerHand[1] = 11 + dk.getCardValue(deck[i-1]);
			}
			if(dk.getCardValue(deck[i-1]) == 11) //if the second card is an ace
			{
				System.out.println((1 + dk.getCardValue(deck[i])) + "/" + (11 + dk.getCardValue(deck[i])));
				playerHand[0] = 1 + dk.getCardValue(deck[i]);
				playerHand[1] = 11 + dk.getCardValue(deck[i]);
			}
		}
		else
		{
			System.out.println(dk.getCardValue(deck[i]) + dk.getCardValue(deck[i-1]));
			playerHand[0] = dk.getCardValue(deck[i]) + dk.getCardValue(deck[i-1]);
			playerHand[1] = 0;
		}
		top-=2;
	}
	/**
	 * Prints out the dealers cards with the second card being hidden
	 * @param i		takes the index of the top of the deck
	 */
	public static void printDealerHand(int i)
	{
		dk.valueOfCount(deck[i]);
		dk.valueOfCount(deck[i-1]);
		System.out.println(deck[i] + " -- " + "[Hidden]"); //deck[i-1] should be hidden
		System.out.println(dk.getCardValue(deck[i]) + "/" + "?"); //deck[i-1] should be hidden
		int hiddenCard = dk.getCardValue(deck[i-1]);
		if(dk.getCardValue(deck[i]) == 11 || hiddenCard == 11)
		{
			if(dk.getCardValue(deck[i]) == 11) //if the first card is an ace
			{
				dealerHand[0] = 1 + dk.getCardValue(deck[i-1]);
				dealerHand[1] = 11 + dk.getCardValue(deck[i-1]);
			}
			if(dk.getCardValue(deck[i-1]) == 11) //if the second card is an ace
			{
				dealerHand[0] = 1 + dk.getCardValue(deck[i]);
				dealerHand[1] = 11 + dk.getCardValue(deck[i]);
			}
		}
		else
		{
			dealerHand[0] = dk.getCardValue(deck[i]) + dk.getCardValue(deck[i-1]);
			dealerHand[1] = 0;
		}
		top-=2;	
	}
	/**
	 * Adds one card to the players hand
	 * @return		true if player did not bust
	 */
	public static boolean hit()
	{
		dk.valueOfCount(deck[Game.top]);
		int nextCard = dk.getCardValue(deck[top]);
		System.out.println("The next card is " + deck[top]);
		top--;
		if(playerHand[1] == 0)
		{
			if(nextCard == 11)
			{
				if(playerHand[0] + nextCard <= 21)
				{
					playerHand[0] += 1; 
					playerHand[1] += 11;
				}
				else
				{
					playerHand[0] += 1;
					if(playerHand[0] > 21)
					{
						return false;
					}
				}
			}
			else
			{
				playerHand[0] = playerHand[0] + nextCard;
				if(playerHand[0] > 21)
				{
					return false;
				}
			}
		}
		else        //if(playerHand[1] != 0)
		{
			if(nextCard == 11)
			{
				//talking about a 3/13 or a 9/19
				playerHand[0] += 1;
				playerHand[1] += 1;
				return true;
			}
			else
			{
				if(playerHand[0] + nextCard <= 21)
				{
					playerHand[0] += nextCard;
				}
				if(playerHand[1] + nextCard <= 21)
				{
					playerHand[1] += nextCard;
				}
				if(playerHand[1] + nextCard > 21)
				{
					playerHand[1] = 0;
				}
				return true;
			}
			
		}
		System.out.println("Players hand is: " + playerHand[0] + "/" + playerHand[1]);
		return false;
	}
	/**
	 * Reveals card of the dealer, as well as adds a card if the dealer did not hit 17 or more.
	 */
	public static void hold()
	{
		
		System.out.println("Dealers cards: " + dealerHand[0] + "/" + dealerHand[1]);
		if(dealerHand[0] >= 17 || dealerHand[1] >= 17)
		{
			//DO NOTHING
		}
		else
		{
			dk.valueOfCount(deck[top]);
			System.out.println("Dealer draws a " + deck[top]);
			int nextCard = dk.getCardValue(deck[top]);
			top--;
			if(dealerHand[1] == 0)
			{
				if(nextCard == 11)
				{
					if(dealerHand[0] + nextCard > 21)
					{
						dealerHand[0] += 1;
					}
					else
					{
						dealerHand[0] += 11;
					}
				}
				else
				{
					dealerHand[0] += nextCard;
				}
			}
			else
			{
				if(nextCard == 11)
				{
					dealerHand[0] += 1;
					dealerHand[1] += 1;
				}
				else
				{
					if(dealerHand[1] + nextCard <= 21)
					{
						dealerHand[1] += nextCard;
					}
					else
					{
						dealerHand[1] = 0;
					}
					dealerHand[0] += nextCard;
				}
			}
		}
		System.out.println("Dealers cards after hit: " + dealerHand[0] + "/" + dealerHand[1]);
		
	}
	public static boolean didPlayerBust()
	{
		if(playerHand[0] > 21 || playerHand[1] > 21)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public static boolean didDealerBust()
	{
		if(dealerHand[0] > 21 || dealerHand[1] > 21)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public static boolean didDealerSeventeen()
	{
		if(dealerHand[0] >= 17 || dealerHand[1] >= 17)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public static int checkWinner()
	{
		int playerScore = Math.max(playerHand[0], playerHand[1]);
		int dealerScore = Math.max(dealerHand[0], dealerHand[1]);
		if(playerScore > 21)
		{
			return 2; //Dealer Win
		}
		else if(dealerScore > 21 && playerScore <= 21)
		{
			return 1; //Player win
		}
		else if(playerScore ==  dealerScore)
		{
			return 3; //Tie game
		}
		else if(playerScore > dealerScore)
		{
			return 1;
		}
		else
		{
			return 2;
		}
	}
	public static void split()
	{
		playerHand[0] = splitValue;
		playerSecondHand[0] = splitValue;
		System.out.println("Splitting...");
		while(true)
		{
			
		}
	}
}
