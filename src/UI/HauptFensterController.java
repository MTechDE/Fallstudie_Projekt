package UI;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;

import java.util.ArrayList;
import java.util.List;

import Projekt.Phase;
import Projekt.Projekt;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;

public class HauptFensterController {
	@FXML
	private TableView<ObservableList<String>> ProjektDetailTabelle;
	@FXML
	private Button btn_newPhase;
	
	// Projekt aus der Startseite
	Projekt projekt;
	
	// Erzeuge Phasen Spalten
	List<String> columns = new ArrayList<String>();
	String headers[] = null;
	TableColumn<ObservableList<String>, String> column;
	
	
	
	// Erzeuge Spalten und Zeilen anhand der Projekt Phasen und Kompetenzen
	@FXML
	private void initialize() {
		projekt = OpenMainPage.tmpProjekt;
		columns.add("Kompetenzen");
		for (Phase phase : projekt.getPhasen()) {
			columns.add(phase.getName());
		}
		for (String string : columns) {
			column = new TableColumn<>(string);
			ProjektDetailTabelle.getColumns().add(column);
		}
        
	}

	// Event Listener on Button[#btn_newPhase].onAction
	// Füge eine neue Phase Hinzu
	@FXML
	public void btn_newPhase_click(ActionEvent event) {
		System.out.println("Neue Zelle hinzufügen");
		column = new TableColumn<>("Neue Phase");
		ProjektDetailTabelle.getColumns().add(column);
	}
}
