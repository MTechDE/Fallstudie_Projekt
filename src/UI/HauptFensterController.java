package UI;

import Datenbank.Datenbank;
import Projekt.Aufwand;
import Projekt.Kompetenz;
import Projekt.Phase;
import Projekt.Projekt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class HauptFensterController {
	@FXML
	private TextField txt_kompetenzName;
	@FXML
	private TextField txt_phasenName;
	@FXML
	private TextField txt_ptIntern;
	@FXML
	private TextField txt_ptExtern;
	@FXML
	private Button btn_speicherPT;
	@FXML
	private Button btn_Export;
	@FXML
	private Button btn_newPhase;
	@FXML
	private Button btn_newKompetenz;
	@FXML
	private Button btn_SpeicherProjekt;
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
	
	// PT eines Aufwands
	double ptIntern;
	double ptExtern;
	
	boolean indexPhaseClicked = false;
	boolean indexKompetenzClicked = false;
	
	// Diese Methode wird autoamtisch beim Starten aufgerufen
	@FXML
	private void initialize() {
		
		projekt = OpenMainPage.tmpProjekt;
		
		// Weise Zellen eine Property zu
		tbl_kompetenzen_Name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		tbl_phasen_name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		tbl_phasen_startDatum.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());
		tbl_phasen_endDatum.setCellValueFactory(cellData -> cellData.getValue().endDateProperty());
		kompetenzenData = FXCollections.observableArrayList(projekt.getKompetenzen());
		phasenData = FXCollections.observableArrayList(projekt.getPhasen());
		
		tbl_kompetenzen.setItems(kompetenzenData);
		tbl_phasen.setItems(phasenData);
		
		tbl_kompetenzen.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				indexKompetenz = tbl_kompetenzen.getSelectionModel().getFocusedIndex();
				indexKompetenzClicked = true;
				if(indexKompetenzClicked && indexPhaseClicked && !kompetenzenData.isEmpty() && !phasenData.isEmpty()){

					int indexIntern = 0;
					int indexExtern  = 0;
					int i = 0;
					for (Aufwand aufwand : projekt.getPhasen().get(indexPhase).getAufwände()) {
						if(aufwand.getZugehoerigkeit().equals(projekt.getKompetenzen().get(indexKompetenz).getName())){
							if(aufwand.getName().equals("Intern"))
								indexIntern = i;
							else
								indexExtern = i;
						}
						i++;
					}		
					
					txt_ptIntern.setText(String.valueOf(projekt.getPhasen().get(indexPhase).getAufwände().get(indexIntern).getPt()));
					txt_ptExtern.setText(String.valueOf(projekt.getPhasen().get(indexPhase).getAufwände().get(indexExtern).getPt()));
				}
			}
		});
		
		tbl_phasen.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				indexPhase = tbl_phasen.getSelectionModel().getFocusedIndex();
				indexPhaseClicked = true;
				if(indexKompetenzClicked && indexPhaseClicked && !kompetenzenData.isEmpty() && !phasenData.isEmpty()){
					Phase tmpPhase = projekt.getPhasen().get(indexPhase);
					Kompetenz tmpKompetenz = projekt.getKompetenzen().get(indexKompetenz);
					
					int indexIntern = 0;
					int indexExtern  = 0;
					int i = 0;
					for (Aufwand aufwand : projekt.getPhasen().get(indexPhase).getAufwände()) {
						if(aufwand.getZugehoerigkeit().equals(projekt.getKompetenzen().get(indexKompetenz).getName())){
							if(aufwand.getName().equals("Intern"))
								indexIntern = i;
							else
								indexExtern = i;
						}
						i++;
					}		
					
					txt_ptIntern.setText(String.valueOf(projekt.getPhasen().get(indexPhase).getAufwände().get(indexIntern).getPt()));
					txt_ptExtern.setText(String.valueOf(projekt.getPhasen().get(indexPhase).getAufwände().get(indexExtern).getPt()));
				}
			}
		});
	}
	
	@FXML
	public void btn_newKompetenz_click(ActionEvent event) throws Exception {
		
		boolean nameUsed = false;
		for (Kompetenz kompetenz : kompetenzenData) {
			if(kompetenz.getName().equals(txt_kompetenzName.getText()))
				nameUsed = true;
		}
		
		if(!nameUsed){
			Kompetenz kompetenz = new Kompetenz(txt_kompetenzName.getText());
			kompetenz.setSingleAufwand(new Aufwand("Intern"));
			kompetenz.setSingleAufwand(new Aufwand("Extern"));
			
			Aufwand intern = new Aufwand("Intern");
			intern.setZugehoerigkeit(kompetenz.getName());
			intern.setPt(0);
			
			Aufwand extern = new Aufwand("Extern");
			extern.setZugehoerigkeit(kompetenz.getName());
			extern.setPt(0);
			
			for(int i = 0; i < projekt.getPhasen().size(); i++){
				projekt.getPhasen().get(i).setSingleAufwand(intern);
				projekt.getPhasen().get(i).setSingleAufwand(extern);
				System.out.println(projekt.getPhasen().get(i).getName());
			}
			
			projekt.setSingleKompetenz(kompetenz);
			kompetenzenData.add(new Kompetenz(txt_kompetenzName.getText()));
		} else{
			System.out.println("Fehler!");
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Kompetenzname bereits  verwendet");
			alert.showAndWait();
		}
	}
	
	@FXML
	public void btn_newPhase_click(ActionEvent event) throws Exception {
	}
	
	@FXML
	public void btn_SpeicherProjekt_click (ActionEvent event) throws Exception {
		myDB.speicherProjekt(projekt);
	}
	
	@FXML
	public void btn_speicherPT_click(ActionEvent event) throws Exception {
		double internPT = Double.parseDouble(txt_ptIntern.getText());
		double externPT = Double.parseDouble(txt_ptExtern.getText());
		
		int indexIntern = 0;
		int indexExtern  = 0;
		int i = 0;
		for (Aufwand aufwand : projekt.getPhasen().get(indexPhase).getAufwände()) {
			if(aufwand.getZugehoerigkeit().equals(projekt.getKompetenzen().get(indexKompetenz).getName())){
				if(aufwand.getName().equals("Intern"))
					indexIntern = i;
				else
					indexExtern = i;
			}
			i++;
		}		
		
		projekt.getPhasen().get(indexPhase).getAufwände().get(indexIntern).setPt(internPT);
		projekt.getPhasen().get(indexPhase).getAufwände().get(indexExtern).setPt(externPT);		
		
	}

}
