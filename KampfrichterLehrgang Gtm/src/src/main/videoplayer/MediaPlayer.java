/*
 * file:///tmp/vlcj-3.0.1/javadoc/uk/co/caprica/vlcj/discovery/NativeDiscovery.html
 * oder
 * https://github.com/caprica/vlcj/blob/master/src/test/java/uk/co/caprica/vlcj/test/quickstart/Example2.java
 * http://www.capricasoftware.co.uk/projects/vlcj/faq.html
 * https://github.com/caprica/vlcj/blob/master/src/test/java/uk/co/caprica/vlcj/test/discovery/NativeDiscoveryTest.java
 */

//TODO:
// - Kommunikation zwischen Player und repeat/pause-Buttons (listener im
// mediaplayer)
// vlc-libraries ins jar
// vlcj-jars ins jar
// wofür ist surface da?
// onBeforeEnterFullScreenMode
// onAfterExitFullScreenMode

package src.main.videoplayer;

import src.main.*;

import java.util.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Component;
import java.awt.BasicStroke;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.RenderingHints;

import java.net.URISyntaxException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.Icon;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.LibVlcConst;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.player.embedded.DefaultFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.x.XFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.FullScreenStrategy;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.binding.internal.libvlc_state_t;
import uk.co.caprica.vlcj.runtime.x.LibXUtil;

class PauseIcon implements Icon {
	private int width;
	private int height;

	private BasicStroke stroke = new BasicStroke(2);

	PauseIcon(int w, int h) {
		width = w;
		height = h;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setColor(Color.black);
		g2d.setStroke(stroke);
		// g2d.drawRect(x+1,y+1,width-2,height-2);
		g2d.fillRect(x + (2 * width / 9), y + 4, (2 * width / 9), height - 8);
		g2d.fillRect(x + 5 * width / 9, y + 4, (2 * width / 9), height - 8);
		g2d.dispose();
	}

	public int getIconWidth() {
		return width;
	}

	public int getIconHeight() {
		return height;
	}

}

class PlayIcon implements Icon {
	private int width;
	private int height;

	PlayIcon(int w, int h) {
		width = w;
		height = h;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setColor(Color.black);
		GeneralPath path = new GeneralPath();
		path.moveTo(x + 2, y + 2);
		path.lineTo(x + width - 2, y + height / 2);
		path.lineTo(x + 2, y + height - 2);
		path.lineTo(x + 2, y + 2);
		g2d.fill(path);
		g2d.dispose();
	}

	public int getIconWidth() {
		return width;
	}

	public int getIconHeight() {
		return height;
	}

}

class FullScreenIcon implements Icon {
	private int width;
	private int height;

	private BasicStroke stroke;

	FullScreenIcon(int w, int h) {
		width = w;
		height = h;
		stroke = new BasicStroke(width / 11);
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setColor(Color.black);
		g2d.setStroke(stroke);
		g2d.fillRect(x + width / 10, y + height / 10, width - width / 5, height
				- height / 5);
		g2d.drawLine(x, y, x + width / 5, y);
		g2d.drawLine(x, y, x, y + height / 5);
		g2d.drawLine(x + width, y, x + width - width / 5, y);
		g2d.drawLine(x + width, y, x + width, y + height / 5);
		g2d.drawLine(x + width, y + height, x + width - width / 5, y + height);
		g2d.drawLine(x + width, y + height, x + width, y + height - height / 5);
		g2d.drawLine(x, y + height, x + width / 5, y + height);
		g2d.drawLine(x, y + height, x, y + height - height / 5);
		g2d.dispose();
	}

	public int getIconWidth() {
		return width;
	}

	public int getIconHeight() {
		return height;
	}

}

class RepeatIcon implements Icon {
	private int width;
	private int height;

	private BasicStroke stroke;

