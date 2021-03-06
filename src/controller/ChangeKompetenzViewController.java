package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Kompetenz;

/**
 * Viewcontroller für die Kompetenz ändern View
 * 
 * @author Tim Krießler, Daniel Sogl
 *
 */
public class ChangeKompetenzViewController {

	@FXML
	public Button btn_abbrechen;
	@FXML
	public Button btn_aendern;
	@FXML
	public TextField txt_kompetenz_aendern;
	@FXML
	public TextField txt_risikozuschlag_aendern;

	private static Kompetenz kompetenz;

	/**
	 * Initialisiert die View
	 */
	@FXML
	private void initialize() {
		txt_kompetenz_aendern.setText(kompetenz.getName());
		txt_risikozuschlag_aendern.setText(String.valueOf(kompetenz.getRisikozuschlag()).replace(".", ","));
	}

	/**
	 * Actionlistener für den Ändern-Button
	 * 
	 * @param event das Clickevent
	 * @throws Exception
	 */
	@FXML
	private void btn_aendern_click(ActionEvent event) throws Exception {

		// Überprüfe ob alle Felder ausgefüllt wurden
		if (txt_kompetenz_aendern.getText().trim().isEmpty() || txt_risikozuschlag_aendern.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			if(txt_kompetenz_aendern.getText().trim().isEmpty())
				alert.setContentText("Bitte geben Sie einen Kompetenznamen an!");
			else if (txt_risikozuschlag_aendern.getText().trim().isEmpty())
				alert.setContentText("Bitte geben Sie einen Risikozuschlag an!");
			alert.showAndWait();
			// Überprüfe ob der Name bereits verwendet wurde
		} else if (!MainViewController.projekt.getKompetenzen().stream()
				.anyMatch(obj -> obj.getName().equals(txt_kompetenz_aendern.getText())) || kompetenz.getName().equals(txt_kompetenz_aendern.getText())) {
			
			// Ändere den Namen in den Aufwänden, falls sich der Name geändert hat
			if(!kompetenz.getName().equals(txt_kompetenz_aendern.getText())){
				for(int p = 0; p < MainViewController.projekt.getPhasen().size(); p++){
					for(int a = 0; a < MainViewController.projekt.getPhasen().get(p).getAufwände().size(); a++){
						if(MainViewController.projekt.getPhasen().get(p).getAufwände().get(a).getZugehoerigkeit().equals(kompetenz.getName()))
							MainViewController.projekt.getPhasen().get(p).getAufwände().get(a).setZugehoerigkeit(txt_kompetenz_aendern.getText());
					}
				}
			}
			
			// Setzt den neuen Namen
			kompetenz.setName(txt_kompetenz_aendern.getText());
			kompetenz.setRisikozuschlag(Double.parseDouble(txt_risikozuschlag_aendern.getText().replace(",", ".")));
			MainViewController.somethingChanged = true;
			btn_abbrechen_click(event);
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Der angegebene Kompetenzname ist bereits vorhanden!");
			alert.showAndWait();
		}
	}

	/**
	 * Actionlistener für den Abbrechen-Button.
	 * Schließt die Ändern View
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	private void btn_abbrechen_click(ActionEvent event) throws Exception {
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}

	/**
	 * Controller wird zu änderndes Kompetenzobjekt direkt zugewiesen
	 * 
	 * @param k Setzt Kompetenz
	 */
	public static void setKompetenz(Kompetenz k) {
		kompetenz = k;
	}
}