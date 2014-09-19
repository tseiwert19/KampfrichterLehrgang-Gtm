package src.main.listener;

import src.main.components.CorrectPath;

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
		String pfad = "/pdf/";

		switch (command) {
		case "Boden":
			pfad = new CorrectPath(pfad + "01_Boden.pdf").getPath();
			break;
		case "Pauschenpferd":
			pfad = new CorrectPath(pfad + "02_Pauschenpferd.pdf").getPath();
			break;
		case "Ringe":
			pfad = new CorrectPath(pfad + "03_Ringe.pdf").getPath();
			break;
		case "Sprung":
			pfad = new CorrectPath(pfad + "04_Sprung.pdf").getPath();
			break;
		case "Barren":
			pfad = new CorrectPath(pfad + "05_Barren.pdf").getPath();
			break;
		case "Reck":
			pfad = new CorrectPath(pfad + "06_Reck.pdf").getPath();
			break;
		}
		try {
			Desktop.getDesktop().open(new File(pfad));
		} catch (IOException e1) {
			PDFReader reader = new PDFReader(pfad);
		}

	}

}
