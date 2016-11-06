package controller;

import java.io.File;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import configuration.Configuration;
import datenbank.Datenbank;
import export.Excel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Aufwand;
import model.Kompetenz;
import model.Phase;
import model.Projekt;
import view.OpenChangeView;
import view.OpenMainPage;
import view.OpenStartPage;

/**
 * Controller für die Haupt View
 * 
 * @author Daniel Sogl, Tim Krießler
 */
public class MainViewController {

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
	private Button btn_export;
	@FXML
	private Button btn_zurueck;
	@FXML
	private Button btn_deletePhase;
	@FXML
	private Button btn_deleteKompetenz;
	@FXML
	private Button btn_sendProjekt;
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
	@FXML
	private DatePicker dtpkr_meldeDatum;
	@FXML
	private ImageView img_saveBtnImg;
	@FXML
	private ImageView img_sendBtnImg;
	@FXML
	private ImageView img_exportBtnImg;
	@FXML
	private Label lbl_Intern;
	@FXML
	private Label lbl_Extern;

	// Diese Listen aktualisieren sich automatisch und damit auch die Tabellen
	private ObservableList<Kompetenz> kompetenzen = FXCollections.observableArrayList();
	private ObservableList<Phase> phasen = FXCollections.observableArrayList();
	private ObservableList<String> aufwaende = FXCollections.observableArrayList();

	// Variablen
	public static Projekt projekt;
	private long arbeitstage = 0;
	private static Datenbank myDB = new Datenbank();
	private boolean indexPhaseClicked = false;
	private boolean indexKompetenzClicked = false;
	public static boolean somethingChanged = false;

