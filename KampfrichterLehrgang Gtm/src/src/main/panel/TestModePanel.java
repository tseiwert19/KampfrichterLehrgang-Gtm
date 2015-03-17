package src.main.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import src.main.Controller;
import src.main.DatenbankController;
import src.main.components.KariButton;
import src.main.listener.TestModeActionListener;
import src.main.listener.VideoButtonActionListener;
import src.main.videoplayer.MediaPlayer;
import src.main.videoplayer.Video;
import src.main.videoplayer.VideoParser;

/**
 * Testmodus User bekommt 10 zufällige Videos gezeigt und muss die Fragen
 * richtig beantworten
 * 
 * @author Michael
 *
 */
public class TestModePanel extends CenterPanel {
	private static final long serialVersionUID = -3560834471579393469L;
	private static final Color MY_RED = Color.decode("#b92d2e");
	private static final String RICHTIG = "/img/TestModus/richtig.png";
	private static final String FALSCH = "/img/TestModus/falsch.png";
	private static final String[] SCHWIERIGKEITEN = { "A", "B", "C", "D", "E",
			"F" };
	private static final String[] ELEMENTGRUPPEN = { "I", "II", "III", "IV",
			"V" };
	private MediaPlayer mediaPlayer;
	private ArrayList<Video> videos;
	private JRadioButton elementgruppeRb;
	private JRadioButton schwierigkeitsgradRb;
	private JPanel mediaPanel;
	private int aktuellesVideo;
	private TestModeActionListener testActionListener;
	private JPanel modusPanel;
	private JPanel ergebnisPanel;
	private JPanel tabellePanel;
	private ImageIcon richtigImg;
	private ImageIcon falschImg;
	private JPanel antwortPanel;
	private int richtigeAntworten;
	private ArrayList<KariButton> antwortButtons;
	private KariButton nextButton;
	private KariButton neuerTest;
	private JLabel endeErgebnis;
	private JTable ergebnisTabelle;
	private DefaultTableModel model;
	private JPanel neuerTestPanel;
	
	private DatenbankController controller;

	/**
	 * Konstruktor Setzt Layout und ruft Methode auf, die alle Komponenten
	 * erstellt
	 */
	public TestModePanel() {
		setBackground(Color.WHITE);
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(boxLayout);
		testActionListener = new TestModeActionListener();
		try {
			richtigImg = new ImageIcon(ImageIO.read(getClass().getResource(
					RICHTIG)));
			falschImg = new ImageIcon(ImageIO.read(getClass().getResource(
					FALSCH)));
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Fehler beim Einlesen der Icons!");
		}
		createAllComponents();

	}

	/**
	 * Erstellt folgende Panels: - ModusPanel - MediaPanel - AntwortPanel -
	 * ErgebnisPanel
	 */
	private void createAllComponents() {
		createModusPanel();
		createMediaPlayer();
		mediaPanel.setVisible(false);
		antwortPanel = new JPanel();
		antwortPanel.setVisible(false);
		add(antwortPanel);
		createErgebnisPanel();
		ergebnisPanel.setVisible(false);
		createNextButton();
		nextButton.setVisible(false);
	}

	/**
	 * Erstellt ErgebnisPanel Enthält den Antwortverlauf
	 */
	private void createErgebnisPanel() {
		ergebnisPanel = erstelleEinPanel(new FlowLayout(), null, null, null);
		JLabel ergebnis = new JLabel("Ihre Antworten: ");
		ergebnisPanel.add(ergebnis);
		add(ergebnisPanel);

	}

	/**
	 * Erstellt den "Nächste Aufgabe" Button
	 */
	private void createNextButton() {
		nextButton = erstelleEinButton("Nächstes Element", "", "next",
				testActionListener);
		nextButton.setAlignmentX(CENTER_ALIGNMENT);
		add(nextButton);
	}

	/**
	 * Resettet den Antwortverlauf
	 */
	private void resetErgebnisPanel() {
		ergebnisPanel.removeAll();
		ergebnisPanel.setVisible(true);
		JLabel ergebnis = new JLabel("Ihre Antworten: ");
		ergebnisPanel.add(ergebnis);
	}

	/**
	 * Erstellt das Panel für den MediaPlayer
	 * 
	 * @param videoPfad
	 */
	private void createMediaPlayer() {
		mediaPanel = erstelleEinPanel(new BorderLayout(), new Dimension(
				800, 480), new Dimension(
				800, 480), null);
		add(mediaPanel);
	}

