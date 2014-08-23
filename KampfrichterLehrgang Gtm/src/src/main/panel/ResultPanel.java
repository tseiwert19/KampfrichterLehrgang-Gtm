package src.main.panel;

import src.main.videoplayer.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalLookAndFeel;

/**
 * Panel praesentiert die Videoergebnisse
 * 
 * @author michael
 *
 */
public class ResultPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private String iconPfad;
	private JPanel results;
	private JPanel header;
	private JPanel comboBoxPanel;
	private String geraeteName;
	private JComboBox<String> schwierigkeitsgradCb;
	private JComboBox<String> elementgruppeCb;


	private static final String RESOURCEPATH = "../../../img/GeraeteLogos/";
	private static final String BARREN = "Barren";
	private static final String BODEN = "Boden";
	private static final String PAUSCHENPFERD = "Pauschenpferd";
	private static final String RECK = "Reck";
	private static final String RINGE = "Ringe";
	private static final String SPRUNG = "Sprung";
	private static final String FILESUFFIX = ".png";

	/**
	 * Konstruktor
	 * 
	 * @param geraet
	 *            Videos von diesem Geraetetyp werden dargestellt
	 */
	public ResultPanel(String geraet) {
		// Debug
		System.out.println("Create ResultPanel with database search word: "
				+ geraet);
		this.geraeteName = geraet;
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createEmptyBorder());

		BorderLayout borderLayout = new BorderLayout();
		setLayout(borderLayout);
		
		createHeaderPanel(geraet);
		add(header, BorderLayout.NORTH);
		
		createResultPanel();
		add(results, BorderLayout.CENTER);
	}
	/**
	 * Erstellt Panel mit Geraetenamen
	 * @param geraet
	 */
	private void createHeaderPanel(String geraet){
	        header = new JPanel();
	        header.setLayout(new BoxLayout(header, BoxLayout.PAGE_AXIS));
	        JLabel geraeteTyp = new JLabel("<html><font size='8'><b><i>"+ geraet+ "</b></i></font>");
	        header.add(geraeteTyp);
	        header.setBackground(Color.WHITE);
	        
	        createComboBoxPanel();
	        header.add(comboBoxPanel);

	}
	
	private void createComboBoxPanel(){
	    comboBoxPanel = new JPanel();
	    comboBoxPanel.setBackground(Color.WHITE);
	    FlowLayout flowLayout = new FlowLayout();
	    comboBoxPanel.setLayout(flowLayout);
	    
	    String[] schwierigkeitsgrade = {"Alle Schwierigkeitsgrade anzeigen", "A", "B", "C", "D", "E", "F"};
	    String[] elementgruppen = {"Alle Elementgruppen anzeigen", "I", "II", "III", "IV", "V"};
	    
	    schwierigkeitsgradCb = new JComboBox<String>(schwierigkeitsgrade);
	    elementgruppeCb = new JComboBox<String>(elementgruppen);
	    
	    schwierigkeitsgradCb.setName("Schwierigkeitsgrad");
	    elementgruppeCb.setName("Elementgruppe");
	    
	    ComboBoxActionListener listener = new ComboBoxActionListener();
	    schwierigkeitsgradCb.addActionListener(listener);
	    elementgruppeCb.addActionListener(listener);

	    comboBoxPanel.add(schwierigkeitsgradCb);
	    comboBoxPanel.add(elementgruppeCb);   
	}
	/**
	 * Erstellt Panel mit Ergebnissen
	 */
	private void createResultPanel(){
	    results = new JPanel();
        GridLayout gridlayout = new GridLayout(0, 3, 20, 20);
        results.setLayout(gridlayout);
        results.setBorder(new EmptyBorder(20, 20, 20, 20));
        results.setBackground(Color.WHITE);
        
        VideoParser parser = new VideoParser();
        ArrayList<Video> videos = new ArrayList<Video>();

        videos = parser.mappeVideosVonGeraet(geraeteName);
        createButtons(videos);
	}

	/**
	 * Erzeugt fuer jedes Video einen Button
	 * 
	 * @param geraet
	 *            Videos von diesem Geraet werden dargestellt
	 */
	private void createButtons(ArrayList<Video> videos) {
		Color myRot = Color.decode("#b92d2e");
		Color white = Color.decode("#FFFFFF");
		
		results.removeAll();
		if(videos.size() != 0){
		for (Video video : videos) {
			JButton newButton = new JButton(createHtmlString(video));
			newButton.setForeground(white);
			newButton.setBackground(myRot);

			results.add(newButton);
		}
		}else{
		    JLabel keineTreffer = new JLabel("<html><font size='8'><b>Keine Treffer!</b></i></font>");
		    JPanel platzhalter = new JPanel();
		    JPanel platzhalter2 = new JPanel();
		    //Platzhalter werden benoetigt damit Label in der Mitte erscheint!
		    platzhalter.setBackground(Color.WHITE);
		    platzhalter2.setBackground(Color.WHITE);
		    results.add(platzhalter);
		    results.add(keineTreffer);
		    results.add(platzhalter2);
		}
		
		 validate();
         repaint();

	}
	
	public void filterVideos(String schwierigkeitsgrad, String elementgruppe){
	    VideoParser parser = new VideoParser();
        ArrayList<Video> videos = new ArrayList<Video>();

        videos = parser.mappeGefilterteVideos(geraeteName, schwierigkeitsgrad, elementgruppe);
        createButtons(videos);
	}
	

	/**
	 * Formatiert den Text, der auf dem Button steht (in HTML)
	 * 
	 * @param video
	 *            Text wird fuer dieses Objekt generiert
	 * @return Text in HTML
	 */
	private String createHtmlString(Video video) {
		return "<html><font size='6'><b><i>" + video.getName()
				+ " </b></i></font><br> Schwierigkeitsgrad: "
				+ video.getSchwierigkeitsgrad() + "<br> Elementgruppe: "
				+ video.getElementgruppe() + "  </html>";
	}
	
	public JComboBox<String> getSchwierigkeitsgradCb(){
	    return schwierigkeitsgradCb;
	}
	
	public JComboBox<String> getElementgruppeCb(){
	    return elementgruppeCb;
	}

	/**
	 * Setzt den Pfad fuer das Geraetelogo
	 * 
	 * @param geraet
	 */
	private void selectIconPath(String geraet) {
		geraet = geraet.toLowerCase();
		if (geraet.equals(BARREN.toLowerCase())) {
			iconPfad = RESOURCEPATH + BARREN + FILESUFFIX;
		} else if (geraet.equals(BODEN.toLowerCase())) {
			iconPfad = RESOURCEPATH + BODEN + FILESUFFIX;
		} else if (geraet.equals(PAUSCHENPFERD.toLowerCase())) {
			iconPfad = RESOURCEPATH + PAUSCHENPFERD + FILESUFFIX;
		} else if (geraet.equals(RECK.toLowerCase())) {
			iconPfad = RESOURCEPATH + RECK + FILESUFFIX;
		} else if (geraet.equals(RINGE.toLowerCase())) {
			iconPfad = RESOURCEPATH + RINGE + FILESUFFIX;
		} else if (geraet.equals(SPRUNG.toLowerCase())) {
			iconPfad = RESOURCEPATH + SPRUNG + FILESUFFIX;
		}
	}

	// Dient zum Testen
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(800, 800);
		ResultPanel panel = new ResultPanel("Reck");
		frame.getContentPane().add(panel);
		frame.setVisible(true);

	}

}
