package UI;

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

}
