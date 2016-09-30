package Projekt;

/**
 * @author Daniel Sogl
 */

public class Person {

	private String name;
	private String zugehoerigkeit;
	private double pt;
	private double mak;
	private double preisPT;
	private boolean intern;
	
	public Person(String name){
		this.setName(name);
		this.setPt(0);
		this.setMak(0.0);
		this.setPreisPT(0.0);
		this.setIntern(true);
	}

	public Person(String name, boolean intern) {
		this.setName(name);
		this.setPt(0);
		this.setMak(0.0);
		this.setPreisPT(0.0);
		this.setIntern(intern);
	}
	
	public Person(String name, String zugehoerigkeit, double pt, double mak, double preisPT, boolean intern) {
		this.setName(name);
		this.setZugehoerigkeit(zugehoerigkeit);
		this.setPt(pt);
		this.setMak(mak);
		this.setPreisPT(preisPT);
		this.setIntern(intern);
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

	public double getMak() {
		return mak;
	}

	public void setMak(double mak) {
		this.mak = mak;
	}

	public double getPreisPT() {
		return preisPT;
	}

	public void setPreisPT(double preisPT) {
		this.preisPT = preisPT;
	}

	public boolean isIntern() {
		return intern;
	}

	public void setIntern(boolean intern) {
		this.intern = intern;
	}

}
