package UI;

import Datenbank.Datenbank;
import Projekt.Aufwand;
import Projekt.Kompetenz;
import Projekt.Phase;
import Projekt.Projekt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.DatePicker;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class HauptFensterController {
	@FXML
	private Button btn_Export;
	@FXML
	private TextField txt_projektName;
	@FXML
	private TextField txt_projektErsteller;
	@FXML
	private static TextField txt_aufwandIntern;
	@FXML
	private static TextField txt_aufwandExtern;
	@FXML
	private DatePicker datePicker_startDatum;
	@FXML
	private DatePicker datePicker_endDatum;
	@FXML
	private TableView<Kompetenz> tbl_kompetenzen;
	@FXML
	private TableColumn<Kompetenz, String> tbl_kompetenzen_Name;
	@FXML
	private TableView<Phase> tbl_phasen;
	@FXML
	private TableColumn<Phase, String> tbl_phasen_name;
	@FXML
	private TableColumn<Phase, String> tbl_phasen_startDatum;
	@FXML
	private TableColumn<Phase, String> tbl_phasen_endDatum;
	
	static Projekt projekt;
	Datenbank myDB = new Datenbank();
	
	private ObservableList<Kompetenz> kompetenzenData;
	private ObservableList<Phase> phasenData;
	
	// Indizes der Phasen und Kompetenzen
	int indexPhase;
	int indexKompetenz;
	
	boolean indexPhaseClicked = false;
	boolean indexKompetenzClicked = false;
	
	// Diese Methode wird autoamtisch beim Starten aufgerufen
	@FXML
	private void initialize() {
		
		projekt = OpenMainPage.tmpProjekt;
		
		
		for (Kompetenz kompetenz : projekt.getKompetenzen()) {
			for (Phase phase : projekt.getPhasen()) {
				System.out.println(phase.getName());
				for (Aufwand aufwand : phase.getAufwände()) {
					if(aufwand.getZugehoerigkeit().equals(kompetenz.getName()))
						System.out.println(aufwand.getName() + " PT: " + aufwand.getPt() + " Kompetenz: " + aufwand.getZugehoerigkeit());
				}
			}
		}
		
		
		// Weise Zellen eine Property zu
		tbl_kompetenzen_Name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		tbl_phasen_name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		tbl_phasen_startDatum.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());
		tbl_phasen_endDatum.setCellValueFactory(cellData -> cellData.getValue().endDateProperty());
		kompetenzenData = FXCollections.observableArrayList(projekt.getKompetenzen());
		phasenData = FXCollections.observableArrayList(projekt.getPhasen());
		
		if(!kompetenzenData.isEmpty())
			tbl_kompetenzen.setItems(kompetenzenData);
		if(!phasenData.isEmpty())
			tbl_phasen.setItems(phasenData);
		
		tbl_kompetenzen.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				indexKompetenz = tbl_kompetenzen.getSelectionModel().getFocusedIndex();
				indexKompetenzClicked = true;
				if(indexKompetenzClicked && indexPhaseClicked)
					showPT(indexPhase, indexKompetenz);
			}
		});
		
		tbl_phasen.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				indexPhase = tbl_phasen.getSelectionModel().getFocusedIndex();
				indexPhaseClicked = true;
				if(indexKompetenzClicked && indexPhaseClicked)
					showPT(indexPhase, indexKompetenz);
			}
		});
	}
	
	public static void showPT(int pIndex, int kIndex){
		System.out.println("Beide Komponenten wurden ausgewählt");
		
		// Hole die PT anhand der Phase und des Kompetenznamnes
		
		Phase tmpPhase = projekt.getPhasen().get(pIndex);
		Kompetenz tmpKompetenz = projekt.getKompetenzen().get(kIndex);
		
		for (Aufwand aufwand : tmpKompetenz.getAufwände()) {
			for(Aufwand phasenAufwand: tmpPhase.getAufwände()){
				if(aufwand.getName().equals(phasenAufwand.getName())){
					System.out.println(phasenAufwand.getName() + "PT: " + phasenAufwand.getPt());
				}
			}
		}
	}

}
