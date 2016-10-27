package export;

import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
		int kompetenzLength, phasenLenght;
		List<Kompetenz> kompetenzen;
		List<Phase> phasen;
		FileOutputStream fileOut;

		// Initalisiere Daten
		kompetenzen = projekt.getKompetenzen(); // Kompetenzenliste laden
		phasen = projekt.getPhasen(); // Phasenliste laden
		phasenLenght = phasen.size() + 1; // Anzahl Reihen indem Phasenanzahl genommen wird
		kompetenzLength = kompetenzen.size(); // Anzahl Kompetenzen

		// Erzeuge XSS Workbook
        XSSFWorkbook workbook = new XSSFWorkbook(); 
        
        // Erzeuge Sheets
        XSSFSheet sheet1 = workbook.createSheet("Projektübersicht");
        XSSFSheet sheet2 = workbook.createSheet("Erweiterte Projektübersicht");
		
		
		
		
		

		// Schreibe die Excel Datei in den angegebenen Pfad
		try {
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
