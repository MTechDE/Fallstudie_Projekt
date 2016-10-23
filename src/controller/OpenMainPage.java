package controller;

import java.util.Optional;
import Datenbank.Datenbank;
import Projekt.Projekt;
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

public class OpenMainPage extends Stage {

	private Stage stage;
	private Datenbank myDB = new Datenbank();
	public static Projekt tmpProjekt;

	public OpenMainPage(Projekt projekt, boolean newProjekt) throws Exception {

		tmpProjekt = projekt;

		if (newProjekt) {
			tmpProjekt = projekt;
		} else {
			tmpProjekt = myDB.getProjekt(projekt);
		}

		try {
			stage = this;
			Parent root = FXMLLoader.load(getClass().getResource("../view/Anlegen.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle(projekt.getName());
			stage.getIcons().add(new Image(getClass().getResourceAsStream("../img/VanillaSky.png")));
			stage.setResizable(false);
			stage.show();

			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(final WindowEvent event) {

					if (AnlegenController.somethingChanged) {
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setContentText("Möchten Sie Änderungen speichern?");

						ButtonType buttonTypeOne = new ButtonType("Speichern & Verlassen");
						ButtonType buttonTypeTwo = new ButtonType("Ohne speichern verlassen");
						ButtonType buttonTypeCancel = new ButtonType("Abbrechen", ButtonData.CANCEL_CLOSE);

						alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

						Optional<ButtonType> result = alert.showAndWait();

						if (result.get() == buttonTypeOne)
							AnlegenController.saveProjektRemote();
						if (result.get() == buttonTypeCancel)
							event.consume();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}

	}

	public OpenMainPage(Projekt projekt) {
		try {
			OpenMainPage.tmpProjekt = projekt;
			stage = this;
			Parent root = FXMLLoader.load(getClass().getResource("Anlegen.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle(tmpProjekt.getName());
			stage.getIcons().add(new Image(OpenMainPage.class.getResourceAsStream("VanillaSky.png")));
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Bitte straten Sie die Anwendung neu.");
			alert.showAndWait();
		}
	}
}
