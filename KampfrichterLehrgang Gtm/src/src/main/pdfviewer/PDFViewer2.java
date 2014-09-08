package src.main.pdfviewer;

import java.io.File;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import de.intarsys.pdf.parser.COSLoadException;
import de.intarsys.pdf.pd.PDDocument;
import de.intarsys.tools.locator.FileLocator;

public class PDFViewer2 extends JPanel
{
	private PDDocument doc;
	private PDFCanvas pdfCanvas;
	private JScrollPane scrollPane;
	private JPanel controlPanel;
	private Action firstAction, lastAction, nextAction, prevAction, zoomInAction, zoomOutAction;
	private JButton firstPageButton, lastPageButton, nextPageButton, previousPageButton, zoomInButton, zoomOutButton;

	public PDFViewer2(String pdfFilePath)
	{
		setLayout(new BorderLayout());
		createControlPanel();
		add(controlPanel, BorderLayout.NORTH);

		pdfCanvas = new PDFCanvas();
		scrollPane = new JScrollPane(pdfCanvas,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane, BorderLayout.CENTER);

		FileLocator locator = new FileLocator(pdfFilePath);
		try
		{
			this.doc = PDDocument.createFromLocator(locator);
		}
		catch (Exception e) { System.err.println("Error loading file " + pdfFilePath + "!"); }
		pdfCanvas.setDoc(this.doc);
	}

	private void createActions()
	{
		firstAction = new AbstractAction("First") {
			public void actionPerformed(ActionEvent e) {
				pdfCanvas.selectFirstPage();
			}
		};

		lastAction = new AbstractAction("Last") {
			public void actionPerformed(ActionEvent e) {
				pdfCanvas.selectLastPage();
			}
		};
		nextAction = new AbstractAction("Next") {
			public void actionPerformed(ActionEvent e) {
				pdfCanvas.selectNextPage();
			}
		};

		prevAction = new AbstractAction("Prev") {
			public void actionPerformed(ActionEvent e) {
				pdfCanvas.selectPreviousPage();
			}
		};

		zoomInAction = new AbstractAction("Zoom in") {
			public void actionPerformed(ActionEvent e) {
				pdfCanvas.setScale(pdfCanvas.getScale() * 1.2);
			}
		};

		zoomOutAction = new AbstractAction("Zoom out") {
			public void actionPerformed(ActionEvent e) {
				pdfCanvas.setScale(pdfCanvas.getScale() / 1.2);
			}
		};
	}

	private void createControlPanel()
	{
		createActions();
		controlPanel = new JPanel();
		firstPageButton = new JButton(firstAction);
		lastPageButton = new JButton(lastAction);
		nextPageButton = new JButton(nextAction);
		previousPageButton = new JButton(prevAction);
		zoomInButton = new JButton(zoomInAction);
		zoomOutButton = new JButton(zoomOutAction);

		controlPanel.add(firstPageButton);
		controlPanel.add(lastPageButton);
		controlPanel.add(nextPageButton);
		controlPanel.add(previousPageButton);
		controlPanel.add(zoomInButton);
		controlPanel.add(zoomOutButton);

	}

	public static void main(String[] args)
	{
		if (args.length < 1) {
			System.out.println("No argument given");
			System.exit(1);
		}

		JFrame frame = new JFrame("PDF File: " + args[0]);
		frame.setPreferredSize(new Dimension(300,300));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setBackground(Color.black);

		PDFViewer2 pdfViewer = new PDFViewer2(args[0]);
		
		frame.setContentPane(pdfViewer);
		
		frame.pack();
		frame.setVisible(true);
	}
}
