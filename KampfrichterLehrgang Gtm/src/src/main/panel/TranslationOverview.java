package src.main.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import server.Video;
import src.main.Controller;
import src.main.DatenbankController;
import src.main.listener.TranslationButtonActionListener;
import src.main.videoplayer.VideoParser;

public class TranslationOverview extends CenterPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2879746137707156229L;
	private static final short GREEN = 0;
	private static final short YELLOW= 1;
	private static final short RED = 2;
	private JTable ergebnisTabelle;
	private JPanel tabellePanel;
	private DefaultTableModel model;
	
	public TranslationOverview(){

		setBackground(Color.WHITE);
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(boxLayout);
		erstelleTabelle();
	}
	private void erstelleTabelle(){
		String[] spaltenNamen = {"ID", "Aktuelle Übersetzung","Neue Übesetzung", "Aktion", "Validierung"};
		model = new DefaultTableModel(spaltenNamen, 0);
		ergebnisTabelle = new JTable(model);
		ergebnisTabelle.setRowHeight(30);
		tabellePanel = erstelleEinPanel(null, null, null, null);

		JScrollPane scroll = new JScrollPane(ergebnisTabelle);
		ergebnisTabelle.getColumnModel().getColumn(0).setMaxWidth(30);
		ergebnisTabelle.getColumnModel().getColumn(3)
				.setCellRenderer(new ButtonRenderer("Neue Übersetzung übernehmen"));
		ergebnisTabelle.getColumnModel().getColumn(3)
				.setCellEditor(new ButtonEditor(new JCheckBox(), "Neue Übersetzung übernehmen", new TranslationButtonActionListener()));
		ergebnisTabelle.getColumnModel().getColumn(4)
		.setCellRenderer(new ButtonRenderer("Übersetzung ok"));
ergebnisTabelle.getColumnModel().getColumn(4)
		.setCellEditor(new ButtonEditor(new JCheckBox(), "Übersetzung ok", new TranslationButtonActionListener()));
		scroll.setPreferredSize(new Dimension(1000, 700));
		tabellePanel.add(scroll);
		fuelleTabelle();
		add(tabellePanel);
	}
	/**
	 * Fügt eine neue Zeile der Tabelle hinzu
	 * 
	 * @param uebersetzung
	 *           	Name des Videos
	 */
	public void addTabelleZeile(String uebersetzung, Video video) {
		int size = model.getColumnCount();

		// einen neuen Vector mit Daten herstellen
		Vector<Object> vector = new Vector<Object>(size);
		vector.add(video.getId());
		vector.add(uebersetzung);
		vector.add("Neue Übersetzung eingeben...");

		// eine neue Row hinzufügen
		model.addRow(vector);

	}
	private void fuelleTabelle(){
		VideoParser parser = new VideoParser();
		ArrayList<Video> videos = parser.mappeVideosVonAmpel(YELLOW);
		videos.addAll(parser.mappeVideosVonAmpel(RED));
		for(Video current : videos){
			addTabelleZeile(current.getName(), current);
		}
	}
	/**
	 * Erstellt ein Standardpanel
	 * 
	 * @param layout
	 *            Layout-Manager
	 * @param prefSize
	 * @param maxSize
	 * @param minSize
	 * @return Panel mit gewünschten Attributen
	 */
	private JPanel erstelleEinPanel(LayoutManager layout, Dimension prefSize,
			Dimension maxSize, Dimension minSize) {
		JPanel neuesPanel = new JPanel();
		neuesPanel.setBackground(Color.WHITE);
		if (layout != null)
			neuesPanel.setLayout(layout);
		if (prefSize != null)
			neuesPanel.setPreferredSize(prefSize);
		if (maxSize != null)
			neuesPanel.setMaximumSize(maxSize);
		if (minSize != null)
			neuesPanel.setMinimumSize(minSize);

		return neuesPanel;
	}
	public JTable getTabelle(){
		return ergebnisTabelle;
	}
	public static void main(String[] args){
		TranslationOverview panel = new TranslationOverview();
		Controller.setTranslationOverview(panel);
		Controller.setDatenbankController(new DatenbankController());
		Controller.setVidparser(new VideoParser());
		JFrame frame = new JFrame();
		frame.setSize(800, 800);
		frame.getContentPane().add(new JScrollPane(panel));
		frame.setVisible(true);
	}
}

