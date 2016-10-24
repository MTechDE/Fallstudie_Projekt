package ui;

import java.util.List;

import datenbank.Datenbank;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import projektDaten.Projekt;

/**
 * Die Main-Klasse lädt lediglich die MainUI.fxml Datei und stellt diese dar.
 * 
 * @author Daniel Sogl
 *
 */

public class Main extends Application {

	public static List<Projekt> projekte;
	private Datenbank db;

	@Override
	public void start(Stage primaryStage) throws Exception {
		db = new Datenbank();
		if (db.testConnection())
			projekte = db.getProjekte();

		try {
			Parent root = FXMLLoader.load(getClass().getResource("StartView.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("Vanilla Sky");
			primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("VanillaSky.png")));
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
