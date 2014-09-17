package src.main.videoplayer;

import src.main.*;

import java.util.ResourceBundle;

import javax.swing.JPanel;

import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.Icon;

import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Component;
import java.awt.BasicStroke;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.RenderingHints;

import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class PlayerControlsPanel extends JPanel
{
	private JButton playPauseButton;
	private JToggleButton repeatButton;
	private JButton fullScreenButton;

	private PlayIcon playIcon;
	private PauseIcon pauseIcon;
	private FullScreenIcon fullScreenIcon;
	private RepeatIcon repeatIcon;
	private String pauseLabel, playLabel, fullscreenButtonLabel,
			repeatButtonLabel;

	private ResourceBundle localeBundle;

	private MediaPlayer mediaPlayer;

	public PlayerControlsPanel(MediaPlayer m)
	{
		this.mediaPlayer = m;

		// http://docs.oracle.com/javase/tutorial/i18n/resbundle/concept.html
		try
		{
			localeBundle = ResourceBundle
				.getBundle("src.main.videoplayer.localization.MediaPlayer");
		}
		catch (Exception e)
		{
			System.err.println("Failed to load localization!");
		}


		setLayout(new FlowLayout());

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
		setPlaying(mediaPlayer.getPlayingState());
		playPauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mediaPlayer.pause();
				PlayerControlsPanel.this.setPlaying(mediaPlayer.getPlayingState());
			}
		});
		add(playPauseButton);

		repeatButton = new JToggleButton(repeatButtonLabel,
				mediaPlayer.getRepeatState());
		repeatButton.setIcon(repeatIcon);
		repeatButton.setToolTipText(repeatButtonLabel);
		repeatButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mediaPlayer.toggleRepeat();
			}
		});
		add(repeatButton);

		/*
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
		add(fullScreenButton);
		*/

		// TODO: player muss dem repeat button einen veränderten
		// repeat-state mitteilen, ebenso wie dem pause-button
		// repeatButton.setSelected(false);

	}

	public void setPlaying(boolean isPlaying)
	{
		if (isPlaying) {
			playPauseButton.setText(pauseLabel);
			playPauseButton.setToolTipText(pauseLabel);
			playPauseButton.setIcon(pauseIcon);
		} else {
			playPauseButton.setText(playLabel);
			playPauseButton.setToolTipText(playLabel);
			playPauseButton.setIcon(playIcon);
		}
	}

	public void setRepeat(boolean repeatState)
	{
		repeatButton.setSelected(repeatState);
	}
}
