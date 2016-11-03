package export;

import java.io.FileOutputStream;
import java.util.Comparator;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import projektDaten.Kompetenz;
import projektDaten.Phase;
import projektDaten.Projekt;

/**
 * Die Excel Klasse exportiert das Projekt mit Hilfe der Apache POI Bibliothek
 * in einem xlsx Format an den gewählten Speicherort.
 * 
 * @author Daniel Sogl
 *
 */
public class Excel {

	/**
	 * Erzeugt eine Excel Datei mit zwei Seiten
	 * @param projekt
	 * @param path
	 */
	public static void ExportToExcel(Projekt projekt, String path) {

		// Variablen
		List<Kompetenz> kompetenzen;
		List<Phase> phasen;
		FileOutputStream fileOut;

		// Initialisiere Daten
		kompetenzen = projekt.getKompetenzen(); // Kompetenzenliste laden
		phasen = projekt.getPhasen(); // Phasenliste laden

		// Erzeuge XSS Workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Erzeuge Sheets
		XSSFSheet sheet1 = workbook.createSheet("Projektübersicht");
		XSSFSheet sheet2 = workbook.createSheet("Erweiterte Projektübersicht");

		// Erzeuge Header
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

		// Erstelle Erweiterte-Ansicht

		// Erzeuge Header

		// Berechne die Anzahl der Jahre
		projekt.getPhasen().sort(Comparator.comparing(Phase::getStartDate));
		int startJahr = Integer.parseInt(projekt.getPhasen().get(0).getStartDate().substring(0, 4));
		projekt.getPhasen().sort(Comparator.comparing(Phase::getEndDate));
		int endJahr = Integer
				.parseInt(projekt.getPhasen().get(projekt.getPhasen().size() - 1).getEndDate().substring(0, 4));

		// Es muss mindestens ein Jahr geben
		int anzJahre = (endJahr - startJahr) + 1;

		// Erzeuge Kompetenzen Spalte
		for (int i = 0; i < projekt.getKompetenzen().size(); i++) {
			Row kompetenzenRow = sheet2.createRow(4 + i);
			kompetenzenRow.createCell(0).setCellValue(projekt.getKompetenzen().get(i).getName());
		}

		// Erzeuge Quartal Spalten
		header1 = sheet2.createRow(3);
		header1.createCell(0).setCellValue("Technologien / Kompetenzen");
		for (int j = 0; j < anzJahre; j++) {
			for (int i = 1; i <= 4; i++) {
				header1.createCell((j * 4) + i).setCellValue("Q" + i + " - " + String.valueOf(startJahr + j));
			}
		}

		// Weise die PT dem richtigen Quartal sowie der richtigen Kompetenz zu

		// Durchlaufe jedes Jahr und dessen Quartale um die passenden PT
		// herauszufiltern

		for (int k = 0; k < kompetenzen.size(); k++) {
			for (int j = 0; j < anzJahre; j++) {
				// 4 Quartale pro Jahr
				for (int i = 1; i <= 4; i++) {
					double gesPT = 0;
					for (int p = 0; p < phasen.size(); p++) {
						for (int a = 0; a < phasen.get(p).getAufwände().size(); a++) {
							if (phasen.get(p).getAufwände().get(a).getZugehoerigkeit()
									.equals(kompetenzen.get(k).getName())
									&& (startJahr + j) == Integer
											.parseInt(phasen.get(p).getStartDate().substring(0, 4))) {

								// Berechne Anzahl der Jahre zwischen Start und
								// Enddatum
								int difJahre = Integer.parseInt(phasen.get(p).getEndDate().substring(0, 4))
										- Integer.parseInt(phasen.get(p).getStartDate().substring(0, 4));

								// Berechne die Anzahl der Quartale durch die
								// die Phase geht

								int startMonat = Integer.parseInt(phasen.get(p).getStartDate().substring(5, 7));
								int endMonat = Integer.parseInt(phasen.get(p).getEndDate().substring(5, 7));

								int startQuartal, endQuartal;

								if (startMonat == 1 || startMonat == 2 || startMonat == 3)
									startQuartal = 1;
								else if (startMonat == 4 || startMonat == 5 || startMonat == 6)
									startQuartal = 2;
								else if (startMonat == 7 || startMonat == 8 || startMonat == 9)
									startQuartal = 3;
								else
									startQuartal = 4;

								if (endMonat == 1 || endMonat == 2 || endMonat == 3)
									endQuartal = 1;
								else if (endMonat == 4 || endMonat == 5 || endMonat == 6)
									endQuartal = 2;
								else if (endMonat == 7 || endMonat == 8 || endMonat == 9)
									endQuartal = 3;
								else
									endQuartal = 4;

								if (startQuartal == endQuartal && difJahre == 0 && startQuartal == i) {
									gesPT += (phasen.get(p).getAufwände().get(a).getPt()
											* (1 + (kompetenzen.get(k).getRisikozuschlag() / 100)));
								}
								if (startQuartal != endQuartal && difJahre == 0 && (startQuartal == i || endQuartal == i)) {
									gesPT += (phasen.get(p).getAufwände().get(a).getPt()
											* (1 + (kompetenzen.get(k).getRisikozuschlag() / 100))) / (endQuartal - startQuartal + 1);
								}
							}
						}

					}

					// Speichere die PT des Quartals
					int row = 4 + k;
					int cell = i + j;

					// Reihe = Kompetenz
					// Spalte = Jahr + Quartal
					sheet2.getRow(row).createCell(cell).setCellValue(gesPT);
				}
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
