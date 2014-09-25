package src.main.panel;

import src.main.videoplayer.MediaPlayer;
import src.main.videoplayer.Video;
import src.main.Controller;

import java.awt.Font;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;


public class VideoInfoPanel extends CenterPanel {
	private MediaPlayer mediaPlayer;
	private JTextPane jTextPane;
	private JScrollPane jScrollPane;
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
			System.err.println("VideoInfoPanel: " + video.toString());
		}


		mediaPlayer = new MediaPlayer(videoPathFromDB);


		if (video.getBeschreibung() == null || video.getBeschreibung().isEmpty()) {
			formattedText = "<html><font face='sans-serif' color='white'><font size=\"6\"><b><i>\n"
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
			formattedText = "<html><font face='sans-serif' color='white'><font size=\"6\"><b><i>\n"
					+ video.getName()
					+ "</i></b></font size><br/><br/>\n"
					+ "<font size=\"4\"><b>Beschreibung: </b>" + (video.getBeschreibung().trim().length() >= 10 ? "<br/>"+video.getBeschreibung() : video.getBeschreibung())
					+"<br/><br/>\n"
					+ "<b>Gerät: </b>"
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
		jTextPane.setMargin(new Insets(10, 20, 10, 20));
		jTextPane.setForeground(Color.WHITE);
		jTextPane.setBackground(MYRED);
		jTextPane.setContentType("text/html");
		jTextPane.setFont(new Font("Arial", Font.PLAIN, 25));
		jTextPane.setText(formattedText);
		jScrollPane=new JScrollPane(jTextPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jScrollPane.setPreferredSize(new Dimension(300, 300));
		jScrollPane.setMinimumSize(new Dimension(10, 10));




		add(mediaPlayer, BorderLayout.CENTER);
		add(jScrollPane, BorderLayout.EAST);


		Controller.setVideoInfoPanel(this);


	}

	public void enterFullScreen() {
		System.out.println("VideoInfoPanel: enterFullScreen");
		jScrollPane.setVisible(false);
		// repaint();
	}

	public void leaveFullScreen() {
		System.out.println("VideoInfoPanel: leaveFullScreen");
		jScrollPane.setVisible(true);
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
	}
}
