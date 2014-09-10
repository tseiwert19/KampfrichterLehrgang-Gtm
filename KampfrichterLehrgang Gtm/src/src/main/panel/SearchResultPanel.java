package src.main.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.AbstractButton;
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
import src.main.listener.ComboBoxSearchActionListener;
import src.main.listener.ItemChangeListener;
import src.main.listener.PDFActionListener;
import src.main.listener.VideoButtonActionListener;
import src.main.listener.VideoButtonMouseListener;
import src.main.videoplayer.Video;
import src.main.videoplayer.VideoParser;

/**
 * Praesentiert die Ergebnisse einer Suche
 * @author michael
 *
 */
public class SearchResultPanel extends CenterPanel {

    private static final long serialVersionUID = -1462843621974397662L;
    private static final String[] GERAETE = {"Alle Geräte anzeigen", "Boden", "Pauschenpferd", "Ringe", "Sprung", "Barren", "Reck"} ;
    private static final String[] SCHWIERIGKEITSGRADE = {"Alle Elementgruppen anzeigen", "I", "II", "III", "IV", "V"};
    private RoundCorneredComboBox geraeteCb;
    private RoundCorneredComboBox elementgruppeCb;
    private JPanel comboBoxPanel;
    private JPanel resultPanel;
    private JPanel mainResultPanel;
    private String name;

    /**
     * Konstruktor
     * @param name Es wird ein Video gesucht, das diesen Teilstring enthaelt
     */
    public SearchResultPanel(String name){
        this.name = name;
        
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder());
        
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(boxLayout);
        
        VideoParser parser = new VideoParser();
        ArrayList<Video> videos = parser.mappeVideosVonName(name);
        createComboBoxPanel();
        
        mainResultPanel = new JPanel();
        mainResultPanel.setBackground(Color.WHITE);
        mainResultPanel.setLayout(new BoxLayout(mainResultPanel, BoxLayout.PAGE_AXIS));
        createAllPanels(videos);
        
