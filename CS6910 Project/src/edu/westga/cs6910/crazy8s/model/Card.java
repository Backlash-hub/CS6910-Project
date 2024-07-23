package edu.westga.cs6910.crazy8s.model;

import java.io.Serializable;

import edu.westga.cs6910.crazy8s.resources.ExceptionMessages;

/**
 * The Card class.
 * 
 * @author CS6910
 * @version Summer 2024
 */
public class Card implements Serializable {

	private static final long serialVersionUID = 1L;
	private Rank rank;
	private Suit suit;

	/**
	 * Creates a playing card with the specified rank and suit.
	 * 
	 * @precondition valid rank and suit
	 * @postcondition getValue() == value && getSuit() == suit
	 * @param rank the rank of this card
	 * @param suit the suit of this card
	 */
	public Card(Rank rank, Suit suit) {
		if (rank == null) {
			throw new IllegalArgumentException(ExceptionMessages.INVALID_RANK);
		}
		if (suit == null) {
			throw new IllegalArgumentException(ExceptionMessages.INVALID_SUIT);
		}
		this.rank = rank;
		this.suit = suit;
	}

	/**
	 * Returns the rank value of this card.
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return the rank
	 */
	public int getRank() {
		return this.rank.getValue();
	}

	/**
	 * Returns the rank of and card
	 * 
	 * @return the Rank of the card
	 */
	public Rank getRankRank() {
		return this.rank;
	}

	/**
	 * Returns the suit of this card.
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return the suit
	 */
	public Suit getSuit() {
		return this.suit;
	}

	@Override
	public String toString() {
		return this.rank + " of " + this.suit;
	}
}
