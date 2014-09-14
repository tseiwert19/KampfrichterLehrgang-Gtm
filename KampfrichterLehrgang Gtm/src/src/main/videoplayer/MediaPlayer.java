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

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

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

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.direct.BufferFormat;
import uk.co.caprica.vlcj.player.direct.BufferFormatCallback;
import uk.co.caprica.vlcj.player.direct.DirectMediaPlayer;
import uk.co.caprica.vlcj.player.direct.RenderCallbackAdapter;
import uk.co.caprica.vlcj.player.direct.format.RV32BufferFormat;


public class MediaPlayer extends JPanel {
	private PlayerControlsPanel controlsPanel;


	private EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent;
	private String mediaPath = "";

	private boolean isPlaying;

	private JFrame topFrame;
	private MediaPlayer mediaPlayer;

    private final int width = 768;
    private final int height = 576;
    private final BufferedImage image;
    private final MediaPlayerFactory factory;
    private final DirectMediaPlayer directMediaPlayer;
    private ImagePane imagePane;

	private int lastState = 0;
	private Rectangle lastBounds = null;

	public MediaPlayer(String mediaURL) {
		mediaPlayer = this;
		this.mediaPath = mediaURL;

		this.isPlaying = false;

		// http://stackoverflow.com/questions/9650874/java-swing-obtain-window-jframe-from-inside-a-jpanel
		topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

		loadVLCLibraries();

        image = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(width, height);
        image.setAccelerationPriority(1.0f);

		imagePane = new ImagePane(image);
		imagePane.setSize(width, height);
		imagePane.setMinimumSize(new Dimension(width, height));
		imagePane.setPreferredSize(new Dimension(width, height));

        factory = new MediaPlayerFactory();
        directMediaPlayer = factory.newDirectMediaPlayer(new TestBufferFormatCallback(), new TestRenderCallback());
		
		directMediaPlayer.setRepeat(true);

		controlsPanel = new PlayerControlsPanel(this);

		setLayout(new BorderLayout());

		add(controlsPanel, BorderLayout.PAGE_END);
		add(imagePane, BorderLayout.CENTER);
	}



	public void toggleFullScreen()
	{
		// https://www3.ntu.edu.sg/home/ehchua/programming/java/J8b_Game_2DGraphics.html
		topFrame=((JFrame)SwingUtilities.getWindowAncestor(mediaPlayer));

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
		directMediaPlayer.pause();
	}

	public boolean getRepeatState()
	{
		return directMediaPlayer.getRepeat();
	}

	public void setRepeatState(boolean repeatState)
	{
		directMediaPlayer.setRepeat(repeatState);
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
		directMediaPlayer.playMedia(mediaPath);
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


    @SuppressWarnings("serial")
    private final class ImagePane extends JPanel {
        private final BufferedImage image;

        public ImagePane(BufferedImage image) {
            this.image = image;
        }

        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            g2.drawImage(image, null, 0, 0);
        }
    }

    private final class TestRenderCallback extends RenderCallbackAdapter {

        public TestRenderCallback() {
            super(((DataBufferInt) image.getRaster().getDataBuffer()).getData());
        }

        @Override
        public void onDisplay(DirectMediaPlayer mediaPlayer, int[] data) {
            imagePane.repaint();
        }
    }

    private final class TestBufferFormatCallback implements BufferFormatCallback {

        @Override
        public BufferFormat getBufferFormat(int sourceWidth, int sourceHeight) {
            return new RV32BufferFormat(width, height);
        }

    }
}
