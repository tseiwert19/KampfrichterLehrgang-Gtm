package src.main.panel;

import src.main.*;
import src.main.videoplayer.*;

import java.lang.*;
import java.util.*;
import java.net.URL;
import java.net.URISyntaxException;
import java.io.File;

import java.awt.*;
import java.awt.Font;
import javax.swing.*;

public class VideoInfoPanel extends CenterPanel {
	private MediaPlayer mediaPlayer;
	private JTextPane jTextPane;
	private JScrollPane jScrollPane;
	private JLabel jlabel;
	private String formattedText;
	private Video video;

	private static final Color MYRED = Color.decode("#b92d2e");

	public VideoInfoPanel(Video v) {
		video = v;
		setLayout(new BorderLayout());

		String videoPathFromDB=video.getPfad();
		if (videoPathFromDB == null || videoPathFromDB.isEmpty())
		{
			System.err.println("VideoInfoPanel: No path for video in database!");
			//TODO: weitere Fehlerbehandlung
		}

		/*
		videoPathFromDB=videoPathFromDB.replaceFirst("[.]wmv$", ".mkv");
		URL	urlOfVideoFile = getClass().getResource("../../../" + videoPathFromDB);
		if (urlOfVideoFile == null)
		{
			System.err.println("VideoInfoPanel: Video " + videoPathFromDB + " not found!");
			//TODO: weitere Fehlerbehandlung
		}
		try
		{
			File videoPathFileObject=new File(urlOfVideoFile.toURI());
			System.out.println("VideoInfoPanel: " + videoPathFromDB + "   " + videoPathFileObject.getPath());
			mediaPlayer = new MediaPlayer(videoPathFileObject.getPath());
		}
		catch (URISyntaxException e)
		{
			//TODO: weitere Fehlerbehandlung
		}
		*/

		mediaPlayer = new MediaPlayer(videoPathFromDB);


		if (video.getBeschreibung() == null || video.getBeschreibung().isEmpty()) {
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
			formattedText = "<html><font color='white'><font size=\"5\"><b><i>\n"
					+ video.getName()
					+ "</i></b></font size><br/><br/>\n"
					+ "<font size=\"4\"><b>Beschreibung: </b>"
					+ video.getBeschreibung()+"<b>Gerät: </b>"
					+ video.getGeraet()
					+ "<br/><br/>\n"
					+ "<b>Schwierigkeitsgrad: </b>"
					+ video.getSchwierigkeitsgrad()
					+ "<br/><br/>\n"
					+ "<b>Elementgruppe: </b>"
					+ video.getElementgruppe()
					+ "<br/></font size>\n" + "</font></html>";
		}

		jTextPane=new JTextPane();
		jTextPane.setForeground(Color.WHITE);
		jTextPane.setBackground(MYRED);
		jTextPane.setFont(new Font("Arial", Font.PLAIN, 25));
		jTextPane.setContentType("text/html");
		jTextPane.setText(formattedText);
		jScrollPane=new JScrollPane(jTextPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jScrollPane.setPreferredSize(new Dimension(300, 300));
		jScrollPane.setMinimumSize(new Dimension(10, 10));



		jlabel = new JLabel(formattedText);
		jlabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		jlabel.setPreferredSize(new Dimension(200, 90));
		jlabel.setForeground(Color.WHITE);
		jlabel.setBackground(MYRED);
		jlabel.setOpaque(true);

		add(mediaPlayer, BorderLayout.CENTER);
		add(jlabel, BorderLayout.EAST);
		add(jScrollPane, BorderLayout.EAST);

		jlabel.repaint();

		Controller.setVideoInfoPanel(this);
	}

	public void enterFullScreen() {
		System.out.println("VideoInfoPanel: enterFullScreen");
		//jlabel.setVisible(false);
		jScrollPane.setVisible(false);
		// repaint();
	}

	public void leaveFullScreen() {
		System.out.println("VideoInfoPanel: leaveFullScreen");
		//jlabel.setVisible(true);
		jScrollPane.setVisible(true);
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
