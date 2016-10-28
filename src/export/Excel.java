package export;

import java.io.FileOutputStream;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import projektDaten.Kompetenz;
import projektDaten.Phase;
import projektDaten.Projekt;

/**
 * Exportiert Projekte in eine CSV oder Text Datei
 * 
 * @author mytec
 *
 */
public class Excel {

	public static void ExportToExcel(Projekt projekt, String path) {

		// Variablen
		List<Kompetenz> kompetenzen;
		List<Phase> phasen;
		FileOutputStream fileOut;

		// Initalisiere Daten
		kompetenzen = projekt.getKompetenzen(); // Kompetenzenliste laden
		phasen = projekt.getPhasen(); // Phasenliste laden
		

		// Erzeuge XSS Workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Erzeuge Sheets
		XSSFSheet sheet1 = workbook.createSheet("Projektübersicht");
		XSSFSheet sheet2 = workbook.createSheet("Erweiterte Projektübersicht");

		// Erzeue Header
		Row header1 = sheet1.createRow(0);
		Row header2 = sheet2.createRow(0);

		header1.createCell(0)
				.setCellValue(projekt.getName() + " - Ersteller: " + projekt.getErsteller() + " - Übersicht");
		header2.createCell(0).setCellValue(
				projekt.getName() + " - Ersteller: " + projekt.getErsteller() + " - Erweiterte Übersicht");

		sheet1.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
		sheet2.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
		

		
		

		// Erzeuge Tabellenstruktur für die einfache Übersicht

		int headerLenght = 0;
		if (projekt.getPhasen().size() < 3)
			headerLenght = 4;
		else
			headerLenght = projekt.getPhasen().size();

		header1 = sheet1.createRow(2);
		header1.createCell(1).setCellValue("Phasen bzw. Aktivitäten");
		sheet1.addMergedRegion(new CellRangeAddress(2, 2, 1, headerLenght));

		// Erzeuge Phasen Header
		header1 = sheet1.createRow(3);
		header1.createCell(0).setCellValue("Technologien / Kompetenzen");
		for (int i = 0; i < projekt.getPhasen().size(); i++) {
			header1.createCell(i + 1).setCellValue(projekt.getPhasen().get(i).getName());
		}

		// Erzeuge Kompetenzen Spalte
		for (int i = 0; i < projekt.getKompetenzen().size(); i++) {
			Row kompetenzenRow = sheet1.createRow(4 + i);
			kompetenzenRow.createCell(0).setCellValue(projekt.getKompetenzen().get(i).getName());
			
		}

		// Berechne gesamt PT pr Phase pro Kompetenz
		for (int p = 0; p < projekt.getPhasen().size(); p++) {
			for (int k = 0; k < projekt.getKompetenzen().size(); k++) {
				double gesPT = 0;
				for (int a = 0; a < projekt.getPhasen().get(p).getAufwände().size(); a++) {
					if (projekt.getPhasen().get(p).getAufwände().get(a).getZugehoerigkeit()
							.equals(projekt.getKompetenzen().get(k).getName())) {
						gesPT += projekt.getPhasen().get(p).getAufwände().get(a).getPt();
					}
				}
				int row = 4 + k;
				int cell = 1 + p;
				
				sheet1.getRow(row).createCell(cell).setCellValue(gesPT);

			}
		}

		// Schreibe die Excel Datei in den angegebenen Pfad
		try {
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
