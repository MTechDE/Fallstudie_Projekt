package UI;

import Datenbank.Datenbank;
import Projekt.Projekt;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class OpenMainPage extends Stage {

	private Stage stage;
	private Datenbank myDB = new Datenbank();
	public static Projekt tmpProjekt;

	public OpenMainPage(Projekt projekt, boolean newProjekt, boolean vorlage) throws Exception {

		tmpProjekt = projekt;

		// Ein neues Projekt soll nicht sofort in der Datenbank gespeichert
		// werden
		// Der Nutzer soll dazu gezwungen werden, mindestens eine Phase und eine
		// Kompetenz anzulegen

		if (newProjekt) {
			if (vorlage) {
				// TODO Eine Vorlage muss noch deffiniert werden
			} else {
				tmpProjekt = projekt;
			}
		} else {
			try {
				tmpProjekt = myDB.getProjekt(projekt);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		try {
			stage = this;
			Parent root = FXMLLoader.load(getClass().getResource("Anlegen.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle(projekt.getName());
			stage.getIcons().add(new Image(OpenMainPage.class.getResourceAsStream("VanillaSky.png")));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Fehler aufgetreten!");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Bitte starten Sie die Anwendung neu.");
			alert.showAndWait();
		}

	}

}
