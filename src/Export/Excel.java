package export;

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

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

		Workbook wb = new HSSFWorkbook();
		CreationHelper createHelper = wb.getCreationHelper();	
		FileOutputStream fileOut;
		
		// Erzeuge zwei Excel Seiten
		Sheet sheet = wb.createSheet("Ohne Risikozuschlag");
		Sheet sheetRisiko = wb.createSheet("Mit Risikozuschlag");
		
		// Die Excel Datei wird im Querformat gedruckt
		PrintSetup printSetup1 = sheet.getPrintSetup();
	    printSetup1.setLandscape(true);
		PrintSetup printSetup2 = sheetRisiko.getPrintSetup();
	    printSetup2.setLandscape(true);
	    
        //the header row: centered text in 48pt font
        Row headerRow = sheet.createRow(1);
        headerRow.setHeightInPoints(12.75f);
        for (int i = 0; i < projekt.getPhasen().size(); i++) {
            Cell cell = headerRow.createCell(	i + 1);
            cell.setCellValue(projekt.getPhasen().get(i).getName());
        }



		// Schreibe die Excel Datei in den angegebenen Pfad
		try {
			fileOut = new FileOutputStream(path);
			wb.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
