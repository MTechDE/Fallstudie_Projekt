package UI;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import Datenbank.Datenbank;
import Export.Excel;
import Projekt.Projekt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

/**
 * In dieser Klasse wird die Logik des Startfensters behandelt.
 * @author Daniel Sogl
 *
 */
public class StartFensterController {
	@FXML
	private AnchorPane startScreen;
	@FXML
	private Button btn_aktualisiereProjekte;
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
	private TableColumn<Projekt, String> tblCell_projektSend;
	@FXML
	private TextField txt_newProjekt_name;
	@FXML
	private TextField txt_newProjekt_ersteller;
	@FXML
	private CheckBox check_vorlage;

	Datenbank myDB = new Datenbank();

	// Diese Liste aktualsiert sich automatisch und damit auch die Tabelle
	private ObservableList<Projekt> projektData;
	
	// Diese Methode wird autoamtisch beim Starten aufgerufen
	@FXML
	private void initialize() {
		
		// Zellen werden automatisch gefüllt, anhand der Projekt-Klasse
		tblCell_projektName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		tblCell_projektErsteller.setCellValueFactory(cellData -> cellData.getValue().erstellerProperty());
		tblCell_projektStart.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());
		tblCell_projektEnd.setCellValueFactory(cellData -> cellData.getValue().endDateProperty());
		tblCell_projektSend.setCellValueFactory(cellData -> cellData.getValue().abgeschicktProperty());

		projektData = FXCollections.observableArrayList(myDB.getProjekte());

		// Lade Projekte in die Tabelle
		if (!projektData.isEmpty())
			tbl_projektTabelle.setItems(projektData);

		// Reagiert auf Klicks auf ein Element in der Tabelle
		tbl_projektTabelle.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				// Doppelklick + Linke Maustaste
				if (mouseEvent.getClickCount() == 2 && (mouseEvent.getButton() == MouseButton.PRIMARY)) {
					// Überprüft ob auf einen Tabelleneintrag mit einem Projekt geklickt wurde
					if (tbl_projektTabelle.getSelectionModel().getSelectedItem() instanceof Projekt) {
						try {		
							// Schließe Fenster
							Node source = (Node) mouseEvent.getSource();
							Stage stage = (Stage) source.getScene().getWindow();
							stage.close();
							// Öffne Hauptfenster
							new OpenMainPage(tbl_projektTabelle.getSelectionModel().getSelectedItem(), false, false);
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					}
				}
			}
		});
		
		// Überprüfe ob die DB online ist
		if(!myDB.testConnection()){
			System.out.println("DB offline");
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Keine Verbindung zur Datenbank möglich");
			alert.showAndWait();
		}
	}

	// Event Listener on Button[#btn_newProjekt].onAction
	@FXML
	public void btn_newProjekt_click(ActionEvent event) throws Exception {
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
				// Schließe Fenster
				Node source = (Node) event.getSource();
				Stage stage = (Stage) source.getScene().getWindow();
				stage.close();
				// Öffne Hauptfenster
				if (check_vorlage.isSelected()) {
					new OpenMainPage(newProjekt, true, true);
				} else {
					new OpenMainPage(newProjekt, true, false);
				}
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
	
	/*
	 * Aktualisiere die Tabelle, falls Bsp. die Verbindung zur DB nicht möglich war,
	 * oder ein neues Projekt von einem anderen PC aus angelegt wurde
	 */
	@FXML
	public void btn_aktualisiereProjekte_click(ActionEvent event) throws Exception {
		System.out.println("Aktuallisiere");
		projektData = FXCollections.observableArrayList(myDB.getProjekte());
		if (!projektData.isEmpty())
			tbl_projektTabelle.setItems(projektData);
	}
	
	/*
	 * Zeige einen Filedialog und übergebe den Pfad an die Export Klasse
	 */
	@FXML
	public void btn_exportExcel_click(ActionEvent event) throws Exception {
		System.out.println("Excel Export");
		Stage stage = (Stage) btn_newProjekt.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Speichere Projekte");
        fileChooser.getExtensionFilters().addAll(
        		new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("Text", "*.txt")
        		);
        File file = fileChooser.showSaveDialog(stage);
        if(!Excel.ExportToExcel(projektData, file.getAbsolutePath())){
			System.out.println("Es wurden nicht alle Felder ausgefüllt");
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Füllen Sie alle Felder aus");
			alert.showAndWait();
        } else {
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Export");
        	alert.setHeaderText(null);
        	alert.setContentText("Speichern erfolgreich");
        	alert.showAndWait();
        }
		
	}
}
