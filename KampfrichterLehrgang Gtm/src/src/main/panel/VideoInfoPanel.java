package src.main.panel;

import src.main.*;
import src.main.videoplayer.*;

import java.lang.*;
import java.util.*;

import java.awt.*;
import javax.swing.*;


public class VideoInfoPanel extends JPanel
{
	private MediaPlayer mediaPlayer;
	//private JScrollPane scrollPane;
	private JTextPane jTextPane;
	private String formattedText;
	private Video video;

    private static final Color MYRED = Color.decode("#b92d2e");

	public VideoInfoPanel(Video v)
	{
		video=v;
		setLayout(new BorderLayout());
		mediaPlayer=new MediaPlayer(video.getPfad());
		formattedText="<html><font color='white'>\n" +
			"<b>Name: </b>" + video.getName() + "<br/>\n" +
			"<b>Gerät: </b>" + video.getGeraet() + "<br/>\n" +
			"<b>Beschreibung: </b>" + video.getBeschreibung() + "<br/>\n" +
			"<b>Schwierigkeitsgrad: </b>" + video.getSchwierigkeitsgrad() + "<br/>\n" +
			"<b>Elementgruppe: </b>" + video.getElementgruppe() + "<br/>\n" +
			"</font></html>";
		jTextPane=new JTextPane();
		jTextPane.setForeground(Color.WHITE);
		jTextPane.setBackground(MYRED);
		jTextPane.setContentType("text/html");
		jTextPane.setText(formattedText);

		//scrollPane=new JScrollPane(jTextPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//scrollPane.setPreferredSize(new Dimension(600, 300));
		//scrollPane.setMinimumSize(new Dimension(10, 10));
		add(mediaPlayer, BorderLayout.WEST);
		add(jTextPane, BorderLayout.CENTER);

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


