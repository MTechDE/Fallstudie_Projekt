package controller;

import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Phase;

/**
 * Viewcontroller für die Phase ändern View
 * 
 * @author Tim Krießler, Daniel Sogl
 *
 */
public class ChangePhaseViewController {

	@FXML
	public Button btn_aendern;
	@FXML
	public Button btn_abbrechen;
	@FXML
	public TextField txt_phase_aendern;
	@FXML
	public DatePicker dtpkr_startdatum_aendern;
	@FXML
	public DatePicker dtpkr_enddatum_aendern;

	private static Phase phase;

	/**
	 * Initialisiert die View
	 */
	@FXML
	private void initialize() {
		txt_phase_aendern.setText(phase.getName());
		dtpkr_startdatum_aendern.setValue(LocalDate.parse(phase.getStartDate()));
		dtpkr_enddatum_aendern.setValue(LocalDate.parse(phase.getEndDate()));
	}

	/**
	 * Actionlistener für den Ändern-Button
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	private void btn_aendern_click(ActionEvent event) throws Exception {
		
		// Überprüfe ob alle Felder gefüllt sind
		if (txt_phase_aendern.getText().trim().isEmpty() || dtpkr_startdatum_aendern.getValue() == null
				|| dtpkr_enddatum_aendern.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			if(txt_phase_aendern.getText().trim().isEmpty())
				alert.setContentText("Bitte geben Sie einen Phasennamen an!");
			else if(dtpkr_startdatum_aendern.getValue() == null)
				alert.setContentText("Bitte geben Sie eine Startdatum an!");
			else if(dtpkr_enddatum_aendern.getValue() == null)
				alert.setContentText("Bitte geben Sie eine Enddatum an!");
			alert.showAndWait();
			// Überprüfe ob der  Namen bereits verwendet wurde
		} else if (!MainViewController.projekt.getPhasen().stream()
				.anyMatch(obj -> obj.getName().equals(txt_phase_aendern.getText()))
				|| txt_phase_aendern.getText().equals(phase.getName())) {
			phase.setName(txt_phase_aendern.getText());
			phase.setStartDate(dtpkr_startdatum_aendern.getValue().toString());
			phase.setEndDate(dtpkr_enddatum_aendern.getValue().toString());
			MainViewController.somethingChanged = true;
			btn_abbrechen_click(event);
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Der angegebene Phasenname ist bereits vorhanden!");
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
	 * Actionlistener für den Endedatum-Datepicker.
	 * Überprüft ob das Enddatum vor dem Startdatum liegt.
	 * 
	 * @param event
	 * @return
	 * @throws Exception
	 */
	@FXML
	public boolean datepicker_ende_selected(ActionEvent event) throws Exception {
		if(dtpkr_startdatum_aendern.getValue() != null){
			int startDatum = Integer.parseInt(dtpkr_startdatum_aendern.getValue().toString().replaceAll("-", ""));
			int endDatum = Integer.parseInt(dtpkr_enddatum_aendern.getValue().toString().replaceAll("-", ""));
			if (endDatum <= startDatum) {
				dtpkr_enddatum_aendern.setValue(dtpkr_startdatum_aendern.getValue().plusDays(1));
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Das Enddatum darf nicht gleich wie das Startdatum sein oder davor liegen.");
				alert.showAndWait();
				return true;
			} else
				return false;
		} else
			return false;
	}

	/**
	 * Controller wird dem zu änderten Phasenobjekt direkt zugewiesen.
	 * 
	 * @param p
	 */
	public static void setPhase(Phase p) {
		phase = p;
	}

}