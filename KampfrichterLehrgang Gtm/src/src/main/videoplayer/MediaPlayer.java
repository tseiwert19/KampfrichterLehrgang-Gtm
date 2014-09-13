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

import java.net.URL;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URISyntaxException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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


public class MediaPlayer extends JPanel {
	private PlayerControlsPanel controlsPanel;


	private EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent;
	private String mediaPath = "";

	private boolean isPlaying;

	private JFrame topFrame;
	private MediaPlayer mediaPlayer;

	public MediaPlayer(String mediaURL) {
		setPreferredSize(new Dimension(550,90));
		mediaPlayer = this;
		this.mediaPath = mediaURL;

		this.isPlaying = false;

		// http://stackoverflow.com/questions/9650874/java-swing-obtain-window-jframe-from-inside-a-jpanel
		topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

		loadVLCLibraries();


		
		// embeddedMediaPlayerComponent=new EmbeddedMediaPlayerComponent();
		embeddedMediaPlayerComponent = new EmbeddedMediaPlayerComponent() {
			public void playing(MediaPlayer mediaPlayer) {
				//playPauseButton.setText(pauseLabel);
				//playPauseButton.setIcon(pauseIcon);
				System.out.println("playing()");
			}

			public void paused(MediaPlayer mediaPlayer) {
				//playPauseButton.setText(pauseLabel);
				//playPauseButton.setIcon(pauseIcon);
				System.out.println("paused()");
			}

			public void finished(MediaPlayer mediaPlayer) {
				//playPauseButton.setText(playLabel);
				//playPauseButton.setIcon(playIcon);
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

		controlsPanel = new PlayerControlsPanel(this);

		setLayout(new BorderLayout());
		embeddedMediaPlayerComponent.setPreferredSize(new Dimension(768, 576));
		embeddedMediaPlayerComponent.setMinimumSize(new Dimension(320, 240));

		add(controlsPanel, BorderLayout.PAGE_END);
		add(embeddedMediaPlayerComponent, BorderLayout.CENTER);
	}


	public void toggleFullScreen()
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

	private void loadVLCLibraries()
	{
		// TODO: exception
		// libvlc.dll und libvlccore.dll
		// -Djna.library.path=C:\programme\videolan\vlc
		// http://download.videolan.org/pub/videolan/vlc/last/win32/
		// http://download.videolan.org/pub/videolan/vlc/last/win64/
		/*
		 * "A common mistake that is made is that a 64-bit Java Virtual
		 * Machine is used with a 32-bit version of vlc. This will simply
		 * not work and there is nothing that can be done to make it work.
		 * You must use a 64-bit version of vlc with a 64-bit JDK, or a
		 * 32-bit version of vlc with a 32-bit JDK.  If you are running a
		 * 64-bit operating system and can not find a 64-bit build of vlc,
		 * then you can install and run a 32-bit JDK instead."
		 * os.arch
		 * sun.arch.data.model
		 *
		 */

		/* https://github.com/caprica/vlcj/issues/62 */

		//export LD_LIBRARY_PATH=${LD_LIBRARY_PATH}:src/main/libs/linux-amd64
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),
				"c:/programme/videolan/vlc");
		/*
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
				*/

		System.err.println(RuntimeUtil.getLibVlcCoreName());


		Properties p=System.getProperties();
		Enumeration props=p.propertyNames();
		while (props.hasMoreElements())
		{
			String key = (String)props.nextElement();
			String value = (String)p.get(key);
			System.out.println(key + ": " + value);
		}



		/*
		//http://www.chilkatsoft.com/java-loadLibrary-Linux.asp
		if (System.getProperty("os.name").equals("Linux"))
		{
			String pathOfLib="";
			if (System.getProperty("os.arch").equals("amd64"))
			{
				pathOfLib="../../../src/main/libs/linux-amd64/libvlccore.so.7.0.0";
			}
			else if (System.getProperty("os.arch").equals("i386"))
			{
				pathOfLib="../../../src/main/libs/linux-i386/libvlccore.so.7";
			}

			URL urlOfLib=getClass().getResource(pathOfLib);
			if (urlOfLib == null)
			{
				System.err.println("MediaPlayer: Library " + pathOfLib + " not found!");
				System.exit(1);
			}
			String absolutePathOfLib="";
			try
			{
				absolutePathOfLib=urlOfLib.toURI().getSchemeSpecificPart();
			}
			catch (Exception e)
			{
				System.err.println("MediaPlayer: URL of library " + pathOfLib + " couldn't be converted to URI!");
				System.exit(1);
			}
			try
			{
				System.load(absolutePathOfLib);
			}
			catch (Exception e)
			{
				System.err.println("MediaPlayer: Failed to load native library " + absolutePathOfLib + "!");
				System.exit(1);
			}
		}
		else
		*/
			Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

		//Native.loadLibrary(RuntimeUtil.getLibVlcCoreName(), LibVlc.class);
		LibXUtil.initialise();
	}

	public void pause()
	{
		embeddedMediaPlayerComponent.getMediaPlayer().pause();
	}

	public boolean getRepeatState()
	{
		return embeddedMediaPlayerComponent.getMediaPlayer().getRepeat();
	}

	public void setRepeatState(boolean repeatState)
	{
		embeddedMediaPlayerComponent.getMediaPlayer().setRepeat(repeatState);
	}

	public boolean getPlayingState()
	{
		return isPlaying;
	}

	public void setPlayingState(boolean isPlaying)
	{
		this.isPlaying = isPlaying;
	}

	public void run() {
		System.out.println("Media path: " + mediaPath);
		embeddedMediaPlayerComponent.getMediaPlayer().playMedia(mediaPath);
		this.isPlaying = true;
		if (this.isPlaying) {
			System.out.println("Playing");
			controlsPanel.setPlaying(true);
		} else {
			System.out.println("Not playing");
			controlsPanel.setPlaying(false);
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
