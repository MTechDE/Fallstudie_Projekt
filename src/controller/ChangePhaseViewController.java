package controller;

import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
<<<<<<< HEAD
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
=======
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
>>>>>>> refs/remotes/origin/master
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
<<<<<<< HEAD
		phase.setName(txt_phase_aendern.getText());
		phase.setStartDate(dtpkr_startdatum_aendern.getValue().toString());
		phase.setEndDate(dtpkr_enddatum_aendern.getValue().toString());
		MainViewController mainViewController = new MainViewController();
		mainViewController.updateTbl_phase();
		btn_abbrechen_click(event);
=======
		// Überprüfe ob der neue Name bereits verwendet wurde
		if(!txt_phase_aendern.getText().equals(phase.getName())){
			boolean check = false;
			for (Phase Phase : MainViewController.projekt.getPhasen()) {
				if(Phase.getName().equals(txt_phase_aendern.getText()))
					check = true;
			}
			if(!check){
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
		} else {
			btn_abbrechen_click(event);
		}
>>>>>>> refs/remotes/origin/master
	}

	/**
	 * Controller wird zu änderndes Phasenobjekt direkt zugewiesen.
	 * 
	 * @param p
	 */
	public static void setPhase(Phase p) {
		phase = p;
	}

<<<<<<< HEAD
}
=======
}
>>>>>>> refs/remotes/origin/master