        add(mainResultPanel);
    }
    /**
     * Parst die Datensaetze aus der Datenbank und erstellt alle benoetigten Panels
     * @param name
     */
    private void createAllPanels( ArrayList<Video> videos){
        mainResultPanel.removeAll();
        ArrayList<Video> videosEinesGeraets = new ArrayList<Video>();
        Video lastVideo = null;
          
        for(Video video: videos){
            if(videosEinesGeraets.size() == 0){
                videosEinesGeraets.add(video);
                lastVideo = video;
            }else if(!lastVideo.getGeraet().equals(video.getGeraet())){
                createGeraetePanel(lastVideo.getGeraet());
                createPanelWithResults(videosEinesGeraets);
                videosEinesGeraets.clear();
                videosEinesGeraets.add(video);
                lastVideo = video;
            }else{
                videosEinesGeraets.add(video);
                lastVideo = video;
            }
        }
        //Falls Videos vorhanden
        if(videos.size() > 0){
        createGeraetePanel(lastVideo.getGeraet());
        createPanelWithResults(videosEinesGeraets);
        }else{
            createNoResults();
        }
        validate();
        repaint();
    }
    /**
     * Erstellt ein Panel, das den Namen eines Geraets anzeigt
     * @param geraeteName
     */
    private void createGeraetePanel(String geraeteName){
        JPanel geraetePanel = new JPanel();
        GridLayout gridlayout = new GridLayout(2, 0, 20, 20);
        geraetePanel.setLayout(gridlayout);
        geraetePanel.setBackground(Color.WHITE);
        JLabel label = new JLabel("<html><font size='8'><b><i>" + geraeteName + "</b></i></font>");
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        geraetePanel.add(label);
        KariButton pdfButton = new KariButton("Informationen zur D-Note");
        pdfButton.setBackground(Color.decode("#b92d2e"));
        pdfButton.setForeground(Color.WHITE);
        pdfButton.setActionCommand(geraeteName);
        pdfButton.addActionListener(new PDFActionListener());
        geraetePanel.add(pdfButton);
        mainResultPanel.add(geraetePanel);
    }
    /**
     * Erstellt Panel, das die ComboBoxen zur Filterung enthaelt
     */
    private void createComboBoxPanel(){
        comboBoxPanel = new JPanel();
        comboBoxPanel.setBackground(Color.WHITE);
        Dimension maxSize = new Dimension(2000, 50);
        comboBoxPanel.setMaximumSize(maxSize);
        FlowLayout flowLayout = new FlowLayout();
        comboBoxPanel.setLayout(flowLayout);
        
        createComboBoxes();
        
        comboBoxPanel.add(geraeteCb);
        comboBoxPanel.add(elementgruppeCb); 
        
        add(comboBoxPanel);
    }
    
    /**
     * Erstellt geraeteCb-ComboBox und elementgruppeCb-ComboBox
     */
    private void createComboBoxes()
    {
        geraeteCb = new RoundCorneredComboBox(GERAETE);
        elementgruppeCb = new RoundCorneredComboBox(SCHWIERIGKEITSGRADE);
        
        geraeteCb.setName("Geraete");
        elementgruppeCb.setName("Elementgruppe");
        
        ComboBoxSearchActionListener actionListener = new ComboBoxSearchActionListener();
        ItemChangeListener itemListener = new ItemChangeListener();
        
        geraeteCb.addActionListener(actionListener);
        geraeteCb.addItemListener(itemListener);
        elementgruppeCb.addActionListener(actionListener);
        elementgruppeCb.addItemListener(itemListener);
        
    }
    /**
     * Erstellt ein Panel, das alle Videos eines Geraetetyps enthaelt
     * @param videos Liste der Videos von einem Geraet
     */
    private void createPanelWithResults(ArrayList<Video> videos){
        resultPanel = new JPanel();
        GridLayout gridlayout = new GridLayout(0, 3, 20, 20);
        resultPanel.setLayout(gridlayout);
        resultPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        resultPanel.setBackground(Color.WHITE);
        

        Color myRot = Color.decode("#b92d2e");
        Color white = Color.decode("#FFFFFF");
        VideoButtonActionListener actionListener = new VideoButtonActionListener();
        VideoButtonMouseListener mouseListener = new VideoButtonMouseListener();
        for (Video video : videos) {
            KariButton newButton = new KariButton(createHtmlString(video));
            newButton.setForeground(white);
            newButton.setBackground(myRot);
            newButton.setName(Integer.toString(video.getId()));
            newButton.addActionListener(actionListener);
            newButton.addMouseListener(mouseListener);
            newButton.setVideo(video);
            resultPanel.add(newButton);
        }
        mainResultPanel.add(resultPanel);
    }
    /**
     * Formatiert den Text, der auf dem Button steht (in HTML)
     * 
     * @param video
     *            Text wird fuer dieses Objekt generiert
     * @return Text in HTML
     */
    private String createHtmlString(Video video) {
        return "<html><font size='5'><b><i>" + kuerzeString(video.getName())
                + " </b></i></font><br> Schwierigkeitsgrad: "
                + video.getSchwierigkeitsgrad() + "<br> Elementgruppe: "
                + video.getElementgruppe() + "  </html>";
    }
    /**
     * Werden keine Ergebnisse gefunden, dann wird der Text "Keine Treffer!" angezeigt
     */
    private void createNoResults(){
    	 JLabel keineTreffer = new JLabel("<html><font size='8'><b>Keine Treffer!</b></i></font>");
         keineTreffer.setHorizontalAlignment(JLabel.CENTER);
         keineTreffer.setVerticalAlignment(JLabel.CENTER);
         JPanel platzhalter = new JPanel();
         JPanel platzhalter2 = new JPanel();
         
         // Platzhalter werden benoetigt damit Label in der Mitte erscheint!
         platzhalter.setBackground(Color.WHITE);
         platzhalter2.setBackground(Color.WHITE);
         mainResultPanel.add(platzhalter);
         mainResultPanel.add(keineTreffer);
         mainResultPanel.add(platzhalter2);
    }
    
    
    public RoundCorneredComboBox getGeraeteCb()
    {
        return geraeteCb;
    }
    public RoundCorneredComboBox getElementgruppeCb()
    {
        return elementgruppeCb;
    }
    
    public String getName()
    {
        return name;
    }
    /**
     * Dient nur zu Testzwecken
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(800, 800);
        SearchResultPanel panel = new SearchResultPanel("Manna");
        frame.getContentPane().add(new JScrollPane(panel));
        frame.setVisible(true);

    }
    /**
     * Filtert Videos nach Name, Geraet und Elementgruppe
     * @param name
     * @param geraet
     * @param elementgruppe
     */
    public void filterVideos(String name, String geraet, String elementgruppe)
    {
        VideoParser parser = new VideoParser();
        ArrayList<Video> videos = new ArrayList<Video>();

        videos = parser.mappeGefilterteSucheVideos(name, geraet, elementgruppe);
        createAllPanels(videos);
    }
    /**
     * Kuerzt einen String.
     * Namen der Videos sind fuer die Buttons zu gross, deswegen werden sie gekuerzt.
     * @param string
     * @return gekuerzter String
     */
    private String kuerzeString(String string)
    {
        if (string == null)
        {
            throw new IllegalArgumentException("s darf nicht null sein");
        }

        if (string.length() <= 15)
        {
            return string;
        }
        else
        {
            return string.substring(0, 15) + "...";
        }
    }
}