	/**
	 * Erstellt ModusPanel Enthält das Menü zum Starten eines Tests
	 */
	private void createModusPanel() {
		GridLayout gridLayout = new GridLayout(3, 0, 0, 10);
		modusPanel = erstelleEinPanel(gridLayout, null,
				new Dimension(400, 400), null);

		elementgruppeRb = new JRadioButton(
				"<html><font size='5'>Elementgruppen abfragen</font></html>");
		elementgruppeRb.setSelected(true);
		elementgruppeRb.setBackground(Color.WHITE);
		schwierigkeitsgradRb = new JRadioButton(
				"<html><font size='5'>Schwierigkeitsgrad abfragen</font></html>");
		schwierigkeitsgradRb.setBackground(Color.WHITE);
		ButtonGroup radioGroup = new ButtonGroup();

		radioGroup.add(elementgruppeRb);
		radioGroup.add(schwierigkeitsgradRb);

		JLabel label = new JLabel(
				"<html><b><font size=\"6\">Wählen Sie den Testmodus:</b></font</html>");
		label.setVerticalAlignment(JLabel.CENTER);
		label.setHorizontalAlignment(JLabel.CENTER);
		modusPanel.add(label);
		JPanel rbPanel = new JPanel();
		rbPanel.setBackground(Color.WHITE);
		rbPanel.add(elementgruppeRb);
		rbPanel.add(schwierigkeitsgradRb);

		modusPanel.add(rbPanel);

		KariButton startButton = erstelleEinButton(
				"<html><font size='5'>Testmodus starten</font></html>", "",
				"start", testActionListener);
		startButton.setAlignmentX(CENTER_ALIGNMENT);
		startButton.setPreferredSize(new Dimension(300, 50));
		JPanel buttonPanel = erstelleEinPanel(null, null, null, null);

		buttonPanel.add(startButton);
		modusPanel.add(buttonPanel);

		add(modusPanel);

	}

	/**
	 * Erstellt einen KariButton
	 * 
	 * @param text
	 *            Text, der angezeigt wird
	 * @param name
	 *            Name des Buttons
	 * @param command
	 *            ActionCommand
	 * @param al
	 *            ActionListener
	 * @return KariButton
	 */
	private KariButton erstelleEinButton(String text, String name,
			String command, ActionListener al) {
		KariButton neuerButton = new KariButton(text);
		neuerButton.setName(name);
		neuerButton.setActionCommand(command);
		neuerButton.addActionListener(al);

		neuerButton.setBackground(MY_RED);
		neuerButton.setForeground(Color.WHITE);

		return neuerButton;
	}

	/**
	 * Startet einen Test
	 */
	public void starteTest() {
		aktuellesVideo = 0;
		richtigeAntworten = 0;
		createErgebnisTabelle();
		resetErgebnisPanel();
		videos = sucheVideos(10);
		antwortPanel.setVisible(true);
		naechstesVideo();
	}

