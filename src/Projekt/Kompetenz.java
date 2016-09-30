package Projekt;

import java.util.ArrayList;
import java.util.List;

import Projekt.Person;

public class Kompetenz {

	private String name;
	private List<Person> personen = new ArrayList<Person>();
	
	public Kompetenz(String name){
		this.setName(name);
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
