package Projekt;

import java.util.ArrayList;
import java.util.List;

import Projekt.Person;

/**
 * @author Daniel Sogl
 */

/*
 * Eine Phase enthaelt den Namen sowie das Start- und Enddatum Eine Phase wird
 * später wiederum einem Projekt zugeteilt.
 */
public class Phase {
	private String name;
	private String startDate;
	private String endDate;
	private List<Person> personen = new ArrayList<Person>();

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

	public List<Person> getPersonen() {
		return personen;
	}

	public void setPersonen(List<Person> personen) {
		this.personen = personen;
	}
	
	public void setSinglePerson(Person person){
		this.personen.add(person);
	}
}
