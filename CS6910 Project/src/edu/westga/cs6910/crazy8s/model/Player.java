package edu.westga.cs6910.crazy8s.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Abstract class Player implements the behavior common with all the players of
 * the game
 * 
 * @author William Pevytoe
 * 
 * @version 6/17/2024
 * 
 */
public abstract class Player implements Play, Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private ArrayList<Card> hand;

	/**
	 * Player constructor
	 * 
	 * @param playerName is a String the represents the players name
	 */
	public Player(String playerName) {
		this.name = playerName;
		this.hand = new ArrayList<Card>();
	}

	/**
	 * Getter method for the hand
	 * 
	 * @return the hand
	 */
	public ArrayList<Card> getHand() {
		return new ArrayList<>(this.hand);
	}

	@Override
	public int getCardCount() {
		return this.hand.size();
	}

	@Override
	public Card playCard(int index) {
		if (index >= 0 && index < this.hand.size()) {
			return this.hand.remove(index);
		}
		return null;
	}

	@Override
	public Card playCard() {
		return null;
	}

	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * Remove card from hand
	 * 
	 * @param index is number of the card to remove
	 * @return the removed card
	 */
	public Card removeCard(int index) {
		if (index >= 0 && index < this.hand.size()) {
			return this.hand.remove(index);
		}
		return null;
	}

	/**
	 * Add a card to the player's hand
	 * 
	 * @param card the card to be added to the hand
	 */
	public void addCard(Card card) {
		if (card != null) {
			this.hand.add(card);
		}
	}

	/**
	 * Print name and cards in had info
	 */
	public void printGameInfo() {
		System.out.println(this.name + " has " + this.hand.size() + " card(s) in their hand.");
	}

	/**
	 * Clears the cards
	 */
	public void clearHand() {
		this.hand.clear();
	}
}
