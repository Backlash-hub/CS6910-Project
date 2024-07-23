package edu.westga.cs6910.crazy8s.viewmodel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import edu.westga.cs6910.crazy8s.game.Game;
import edu.westga.cs6910.crazy8s.model.Card;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * The Class CasinoViewModel.
 * 
 * @author William Pevytoe
 * @version Summer 2024
 */
public class ViewModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Game game;

	private ObservableList<Card> playerHand;
	private ObjectProperty<Card> selectedCard;
	private ObjectProperty<Card> topCard;

	private IntegerProperty gamesPlayedProperty;
	private IntegerProperty humanWinsProperty;
	private IntegerProperty computerWinsProperty;
	private IntegerProperty deckSizeProperty;
	private IntegerProperty tiedGamesProperty;

	/**
	 * Creates a new ViewModel instance for the specified game. Initializes
	 */
	public ViewModel() {

		this.game = new Game("Joe", "ComputerBob");

		this.playerHand = FXCollections.observableArrayList();

		this.selectedCard = new SimpleObjectProperty<Card>();
		this.topCard = new SimpleObjectProperty<Card>();
		this.gamesPlayedProperty = new SimpleIntegerProperty();
		this.humanWinsProperty = new SimpleIntegerProperty();
		this.computerWinsProperty = new SimpleIntegerProperty();
		this.deckSizeProperty = new SimpleIntegerProperty();
		this.tiedGamesProperty = new SimpleIntegerProperty();

		this.game.initializeGame();

		this.playerHand.addListener((ListChangeListener<Card>) change -> {
			while (change.next()) {
				if (change.wasAdded()) {
					System.out.println("Change detected");
				}
				if (change.wasRemoved()) {
					this.handleWinCondition();
				}
			}
		});

	}

	/**
	 * Starts the game by initializing the game state. Called once at the beginning
	 * of the game.
	 */
	public void startGame() {
		this.game.getStartingCard();
		this.updatePlayerHand();
		this.updateTopCard();
	}

	/**
	 * Plays a round of the game
	 * 
	 * @param selectedCardIndex selected card index in the array
	 */
	public void playGameRound(int selectedCardIndex) {
		int index = this.game.selectedCard(this.game.getHumanPlayer(), selectedCardIndex);
		this.game.playRound(index);
		this.updatePlayerHand();
		this.updateTopCard();
		this.updateGameStats();
	}

	/**
	 * Draws a card from the deck and adds it to the player's hand. Updates the
	 * player's hand in the view model.
	 */
	public void drawCard() {
		if (this.game.isDeckEmpty()) {
			System.out.println("Deck is empty. Tied Game! Resetting");
			this.game.getResolution();
			this.updateGameStats();
			this.updatePlayerHand();
			this.updateTopCard();
		} else {
			Card newCard = this.game.dealCard();
			this.game.getHumanPlayer().addCard(newCard);
			this.updatePlayerHand();
		}
	}

	/**
	 * Retrieves the observable list property of the player's hand. Allows external
	 * classes to observe changes to the player's hand.
	 * 
	 * @return The observable list property of the player's hand
	 */
	public ObservableList<Card> playerHandProperty() {
		return this.playerHand;
	}

	/**
	 * Retrieves the object property of the selected card. Allows external classes
	 * to observe changes to the selected card.
	 * 
	 * @return The object property of the selected card
	 */
	public ObjectProperty<Card> selectedCardProperty() {
		return this.selectedCard;
	}

	/**
	 * Retrieves the object property of the top card (starting card). Allows
	 * external classes to observe changes to the top card.
	 * 
	 * @return The object property of the top card
	 */
	public ObjectProperty<Card> topCardProperty() {
		return this.topCard;
	}

	/**
	 * Updates the player's hand based on the current state of the game. Called
	 * after changes to the player's hand in the game model.
	 */
	public void updatePlayerHand() {
		this.playerHand.setAll(this.game.getHumanPlayer().getHand());
		this.deckSizeProperty.set(this.game.getDeckSize());
	}

	/**
	 * Updates the top card (starting card) based on the current state of the game.
	 * Called after changes to the starting card in the game model.
	 */
	private void updateTopCard() {
		this.topCard.set(this.game.getStartingCard());
	}

	/**
	 * Retrieves the Game instance managed by this view model. Allows external
	 * classes to interact directly with the game model.
	 * 
	 * @return The Game instance managed by this view model
	 */
	public Game getGame() {
		return this.game;
	}

	/**
	 * Retrieves the integer property of the games played. Allows external classes
	 * to observe changes to the games played.
	 * 
	 * @return The integer property of the games played
	 */
	public IntegerProperty gamesPlayedProperty() {
		return this.gamesPlayedProperty;
	}

	/**
	 * Retrieves the integer property of the human player's wins. Allows external
	 * classes to observe changes to the human player's wins.
	 * 
	 * @return The integer property of the human player's wins
	 */
	public IntegerProperty humanWinsProperty() {
		return this.humanWinsProperty;
	}
	
	/**
	 * Retrieves the integer property of the games tied. Allow external classes to 
	 * observe changes to the tied games.
	 * 
	 * @return the integer property of the games tied.
	 */
	public IntegerProperty tiedGamesProperty() {
		return this.tiedGamesProperty;
	}

	/**
	 * Retrieves the integer property of the computer player's wins. Allows external
	 * classes to observe changes to the computer player's wins.
	 * 
	 * @return The integer property of the computer player's wins
	 */
	public IntegerProperty computerWinsProperty() {
		return this.computerWinsProperty;
	}

	/**
	 * Retrieves the integer property of the deck size.
	 * 
	 * @return The integer property of the deck size
	 */
	public IntegerProperty deckSizeProperty() {
		return this.deckSizeProperty;
	}

	/**
	 * Handles the win condition when a player's hand becomes empty. Delegates to
	 * the Game's getResolution() method for actual game state change.
	 */
	private void handleWinCondition() {
		if (this.playerHand.isEmpty()) {
			this.game.getResolution();
			this.updateGameStats();
			this.updatePlayerHand();
			this.updateTopCard();
		}
	}

	private void updateGameStats() {
		this.gamesPlayedProperty.set(this.game.getGamesPlayed());
		this.humanWinsProperty.set(this.game.getHumanWins());
		this.computerWinsProperty.set(this.game.getComputerWins());
		this.tiedGamesProperty.set(this.game.getTiedGames());
	}

	/**
	 * Saves the current game state to a file using serialization.
	 */
	public void saveGame() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Game State");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Game State Files", "*.gamestate"));
		Stage stage = new Stage();
		java.io.File file = fileChooser.showSaveDialog(stage);
		if (file != null) {
			try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
				outputStream.writeObject(this.game);
				System.out.println("Game state saved successfully.");
			} catch (IOException ioe) {
				ioe.printStackTrace();
				System.out.println("Failed to save game state.");
			}
		}
	}

	/**
	 * Loads a previously saved game state from a file using deserialization.
	 */
	public void loadGame() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Load Game State");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Game State Files", "*.gamestate"));
		Stage stage = new Stage();
		java.io.File file = fileChooser.showOpenDialog(stage);
		if (file != null) {
			try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
				this.game = (Game) inputStream.readObject();
				this.updateViewModelFromGame();
				System.out.println("Game state loaded successfully.");
			} catch (IOException | ClassNotFoundException cnfe) {
				cnfe.printStackTrace();
				System.out.println("Failed to load game state.");
			}
		}
	}

	/**
	 * Updates ViewModel properties based on the loaded game state. This method
	 * should be called after loading a game state to sync ViewModel with Game.
	 */
	private void updateViewModelFromGame() {
		this.updateGameStats();
		this.updatePlayerHand();
		this.updateTopCard();
	}
	
	/**
	 * Creates a help dialog box for the user to see how to play the game
	 */
	public void showHelpBox() {
		Alert helpAlert = new Alert(AlertType.INFORMATION);
		helpAlert.setTitle("HELP");
		helpAlert.setHeaderText("How to Play Crazy 8s");
		helpAlert.setContentText("The dealer is to shuffle the deck and then deal each player 5 cards to each player, face down. Each player is allowed"
				+ " to look at their cards but not reveil them to the other players. The remaining cards are stacked in the middle"
				+ " of the players. The deal will then flip over the first card on the remaining cards and set it faceup."
				+ " This card is the starting point for the players. The person to the left of the deal goes first. There are only 3 ways a player can discard a card from their hand and place it on"
				+ " top of the stockpile:"
				+ "	\n"
				+ " 	- They have a card that matches the value/rank of the showing card\n"
				+ " 	- They have a card that matches the suit of the showing card\n"
				+ " 	- They have an 8 (wild card). A wild card and be placed on the top of any showing card\n"
				+ " \n"
				+ " If the player is unable to meet any of the conditions above they must draw from the remaining cards:"
				+ " \n"
				+ " 	- If the card they draw is playable, they can place it on the discard pile\n"
				+ " 	- If not, they must draw from the stockpile until they are able to play a card\n"
				+ "\n"
				+ "If there are no more remaining cards, the game is a tie and the game is restarted.\n"
				+ "\n"
				+ "The play continues until a player is able to discard all their cards from their hand. That player is the WINNER!!!"
				+ "");
		DialogPane dialogPane = helpAlert.getDialogPane();
		dialogPane.setPrefSize(600, 450);
		helpAlert.showAndWait();
	}
	
	/**
	 * Creates an about dialog box that displays development information
	 */
	public void showAboutBox() {
		Alert aboutAlert = new Alert(AlertType.INFORMATION);
		aboutAlert.setTitle("ABOUT");
		aboutAlert.setHeaderText("Crazy 8s Game");
		aboutAlert.setContentText("William Pevytoe\n" +  "7/9/2024");
		aboutAlert.showAndWait();
	}

}