package edu.westga.cs6910.crazy8s.model;

/**
 * The enum Suit.
 * 
 * @author CS6910
 * @version Summer 2024
 */
public enum Suit {
    SPADES("Spades"),
    DIAMONDS("Diamonds"),
    HEARTS("Hearts"),
    CLUBS("Clubs");

    private final String suitName;

    /**
     * Constructs a Suit enum with a specified name.
     * 
     * @param suitName the name of the suit
     */
    Suit(String suitName) {
        this.suitName = suitName;
    }

    /**
     * Returns the name of the suit.
     * 
     * @return the name of the suit
     */
    public String getSuitName() {
        return this.suitName;
    }
}
