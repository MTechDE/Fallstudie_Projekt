package UI;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import Datenbank.Datenbank;
import Projekt.Projekt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.CheckBox;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class StartFensterController {
	@FXML
	private AnchorPane startScreen;
	@FXML
	private Button btn_newProjekt;
	@FXML
	private TableView<Projekt> tbl_projektTabelle;
	@FXML
	private TableColumn<Projekt, String> tblCell_projektName;
	@FXML
	private TableColumn<Projekt, String> tblCell_projektErsteller;
	@FXML
	private TableColumn<Projekt, String> tblCell_projektStart;
	@FXML
	private TableColumn<Projekt, String> tblCell_projektEnd;
	@FXML
	private TableColumn<Projekt, Boolean> tblCell_projektSend;
	@FXML
	private TextField txt_newProjekt_name;
	@FXML
	private TextField txt_newProjekt_ersteller;
	@FXML
	private CheckBox check_vorlage;

	Datenbank myDB = new Datenbank();

	// Diese Liste aktualsiert sich automatisch und damit auch die Tabelle
	private ObservableList<Projekt> projektData = FXCollections.observableArrayList();

	// Diese Methode wird autoamtisch beim Starten aufgerufen
	@FXML
	private void initialize() {
		System.out.println("Initalisiere Startfenster");
		// tblCell_projektName.setCellValueFactory(new
		// PropertyValueFactory<Projekt, String>("name"));
		// tblCell_projektErsteller.setCellValueFactory(new
		// PropertyValueFactory<Projekt, String>("ersteller"));
		// tblCell_projektStart.setCellValueFactory(new
		// PropertyValueFactory<Projekt, String>("startDate"));
		// tblCell_projektEnd.setCellValueFactory(new
		// PropertyValueFactory<Projekt, String>("endDate"));
		// tblCell_projektSend.setCellValueFactory(new
		// PropertyValueFactory<Projekt, Boolean>("abgeschickt"));

		tblCell_projektName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		tblCell_projektErsteller.setCellValueFactory(cellData -> cellData.getValue().erstellerProperty());
		tblCell_projektStart.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());
		tblCell_projektEnd.setCellValueFactory(cellData -> cellData.getValue().endDateProperty());
		tblCell_projektSend.setCellValueFactory(cellData -> cellData.getValue().abgeschicktProperty().asObject());

		projektData = FXCollections.observableArrayList(myDB.getProjekte());

		if (!projektData.isEmpty())
			tbl_projektTabelle.setItems(projektData);
		else
			System.out.println("Keine Projekte angelegt");
		
	}

	// Event Listener on Button[#btn_newProjekt].onAction
	@FXML
	public void btn_newProjekt_click(ActionEvent event) throws Exception {
		System.out.println("Neues projekt!");

		// Überprüfe ob alle Eingabefelder ausgefüllt wurden
		if (!txt_newProjekt_name.getText().isEmpty() && !txt_newProjekt_ersteller.getText().isEmpty()) {

			// Überprüfe ob der gewünschte Name bereits verwendet wurde
			boolean doubleName = false;
			for (Projekt projekt : projektData) {
				if (projekt.getName().equals(txt_newProjekt_name.getText()))
					doubleName = true;
			}
			if (!doubleName) {
				Projekt newProjekt = new Projekt(txt_newProjekt_name.getText(), txt_newProjekt_ersteller.getText(),
						false);
				if (check_vorlage.isSelected()) {
					new OpenMainPage(newProjekt.getName(), newProjekt, true, true);
				} else {
					new OpenMainPage(newProjekt.getName(), newProjekt, false, true);
				}
				// Schließe Fenster
				Node source = (Node) event.getSource();
				Stage stage = (Stage) source.getScene().getWindow();
				stage.close();
			} else {
				System.out.println("Doppelter Projektname");
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Ihr gewählter Projektname wurde bereits verwendet!");
				alert.showAndWait();
			}
		} else {
			System.out.println("Es wurden nicht alle Felder ausgefüllt");
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Füllen Sie alle Felder aus");
			alert.showAndWait();
		}
	}
}
