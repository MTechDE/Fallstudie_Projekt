package UI;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;

import java.util.ArrayList;
import java.util.List;

import Projekt.Aufwand;
import Projekt.Kompetenz;
import Projekt.Phase;
import Projekt.Projekt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;


public class HauptFensterController {
	@FXML
	private TableView<ObservableList<String>> ProjektDetailTabelle = new TableView<>();
	@FXML
	private Button btn_newPhase;
	@FXML
	private Button btn_newKompetenz;
	
	// Projekt aus der Startseite
	private Projekt projekt;
	
	// Erzeuge Phasen Spalten
	List<String> columns = new ArrayList<String>();
	List<String> rows = new ArrayList<String>();	
	ObservableList<Kompetenz> kompetenzData;
	ObservableList<Aufwand> aufwandData;
	
    
	
	
	
	// Erzeuge Spalten und Zeilen anhand der Projekt Phasen und Kompetenzen
	@FXML
	private void initialize() {
		
		projekt = OpenMainPage.tmpProjekt;
		
		// Die erste Spalte ist für die Kompetenzen "reserviert"
		columns.add("Kompetenz");
		
		// Phasennamen
		for (Phase phase : projekt.getPhasen()) {
			columns.add(phase.getName());
		}
		
		// Schreibe Überschriften in neue Spalten
		for(int i = 0; i < columns.size(); i++){
			   TableColumn<ObservableList<String>, String> column = new TableColumn<>(columns.get(i));
			   ProjektDetailTabelle.getColumns().add(column);
		}
		
		kompetenzData = FXCollections.observableArrayList(projekt.getKompetenzen());
		
		
		
		
		
//		projekt = OpenMainPage.tmpProjekt;
//		columns.add("Kompetenzen");
//		
//		// Spalten Namen (Phasen Namen)
//		for (Phase phase : projekt.getPhasen()) {
//			columns.add(phase.getName());
//		}
//		
//		// Erzeuge die Spalten mit den Phasennamen als Überschrift
//		for(int i = 0; i < columns.size(); i++){
//			TableColumn<ObservableList<String>, String> column = new TableColumn<>(
//					columns.get(i)
//            );
//			ProjektDetailTabelle.getColumns().add(column);
//		}    
	}

	// Event Listener on Button[#btn_newPhase].onAction
	// Füge eine neue Phase Hinzu
	@FXML
	public void btn_newPhase_click(ActionEvent event) {
		System.out.println("Neue Zelle hinzufügen");
		ProjektDetailTabelle.getColumns().add(new TableColumn<>("Neue Phase"));
	}
	
	@FXML
	public void btn_newKompetenz_click(ActionEvent event) {
		System.out.println("Neue Kompetenz hinzufügen");
	}
}
