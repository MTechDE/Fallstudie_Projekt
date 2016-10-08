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
	private TableView<ObservableList<String>> ProjektDetailTabelle;
	@FXML
	private Button btn_newPhase;
	@FXML
	private Button btn_newKompetenz;
	
	// Projekt aus der Startseite
	Projekt projekt;
	
	// Erzeuge Phasen Spalten
	List<String> columns = new ArrayList<String>();
	List<String> rows = new ArrayList<String>();	
    ObservableList<Kompetenz> kompetenzData = FXCollections.observableArrayList();
    ObservableList<Aufwand> aufwandData = FXCollections.observableArrayList();
	
	
	
	// Erzeuge Spalten und Zeilen anhand der Projekt Phasen und Kompetenzen
	@FXML
	private void initialize() {
		
		// Die erste Spalte ist für die Kompetenzen "reserviert". Die Länge wird auf 100px festgelegt.
		projekt = OpenMainPage.tmpProjekt;
		ProjektDetailTabelle.getColumns().add(new TableColumn<>("Kompetenzen"));
		
		// Spalten Namen (Phasen Namen)
		
		for (Phase phase : projekt.getPhasen()) {
			ProjektDetailTabelle.getColumns().add(new TableColumn<>(phase.getName()));
		}
		
		
		kompetenzData = FXCollections.observableArrayList(projekt.getKompetenzen());
		
        
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
