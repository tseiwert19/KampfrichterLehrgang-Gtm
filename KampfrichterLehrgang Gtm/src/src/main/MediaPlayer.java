
package src.main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.NativeLibrary;

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

