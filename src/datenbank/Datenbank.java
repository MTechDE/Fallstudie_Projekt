package datenbank;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import model.Aufwand;
import model.Kompetenz;
import model.Phase;
import model.Projekt;

/**
 * Schnittstelle zwischen der MySQL Datenbank und dem Programm
 * 
 * @author Daniel Sogl
 */

public class Datenbank {

	// Variablen
	private Sql2o sql2o;

	/**
	 * DB Anmeldedaten werden im Konstruktor konfiguriert.
	 */
	public Datenbank() {
		sql2o = new Sql2o("jdbc:mysql://lolstats.org:3306/fallstudie", "fallstudie_user", "bYv735!r");
	}

	/**
	 * Testet die Verbindung zur Datenbank
	 * 
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
	 * Speichert ein projektDaten und alle enthaltenen Phasen, Kompetenzen und
	 * Aufwände. Bereits vorhandene Daten werden überschrieben (UPDATE).
	 * 
	 * @param projekt das zu speichernde Projekt
	 * @return boolean
	 */
	private void speicherProjekt(Projekt projekt) {

		// Bestimme das Start- und Enddatum
		if (!projekt.getPhasen().isEmpty()) {
			// Sortiere die Liste anhand des Startdatums
			projekt.getPhasen().sort(Comparator.comparing(Phase::getStartDate));
			projekt.setStartDate(projekt.getPhasen().get(0).getStartDate());
			// Sortiere Liste anhand des Enddatum
			projekt.getPhasen().sort(Comparator.comparing(Phase::getEndDate));
			projekt.setEndDate(projekt.getPhasen().get(projekt.getPhasen().size() - 1).getEndDate());
		} else {
			projekt.setStartDate(null);
			projekt.setEndDate(null);
		}

		String sql = "INSERT INTO projekte(name, ersteller, gemeldet, startDate, endDate, meldeDatum) "
				+ "VALUES(:name, :ersteller, :gemeldet, :startDate, :endDate, :meldeDatum)";

		try (Connection con = sql2o.open()) {
			con.createQuery(sql).addParameter("name", projekt.getName())
					.addParameter("ersteller", projekt.getErsteller())
					.addParameter("gemeldet", projekt.isgemeldet())
					.addParameter("startDate", projekt.getStartDate()).addParameter("endDate", projekt.getEndDate())
					.addParameter("meldeDatum", projekt.getMeldeDatum()).executeUpdate();
		} catch (Sql2oException e) {
			System.out.println(e.getMessage());
		}

		sql = "INSERT INTO phasen(name, projekt, startDate, endDate) VALUES(:name, :projekt, :startDate, :endDate)";
		if (!projekt.getPhasen().isEmpty()) {
			try (Connection con = sql2o.beginTransaction()) {
				Query query = con.createQuery(sql);
				projekt.getPhasen().forEach(phase -> {
					query.addParameter("name", phase.getName()).addParameter("projekt", projekt.getName())
					.addParameter("startDate", phase.getStartDate()).addParameter("endDate", phase.getEndDate())
					.addToBatch();
				});
				query.executeBatch();
				con.commit();
			} catch (Sql2oException e) {
				System.out.println(e.getMessage());
			}
		}

		// Speichere alle beteiligten Aufwände der jeweiligen Phasen in die DB

		sql = "INSERT INTO aufwand(person, phase, projekt, pt, zugehoerigkeit) "
				+ "VALUES(:person, :phase, :projekt, :pt, :zugehoerigkeit)";

		if (!projekt.getPhasen().isEmpty()) {
			try (Connection con = sql2o.beginTransaction()) {
				Query query = con.createQuery(sql);
				for (Phase phase : projekt.getPhasen()) {
					if (!phase.getAufwände().isEmpty()) {
						for (Aufwand aufwand : phase.getAufwände()) {
							query.addParameter("person", aufwand.getName()).addParameter("phase", phase.getName())
									.addParameter("projekt", projekt.getName()).addParameter("pt", aufwand.getPt())
									.addParameter("zugehoerigkeit", aufwand.getZugehoerigkeit()).addToBatch();
						}
					}
				}
				query.executeBatch();

				if (!projekt.getKompetenzen().isEmpty()) {
					sql = "INSERT INTO kompetenzen (name, projekt, risikozuschlag) "
							+ "VALUES(:name, :projekt, :risikozuschlag)";
					query = con.createQuery(sql);

					for (Kompetenz kompetenz : projekt.getKompetenzen()) {
						query.addParameter("name", kompetenz.getName()).addParameter("projekt", projekt.getName())
								.addParameter("risikozuschlag", kompetenz.getRisikozuschlag()).addToBatch();
					}
					query.executeBatch();
				}

				con.commit();
			} catch (Sql2oException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * Ein projektDaten wird anhand des übergebenen projektDaten Objektes aus
	 * der Datenbank geholt und als komplettes projektDaten zurückgegeben.
	 * 
	 * @param projekt das aus der Datenbank zu holende Projekt
	 * @return projektDaten
	 */
	public Projekt getProjekt(Projekt projekt) {

		Projekt newprojekt = projekt;
		List<Phase> phasen = new ArrayList<Phase>();
		List<Aufwand> personen = new ArrayList<Aufwand>();
		List<Kompetenz> kompetenzen = new ArrayList<Kompetenz>();

		try (Connection con = sql2o.open()) {

			// Hole Phasen anhand des Projektnamen
			String sql = "SELECT name, startDate, endDate FROM phasen WHERE projekt=:projektName";
			phasen = con.createQuery(sql).addParameter("projektName", newprojekt.getName())
					.executeAndFetch(Phase.class);

			// Hole Alle Personen anhand des Projektnamen
			sql = "SELECT person AS name, zugehoerigkeit, pt from aufwand a "
					+ "WHERE a.phase=:phasenName AND a.projekt=:projektName ORDER BY person DESC";
			if (!phasen.isEmpty()) {
				for (Phase phase : phasen) {
					personen = con.createQuery(sql).addParameter("phasenName", phase.getName())
							.addParameter("projektName", newprojekt.getName()).executeAndFetch(Aufwand.class);
					phase.setAufwände(personen);
				}
				newprojekt.setPhasen((ArrayList<Phase>) phasen);
			}

			// Hole die Kompetenzen aus der DB
			sql = "SELECT DISTINCT  name, risikozuschlag FROM kompetenzen WHERE projekt=:projektName";
			kompetenzen = con.createQuery(sql).addParameter("projektName", newprojekt.getName())
					.executeAndFetch(Kompetenz.class);
			if (!kompetenzen.isEmpty())
				newprojekt.setKompetenzen(kompetenzen);
			
			// Überprüfe ob Phasen und/oder Kompetenzen existieren
			if (phasen.isEmpty())
				newprojekt.setPhasen(phasen);
			if (kompetenzen.isEmpty())
				newprojekt.setKompetenzen(kompetenzen);

		} catch (Sql2oException e) {
			System.out.println(e.getMessage());
		}

		return newprojekt;
	}

	/**
	 * Ein projektDaten wird komplett neu in die Datenbank geschrieben. Dazu
	 * wird es zunächst komplett gelöscht und anschließend neu in die Datenbank
	 * geschrieben.
	 * 
	 * @param projekt das zu updatende Projekte
	 * @return boolean
	 */
	public void updateProjekt(Projekt projekt) {
		this.deleteProjekt(projekt);
		this.speicherProjekt(projekt);
	}

	/**
	 * Alle Projekte die in der Datenbank gefunden wurden, werden in einer Liste
	 * zurückgegeben.
	 * 
	 * @return eine Liste der Projekte in der Datenbank
	 */
	public List<Projekt> getProjekte() {
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
	 * Alle Daten welche mit einem projektDaten zu tun haben (Phasen,
	 * Kompetenzen, Aufwände), werden anhand des Projektname aus der Datenbank
	 * gelöscht.
	 * 
	 * @param projekt Das zu löschende projektDaten
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