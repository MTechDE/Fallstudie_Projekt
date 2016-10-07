package Projekt;

import java.util.ArrayList;
import java.util.List;

import Projekt.Aufwand;

/**
 * Eine Phase enthält den Namen, sowie das Start- und Enddatum.
 * Sowie eine Liste aller darin eingeteilter Personen.
 * @author Daniel Sogl
 */
public class Phase {
	private String name;
	private String startDate;
	private String endDate;
	private List<Aufwand> aufwand = new ArrayList<Aufwand>();

	public Phase(String name, String startDate, String endDate) {
		this.setName(name);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Aufwand> getAufwände() {
		return aufwand;
	}

	public void setAufwände(List<Aufwand> aufwand) {
		this.aufwand = aufwand;
	}
	
	public void setSingleAufwand(Aufwand aufwand){
		this.aufwand.add(aufwand);
	}
}
