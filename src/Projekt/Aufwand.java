package Projekt;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Ein Personen Objekt beinhaltet alle Personen spezifischen Daten.
 * 
 * @author Daniel Sogl
 */

public class Aufwand {

	private StringProperty name;
	private StringProperty zugehoerigkeit;
	private DoubleProperty pt;
	private DoubleProperty anwesenheit;

	public Aufwand(String name) {
		this.setName(name);
		this.setPt(0);
	}

	public Aufwand(String name, String zugehoerigkeit, double pt, Double anwesenheit) {
		this.setName(name);
		this.setZugehoerigkeit(zugehoerigkeit);
		this.setPt(pt);
		this.setAnwesenheit(anwesenheit);
	}

	public String getName() {
		return name.get();
	}

	public StringProperty nameProperty() {
		return name;
	}

	public void setName(String rolle) {
		this.name = new SimpleStringProperty(rolle);
	}

	public String getZugehoerigkeit() {
		return zugehoerigkeit.get();
	}

	public StringProperty zugehoerigkeitProperty() {
		return zugehoerigkeit;
	}

	public void setZugehoerigkeit(String zugehoerigkeit) {
		this.zugehoerigkeit = new SimpleStringProperty(zugehoerigkeit);
	}

	public double getPt() {
		return pt.get();
	}

	public DoubleProperty ptProperty() {
		return pt;
	}

	public void setPt(double pt) {
		this.pt = new SimpleDoubleProperty(pt);
	}
	
	public Double getAnwesenheit() {
		return anwesenheit.get();
	}

	public DoubleProperty getAnwesenheitProperty() {
		return anwesenheit;
	}

	public void setAnwesenheit(Double anwesenheit) {
		this.anwesenheit = new SimpleDoubleProperty(anwesenheit);
	}
}
