package edu.westga.cs6910.crazy8s.view;

import edu.westga.cs6910.crazy8s.model.Card;
import edu.westga.cs6910.crazy8s.viewmodel.ViewCard;
import edu.westga.cs6910.crazy8s.viewmodel.ViewModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * The controller for the Crazy8s Casino GUI. Manages interactions between the
 * view and the view model.
 * 
 * @author William Pevytoe
 * 
 * @version Summer 2024
 */
public class CasinoCodeBehind {
	private static final Color CASINO_TABLE_BACKGROUND = Color.GREEN;

	@FXML
	private HBox playerBox;
	@FXML
	private Button playCardButton;
	@FXML
	private Button drawACard;
	@FXML
	private Button playAgain;
	@FXML
	private AnchorPane pane;
	@FXML
	private VBox stockpileBox;
	@FXML
	private Label gamesPlayedLabel;
	@FXML
	private Label humanWinsLabel;
	@FXML
	private Label computerWinsLabel;
	@FXML
	private Label tiedGamesLabel;
	@FXML
	private Label deckSizeLabel;
	@FXML
	private MenuItem saveMenuItem;
	@FXML
	private MenuItem loadMenuItem;
	@FXML
	private MenuItem aboutMenuItem;
	@FXML
	private MenuItem helpMenuItem;

	private ViewModel viewModel;
	private SimpleObjectProperty<Card> selectedCardObjectProperty;
	private ViewCard viewCard;

	/**
	 * Instantiates a new crazy8s.
	 */
	public CasinoCodeBehind() {
		this.viewModel = new ViewModel();
		this.selectedCardObjectProperty = new SimpleObjectProperty<>();
		this.selectedCardObjectProperty.bindBidirectional(this.viewModel.selectedCardProperty());
	}

	/**
	 * Initializes the GUI components, binding them to the view model properties.
	 */
	@FXML
	public void initialize() {
		this.viewCard = new ViewCard();
		this.initializeUI();
		this.bindToViewModel();
		this.setUpEventHandlers();
		this.setUpListeners();
		this.viewModel.startGame();
		this.setUpMenu();
	}

	private void initializeUI() {
		BackgroundFill fill = new BackgroundFill(CASINO_TABLE_BACKGROUND, CornerRadii.EMPTY, Insets.EMPTY);
		Background background = new Background(fill);
		this.pane.setBackground(background);
		this.updateTopCard();
	}

	private void bindToViewModel() {
		this.viewModel.playerHandProperty().addListener((ListChangeListener<? super Card>) change -> {
			this.updatePlayerHand();
		});

		this.viewModel.selectedCardProperty().bindBidirectional(this.selectedCardObjectProperty);

		this.gamesPlayedLabel.textProperty().bind(this.viewModel.gamesPlayedProperty().asString());
		this.humanWinsLabel.textProperty().bind(this.viewModel.humanWinsProperty().asString());
		this.computerWinsLabel.textProperty().bind(this.viewModel.computerWinsProperty().asString());
		this.tiedGamesLabel.textProperty().bind(this.viewModel.tiedGamesProperty().asString());
		this.deckSizeLabel.textProperty().bind(this.viewModel.deckSizeProperty().asString());

		this.viewModel.topCardProperty().addListener((observable, oldValue, newValue) -> {
			this.updateTopCard();
		});
	}

	private void setUpEventHandlers() {
		this.playCardButton.setOnAction(event -> this.handlePlayCard());
		this.drawACard.setOnAction(event -> {
			this.viewModel.drawCard();
			this.updatePlayerHand();
		});
		this.playAgain.setOnAction(event -> {
			this.viewModel.getGame().resetGame();
			this.viewModel.startGame();
			this.updatePlayerHand();
			this.updateTopCard();
		});
	}

	private void handlePlayCard() {
		Card selectedCard = this.selectedCardObjectProperty.get();
		if (selectedCard == null) {
			return;
		}
		ObservableList<Card> playerHand = this.viewModel.playerHandProperty();
		int selectedIndex = playerHand.indexOf(selectedCard);
		if (selectedIndex == -1) {
			System.out.println("Selected card not found in player hand.");
			return;
		}
		this.viewModel.playGameRound(selectedIndex);
	}

	private void setUpListeners() {
		this.selectedCardObjectProperty.addListener((observable, oldValue, newValue) -> {
			if (newValue != null && newValue != oldValue) {
				System.out.println("User picked card: " + newValue);
				this.selectedCardObjectProperty.set(newValue);
				this.refreshPlayerBox();
			}
		});
	}

	private void updatePlayerHand() {
		this.playerBox.getChildren().clear();
		ObservableList<Card> playerHand = this.viewModel.playerHandProperty();
		for (Card card : playerHand) {
			Node cardNode = this.viewCard.faceUp(card);
			cardNode.setOnMouseClicked(event -> {
				this.selectedCardObjectProperty.set(card);
			});
			this.playerBox.getChildren().add(cardNode);
		}
	}

	private void updateTopCard() {
		this.stockpileBox.getChildren().clear();
		Card topCard = this.viewModel.topCardProperty().get();
		if (topCard != null) {
			Node cardNode = this.viewCard.faceUp(topCard);
			this.stockpileBox.getChildren().add(cardNode);
		}
	}

	private void refreshPlayerBox() {
		this.playerBox.getChildren().clear();
		ObservableList<Card> playerHand = this.viewModel.playerHandProperty();
		for (Card card : playerHand) {
			Node cardNode;
			if (card == this.selectedCardObjectProperty.get()) {
				cardNode = this.viewCard.faceDown(card);
			} else {
				cardNode = this.viewCard.faceUp(card);
				cardNode.setOnMouseClicked(event -> {
					this.selectedCardObjectProperty.set(card);
				});
			}
			this.playerBox.getChildren().add(cardNode);
		}
	}

	private void setUpMenu() {
		this.saveMenuItem.setOnAction(event -> this.viewModel.saveGame());
		this.loadMenuItem.setOnAction(event -> this.viewModel.loadGame());
		this.helpMenuItem.setOnAction(event -> this.viewModel.showHelpBox());
		this.aboutMenuItem.setOnAction(event -> this.viewModel.showAboutBox());
	}
}
