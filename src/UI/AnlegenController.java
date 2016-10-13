package UI;

import Projekt.Kompetenz;
import Projekt.Phase;
import Projekt.Projekt;
import UI.OpenMainPage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Controller für Anlegen.fxml GUI
 * 
 * @author Tim Krießler
 */
public class AnlegenController {

	@FXML
	private TableView<Kompetenz> tbl_kompetenz;
	@FXML
	private TableView<Phase> tbl_phase;
	@FXML
	private TextField txt_kompetenz;
	@FXML
	private TextField txt_phase;
	@FXML
	private TextField txt_risikozuschlag;
	@FXML
	private TextField txt_pt_intern;
	@FXML
	private TextField txt_pt_extern;
	@FXML
	private TextField txt_mak_pt_intern;
	@FXML
	private TextField txt_mak_pt_extern;
	@FXML
	private TextField txt_mak_intern;
	@FXML
	private TextField txt_mak_extern;
	@FXML
	private Button btn_phase;
	@FXML
	private Button btn_kompetenz;
	@FXML
	private Button btn_projekt_speichern;
	@FXML
	private TableColumn<Kompetenz, String> tblCell_kompetenz;
	@FXML
	private TableColumn<Phase, String> tblCell_phase;
	@FXML
	private ChoiceBox<String> chobx_aufwand;
	@FXML
	private DatePicker dtpkr_start;
	@FXML
	private DatePicker dtpkr_end;

	// Diese Liste aktualsiert sich automatisch und damit auch die Tabelle
	private ObservableList<Kompetenz> kompetenzen = FXCollections.observableArrayList();

	// Diese Liste aktualsiert sich automatisch und damit auch die Tabelle
	private ObservableList<Phase> phasen = FXCollections.observableArrayList();

	// Diese Liste aktualsiert sich automatisch und damit auch die Tabelle
	private ObservableList<String> aufwaende = FXCollections.observableArrayList();

	// Variablen
	Projekt projekt;

	@FXML
	private void initialize() {
		System.out.println("Fenster wurde geöffnet.");

		// Importiere Projekt
		projekt = OpenMainPage.tmpProjekt;

		// Initalisiere Tabelle

		tblCell_kompetenz.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		tblCell_phase.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

		kompetenzen = FXCollections.observableArrayList(projekt.getKompetenzen());
		phasen = FXCollections.observableArrayList(projekt.getPhasen());

		tbl_kompetenz.setItems(kompetenzen);
		tbl_phase.setItems(phasen);

		aufwaende.add("Personentage (PT)");
		aufwaende.add("Mitarbeiterkapazität (MAK)");
		chobx_aufwand.setItems(aufwaende);
		// chobx_aufwand.getSelectionModel().selectFirst();

		txt_pt_intern.setVisible(false);
		txt_pt_extern.setVisible(false);
		txt_mak_intern.setVisible(false);
		txt_mak_extern.setVisible(false);
		txt_mak_pt_intern.setVisible(false);
		txt_mak_pt_extern.setVisible(false);
	}

	@FXML
	public void btn_kompetenz_click(ActionEvent event) throws Exception {
		// TODO Kompetenz darf nicht bereits bestehen

		if (!(txt_kompetenz.getText().equals("") || txt_kompetenz == null)) {
			kompetenzen.add(new Kompetenz(txt_kompetenz.getText()));
			tbl_kompetenz.setItems(kompetenzen);
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Kompetenzbezeichnung darf nicht leer sein.");
			alert.showAndWait();
		}

	}

	@FXML
	public void btn_phase_click(ActionEvent event) throws Exception {
		// TODO Phase darf nicht bereits bestehen

		if ((!(txt_phase.getText().equals("")) || txt_phase != null) && (dtpkr_start.getValue() != null)
				&& (dtpkr_end.getValue() != null)) {
			// TODO Risikozuschlag muss noch übergeben werden
			// phasen.add(
			// new Phase(txt_phase.getText(), dtpkr_start.getValue().toString(),
			// dtpkr_end.getValue().toString()));
			tbl_phase.setItems(phasen);
		} else {
			String fehlermeldung = "";

			if ((dtpkr_start.getValue() == null) || (dtpkr_end.getValue() == null)) {
				fehlermeldung = "Zeitraum muss ausgewählt werden.";
			}
			if (txt_phase.getText().equals("") || txt_phase == null) {
				fehlermeldung = "Phasenbezeichnung darf nicht leer sein.";
			}
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(fehlermeldung);
			alert.showAndWait();
		}
	}

	@FXML
	public void chobx_aufwand_selected(ActionEvent event) throws Exception {

		String chobx_aufwand_selection = chobx_aufwand.getValue();
		System.out.println(chobx_aufwand_selection);
		switch (chobx_aufwand_selection) {
		case "Personentage (PT)":
			txt_mak_intern.setVisible(false);
			txt_mak_extern.setVisible(false);
			txt_mak_pt_intern.setVisible(false);
			txt_mak_pt_extern.setVisible(false);
			txt_pt_intern.setVisible(true);
			txt_pt_extern.setVisible(true);
			break;
		case "Mitarbeiterkapazität (MAK)":
			txt_pt_intern.setVisible(false);
			txt_pt_extern.setVisible(false);
			txt_mak_intern.setVisible(true);
			txt_mak_extern.setVisible(true);
			txt_mak_pt_intern.setVisible(true);
			txt_mak_pt_extern.setVisible(true);
			break;

		default:
			break;
		}
	}

	@FXML
	public void datepicker_ende_selected(ActionEvent event) throws Exception {

		int startDatum = Integer.parseInt(dtpkr_start.getValue().toString().replaceAll("-", ""));
		int endDatum = Integer.parseInt(dtpkr_end.getValue().toString().replaceAll("-", ""));
		System.out.println(startDatum);
		System.out.println(endDatum);
		if (endDatum <= startDatum) {
			dtpkr_end.setValue(dtpkr_start.getValue().plusDays(1));
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Das Enddatum darf nicht gleich wie das Startdatum sein oder davor liegen.");
			alert.showAndWait();
		}
	}

	@FXML
	public void btn_projekt_speichern_click(ActionEvent event) throws Exception {
		System.out.println("Projekt gespeichert!");
	}

}