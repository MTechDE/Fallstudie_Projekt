package Projekt;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * In einem Kompetenzen Objekt werden die beteiligten Personen abgespeichert.
 * 
 * @author Daniel Sogl
 */
public class Kompetenz {

	private StringProperty name;
	private DoubleProperty risikozuschlag;

	public Kompetenz(String name) {
		this.setName(name);
		this.setRisikozuschlag(0.0);
	}
	
	public Kompetenz(String name, Double risikozuschlag) {
		this.setName(name);
		this.setRisikozuschlag(risikozuschlag);
	}

	public String getName() {
		return name.get();
	}

	public StringProperty nameProperty() {
		return name;
	}

	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}

	public Double getRisikozuschlag() {
		return risikozuschlag.get();
	}
	
	public DoubleProperty risikozuschlagPropert(){
		return risikozuschlag;
	}

	public void setRisikozuschlag(Double risikozuschlag) {
		this.risikozuschlag = new SimpleDoubleProperty(risikozuschlag);
	}
}
