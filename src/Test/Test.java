package Test;

import java.util.ArrayList;
import java.util.List;

import Datenbank.Datenbank;
import Projekt.Aufwand;
import Projekt.Kompetenz;
import Projekt.Phase;
import Projekt.Projekt;

/**
 * In dieser Klasse sollen alle eigene Tests programmiert werden.
 * @author Daniel Sogl
 *
 */

public class Test {

	static List<Projekt> projekte = new ArrayList<Projekt>();
	static List<Aufwand> aufwände = new ArrayList<Aufwand>();
	static List<Phase> phasen = new ArrayList<Phase>();
	static List<Kompetenz> kompetenzen = new ArrayList<Kompetenz>();
	static Datenbank myDB = new Datenbank();

	public static void main(String[] args) throws InterruptedException {
		
		Test2();
//		Test3();
//		Test4();
//		Test5();
//		Test6();
//		Test7();
//		Test8();
//		DeleteAllData();
		System.out.println("Tests Beendet");

	}


	public static void Test2() {
		System.out.println("Test 2");
		System.out.println("======");
		Projekt projekt = new Projekt("Projekt 1", "Daniel", false);
		Kompetenz k1 = new Kompetenz("Kompetenz 1");
		
		Phase p1 = new Phase("Phase 1", "2016-01-01", "2016-01-02" , 0.0);
		p1.setSingleAufwand(new Aufwand("Intern"));
		p1.setSingleAufwand(new Aufwand("Extern"));
		
		p1.getAufwände().get(0).setZugehoerigkeit("Kompetenz 1");
		p1.getAufwände().get(0).setPt(100);
		p1.getAufwände().get(1).setZugehoerigkeit("Kompetenz 1");
		p1.getAufwände().get(1).setPt(200);
		
		
		Phase p2 = new Phase("Phase 2", "2016-01-02", "2016-01-03" , 0.0);
		p2.setSingleAufwand(new Aufwand("Intern"));
		p2.setSingleAufwand(new Aufwand("Extern"));
		
		p2.getAufwände().get(0).setZugehoerigkeit("Kompetenz 1");
		p2.getAufwände().get(0).setPt(20);
		p2.getAufwände().get(1).setZugehoerigkeit("Kompetenz 1");
		p2.getAufwände().get(1).setPt(30);
		
		
		projekt.setSingleKompetenz(k1);
		projekt.setSinglePhase(p1);
		projekt.setSinglePhase(p2);
		
		myDB.speicherProjekt(projekt);
	}

	public static void Test3() {
		System.out.println("Test 3");
		System.out.println("======");
		projekte.clear();
		aufwände.clear();
		phasen.clear();

		System.out.println("Lade Projekte aus der DB");

		if (myDB.testConnection()) {
			projekte.add(myDB.getProjekt(new Projekt("Projekt 2", "David", false)));
			projekte.add(myDB.getProjekt(new Projekt("Projekt 1", "Daniel", true)));

			System.out.println("Projekt 2 Startdatum: " + projekte.get(0).getStartDate() + " Enddatum: "
					+ projekte.get(0).getEndDate());
			for (Phase phase : projekte.get(0).getPhasen()) {
				System.out.println(phase.getName());
				for (Aufwand aufwand : phase.getAufwände()) {
					System.out.println(
							aufwand.getName() + " PT: " + aufwand.getPt() + " Phase: " + aufwand.getZugehoerigkeit());
				}
			}

			System.out.println();
			System.out.println("Projekt 1 Startdatum: " + projekte.get(1).getStartDate() + " Enddatum: "
					+ projekte.get(1).getEndDate());
			for (Phase phase : projekte.get(1).getPhasen()) {
				System.out.println(phase.getName());
				for (Aufwand aufwand : phase.getAufwände()) {
					System.out.println(
							aufwand.getName() + " PT: " + aufwand.getPt() + " Phase: " + aufwand.getZugehoerigkeit());
				}
			}
		}
	}

	public static void Test4() {
		System.out.println("Test 4");
		System.out.println("======");
		projekte.clear();
		aufwände.clear();
		phasen.clear();
		System.out.println("Hole Projekt aus der DB und füge eine neue Phase hinzu");

		if (myDB.testConnection()) {
			projekte.add(myDB.getProjekt(new Projekt("Projekt 2", "David", false)));

			for (Phase phase : projekte.get(0).getPhasen()) {
				System.out.println(phase.getName());
				for (Aufwand aufwand : phase.getAufwände()) {
					System.out.println(
							aufwand.getName() + " PT: " + aufwand.getPt() + " Phase: " + aufwand.getZugehoerigkeit());
				}
			}

			phasen = projekte.get(0).getPhasen();
			aufwände = phasen.get(0).getAufwände();
			for (Aufwand aufwand : aufwände) {
				aufwand.setPt(5555);
			}

			Phase phase3 = new Phase("Phase 3", "2011-01-01", "2015-01-01" , 0.0);
			phase3.setAufwände(aufwände);

			for (Aufwand aufwand : phase3.getAufwände()) {
				aufwand.setZugehoerigkeit(phase3.getName());
				System.out.println(aufwand.getName() + " " + aufwand.getZugehoerigkeit() + " " + aufwand.getPt());
			}

			System.out.println("Weise Projekt neue Phase 3 hinzu");
			projekte.get(0).setSinglePhase(phase3);

			System.out.println("Schreibe Projekt in die DB");

			if (myDB.updateProjekt(projekte.get(0)))
				System.out.println("Projekt wurde Geupdated");
			else
				System.out.println("Projekt konnte nicht geupdated werden");

		}
	}

