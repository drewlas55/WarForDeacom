import java.io.*; 
import java.util.*;

public class War{
	//we have the deck, each players hand, the pile of winning cards, and a pile for when there is war
	private static Stack<Card> deck = new Stack<Card>();
	private static Stack<Card> player1 = new Stack<Card>();
	private static Stack<Card> player2 = new Stack<Card>();
	private static Stack<Card> pile1 = new Stack<Card>();
	private static Stack<Card> pile2 = new Stack<Card>();
	private static Stack<Card> warPile = new Stack<Card>();
	public static int round = 1;
	public static boolean verbose = false;
	public static boolean turn = false;

	//this is the start of our game
	public static void war(){
		deal();
		System.out.print("Begin playing! ");
		play();
	}
	//play until one of the players has no cards
	public static void play(){
		//result is the result of comparing our cards
		int result;

		while(true){
			//check if the game is over
			gameOverCheck();
			if(verbose){
				System.out.print("Player 1's hand size:"); 
				System.out.print(player1.size() + "\t");
				System.out.print("Player 1's pile size:"); 
				System.out.println(pile1.size());

				System.out.print("Player 2's hand size:");
				System.out.print(player2.size() + "\t");
				System.out.print("Player 1's pile size:"); 
				System.out.println(pile2.size() + "\n");
			}

			//if the game is not over, each player should play their next card
			Card player1Card = player1.pop();
			Card player2Card = player2.pop();
			//compare the two cards for our result
			result = player1Card.compareTo(player2Card);
			if(result > 0){
				//message player 1 won and add to their pile
				System.out.println("Player 1 wins round " + round + ": " + player1Card.toString() + "beats " + player2Card.toString());
				pile1.push(player1Card);
				pile1.push(player2Card);
			}
			else if(result < 0){
				//message player 2 won and add to their pile
				System.out.println("Player 2 wins round " + round + ": " + player2Card.toString() + "beats " + player1Card.toString());
				pile2.push(player1Card);
				pile2.push(player2Card);
			}
			else{
				//message it resulted in a tie and they are going to war
				System.out.println("WAR at round " + round + ": " + player1Card.toString() + " ties " + player2Card.toString());
				tie(player1Card, player2Card);
			}
			round++;//increment the round
		}
	}

	//this method plays out what happens when there is a tie
	public static void tie(Card card1, Card card2){
		//if a player does not have enough cards in their hand, they will pick the amount they need from their respective piles
		if(player1.size() < 3){
			while(player1.size() < 4){
				//if they dont have three cards to put out and their pile is empty then game is over
				if(pile1.empty()){
					System.out.println("Player 1 does not have enough cards to fight the war, Player 2 Wins!!");
					System.exit(0);
				}
				else{
					player1.push(pile1.pop());
				}
			}
		}
		if(player2.size() < 3){
			while(player2.size() < 4){
				if(pile2.empty()){
					System.out.println("Player 2 does not have enough cards to fight the war, Player 1 Wins!!");
					System.exit(0);
				}
				else{
					player2.push(pile2.pop());
				}
			}
		}
		//add the two flipped cards to the warPile along with two unturned cards from each players deck
		warPile.push(card1);
		warPile.push(card2);

		warPile.push(player1.pop());
		warPile.push(player1.pop());
		warPile.push(player2.pop());
		warPile.push(player2.pop());

		//now turn two cards to see who wins the war
		Card player1Card = player1.pop();
		Card player2Card = player2.pop();
		int result = player1Card.compareTo(player2Card);

		if(result > 0){
			//message player 1 won the war and give them the cards from the war pile
			System.out.println("Player 1 wins the war in round " + round + ": " + player1Card.toString() + " beats " + player2Card.toString());
			while(!warPile.empty()){
				pile1.push(warPile.pop());
			}
			pile1.push(player1Card);
			pile1.push(player2Card);
		}
		else if(result < 0){
			//message player 2 won the war and give them the cards from the war pile
			System.out.println("Player 2 wins the war in round " + round + ": " + player2Card.toString() + " beats " + player1Card.toString());
			while(!warPile.empty()){
				pile2.push(warPile.pop());
			}
			pile2.push(player1Card);
			pile2.push(player2Card);
		}
		else{
			//message that there was another tie, so there will be another round of war
			System.out.println("WAR at round " + round + ": " + player1Card.toString() + " ties " + player2Card.toString());
			tie(player1Card, player2Card);
		}
	}


	//check if one of the players has no more cards
	public static void gameOverCheck(){
		//if player 1 is out of cards then play 2 wins
		if(player1.size() == 0){
			if(pile1.size() == 0){
				System.out.println("Player 2 Wins!!");
				System.exit(0);
			}
			else{
				//if they have cards in their winnings pile, shuffle that pile then make it their current hand
				Collections.shuffle(pile1, new Random());
				player1 = (Stack<Card>)pile1.clone();
				pile1.clear();
			}
		}
		//if player 2 is out of cards then player 1 wins
		if(player2.size() == 0){
			if(pile2.size() == 0){
				System.out.println("Player 1 Wins!!");
				System.exit(0);
			}
			else{
				Collections.shuffle(pile2, new Random());
				player2 = (Stack<Card>)pile2.clone();
				pile2.clear();
			}
		}
	}

	//this will deal both players their hand, splitting the deck in two
	public static void deal(){
		System.out.println("Now dealing the cards to the players...");
		Collections.shuffle(deck, new Random());
		for(int i = 0; i < 26; i++){
			player1.push(deck.pop());
			player2.push(deck.pop());
		}
		//show each players hands
		if(verbose){
			System.out.println("Player 1's hand:");
			System.out.println(player1.toString());
			System.out.println("Player 2's hand:");
			System.out.println(player2.toString());
		}
	}

	//Creates our deck
	public static void createDeck(){
		for (Card.Suits s: Card.Suits.values())
		{	
			for (Card.Ranks r: Card.Ranks.values())
			{
				Card c =new Card(s,r);
				deck.push(c);
			}
		}
	}

	//Our main class initiating the deck and starting the game
	public static void main(String[] args){
		System.out.println("Welcome to the game of War");

		System.out.println("If you would like to enable Verbose mode, Enter V, if not, just press enter");
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		if(input.equals("V")){
			verbose = true;
		}
		else if(input.isEmpty()){
			verbose = false;
		}
		scanner.close();
		createDeck();
		war();
	}
}