package Projekt;

import java.util.ArrayList;
import java.util.List;

import Projekt.Aufwand;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * In einem Kompetenzen Objekt werden die beteiligten Personen abgespeichert.
 * @author Daniel Sogl
 */
public class Kompetenz {

	private StringProperty name;
	private List<Aufwand> aufwände = new ArrayList<Aufwand>();
	
	public Kompetenz(String name){
		this.setName(name);
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

	public List<Aufwand> getAufwände() {
		return aufwände;
	}

	public void setAufwände(List<Aufwand> aufwände) {
		this.aufwände = aufwände;
	}
	
	public void setSingleAufwand(Aufwand aufwand){
		this.aufwände.add(aufwand);
	}
	
	
}
