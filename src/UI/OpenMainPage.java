package ui;

import java.util.Optional;

import controller.MainViewController;
import datenbank.Datenbank;
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

/**
 * 
 * @author Daniel Sogl
 *
 */
public class OpenMainPage extends Stage {

	private Stage stage;
	private Datenbank myDB = new Datenbank();
	public static Projekt tmpProjekt;

	public OpenMainPage(Projekt projekt, boolean newProjekt) throws Exception {

		tmpProjekt = projekt;

		if (newProjekt) {
			tmpProjekt = projekt;

		} else {
			try {
				tmpProjekt = myDB.getProjekt(projekt);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		try {
			stage = this;
			Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle(projekt.getName());
			stage.getIcons().add(new Image(OpenMainPage.class.getResourceAsStream("VanillaSky.png")));
			stage.setResizable(false);
			stage.show();

			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(final WindowEvent event) {

					if (MainViewController.somethingChanged) {
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setContentText("Möchten Sie Änderungen speichern?");

						ButtonType buttonTypeOne = new ButtonType("Speichern & Verlassen");
						ButtonType buttonTypeTwo = new ButtonType("Ohne speichern verlassen");
						ButtonType buttonTypeCancel = new ButtonType("Abbrechen", ButtonData.CANCEL_CLOSE);

						alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

						Optional<ButtonType> result = alert.showAndWait();

						if (result.get() == buttonTypeOne)
							MainViewController.saveProjektRemote();
						if(result.get() == buttonTypeCancel)
							event.consume();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Bitte straten Sie die Anwendung neu.");
			alert.showAndWait();
		}

	}

	public OpenMainPage(Projekt projekt) {
		try {
			OpenMainPage.tmpProjekt = projekt;
			stage = this;
			Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));
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
