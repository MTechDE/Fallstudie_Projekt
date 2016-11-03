package model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Aufwand;

/**
 * Speichert Phasen
 * 
 * @author Daniel Sogl
 */
public class Phase {
	private StringProperty name;
	private StringProperty startDate;
	private StringProperty endDate;
	private List<Aufwand> aufwand;

	/**
	 * Standardkonstruktor
	 * 
	 * @param name Name der Phase
	 * @param startDate Startdatum der Phase
	 * @param endDate Enddatum der Phase
	 */
	public Phase(String name, String startDate, String endDate) {
		this.setName(name);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
		aufwand = new ArrayList<Aufwand>();
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
	 * @param startDate Startdatum der Phase
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
	 * @param endDate Enddatum der Phase
	 */
	public void setEndDate(String endDate) {
		this.endDate = new SimpleStringProperty(endDate);
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
	 * @param name Name der Phase
	 */
	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}

	/**
	 * 
	 * @return List Aufwands-Liste
	 */
	public List<Aufwand> getAufwände() {
		return aufwand;
	}

	/**
	 * 
	 * @param aufwand Liste von Aufwänden
	 */
	public void setAufwände(List<Aufwand> aufwand) {
		this.aufwand = aufwand;
	}

	/**
	 * 
	 * @param aufwand Einzelner Aufwand
	 */
	public void setSingleAufwand(Aufwand aufwand) {
		this.aufwand.add(aufwand);
	}
}
