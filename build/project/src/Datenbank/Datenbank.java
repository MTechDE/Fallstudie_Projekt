package Datenbank;

import java.util.ArrayList;
import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import Projekt.Aufwand;
import Projekt.Kompetenz;
import Projekt.Phase;
import Projekt.Projekt;

/**
 * Alle Opperationen welche mit der Datenbank zu tun haben (speichern und laden von Projekten),
 * werden mit dieser Klasse realisiert.
 * @author Daniel Sogl
 * @version 1.2.1
 */

public class Datenbank {

	private Sql2o sql2o;

	/**
	 * DB Anmeldedaten werden im Konstruktur konfiguriert.
	 */
	public Datenbank() {
		sql2o = new Sql2o("jdbc:mysql://lolstats.org:3306/fallstudie", "fallstudie_user", "bYv735!r");
	}

	/**
	 * Testet die Verbindung zur Datenbank
	 * @return boolean
	 */
	public boolean testConnection() {
		
		try (Connection con = sql2o.open()) {
			return true;
		} catch (Sql2oException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	/**
	 * Speichert ein Projekt und alle enthaltenen Phasen, Kompetenzen und Aufwände.
	 * Bereits vorhandene Daten werden überschrieben (UPDATE).
	 * @param projekt
	 * @return boolean
	 */
	public boolean speicherProjekt(Projekt projekt) {

		String sql = "INSERT INTO projekte(name, ersteller, abgeschickt, startDate, endDate) " + 
		"VALUES(:name, :ersteller, :abgeschickt, :startDate, :endDate) " + 
		"ON DUPLICATE KEY UPDATE name=:name, ersteller=:ersteller, abgeschickt=:abgeschickt, startDate=:startDate, endDate=:endDate";

		try (Connection con = sql2o.open()) {
			con.createQuery(sql).addParameter("name", projekt.getName())
					.addParameter("ersteller", projekt.getErsteller())
					.addParameter("abgeschickt", projekt.isAbgeschickt())
					.addParameter("startDate", projekt.getStartDate())
					.addParameter("endDate", projekt.getEndDate())
					.executeUpdate();
		} catch (Sql2oException e) {
			System.out.println(e.getMessage());
			return false;
		}

		sql = "INSERT INTO phasen(phasenKey, name, projekt, startDate, endDate) VALUES(:phasenKey, :name, :projekt, :startDate, :endDate) "+
		"ON DUPLICATE KEY UPDATE phasenKey=:phasenKey, name=:name, projekt=:projekt, startDate=:startDate, endDate=:endDate";

		if(!projekt.getPhasen().isEmpty()){
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
		}

		// Speichere alle beteiligten Aufwände der jeweiligen Phasen in die DB

		sql = "INSERT INTO aufwand(aufwandKey, person, phase, projekt, pt, intern, risiko) " + 
		"VALUES(:aufwandKey, :person, :phase, :projekt, :pt, :intern, :risiko) " + 
		"ON DUPLICATE KEY UPDATE aufwandKey=:aufwandKey, person=:person, phase=:phase,  projekt=:projekt, pt=:pt, intern=:intern, risiko=:risiko";

		if(!projekt.getAufwände().isEmpty()){
			try (Connection con = sql2o.beginTransaction()) {
				Query query = con.createQuery(sql);

				for (Phase phase : projekt.getPhasen()) {
					for (Aufwand aufwand : phase.getAufwände()) {
						query.addParameter("aufwandKey",
								(aufwand.getName() + phase.getName() + projekt.getName()).replaceAll("\\s+", ""))
								.addParameter("person", aufwand.getName())
								.addParameter("phase", phase.getName())
								.addParameter("projekt", projekt.getName())
								.addParameter("pt", aufwand.getPt())
								.addParameter("intern", aufwand.isIntern())
								.addParameter("risiko", aufwand.getRisiko())
								.addToBatch();
					}
				}

				query.executeBatch();
				con.commit();
			} catch (Sql2oException e) {
				System.out.println(e.getMessage());
				return false;
			}
		}

		
		sql = "INSERT INTO kompetenzen (kompetenzKey, name, person, projekt) " + 
		"VALUES(:kompetenzKey, :name, :person, :projekt) " + 
		"ON DUPLICATE KEY UPDATE kompetenzKey=:kompetenzKey, name=:name, person=:person, projekt=:projekt";
				
		if(!projekt.getKompetenzen().isEmpty()){
			try (Connection con = sql2o.beginTransaction()) {
				
				Query query = con.createQuery(sql);
				
				for(Kompetenz kompetenz: projekt.getKompetenzen()){
					for (Aufwand aufwand : kompetenz.getAufwände()) {
						query
						.addParameter("kompetenzKey", (kompetenz.getName() + aufwand.getName() + projekt.getName()).replaceAll("\\s+", ""))
						.addParameter("name", kompetenz.getName())
						.addParameter("person", aufwand.getName())
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
		} else {
			return true;
		}
	}

	/**
	 * Ein Projekt wird anhand des übergebenen Projekt Objektes aus der
	 * Datenbank geholt und als komplettes Projekt zurückgegeben.
	 * 
	 * @param projekt
	 * @return Projekt
	 */
	public Projekt getProjekt(Projekt projekt) {

		Projekt newprojekt = new Projekt(projekt.getName(), projekt.getErsteller(), projekt.isAbgeschickt());
		List<Phase> phasen = new ArrayList<Phase>();
		List<Aufwand> personen = new ArrayList<Aufwand>();

		// Hole Phasen anhand des Projektnamens
		String sql = "SELECT name, startDate, endDate FROM phasen WHERE projekt=:projektName";
		try (Connection con = sql2o.open()) {
			phasen = con.createQuery(sql).addParameter("projektName", newprojekt.getName()).executeAndFetch(Phase.class);
		} catch (Sql2oException e) {
			System.out.println(e.getMessage());
		}

		// Hole Alle Personen anhand des Projektnamnes
		sql = "SELECT person AS name, phase AS zugehoerigkeit, pt, intern, risiko from aufwand a " + 
		"WHERE a.phase=:phasenName AND a.projekt=:projektName";

		try (Connection con = sql2o.open()) {
			for (Phase phase : phasen) {
				personen = con.createQuery(sql)
						.addParameter("phasenName", phase.getName())
						.addParameter("projektName", newprojekt.getName())
						.executeAndFetch(Aufwand.class);
				phase.setAufwände(personen);
			}
		} catch (Sql2oException e) {
			System.out.println(e.getMessage());
		}

		// Schreibe die Phasen in das Projekt
		newprojekt.setPhasen((ArrayList<Phase>) phasen);

		for (Phase phase : newprojekt.getPhasen()) {
			for (Aufwand aufwand : personen) {
				aufwand.setZugehoerigkeit(phase.getName());
			}
		}
		
		//Setze Projekt Start- und Enddatum
		int endPhase = newprojekt.getPhasen().size() - 1;
		newprojekt.setStartDate(newprojekt.getPhasen().get(0).getStartDate());
		newprojekt.setEndDate(newprojekt.getPhasen().get(endPhase).getEndDate());

		// Weise die Personen dem projekt hinzu
		newprojekt.setAufwände((ArrayList<Aufwand>) personen);
		
		
		//Hole die Kompetenzen aus der DB
		sql = "SELECT DISTINCT  name FROM kompetenzen WHERE projekt=:projektName";
		List<Kompetenz> kompetenzen = new ArrayList<Kompetenz>();
		try (Connection con = sql2o.open()) {
			 kompetenzen = con.createQuery(sql)
					 .addParameter("projektName", newprojekt.getName())
					 .executeAndFetch(Kompetenz.class);
		} catch (Sql2oException e) {
			System.out.println(e.getMessage());
		}
		
		
		// Weise Kompetenzen Peronen zu
		sql = "SELECT person as name FROM kompetenzen WHERE projekt=:projekt AND name=:name";
		try (Connection con = sql2o.open()) {
			for (Kompetenz kompetenz : kompetenzen) {
				 List<Aufwand> tmpPersonen = con.createQuery(sql)
							.addParameter("projekt", newprojekt.getName())
							.addParameter("name", kompetenz.getName())
							.executeAndFetch(Aufwand.class);
				kompetenz.setAufwände(tmpPersonen);
			}
				
		} catch (Sql2oException e) {
			System.out.println(e.getMessage());
		}
		
		//Weise Projekt die Kompetenzen mit den gespeicherten personen hinzu
		newprojekt.setKompetenzen(kompetenzen);
		

		return newprojekt;
	}

	/**
	 * Ein Projekt wird komplett neu in die Datenbank geschrieben.
	 * Dazu wird es zunächst komplett gelöscht und anschließend
	 * neu in die Datenbank geschrieben.
	 * @param projekt
	 * @return boolean
	 */
	public boolean updateProjekt(Projekt projekt) {
		this.deleteProjekt(projekt);
		return this.speicherProjekt(projekt);
	}

	public List<Aufwand> getPersonen() {

		/*
		 * Es wird eine Liste von Personen zur�ckgeben. Ist diese leer/null muss
		 * dies in der Main Klasse abgefragt werden.
		 */

		List<Aufwand> personen = new ArrayList<Aufwand>();
		String sql = "SELECT * FROM aufwand";
		try (Connection con = sql2o.open()) {
			personen = con.createQuery(sql).executeAndFetch(Aufwand.class);
		} catch (Sql2oException e) {
			System.out.println(e.getMessage());
		}
		return personen;
	}

	/**
	 * Alle Projekte die in der Datenbank gefunden wurden, werden in einer 
	 * Liste zurückgegeben.
	 * @return List<Projekt>
	 */
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

	/**
	 * Alle Daten welche mit einem Projekt zu tun haben (Phasen, Kompetenzen, Aufwände),
	 * werden anhand des Projektnames aus der Datenbank gelöscht.
	 * @param projekt
	 * @return boolean
	 */
	public boolean deleteProjekt(Projekt projekt) {

		String sql1 = "DELETE FROM aufwand WHERE projekt=:projektName";
		String sql2 = "DELETE FROM phasen WHERE projekt=:projektName";
		String sql3 = "DELETE FROM projekte WHERE name=:projektName";
		String sql4 = "DELETE FROM kompetenzen WHERE projekt=:projektName";
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