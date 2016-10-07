package UI;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import org.sql2o.logging.SysOutLogger;

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
	private Button btn_newProjekt;
	@FXML
	private TableView<Projekt> tbl_projektTabelle = new TableView<Projekt>(); 
	@FXML
	private TableColumn<Projekt, String> tblCell_projektName = new TableColumn<Projekt, String>("Projekt Name");
	@FXML
	private TableColumn<Projekt, String> tblCell_projektErsteller = new TableColumn<Projekt, String>("Ersteller");;
	@FXML
	private TableColumn<Projekt, String> tblCell_projektStart = new TableColumn<Projekt, String>("Start Datum");
	@FXML
	private TableColumn<Projekt, String> tblCell_projektEnd = new TableColumn<Projekt, String>("End Datum");
	@FXML
	private TableColumn<Projekt, Boolean> tblCell_projektSend = new TableColumn<Projekt, Boolean>("Abgeschickt");
	@FXML
	private TextField txt_suche;
	@FXML
	private TextField txt_newProjekt_name;
	@FXML
	private TextField txt_newProjekt_ersteller;
	@FXML
	private CheckBox check_vorlage;
	@FXML
	private Button btn_suche;
		
	
	 Datenbank myDB = new Datenbank();
	 private ObservableList<Projekt> projektData = FXCollections.observableArrayList();

	
	
	@FXML
    private void initialize() {
		System.out.println("Initalisierungs Methode startet automatisch");
		
//		tblCell_projektName.setCellValueFactory(new PropertyValueFactory<Projekt, String>("name"));
//		tblCell_projektErsteller.setCellValueFactory(new PropertyValueFactory<Projekt, String>("ersteller"));
//		tblCell_projektStart.setCellValueFactory(new PropertyValueFactory<Projekt, String>("startDate"));
//		tblCell_projektEnd.setCellValueFactory(new PropertyValueFactory<Projekt, String>("endDate"));
//		tblCell_projektSend.setCellValueFactory(new PropertyValueFactory<Projekt, Boolean>("abgeschickt"));
		
		tblCell_projektName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		tblCell_projektErsteller.setCellValueFactory(cellData -> cellData.getValue().erstellerProperty());
		tblCell_projektStart.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());
		tblCell_projektEnd.setCellValueFactory(cellData -> cellData.getValue().endDateProperty());
		tblCell_projektSend.setCellValueFactory(cellData -> cellData.getValue().abgeschicktProperty().asObject());
		
		
		
		projektData = FXCollections.observableArrayList(myDB.getProjekte());
		
		if(!projektData.isEmpty())
			tbl_projektTabelle.setItems(projektData);
		else
			System.out.println("Keine Projekte angelegt");
		
			
	}

	// Event Listener on Button[#btn_newProjekt].onAction
	@FXML
	public void btn_newProjekt_click(ActionEvent event) {
		System.out.println("Neues projekt!");
	}
	// Event Listener on Button[#btn_suche].onAction
	@FXML
	public void btn_suche_click(ActionEvent event) {
		System.out.println("Suchen");
	}
}
