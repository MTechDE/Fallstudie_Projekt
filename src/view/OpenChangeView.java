package view;

import configuration.Configuration;
import controller.ChangeKompetenzViewController;
import controller.ChangePhaseViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Kompetenz;
import model.Phase;

/**
 * Öffnet die passende Bearbeitungs View
 * @author Tim Krießler, Daniel Sogl
 *
 */
public class OpenChangeView extends Stage {

	private Stage stage;

	/**
	 * Öffne die View um eine Phase zu bearbeiten
	 * @param phase
	 */
	public OpenChangeView(Phase phase) {
		try {
			ChangePhaseViewController.setPhase(phase);
			stage = this;
			Parent root = FXMLLoader.load(getClass().getResource("ChangePhaseView.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle(phase.getName() + " ändern");
			stage.getIcons().add(new Image(getClass().getResourceAsStream("VanillaSky.png")));
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
		} catch (Exception e) {
			if(Configuration.DEBUG)
				System.out.println(e.getMessage());
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Bitte starten Sie die Anwendung neu.");
			alert.showAndWait();
		}
	}

	/**
	 * Öffnet die View um eine Kompetenz zu bearbeiten
	 * @param kompetenz
	 */
	public OpenChangeView(Kompetenz kompetenz) {
		try {
			ChangeKompetenzViewController.setKompetenz(kompetenz);
			stage = this;
			Parent root = FXMLLoader.load(getClass().getResource("ChangeKompetenzView.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle(kompetenz.getName() + " ändern");
			stage.getIcons().add(new Image(getClass().getResourceAsStream("VanillaSky.png")));
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
		} catch (Exception e) {
			if(Configuration.DEBUG)
				System.out.println(e.getMessage());
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Bitte starten Sie die Anwendung neu.");
			alert.showAndWait();
		}
	}
}