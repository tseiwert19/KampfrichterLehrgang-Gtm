package src.main.panel;

import src.main.videoplayer.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

/**
 * Panel praesentiert die Videoergebnisse
 * 
 * @author michael
 *
 */
public class ResultPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private String iconPfad;
	
	   private static final String RESOURCEPATH = "../../../img/GeraeteLogos/";
	    private static final String BARREN = "Barren";
	    private static final String BODEN = "Boden";
	    private static final String PAUSCHENPFERD = "Pauschenpferd";
	    private static final String RECK = "Reck";
	    private static final String RINGE = "Ringe";
	    private static final String SPRUNG = "Sprung";
	        private static final String FILESUFFIX = ".png";

	    private static final String IMAGES[] = new String[] { BODEN, PAUSCHENPFERD,
	            RINGE, SPRUNG, BARREN, RECK };

	/**
	 * Konstruktor
	 * 
	 * @param geraet
	 *            Videos von diesem Geraetetyp werden dargestellt
	 */
	public ResultPanel(String geraet) {
                // Reimsbach, ueberpruef mal ob dieser cast zum lowerCase notwendig ist
               // geraet = geraet.toLowerCase();
                //Debug
                System.out.println("Create ResultPanel with database search word: " + geraet);
                JScrollPane scrollPane = new JScrollPane(this);
        
		setBackground(Color.WHITE);
		GridLayout layout = new GridLayout(0, 3, 20, 20);
		setLayout(layout);
		setBorder(new EmptyBorder(20, 20, 20, 20));
		selectIconPath(geraet);
		createButtons(geraet);
	}

	/**
	 * Erzeugt fuer jedes Video einen Button
	 * 
	 * @param geraet
	 *            Videos von diesem Geraet werden dargestellt
	 */
	private void createButtons(String geraet) {
		VideoParser parser = new VideoParser();
		ArrayList<Video> videos = new ArrayList<Video>();

		videos = parser.mappeVideosVonGeraet(geraet);

		Color myRot = Color.decode("#b92d2e");
		Color white = Color.decode("#FFFFFF");

		String htmlString;
		Dimension minimumSize = new Dimension(50, 25);
		Dimension maximumSize = new Dimension(75, 50);
		
		BufferedImage buttonImage = null;
        Icon buttonIcon;
        try
        {
            buttonImage = ImageIO.read(getClass().getResource(iconPfad));
        }
        catch (IOException e)
        {
            System.err.println("Fehler beim Lesen der GeraeteLogos!");
            e.printStackTrace();
        }
        buttonIcon = new ImageIcon(buttonImage);
        
		for (Video video : videos) {
			htmlString = createHtmlString(video);
			JButton newButton = new JButton(htmlString);
			
		    newButton.setIcon(buttonIcon);
			newButton.setForeground(white);
			newButton.setBackground(myRot);
			newButton.setMinimumSize(minimumSize);
			newButton.setMaximumSize(maximumSize);

			add(newButton);

		}

	}

	/**
	 * Formatiert den Text, der auf dem Button steht (in HTML)
	 * 
	 * @param video
	 *            Text wird fuer dieses Objekt generiert
	 * @return Text in HTML
	 */
	private String createHtmlString(Video video) {
		String htmlString;
		htmlString = "<html> Name: " + video.getName() 
		        + " <br> Schwierigkeitsgrad: "
				+ video.getSchwierigkeitsgrad() + "<br> Elementgruppe: "
				+ video.getElementgruppe() + "</html>";
		return htmlString;
	}
	/**
	 * Setzt den Pfad fuer das Geraetelogo
	 * @param geraet 
	 */
		private void selectIconPath(String geraet){
	    geraet = geraet.toLowerCase();
	    if(geraet.equals(BARREN.toLowerCase())){
	        iconPfad = RESOURCEPATH + BARREN + FILESUFFIX; 
	    }else if(geraet.equals(BODEN.toLowerCase())){
	        iconPfad = RESOURCEPATH + BODEN + FILESUFFIX;
	    }else if(geraet.equals(PAUSCHENPFERD.toLowerCase())){
	        iconPfad = RESOURCEPATH + PAUSCHENPFERD + FILESUFFIX;
	    }else if(geraet.equals(RECK.toLowerCase())){
	        iconPfad = RESOURCEPATH + RECK + FILESUFFIX;
	    }else if(geraet.equals(RINGE.toLowerCase())){
	        iconPfad = RESOURCEPATH + RINGE + FILESUFFIX;
	    }else if(geraet.equals(SPRUNG.toLowerCase())){
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
