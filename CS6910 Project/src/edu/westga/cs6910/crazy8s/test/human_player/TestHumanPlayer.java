package edu.westga.cs6910.crazy8s.test.human_player;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs6910.crazy8s.model.Card;
import edu.westga.cs6910.crazy8s.model.Deck;
import edu.westga.cs6910.crazy8s.model.HumanPlayer;
import edu.westga.cs6910.crazy8s.model.Rank;
import edu.westga.cs6910.crazy8s.model.Suit;

/**
 * Testing Human Player
 * 
 * @author William Pevytoe
 * 
 * @version 6/21/2024
 */
public class TestHumanPlayer {

    private HumanPlayer player;
    private Deck deck;

    /**
     * Preconditions for all the tests
     */
    @BeforeEach
    public void setUp() {
        this.player = new HumanPlayer("Alice");
        this.deck = new Deck();
    }

    /**
     * Testing adding a card to the players hand
     */
    @Test
    public void testPlayCard_validIndex() {
        Card cardToPlay = this.deck.deal();
        this.player.addCard(cardToPlay);
        int initialHandSize = this.player.getCardCount();
        Card playedCard = this.player.playCard(0);
        assertNotNull(playedCard);
        assertEquals(initialHandSize, this.player.getCardCount());
    }

    /**
     * Test error checking if index doesn't exist
     */
    @Test
    public void testPlayCard_invalidIndex() {
        this.player.addCard(new Card(Rank.KING, Suit.DIAMONDS));
        assertThrows(IndexOutOfBoundsException.class, () -> {
            this.player.playCard(1);
        });
    }
}