package controller;

import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import projektDaten.Phase;

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

	@FXML
	private void initialize() {
		txt_phase_aendern.setText(phase.getName());
		dtpkr_startdatum_aendern.setValue(LocalDate.parse(phase.getStartDate()));
		dtpkr_enddatum_aendern.setValue(LocalDate.parse(phase.getEndDate()));
	}

	@FXML
	private void btn_abbrechen_click(ActionEvent event) throws Exception {
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void btn_aendern_click(ActionEvent event) throws Exception {
		phase.setName(txt_phase_aendern.getText());
		phase.setStartDate(dtpkr_startdatum_aendern.getValue().toString());
		phase.setEndDate(dtpkr_enddatum_aendern.getValue().toString());
		MainViewController mainViewController = new MainViewController();
		mainViewController.updateTbl_phase();
		btn_abbrechen_click(event);
	}

	/**
	 * Controller wird zu änderndes Phasenobjekt direkt zugewiesen.
	 * 
	 * @param p
	 */
	public static void setPhase(Phase p) {
		phase = p;
	}

}