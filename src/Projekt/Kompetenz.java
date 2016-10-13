package Projekt;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * In einem Kompetenzen Objekt wird die Tätigkeitsbezeichnung abgespeichert.
 * 
 * @author Daniel Sogl
 */
public class Kompetenz {

	private StringProperty name;

	public Kompetenz(String name) {
		this.setName(name);
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
}
