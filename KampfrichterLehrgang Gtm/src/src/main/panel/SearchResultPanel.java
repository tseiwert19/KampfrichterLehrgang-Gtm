package src.main.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import src.main.listener.VideoButtonActionListener;
import src.main.videoplayer.Video;
import src.main.videoplayer.VideoParser;

/**
 * Praesentiert die Ergebnisse einer Suche
 * @author michael
 *
 */
public class SearchResultPanel extends CenterPanel {


    /**
     * 
     */
    private static final long serialVersionUID = -1462843621974397662L;

    /**
     * Konstruktor
     * @param name Es wird ein Video gesucht, das diesen Teilstring enthaelt
     */
    public SearchResultPanel(String name){
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder());

        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(boxLayout);
        
        createAllPanels(name);
    }
    /**
     * Parst die Datensaetze aus der Datenbank und erstellt alle benoetigten Panels
     * @param name
     */
    private void createAllPanels(String name){
        VideoParser parser = new VideoParser();
        ArrayList<Video> videos = parser.mappeVideosVonName(name);
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
    }
    /**
     * Erstellt ein Panel, das den Namen eines Geraets anzeigt
     * @param geraeteName
     */
    private void createGeraetePanel(String geraeteName){
        Dimension maxSize = new Dimension(200, 50);
        JPanel geraetePanel = new JPanel();
        geraetePanel.setMaximumSize(maxSize);
        geraetePanel.setBackground(Color.WHITE);
        JLabel label = new JLabel(geraeteName);
        geraetePanel.add(label);
        add(geraetePanel);
    }
    /**
     * Erstellt ein Panel, das alle Videos eines Geraetetyps enthaelt
     * @param videos Liste der Videos von einem Geraet
     */
    private void createPanelWithResults(ArrayList<Video> videos){
        JPanel resultPanel = new JPanel();
        GridLayout gridlayout = new GridLayout(0, 3, 20, 20);
        resultPanel.setLayout(gridlayout);
        resultPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        resultPanel.setBackground(Color.WHITE);
        

        Color myRot = Color.decode("#b92d2e");
        Color white = Color.decode("#FFFFFF");
        VideoButtonActionListener actionListener = new VideoButtonActionListener();
        for (Video video : videos) {
            KariButton newButton = new KariButton(createHtmlString(video));
            newButton.setForeground(white);
            newButton.setBackground(myRot);
            newButton.setName(Integer.toString(video.getId()));
            newButton.addActionListener(actionListener);
            resultPanel.add(newButton);
        }
        add(resultPanel);
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
    /**
     * Werden keine Ergebnisse gefunden, dann wird der Text "Keine Treffer!" angezeigt
     */
    private void createNoResults(){
        JLabel keineTreffer = new JLabel("<html><font size='8'><b>Keine Treffer!</b></i></font>");
        JPanel platzhalter = new JPanel();
        JPanel platzhalter2 = new JPanel();
        //Platzhalter werden benoetigt damit Label in der Mitte erscheint! ----> Funktioniert allerdings nicht
        platzhalter.setBackground(Color.WHITE);
        platzhalter2.setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        add(platzhalter, BorderLayout.WEST);
        add(keineTreffer, BorderLayout.CENTER);
        add(platzhalter2, BorderLayout.EAST);
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
}