	RepeatIcon(int w, int h) {
		width = w;
		height = h;
		stroke = new BasicStroke(width / 11);
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(stroke);
		g2d.setColor(Color.black);

		Path2D.Float path = new Path2D.Float();
		path.moveTo(x + width / 16, y + 17 * height / 32);
		// path.lineTo(x+width/8, y+3*height/8);
		path.lineTo(x + width / 16, y + 7 * height / 16);
		path.quadTo(x + width / 16, y + 5 * height / 16, x + 4 * width / 16, y
				+ 5 * height / 16);
		path.lineTo(x + 6 * width / 8, y + 5 * height / 16);
		g2d.draw(path);
		path.moveTo(x + 7 * width / 8, y + 5 * height / 16);
		path.lineTo(x + 10 * width / 16, y + 7 * height / 32);
		path.lineTo(x + 10 * width / 16, y + 13 * height / 32);
		path.lineTo(x + 7 * width / 8, y + 5 * height / 16);
		g2d.draw(path);
		path.moveTo(x + 15 * width / 16, y + 15 * height / 32);
		// path.lineTo(x+7*width/8, y+5*height/8);
		path.lineTo(x + 15 * width / 16, y + 9 * height / 16);
		path.quadTo(x + 15 * width / 16, y + 11 * height / 16, x + 12 * width
				/ 16, y + 11 * height / 16);
		path.lineTo(x + 2 * width / 8, y + 11 * height / 16);
		g2d.draw(path);
		path.moveTo(x + 1 * width / 8, y + 11 * height / 16);
		path.lineTo(x + 6 * width / 16, y + 25 * height / 32);
		path.lineTo(x + 6 * width / 16, y + 19 * height / 32);
		path.lineTo(x + 1 * width / 8, y + 11 * height / 16);
		g2d.draw(path);
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
			.getBundle("src.main.videoplayer.localization.MediaPlayer");
	private JPanel controlsPanel;

	private JButton playPauseButton;
	private JToggleButton repeatButton;
	private JButton fullScreenButton;

	private PlayIcon playIcon;
	private PauseIcon pauseIcon;
	private FullScreenIcon fullScreenIcon;
	private RepeatIcon repeatIcon;
	private String pauseLabel, playLabel, fullscreenButtonLabel,
			repeatButtonLabel;

	private EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent;
	private String mediaPath = "";

	private boolean isPlaying;

	private JFrame topFrame;
	private MediaPlayer mediaPlayer;

	// MediaPlayer(String mediaURL, JFrame f)
	public MediaPlayer(String mediaURL) {
		setPreferredSize(new Dimension(550,90));
		mediaPlayer = this;
		this.mediaPath = mediaURL;

		this.isPlaying = false;

		// http://stackoverflow.com/questions/9650874/java-swing-obtain-window-jframe-from-inside-a-jpanel
		topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		// topFrame=f;

		// TODO: exception
		// libvlc.dll und libvlccore.dll
		// -Djna.library.path=C:\programme\videolan\vlc
		// http://download.videolan.org/pub/videolan/vlc/last/win32/
		// http://download.videolan.org/pub/videolan/vlc/last/win64/

		//export LD_LIBRARY_PATH=${LD_LIBRARY_PATH}:src/main/libs/linux-amd64
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),
				"c:/programme/videolan/vlc");
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),
				"src/main/libs");
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcCoreName(),
				"src/main/libs");
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),
				"src/main/libs/linux-i386");
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),
				"src/main/libs/linux-amd64");
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcCoreName(),
				"src/main/libs/linux-i386");
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcCoreName(),
				"src/main/libs/linux-amd64");

		String property = System.getProperty("java.library.path");
		StringTokenizer parser = new StringTokenizer(property, System.getProperty("path.separator"));
		System.err.println("MediaPlayer.java: java.library.path: ");
		while (parser.hasMoreTokens()) {
			System.err.println(parser.nextToken());
		}
		System.err.println(RuntimeUtil.getLibVlcCoreName());

		System.out.println("os.name: " + System.getProperty("os.name"));
		System.out.println("os.arch: " + System.getProperty("os.arch"));

		Properties p=System.getProperties();


		//http://www.chilkatsoft.com/java-loadLibrary-Linux.asp
		if (System.getProperty("os.name").equals("Linux"))
		{
			if (System.getProperty("os.arch").equals("amd64"))
			{
				try
				{
					System.out.println(getClass().getResource("../libs/linux-amd64/libvlccore.so.7.0.0").toURI().getPath());
				}
				catch (URISyntaxException e) { }
				try {
					System.load(getClass().getResource("../libs/linux-amd64/libvlccore.so.7.0.0").toURI().getPath());
				}
				catch (URISyntaxException e) { }
				catch (UnsatisfiedLinkError e) {
					System.err.println("Native code library failed to load.\n" + e);
					System.exit(1);
				}
			}
			else if (System.getProperty("os.arch").equals("i386"))
			{
				try {
					System.load(getClass().getResource("../libs/linux-i386/libvlccore.so.7").toURI().getPath());
				}
				catch (URISyntaxException e) { }
				catch (UnsatisfiedLinkError e) {
					System.err.println("Native code library failed to load.\n" + e);
					System.exit(1);
				}
			}
		}
		else
			Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

		//Native.loadLibrary(RuntimeUtil.getLibVlcCoreName(), LibVlc.class);
		LibXUtil.initialise();


		
		// embeddedMediaPlayerComponent=new EmbeddedMediaPlayerComponent();
		embeddedMediaPlayerComponent = new EmbeddedMediaPlayerComponent() {
			public void playing(MediaPlayer mediaPlayer) {
				playPauseButton.setText(pauseLabel);
				playPauseButton.setIcon(pauseIcon);
				System.out.println("playing()");
			}

			public void paused(MediaPlayer mediaPlayer) {
				playPauseButton.setText(pauseLabel);
				playPauseButton.setIcon(pauseIcon);
				System.out.println("paused()");
			}

			public void finished(MediaPlayer mediaPlayer) {
				playPauseButton.setText(playLabel);
				playPauseButton.setIcon(playIcon);
				System.out.println("finished()");
				embeddedMediaPlayerComponent.release();
				System.exit(0);
			}

			public void error(MediaPlayer mediaPlayer) {
				System.out.println("error()");
			}
		};

		/*
		 * embeddedMediaPlayerComponent.getMediaPlayer().addMediaPlayerEventListener
		 * (new MediaPlayerEventAdapter() { public void playing(MediaPlayer
		 * mediaPlayer) { playPauseButton.setText(pauseLabel);
		 * playPauseButton.setIcon(pauseIcon); System.out.println("playing()");
		 * }
		 * 
		 * public void paused(MediaPlayer mediaPlayer) {
		 * playPauseButton.setText(pauseLabel);
		 * playPauseButton.setIcon(pauseIcon); System.out.println("paused()"); }
		 * public void finished(MediaPlayer mediaPlayer) {
		 * System.out.println("finished()"); }
		 * 
		 * public void error(MediaPlayer mediaPlayer) {
		 * System.out.println("error()"); } });
		 */

		embeddedMediaPlayerComponent.getMediaPlayer().setRepeat(true);

		controlsPanel = new JPanel(new FlowLayout());

		setLayout(new BorderLayout());
		embeddedMediaPlayerComponent.setPreferredSize(new Dimension(768, 576));
		embeddedMediaPlayerComponent.setMinimumSize(new Dimension(320, 240));

		// http://docs.oracle.com/javase/tutorial/uiswing/components/icon.html
		playIcon = new PlayIcon(32, 32);
		pauseIcon = new PauseIcon(32, 32);
		fullScreenIcon = new FullScreenIcon(32, 32);
		repeatIcon = new RepeatIcon(32, 32);

		pauseLabel = localeBundle.getString("pauseButtonLabel");
		playLabel = localeBundle.getString("playButtonLabel");
		fullscreenButtonLabel = localeBundle.getString("fullscreenButtonLabel");
		repeatButtonLabel = localeBundle.getString("repeatButtonLabel");

		playPauseButton = new JButton();
		if (this.isPlaying) {
			playPauseButton.setText(pauseLabel);
			playPauseButton.setToolTipText(pauseLabel);
			playPauseButton.setIcon(pauseIcon);
		} else {
			playPauseButton.setText(playLabel);
			playPauseButton.setToolTipText(playLabel);
			playPauseButton.setIcon(playIcon);
		}
		playPauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				embeddedMediaPlayerComponent.getMediaPlayer().pause();
				mediaPlayer.isPlaying = !mediaPlayer.isPlaying;
				if (mediaPlayer.isPlaying) {
					System.out.println("Playing");
					playPauseButton.setText(pauseLabel);
					playPauseButton.setToolTipText(pauseLabel);
					playPauseButton.setIcon(pauseIcon);
				} else {
					System.out.println("Not playing");
					playPauseButton.setText(playLabel);
					playPauseButton.setToolTipText(playLabel);
					playPauseButton.setIcon(playIcon);
				}
			}
		});
		controlsPanel.add(playPauseButton);

		repeatButton = new JToggleButton(repeatButtonLabel,
				embeddedMediaPlayerComponent.getMediaPlayer().getRepeat());
		repeatButton.setIcon(repeatIcon);
		repeatButton.setToolTipText(repeatButtonLabel);
		repeatButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean repeatState = embeddedMediaPlayerComponent
						.getMediaPlayer().getRepeat();
				embeddedMediaPlayerComponent.getMediaPlayer().setRepeat(
						!repeatState);
				repeatState = embeddedMediaPlayerComponent.getMediaPlayer()
						.getRepeat();
				repeatButton.setSelected(repeatState);
			}
		});
		controlsPanel.add(repeatButton);

		fullScreenButton = new JButton();
		fullScreenButton.setIcon(fullScreenIcon);
		fullScreenButton.setText(fullscreenButtonLabel);
		fullScreenButton.setToolTipText(fullscreenButtonLabel);
		fullScreenButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mediaPlayer.toggleFullScreen();
			}
		});
		controlsPanel.add(fullScreenButton);

		add(embeddedMediaPlayerComponent, BorderLayout.CENTER);
		add(controlsPanel, BorderLayout.PAGE_END);
		// videoPlayerPanel.pack();
		// TODO: player muss dem repeat button einen veränderten
		// repeat-state mitteilen, ebenso wie dem pause-button
		// repeatButton.setSelected(false);

	}


	private void toggleFullScreen()
	{
		// https://www3.ntu.edu.sg/home/ehchua/programming/java/J8b_Game_2DGraphics.html
		// embeddedMediaPlayerComponent.getMediaPlayer().toggleFullScreen();
		topFrame=((JFrame)SwingUtilities.getWindowAncestor(mediaPlayer));
		if (GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice().getFullScreenWindow() == null) {
			try
			{
				Controller.setFullScreen();
			}
			catch (Exception ex) {}
			/*
			 * topFrame.setVisible(false);
			 * topFrame.setResizable(false);
			 * topFrame.dispose();
			 * topFrame.setUndecorated(true);
			 * GraphicsEnvironment
			 * .getLocalGraphicsEnvironment().getDefaultScreenDevice
			 * ().setFullScreenWindow(topFrame);
			 * topFrame.setVisible(true);
			 * topFrame.repaint();
			 */

			GraphicsEnvironment
				.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice()
				.setFullScreenWindow(topFrame);
			try
			{
				LibXUtil.setFullScreenWindow(topFrame, true);
			}
			catch (Exception ex) {}
			//topFrame.invalidate();
			//topFrame.validate();

		} else {
			try
			{
				LibXUtil.setFullScreenWindow(topFrame, false);
			}
			catch (Exception ex) {}
			GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice().setFullScreenWindow(null);
			// topFrame.toFront();
			// topFrame.setVisible(true);
			// f.dispose();
			// f.setBounds(0, 0, 1920, 1080);
			// f.toFront();
			// f.setVisible(true);

			try
			{
				Controller.unsetFullScreen();
			}
			catch (Exception ex) {}
		}
		boolean vollMoeglich = GraphicsEnvironment
			.getLocalGraphicsEnvironment().getDefaultScreenDevice()
			.isFullScreenSupported();
		if (vollMoeglich == true) {
			System.out.println("Vollbild möglich");
		}

	}


	public void run() {
		System.out.println("Media path: " + mediaPath);
		embeddedMediaPlayerComponent.getMediaPlayer().playMedia(mediaPath);
		this.isPlaying = true;
		if (this.isPlaying) {
			System.out.println("Playing");
			playPauseButton.setText(pauseLabel);
			playPauseButton.setToolTipText(pauseLabel);
			playPauseButton.setIcon(pauseIcon);
		} else {
			System.out.println("Not playing");
			playPauseButton.setText(playLabel);
			playPauseButton.setToolTipText(playLabel);
			playPauseButton.setIcon(playIcon);
		}
	}

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("No argument given");
			System.exit(1);
		}

		JFrame mainFrame = new JFrame(args[0]);
		mainFrame.setSize(640, 480);

		MediaPlayer myMediaPlayer = new MediaPlayer(args[0]);
		mainFrame.setContentPane(myMediaPlayer);

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainFrame.pack();
		mainFrame.setVisible(true);

		myMediaPlayer.run();
	}
}
