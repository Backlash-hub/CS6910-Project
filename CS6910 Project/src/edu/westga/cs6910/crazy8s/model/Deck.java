package edu.westga.cs6910.crazy8s.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The deck class represents a deck of cards
 * 
 * @author William Pevytoe
 * @version 6/10/2024
 */
public class Deck implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<Card> cards;

	/**
	 * Constructor to create a deck of 52 cards
	 * 
	 * @precondition none
	 * @postcondition deck contains 52 unique cards
	 */
	public Deck() {
		this.cards = new ArrayList<Card>();
		
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				this.cards.add(new Card(rank, suit));
			}
		}
	}

	/**
	 * Shuffle the deck of cards
	 */
	public void shuffleDeck() {
		Collections.shuffle(this.cards);
	}

	/**
	 * Deal card to start the game
	 * 
	 * @return the dealt card
	 */
	public Card deal() {
		if (this.cards.isEmpty()) {
			throw new IllegalStateException("Cannot deal from empty deck");
		}

		Card dealtCard = this.cards.remove(this.cards.size() - 1);
		return dealtCard;
	}

	/**
	 * Returns the number of cards in the deck
	 * 
	 * @return number of cards in the deck
	 */
	public int deckCount() {
		return this.cards.size();
	}

	/**
	 * Returns a string value of the cards in the deck
	 * 
	 * @return the string value of the cards in the deck
	 */
	@Override
	public String toString() {
		return this.cards.toString();
	}
}