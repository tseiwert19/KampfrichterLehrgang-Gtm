/*
 * file:///tmp/vlcj-3.0.1/javadoc/uk/co/caprica/vlcj/discovery/NativeDiscovery.html
 * oder
 * https://github.com/caprica/vlcj/blob/master/src/test/java/uk/co/caprica/vlcj/test/quickstart/Example2.java
 * http://www.capricasoftware.co.uk/projects/vlcj/faq.html
 * https://github.com/caprica/vlcj/blob/master/src/test/java/uk/co/caprica/vlcj/test/discovery/NativeDiscoveryTest.java
 */



package src.main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.binding.LibVlcConst;
//import uk.co.caprica.vlcj.filter.swing.SwingFileFilterFactory;
//import uk.co.caprica.vlcj.player.MediaPlayer;
//import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
//import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class MediaPlayer
{
	private JPanel videoPlayerPanel=new JPanel();
	private JPanel controlsPanel=new JPanel();
    private JButton pauseButton=new JButton();
    private JButton repeatButton=new JButton();
	private EmbeddedMediaPlayerComponent ourMediaPlayer;
	private String mediaPath="";

	MediaPlayer(String mediaURL)
	{
		this.mediaPath=mediaURL;

		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "lib");


		videoPlayerPanel.setLayout(new BorderLayout());
		controlsPanel.setLayout(new FlowLayout());
		pauseButton.setText("Pause");
		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ourMediaPlayer.getMediaPlayer().pause();
			}
		});
		controlsPanel.add(pauseButton);
		repeatButton.setText("Repeat");
		repeatButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ourMediaPlayer.getMediaPlayer().setRepeat(true);
			}
		});
		controlsPanel.add(repeatButton);

		ourMediaPlayer=new EmbeddedMediaPlayerComponent();
        videoPlayerPanel.add(ourMediaPlayer, BorderLayout.CENTER);
        videoPlayerPanel.add(controlsPanel, BorderLayout.SOUTH);
        //videoPlayerPanel.pack();



	}

	public JPanel getVideoPlayerPanel()
	{
		return videoPlayerPanel;
	}

	public void run()
	{
		ourMediaPlayer.getMediaPlayer().playMedia(mediaPath);
	}
}

