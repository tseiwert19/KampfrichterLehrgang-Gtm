package src.main.pdfviewer;

import java.io.IOException;

import src.main.components.CorrectPath;

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
		String pdfViewerFile=new CorrectPath("/src/main/pdfviewer/SumatraPDF.exe").getPath();
		System.out.println("PDFReader " + pdfViewerFile);
		try {
			Process process = new ProcessBuilder(pdfViewerFile, pfad ).start();
			//Process process = new ProcessBuilder("src/src/main/pdfviewer/SumatraPDF.exe", pfad ).start();
		} catch (IOException e) {
			System.err.println("Fehler beim Lesen der PDF!");
			e.printStackTrace();
		}
	}
}
