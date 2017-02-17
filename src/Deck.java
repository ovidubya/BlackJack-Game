import java.util.Random;

public class Deck {
	private int count = 0;
	private String[] deck = {
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
	public Deck()
	{
	}
	public String[] getShuffledDeck()
	{
		String[] copyDeck = deck;
		shuffleDeck(copyDeck);
		return copyDeck;
	}
	public void shuffleDeck(String[] ar)
	{
	    // If running on Java 6 or older, use `new Random()` on RHS here
	    Random rnd = new Random();
	    for (int i = ar.length - 1; i > 0; i--)
	    {
	      int index = rnd.nextInt(i + 1);
	      // Simple swap
	      String a = ar[index];
	      ar[index] = ar[i];
	      ar[i] = a;
	    }
	 }
	public int getCardValue(String n)
	{
		String[] card = n.split(" ");
		if(card[0].equals("Two"))
		{
			return 2;
		}
		if(card[0].equals("Three"))
		{
			return 3;
		}
		if(card[0].equals("Four"))
		{
			return 4;
		}
		if(card[0].equals("Five"))
		{
			return 5;
		}
		if(card[0].equals("Six"))
		{
			return 6;
		}
		if(card[0].equals("Seven"))
		{
			return 7;
		}
		if(card[0].equals("Eight"))
		{
			return 8;
		}
		if(card[0].equals("Nine"))
		{
			return 9;
		}
		if(card[0].equals("Ten"))
		{
			return 10;
		}
		if(card[0].equals("Jack"))
		{
			return 10;
		}
		if(card[0].equals("Queen"))
		{
			return 10;
		}
		if(card[0].equals("King"))
		{
			return 10;
		}
		if(card[0].equals("Ace"))
		{
			return 11;
		}
		return 0;
	}
	public int getCount()
	{
		return count;
	}
	public void valueOfCount(String n)
	{
		int value = getCardValue(n);
		if(value == 2 || value == 3 || value == 4 || value == 5 || value == 6)
		{
			count++; //Plus 1
		}
		else if(value == 10 || value == 11)
		{
			count--; //Minus 1
		}
		else
		{
			count += 0; // Zero
		}
	}
}
