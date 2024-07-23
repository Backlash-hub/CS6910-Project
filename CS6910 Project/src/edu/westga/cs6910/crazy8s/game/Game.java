package edu.westga.cs6910.crazy8s.game;

import java.io.Serializable;
import java.util.ArrayList;

import edu.westga.cs6910.crazy8s.model.Card;
import edu.westga.cs6910.crazy8s.model.ComputerPlayer;
import edu.westga.cs6910.crazy8s.model.Deck;
import edu.westga.cs6910.crazy8s.model.HumanPlayer;
import edu.westga.cs6910.crazy8s.model.Rank;

/**
 * Game represents the logic for the game crazy8s
 * 
 * @author William Pevytoe
 * 
 * @version 6/18/2024
 */
public class Game implements Serializable {

	private static final long serialVersionUID = 1L;

	private Deck deck;
	private HumanPlayer humanPlayer;
	private ComputerPlayer computerPlayer;
	private Card tableCard;

	private int gamesPlayed;
	private int humanWins;
	private int computerWins;
	private int tiedGames;

	/**
	 * Creates the starting of the game
	 * 
	 * @param humanName    the name of the human player
	 * @param computerName the name of the computer player
	 */
	public Game(String humanName, String computerName) {
		this.deck = new Deck();
		this.humanPlayer = new HumanPlayer(humanName);
		this.computerPlayer = new ComputerPlayer(computerName, this);
		this.gamesPlayed = 0;
		this.humanWins = 0;
		this.computerWins = 0;
		this.tiedGames = 0;
	}

	/**
	 * Gets number of games played
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return number of games played
	 */
	public int getGamesPlayed() {
		return this.gamesPlayed;
	}
	
	/**
	 * Number of Tied games
	 * 
	 * @return number of tied games
	 */
	public int getTiedGames() {
		return this.tiedGames;
	}
	
	/**
	 * Increments the number of tied Games
	 */
	public void incrementTiedGames() {
		this.tiedGames++;
	}

	/**
	 * Increments the number of games played
	 * 
	 * @precondition none
	 * @postcondition getGamesPlayed() == getGamesPlayed()@prev + 1
	 */
	public void incrementGamesPlayed() {
		this.gamesPlayed++;
	}

	/**
	 * Gets number of human wins
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return number of human wins
	 */
	public int getHumanWins() {
		return this.humanWins;
	}

	/**
	 * Increments the number of games played
	 * 
	 * @precondition none
	 * @postcondition getHumanWins() == getHumanWins()@prev + 1
	 */
	public void incrementHumanWins() {
		this.humanWins++;
	}

	/**
	 * Gets number of computer wins
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return number of computer wins
	 */
	public int getComputerWins() {
		return this.computerWins;
	}

	/**
	 * Increments the number of computer wins
	 * 
	 * @precondition none
	 * @postcondition getComputerWins() == getComputerWins()@prev + 1
	 */
	public void incrementComputerWins() {
		this.computerWins++;
	}

	/**
	 * Play a round of the game
	 * 
	 * @param humanCardIndex is the index of the card to be played
	 */
	public void playRound(int humanCardIndex) {
	    if (humanCardIndex >= 0) {
	        Card humanCard = this.humanPlayer.playCard(humanCardIndex);
	        System.out.println(this.humanPlayer.getName() + " plays " + humanCard);
	        this.tableCard = humanCard;
	    } else {
	        Card drawnCard = this.dealCard();
	        if (drawnCard == null) {
	            return;
	        }
	        System.out.println("No valid card to play. Drawing a card instead.");
	        this.humanPlayer.addCard(drawnCard);
	    }

	    boolean computerPlayed = false;
	    while (!computerPlayed) {
	        for (int position = 0; position < this.computerPlayer.getHand().size(); position++) {
	            Card computerCard = this.computerPlayer.getHand().get(position);
	            if (this.isCardPlayable(computerCard)) {
	                computerCard = this.computerPlayer.playCard(position);
	                System.out.println(this.computerPlayer.getName() + " plays " + computerCard);
	                this.tableCard = computerCard;
	                computerPlayed = true;
	                break;
	            }
	        }
	        if (!computerPlayed) {
	            Card drawnCard = this.dealCard();
	            if (drawnCard == null) {
	                return;
	            }
	            System.out.println("No valid card to play for " + this.computerPlayer.getName() + ". Drawing a card.");
	            this.computerPlayer.addCard(drawnCard);
	        }
	    }
	    this.getResolution();
	}

