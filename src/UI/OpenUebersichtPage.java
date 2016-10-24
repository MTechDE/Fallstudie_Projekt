package ui;

import java.util.Optional;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import projektDaten.Projekt;

public class OpenUebersichtPage extends Stage {

	private Stage stage;
	public static Projekt tmpProjekt;

	public OpenUebersichtPage(Projekt projekt) {

		OpenUebersichtPage.tmpProjekt = projekt;
		Main.projekt = projekt;

		try {
			stage = this;
			Parent root = FXMLLoader.load(getClass().getResource("UebersichtFenster.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle(projekt.getName() + " - Übersicht");
			stage.getIcons().add(new Image(OpenMainPage.class.getResourceAsStream("VanillaSky.png")));
			stage.setResizable(false);
			stage.show();
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(final WindowEvent event) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setContentText("Möchten Sie Änderungen speichern?");

					ButtonType buttonTypeOne = new ButtonType("Speichern & Verlassen");
					ButtonType buttonTypeTwo = new ButtonType("Ohne speichern verlassen");
					ButtonType buttonTypeCancel = new ButtonType("Abbrechen", ButtonData.CANCEL_CLOSE);

					alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

					Optional<ButtonType> result = alert.showAndWait();

					if (result.get() == buttonTypeOne)
						System.out.println("Speichere projektDaten in der Übersicht");
					if (result.get() == buttonTypeCancel)
						event.consume();
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Fehler aufgetreten!");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Bitte straten Sie die Anwendung neu.");
			alert.showAndWait();
		}
	}
}
