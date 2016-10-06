package Projekt;

/**
 * Ein Personen Objekt beinhaltet alle Personen spezifischen Daten.
 * @author Daniel Sogl
 */

public class Aufwand {

	private String name;
	private String zugehoerigkeit;
	private double pt;
	private double risiko;
	private boolean intern;
	
	public Aufwand(String name){
		this.setName(name);
		this.setPt(0);
		this.setIntern(true);
		this.setRisiko(0);
	}

	public Aufwand(String name, boolean intern) {
		this.setName(name);
		this.setPt(0);
		this.setIntern(intern);
		this.setRisiko(0);
	}
	
	public Aufwand(String name, String zugehoerigkeit, double pt, boolean intern, double risiko) {
		this.setName(name);
		this.setZugehoerigkeit(zugehoerigkeit);
		this.setPt(pt);
		this.setIntern(intern);
		this.setRisiko(risiko);
	}

	public String getName() {
		return name;
	}

	public void setName(String rolle) {
		this.name = rolle;
	}

	public String getZugehoerigkeit() {
		return zugehoerigkeit;
	}

	public void setZugehoerigkeit(String zugehoerigkeit) {
		this.zugehoerigkeit = zugehoerigkeit;
	}

	public double getPt() {
		return pt;
	}

	public void setPt(double pt) {
		this.pt = pt;
	}

	public boolean isIntern() {
		return intern;
	}

	public void setIntern(boolean intern) {
		this.intern = intern;
	}

	public double getRisiko() {
		return risiko;
	}

	public void setRisiko(double risiko) {
		this.risiko = risiko;
	}

}
