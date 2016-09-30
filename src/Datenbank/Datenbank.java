package Datenbank;

import java.util.ArrayList;
import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import Projekt.Person;
import Projekt.Kompetenz;
import Projekt.Phase;
import Projekt.Projekt;

/**
 * @author Daniel Sogl
 */

/*
 * Alle Operationen welche mit der DB zu tun haben, werden ueber diese Klasse
 * abgearbeitet. Fehler werden als Exception abgefangen. Treten fehler auf, wird
 * ein boolean Wert -false - zurueckgegeben. Ergebnisse einer SELECT Abfrage
 * werden als ArrayListen mit den passenden Objecten zurueckgegeben.
 */

public class Datenbank {

	// Variablen um Verbindung mit der DB aufnehmen zu kÃ¶nnen
	private Sql2o sql2o;

	// Query Ergebnisse werden in Listen gespeichert

	public Datenbank() {
		/*
		 * Der SQL Nutzer halt Lediglich die Rechte: SELECT, INSTERT, UPDATE,
		 * DELETE Somit wird verhindert, dass der Nutzer ganze Tabellen löschen
		 * kann (Stichwort SQL-Injection). Die SQL Server URL kann am ende
		 * belieben angepasst werden Ein CREATE TABLE Befehl ist NICHT
		 * vorgesehen!
		 */
		sql2o = new Sql2o("jdbc:mysql://beta.lolstats.org:3306/fallstudie", "fallstudie_user", "Kqj5^g98");
	}

