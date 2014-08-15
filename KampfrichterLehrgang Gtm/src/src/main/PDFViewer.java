/*
 * ===========================================
 * Java Pdf Extraction Decoding Access Library
 * ===========================================
 *
 * Project Info:  http://www.idrsolutions.com
 * Help section for developers at http://www.idrsolutions.com/java-pdf-library-support/
 *
 * List of all example and a link to zip at http://www.idrsolutions.com/java-code-examples-for-pdf-files/
 *
 * (C) Copyright 1997-2014, IDRsolutions and Contributors.
 *
 *   This file is part of JPedal
 *
 This source code is copyright IDRSolutions 2012


 *
 *
 * ---------------
 * PDFViewer.java
 * ---------------
 */

package src.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.*;

import org.jpedal.PdfDecoder;
import org.jpedal.examples.viewer.Commands;
import org.jpedal.examples.viewer.Viewer;

public class PDFViewer {

	public static void main(String[] args) {

		Dimension dim;
		float scale = 0;
		String source = "../../pdf/01_Boden.pdf";
		JFrame frame = new JFrame("PDF File: "+source);
		frame.setPreferredSize(new Dimension(300,300));
//		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setBackground(Color.black);
		
		PdfDecoder decoder = new PdfDecoder();
		
		try {
			decoder.openPdfFile(source);
			decoder.decodePage(1);
			dim = decoder.getSize();
			// A bit of math...   80 width/100 PDF = 0.8 (80%)
			// 80 width/50 PDF = 1.6 (160%)
			decoder.setPageParameters(scale, 1);
		} catch (Exception e) {
			System.err.println("Error: "+e.getMessage());
		}
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(decoder);
		/*scroll.setHorizontalScrollBarPolicy(0);
		scroll.setVerticalScrollBarPolicy(0);*/
		Container pane = new Container();
		pane.setLayout(new FlowLayout());
		pane.setBackground(Color.white);
		pane.setPreferredSize(new Dimension(100, 100));
		frame.setContentPane(pane);

		pane.add(decoder);
		
		frame.pack();
		frame.setVisible(true);
	}
}