	/**
	 * Ein nächstes Video wird geladen und somit eine neue Aufgabe
	 */
	public void naechstesVideo() {
		nextButton.setVisible(false);
		if (aktuellesVideo == 10) {
			beendeTest();
		} else {
			Video video = videos.get(aktuellesVideo);
			if (mediaPlayer != null)
				mediaPanel.remove(mediaPlayer);
			mediaPlayer = new MediaPlayer(video.getPfad());
			mediaPanel.add(mediaPlayer);
			mediaPanel.setVisible(true);
			antwortPanel.removeAll();
			createAntwortPanel(video);
			aktuellesVideo++;
			validate();
			repaint();
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

	/**
	 * Beendet den aktuellen Test und gibt Möglichkeit einen neuen Test zu
	 * starten
	 */
	private void beendeTest() {
		System.out.println("Ende");
		endeErgebnis = new JLabel("<html><font size='5'>Sie haben "
				+ richtigeAntworten
				+ " von 10 Übungen richtig erkannt!</font></html>");

		mediaPanel.setVisible(false);
		antwortPanel.setVisible(false);
		ergebnisPanel.setVisible(false);
		//remove(mediaPanel);
		//remove(antwortPanel);
		//remove(ergebnisPanel);
		endeErgebnis.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		endeErgebnis.setVerticalAlignment(JLabel.CENTER);
		endeErgebnis.setHorizontalAlignment(JLabel.CENTER);
		add(endeErgebnis);
		neuerTest = erstelleEinButton(
				"<html><font size='5'>Test neustarten</font></html>", "new",
				"new", testActionListener);
		neuerTest.setAlignmentX(CENTER_ALIGNMENT);
		neuerTest.setPreferredSize(new Dimension(300, 50));
		neuerTestPanel = erstelleEinPanel(null, null, null, null);
		neuerTestPanel.add(neuerTest);
		add(tabellePanel);
		add(neuerTestPanel);
		validate();
		repaint();

	}

	/**
	 * Erstellt Panel, das die AntwortButtons enthält
	 * 
	 * @param video
	 *            zu diesem Video werden AntwortButtons erstellt
	 */
	private void createAntwortPanel(Video video) {
		antwortPanel.setPreferredSize(new Dimension(600, 50));
		antwortPanel.setBackground(Color.WHITE);
		antwortPanel.add(new JLabel("Antwort wählen:"));
		createAntwortButtons(elementgruppeRb.isSelected(), video);
	}

	/**
	 * Erstellt die AntwortButtons zu einem Video
	 * 
	 * @param elementgruppe
	 *            Wird Elementgruppe abgefragt?
	 * @param video
	 *            Antworten beziehen sich auf dieses Video
	 */
	private void createAntwortButtons(Boolean elementgruppe, Video video) {
		antwortButtons = new ArrayList<KariButton>();
		antwortButtons.clear();
		int anzahlButtons;
		String antwort;
		String[] buttonText;
		KariButton neuerButton;
		if (elementgruppe) {
			antwort = video.getElementgruppe();
			anzahlButtons = 5;
			buttonText = ELEMENTGRUPPEN;
		} else {
			antwort = video.getSchwierigkeitsgrad();
			anzahlButtons = 6;
			buttonText = SCHWIERIGKEITEN;
		}

		for (int i = 0; i < anzahlButtons; i++) {
			neuerButton = erstelleEinButton(buttonText[i], "", antwort,
					testActionListener);
			neuerButton.setPreferredSize(new Dimension(70, 40));
			antwortButtons.add(neuerButton);
			antwortPanel.add(neuerButton);
		}
	}

	/**
	 * Sucht n zufällige Videos aus der Datenbank
	 * 
	 * @param anzahl
	 *            Anzahl der zufälligen Videos
	 * @return Liste mit den gefundenen Videos
	 */
	private ArrayList<Video> sucheVideos(int anzahl) {
		controller = new DatenbankController();
		ArrayList<Video> videoListe = new ArrayList<Video>();
		VideoParser parser = new VideoParser();
		Integer id;

		for (int i = 0; i < anzahl; i++) {
			id = (int) (Math.random() * controller.ermittleAnzahlVideosInDatenbank());
			videoListe.add(parser.mappeEinVideo(id));
		}

		return videoListe;

	}

	/**
	 * Dient zum Testen
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(800, 800);
		TestModePanel panel = new TestModePanel();
		frame.getContentPane().add(new JScrollPane(panel));
		Controller.setTestModePanel(panel);
		frame.setVisible(true);
	}

	/**
	 * Zeigt dem User das richtige Ergebnis
	 * 
	 * @param richtigeAntwort
	 */
	public void zeigeRichtigesErgebnis(String richtigeAntwort) {
		richtigeAntworten++;
		remove(ergebnisPanel);
		JLabel richtig = new JLabel(richtigImg);
		ergebnisPanel.add(richtig);
		System.out.println("richtig");
		add(ergebnisPanel);
		aendereFarbeRichtigerButton(richtigeAntwort);
		nextButton.setVisible(true);
		disableAntwortButtons();

	}

	/**
	 * Es wird ein neuer Test vorbereitet, d.h. alle unnötigen Panels werden
	 * ausgeblendet und das ModusPanel wird wieder eingeblendet
	 */
	public void neuerTestVorbereiten() {
		modusPanel.setVisible(true);
		ergebnisPanel.removeAll();
		antwortPanel.removeAll();
		tabellePanel.removeAll();
		remove(neuerTestPanel);
		remove(endeErgebnis);
		remove(tabellePanel);
		validate();
		repaint();
	}

	/**
	 * Ändert die Farbe des richtigen Buttons zu grün
	 * 
	 * @param richtigeAntwort
	 */
	private void aendereFarbeRichtigerButton(String richtigeAntwort) {
		for (KariButton aktuellerButton : antwortButtons) {
			if (aktuellerButton.getText().equals(richtigeAntwort))
				aktuellerButton.setBackground(Color.decode("#32cd32"));
		}

	}

	/**
	 * Fügt dem Antwortverlauf ein X hinzu
	 * 
	 * @param richtigeAntwort
	 */
	public void zeigeFalschesErgebnis(String richtigeAntwort) {
		remove(ergebnisPanel);
		JLabel falsch = new JLabel(falschImg);
		ergebnisPanel.add(falsch);
		System.out.println("falsch");
		add(ergebnisPanel);
		aendereFarbeRichtigerButton(richtigeAntwort);
		nextButton.setVisible(true);
		disableAntwortButtons();

	}

	/**
	 * Erstellt die Tabelle die dem User sein Testergebnis anzeigt
	 */
	private void createErgebnisTabelle() {
		String[] spaltenNamen = { "Ihre Antwort", "Richtige Antwort",
				"Richtig/Falsch", "Video" };
		model = new DefaultTableModel(spaltenNamen, 0);
		ergebnisTabelle = new JTable(model);
		ergebnisTabelle.setRowHeight(30);
		TableColumn tc = ergebnisTabelle.getColumnModel().getColumn(2);
		ImageRenderer imgRenderer = new ImageRenderer();
		tc.setCellRenderer(imgRenderer);
		tabellePanel = erstelleEinPanel(null, null, null, null);

		JScrollPane scroll = new JScrollPane(ergebnisTabelle);
		ergebnisTabelle.getColumnModel().getColumn(3)
				.setCellRenderer(new ButtonRenderer("Video anschauen"));
		ergebnisTabelle.getColumnModel().getColumn(3)
				.setCellEditor(new ButtonEditor(new JCheckBox(), "Video anschauen", new VideoButtonActionListener()));
		scroll.setPreferredSize(new Dimension(800, 330));
		tabellePanel.add(scroll);
	}

	/**
	 * Fügt eine neue Zeile der Ergebnistabelle hinzu
	 * 
	 * @param antwort
	 *            Antwort des Users
	 * @param loesung
	 *            Lösung zur Frage
	 * @param richtig
	 *            Richtige Antwort?
	 */
	public void addTabelleZeile(String antwort, String loesung, boolean richtig) {
		int size = model.getColumnCount();

		// einen neuen Vector mit Daten herstellen
		Vector<Object> vector = new Vector<Object>(size);
		vector.add(antwort);
		vector.add(loesung);
		if (richtig) {
			vector.add(richtigImg);
		} else {
			vector.add(falschImg);
		}
		Video video = videos.get(aktuellesVideo - 1);
		vector.add(video.getId());

		// eine neue Row hinzufügen
		model.addRow(vector);

	}

	/**
	 * Disabled Antwortbuttons
	 */
	private void disableAntwortButtons() {
		for (KariButton aktuellerButton : antwortButtons) {
			aktuellerButton.setEnabled(false);
		}
	}

	/**
	 * Versteckt das ModusPanel
	 */
	public void versteckeModusPanel() {
		modusPanel.setVisible(false);
	}
}

/**
 * Renderer, damit Icon in JTable angezeigt wird
 * 
 * @author Michael
 *
 */
class ImageRenderer extends DefaultTableCellRenderer {
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel lbl = ((JLabel) super.getTableCellRendererComponent(table,
				value, isSelected, hasFocus, row, column));
		lbl = new JLabel();
		lbl.setIcon((ImageIcon) value);
		return lbl;
	}
}

/**
 * Quelle:
 * http://www.java2s.com/Code/Java/Swing-Components/ButtonTableExample.htm
 */
class ButtonRenderer extends JButton implements TableCellRenderer {

