package src.main.panel;

import src.main.*;
import src.main.videoplayer.*;

import java.lang.*;
import java.util.*;
import java.net.URL;
import java.io.File;

import java.awt.*;
import javax.swing.*;

public class VideoInfoPanel extends CenterPanel {
	private MediaPlayer mediaPlayer;
	private JLabel jlabel;
	private String formattedText;
	private Video video;

	private static final Color MYRED = Color.decode("#b92d2e");

	public VideoInfoPanel(Video v) {
		video = v;
		setLayout(new BorderLayout());

		/*
		String os = "os.name";

		Properties prop = System.getProperties();
		String workingDir = System.getProperty("user.dir");
		if (prop.getProperty(os).contains("Windows")) {
			String videostring = video.getPfad().replace("/", prop.getProperty("file.separator"));
			mediaPlayer = new MediaPlayer(workingDir + "\\src\\" + videostring);
		} else {
			//mediaPlayer = new MediaPlayer(workingDir + "/src/"
					//+ video.getPfad());
			mediaPlayer = new MediaPlayer(video.getPfad());
		}
		*/

		URL	urlOfVideoFile = getClass().getClassLoader().getResource(video.getPfad());
		System.out.println("VideoInfoPanel.java: " + video.getPfad() + "   " + urlOfVideoFile.toString());
		/*
		 * Crappy URL.toString gives file:/... instead of needed file:///...
		 */
		mediaPlayer = new MediaPlayer("file:///" + urlOfVideoFile.getPath());


		if (video.getBeschreibung() == null) {
			formattedText = "<html><font color='white'><font size=\"5\"><b><i>\n"
					+ video.getName()
					+ "</i></b></font size><br/><br/>\n"
					+ "<font size=\"4\"><b>Gerät: </b>"
					+ video.getGeraet()
					+ "<br/><br/>\n"
					+ "<b>Schwierigkeitsgrad: </b>"
					+ video.getSchwierigkeitsgrad()
					+ "<br/><br/>\n"
					+ "<b>Elementgruppe: </b>"
					+ video.getElementgruppe()
					+ "<br/></font size>\n" + "</font></html>";
		} else {
			formattedText = "<html><font color='white'>\n" + "<b>Name: </b>"
					+ video.getName() + "<br/>\n" + "<b>Gerät: </b>"
					+ video.getGeraet() + "<br/>\n" + "<b>Beschreibung: </b>"
					+ video.getBeschreibung() + "<br/>\n"
					+ "<b>Schwierigkeitsgrad: </b>"
					+ video.getSchwierigkeitsgrad() + "<br/>\n"
					+ "<b>Elementgruppe: </b>" + video.getElementgruppe()
					+ "<br/>\n" + "</font></html>";
		}

		jlabel = new JLabel(formattedText);
		jlabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		jlabel.setPreferredSize(new Dimension(200, 90));
		jlabel.setForeground(Color.WHITE);
		jlabel.setBackground(MYRED);
		jlabel.setOpaque(true);

		add(mediaPlayer, BorderLayout.CENTER);
		add(jlabel, BorderLayout.EAST);

		jlabel.repaint();

		Controller.setVideoInfoPanel(this);
	}

	public void enterFullScreen() {
		System.out.println("VideoInfoPanel: enterFullScreen");
		jlabel.setVisible(false);
		// repaint();
	}

	public void leaveFullScreen() {
		System.out.println("VideoInfoPanel: leaveFullScreen");
		jlabel.setVisible(true);
	}

	public void run() {
		mediaPlayer.run();
	}

	public static void main(String[] args) {
		JFrame mainFrame = new JFrame();
		mainFrame.setSize(640, 480);

		VideoInfoPanel videoInfoPanel = new VideoInfoPanel(new Video(666,
				"Name", "/tmp/out.mkv", "Gerät", "Beschreibung",
				"Schwierigkeitsgrad", "Elementgruppe"));
		mainFrame.setContentPane(videoInfoPanel);

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainFrame.pack();
		mainFrame.setVisible(true);
		videoInfoPanel.run();
	}
}