	public boolean testConnection() {
		/*
		 * Ist eine Verbindung mit der Datenbank moeglich, wird true
		 * zurueckgegeben Bei einem Fehler wird eine Exception geworfen und
		 * false zurueckgegeben Diese Methode soll vor jedem DB Aufruf
		 * aufgerufen werden
		 */

		try (Connection con = sql2o.open()) {
			return true;
		} catch (Sql2oException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean speicherProjekt(Projekt projekt) {
		boolean result;

		/*
		 * Ein Projekt wird komplett auf einmal in die Datenbank geschrieben.
		 * Existiert ein Projekt und seine Phasen und deren Personen bereits,
		 * werden diese Daten mit den neuen überschrieben. Somit kann das
		 * gesammte Projekt auf einmal geupdated werden.
		 */

		// INSERT INTO projekte(name, ersteller, abgeschickt,startDate, endDate)
		// VALUES("Projekt 3", "Peter", 1, "2011-01-01", "2015-01-01")

		String sql = "INSERT INTO projekte(name, ersteller, abgeschickt, startDate, endDate) " + 
		"VALUES(:name, :ersteller, :abgeschickt, :startDate, :endDate) " + 
		"ON DUPLICATE KEY UPDATE name=:name, ersteller=:ersteller, abgeschickt=:abgeschickt, startDate=:startDate, endDate=:endDate";

		// Das Start und Enddatum eines Projektes entspricht dem Startdatum der
		// Ersten Phasen und dem Enddatum der letzten Phase

		try (Connection con = sql2o.open()) {
			con.createQuery(sql).addParameter("name", projekt.getName())
					.addParameter("ersteller", projekt.getErsteller())
					.addParameter("abgeschickt", projekt.isAbgeschickt())
					.addParameter("startDate", projekt.getStartDate()).addParameter("endDate", projekt.getEndDate())
					.executeUpdate();
			result = true;
		} catch (Sql2oException e) {
			System.out.println(e.getMessage());
			return false;
		}

		/*
		 * Speichert Phasen in die DB. Existiert ein Eintrag Bereits, wird er
		 * automatisch überschrieben
		 */
		sql = "INSERT INTO phasen(phasenKey, name, projekt, startDate, endDate) VALUES(:phasenKey, :name, :projekt, :startDate, :endDate) "+
		"ON DUPLICATE KEY UPDATE phasenKey=:phasenKey, name=:name, projekt=:projekt, startDate=:startDate, endDate=:endDate";

		try (Connection con = sql2o.beginTransaction()) {
			Query query = con.createQuery(sql);

			for (Phase phase : projekt.getPhasen()) {
				query.addParameter("phasenKey", (phase.getName() + projekt.getName()).replaceAll("\\s+", ""))
						.addParameter("name", phase.getName()).addParameter("projekt", projekt.getName())
						.addParameter("startDate", phase.getStartDate()).addParameter("endDate", phase.getEndDate())
						.addToBatch();
			}
			query.executeBatch();
			con.commit();

		} catch (Sql2oException e) {
			System.out.println(e.getMessage());
			return false;
		}

		// Speichere alle beteiligten Personen der jeweiligen Phasen in die DB

		sql = "INSERT INTO beteiligte(beteiligteKey, person, phase, projekt, pt, mak, preisPT, intern) " + 
		"VALUES(:beteiligteKey, :person, :phase, :projekt, :pt, :mak, :preisPT, :intern) " + 
		"ON DUPLICATE KEY UPDATE beteiligteKey=:beteiligteKey, person=:person, phase=:phase,  projekt=:projekt, pt=:pt, mak=:mak, preisPT=:preisPT, intern=:intern";

		try (Connection con = sql2o.beginTransaction()) {
			Query query = con.createQuery(sql);

			for (Phase phase : projekt.getPhasen()) {
				for (Person person : phase.getPersonen()) {
					query.addParameter("beteiligteKey",
							(person.getName() + phase.getName() + projekt.getName()).replaceAll("\\s+", ""))
							.addParameter("person", person.getName())
							.addParameter("phase", phase.getName())
							.addParameter("projekt", projekt.getName())
							.addParameter("pt", person.getPt())
							.addParameter("mak", person.getMak())
							.addParameter("preisPT", person.getPreisPT())
							.addParameter("intern", person.isIntern())
							.addToBatch();
				}
			}

			query.executeBatch();
			con.commit();
		} catch (Sql2oException e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		sql = "INSERT INTO kompetenzen (kompetenzKey, name, person, projekt) " + 
		"VALUES(:kompetenzKey, :name, :person, :projekt) " + 
		"ON DUPLICATE KEY UPDATE kompetenzKey=:kompetenzKey, name=:name, person=:person, projekt=:projekt";
				
		try (Connection con = sql2o.beginTransaction()) {
			
			Query query = con.createQuery(sql);
			
			for(Kompetenz kompetenz: projekt.getKompetenzen()){
				for (Person person : kompetenz.getPersonen()) {
					query
					.addParameter("kompetenzKey", (kompetenz.getName() + person.getName() + projekt.getName()).replaceAll("\\s+", ""))
					.addParameter("name", kompetenz.getName())
					.addParameter("person", person.getName())
					.addParameter("projekt", projekt.getName())
					.addToBatch();
				}
			}
			query.executeBatch();
			con.commit();
			return true;
		} catch (Sql2oException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public Projekt getProjekt(String projektName, String projektErsteller, Boolean abgeschickt) {

		/*
		 * Diese Methode gibt ein komplettes Projekt mit allen Phasen und
		 * Personen zurück.
		 */

		Projekt projekt = new Projekt(projektName, projektErsteller, abgeschickt);
		List<Phase> phasen = new ArrayList<Phase>();
		List<Person> personen = new ArrayList<Person>();

		// Hole Phasen anhand des Projektnamens
		String sql = "SELECT name, startDate, endDate FROM phasen WHERE projekt=:projektName";
		try (Connection con = sql2o.open()) {
			phasen = con.createQuery(sql).addParameter("projektName", projektName).executeAndFetch(Phase.class);
		} catch (Sql2oException e) {
			System.out.println(e.getMessage());
		}

		// Hole Alle Personen anhand des Projektnamnes
		sql = "SELECT person AS name, phase AS zugehoerigkeit, pt, mak, preisPT, intern from beteiligte b, personen p " + 
		"WHERE b.phase=:phasenName AND b.projekt=:projektName AND p.name=b.person";

		try (Connection con = sql2o.open()) {
			for (Phase phase : phasen) {
				personen = con.createQuery(sql)
						.addParameter("phasenName", phase.getName())
						.addParameter("projektName", projekt.getName())
						.executeAndFetch(Person.class);
				phase.setPersonen(personen);
			}
		} catch (Sql2oException e) {
			System.out.println(e.getMessage());
		}

		// Schreibe die Phasen in das Projekt
		projekt.setPhasen((ArrayList<Phase>) phasen);

		for (Phase phase : projekt.getPhasen()) {
			for (Person person : personen) {
				person.setZugehoerigkeit(phase.getName());
			}
		}
		
		//Setze Projekt Start- und Enddatum
		int endPhase = projekt.getPhasen().size() - 1;
		projekt.setStartDate(projekt.getPhasen().get(0).getStartDate());
		projekt.setEndDate(projekt.getPhasen().get(endPhase).getEndDate());

		// Weise die Personen dem projekt hinzu
		projekt.setPersonen((ArrayList<Person>) personen);
		
		
		//Hole die Kompetenzen aus der DB
		sql = "SELECT DISTINCT  name FROM kompetenzen WHERE projekt=:projektName";
		List<Kompetenz> kompetenzen = new ArrayList<Kompetenz>();
		try (Connection con = sql2o.open()) {
			 kompetenzen = con.createQuery(sql)
					 .addParameter("projektName", projekt.getName())
					 .executeAndFetch(Kompetenz.class);
		} catch (Sql2oException e) {
			System.out.println(e.getMessage());
		}
		
		
		// Weise Kompetenzen Peronen zu
		sql = "SELECT person as name FROM kompetenzen WHERE projekt=:projekt AND name=:name";
		try (Connection con = sql2o.open()) {
			for (Kompetenz kompetenz : kompetenzen) {
				 List<Person> tmpPersonen = con.createQuery(sql)
							.addParameter("projekt", projekt.getName())
							.addParameter("name", kompetenz.getName())
							.executeAndFetch(Person.class);
				kompetenz.setPersonen(tmpPersonen);
			}
				
		} catch (Sql2oException e) {
			System.out.println(e.getMessage());
		}
		
		//Weise Projekt die Kompetenzen mit den gespeicherten personen hinzu
		projekt.setKompetenzen(kompetenzen);
		

		return projekt;
	}

	public boolean updateProjekt(Projekt projekt) {
		/*
		 * Wird ein Projekt geupdated, müssen alle vorherigen Einträge gelöscht
		 * werden, da ansonsten die DB nach einem bestimmten Eintrag durchsucht
		 * werden muss. Das direkte löschen und neu schreiben geht schneller!
		 */

		this.deleteProjekt(projekt);
		return this.speicherProjekt(projekt);
	}

	public List<Person> getPersonen() {

		/*
		 * Es wird eine Liste von Personen zurückgeben. Ist diese leer/null muss
		 * dies in der Main Klasse abgefragt werden.
		 */

		List<Person> personen = new ArrayList<Person>();
		String sql = "SELECT * FROM personen";
		try (Connection con = sql2o.open()) {
			personen = con.createQuery(sql).executeAndFetch(Person.class);
		} catch (Sql2oException e) {
			System.out.println(e.getMessage());
		}
		return personen;
	}

	public boolean deletePerson(Person person) {
		String sql = "DELETE * FROM personen WHERE name=:name";

		try (Connection con = sql2o.open()) {
			con.createQuery(sql).addParameter("name", person.getName()).executeUpdate();
			return true;
		} catch (Sql2oException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public List<Projekt> getProjekte() {

		/*
		 * Alle Projekte welche bereits in der Datenbank gespeichert sind,
		 * werden als Liste Zurueckgegeben
		 */
		List<Projekt> projekte = new ArrayList<Projekt>();

		String sql = "SELECT * FROM projekte";
		try (Connection con = sql2o.open()) {
			projekte = con.createQuery(sql).executeAndFetch(Projekt.class);
		} catch (Sql2oException e) {
			System.out.println(e.getMessage());
		}
		return projekte;
	}

	public boolean deleteProjekt(Projekt projekt) {

		/*
		 * Alle Einträge welche mit einem Projekt zu tun ahben, werden anhand
		 * des Projektnamens gelöscht. Anschließend kann ein projekt erneut
		 * geschriben werden.
		 */

		String sql1 = "DELETE FROM beteiligte WHERE projekt=:projektName";
		String sql2 = "DELETE FROM phasen WHERE projekt=:projektName";
		String sql3 = "DELETE FROM projekte WHERE name=:projektName";
		String sql4 = "DELETE FROM kompetenzen WHERE name=:projektName";
		try (Connection con = sql2o.open()) {
			con.createQuery(sql1).addParameter("projektName", projekt.getName()).executeUpdate();

			con.createQuery(sql2).addParameter("projektName", projekt.getName()).executeUpdate();

			con.createQuery(sql3).addParameter("projektName", projekt.getName()).executeUpdate();
			
			con.createQuery(sql4).addParameter("projektName", projekt.getName()).executeUpdate();
			return true;
		} catch (Sql2oException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

}
