package Test;

import java.util.ArrayList;
import java.util.List;

import Datenbank.Datenbank;
import Projekt.Aufwand;
import Projekt.Kompetenz;
import Projekt.Phase;
import Projekt.Projekt;

public class NeueTests {

	static Datenbank myDB = new Datenbank();

	public static void main(String[] args) {
		Projekt projekt = new Projekt("Projekt 1", "Daniel", false);
		Kompetenz k1 = new Kompetenz("Kompetenz 1");

		Phase p1 = new Phase("Phase 1", "2016-01-01", "2016-01-02", 0.0);
		p1.setSingleAufwand(new Aufwand("Intern"));
		p1.setSingleAufwand(new Aufwand("Extern"));

		p1.getAufwände().get(0).setZugehoerigkeit("Kompetenz 1");
		p1.getAufwände().get(0).setPt(100);
		p1.getAufwände().get(1).setZugehoerigkeit("Kompetenz 1");
		p1.getAufwände().get(1).setPt(200);

		Phase p2 = new Phase("Phase 2", "2016-01-02", "2016-01-03", 0.0);
		p2.setSingleAufwand(new Aufwand("Intern"));
		p2.setSingleAufwand(new Aufwand("Extern"));

		p2.getAufwände().get(0).setZugehoerigkeit("Kompetenz 1");
		p2.getAufwände().get(0).setPt(20);
		p2.getAufwände().get(1).setZugehoerigkeit("Kompetenz 1");
		p2.getAufwände().get(1).setPt(30);

		projekt.setSingleKompetenz(k1);
		projekt.setSinglePhase(p1);
		projekt.setSinglePhase(p2);

		for (Kompetenz kompetenz : projekt.getKompetenzen()) {
			for (Phase phase : projekt.getPhasen()) {
				System.out.println(phase.getName());
				for (Aufwand aufwand : phase.getAufwände()) {
					if (aufwand.getZugehoerigkeit().equals(kompetenz.getName()))
						System.out.println(aufwand.getName() + " PT: " + aufwand.getPt() + " Kompetenz: "
								+ aufwand.getZugehoerigkeit());
				}
			}
		}

		myDB.updateProjekt(projekt);

		Projekt neuesProjekt;
		List<Phase> phasen = new ArrayList<Phase>();
		List<Kompetenz> kompetenzen = new ArrayList<Kompetenz>();
		List<Aufwand> aufwände = new ArrayList<Aufwand>();

		neuesProjekt = myDB.getProjektBasic("Projekt 1");
		phasen = myDB.getPhasen("Projekt 1");
		kompetenzen = myDB.getKompetenzen("Projekt 1");

		System.out.println(neuesProjekt.getName());
		System.out.println("Phasen: " + phasen.size());
		System.out.println("Kompetenzen: " + kompetenzen.size());
		System.out.println("\n");

		for (int i = 0; i < kompetenzen.size(); i++) {
			System.out.println(kompetenzen.get(i).getName());
			for (int k = 0; k < phasen.size(); k++) {
				System.out.println(phasen.get(k).getName());
				aufwände = myDB.getAufwände(projekt.getName(), phasen.get(k).getName(), kompetenzen.get(i).getName());
				System.out.println("Anzahl der Aufwände: " + aufwände.size());
				System.out.println(aufwände.get(0).getZugehoerigkeit() + " PT: " + aufwände.get(0).getPt()
						+ " Zugehörigkeit: " + aufwände.get(0).getZugehoerigkeit());
				System.out.println(aufwände.get(1).getZugehoerigkeit() + " PT: " + aufwände.get(1).getPt()
						+ " Zugehörigkeit: " + aufwände.get(1).getZugehoerigkeit());
			}
		}

	}

}
