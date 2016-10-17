package UI;

import Projekt.Projekt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class UebersichtFensterController extends Stage {
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
	private Stage stage;

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

	}

	public void btn_zurueck_click(ActionEvent event) throws Exception {

		try {
			stage = this;
			Parent root = FXMLLoader.load(getClass().getResource("StartFenster.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Vanilla Sky");
			stage.getIcons().add(new Image(OpenMainPage.class.getResourceAsStream("VanillaSky.png")));
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Fehler aufgetreten!");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Bitte starten Sie die Anwendung neu.");
			alert.showAndWait();

		}

	}
}