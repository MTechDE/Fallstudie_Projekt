package UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Die Main-Klasse lädt lediglich die MainUI.fxml Datei und stellt diese dar.
 * Jegliche Logik wird in der MainUICtrl Klasse behandelt.
 * @author Daniel Sogl
 *
 */

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		try{
			Parent root = FXMLLoader.load(getClass().getResource("StartFenster.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("modena.css").toExternalForm());
			primaryStage.setTitle("Vanilla Sky");
			primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("VanillaSky.png")));
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
