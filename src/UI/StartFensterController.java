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
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.MenuItem;

import org.sql2o.logging.SysOutLogger;

import com.sun.webkit.ContextMenu;

import Datenbank.Datenbank;
import Projekt.Projekt;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

		// Lade Projekte in die Tabelle
		if (!projektData.isEmpty())
			tbl_projektTabelle.setItems(projektData);

		// Reagiert auf Klicks auf ein Element in der Tabelle
		tbl_projektTabelle.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				// Doppelklick + Linke Maustaste
				if (mouseEvent.getClickCount() == 2 && (mouseEvent.getButton() == MouseButton.PRIMARY)) {
					// Überprüft ob auf einen Tabelleneintrag mit einem projekt geklickt wurde
					if (tbl_projektTabelle.getSelectionModel().getSelectedItem() instanceof Projekt) {
						try {
							new OpenMainPage(tbl_projektTabelle.getSelectionModel().getSelectedItem().getName(),
									tbl_projektTabelle.getSelectionModel().getSelectedItem(), true, false);
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
