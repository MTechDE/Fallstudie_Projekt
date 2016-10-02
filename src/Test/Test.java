package Test;

import java.util.ArrayList;
import java.util.List;

import Datenbank.Datenbank;
import Projekt.Person;
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
	static List<Person> personen = new ArrayList<Person>();
	static List<Phase> phasen = new ArrayList<Phase>();
	static List<Kompetenz> kompetenzen = new ArrayList<Kompetenz>();
	static Datenbank myDB = new Datenbank();

	public static void main(String[] args) throws InterruptedException {
		Test1();
		Test2();
		Test3();
		Test4();
		Test5();
		Test6();
		Test7();

		DeleteAllData();
		System.out.println("Tests Beendet");

	}

	public static void Test1() {

		System.out.println("Test 1");
		System.out.println("======");

		projekte.clear();
		personen.clear();
		phasen.clear();
		kompetenzen.clear();
		System.out.println("Erstelle ein Projekt und weise diesem 3 Phasen mit je 3 Mitarbeitern zu");

		projekte.add(new Projekt("Projekt 1", "Daniel", false));
		// zeigeAlleProjekte();

		projekte.get(0).setSinglePhase(new Phase("Phase 1", "2011-01-01", "2015-01-01"));
		projekte.get(0).setSinglePhase(new Phase("Phase 2", "2011-01-01", "2015-01-01"));

		projekte.get(0).setSinglePhase(new Phase("Phase 3", "2011-01-01", "2015-01-01"));
		// zeigeAllePhasen();

		projekte.get(0).getPhasen().get(0).setSinglePerson(new Person("Programmierer 1", true));
		projekte.get(0).getPhasen().get(0).setSinglePerson(new Person("Programmierer 2", false));

		projekte.get(0).getPhasen().get(1).setSinglePerson(new Person("Programmierer 1", false));
		projekte.get(0).getPhasen().get(1).setSinglePerson(new Person("Programmierer 2", false));

		projekte.get(0).getPhasen().get(2).setSinglePerson(new Person("Programmierer 1", true));
		projekte.get(0).getPhasen().get(2).setSinglePerson(new Person("Programmierer 2", true));

		projekte.get(0).getPhasen().get(0).getPersonen().get(0).setPt(5);
		projekte.get(0).getPhasen().get(0).getPersonen().get(1).setPt(10);

		projekte.get(0).getPhasen().get(1).getPersonen().get(0).setPt(6);
		projekte.get(0).getPhasen().get(1).getPersonen().get(1).setPt(9);

		projekte.get(0).getPhasen().get(2).getPersonen().get(0).setPt(5);
		projekte.get(0).getPhasen().get(2).getPersonen().get(1).setPt(40);

		for (Phase phase : projekte.get(0).getPhasen()) {
			for (Person person : phase.getPersonen()) {
				projekte.get(0).setSinglePerson(person);
			}
		}

		// zeigeAllePersonenInPhasen();

		System.out.println("Weise Personen einzelnen Kompetenzen zu");
		kompetenzen.add(new Kompetenz("Java Backend"));
		kompetenzen.add(new Kompetenz("Java Frontend"));

		kompetenzen.get(0).setSinglePerson(projekte.get(0).getPersonen().get(0));
		kompetenzen.get(1).setSinglePerson(projekte.get(0).getPersonen().get(1));

		for (Kompetenz kompetenz : kompetenzen) {
			for (Person person : kompetenz.getPersonen()) {
				System.out.println(kompetenz.getName() + ": " + person.getName());
			}
		}

	}

	public static void Test2() {
		System.out.println("Test 2");
		System.out.println("======");
		projekte.clear();
		personen.clear();
		phasen.clear();
		kompetenzen.clear();
		System.out.println(
				"Lege zwei Projekte in der Datenbank an und deffiniere 2 Phasen mit unterschidlichen Personen");

		System.out.println("Projekt 1");
		projekte.add(new Projekt("Projekt 1", "Daniel", true));
		projekte.get(0).setSinglePhase(new Phase("Phase 1", "2011-01-01", "2015-01-01"));
		projekte.get(0).setSinglePhase(new Phase("Phase 2", "2011-01-01", "2015-01-01"));

		projekte.get(0).getPhasen().get(0).setSinglePerson(new Person("Programmierer 1", true));
		projekte.get(0).getPhasen().get(0).setSinglePerson(new Person("Programmierer 2", false));

		projekte.get(0).getPhasen().get(1).setSinglePerson(new Person("Programmierer 1", false));
		projekte.get(0).getPhasen().get(1).setSinglePerson(new Person("Programmierer 2", false));

		projekte.get(0).getPhasen().get(0).getPersonen().get(0).setPt(9);
		projekte.get(0).getPhasen().get(0).getPersonen().get(1).setPt(9);

		projekte.get(0).getPhasen().get(1).getPersonen().get(0).setPt(8);
		projekte.get(0).getPhasen().get(1).getPersonen().get(1).setPt(8);

		projekte.get(0).setStartDate(projekte.get(0).getPhasen().get(0).getStartDate());
		projekte.get(0).setEndDate(projekte.get(0).getPhasen().get(1).getEndDate());

		for (Person person : projekte.get(0).getPhasen().get(0).getPersonen()) {
			projekte.get(0).setSinglePerson(person);
		}

		kompetenzen.add(new Kompetenz("Java Backend"));
		kompetenzen.add(new Kompetenz("Java Frontend"));
		kompetenzen.get(0).setSinglePerson(projekte.get(0).getPersonen().get(0));
		kompetenzen.get(1).setSinglePerson(projekte.get(0).getPersonen().get(1));

		projekte.get(0).setKompetenzen(kompetenzen);

		if (myDB.testConnection())
			if (myDB.updateProjekt(projekte.get(0)))
				System.out.println("Projekt 1 gespeichert");

		System.out.println("Projekt 2");
		projekte.add(new Projekt("Projekt 2", "David", false));
		projekte.get(1).setSinglePhase(new Phase("Phase 1", "2011-01-01", "2015-01-01"));
		projekte.get(1).setSinglePhase(new Phase("Phase 2", "2011-01-01", "2015-01-01"));

		projekte.get(1).getPhasen().get(0).setSinglePerson(new Person("Programmierer 1", true));
		projekte.get(1).getPhasen().get(0).setSinglePerson(new Person("Programmierer 2", true));
		projekte.get(1).getPhasen().get(0).setSinglePerson(new Person("Programmierer 3", false));

		projekte.get(1).getPhasen().get(1).setSinglePerson(new Person("Programmierer 1", false));
		projekte.get(1).getPhasen().get(1).setSinglePerson(new Person("Programmierer 2", true));
		projekte.get(1).getPhasen().get(1).setSinglePerson(new Person("Programmierer 3", false));

		projekte.get(1).getPhasen().get(0).getPersonen().get(0).setPt(8);
		projekte.get(1).getPhasen().get(0).getPersonen().get(1).setPt(8);
		projekte.get(1).getPhasen().get(0).getPersonen().get(2).setPt(8);

		projekte.get(1).getPhasen().get(1).getPersonen().get(0).setPt(7);
		projekte.get(1).getPhasen().get(1).getPersonen().get(1).setPt(7);
		projekte.get(1).getPhasen().get(1).getPersonen().get(2).setPt(7);

		projekte.get(1).setStartDate(projekte.get(1).getPhasen().get(0).getStartDate());
		projekte.get(1).setEndDate(projekte.get(1).getPhasen().get(1).getEndDate());

		for (Person person : projekte.get(1).getPhasen().get(0).getPersonen()) {
			projekte.get(1).setSinglePerson(person);
		}

		kompetenzen.clear();

		kompetenzen.add(new Kompetenz("Java Backend"));
		kompetenzen.add(new Kompetenz("Java Frontend"));

		kompetenzen.get(0).setSinglePerson(projekte.get(1).getPersonen().get(0));
		kompetenzen.get(1).setSinglePerson(projekte.get(1).getPersonen().get(1));
		kompetenzen.get(1).setSinglePerson(projekte.get(1).getPersonen().get(2));

		projekte.get(1).setKompetenzen(kompetenzen);

		if (myDB.testConnection())
			if (myDB.updateProjekt(projekte.get(1)))
				System.out.println("Projekt 2 gespeichert");
	}

	public static void Test3() {
		System.out.println("Test 3");
		System.out.println("======");
		projekte.clear();
		personen.clear();
		phasen.clear();

		System.out.println("Lade Projekte aus der DB");

		if (myDB.testConnection()) {
			projekte.add(myDB.getProjekt(new Projekt("Projekt 2", "David", false)));
			projekte.add(myDB.getProjekt(new Projekt("Projekt 1", "Daniel", true)));

			System.out.println("Projekt 2 Startdatum: " + projekte.get(0).getStartDate() + " Enddatum: "
					+ projekte.get(0).getEndDate());
			for (Phase phase : projekte.get(0).getPhasen()) {
				System.out.println(phase.getName());
				for (Person person : phase.getPersonen()) {
					System.out.println(
							person.getName() + " PT: " + person.getPt() + " Phase: " + person.getZugehoerigkeit());
				}
			}

			System.out.println();
			System.out.println("Projekt 1 Startdatum: " + projekte.get(1).getStartDate() + " Enddatum: "
					+ projekte.get(1).getEndDate());
			for (Phase phase : projekte.get(1).getPhasen()) {
				System.out.println(phase.getName());
				for (Person person : phase.getPersonen()) {
					System.out.println(
							person.getName() + " PT: " + person.getPt() + " Phase: " + person.getZugehoerigkeit());
				}
			}
		}
	}

	public static void Test4() {
		System.out.println("Test 4");
		System.out.println("======");
		projekte.clear();
		personen.clear();
		phasen.clear();
		System.out.println("Hole Projekt aus der DB und f�ge eine neue Phase hinzu");

		if (myDB.testConnection()) {
			projekte.add(myDB.getProjekt(new Projekt("Projekt 2", "David", false)));

			for (Phase phase : projekte.get(0).getPhasen()) {
				System.out.println(phase.getName());
				for (Person person : phase.getPersonen()) {
					System.out.println(
							person.getName() + " PT: " + person.getPt() + " Phase: " + person.getZugehoerigkeit());
				}
			}

			phasen = projekte.get(0).getPhasen();
			personen = phasen.get(0).getPersonen();
			for (Person person : personen) {
				person.setPt(5555);
			}

			Phase phase3 = new Phase("Phase 3", "2011-01-01", "2015-01-01");
			phase3.setPersonen(personen);

			for (Person person : phase3.getPersonen()) {
				person.setZugehoerigkeit(phase3.getName());
				System.out.println(person.getName() + " " + person.getZugehoerigkeit() + " " + person.getPt());
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
		personen.clear();
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
		personen.clear();
		phasen.clear();

		System.out.println("Hole Projekt aus der DB und lösche eine Phase und eine Person");
		if (myDB.testConnection()) {
			projekte.add(myDB.getProjekt(new Projekt("Projekt 2", "David", false)));
			projekte.get(0).getPhasen().remove(1);
			projekte.get(0).getPhasen().get(0).getPersonen().remove(0);
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
		personen.clear();
		phasen.clear();

		System.out.println("Erste Tabelle anhand der Phasen und Personen");

		if (myDB.testConnection()) {
			projekte.add(myDB.getProjekt(new Projekt("Projekt 2", "David", false)));
			projekte.add(myDB.getProjekt(new Projekt("Projekt 1", "Daniel", true)));
			
			for (Projekt projekt : projekte) {
				for (Kompetenz kompetenz : projekt.getKompetenzen()) {
					System.out.println(kompetenz.getName());
					for (Person person : kompetenz.getPersonen()) {
						System.out.println(person.getName());
						for (Phase phase : projekt.getPhasen()) {
							System.out.println(phase.getName());
							for (Person phasePerson : phase.getPersonen()) {
								if(person.getName().equals(phasePerson.getName()))
									System.out.println(phasePerson.getName() + " - " + phasePerson.getPt());
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
				for (Person person : phase.getPersonen()) {
					person.setZugehoerigkeit(phase.getName());
					System.out.println("Beteiligte: " + person.getName() + " PT: " + person.getPt() + " Kosten p.Pt: "
							+ person.getPreisPT() + " Zugehörigkeit: " + person.getZugehoerigkeit());
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
