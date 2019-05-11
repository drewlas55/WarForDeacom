public class Card implements Comparable<Card>{
	//the Suits the cards can be
	public static enum Suits {Spades, Clubs, Diamonds, Hearts}
	//the Ranks the cards can be
	public static enum Ranks {Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace}
	private Suits suit;
	private Ranks rank;
	
	//card initilizer
	public Card(Suits s, Ranks r)
	{
		suit = s;
		rank = r;
	}

	// Equals is only true if both the suit and the rank of the cards match
	public boolean equals(Object rhs)
	{
		Card rside = (Card) rhs;
		if (suit == rside.suit && rank == rside.rank)
			return true;
		else
			return false;
	}
	// returns the card into a string represntation
	public String toString()
	{
		return (rank + "-of-" + suit);
	}

	// Compare one Card to another Card
	public int compareTo(Card rhs)
	{
		return rank.compareTo(rhs.rank);
	}

}