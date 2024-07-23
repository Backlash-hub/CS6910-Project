/**
 * 
 */
package edu.westga.cs6910.crazy8s.test.game;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs6910.crazy8s.game.Game;
import edu.westga.cs6910.crazy8s.model.Card;

/**
 * Testing the constructor and initializing 
 * 
 * @author William Pevytoe
 * 
 * @version 6/19/2024
 * 
 */
public class TestGame {
    private Game game;

    /**
     * Preconditions for all tests
     */
    @BeforeEach
    public void setUp() {
        this.game = new Game("Alice", "BobComputer");
    }

    /**
     * Testing playing a round of the game
     */
    @Test
    public void testPlayRound() {
        this.game.initializeGame();
        this.game.playRound(0);
        Card newStartingCard = this.game.getStartingCard();
        assertNotNull(newStartingCard);
        assertTrue(this.game.isCardPlayable(newStartingCard));
    }
}
