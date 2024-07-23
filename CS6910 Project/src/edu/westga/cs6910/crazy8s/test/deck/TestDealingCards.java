package edu.westga.cs6910.crazy8s.test.deck;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.westga.cs6910.crazy8s.model.Deck;

/**
 * Testing dealing the deck
 * 
 * @author William Pevytoe
 * 
 * @version 6/11/2024
 */
class TestDealingCards {

	/**
	 * Testing dealing 1 card
	 */
	@Test
	public void testDealing1Card() {
		Deck deck = new Deck();
		deck.deal();
		assertEquals(51, deck.deckCount());
	}
	
	/**
	 * Testing dealing 10 cards (5 per player)
	 */
	@Test
	public void testDealing10Cards() {
		Deck deck = new Deck();
		int numberOfPlayers = 2;
		int startingCards = 5;
		for (int position = 0; position < (numberOfPlayers * startingCards); position++ )
			deck.deal();
		assertEquals(42, deck.deckCount());
	}
}
