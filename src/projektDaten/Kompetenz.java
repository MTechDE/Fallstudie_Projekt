package projektDaten;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Speichert Kompetenzen
 * 
 * @author Daniel Sogl
 */
public class Kompetenz {

	private StringProperty name;
	private DoubleProperty risikozuschlag;

	/**
	 * Standardkonstruktor
	 * 
	 * @param name Name der Kompetenz
	 * @param risikozuschlag Risikozuschlag der Kompetenz
	 */
	public Kompetenz(String name, Double risikozuschlag) {
		this.setName(name);
		this.setRisikozuschlag(risikozuschlag);
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
	 * @param name Name der Kompetenz
	 */
	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}

	/**
	 * 
	 * @return Double
	 */
	public Double getRisikozuschlag() {
		return risikozuschlag.get();
	}

	/**
	 * 
	 * @return DoubleProperty
	 */
	public DoubleProperty risikozuschlagPropert() {
		return risikozuschlag;
	}

	/**
	 * 
	 * @param risikozuschlag Risikozuschlag der Kompetenz
	 */
	public void setRisikozuschlag(Double risikozuschlag) {
		this.risikozuschlag = new SimpleDoubleProperty(risikozuschlag);
	}
}
