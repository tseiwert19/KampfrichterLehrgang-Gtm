package src.main.panel;

import src.main.*;
import src.main.videoplayer.*;

import java.lang.*;
import java.util.*;

import java.awt.*;
import javax.swing.*;


class VideoInfoPanel extends JPanel
{
	private MediaPlayer mediaPlayer;
	private JScrollPane scrollPane;
	private JTextPane jTextPane;
	private String formattedText;
	private Video video;

	VideoInfoPanel(Video v)
	{
		video=v;
		setLayout(new BorderLayout());
		mediaPlayer=new MediaPlayer(video.getPfad());
		formattedText="<html>\n" +
			"<b>ID: </b>" + video.getId() + "<br/>\n" +
			"<b>Name: </b>" + video.getName() + "<br/>\n" +
			"<b>Pfad: </b>" + video.getPfad() + "<br/>\n" +
			"<b>Gerät: </b>" + video.getGeraet() + "<br/>\n" +
			"<b>Beschreibung: </b>" + video.getBeschreibung() + "<br/>\n" +
			"<b>Schwierigkeitsgrad: </b>" + video.getSchwierigkeitsgrad() + "<br/>\n" +
			"<b>Elementgruppe: </b>" + video.getElementgruppe() + "<br/>\n" +
			"</html>";
		jTextPane=new JTextPane();
		jTextPane.setContentType("text/html");
		jTextPane.setText(formattedText);

		scrollPane=new JScrollPane(jTextPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(600, 300));
		scrollPane.setMinimumSize(new Dimension(10, 10));
		add(mediaPlayer, BorderLayout.WEST);
		add(scrollPane, BorderLayout.CENTER);

		//mediaPlayer.run();
	}

	public static void main(String[] args)
	{
		JFrame mainFrame=new JFrame();
		mainFrame.setSize(640,480);

		VideoInfoPanel videoInfoPanel=new VideoInfoPanel(new Video(
					666,
					"Name",
					"/tmp/ATARI SOUNDDESIGN #01 - rE7aNqOS1L8.webm",
					"Gerät",
					"Beschreibung",
					"Schwierigkeitsgrad",
					"Elementgruppe"));
		mainFrame.setContentPane(videoInfoPanel);

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
}


