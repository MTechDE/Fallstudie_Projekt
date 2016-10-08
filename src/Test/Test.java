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
		
//		Test1();
//		Test2();
//		Test3();
//		Test4();
//		Test5();
//		Test6();
//		Test7();
		Test8();
//		DeleteAllData();
		System.out.println("Tests Beendet");

	}

	public static void Test1() {

		System.out.println("Test 1");
		System.out.println("======");

		projekte.clear();
		aufwände.clear();
		phasen.clear();
		kompetenzen.clear();
		System.out.println("Erstelle ein Projekt und weise diesem 3 Phasen mit je 3 Mitarbeitern zu");

		projekte.add(new Projekt("Projekt 1", "Daniel", false));
		// zeigeAlleProjekte();

		projekte.get(0).setSinglePhase(new Phase("Phase 1", "2011-01-01", "2015-01-01"));
		projekte.get(0).setSinglePhase(new Phase("Phase 2", "2011-01-01", "2015-01-01"));

		projekte.get(0).setSinglePhase(new Phase("Phase 3", "2011-01-01", "2015-01-01"));
		// zeigeAllePhasen();

		projekte.get(0).getPhasen().get(0).setSingleAufwand(new Aufwand("Intern", true));
		projekte.get(0).getPhasen().get(0).setSingleAufwand(new Aufwand("Extern", false));

		projekte.get(0).getPhasen().get(1).setSingleAufwand(new Aufwand("Programmierer 1", false));
		projekte.get(0).getPhasen().get(1).setSingleAufwand(new Aufwand("Programmierer 2", false));

		projekte.get(0).getPhasen().get(2).setSingleAufwand(new Aufwand("Programmierer 1", true));
		projekte.get(0).getPhasen().get(2).setSingleAufwand(new Aufwand("Programmierer 2", true));

		projekte.get(0).getPhasen().get(0).getAufwände().get(0).setPt(5);
		projekte.get(0).getPhasen().get(0).getAufwände().get(1).setPt(10);

		projekte.get(0).getPhasen().get(1).getAufwände().get(0).setPt(6);
		projekte.get(0).getPhasen().get(1).getAufwände().get(1).setPt(9);

		projekte.get(0).getPhasen().get(2).getAufwände().get(0).setPt(5);
		projekte.get(0).getPhasen().get(2).getAufwände().get(1).setPt(40);

		// zeigeAllePersonenInPhasen();

		System.out.println("Weise Personen einzelnen Kompetenzen zu");
		kompetenzen.add(new Kompetenz("Java Backend"));
		kompetenzen.add(new Kompetenz("Java Frontend"));

		kompetenzen.get(0).setSingleAufwand(new Aufwand("Intern"));
		kompetenzen.get(0).setSingleAufwand(new Aufwand("Extern"));
		
		kompetenzen.get(1).setSingleAufwand(new Aufwand("Intern"));
		kompetenzen.get(1).setSingleAufwand(new Aufwand("Extern"));

		for (Kompetenz kompetenz : kompetenzen) {
			for (Aufwand aufwand : kompetenz.getAufwände()) {
				System.out.println(kompetenz.getName() + ": " + aufwand.getName());
			}
		}

	}

	public static void Test2() {
		System.out.println("Test 2");
		System.out.println("======");
		projekte.clear();
		aufwände.clear();
		phasen.clear();
		kompetenzen.clear();
		System.out.println(
				"Lege zwei Projekte in der Datenbank an und deffiniere 2 Phasen mit unterschiedlichen Personen");

		System.out.println("Projekt 1");
		projekte.add(new Projekt("Projekt 1", "Daniel", true));
		projekte.get(0).setSinglePhase(new Phase("Phase 1", "2011-01-01", "2015-01-01"));
		projekte.get(0).setSinglePhase(new Phase("Phase 2", "2011-01-01", "2015-01-01"));

		projekte.get(0).getPhasen().get(0).setSingleAufwand(new Aufwand("Programmierer 1", true));
		projekte.get(0).getPhasen().get(0).setSingleAufwand(new Aufwand("Programmierer 2", false));

		projekte.get(0).getPhasen().get(1).setSingleAufwand(new Aufwand("Programmierer 1", false));
		projekte.get(0).getPhasen().get(1).setSingleAufwand(new Aufwand("Programmierer 2", false));

		projekte.get(0).getPhasen().get(0).getAufwände().get(0).setPt(9);
		projekte.get(0).getPhasen().get(0).getAufwände().get(1).setPt(9);

		projekte.get(0).getPhasen().get(1).getAufwände().get(0).setPt(8);
		projekte.get(0).getPhasen().get(1).getAufwände().get(1).setPt(8);

		projekte.get(0).setStartDate(projekte.get(0).getPhasen().get(0).getStartDate());
		projekte.get(0).setEndDate(projekte.get(0).getPhasen().get(1).getEndDate());


		kompetenzen.add(new Kompetenz("Java Backend"));
		kompetenzen.add(new Kompetenz("Java Frontend"));
		
		kompetenzen.get(0).setSingleAufwand(new Aufwand("Intern"));
		kompetenzen.get(0).setSingleAufwand(new Aufwand("Extern"));
		kompetenzen.get(1).setSingleAufwand(new Aufwand("Intern"));
		kompetenzen.get(1).setSingleAufwand(new Aufwand("Extern"));

		projekte.get(0).setKompetenzen(kompetenzen);

		if (myDB.testConnection())
			if (myDB.updateProjekt(projekte.get(0)))
				System.out.println("Projekt 1 gespeichert");

		System.out.println("Projekt 2");
		projekte.add(new Projekt("Projekt 2", "David", false));
		projekte.get(1).setSinglePhase(new Phase("Phase 1", "2011-01-01", "2015-01-01"));
		projekte.get(1).setSinglePhase(new Phase("Phase 2", "2011-01-01", "2015-01-01"));

		projekte.get(1).getPhasen().get(0).setSingleAufwand(new Aufwand("Programmierer 1", true));
		projekte.get(1).getPhasen().get(0).setSingleAufwand(new Aufwand("Programmierer 2", true));
		projekte.get(1).getPhasen().get(0).setSingleAufwand(new Aufwand("Programmierer 3", false));

		projekte.get(1).getPhasen().get(1).setSingleAufwand(new Aufwand("Programmierer 1", false));
		projekte.get(1).getPhasen().get(1).setSingleAufwand(new Aufwand("Programmierer 2", true));
		projekte.get(1).getPhasen().get(1).setSingleAufwand(new Aufwand("Programmierer 3", false));

		projekte.get(1).getPhasen().get(0).getAufwände().get(0).setPt(8);
		projekte.get(1).getPhasen().get(0).getAufwände().get(1).setPt(8);
		projekte.get(1).getPhasen().get(0).getAufwände().get(2).setPt(8);

		projekte.get(1).getPhasen().get(1).getAufwände().get(0).setPt(7);
		projekte.get(1).getPhasen().get(1).getAufwände().get(1).setPt(7);
		projekte.get(1).getPhasen().get(1).getAufwände().get(2).setPt(7);

		projekte.get(1).setStartDate(projekte.get(1).getPhasen().get(0).getStartDate());
		projekte.get(1).setEndDate(projekte.get(1).getPhasen().get(1).getEndDate());


		kompetenzen.clear();

		kompetenzen.add(new Kompetenz("Java Backend"));
		kompetenzen.add(new Kompetenz("Java Frontend"));

		kompetenzen.get(0).setSingleAufwand(new Aufwand("Intern"));
		kompetenzen.get(0).setSingleAufwand(new Aufwand("Extern"));
		
		kompetenzen.get(1).setSingleAufwand(new Aufwand("Intern"));
		kompetenzen.get(1).setSingleAufwand(new Aufwand("Extern"));

		projekte.get(1).setKompetenzen(kompetenzen);

		if (myDB.testConnection())
			if (myDB.updateProjekt(projekte.get(1)))
				System.out.println("Projekt 2 gespeichert");
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

			Phase phase3 = new Phase("Phase 3", "2011-01-01", "2015-01-01");
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
			
			for (Projekt projekt : projekte) {
				for (Kompetenz kompetenz : projekt.getKompetenzen()) {
					System.out.println(kompetenz.getName());
					for (Aufwand aufwand : kompetenz.getAufwände()) {
						System.out.println(aufwand.getName());
						for (Phase phase : projekt.getPhasen()) {
							System.out.println(phase.getName());
							for (Aufwand phaseAufwand : phase.getAufwände()) {
								if(aufwand.getName().equals(phaseAufwand.getName()))
									System.out.println(phaseAufwand.getName() + " - " + phaseAufwand.getPt());
							}
							System.out.println("");
						}
						System.out.println("");
					}
					System.out.println("");
				}
			}
		}
	}
	
	public static void Test8(){
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
