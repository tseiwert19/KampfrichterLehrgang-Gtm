package src.main.pdfviewer;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

import org.jpedal.PdfDecoder;

public class PDFViewer {

	public static void main(String[] args) {

		String source = "src/pdf/01_Boden.pdf";
		JFrame frame = new JFrame("PDF File: "+source);
		frame.setPreferredSize(new Dimension(300,300));
//		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setBackground(Color.black);
		
		PdfDecoder decoder = new PdfDecoder(true);
		decoder.setPreferredSize(new Dimension(300,300));
		try {
			decoder.openPdfFile(source);
			decoder.decodePage(1);
			decoder.setPageParameters(1,1);
		} catch (Exception e) {
			System.err.println("Error: "+e.getMessage());
		}
		

		frame.add(decoder);
		
		frame.pack();
		frame.setVisible(true);
	}
}
