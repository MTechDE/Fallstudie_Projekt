package model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Bildet ein Projekt ab und speichert Phasen
 * sowie Kompetenzen.
 * 
 * @author Daniel Sogl
 *
 */
public class Projekt {
	private List<Phase> phasen = new ArrayList<Phase>();
	private List<Kompetenz> kompetenzen = new ArrayList<Kompetenz>();
	private BooleanProperty gemeldet;
	private StringProperty name;
	private StringProperty ersteller;
	private StringProperty startDate;
	private StringProperty endDate;
	private StringProperty meldeDatum;
	
	
	public Projekt(String name){
		this.setName(name);
	}

	/**
	 * Konstruktor um die Grundinformationen eines Projektes abbilden zu können
	 * 
	 * @param name Name des Projektes
	 * @param ersteller Ersteller des Projektes
	 * @param gemeldet Ob das projekt gemeldet wurde
	 */
	public Projekt(String name, String ersteller, boolean gemeldet) {
		this.setName(name);
		this.setErsteller(ersteller);
		this.setgemeldet(gemeldet);
		this.setStartDate(null);
		this.setEndDate(null);
		this.setMeldeDatum(null);
	}

	/**
	 * Konstruktor welcher von der Datenbankklasse aufgerufen wird
	 * @param name Name des Projektes
	 * @param ersteller Ersteller des Projektes
	 * @param gemeldet Ob das projekt gemeldet wurde
	 * @param startDate Startdatum des Projektes
	 * @param endDate Enddatum des Projektes
	 */
	public Projekt(String name, String ersteller, boolean gemeldet, String startDate, String endDate, String meldeDatum) {
		this.setName(name);
		this.setErsteller(ersteller);
		this.setgemeldet(gemeldet);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
		this.setPhasen(null);
		this.setKompetenzen(null);
		this.setMeldeDatum(meldeDatum);
	}

	/**
	 * 
	 * @return String
	 */
	public String getName() {
		return name.get();
	}

	/**
	 * 
	 * @return StringProperty
	 */
	public StringProperty nameProperty() {
		return name;
	}

	/**
	 * 
	 * @param name Name des Projektes
	 */
	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}

	/**
	 *
	 * @return ArrayList Phasen-ArrayListe
	 */
	public ArrayList<Phase> getPhasen() {
		return (ArrayList<Phase>) phasen;
	}

	/**
	 * 
	 * @param phasen Liste von Phasen
	 */
	public void setPhasen(List<Phase> phasen) {
		this.phasen = phasen;
	}

	/**
	 * 
	 * @param phase Eine einzelne Phase
	 */
	public void setSinglePhase(Phase phase) {
		this.phasen.add(phase);
	}

	/**
	 * 
	 * @return String
	 */
	public String getErsteller() {
		return ersteller.get();
	}

	/**
	 * 
	 * @return StringProperty
	 */
	public StringProperty erstellerProperty() {
		return ersteller;
	}

	/**
	 * 
	 * @param ersteller Ersteller des Projektes
	 */
	public void setErsteller(String ersteller) {
		this.ersteller = new SimpleStringProperty(ersteller);
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isgemeldet() {
		return gemeldet.get();
	}

	/**
	 * 
	 * @return StringProperty
	 */
	public StringProperty gemeldetProperty() {
		if (this.isgemeldet())
			return new SimpleStringProperty("Ja");
		else
			return new SimpleStringProperty("Nein");
	}

	/**
	 * 
	 * @param gemeldet gemeldet Status des Projektes
	 */
	public void setgemeldet(boolean gemeldet) {
		this.gemeldet = new SimpleBooleanProperty(gemeldet);
	}

	/**
	 * 
	 * @return String
	 */
	public String getStartDate() {
		return startDate.get();
	}

	/**
	 * 
	 * @return StringProperty
	 */
	public StringProperty startDateProperty() {
		return startDate;
	}

	/**
	 * 
	 * @param startDate Startdatum des Projektes
	 */
	public void setStartDate(String startDate) {
		this.startDate = new SimpleStringProperty(startDate);
	}

	/**
	 * 
	 * @return String
	 */
	public String getEndDate() {
		return endDate.get();
	}

	/**
	 * 
	 * @return StringProperty
	 */
	public StringProperty endDateProperty() {
		return endDate;
	}

	/**
	 * 
	 * @param endDate Enddatum des Projektes
	 */
	public void setEndDate(String endDate) {
		this.endDate = new SimpleStringProperty(endDate);
	}

	/**
	 * 
	 * @return List Kompetenz-Liste
	 */
	public List<Kompetenz> getKompetenzen() {
		return kompetenzen;
	}

	/**
	 * 
	 * @param kompetenzen Liste von Kompetenzen
	 */
	public void setKompetenzen(List<Kompetenz> kompetenzen) {
		this.kompetenzen = kompetenzen;
	}

	/**
	 * 
	 * @param kompetenz Einzelne Kompetenz
	 */
	public void setSingleKompetenz(Kompetenz kompetenz) {
		this.kompetenzen.add(kompetenz);
	}

	public StringProperty getMeldeDatumProperty() {
		return meldeDatum;
	}
	
	public String getMeldeDatum() {
		return meldeDatum.get();
	}

	public void setMeldeDatum(String meldeDatum) {
		this.meldeDatum = new SimpleStringProperty(meldeDatum);
	}
}
