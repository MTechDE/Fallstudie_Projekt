

import java.util.ArrayList;
import java.util.List;

import Projekt.Aufwand;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Eine Phase enthält den Namen, sowie das Start- und Enddatum.
 * Sowie eine Liste aller darin eingeteilter Personen.
 * @author Daniel Sogl
 */
public class Phase {
	private StringProperty name;
	private StringProperty startDate;
	private StringProperty endDate;
	private List<Aufwand> aufwand;

	public Phase(String name, String startDate, String endDate) {
		this.setName(name);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
		aufwand = new ArrayList<Aufwand>();
	}

	public String getStartDate() {
		return startDate.get();
	}
	
	public StringProperty startDateProperty(){
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = new SimpleStringProperty(startDate);
	}

	public String getEndDate() {
		return endDate.get();
	}
	
	public StringProperty endDateProperty(){
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = new SimpleStringProperty(endDate);
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
		return aufwand;
	}

	public void setAufwände(List<Aufwand> aufwand) {
		this.aufwand = aufwand;
	}
	
	public void setSingleAufwand(Aufwand aufwand){
		this.aufwand.add(aufwand);
	}
}