	public static void Test5() {
		System.out.println("Test 5");
		System.out.println("======");
		projekte.clear();
		aufwände.clear();
		phasen.clear();

		System.out.println("Überprüfe die Datenbank auf Projekte");

		if (myDB.testConnection()) {
			if (!myDB.getProjekte().isEmpty())
				System.out.println("Projekte Gefunden: " + myDB.getProjekte().size());
			else
				System.out.println("Keine Projekte angelegt!");
		}
	}

	public static void Test6() {
		System.out.println("Test 6");
		System.out.println("======");
		projekte.clear();
		aufwände.clear();
		phasen.clear();

		System.out.println("Hole Projekt aus der DB und lösche eine Phase und eine Person");
		if (myDB.testConnection()) {
			projekte.add(myDB.getProjekt(new Projekt("Projekt 2", "David", false)));
			projekte.get(0).getPhasen().remove(1);
			projekte.get(0).getPhasen().get(0).getAufwände().remove(0);
		}

		if (myDB.updateProjekt(projekte.get(0)))
			System.out.println("Projekt wurde Geupdated");
		else
			System.out.println("Projekt konnte nicht geupdated werden");
	}

	public static void Test7() {
		System.out.println("Test 6");
		System.out.println("======");
		projekte.clear();
		aufwände.clear();
		phasen.clear();

		System.out.println("Erste Tabelle anhand der Phasen und Personen");

		if (myDB.testConnection()) {
			projekte.add(myDB.getProjekt(new Projekt("Projekt 2", "David", false)));
			projekte.add(myDB.getProjekt(new Projekt("Projekt 1", "Daniel", true)));

		}
	}
	
	public static void Test8(){
		Projekt projekt = new Projekt("Projekt 100", "Daniel", false);
		Kompetenz kompetenz1 = new Kompetenz("Kompetenz 1");
		projekt.setSingleKompetenz(kompetenz1);
		Kompetenz kompetenz2 = new Kompetenz("Kompetenz 1");
		projekt.setSingleKompetenz(kompetenz2);
		
		Aufwand k1Intern = new Aufwand("Intern");
		Aufwand k1Extern = new Aufwand("Extern");
		
		Aufwand k2Intern = new Aufwand("Intern");
		Aufwand k2Extern = new Aufwand("Extern");
		
		Phase phase1 = new Phase("Phase 1", "2016-01-01", "2016-01-02", 0.0);
		Phase phase2 = new Phase("Phase 2", "2016-01-02", "2016-01-03", 0.0);
		
		k1Intern.setZugehoerigkeit(kompetenz1.getName());
		k1Extern.setZugehoerigkeit(kompetenz1.getName());
		k2Intern.setZugehoerigkeit(kompetenz2.getName());
		k2Extern.setZugehoerigkeit(kompetenz2.getName());
		
		phase1.setSingleAufwand(k1Intern);
		phase1.setSingleAufwand(k1Extern);
		
		phase1.setSingleAufwand(k2Intern);
		phase1.setSingleAufwand(k2Extern);
		
		phase2.setSingleAufwand(k1Intern);
		phase2.setSingleAufwand(k1Extern);
		
		phase2.setSingleAufwand(k2Intern);
		phase2.setSingleAufwand(k2Extern);
	}

	public static void zeigeAlleProjekte() {
		for (Projekt projekt : projekte) {
			System.out.println(projekt.getName() + " Ersteller: " + projekt.getErsteller() + " Abgesendet: "
					+ projekt.isAbgeschickt());
		}
	}

	public static void zeigeAllePhasen() {
		for (Projekt projekt : projekte) {
			for (Phase phase : projekt.getPhasen()) {
				System.out.println(projekt.getName() + " Phase: " + phase.getName());
			}
		}
	}

	public static void zeigeAllePersonenInPhasen() {
		for (Projekt projekt : projekte) {
			System.out.println("Projekt: " + projekt.getName());
			for (Phase phase : projekt.getPhasen()) {
				System.out.println("Phase: " + phase.getName());
				for (Aufwand aufwand : phase.getAufwände()) {
					aufwand.setZugehoerigkeit(phase.getName());
					System.out.println("Beteiligte: " + aufwand.getName() + " PT: " + aufwand.getPt() +
							" Zugehörigkeit: " + aufwand.getZugehoerigkeit());
				}
			}
		}
	}

	public static void DeleteAllData() {
		System.out.println("Lösche alle Daten aus der Datenbank");
		for (Projekt projekt : projekte) {
			myDB.deleteProjekt(projekt);
		}
	}

}
