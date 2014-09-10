package src.main.pdfviewer;

import java.io.IOException;
/**
 * Darstellung von PDF-Dateien
 * @author Michael
 *
 */
public class PDFReader {
	/**
	 * Es wird der portable Sumatra-PDF Reader verwendet
	 * Quelle: http://blog.kowalczyk.info/software/sumatrapdf/free-pdf-reader-de.html
	 * @param pfad der PDF-Datei
	 */
	public PDFReader(String pfad){
		try {
			Process process = new ProcessBuilder("src/src/main/pdfviewer/SumatraPDF.exe", pfad ).start();
		} catch (IOException e) {
			System.err.println("Fehler beim Lesen der PDF!");
			e.printStackTrace();
		}
	}
}
