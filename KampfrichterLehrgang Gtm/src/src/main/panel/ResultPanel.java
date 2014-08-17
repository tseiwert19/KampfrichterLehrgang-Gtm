package src.main.panel;

import src.main.videoplayer.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Panel praesentiert die Videoergebnisse
 * 
 * @author michael
 *
 */
public class ResultPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor
	 * 
	 * @param geraet
	 *            Videos von diesem Geraetetyp werden dargestellt
	 */
	public ResultPanel(String geraet) {
		setBackground(Color.WHITE);
		GridLayout layout = new GridLayout(0, 3, 20, 20);
		setLayout(layout);
		setBorder(new EmptyBorder(20, 20, 20, 20));
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
		Dimension minimumSize = new Dimension(100, 100);

		for (Video video : videos) {
			htmlString = createHtmlString(video);
			JButton newButton = new JButton(htmlString);

			newButton.setForeground(white);
			newButton.setBackground(myRot);
			newButton.setPreferredSize(minimumSize);

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
		htmlString = "<html> Name: " + video.getName() + "<br> Gerät: "
				+ video.getGeraet() + " <br> Schwierigkeitsgrad: "
				+ video.getSchwierigkeitsgrad() + "<br> Elementgruppe: "
				+ video.getElementgruppe() + "</html>";
		return htmlString;
	}

	// Dient zum Testen
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(800, 800);
		ResultPanel panel = new ResultPanel("boden");
		frame.getContentPane().add(panel);
		frame.setVisible(true);

	}

}
