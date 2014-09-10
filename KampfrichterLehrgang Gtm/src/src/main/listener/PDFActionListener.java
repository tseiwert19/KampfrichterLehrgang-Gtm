package src.main.listener;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import src.main.pdfviewer.PDFReader;
/**
 * ActionListener der ein PDFDokument anzeigt
 * @author Michael
 *
 */
public class PDFActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		String pfad = "src/pdf/";

		switch (command) {
		case "Boden":
			pfad = pfad + "01_Boden.pdf";
			break;
		case "Pauschenpferd":
			pfad = pfad + "02_Pauschenpferd.pdf";
			break;
		case "Ringe":
			pfad = pfad + "03_Ringe.pdf";
			break;
		case "Sprung":
			pfad = pfad + "04_Sprung.pdf";
			break;
		case "Barren":
			pfad = pfad + "05_Barren.pdf";
			break;
		case "Reck":
			pfad = pfad + "06_Reck.pdf";
			break;
		}
		try {
			Desktop.getDesktop().open(new File(pfad));
		} catch (IOException e1) {
			PDFReader reader = new PDFReader(pfad);
		}

	}

}