	private static final long serialVersionUID = 90599820059818649L;
	private String buttonTxt;
	
	public ButtonRenderer(String buttonTxt){
		super();
		this.buttonTxt = buttonTxt;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		setForeground(Color.WHITE);
		setBackground(Color.decode("#b92d2e"));
		setText(buttonTxt);
		return this;
	}
}

/**
 * Quelle:
 * http://www.java2s.com/Code/Java/Swing-Components/ButtonTableExample.htm
 *
 */
class ButtonEditor extends DefaultCellEditor {

	private static final long serialVersionUID = 7491064410137584273L;
	protected KariButton button;
	private String label;
	private boolean isPushed;
	private String buttonTxt;

	public ButtonEditor(JCheckBox checkBox, String buttonTxt, ActionListener listener) {
		super(checkBox);
		button = new KariButton();
		button.addActionListener(listener);
		this.buttonTxt = buttonTxt;

	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {

		button.setForeground(Color.WHITE);
		button.setBackground(Color.decode("#b92d2e"));

		button.setText(buttonTxt);
		if(value != null){
			button.setName(value.toString());
		}else{
			button.setName(String.valueOf(row));
		}
		isPushed = true;
		return button;
	}

	@Override
	public boolean stopCellEditing() {
		isPushed = false;
		return super.stopCellEditing();
	}

	@Override
	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}
}
