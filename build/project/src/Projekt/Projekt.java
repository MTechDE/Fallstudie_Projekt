package Projekt;

import java.util.ArrayList;
import java.util.List;

import Projekt.Aufwand;


/**
 * Ein Projekt-Objekt enthält neben den Projektdaten, die dazu gehörenden
 * Kompetenzen, Phasen und Beteiligten Personen.
 * @author Daniel Sogl
 *
 */
public class Projekt {
	private List<Aufwand> aufwände = new ArrayList<Aufwand>();
	private List<Phase> phasen = new ArrayList<Phase>();
	private List<Kompetenz> kompetenzen = new ArrayList<Kompetenz>();
	private boolean abgeschickt;
	private String name;
	private String ersteller;
	private String startDate;
	private String endDate;
	

	public Projekt(String name, String ersteller, boolean abgeschickt) {
		this.setName(name);
		this.setErsteller(ersteller);
		this.setAbgeschickt(abgeschickt);
	}
	
	public Projekt(String name, String ersteller, boolean abgeschickt, String startDate, String endDate) {
		this.setName(name);
		this.setErsteller(ersteller);
		this.setAbgeschickt(abgeschickt);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Aufwand> getAufwände() {
		return (ArrayList<Aufwand>) aufwände;
	}

	public void setAufwände(ArrayList<Aufwand> aufwände) {
		this.aufwände = aufwände;
	}
	
	public void setSingleAufwand(Aufwand aufwand){
		this.aufwände.add(aufwand);
	}

	public ArrayList<Phase> getPhasen() {
		return (ArrayList<Phase>) phasen;
	}

	public void setPhasen(ArrayList<Phase> phasen) {
		this.phasen = phasen;
	}
	
	public void setSinglePhase(Phase phase){
		this.phasen.add(phase);
	}

	public String getErsteller() {
		return ersteller;
	}

	public void setErsteller(String ersteller) {
		this.ersteller = ersteller;
	}

	public boolean isAbgeschickt() {
		return abgeschickt;
	}

	public void setAbgeschickt(boolean abgeschickt) {
		this.abgeschickt = abgeschickt;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<Kompetenz> getKompetenzen() {
		return kompetenzen;
	}

	public void setKompetenzen(List<Kompetenz> kompetenzen) {
		this.kompetenzen = kompetenzen;
	}
}
