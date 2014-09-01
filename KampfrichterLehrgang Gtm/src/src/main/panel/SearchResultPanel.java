package src.main.panel;

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
        createGeraetePanel(lastVideo.getGeraet());
        createPanelWithResults(videosEinesGeraets);
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

        for (Video video : videos) {
            JButton newButton = new JButton(createHtmlString(video));
            newButton.setForeground(white);
            newButton.setBackground(myRot);

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