	/**
	 * Initialisiert die View
	 */
	@FXML
	private void initialize() {

		// Importiere Projekt
		projekt = OpenMainPage.tmpProjekt;

		// Initialisiere Tabellen
		tbl_kompetenz.setPlaceholder(new Label("Keine Kompetenzen angelegt"));
		tbl_phase.setPlaceholder(new Label("Keine Phasen angelegt"));
		tblCell_kompetenz.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		tblCell_phase.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

		if (!projekt.getKompetenzen().isEmpty())
			kompetenzen = FXCollections.observableArrayList(projekt.getKompetenzen());
		if (!projekt.getPhasen().isEmpty())
			phasen = FXCollections.observableArrayList(projekt.getPhasen());

		// Weise den Tabellen die Daten zu
		tbl_kompetenz.setItems(kompetenzen);
		tbl_phase.setItems(phasen);

		//Initialisiere UI Elemente
		aufwaende.add("Personentage (PT)");
		aufwaende.add("Mitarbeiterkapazität (MAK)");
		chobx_aufwand.setItems(aufwaende);
		img_exportBtnImg.setVisible(false);
		img_sendBtnImg.setVisible(false);
		img_saveBtnImg.setVisible(false);
		lbl_Intern.setVisible(false);
		lbl_Extern.setVisible(false);
		btn_aufwand_festlegen.setVisible(false);
		txt_pt_intern.setVisible(false);
		txt_pt_extern.setVisible(false);
		txt_mak_intern.setVisible(false);
		txt_mak_extern.setVisible(false);
		txt_mak_pt_intern.setVisible(false);
		txt_mak_pt_extern.setVisible(false);

		// Setze das Meldedatum
		if (projekt.isgemeldet())
			dtpkr_meldeDatum.setValue(LocalDate.parse(projekt.getMeldeDatum()));

		// ActionHandler Tabelle Phase
		tbl_phase.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				// Überprüfe ob ein Element aktiv ist, oder nur ins "leere"
				// geklickt wurde
				if (mouseEvent.getClickCount() == 2 && (mouseEvent.getButton() == MouseButton.PRIMARY)
						&& !tbl_phase.getItems().isEmpty()
						&& (tbl_phase.getSelectionModel().getSelectedItem() instanceof Phase)) {
					try {
						new OpenChangeView(tbl_phase.getSelectionModel().getSelectedItem());
						aktualisierePhasen();
					} catch (Exception e) {
						if (Configuration.DEBUG)
							System.out.println(e.getMessage());
					}
				}

				try {
					if (!tbl_phase.getItems().isEmpty())
						btn_deletePhase.setDisable(false);

					// Überprüfe ob ein Element aktiv ist, oder nur ins "leere"
					// geklickt wurde
					if (!tbl_phase.getItems().isEmpty() && !tbl_kompetenz.getItems().isEmpty()
							&& tbl_phase.getSelectionModel().getSelectedItem().getClass() == Phase.class) {
						Phase phaseSelected = tbl_phase.getSelectionModel().getSelectedItem();
						// Berechnung Arbeitstage aus Phasenzeitraum für
						// PT-Berechnung bei MAK
						String startdatum = phaseSelected.getStartDate();
						String enddatum = phaseSelected.getEndDate();

						arbeitstage = calculateDate(startdatum, enddatum);
						txt_mak_pt_intern.setText(arbeitstage + " PT");
						txt_mak_pt_extern.setText(arbeitstage + " PT");
						indexPhaseClicked = true;
						if (indexKompetenzClicked) {
							btn_aufwand_festlegen.setDisable(false);
							fülleFelder();
						}
					}
				} catch (Exception e) {
					if (Configuration.DEBUG)
						System.out.println(e.getMessage());
				}
			}
		});

		// ActionHandler Tabelle Kompetenz
		tbl_kompetenz.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {

				// Überprüft ob ein Doppelklick getätigt wurde und ob auf einen Eintrag geklickt wurde
				if (mouseEvent.getClickCount() == 2 && (mouseEvent.getButton() == MouseButton.PRIMARY)
						&& !tbl_kompetenz.getItems().isEmpty()
						&& (tbl_kompetenz.getSelectionModel().getSelectedItem() instanceof Kompetenz)) {
					try {
						new OpenChangeView(tbl_kompetenz.getSelectionModel().getSelectedItem());
						aktualisiereKompetenzen();
					} catch (Exception e) {
						if (Configuration.DEBUG)
							System.out.println(e.getMessage());
					}
				}

				// Befüllt die Felder
				try {
					if (!tbl_kompetenz.getItems().isEmpty())
						btn_deleteKompetenz.setDisable(false);

					if (!tbl_kompetenz.getItems().isEmpty() && !tbl_phase.getItems().isEmpty()
							&& tbl_kompetenz.getSelectionModel().getSelectedItem().getClass() == Kompetenz.class) {
						indexKompetenzClicked = true;
						if (indexPhaseClicked) {
							btn_aufwand_festlegen.setDisable(false);
							fülleFelder();
						}
					}
				} catch (Exception e) {
					if (Configuration.DEBUG)
						System.out.println(e.getMessage());
				}
			}
		});

		// Überprüft, ob die PT oder MAK geändert wurden
		txt_pt_intern.textProperty().addListener((observable) -> {
			btn_aufwand_festlegen.setDisable(false);
		});
		txt_pt_extern.textProperty().addListener((observable) -> {
			btn_aufwand_festlegen.setDisable(false);
		});
		txt_mak_intern.textProperty().addListener((observable) -> {
			btn_aufwand_festlegen.setDisable(false);
		});
		txt_mak_extern.textProperty().addListener((observable) -> {
			btn_aufwand_festlegen.setDisable(false);
		});
	}

	/**
	 * Actionlistener für den Neue Kompetenz-Button
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	public void btn_kompetenz_click(ActionEvent event) throws Exception {

		// Überprüfe ob alle Felder befüllt wurden
		if (txt_kompetenz.getText().trim().isEmpty() || txt_risikozuschlag.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			if (txt_kompetenz.getText().trim().isEmpty())
				alert.setContentText("Bitte geben Sie eine Kompetenzbezeichnung an!");
			else if (txt_risikozuschlag.getText().trim().isEmpty())
				alert.setContentText("Bitte geben Sie einen Risikozuschlag an!");
			alert.showAndWait();

			// Überprüft ob der Name bereits verwendet wurde
		} else if (!kompetenzen.parallelStream().anyMatch(k -> k.getName().equals(txt_kompetenz.getText()))) {

			// Risikozuschlag von -,% und falschem Dezimalzeichen
			// befreien

			try {
				String risikozuschlagString = txt_risikozuschlag.getText().replaceAll("%", "");
				risikozuschlagString = risikozuschlagString.replaceAll(",", ".");
				risikozuschlagString = risikozuschlagString.replaceAll("-", "");
				Double risikozuschlag = Double.parseDouble(risikozuschlagString);

				Kompetenz kompetenz = new Kompetenz(txt_kompetenz.getText(), risikozuschlag);

				projekt.setSingleKompetenz(kompetenz);
				kompetenzen.add(kompetenz);
				tbl_kompetenz.setItems(kompetenzen);
				checkChanges();
			} catch (Exception e) {
				if (Configuration.DEBUG)
					System.out.println(e.getMessage());
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Der angegebene Kompetenzname ist bereits vorhanden!");
			alert.showAndWait();
		}
	}

	/**
	 * Aktualisiert die Kompetenztabelle
	 */
	public void aktualisiereKompetenzen() {
		// Workaround um die Tabelle zu aktualisieren
		tbl_kompetenz.getColumns().get(0).setVisible(false);
		tbl_kompetenz.getColumns().get(0).setVisible(true);
	}

	/**
	 * Actionlistener für den Neue Phase-Button
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	public void btn_phase_click(ActionEvent event) throws Exception {
		
		// Überprüft ob alle Felder befüllt wurden
		if (txt_phase.getText().trim().isEmpty() || dtpkr_start.getValue() == null || dtpkr_end.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			if (txt_phase.getText().trim().isEmpty())
				alert.setContentText("Bitte geben Sie einen Phasennamen an!");
			else if (dtpkr_start.getValue() == null)
				alert.setContentText("Bitte geben Sie ein Startdatum an!");
			else if (dtpkr_end.getValue() == null)
				alert.setContentText("Bitte geben Sie eine Enddatum an!");
			alert.showAndWait();
			// Überprüft ob der Namen bereits verwendet wurde
		} else if (!phasen.parallelStream().anyMatch(p -> p.getName().equals(txt_phase.getText()))) {
			Phase phase = new Phase(txt_phase.getText(), dtpkr_start.getValue().toString(),
					dtpkr_end.getValue().toString());
			projekt.setSinglePhase(phase);
			phasen.add(phase);
			tbl_phase.setItems(phasen);
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Der angegebene Phasenname ist bereits vorhanden!");
			alert.showAndWait();
		}
	}

	/**
	 * Aktualisiert die Phasen Tabelle
	 */
	public void aktualisierePhasen() {
		// Workaround um die Tabelle zu aktualisieren
		tbl_phase.getColumns().get(0).setVisible(false);
		tbl_phase.getColumns().get(0).setVisible(true);
	}

	/**
	 * Actionlistener für das Aufwands Menü.
	 * Setzt die passenden Eingabefelder
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	public void chobx_aufwand_selected(ActionEvent event) throws Exception {
		String chobx_aufwand_selection = chobx_aufwand.getValue();
		switch (chobx_aufwand_selection) {
		case "Personentage (PT)":
			btn_aufwand_festlegen.setVisible(true);
			txt_mak_intern.setVisible(false);
			txt_mak_extern.setVisible(false);
			txt_mak_pt_intern.setVisible(false);
			txt_mak_pt_extern.setVisible(false);
			txt_pt_intern.setVisible(true);
			txt_pt_extern.setVisible(true);
			lbl_Intern.setVisible(true);
			lbl_Extern.setVisible(true);
			break;
		case "Mitarbeiterkapazität (MAK)":
			btn_aufwand_festlegen.setVisible(true);
			txt_pt_intern.setVisible(false);
			txt_pt_extern.setVisible(false);
			txt_mak_intern.setVisible(true);
			txt_mak_extern.setVisible(true);
			txt_mak_pt_intern.setVisible(true);
			txt_mak_pt_extern.setVisible(true);
			lbl_Intern.setVisible(true);
			lbl_Extern.setVisible(true);
			break;
		}

		if (indexPhaseClicked && indexKompetenzClicked) {
			fülleFelder();
		}
	}

	/**
	 * Actionlistener für den Enddatum Datepicker
	 * 
	 * @param event
	 * @return
	 * @throws Exception
	 */
	@FXML
	public boolean datepicker_ende_selected(ActionEvent event) throws Exception {
		// Überprüft ob das Enddatum vor dem Startdatum liegt
		if (dtpkr_start.getValue() != null && dtpkr_end.getValue() != null) {
			int startDatum = Integer.parseInt(dtpkr_start.getValue().toString().replaceAll("-", ""));
			int endDatum = Integer.parseInt(dtpkr_end.getValue().toString().replaceAll("-", ""));
			if (endDatum <= startDatum) {
				dtpkr_end.setValue(dtpkr_start.getValue().plusDays(1));
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Das Enddatum darf nicht gleich wie das Startdatum sein oder davor liegen.");
				alert.showAndWait();
				return true;
			} else
				return false;
		} else
			return false;
	}

	/**
	 * Actionlistener für den Aufwand festlegen Button
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	public void btn_aufwand_festlegen_click(ActionEvent event) throws Exception {
		double ptIntern = 0;
		double ptExtern = 0;
		int auswahl = chobx_aufwand.getSelectionModel().getSelectedIndex();

		// Überprüft ob alle Felder befüllt wurden
		if ((auswahl == 0 && (txt_pt_intern.getText().trim().isEmpty() || txt_pt_extern.getText().trim().isEmpty()))
				|| (auswahl == 1
						&& (txt_mak_intern.getText().isEmpty() || txt_mak_extern.getText().trim().isEmpty()))) {
			Alert alert = new Alert(AlertType.ERROR);
			if (auswahl == 0 && txt_pt_intern.getText().trim().isEmpty())
				alert.setContentText("Bitte geben Sie die Internen PT an!");
			else if (auswahl == 0 && txt_pt_extern.getText().trim().isEmpty())
				alert.setContentText("Bitte geben Sie die Externen PT an!");
			else if (auswahl == 1 && txt_mak_intern.getText().isEmpty())
				alert.setContentText("Bitte geben Sie die internen MAK an!");
			else if (auswahl == 1 && txt_mak_extern.getText().trim().isEmpty())
				alert.setContentText("Bitte geben Sie die externen MAK an!");
			alert.showAndWait();
		} else {
			try {
				int phasenIndex = tbl_phase.getSelectionModel().getSelectedIndex();
				Kompetenz kompetenzSelected = tbl_kompetenz.getSelectionModel().getSelectedItem();

				// Setze PT anhand der getroffenen Auswahl
				switch (auswahl) {
				case 0:
					// Entfernt Minuszeichen und macht aus einem , ein .
					ptIntern = Double.parseDouble(txt_pt_intern.getText().replace(",", ".").replace("-", ""));
					ptExtern = Double.parseDouble(txt_pt_extern.getText().replace(",", ".").replace("-", ""));
					break;
				case 1:
					// Berechnung der PT: MAK * verfügbare Werktage der Phase
					// Entfernt Minuszeichen und macht aus einem , ein .
					ptIntern = Double.parseDouble(txt_mak_intern.getText().replace(",", ".").replace("-", "")) * arbeitstage;
					ptExtern = Double.parseDouble(txt_mak_extern.getText().replace(",", ".").replace("-", "")) * arbeitstage;
					break;
				}

				// Setzt neue Aufwände anhand der Zugehörigkeit falls noch nicht geschehen
				if (!projekt.getPhasen().get(phasenIndex).getAufwände().stream()
						.anyMatch(obj -> obj.getZugehoerigkeit().equals(kompetenzSelected.getName()))) {
					projekt.getPhasen().get(phasenIndex)
							.setSingleAufwand(new Aufwand("intern", kompetenzSelected.getName(), ptIntern));
					projekt.getPhasen().get(phasenIndex)
							.setSingleAufwand(new Aufwand("extern", kompetenzSelected.getName(), ptExtern));
				} else {
					// Setzt die angegebenen PT in den passenden Aufwänden
					for (int i = 0; i < projekt.getPhasen().get(phasenIndex).getAufwände().size(); i++) {

						if (projekt.getPhasen().get(phasenIndex).getAufwände().get(i).getName().equals("intern")
								&& projekt.getPhasen().get(phasenIndex).getAufwände().get(i).getZugehoerigkeit()
										.equals(kompetenzSelected.getName())) {
							projekt.getPhasen().get(phasenIndex).getAufwände().get(i).setPt(ptIntern);
						}

						if (projekt.getPhasen().get(phasenIndex).getAufwände().get(i).getName().equals("extern")
								&& projekt.getPhasen().get(phasenIndex).getAufwände().get(i).getZugehoerigkeit()
										.equals(kompetenzSelected.getName())) {
							projekt.getPhasen().get(phasenIndex).getAufwände().get(i).setPt(ptExtern);
						}
					}
				}

				kompetenzen = FXCollections.observableArrayList(projekt.getKompetenzen());
				phasen = FXCollections.observableArrayList(projekt.getPhasen());

				checkChanges();
				btn_aufwand_festlegen.setDisable(true);
			} catch (Exception e) {
				if (Configuration.DEBUG)
					System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * Speichert das Projekt außerhalb der geöffneten View
	 */
	public static void saveProjektRemote() {
		Datenbank db = new Datenbank();
		db.updateProjekt(projekt);
	}

	/**
	 * Actionlistener für den Projekt speichern Button
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	public void btn_projekt_speichern_click(ActionEvent event) throws Exception {

		Node source = (Node) event.getSource();
		Scene scene = source.getScene();

		try {
			/*
			 * Speichervorgang wird in einem neuen Thread ausgeführt
			 * um die Anwendung nicht einzufrieren
			 */
			new Thread(new Runnable() {
				@Override
				public void run() {
					img_saveBtnImg.setVisible(true);
					btn_projekt_speichern.setDisable(true);
					myDB.updateProjekt(projekt);
					btn_projekt_speichern.setDisable(false);
					img_saveBtnImg.setVisible(false);
				}
			}).start();
			somethingChanged = false;
			Stage stage = (Stage) scene.getWindow();
			stage.setTitle(projekt.getName());
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Speichern fehlgeschlagen!");
			alert.showAndWait();
		}
	}

	/**
	 * Eventlistener für den Projekt melden Button
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	public void btn_sendProjekt_click(ActionEvent event) throws Exception {
		
		// Überprüft ob das Meldedatum gesetzt wurde
		if (dtpkr_meldeDatum.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Ungültiges Meldedatum");
			alert.setContentText("Bitte geben Sie ein Meldedatum an!");
			alert.showAndWait();
		} else {
			Node source = (Node) event.getSource();
			Scene scene = source.getScene();
			Stage stage = (Stage) scene.getWindow();

			// Öffne einen Speichern Dialog und übergebe den Pfad an die Export Klasse
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Projektdaten exportieren");
			fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("xlsx", "*.xlsx"));
			File file = fileChooser.showSaveDialog(stage);

			if (file != null) {
				// Exportiere die Datei in einem neuen Thread
				new Thread(new Runnable() {
					@Override
					public void run() {
						btn_sendProjekt.setDisable(true);
						img_sendBtnImg.setVisible(true);
						projekt.setgemeldet(true);
						projekt.setMeldeDatum(dtpkr_meldeDatum.getValue().toString());
						Excel.ExportToExcel(projekt, file.getAbsolutePath());
						try {
							btn_projekt_speichern_click(event);
						} catch (Exception e) {
							if (Configuration.DEBUG)
								System.out.println(e.getMessage());
						}
						img_sendBtnImg.setVisible(false);
						btn_sendProjekt.setDisable(false);
					}
				}).start();
			}
		}
	}

	/**
	 * Eventlistener für den Zurück Button
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	public void btn_zurueck_click(ActionEvent event) throws Exception {

		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();

		// Überprüft ob Daten verändert wurden
		if (somethingChanged) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setContentText("Bitte wählen Sie eine der folgenden Auswahlmöglichkeiten!");

			ButtonType buttonTypeOne = new ButtonType("Speichern & Verlassen");
			ButtonType buttonTypeTwo = new ButtonType("Ohne speichern verlassen");
			ButtonType buttonTypeCancel = new ButtonType("Abbrechen", ButtonData.CANCEL_CLOSE);

			alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == buttonTypeOne) {
				myDB.updateProjekt(projekt);
				new OpenStartPage();
				stage.close();
			} else if (result.get() == buttonTypeTwo) {
				new OpenStartPage();
				stage.close();
			}
		} else {
			new OpenStartPage();
			stage.close();
		}
	}

	/**
	 * Berechne die Anzahl der Arbeitstage zwischen zwei Daten
	 * 
	 * @param startDatum
	 * @param endDatum
	 * @return
	 */
	public long calculateDate(String startDatum, String endDatum) {

		LocalDate start = LocalDate.parse(startDatum);
		LocalDate ende = LocalDate.parse(endDatum);
		double personentage = 0;
		long daysBetween = 0;

		while (!start.getMonth().equals(ende.getMonth())) {
			int monatslaenge = start.getMonth().maxLength();
			Month startMonat = start.getMonth();
			daysBetween = monatslaenge - start.getDayOfMonth();
			daysBetween++; // Ein Tag mehr, damit EndDatum inklusive ist
			// Je nach Länge des Monats wird anderer Faktor benötigt
			// Februar wird immer mit 29T gerechnet.
			switch (monatslaenge) {
			case 29:
				personentage += 0.586 * daysBetween;
				break;
			case 30:
				personentage += 0.567 * daysBetween;
				break;
			case 31:
				personentage += 0.548 * daysBetween;
				break;
			}

			// Datum bis zum 1. Tag des nächsten Monats verändern
			while (startMonat.equals(start.getMonth())) {
				start = start.plusDays(1);
			}
		}

		// Berechnung der restlichen Tage
		daysBetween = ende.getDayOfMonth() - start.getDayOfMonth();
		daysBetween++;
		int monatslaenge = start.getMonth().maxLength();
		// Je nach Länge des Monats wird anderer Faktor benötigt
		// Februar wird immer mit 29T gerechnet.
		switch (monatslaenge) {
		case 29:
			personentage += 0.586 * daysBetween;
			break;
		case 30:
			personentage += 0.567 * daysBetween;
			break;
		case 31:
			personentage += 0.548 * daysBetween;
			break;
		}
		return Math.round(personentage);
	}

	/**
	 * Actionlistener für den Export Button.
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	public void btn_export_click(ActionEvent event) throws Exception {
		Node source = (Node) event.getSource();
		Scene scene = source.getScene();
		Stage stage = (Stage) scene.getWindow();

		// Öffne einen SPeichern Dialog und übergebe den Pfad an die Export Klasse
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Projektdaten exportieren");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("xlsx", "*.xlsx"));
		File file = fileChooser.showSaveDialog(stage);

		// Exprtiere die Daten in einem neuen Thread
		if (file != null) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					btn_export.setDisable(true);
					img_exportBtnImg.setVisible(true);
					Excel.ExportToExcel(projekt, file.getAbsolutePath());
					img_exportBtnImg.setVisible(false);
					btn_export.setDisable(false);
				}
			}).start();
		}
	}

	/**
	 * Actionlistener für den Phase löschen Button
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	public void btn_deletePhase_click(ActionEvent event) throws Exception {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setContentText("Möchten Sie die Phase: " + tbl_phase.getSelectionModel().getSelectedItem().getName()
				+ " wirklich löschen?");
		ButtonType buttonTypeOne = new ButtonType("Löschen");
		ButtonType buttonTypeCancel = new ButtonType("Abbrechen", ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == buttonTypeOne) {
			try {
				projekt.getPhasen().remove(tbl_phase.getSelectionModel().getSelectedIndex());
				phasen.remove(tbl_phase.getSelectionModel().getSelectedIndex());

				// Falls es keine Phasen mehr gibt soll der löschen Button
				// deaktiviert werden
				if (tbl_phase.getItems().isEmpty())
					btn_deletePhase.setDisable(true);

				checkChanges();
			} catch (Exception e) {
				if (Configuration.DEBUG)
					System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * Actionlistener für den Kompetenz löschen Button
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	public void btn_deleteKompetenz_click(ActionEvent event) throws Exception {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setContentText("Möchten Sie die Kompetenz: "
				+ tbl_kompetenz.getSelectionModel().getSelectedItem().getName() + " wirklich löschen?");
		ButtonType buttonTypeOne = new ButtonType("Löschen");
		ButtonType buttonTypeCancel = new ButtonType("Abbrechen", ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == buttonTypeOne) {
			try {
				// Lösche die Aufwände in den Phasen mit der Zugehörigkeit zur
				// Kompetenz
				for (int i = 0; i < projekt.getPhasen().size(); i++) {
					projekt.getPhasen().get(i).getAufwände().removeIf(a -> a.getZugehoerigkeit()
							.equals(tbl_kompetenz.getSelectionModel().getSelectedItem().getName()));
				}
				projekt.getKompetenzen().remove(tbl_kompetenz.getSelectionModel().getSelectedIndex());
				kompetenzen.remove(tbl_kompetenz.getSelectionModel().getSelectedIndex());

				// Falls keine Kompetenzrn mehr vorhanden sind wird der Button wieder diabled
				if (tbl_kompetenz.getItems().isEmpty())
					btn_deleteKompetenz.setDisable(true);

				checkChanges();

			} catch (Exception e) {
				if (Configuration.DEBUG)
					System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * Befüllt die PT Textfelder
	 */
	public void fülleFelder() {
		try {

			txt_pt_intern.setText("0,0");
			txt_pt_extern.setText("0,0");
			txt_mak_intern.setText("0,0");
			txt_mak_extern.setText("0,0");

			// Durchsuche die Aufwände nach dem Passenden Aufwand und schreib
			// ihn in die Textfelder
			tbl_phase.getSelectionModel().getSelectedItem().getAufwände().stream().filter(
					a -> a.getZugehoerigkeit().equals(tbl_kompetenz.getSelectionModel().getSelectedItem().getName()))
					.forEach(a -> {
						if (a.getName().equals("intern")) {
							txt_pt_intern.setText(String.valueOf(a.getPt()).replace(".", ","));
							txt_mak_intern.setText(String.valueOf(a.getPt() / arbeitstage).replace(".", ","));
						} else {
							txt_pt_extern.setText(String.valueOf(a.getPt()).replace(".", ","));
							txt_mak_extern.setText(String.valueOf(a.getPt() / arbeitstage).replace(".", ","));
						}
					});
		} catch (Exception e) {
			if (Configuration.DEBUG)
				System.out.println(e.getMessage());
		}
	}

	/**
	 * Überprüft Änderungen
	 */
	public void checkChanges() {
		somethingChanged = true;
		Scene scene = (Scene) txt_pt_extern.getScene();
		Stage stage = (Stage) scene.getWindow();
		stage.setTitle("* " + projekt.getName());
	}
}