	/**
	 * Checks if a player's hand is empty and increments the winner's win count.
	 */
	public void getResolution() {
	    if (this.humanPlayer.getHand().isEmpty()) {
	        this.incrementHumanWins();
	        System.out.println("Human wins!");
	        this.incrementGamesPlayed();
	        this.resetGame();
	    } else if (this.computerPlayer.getHand().isEmpty()) {
	        this.incrementComputerWins();
	        System.out.println("Computer wins!");
	        this.incrementGamesPlayed();
	        this.resetGame();
	    } else if (this.isDeckEmpty()) {
	        this.incrementTiedGames();
	        System.out.println("Deck is empty. Game is tied.");
	        this.incrementGamesPlayed();
	        this.resetGame();
	    }
	}

	/**
	 * Initializes the game by shuffling the deck, dealing initial cards to players,
	 * and selecting the starting card.
	 */
	public void initializeGame() {
	    System.out.println("games: " + this.gamesPlayed);
	    System.out.println("H wins: " + this.humanWins);
	    System.out.println("Ties: " + this.tiedGames);
	    this.computerPlayer.clearHand();
	    this.humanPlayer.clearHand();
	    this.deck.shuffleDeck();
	    for (int position = 0; position < 5; position++) {
	        Card card = this.deck.deal();
	        this.humanPlayer.addCard(card);
	    }
	    for (int position = 0; position < 5; position++) {
	        Card card = this.deck.deal();
	        this.computerPlayer.addCard(card);
	    }
	    this.tableCard = this.deck.deal();
	    System.out.println("Hands have been dealt and the starting card is a(n) " + this.tableCard.getRankRank() + " of " + this.tableCard.getSuit());
	}

	/**
	 * Have the user select a card to be played
	 * 
	 * @param player        is the human player
	 * @param selectedIndex the index of the card to be played
	 * @return the selectedIndex or a -1 to throw error
	 */
	public int selectedCard(HumanPlayer player, int selectedIndex) {
		ArrayList<Card> hand = player.getHand();
		if (selectedIndex >= 0 && selectedIndex < hand.size()) {
			Card selectedCard = hand.get(selectedIndex);
			if (this.isCardPlayable(selectedCard)) {
				return selectedIndex;
			} else {
				System.out.println("Invalid card selected. Needs to be an 8 or match the suit/rank.");
			}
		} else {
			System.out.println("Invalid index");
		}
		return -1;
	}

	/**
	 * Checks if the card is valid to be played
	 * 
	 * @param card the card the computer or player is trying to play
	 * @return if the card is playable or not
	 */
	public boolean isCardPlayable(Card card) {
		return card.getRank() == this.tableCard.getRank() || card.getSuit() == this.tableCard.getSuit()
				|| card.getRank() == Rank.EIGHT.getValue();
	}

	/**
	 * Getter for the deal
	 * 
	 * @return the dealt card or null if the deck is empty and the game is reset
	 */
	public Card dealCard() {
	    if (this.isDeckEmpty()) {
	        System.out.println("Deck is empty. Resetting game.");
	        this.getResolution();
	        return null;
	    }
	    return this.deck.deal();
	}

	/**
	 * Getter for startingCard
	 * 
	 * @return the startingCard
	 */
	public Card getStartingCard() {
		return this.tableCard;
	}

	/**
	 * Getter the humanPlayer
	 * 
	 * @return the humanPlayer
	 */
	public HumanPlayer getHumanPlayer() {
		return this.humanPlayer;
	}

	/**
	 * Getter the computerPlayer
	 * 
	 * @return the computerPlayer
	 */
	public ComputerPlayer getComputerPlayer() {
		return this.computerPlayer;
	}

	/**
	 * Setter for startingCard
	 * 
	 * @param card card from players hand
	 * @return new starting card
	 */
	public Card setStartingCard(Card card) {
		this.tableCard = card;
		return this.tableCard;
	}

	/**
	 * Setter for the initial starting card
	 */
	public void setInitialStartingCard() {
		Card card = this.dealCard();
		this.tableCard = card;
	}

	/**
	 * Resetting the game
	 */
	public void resetGame() {
		this.deck = new Deck();
		this.initializeGame();
	}

	/**
	 * Checks if the deck is empty
	 * 
	 * @return true if the deck is empty
	 */
	public boolean isDeckEmpty() {
		return this.deck.deckCount() == 0;
	}

	/**
	 * Gets the number of cards left in the deck (yet to be played)
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return number of cards left in deck
	 */
	public int getDeckSize() {
		return this.deck.deckCount();
	}
}