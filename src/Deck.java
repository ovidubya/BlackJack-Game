import java.util.Random;
/**
 * This is a simple blackjack program for SER 215.
 * 
 * The deck class contains 3 attributes. rawDeck, count, and deck
 * rawDeck is a string array with all 52 cards. E.g "Six of Hearts", "Ace of Clubs" "King of Diamonds", etc
 * count contains a int value of each card that gets removed from the deck.
 * deck contains a array of 52 card objects with which is intialized from fillDeck method
 * 
 * @author Paul Hood, Aaron Monahan Ovadia Shalom, Christopher Vasquez
 * @version 2/24/2017
 */
public class Deck {
	//deck of a 52 cards in String format
	private String[] rawDeck = {
			"Ace of Hearts",
			"Ace of Diamonds",
			"Ace of Clubs",
			"Ace of Spades",
			"Two of Hearts",
			"Three of Hearts",
			"Four of Hearts",
			"Five of Hearts",
			"Six of Hearts",
			"Seven of Hearts",
			"Eight of Hearts",
			"Nine of Hearts",
			"Ten of Hearts",
			"Jack of Hearts",
			"Queen of Hearts",
			"King of Hearts",
			"Two of Spades",
			"Three of Spades",
			"Four of Spades",
			"Five of Spades",
			"Six of Spades",
			"Seven of Spades",
			"Eight of Spades",
			"Nine of Spades",
			"Ten of Spades",
			"Jack of Spades",
			"Queen of Spades",
			"King of Spades",
			"Two of Clubs",
			"Three of Clubs",
			"Four of Clubs",
			"Five of Clubs",
			"Six of Clubs",
			"Seven of Clubs",
			"Eight of Clubs",
			"Nine of Clubs",
			"Ten of Clubs",
			"Jack of Clubs",
			"Queen of Clubs",
			"King of Clubs",
			"Two of Diamonds",
			"Three of Diamonds",
			"Four of Diamonds",
			"Five of Diamonds",
			"Six of Diamonds",
			"Seven of Diamonds",
			"Eight of Diamonds",
			"Nine of Diamonds",
			"Ten of Diamonds",
			"Jack of Diamonds",
			"Queen of Diamonds",
			"King of Diamonds",
			};
	private int count; //count of cards
	private Card[] deck; //deck that will be used in the game
	public Deck()
	{
		//initialize deck object with 52 Card objects with null values 
		deck = new Card[52];
	}
	/**
	 * Iterates through the rawDeck. Splits each string in the raw deck to String[] tempCard. 
	 * After split tempCard will contain 3 strings e.g "Six of Clubs" ==> "Six", "of", "Clubs"
	 * 0th index will be the name of the card
	 * 2th index will be the suit of the card
	 * 
	 */
	public void fillDeck()
	{
		for(int i = 0; i < rawDeck.length; i++)
		{
			String[] tempCard = rawDeck[i].split(" "); //splits String to array of Strings e.g "Six", "of", "Clubs"
			String tempName = tempCard[0]; //tempName will be the 0th index of the tempCard e.g "Six"
			int tempValue = getCardValue(tempName); //tempValue will be card value of tempName e.g getCardValue("Six") ==> 6
			String tempSuit = tempCard[2]; //tempSuit will be the 2th index of tempCard e.g "Clubs"
			deck[i] = new Card(tempName, tempSuit, tempValue); //initialize card object in deck[i] with the following attributes
		}
	}
	/**
	 * Shuffles deck object
	 * @param deck	shuffles deck object
	 */
	public void shuffleDeck(Card[] deck)
	{
		fillDeck(); //before shuffling deck, need to initialize first
	    Random rnd = new Random(); //random object
	    for (int i = deck.length - 1; i > 0; i--)
	    {
	      int index = rnd.nextInt(i + 1);
	      // Simple swap
	      Card a = deck[index];
	      deck[index] = deck[i];
	      deck[i] = a;
	    }
	 }
	/**
	 * Retuns a copy of the deck object
	 * @return	copyDeck	returns a copy of the deck object
	 */
	public Card[] getShuffledDeck()
	{
		Card[] copyDeck = deck;
		shuffleDeck(copyDeck);
		return copyDeck;
	}
	/**
	 * Returns the value of the card based on the name of the card
	 * @param n	name of the card
	 * @return	returns the value of the card
	 */
	public int getCardValue(String n)
	{
		if(n.equals("Two"))
		{
			return 2;
		}
		if(n.equals("Three"))
		{
			return 3;
		}
		if(n.equals("Four"))
		{
			return 4;
		}
		if(n.equals("Five"))
		{
			return 5;
		}
		if(n.equals("Six"))
		{
			return 6;
		}
		if(n.equals("Seven"))
		{
			return 7;
		}
		if(n.equals("Eight"))
		{
			return 8;
		}
		if(n.equals("Nine"))
		{
			return 9;
		}
		if(n.equals("Ten"))
		{
			return 10;
		}
		if(n.equals("Jack"))
		{
			return 10;
		}
		if(n.equals("Queen"))
		{
			return 10;
		}
		if(n.equals("King"))
		{
			return 10;
		}
		if(n.equals("Ace"))
		{
			return 11;
		}
		return -1;
	}
	/**
	 * Returns the current count
	 * @return	count	count of cards out of the deck
	 */
	public int getCount()
	{
		return count;
	}
	
	/**
	 * Resets the count to 0
	 */
	public void resetCount()
	{
		count = 0;
	}
	/**
	 * Returns the count of a card object
	 * @param n		card object 
	 */
	public void valueOfCount(Card n)
	{
		int value = n.getValue(); //gets the value of the card
		if(value == 2 || value == 3 || value == 4 || value == 5 || value == 6) //if value is 2 3 4 5 6, add 1 to count
		{
			count++; //Plus 1
		}
		else if(value == 10 || value == 11) //if card is jack, queen, king, or ace, minus 1 to count
		{
			count--; //Minus 1
		}
		else //if card is 7 8 9, do nothing
		{
			count += 0; // Zero 
		}
	}
}
