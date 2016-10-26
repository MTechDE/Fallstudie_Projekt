package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import projektDaten.Kompetenz;

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

	@FXML
	private void initialize() {
		txt_kompetenz_aendern.setText(kompetenz.getName());
		txt_risikozuschlag_aendern.setText(String.valueOf(kompetenz.getRisikozuschlag()));
	}

	@FXML
	private void btn_aendern_click(ActionEvent event) throws Exception {
		kompetenz.setName(txt_kompetenz_aendern.getText());
		kompetenz.setRisikozuschlag(Double.parseDouble(txt_risikozuschlag_aendern.getText()));
		MainViewController mainViewController = new MainViewController();
		mainViewController.updateTbl_kompetenz();
		btn_abbrechen_click(event);
	}

	@FXML
	private void btn_abbrechen_click(ActionEvent event) throws Exception {
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}

	/**
	 * Controller wird zu änderndes Kompetenzobjekt direkt zugewiesen
	 * 
	 * @param k
	 */
	public static void setKompetenz(Kompetenz k) {
		kompetenz = k;
	}
}