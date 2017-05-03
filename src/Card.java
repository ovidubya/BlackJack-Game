/**
 * This is a simple blackjack program for SER 215.
 * 
 * @author Paul Hood, Aaron Monahan Ovadia Shalom, Christopher Vasquez
 * @version 2/24/2017
 */
public class Card {
	private String suit; //suit of the card
	private String name; //name of the card
	private int value; //value of the card
	
	/**
	 * Initialize card object with the following attributes 
	 * @param tempName		name of the card, e.g "Ace", "Three", "Jack", etc
	 * @param tempSuit		suit of the card, e.g "Spades", "Hearts", etc
	 * @param tempValue		value of the card, e.g 2, 10, 11, etc
	 */
	public Card(String tempName, String tempSuit, int tempValue) 
	{
		this.name = tempName;
		this.suit = tempSuit;
		this.value = tempValue;
	}
	
	/**
	 * Changes the suit of the card
	 * @param	 n	sets the cards suit to n
	 */
	public void setSuit(String n)
	{
		suit = n;
	}
	
	/**
	 * Returns the suit of the card, e.g "Hearts", "Spades" etc
	 * @return	String	the suit of the card
	 */
	public String getSuit()
	{
		return suit;
	}
	
	/**
	 * Changes the name of the card
	 * @param n		sets name of the card
	 */
	public void setName(String n)
	{
		name = n;
	}
	
	/**
	 * Returns the name of the card, e.g "Ace", "Two". "Three" etc
	 * @return	returns the name of the card
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Sets the value of the card
	 * @param n	value of the card to be set too
	 */
	public void setValue(int n)
	{
		value = n;
	}
	
	/**
	 * Returns the value of the card
	 * E.g 2, 3, 5, 10, 11
	 * @return	int	value of the card
	 */
	public int getValue()
	{
		return value;
	}
}
