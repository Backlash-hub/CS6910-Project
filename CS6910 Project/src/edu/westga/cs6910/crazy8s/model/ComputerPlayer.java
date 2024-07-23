package edu.westga.cs6910.crazy8s.model;

import java.io.Serializable;
import java.util.ArrayList;

import edu.westga.cs6910.crazy8s.game.Game;

/**
 * ComputerPlayer represents the Computer in the game
 * 
 * @author William Pevytoe
 * 
 * @version 6/18/2024
 */
public class ComputerPlayer extends Player implements Serializable {

	private static final long serialVersionUID = 1L;

	private ArrayList<Card> hand;

	/**
	 * Creates a player by a specific name
	 * 
	 * @param playerName is String representation of the players name
	 * @param game       is the instance of the game
	 */
	public ComputerPlayer(String playerName, Game game) {
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
	 * The cards in the computer players hand
	 * 
	 * @return the cards in the computer players hand
	 */
	public ArrayList<Card> getHand() {
		return super.getHand();
	}

	/**
	 * Returns the number of cards in the computer players hand
	 * 
	 * @return the number of cards in the computer players hand
	 */
	public int getCardCount() {
		return this.hand.size();
	}
}
