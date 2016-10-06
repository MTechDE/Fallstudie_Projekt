package UI;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Die Main-Klasse lädt lediglich die MainUI.fxml Datei und stellt diese dar.
 * Jegliche Logik wird in der MainUICtrl Klasse behandelt.
 * @author Daniel Sogl
 *
 */

public class Main extends Application {

	// TODO Programm Namen Eintragen
	@Override
	public void start(Stage primaryStage) throws Exception {
		try{
			Parent root = FXMLLoader.load(getClass().getResource("MainUI.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("Vanilla Sky");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e){
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
