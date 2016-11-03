package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Läd die Start View
 * 
 * @author Daniel Sogl
 *
 */

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			// Lade die FXML Datei
			Parent root = FXMLLoader.load(getClass().getResource("StartView.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("Vanilla Sky");
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("VanillaSky.png")));
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
