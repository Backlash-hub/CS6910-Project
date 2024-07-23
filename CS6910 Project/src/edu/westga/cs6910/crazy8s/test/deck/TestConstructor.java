package edu.westga.cs6910.crazy8s.test.deck;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs6910.crazy8s.model.Card;
import edu.westga.cs6910.crazy8s.model.Deck;
import edu.westga.cs6910.crazy8s.model.HumanPlayer;

/**
 * Testing the Deck Constructor
 * 
 * @author William Pevytoe
 * 
 * @version 6/11/2024
 */
public class TestConstructor {
	private Deck deck;
	private HumanPlayer humanPlayer;

	/**
	 * Preconditions for every test
	 */
	@BeforeEach
	public void setUp() {
		this.deck = new Deck();
	}

	/**
	 * Testing that when the deck is created that there are 52 cards
	 */
	@Test
	public void testDeckConstructor() {
		assertEquals(52, this.deck.deckCount());
	}

	/**
	 * Testing dealing cards to player
	 */
	@Test
	public void testDealToPlayer() {
		HumanPlayer humanPlayer = new HumanPlayer("TestPlayer");
		this.humanPlayer = humanPlayer;
		Card dealtCard = this.deck.deal();
		this.humanPlayer.addCard(dealtCard);
		assertEquals(51, this.deck.deckCount());
		assertTrue(this.humanPlayer.getHand().contains(dealtCard));
		assertEquals(1, this.humanPlayer.getHand().size());
	}

	/**
	 * Testing trying to deal from an empty deck
	 */
	@Test
	public void testDealFromEmptyDeck() {
		while (this.deck.deckCount() > 0) {
			this.deck.deal();
		}
		assertThrows(IllegalStateException.class, () -> {
			this.deck.deal();
		});
	}

	// /**
	// * Test adding card to the stockpile
	// */
	// @Test
	// public void testAddToStockpile() {
	// Card card = this.deck.deal();
	// this.deck.addToStockpile(card);
	// assertEquals(1, this.deck.getStockpile().size());
	// assertTrue(this.deck.getStockpile().contains(card));
	// }
}
