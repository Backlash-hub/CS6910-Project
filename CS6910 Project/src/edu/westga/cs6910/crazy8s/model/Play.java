package edu.westga.cs6910.crazy8s.model;

/**
 * Behaviors that a player must implement
 * 
 * @author William Pevytoe
 * @version 6/17/2024
 */
public interface Play {

	// NO NEED FOR THIS
	// /**
	// * Draws card from deck and adds it to players hand
	// *
	// * @param deck is the deck object/deck of cards to play from
	// */
	// void drawCard();

	/**
	 * Plays a card from the player's hand
	 * 
	 * @param index is the index within the array you want to remove
	 * 
	 * @return the card played by the player
	 */
	Card playCard(int index);

	/**
	 * Play card from computer's hand
	 * 
	 * @return card played by computer
	 */
	Card playCard();

	/**<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.Button?>


<Button fx:id="playCardButton" layoutX="78.0" layoutY="299.0" mnemonicParsing="false" prefHeight="59.0" prefWidth="135.0" text="Play Card" xmlns="http://javafx.com/javafx/20.0.1" xmlns:fx="http://javafx.com/fxml/1" />

	 * Get the number of cards in a players hand
	 * 
	 * @return the number of cards in a players hand
	 */
	int getCardCount();

	/**
	 * Players name
	 * 
	 * @return a string the represents the players name
	 */
	String getName();
}
