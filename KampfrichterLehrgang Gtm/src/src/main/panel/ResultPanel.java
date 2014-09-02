package src.main.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import src.main.components.KariButton;
import src.main.components.RoundCorneredComboBox;
import src.main.listener.ComboBoxActionListener;
import src.main.listener.ItemChangeListener;
import src.main.listener.VideoButtonActionListener;
import src.main.videoplayer.Video;
import src.main.videoplayer.VideoParser;

/**
 * Panel praesentiert die Videoergebnisse
 * 
 * @author michael
 *
 */
public class ResultPanel extends CenterPanel { 

	private static final long serialVersionUID = 1L;
	private String iconPfad;
	private JPanel results;
	private JPanel header;
	private JPanel comboBoxPanel;
	private String geraeteName;
	private RoundCorneredComboBox schwierigkeitsgradCb;
	private RoundCorneredComboBox elementgruppeCb;
	
	private static final String[] SCHWIERIGKEITSGRADE = {"Alle Schwierigkeitsgrade anzeigen", "A", "B", "C", "D", "E", "F"};
    private static final String[] ELEMENTGRUPPEN = {"Alle Elementgruppen anzeigen", "I", "II", "III", "IV", "V"};
    
    private static final Color MYRED = Color.decode("#b92d2e");
    
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
	/**
	 * Erstellt Panel fuer die Anzeige der ComboBoxen
	 */
	private void createComboBoxPanel(){
	    comboBoxPanel = new JPanel();
	    comboBoxPanel.setBackground(Color.WHITE);
	    FlowLayout flowLayout = new FlowLayout();
	    comboBoxPanel.setLayout(flowLayout);
	    
	    createComboBoxes();
	    
	    comboBoxPanel.add(schwierigkeitsgradCb);
	    comboBoxPanel.add(elementgruppeCb);   
	}
	/**
	 * Erstellt beide ComboBoxen 
	 */
	private void createComboBoxes(){
	    schwierigkeitsgradCb = new RoundCorneredComboBox(SCHWIERIGKEITSGRADE);
        elementgruppeCb = new RoundCorneredComboBox(ELEMENTGRUPPEN);
        
        schwierigkeitsgradCb.setName("Schwierigkeitsgrad");
        elementgruppeCb.setName("Elementgruppe");
        
        ComboBoxActionListener actionListener = new ComboBoxActionListener();
        ItemChangeListener itemListener = new ItemChangeListener();
        
        schwierigkeitsgradCb.addActionListener(actionListener);
        schwierigkeitsgradCb.addItemListener(itemListener);
        elementgruppeCb.addActionListener(actionListener);
        elementgruppeCb.addItemListener(itemListener);
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
		
		results.removeAll();
		VideoButtonActionListener actionListener = new VideoButtonActionListener();
		if(videos.size() != 0){
		for (Video video : videos) {
			KariButton newButton = new KariButton(createHtmlString(video));
			newButton.setForeground(Color.WHITE);
			newButton.setBackground(MYRED);
			newButton.setName(Integer.toString(video.getId()));
			newButton.addActionListener(actionListener);

			results.add(newButton);
		}
		}else{
		    createNoResults();
		}
		
		 validate();
         repaint();

	}
	/**
	 * Werden keine Ergebnisse gefunden, dann wird der Text "Keine Treffer!" angezeigt
	 */
	private void createNoResults(){
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
	
	public RoundCorneredComboBox getSchwierigkeitsgradCb(){
	    return schwierigkeitsgradCb;
	}
	
	public RoundCorneredComboBox getElementgruppeCb(){
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
		frame.getContentPane().add(new JScrollPane(panel));
		frame.setVisible(true);

	}

	
}
