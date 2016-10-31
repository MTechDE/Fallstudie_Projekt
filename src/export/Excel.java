package export;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
		for (int i = 0; i < phasen.size(); i++) {
			header1.createCell(i + 1).setCellValue(phasen.get(i).getName());
		}

		// Erzeuge Kompetenzen Spalte
		for (int i = 0; i < projekt.getKompetenzen().size(); i++) {
			Row kompetenzenRow = sheet1.createRow(4 + i);
			kompetenzenRow.createCell(0).setCellValue(projekt.getKompetenzen().get(i).getName());

		}

		// Berechne gesamt PT pro Phase pro Kompetenz
		for (int p = 0; p < phasen.size(); p++) {
			for (int k = 0; k < kompetenzen.size(); k++) {
				double gesPT = 0;
				for (int a = 0; a < phasen.get(p).getAufwände().size(); a++) {
					if (phasen.get(p).getAufwände().get(a).getZugehoerigkeit().equals(kompetenzen.get(k).getName())) {
						gesPT += phasen.get(p).getAufwände().get(a).getPt();
					}
				}
				int row = 4 + k;
				int cell = 1 + p;

				sheet1.getRow(row).createCell(cell).setCellValue(gesPT);

			}
		}

		sheet1.createRow(6 + kompetenzen.size());
		sheet1.getRow((6 + kompetenzen.size())).createCell(0).setCellValue("Mit Risikozuschlag");

		header1 = sheet1.createRow(8 + kompetenzen.size());
		header1.createCell(1).setCellValue("Phasen bzw. Aktivitäten");
		sheet1.addMergedRegion(new CellRangeAddress(8 + kompetenzen.size(), 8 + kompetenzen.size(), 1, headerLenght));

		// Erzeuge Phasen Header
		header1 = sheet1.createRow(9 + kompetenzen.size());
		header1.createCell(0).setCellValue("Technologien / Kompetenzen");
		for (int i = 0; i < phasen.size(); i++) {
			header1.createCell(i + 1).setCellValue(phasen.get(i).getName());
		}

		// Erzeuge Kompetenzen Spalte
		for (int i = 0; i < projekt.getKompetenzen().size(); i++) {
			Row kompetenzenRow = sheet1.createRow(10 + kompetenzen.size() + i);
			kompetenzenRow.createCell(0).setCellValue(projekt.getKompetenzen().get(i).getName());

		}

		// Berechne gesamt PT pro Phase pro Kompetenz
		for (int p = 0; p < phasen.size(); p++) {
			for (int k = 0; k < kompetenzen.size(); k++) {
				double gesPT = 0;
				for (int a = 0; a < phasen.get(p).getAufwände().size(); a++) {
					if (phasen.get(p).getAufwände().get(a).getZugehoerigkeit().equals(kompetenzen.get(k).getName())) {
						gesPT += phasen.get(p).getAufwände().get(a).getPt()
								* (1 + (kompetenzen.get(k).getRisikozuschlag() / 100));
					}
				}
				int row = 10 + kompetenzen.size() + k;
				int cell = 1 + p;

				sheet1.getRow(row).createCell(cell).setCellValue(gesPT);

			}
		}

		// Erstelle erweiterte Ansicht

		// Erzeuge Header

		// Berechne die Anzahl der Jahre
		projekt.getPhasen().sort(Comparator.comparing(Phase::getStartDate));
		int startJahr = Integer.parseInt(projekt.getPhasen().get(0).getStartDate().substring(0, 4));
		projekt.getPhasen().sort(Comparator.comparing(Phase::getEndDate));
		int endJahr = Integer
				.parseInt(projekt.getPhasen().get(projekt.getPhasen().size() - 1).getEndDate().substring(0, 4));
		int anzJahre = endJahr - startJahr;
		if(anzJahre == 0)
			anzJahre = 1;
		
		// Erzeuge Kompetenzen Spalte
		for (int i = 0; i < projekt.getKompetenzen().size(); i++) {
			Row kompetenzenRow = sheet2.createRow(4 + i);
			kompetenzenRow.createCell(0).setCellValue(projekt.getKompetenzen().get(i).getName());

		}

		// Erzeuge Quartal Spalten
		header1 = sheet2.createRow(3);
		for (int j = 0; j < anzJahre; j++) {
			for(int i = 1; i <= 4; i++){
				header1.createCell((j * 4) + i).setCellValue("Q" + i + " - " + String.valueOf(startJahr + j));
			}			
		}
		
		// Erzeuge Phasen Header
		header1 = sheet2.createRow(4);
		header1.createCell(0).setCellValue("Technologien / Kompetenzen");
		
		// Weise Phasen den richtigen Quartalen zu
		for (int j = 0; j < anzJahre; j++) {
			for(int i = 1; i <= 4; i++){
				System.out.println("Jahr: " + (startJahr + j) + " Quartal: " + i);
			}
		}
		
		for (int i = 0; i < phasen.size(); i++) {
			header1.createCell(i + 1).setCellValue(phasen.get(i).getName());
		}
		
		// Erzeuge Phasen Überschriften
		

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
