package UI;

import Export.Excel;
import Projekt.Projekt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;

public class UebersichtFensterController {
	@FXML
	private Tab tab_Projekt;
	@FXML
	private TableColumn table_p1;
	@FXML
	private TableColumn table_P2;
	@FXML
	private Tab tab_Quartal;
	@FXML
	private TableColumn table_Q1;
	@FXML
	private TableColumn table_Q2;
	@FXML
	private Tab tab_Jahr;
	@FXML
	private TableColumn table_J1;
	@FXML
	private TableColumn table_J2;
	@FXML
	private CheckBox ckb_risiko;
	@FXML
	private Button btn_Export;
	@FXML
	private Button btn_zurueck;
	@FXML
	private Label lbl_Name;
	@FXML
	private Label lbl_Ersteller;
	@FXML
	private Label lbl_SDate;
	@FXML
	private Label lbl_EDate;
	@FXML
	private Label lbl_Meldestat;

	static Projekt projekt;

	public void initialize() { // Grundinfos werden im Fenster eingesetzt

		lbl_Name.setText(projekt.getName());
		lbl_Ersteller.setText(projekt.getErsteller());
		lbl_SDate.setText(projekt.getStartDate());
		lbl_EDate.setText(projekt.getEndDate());
		String A;
		if (projekt.isAbgeschickt() == true) {
			A = "Projekt Gemeldet";
		} else {
			A = "Projekt nicht Gemeldet";
		}
		lbl_Meldestat.setText(A);
	}

	public void btn_Export_click(ActionEvent event) throws Exception {

		Excel.ExportToExcel(projekte, path);

	}

	public void btn_zurueck_click(ActionEvent event) throws Exception {

	}

}
