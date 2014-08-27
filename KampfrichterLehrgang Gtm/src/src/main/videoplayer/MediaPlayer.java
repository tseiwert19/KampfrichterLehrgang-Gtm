package src.main.videoplayer;

//TODO:
// - Kommunikation zwischen Player und repeat/pause-Buttons (listener im
// mediaplayer)
// - fullscreenstrategy später festlegen
// => vlcj-quellcode
// vlc-libraries ins jar
// vlcj-jars ins jar
// wofür ist surface da?

import src.main.*;

import java.util.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Component;
import java.awt.BasicStroke;
import java.awt.geom.GeneralPath;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.LibVlcConst;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.player.embedded.DefaultFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.x.XFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.FullScreenStrategy;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.binding.internal.libvlc_state_t;

class PauseIcon implements Icon {

	private int width = 32;
	private int height = 32;

	private BasicStroke stroke = new BasicStroke(2);

	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setColor(Color.black);
		g2d.setStroke(stroke);
		g2d.drawRect(x + 1, y + 1, width - 1, height - 1);
		g2d.fillRect(x + width / 4, y + 2, width / 4 + width / 6, height - 2);
		g2d.fillRect(x + 3 * width / 4, y + 2, (3 * width / 4) - (width / 6),
				height - 2);
		g2d.drawLine(3, 3, 3, 11);
		g2d.drawLine(8, 3, 8, 11);
		g2d.dispose();
	}

	public int getIconWidth() {
		return width;
	}

	public int getIconHeight() {
		return height;
	}

}

class MissingIcon implements Icon {

	private int width = 32;
	private int height = 32;

	private BasicStroke stroke = new BasicStroke(4);

	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2d = (Graphics2D) g.create();

		/*
		 * g2d.setColor(Color.black); g2d.setStroke(stroke); g2d.drawLine(3, 3,
		 * 3, 11); g2d.drawLine(8, 3, 8, 11);
		 */

		g2d.setColor(Color.BLACK);
		// g2d.drawRect(x+1,y+1,width-1,height-1);
		GeneralPath path = new GeneralPath();
		path.moveTo(x + 2, y + 2);
		path.lineTo(x + width - 2, y + height / 2);
		path.lineTo(x + 2, y + height - 2);
		path.lineTo(x + 2, y + 2);
		g2d.fill(path);

		/*
		 * g2d.setColor(Color.WHITE); g2d.fillRect(x +1 ,y + 1,width -2 ,height
		 * -2);
		 * 
		 * g2d.setColor(Color.BLACK); g2d.drawRect(x +1 ,y + 1,width -2 ,height
		 * -2);
		 * 
		 * g2d.setColor(Color.RED);
		 * 
		 * g2d.setStroke(stroke); g2d.drawLine(x +10, y + 10, x + width -10, y +
		 * height -10); g2d.drawLine(x +10, y + height -10, x + width -10, y +
		 * 10);
		 */

		g2d.dispose();
	}

	public int getIconWidth() {
		return width;
	}

	public int getIconHeight() {
		return height;
	}

}

public class MediaPlayer extends JPanel {
	// TODO: exception
	// http://docs.oracle.com/javase/tutorial/i18n/resbundle/concept.html
	private ResourceBundle localeBundle = ResourceBundle
			.getBundle("src.main.localization.MediaPlayer");
	// private JPanel videoPlayerPanel=new JPanel();
	private JPanel controlsPanel = new JPanel();

	private JButton pauseButton;
	private JToggleButton repeatButton;
	private JButton fullScreenButton;

	private ImageIcon playIcon, pauseIcon;
	private String pauseButtonLabel, playButtonLabel, fullscreenButtonLabel,
			repeatButtonLabel;

	private EmbeddedMediaPlayerComponent ourMediaPlayer;
	private String mediaPath = "";

	private JFrame topFrame;

	MediaPlayer(String mediaURL) {
		this.mediaPath = mediaURL;

		// http://stackoverflow.com/questions/9650874/java-swing-obtain-window-jframe-from-inside-a-jpanel
		topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "lib");
		// ourMediaPlayer=new EmbeddedMediaPlayerComponent();
		ourMediaPlayer = new EmbeddedMediaPlayerComponent() {
			// TODO: für alle Plattformen
			// protected FullScreenStrategy onGetFullScreenStrategy() {
			// return new XFullScreenStrategy(topFrame);
			// }
			public void playing(MediaPlayer mediaPlayer) {
				pauseButton.setText(pauseButtonLabel);
				pauseButton.setIcon(pauseIcon);
				System.out.println("playing()");
			}

			public void paused(MediaPlayer mediaPlayer) {
				pauseButton.setText(pauseButtonLabel);
				pauseButton.setIcon(pauseIcon);
				System.out.println("paused()");
			}

			public void finished(MediaPlayer mediaPlayer) {
				pauseButton.setText(playButtonLabel);
				pauseButton.setIcon(playIcon);
				System.out.println("finished()");
				ourMediaPlayer.release();
				System.exit(0);
			}

			public void error(MediaPlayer mediaPlayer) {
				System.out.println("error()");
			}
		};

