/**
 * 
 * 
 * @author Paul Hood, Aaron Monahan Ovadia Shalom, Christopher Vasquez
 * @version 2/24/2017
 */
public class Player {
	private String name; //name of the player
	private double balance; //balance of the player
	private int[] hand = new int[2]; //hand of the player
	private int[] hand2 = new int[2]; //if player can split, then hand2 will be used for the second hand
	private boolean isSplit; //if player can split, set to true
	private boolean isDoubleDown;
	private boolean isBlackJack;
	
	public Player(String name, int balance)
	{
		this.name = name;
		this.balance = balance;
		this.isSplit = false;
		//sets 2nd had to false
		hand2[0] = 0; 
		hand2[1] = 0;
		this.isBlackJack = false;
	}
	/**
	 * Returns if the player got a blackjack
	 */
	public boolean isBlackJack()
	{
		return this.isBlackJack;
	}
	/**
	 * Sets true if the player got a blackjack!
	 */
	public void setBlackJack(boolean value)
	{
		this.isBlackJack = value;
	}
	/**
	 * The hand of the player will consit of 2 spots. hand[0] and hand[1]
	 * if the player has dual values for example a Ace and Five. than hand[0] = 6 and hand[1] = 16.
	 * Since an ace can be either a 1 or a 11.
	 * @return		the hand of the player
	 */
	public int[] returnHand()
	{
		return hand;
	}
	/**
	 * If player is allowed to split, then hand2 will be used to get the card data
	 * @return		the second hand of the player
	 */
	public int[] returnSecondHand()
	{
		return hand2;
	}
	
	/**
	 * Sets the value of the players first hand
	 * @param hand	returns an updated 1st hand for the player
	 */
	public void setHand(int[] hand)
	{
		this.hand = hand;
	}
	/**
	 * Sets the value of the players second hand
	 * @param hand	returns an updated 2nd hand for the player
	 */
	public void setSecondHand(int[] hand)
	{
		this.hand2 = hand;
	}
	/**
	 * Returns the players field variable balance
	 * @return	balance
	 */
	public double getBalance()
	{
		return balance;
	}
	/**
	 * Sets the players field variable balance
	 * @param newBalance
	 */
	public void setBalance(double newBalance)
	{
		balance = newBalance;
	}
	/**
	 * Returns true or false if the player is able to split with the 1st hand
	 * @return	isSplit	field varible for the player
	 */
	public boolean isSplitable()
	{
		return isSplit;
	}
	public boolean isDoubleDown()
	{
		return isDoubleDown;
	}
	public void setDoubleDown(boolean setValue)
	{
		this.isDoubleDown = setValue;
	}
	
	/**
	 * This method is called when the player starts a new round after he makes a wager with the dealer.
	 * The player gets two cards, card1 and card2 from the deck class and sets up the hand field varible.
	 * @param card1
	 * @param card2
	 */
	public void getHand(Card card1, Card card2)
	{
		//If the first card and the second card both have the same name i.e Five and Five, Ten and Ten.
		//Then the player's field varible isSplit is set to true.
		if(card1.getName().equals(card2.getName()))
		{
			setSplit(true);
		}
		//if the first card is a 11 and the second is a 10 or the first is a 10 and the second card is a 11.
		//the the player got a blackjack!!
		if((card1.getValue() == 11 && card2.getValue() == 10) || (card1.getValue() == 10 && card2.getValue() == 11))
		{
			setBlackJack(true);
		}
		
		//If either card value is 11 (which is an Ace)
		//Then the field varible 'hand' will use its second index, hand[1]
		if(card1.getValue() == 11 || card2.getValue() == 11)
		{
			//if both card are aces, than the value is 2 or 12. 1+1 or 11+1. 
			if(card1.getValue() == 11 && card2.getValue() == 11)
			{
				hand[0] = 2;
				hand[1] = 12;
			}
			//if the first card is an ace, and the second is whatever, then 1+secondCard/11+secondCard
			else if(card1.getValue() == 11)
			{
				hand[0] = 1 + card2.getValue();
				hand[1] = 11 + card2.getValue();
			}
			//if the second card is an ace, then same as above. hand[0] = 1 + firstCard/ hand[1] = 11 + firstCard
			else if(card2.getValue() == 11)
			{
				hand[0] = 1 + card1.getValue();
				hand[1] = 11 + card1.getValue();
			}
		}
		else
		{
			//card 1 and card 2 doesnt have an ace, add it up and put it in hand[0] and make hand[1] = 0. 
			hand[0] = card1.getValue() + card2.getValue();
			hand[1] = 0;
		}
	}
	
	/**
	 * Prints players hand with suit and value
	 * @param x
	 * @param y
	 */
	public void printHand(Card x, Card y)
	{
		System.out.println("Players Hand:");
		System.out.println(x.getName() + " of " + x.getSuit() + " --- " + y.getName() + " of " + y.getSuit());
		System.out.println(hand[0] + "/" + hand[1]);
	}
	/**
	 * Returns field variable name of the player
	 * @return	name 	of the player
	 */
	public String getname()
	{
		return name;
	}
	
	/**
	 *  
	 */
	public void stand()
	{
		//TODO: No function is needed
	}
	
	/**
	 * Adds the next card value to the hand
	 * @param hand	the players hand, either first or second
	 * @param nextCard	the next card from the deck 
	 */
	public void hit(int[] hand, Card nextCard)
	{
		System.out.println("The next card is the " + nextCard.getName() + " of " + nextCard.getSuit());
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
		System.out.println("Players Hand: " + hand[0] + "/" + hand[1]);
	}
	/**
	 * Method that returns true or false if the players hand is busted
	 * @param hand	players hand field variable 
	 * @return	true if player's hand is busted
	 */
	public boolean isBust(int[] hand)
	{
		return Math.max(hand[0], hand[1]) > 21;
	}
	public void doubleDown()
	{
		
	}
	
	/**
	 * Splits the first hand value e.g 16 to 8/0 and 8/0.
	 */
	public void split()
	{
		//if 4/0 6/0 8/0 16/0 20/0 if 2/0
		hand[0] = hand[0] / 2;
		hand[1] = 0;
		
		hand2[0]= hand[0];
		hand2[1] = 0;
	}
	public void insure()
	{
		
	}
	/**
	 * Sets the isSplit field variable 
	 * @param split		true or false to be set
	 */
    public void setSplit(boolean split)
    {
    	this.isSplit = split;
    }
}
