package edu.westga.cs6910.crazy8s.model;

import java.util.ArrayList;

/**
 * HumanPlayer represents the human in the game
 * 
 * @author William Pevytoe
 * 
 * @version 6/18/2024
 */
public class HumanPlayer extends Player {

	private static final long serialVersionUID = 1L;
	private ArrayList<Card> hand;

	/**
	 * Creates a player by a specific name
	 * 
	 * @param playerName is String representation of the players name
	 */
	public HumanPlayer(String playerName) {
		super(playerName);
		this.hand = new ArrayList<Card>();
	}

	@Override
	public Card playCard(int index) {
		if (index < 0 || index >= super.getHand().size()) {
			throw new IndexOutOfBoundsException(
					"Index " + index + " out of bounds for length " + super.getHand().size());
		}
		return super.playCard(index);
	}

	/**
	 * The cards in the human players hand
	 * 
	 * @return the cards in the human players hand
	 */
	public ArrayList<Card> getHand() {
		return super.getHand();
	}

	/**
	 * Returns the number of cards in the human players hand
	 * 
	 * @return the number of cards in the human players hand
	 */
	public int getCardCount() {
		return this.hand.size();
	}
}
