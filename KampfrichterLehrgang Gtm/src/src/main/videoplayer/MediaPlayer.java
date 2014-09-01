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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.Icon;

import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.binding.LibVlcConst;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.player.embedded.DefaultFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.x.XFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.FullScreenStrategy;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.binding.internal.libvlc_state_t;
import uk.co.caprica.vlcj.runtime.x.LibXUtil;

class PauseIcon implements Icon{
	private int width;
	private int height;

	private BasicStroke stroke=new BasicStroke(2);

	PauseIcon(int w, int h)
	{
		width=w;
		height=h;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setColor(Color.black);
		g2d.setStroke(stroke);
		//g2d.drawRect(x+1,y+1,width-2,height-2);
		g2d.fillRect(x+(2*width/9),y+4,(2*width/9),height-8);
		g2d.fillRect(x+5*width/9,y+4,(2*width/9),height-8);
		g2d.dispose();
	}

	public int getIconWidth() {
		return width;
	}

	public int getIconHeight() {
		return height;
	}

}

class PlayIcon implements Icon{
	private int width;
	private int height;

	PlayIcon(int w, int h)
	{
		width=w;
		height=h;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setColor(Color.black);
		GeneralPath path=new GeneralPath(); path.moveTo(x+2, y+2);
		path.lineTo(x+width-2 , y+height/2);
		path.lineTo(x+2, y+height-2);
		path.lineTo(x+2, y+2);
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

class FullScreenIcon implements Icon{
	private int width;
	private int height;

	private BasicStroke stroke;

	FullScreenIcon(int w, int h)
	{
		width=w;
		height=h;
		stroke=new BasicStroke(width/11);
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setColor(Color.black);
		g2d.setStroke(stroke);
		g2d.fillRect(x+width/10,y+height/10,width-width/5,height-height/5);
		g2d.drawLine(x, y, x+width/5, y);
		g2d.drawLine(x, y, x, y+height/5);
		g2d.drawLine(x+width, y, x+width-width/5, y);
		g2d.drawLine(x+width, y, x+width, y+height/5);
		g2d.drawLine(x+width, y+height, x+width-width/5, y+height);
		g2d.drawLine(x+width, y+height, x+width, y+height-height/5);
		g2d.drawLine(x, y+height, x+width/5, y+height);
		g2d.drawLine(x, y+height, x, y+height-height/5);
		//g2d.drawLine(x +10, y + 10, x + width -10, y + height -10);
		//g2d.drawLine(x +10, y + height -10, x + width -10, y + 10);
		g2d.dispose();
	}

	public int getIconWidth() {
		return width;
	}

	public int getIconHeight() {
		return height;
	}

}

class RepeatIcon implements Icon{
	private int width;
	private int height;

	private BasicStroke stroke;

	RepeatIcon(int w, int h)
	{
		width=w;
		height=h;
		stroke=new BasicStroke(width/20);
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setColor(Color.black);
		g2d.setStroke(stroke);
		g2d.drawRect(x+1,y+1,width-2,height-2);
		g2d.dispose();
	}

	public int getIconWidth() {
		return width;
	}

	public int getIconHeight() {
		return height;
	}

}

class MissingIcon implements Icon{

	private int width = 32;
	private int height = 32;

	private BasicStroke stroke = new BasicStroke(4);

	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2d = (Graphics2D) g.create();

		/*
		g2d.setColor(Color.black);
		g2d.setStroke(stroke);
		g2d.drawLine(3, 3, 3, 11);
		g2d.drawLine(8, 3, 8, 11);
		*/

		g2d.setColor(Color.BLACK);
		//g2d.drawRect(x+1,y+1,width-1,height-1);
		GeneralPath path=new GeneralPath(); path.moveTo(x+2, y+2);
		path.lineTo(x+width-2 , y+height/2);
		path.lineTo(x+2, y+height-2);
		path.lineTo(x+2, y+2);
		g2d.fill(path);

/*
		g2d.setColor(Color.WHITE);
		g2d.fillRect(x +1 ,y + 1,width -2 ,height -2);

		g2d.setColor(Color.BLACK);
		g2d.drawRect(x +1 ,y + 1,width -2 ,height -2);

		g2d.setColor(Color.RED);

		g2d.setStroke(stroke);
		g2d.drawLine(x +10, y + 10, x + width -10, y + height -10);
		g2d.drawLine(x +10, y + height -10, x + width -10, y + 10);
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

public class MediaPlayer extends JPanel
{
	//TODO: exception
	//http://docs.oracle.com/javase/tutorial/i18n/resbundle/concept.html
	private ResourceBundle localeBundle=ResourceBundle.getBundle("src.main.videoplayer.localization.MediaPlayer");
	//private JPanel videoPlayerPanel=new JPanel();
	private JPanel controlsPanel=new JPanel();

    private JButton pauseButton;
    private JToggleButton repeatButton;
	private JButton fullScreenButton;

	private PlayIcon playIcon;
	private PauseIcon pauseIcon;
	private FullScreenIcon fullScreenIcon;
	private RepeatIcon repeatIcon;
	private String pauseButtonLabel, playButtonLabel, fullscreenButtonLabel, repeatButtonLabel;

	private EmbeddedMediaPlayerComponent embeddedMediaPlayerComponent;
	private String mediaPath="";

	private JFrame topFrame;
	private MediaPlayer mediaPlayer;

	//MediaPlayer(String mediaURL, JFrame f)
	public MediaPlayer(String mediaURL)
	{
		mediaPlayer=this;
		this.mediaPath=mediaURL;

		//http://stackoverflow.com/questions/9650874/java-swing-obtain-window-jframe-from-inside-a-jpanel
		topFrame=(JFrame)SwingUtilities.getWindowAncestor(this);
		//topFrame=f;

		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "lib");
		embeddedMediaPlayerComponent=new EmbeddedMediaPlayerComponent();
		/*
		embeddedMediaPlayerComponent=new EmbeddedMediaPlayerComponent()
		{
			//TODO: für alle Plattformen
			protected FullScreenStrategy onGetFullScreenStrategy() {
				return new XFullScreenStrategy(topFrame);
			}
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
				embeddedMediaPlayerComponent.release();
				System.exit(0);
			}

			public void error(MediaPlayer mediaPlayer) {
				System.out.println("error()");
			}
		};
		*/

			/*
        embeddedMediaPlayerComponent.getMediaPlayer().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
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
				System.out.println("finished()");
            }

            public void error(MediaPlayer mediaPlayer) {
				System.out.println("error()");
            }
        });
			*/

		embeddedMediaPlayerComponent.getMediaPlayer().setRepeat(true);




		setLayout(new BorderLayout());
		embeddedMediaPlayerComponent.setPreferredSize(new Dimension(768, 576));
		controlsPanel.setLayout(new FlowLayout());

		//http://docs.oracle.com/javase/tutorial/uiswing/components/icon.html
		playIcon=new PlayIcon(32,32);
		pauseIcon=new PauseIcon(32,32);
		fullScreenIcon=new FullScreenIcon(32,32);
		repeatIcon=new RepeatIcon(32,32);

		pauseButtonLabel=localeBundle.getString("pauseButtonLabel");
		playButtonLabel=localeBundle.getString("playButtonLabel");
		fullscreenButtonLabel=localeBundle.getString("fullscreenButtonLabel");
		repeatButtonLabel=localeBundle.getString("repeatButtonLabel");

		pauseButton=new JButton();
		//if (embeddedMediaPlayerComponent.getMediaPlayer().getMediaPlayerState() == libvlc_state_t.libvlc_Playing )
		if (embeddedMediaPlayerComponent.getMediaPlayer().isPlaying())
		{
			pauseButton.setText(pauseButtonLabel);
			pauseButton.setIcon(pauseIcon);
		}
		else
		{
			pauseButton.setText(playButtonLabel);
			pauseButton.setIcon(playIcon);
		}
		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				embeddedMediaPlayerComponent.getMediaPlayer().pause();
				//if (embeddedMediaPlayerComponent.getMediaPlayer().getMediaPlayerState() == libvlc_state_t.libvlc_Paused )
				if (embeddedMediaPlayerComponent.getMediaPlayer().isPlaying())
				{
					System.out.println("Playing");
					pauseButton.setText(pauseButtonLabel);
					pauseButton.setIcon(pauseIcon);
				}
				else
				{
					System.out.println("Not playing");
					pauseButton.setText(playButtonLabel);
					pauseButton.setIcon(playIcon);
				}
			}
		});
		controlsPanel.add(pauseButton);

		repeatButton=new JToggleButton(repeatButtonLabel, embeddedMediaPlayerComponent.getMediaPlayer().getRepeat());
		repeatButton.setIcon(repeatIcon);
		repeatButton.setToolTipText(repeatButtonLabel);
		repeatButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean repeatState=embeddedMediaPlayerComponent.getMediaPlayer().getRepeat();
				embeddedMediaPlayerComponent.getMediaPlayer().setRepeat(!repeatState);
				repeatState=embeddedMediaPlayerComponent.getMediaPlayer().getRepeat();
				repeatButton.setSelected(repeatState);
			}
		});
		controlsPanel.add(repeatButton);

		fullScreenButton=new JButton();
		fullScreenButton.setIcon(fullScreenIcon);
		fullScreenButton.setText(fullscreenButtonLabel);
		fullScreenButton.setToolTipText(fullscreenButtonLabel);
        fullScreenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //embeddedMediaPlayerComponent.getMediaPlayer().toggleFullScreen();
				if (GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getFullScreenWindow() == null)
				{
					GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow((JFrame)SwingUtilities.getWindowAncestor(mediaPlayer));
					//DisplayMode displayMode=getDisplayMode(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayModes());
					DisplayMode displayMode=GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
					LibXUtil.setFullScreenWindow(SwingUtilities.getWindowAncestor(mediaPlayer), true);
					if (displayMode != null)
					{
						System.out.println("Hallo");
						GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setDisplayMode(displayMode);
					}
					SwingUtilities.getWindowAncestor(mediaPlayer).invalidate();
					SwingUtilities.getWindowAncestor(mediaPlayer).validate();
				}
				else
				{
					//((JFrame)SwingUtilities.getWindowAncestor(mediaPlayer)).dispose();
					//((JFrame)SwingUtilities.getWindowAncestor(mediaPlayer)).setUndecorated(false);
					LibXUtil.setFullScreenWindow(SwingUtilities.getWindowAncestor(mediaPlayer), false);
					GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(null);
					//((JFrame)SwingUtilities.getWindowAncestor(mediaPlayer)).toFront();
					//((JFrame)SwingUtilities.getWindowAncestor(mediaPlayer)).setVisible(true);
                        // f.dispose();
                        // f.setBounds(0, 0, 1920, 1080);
                        //f.toFront();
                        //f.setVisible(true);
				}
				boolean vollMoeglich=GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().isFullScreenSupported();
				if (vollMoeglich==true)
				{
					System.out.println("Vollbild möglich");
				}

            }
        });
		controlsPanel.add(fullScreenButton);


        add(embeddedMediaPlayerComponent, BorderLayout.CENTER);
        add(controlsPanel, BorderLayout.PAGE_END);
        //videoPlayerPanel.pack();

		//TODO: player muss dem repeat button einen veränderten
		//repeat-state mitteilen, ebenso wie dem pause-button
		//repeatButton.setSelected(false);

		//setMinimumSize();
	}

    protected DisplayMode getDisplayMode(DisplayMode[] displayModes) {
        System.out.println(Arrays.toString(displayModes));
        return null;
    }


	public void run()
	{
		System.out.println("Media path: " + mediaPath);
		embeddedMediaPlayerComponent.getMediaPlayer().playMedia(mediaPath);
	}


	public static void main(String[] args)
	{
		if (args.length<1) {
			System.out.println("No argument given");
			System.exit(1);
		}

		JFrame mainFrame=new JFrame(args[0]);
		mainFrame.setSize(640,480);

		MediaPlayer myMediaPlayer=new MediaPlayer(args[0]);
		mainFrame.setContentPane(myMediaPlayer);

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainFrame.pack();
		mainFrame.setVisible(true);

		myMediaPlayer.run();
	}
}

