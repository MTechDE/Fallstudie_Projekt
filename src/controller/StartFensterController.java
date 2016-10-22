package controller;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Optional;

import Datenbank.Datenbank;
import Projekt.Projekt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

/**
 * In dieser Klasse wird die Logik des Startfensters behandelt.
 * 
 * @author Daniel Sogl
 *
 */
public class StartFensterController {
	@FXML
	private AnchorPane startScreen;
	@FXML
	private Button btn_newProjekt;
	@FXML
	private Button btn_deleteProjekt;
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
	private TextField txt_searchProjekt_name;
	@FXML
	private ProgressBar progress_statusBar;
	@FXML
	private Label lbl_projekteGefunden;

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
		lbl_projekteGefunden.setText(String.valueOf(projektData.size()));

		FilteredList<Projekt> filteredData = new FilteredList<>(projektData, p -> true);
		txt_searchProjekt_name.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(projekt -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (projekt.getName().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}
				return false;
			});
		});

		SortedList<Projekt> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tbl_projektTabelle.comparatorProperty());

		// Lade Projekte in die Tabelle
		if (!projektData.isEmpty())
			tbl_projektTabelle.setItems(sortedData);

		// Reagiert auf Klicks auf ein Element in der Tabelle
		tbl_projektTabelle.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {

				if (tbl_projektTabelle.getFocusModel().getFocusedIndex() != 0)
					btn_deleteProjekt.setDisable(false);
				// Doppelklick + Linke Maustaste
				if (mouseEvent.getClickCount() == 2 && (mouseEvent.getButton() == MouseButton.PRIMARY)) {
					// Überprüft ob auf einen Tabelleneintrag mit einem Projekt
					// geklickt wurde
					if (tbl_projektTabelle.getSelectionModel().getSelectedItem() instanceof Projekt) {
						try {
							// Öffne Hauptfenster
							new OpenMainPage(tbl_projektTabelle.getSelectionModel().getSelectedItem(), false);
							// Schließe Fenster
							Node source = (Node) mouseEvent.getSource();
							Stage stage = (Stage) source.getScene().getWindow();
							stage.close();
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					}
				}
			}
		});

		// Überprüfe ob die DB online ist
		if (!myDB.testConnection()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Keine Verbindung zur Datenbank möglich");
			alert.showAndWait();
		}
	}

	@FXML
	public void btn_deleteProjekt_click(ActionEvent event) throws Exception {

		if (!tbl_projektTabelle.getItems().isEmpty()) {
			try {
				int index = tbl_projektTabelle.getSelectionModel().getSelectedIndex();
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setContentText("Möchten Sie das Projekt: "
						+ tbl_projektTabelle.getSelectionModel().getSelectedItem().getName() + " wirklich löschen?");
				ButtonType buttonTypeOne = new ButtonType("Löschen");
				ButtonType buttonTypeCancel = new ButtonType("Abbrechen", ButtonData.CANCEL_CLOSE);
				alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
				Optional<ButtonType> result = alert.showAndWait();

				if (result.get() == buttonTypeOne) {
					Node source = (Node) event.getSource();
					Scene scene = source.getScene();
					scene.setCursor(Cursor.WAIT);
					myDB.deleteProjekt(tbl_projektTabelle.getSelectionModel().getSelectedItem());
					projektData.remove(index);
					lbl_projekteGefunden.setText(String.valueOf(projektData.size()));
					scene.setCursor(Cursor.DEFAULT);
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	// Event Listener on Button[#btn_newProjekt].onAction
	@FXML
	public void btn_newProjekt_click(ActionEvent event) throws Exception {
		// Überprüfe ob alle Eingabefelder ausgefüllt wurden
		if (!txt_newProjekt_name.getText().isEmpty() && !txt_newProjekt_ersteller.getText().isEmpty()) {
			if (txt_newProjekt_name.getText().length() <= 120 && txt_newProjekt_ersteller.getText().length() <= 60) {
				// Überprüfe ob der gewünschte Name bereits verwendet wurde
				boolean doubleName = false;
				for (Projekt projekt : projektData) {
					if (projekt.getName().equals(txt_newProjekt_name.getText()))
						doubleName = true;
				}
				if (!doubleName) {
					Projekt newProjekt = new Projekt(txt_newProjekt_name.getText(), txt_newProjekt_ersteller.getText(),
							false);
					// Öffne Hauptfenster
					Node source = (Node) event.getSource();
					Stage stage = (Stage) source.getScene().getWindow();
					new OpenMainPage(newProjekt, true);

					// Schließe Fenster
					stage.close();
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Projektname wurde bereits verwendet!");
					alert.showAndWait();
				}

			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Ihr gewählter Projektname wurde bereits verwendet!");
				alert.showAndWait();
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Füllen Sie alle Felder aus");
			alert.showAndWait();
		}
	}
}
