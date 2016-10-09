package Export;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import Projekt.Projekt;

/**
 * Exportiert Projekte in eine CSV oder Text Datei
 * @author mytec
 *
 */
public class Excel {

	public static boolean ExportToExcel(List<Projekt> projekte, String path) {

		String file = path;
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(file))) {
			// CSV Header
			writer.write("Projekt Name;");
			writer.write("Projekt Ersteller;");
			writer.write("Start Datum;");
			writer.write("End Datum;");
			writer.write("Abgeschickt?;");
			writer.write("\n");

			// Schreibe zeilenweise in die Datei
			for (Projekt projekt : projekte) {
				writer.write(projekt.getName() + ";");
				writer.write(projekt.getErsteller() + ";");
				writer.write(projekt.getStartDate() + ";");
				writer.write(projekt.getEndDate() + ";");
				if (projekt.isAbgeschickt())
					writer.write("Ja;");
				else
					writer.write("Nein;");

				writer.write("\n");
			}
			return true;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

}
