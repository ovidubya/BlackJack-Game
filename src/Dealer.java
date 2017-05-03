import java.lang.Math;
/**
 * This is a simple blackjack program for SER 215.
 * 
 * 
 * @author Paul Hood, Aaron Monahan Ovadia Shalom, Christopher Vasquez
 * @version 2/24/2017
 */
public class Dealer {
	private int[] hand = new int[2];
	private boolean isBlackJack; //TODO:// need implemation
	public Dealer()
	{
		this.isBlackJack = false;
	}
	public int[] returnHand()
	{
		return hand;
	}
	/**
	 * Code is not used
	 *TODO:need to work out a function for dealer to get blackjack.
	 * @return
	 */
	public boolean IsBlackJack()
	{
		return this.isBlackJack;
	}
	public void getHand(Card card1, Card card2)
	{
		if(card1.getValue() == 11 || card2.getValue() == 11)
		{
			if(card1.getValue() == 11)
			{
				hand[0] = 1 + card2.getValue();
				hand[1] = 11 + card2.getValue();
			}
			if(card2.getValue() == 11)
			{
				hand[0] = 1 + card1.getValue();
				hand[1] = 11 + card1.getValue();
			}
		}
		else
		{
			hand[0] = card1.getValue() + card2.getValue();
			hand[1] = 0;
		}
	}
	public void printHand(Card x, Card y)
	{
		System.out.println("Dealers Hand:");
		System.out.println(x.getName() + " of " + x.getSuit() + " --- " + "[Hidden]");
		System.out.println(x.getValue() + "/" + "?");
	}
	public void printHandUnhidden()
	{
		System.out.println("Dealers Hand:");
		System.out.println(hand[0] + "/" + hand[1]);
	}
	/**
	 * Dealer hits a card and prints value and hand.
	 * @param nextCard		of the deck object
	 */
	public void hit(Card nextCard)
	{
		System.out.println("Dealers Hand:");
		System.out.println(hand[0] + "/" + hand[1]);
		System.out.println("Dealer draws the " + nextCard.getName() + " of " + nextCard.getSuit());
		if(nextCard.getValue() == 11)
		{
			if(hand[1] == 0)
			{
				if(hand[0] < 10)
				{
					hand[0] = hand[0] + 1;
					hand[1] = hand[0] + 10;
				}
				else if(hand[0] == 10)
				{
					hand[0] = 11;
					hand[1] = 21;
				}
				else 
				{
					hand[0] = hand[0] + 1;
					hand[1] = 0;
				}
			}
			else
			{
				hand[0] = hand[0] + 1;
				hand[1] = hand[1] + 1;
			}
		}
		else
		{
			if(hand[1] == 0)
			{
				hand[0] = hand[0] + nextCard.getValue();
			}
			else
			{
				if(hand[1] + nextCard.getValue() <= 21)
				{
					hand[0] = hand[0] + nextCard.getValue();
					hand[1] = hand[1] + nextCard.getValue();
				}
				else
				{
					hand[1] = 0;
					hand[0] = hand[0] + nextCard.getValue();
				}
			}
		}
		System.out.println("Dealers Hand: " + hand[0] + "/" + hand[1]);
	}
	/**
	 * Checks to see if the dealer hit 17 or more
	 * @return	return true if dealer hit 17 or more, or false if not.
	 */
	public boolean isSeventeen()
	{
		return Math.max(hand[0], hand[1]) >= 17;
	}
	/**
	 * Checks to see if the dealer busted
	 * @return	true if dealer busted, false if not
	 */
	public boolean isBust()
	{
		return Math.max(hand[0], hand[1]) > 21;
	}
}
