package UI;

import java.util.Calendar;
import java.util.Date;

import Projekt.Aufwand;
import Projekt.Kompetenz;
import Projekt.Phase;
import Projekt.Projekt;
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
	private Button btn_aufwand_festlegen;
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
	long arbeitstage = 0;

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
		boolean vorhanden = false;
		// Prüfung ob Phase bereits vorhanden
		for (Phase phase : phasen) {
			if (phase.getName().equals(txt_phase.getText()))
				vorhanden = true;
		}
		if (!vorhanden) {
			// Prüfung ob alle Felder ausgefüllt
			if ((!(txt_phase.getText().equals("")) || txt_phase != null) && (dtpkr_start.getValue() != null)
					&& (dtpkr_end.getValue() != null) && !(txt_risikozuschlag.getText().equals(""))) {

				// Risikozuschlag von -,% und falschem Dezimalzeichen befreien
				String risikozuschlagString = txt_risikozuschlag.getText().replaceAll("%", "");
				risikozuschlagString = risikozuschlagString.replaceAll(",", ".");
				risikozuschlagString = risikozuschlagString.replaceAll("-", "");
				Double risikozuschlag = Double.parseDouble(risikozuschlagString);

				Phase phase = new Phase(txt_phase.getText(), dtpkr_start.getValue().toString(),
						dtpkr_end.getValue().toString(), risikozuschlag);

				phase.setSingleAufwand(new Aufwand("intern"));
				phase.setSingleAufwand(new Aufwand("extern"));
				phasen.add(phase);

				tbl_phase.setItems(phasen);
				// TODO: Fokus auf ein Element setzen, damit Arbeitstage immer
				// berechnet werden können
			} else {
				String fehlermeldung = "";
				if (txt_risikozuschlag.getText().equals(""))
					fehlermeldung = "Risikozuschlag eingeben.";

				if ((dtpkr_start.getValue() == null) || (dtpkr_end.getValue() == null))
					fehlermeldung = "Zeitraum muss ausgewählt werden.";

				if (txt_phase.getText().equals("") || txt_phase == null)
					fehlermeldung = "Phasenbezeichnung darf nicht leer sein.";

				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText(fehlermeldung);
				alert.showAndWait();
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Der angegebene Phasenname ist bereits vorhanden!");
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

			Phase phaseSelected = tbl_phase.getSelectionModel().getSelectedItem();
			Kompetenz kompetenzSelected = tbl_kompetenz.getSelectionModel().getSelectedItem();
			// Berechnung Arbeitstage aus Phasenzeitraum für
			// PT-Berechnung bei
			// MAK
			String startdatum = phaseSelected.getStartDate();
			String enddatum = phaseSelected.getEndDate();

			String[] startdatumArray = startdatum.split("-");
			String[] enddatumArray = enddatum.split("-");

			// Monat -1 da 0-11, Enddatumtag +1 damit inklusive
			arbeitstage = berechneArbeitstage(
					new Date(Integer.parseInt(startdatumArray[0]), Integer.parseInt(startdatumArray[1]) - 1,
							Integer.parseInt(startdatumArray[2])),
					new Date(Integer.parseInt(enddatumArray[0]), Integer.parseInt(enddatumArray[1]) - 1,
							Integer.parseInt(enddatumArray[2]) + 1));
			txt_mak_pt_intern.setText(arbeitstage + " PT");
			txt_mak_pt_extern.setText(arbeitstage + " PT");
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
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Das Enddatum darf nicht gleich wie das Startdatum sein oder davor liegen.");
			alert.showAndWait();
		}
	}

	@FXML
	public void btn_aufwand_festlegen_click(ActionEvent event) throws Exception {
		double ptIntern = 0;
		double ptExtern = 0;
		int auswahl = chobx_aufwand.getSelectionModel().getSelectedIndex();

		if ((auswahl == 0 && (txt_pt_intern.equals("") || txt_pt_extern.equals("")))
				|| (auswahl == 1 && (txt_mak_intern.equals("") || txt_mak_extern.equals("")))) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Bitte Aufwände für intern und extern eingeben!");
			alert.showAndWait();
		} else {
			try {
				Phase phaseSelected = tbl_phase.getSelectionModel().getSelectedItem();
				Kompetenz kompetenzSelected = tbl_kompetenz.getSelectionModel().getSelectedItem();

				switch (auswahl) {
				case 0:
					ptIntern = Double.parseDouble(txt_pt_intern.getText());
					ptExtern = Double.parseDouble(txt_pt_extern.getText());
					break;
				case 1:
					// Berechnung der PT: MAK * verfügbare Werktage der Phase
					ptIntern = Double.parseDouble(txt_mak_intern.getText()) * arbeitstage;
					ptExtern = Double.parseDouble(txt_mak_extern.getText()) * arbeitstage;
					break;
				default:
					break;
				}

				phaseSelected.getAufwände().get(0).setZugehoerigkeit(kompetenzSelected.getName());
				phaseSelected.getAufwände().get(0).setPt(ptIntern);

				phaseSelected.getAufwände().get(1).setZugehoerigkeit(kompetenzSelected.getName());
				phaseSelected.getAufwände().get(1).setPt(ptExtern);

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	@FXML
	public void btn_projekt_speichern_click(ActionEvent event) throws Exception {

		Projekt projekt = OpenMainPage.tmpProjekt;
		if (projekt != null) {
			for (Phase phase : tbl_phase.getItems()) {
				projekt.setSinglePhase(phase);
				System.out.println(phase.getName() + " wurde Projekt hinzugefügt!");
			}
			for (Kompetenz kompetenz : tbl_kompetenz.getItems()) {
				projekt.setSingleKompetenz(kompetenz);
				System.out.println(kompetenz.getName() + " wurde Projekt hinzugefügt!");
			}
			System.out.println("Projekt gespeichert!");
		}

	}

	// berechne Anzahl der Arbeitstage zwischen zwei Daten (Inklusive Start und
	// exklusive Enddatum!)
	public static long berechneArbeitstage(Date start, Date end) {
		// Ignore argument check
		Calendar c1 = Calendar.getInstance();
		c1.setTime(start);
		int w1 = c1.get(Calendar.DAY_OF_WEEK);
		c1.add(Calendar.DAY_OF_WEEK, -w1);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(end);
		int w2 = c2.get(Calendar.DAY_OF_WEEK);
		c2.add(Calendar.DAY_OF_WEEK, -w2);

		// end Saturday to start Saturday
		long days = (c2.getTimeInMillis() - c1.getTimeInMillis()) / (1000 * 60 * 60 * 24);
		long daysWithoutWeekendDays = days - (days * 2 / 7);

		// Adjust days to add on (w2) and days to subtract (w1) so that Saturday
		// and Sunday are not included
		if (w1 == Calendar.SUNDAY && w2 != Calendar.SATURDAY) {
			w1 = Calendar.MONDAY;
		} else if (w1 == Calendar.SATURDAY && w2 != Calendar.SUNDAY) {
			w1 = Calendar.FRIDAY;
		}

		if (w2 == Calendar.SUNDAY) {
			w2 = Calendar.MONDAY;
		} else if (w2 == Calendar.SATURDAY) {
			w2 = Calendar.FRIDAY;
		}

		return daysWithoutWeekendDays - w1 + w2;
	}
}