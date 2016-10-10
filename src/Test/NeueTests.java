package Test;

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
		k1.setSingleAufwand(new Aufwand("Intern"));
		k1.setSingleAufwand(new Aufwand("Extern"));
		
		Phase p1 = new Phase("Phase 1", "2016-01-01", "2016-01-02");
		p1.setSingleAufwand(new Aufwand("Intern"));
		p1.setSingleAufwand(new Aufwand("Extern"));
		
		p1.getAufwände().get(0).setZugehoerigkeit("Kompetenz 1");
		p1.getAufwände().get(0).setPt(100);
		p1.getAufwände().get(1).setZugehoerigkeit("Kompetenz 1");
		p1.getAufwände().get(1).setPt(200);
		
		
		Phase p2 = new Phase("Phase 2", "2016-01-02", "2016-01-03");
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
					if(aufwand.getZugehoerigkeit().equals(kompetenz.getName()))
						System.out.println(aufwand.getName() + " PT: " + aufwand.getPt() + " Kompetenz: " + aufwand.getZugehoerigkeit());
				}
			}
		}
		
		System.out.println("Speichere Projekt");
		myDB.speicherProjekt(projekt);

		System.out.println("Hole das Projekt zurück aus der Datenbank und gebe es aus");
		projekt = myDB.getProjekt(projekt);
		
		for (Kompetenz kompetenz : projekt.getKompetenzen()) {
			for (Phase phase : projekt.getPhasen()) {
				System.out.println(phase.getName());
				for (Aufwand aufwand : phase.getAufwände()) {
					if(aufwand.getZugehoerigkeit().equals(kompetenz.getName()))
						System.out.println(aufwand.getName() + " PT: " + aufwand.getPt() + " Kompetenz: " + aufwand.getZugehoerigkeit());
				}
			}
		}
		
		
	}

}
