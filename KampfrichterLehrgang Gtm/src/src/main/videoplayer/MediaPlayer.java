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

package src.main.videoplayer;

import src.main.*;

import java.lang.Double;

import java.util.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Canvas;

import java.net.URL;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URISyntaxException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import java.awt.event.ContainerAdapter;
import java.awt.event.HierarchyListener;
import java.awt.event.ContainerEvent;
import java.awt.event.HierarchyEvent;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.LibVlcConst;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.player.embedded.DefaultFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.x.XFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.FullScreenStrategy;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.binding.internal.libvlc_state_t;
import uk.co.caprica.vlcj.runtime.x.LibXUtil;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;


public class MediaPlayer extends JPanel {
	private PlayerControlsPanel controlsPanel;

	private EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent;

	private final Canvas canvas;
    private final MediaPlayerFactory mediaPlayerFactory;
    private EmbeddedMediaPlayer embeddedMediaPlayer;

	private String mediaPath = "";

	private boolean isPlaying;

	private JFrame topFrame;
	private MediaPlayer mediaPlayer;

	private MediaPlayerEventAdapter mediaPlayerEventAdapter;

	private int lastState = 0;
	private Rectangle lastBounds = null;

	public MediaPlayer(String mediaURL) {
		mediaPlayer = this;
		this.mediaPath = mediaURL;

		this.isPlaying = false;

		// http://stackoverflow.com/questions/9650874/java-swing-obtain-window-jframe-from-inside-a-jpanel
		topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		setLayout(new BorderLayout());

		loadVLCLibraries();


		controlsPanel = new PlayerControlsPanel(this);

		add(controlsPanel, BorderLayout.PAGE_END);

		mediaPlayerFactory = new MediaPlayerFactory();
		canvas = new Canvas();
		canvas.setBackground(Color.black);
		canvas.setMinimumSize(new Dimension(176,144));
		canvas.setPreferredSize(new Dimension(768,576));

		mediaPlayerEventAdapter=new MediaPlayerEventAdapter() {
			public void finished(MediaPlayer mediaPlayer) {
				System.out.println("MediaPlayer: event: finished");
				printPlayState();
			}
			public void playing(MediaPlayer mediaPlayer) {
				System.out.println("playing");
			}
		};


		// http://stackoverflow.com/questions/10051176/listening-handling-jpanel-events
		addHierarchyListener(new HierarchyListener() {

			@Override
			public void hierarchyChanged(HierarchyEvent e) {
				//System.out.println("Components Change: " + e.getChanged());
				if ((e.getChangeFlags() & HierarchyEvent.DISPLAYABILITY_CHANGED) != 0) {
					if (e.getComponent().isDisplayable()) {
						embeddedMediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();
						embeddedMediaPlayer.addMediaPlayerEventListener(mediaPlayerEventAdapter);
						embeddedMediaPlayer.setVideoSurface(
								mediaPlayerFactory.newVideoSurface(canvas)
								);
						embeddedMediaPlayer.setRepeat(true);
						MediaPlayer.this.add(canvas, BorderLayout.CENTER);
						MediaPlayer.this.revalidate();
						MediaPlayer.this.run();
						System.out.println("Is displayable!");
					} else {
						System.out.println("Is not displayable!");
						MediaPlayer.this.remove(canvas);
						embeddedMediaPlayer.release();
					}
				}
			}
		});
	}



	public void toggleFullScreen()
	{
		/*
		if (System.getProperty("os.name").equals("Windows"))
		{
		//caprica/vlcj/player/embedded/windows/Win32FullScreenHandler.java
		}
		*/
		// https://www3.ntu.edu.sg/home/ehchua/programming/java/J8b_Game_2DGraphics.html
		topFrame=((JFrame)SwingUtilities.getWindowAncestor(this));

		topFrame.dispose();
		if (!topFrame.isUndecorated()) {
			//save last bounds and its extended state
			lastState = topFrame.getExtendedState();
			lastBounds = topFrame.getBounds();
			//GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(topFrame);
			try{
				topFrame.setExtendedState(topFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
			}
			catch(Exception ev){
				topFrame.setBounds(getGraphicsConfiguration().getDevice().getDefaultConfiguration().getBounds());
				ev.printStackTrace();
			}
		}
		else {
			//restore last bounds and its extended state
			topFrame.setBounds(lastBounds);
			topFrame.setExtendedState(lastState);
		}
		topFrame.setUndecorated(!topFrame.isUndecorated());
		topFrame.setVisible(true);
	}

	private void loadVLCLibraries()
	{
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
			System.err.println(key + ": " + value);
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
		printPlayState();
		if (embeddedMediaPlayer.getMediaPlayerState() == libvlc_state_t.libvlc_Ended)
		{
			System.out.println("MediaPlayer: try play!");
			//embeddedMediaPlayer.start();
			run();
		}
		else
		{
			embeddedMediaPlayer.pause();
		}
		setPlayingState(!isPlaying);
	}

	public boolean getRepeatState()
	{
		try
		{
			return embeddedMediaPlayer.getRepeat();
		}
		catch (Exception e)
		{
			return true;
		}
	}

	public void toggleRepeat()
	{
		embeddedMediaPlayer.setRepeat(!embeddedMediaPlayer.getRepeat());
		controlsPanel.setRepeat(embeddedMediaPlayer.getRepeat());
	}


	public boolean getPlayingState()
	{
		//return isPlaying;
		try
		{
			return embeddedMediaPlayer.isPlaying();
		}
		catch (Exception e)
		{
			return true;
		}
	}

	private void setPlayingState(boolean isPlaying)
	{
		this.isPlaying = isPlaying;
		controlsPanel.setPlaying(isPlaying);
	}

	public void run() {
		System.out.println("MediaPlayer: Media path: " + mediaPath);
		if (!embeddedMediaPlayer.startMedia(mediaPath))
		{
			System.err.println("MediaPlayer: error occured while trying to play media " + mediaPath);
		}
		else setPlayingState(true);
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
		mainFrame.pack();
		mainFrame.setVisible(true);
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

	}

	private void printPlayState()
	{
		if (embeddedMediaPlayer.isPlaying())
		{
			System.out.println("MediaPlayer: is playing!");
		}
		else
		{
			System.out.println("MediaPlayer: is not playing!");
		}

		switch (embeddedMediaPlayer.getMediaPlayerState()) {
			case libvlc_Playing:
				System.out.println("MediaPlayer: state: libvlc_Playing");
				break;
			case libvlc_Paused:
				System.out.println("MediaPlayer: state: libvlc_Paused");
				break;
			case libvlc_Stopped:
				System.out.println("MediaPlayer: state: libvlc_Stopped");
				break;
			case libvlc_Ended:
				System.out.println("MediaPlayer: state: libvlc_Ended");
				break;
			case libvlc_Error:
				System.out.println("MediaPlayer: state: libvlc_Error");
				break;
			case libvlc_Buffering:
				System.out.println("MediaPlayer: state: libvlc_Buffering");
				break;
			case libvlc_Opening:
				System.out.println("MediaPlayer: state: libvlc_Opening");
				break;
			case libvlc_NothingSpecial:
				System.out.println("MediaPlayer: state: libvlc_NothingSpecial");
				break;
		}
	}


}
