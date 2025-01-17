package edu.westga.cs6910.crazy8s;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * CasinoApplication extends the JavaFX Application class to build the GUI and
 * start program execution.
 * 
 * @author CS6910
 * @version Summer 2024
 */
public class CasinoApplication extends Application {

	public static final String GUI_FXML = "view/Casino.fxml";
	public static final String APP_NAME = "CS6910 Crazy8s by William Pevytoe";

	/**
	 * Constructs a new Application.
	 * 
	 * @precondition none
	 * @postcondition none
	 */
	public CasinoApplication() {
		super();
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			Pane pane = this.loadGui();
			Scene scene = new Scene(pane);
			primaryStage.setScene(scene);
			primaryStage.setTitle(APP_NAME);
			primaryStage.show();
		} catch (IllegalStateException | IOException anException) {
			anException.printStackTrace();
		}
	}

	private Pane loadGui() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(GUI_FXML));
		return (Pane) loader.load();

	}

	/**
	 * The main method - entry point for the program.
	 * 
	 * @precondition none
	 * @postcondition none
	 * @param args - the command line arguments not used
	 */
	public static void main(String[] args) {
		launch(args);
	}
}