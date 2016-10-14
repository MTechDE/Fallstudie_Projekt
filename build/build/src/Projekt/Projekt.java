package Projekt;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Ein Projekt-Objekt enthält neben den Projektdaten, die dazu gehörenden
 * Kompetenzen, Phasen und Beteiligten Personen.
 * @author Daniel Sogl
 *
 */
public class Projekt {
	private List<Phase> phasen = new ArrayList<Phase>();
	private List<Kompetenz> kompetenzen = new ArrayList<Kompetenz>();
	private BooleanProperty abgeschickt;
	private StringProperty  name;
	private StringProperty ersteller;
	private StringProperty startDate;
	private StringProperty endDate;

	public Projekt(String name, String ersteller, boolean abgeschickt) {
		this.setName(name);
		this.setErsteller(ersteller);
		this.setAbgeschickt(abgeschickt);
		this.setStartDate(null);
		this.setEndDate(null);
	}
	
	public Projekt(String name, String ersteller, boolean abgeschickt, String startDate, String endDate) {
		this.setName(name);
		this.setErsteller(ersteller);
		this.setAbgeschickt(abgeschickt);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
		this.setPhasen(null);
		this.setKompetenzen(null);
	}

	public String getName() {
		return name.get();
	}
	
	public StringProperty nameProperty(){
		return name;
	}

	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}

	public ArrayList<Phase> getPhasen() {
		return (ArrayList<Phase>) phasen;
	}

	public void setPhasen(List<Phase> phasen) {
		this.phasen = phasen;
	}
	
	public void setSinglePhase(Phase phase){
		this.phasen.add(phase);
	}

	public String getErsteller() {
		return ersteller.get();
	}
	
	public StringProperty erstellerProperty(){
		return ersteller;
	}

	public void setErsteller(String ersteller) {
		this.ersteller = new SimpleStringProperty(ersteller);
	}

	public boolean isAbgeschickt() {
		return abgeschickt.get();
	}
	
	public StringProperty abgeschicktProperty(){
		if(this.isAbgeschickt())
			return new SimpleStringProperty("Ja");
		else
			return new SimpleStringProperty("Nein");
	}

	public void setAbgeschickt(boolean abgeschickt) {
		this.abgeschickt = new SimpleBooleanProperty(abgeschickt);
	}

	public String getStartDate() {
		return startDate.get();
	}
	
	public StringProperty startDateProperty(){
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = new SimpleStringProperty(startDate);
	}

	public String getEndDate() {
		return endDate.get();
	}
	
	public StringProperty endDateProperty(){
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = new SimpleStringProperty(endDate);
	}

	public List<Kompetenz> getKompetenzen() {
		return kompetenzen;
	}

	public void setKompetenzen(List<Kompetenz> kompetenzen) {
		this.kompetenzen = kompetenzen;
	}
	
	public void setSingleKompetenz(Kompetenz kompetenz){
		this.kompetenzen.add(kompetenz);
	}
}