		/*
		 * ourMediaPlayer.getMediaPlayer().addMediaPlayerEventListener(new
		 * MediaPlayerEventAdapter() { public void playing(MediaPlayer
		 * mediaPlayer) { pauseButton.setText(pauseButtonLabel);
		 * pauseButton.setIcon(pauseIcon); System.out.println("playing()"); }
		 * 
		 * public void paused(MediaPlayer mediaPlayer) {
		 * pauseButton.setText(pauseButtonLabel);
		 * pauseButton.setIcon(pauseIcon); System.out.println("paused()"); }
		 * public void finished(MediaPlayer mediaPlayer) {
		 * System.out.println("finished()"); }
		 * 
		 * public void error(MediaPlayer mediaPlayer) {
		 * System.out.println("error()"); } });
		 */

		ourMediaPlayer.getMediaPlayer().setRepeat(true);

		setLayout(new BorderLayout());
		controlsPanel.setLayout(new FlowLayout());

		// http://docs.oracle.com/javase/tutorial/uiswing/components/icon.html
		// TODO: exception
		playIcon = new ImageIcon(getClass().getResource(
				"icons/control_play_blue.png"));
		pauseIcon = new ImageIcon(getClass().getResource(
				"icons/control_pause_blue.png"));

		pauseButtonLabel = localeBundle.getString("pauseButtonLabel");
		playButtonLabel = localeBundle.getString("playButtonLabel");
		fullscreenButtonLabel = localeBundle.getString("fullscreenButtonLabel");
		repeatButtonLabel = localeBundle.getString("repeatButtonLabel");

		pauseButton = new JButton();
		if (ourMediaPlayer.getMediaPlayer().getMediaPlayerState() == libvlc_state_t.libvlc_Playing) {
			pauseButton.setText(pauseButtonLabel);
			pauseButton.setIcon(new PauseIcon());
		} else {
			pauseButton.setText(playButtonLabel);
			pauseButton.setIcon(new PauseIcon());
		}
		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ourMediaPlayer.getMediaPlayer().pause();
				if (ourMediaPlayer.getMediaPlayer().getMediaPlayerState() == libvlc_state_t.libvlc_Paused) {
					pauseButton.setText(pauseButtonLabel);
					pauseButton.setIcon(pauseIcon);
				} else {
					pauseButton.setText(playButtonLabel);
					pauseButton.setIcon(playIcon);
				}
			}
		});
		controlsPanel.add(pauseButton);

		repeatButton = new JToggleButton(repeatButtonLabel, ourMediaPlayer
				.getMediaPlayer().getRepeat());
		repeatButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean repeatState = ourMediaPlayer.getMediaPlayer()
						.getRepeat();
				ourMediaPlayer.getMediaPlayer().setRepeat(!repeatState);
				repeatState = ourMediaPlayer.getMediaPlayer().getRepeat();
				repeatButton.setSelected(repeatState);
			}
		});
		controlsPanel.add(repeatButton);

		fullScreenButton = new JButton(fullscreenButtonLabel);
		fullScreenButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// ourMediaPlayer.getMediaPlayer().toggleFullScreen();
				GraphicsEnvironment
						.getLocalGraphicsEnvironment()
						.getDefaultScreenDevice()
						.setFullScreenWindow(
								(JFrame) SwingUtilities.getWindowAncestor(this));
			}
		});
		controlsPanel.add(fullScreenButton);

		add(ourMediaPlayer, BorderLayout.CENTER);
		add(controlsPanel, BorderLayout.SOUTH);
		// videoPlayerPanel.pack();

		// TODO: player muss dem repeat button einen veränderten
		// repeat-state mitteilen, ebenso wie dem pause-button
		// repeatButton.setSelected(false);

	}

	/*
	 * public JPanel getVideoPlayerPanel() { return videoPlayerPanel; }
	 */

	public void run() {
		ourMediaPlayer.getMediaPlayer().playMedia(mediaPath);
	}
}
