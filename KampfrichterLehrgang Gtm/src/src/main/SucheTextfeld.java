package src.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SucheTextfeld extends JTextField implements MouseListener,
		KeyListener {

	private static final long serialVersionUID = -575653543070324305L;

	private static final String STARTTEXT = "Nach Element suchen ... ";
	private static final String LUPE_LOGO = "../../img/Logo/lupe.jpg";

	public SucheTextfeld() {
		setText(STARTTEXT);
		setPreferredSize(new Dimension(700, 40));
		setLayout(new BorderLayout());

		try {
			BufferedImage lupe = ImageIO
					.read(getClass().getResource(LUPE_LOGO));

			JButton lupeButton = new KariButton();
			lupeButton.setIcon(new ImageIcon(lupe));
			lupeButton.setPreferredSize(new Dimension(50, 40));

			add(lupeButton, BorderLayout.EAST);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addMouseListener(this);
		addKeyListener(this);
	}

	protected void paintComponent(Graphics g) {
		g.setColor(getBackground());
		g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
		super.paintComponent(g);
	}

	protected void paintBorder(Graphics g) {
		g.setColor(getForeground());
		g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
	}

	public void mouseClicked(MouseEvent e) {
		if (getText().equals(STARTTEXT)) {
			setText("");
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		setToolTipText("<html>Geben sie hier bitte den Begriff ein nach, nach dem sie suchen wollen <br /><br /> Mit Enter startet die Suche </html>");
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (getText().equals(STARTTEXT)) {
			setText("");
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (getText().equals(STARTTEXT)) {
			setText("");
		}
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
			System.out.println("ENTER key pressed");
		}
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {
	}
}
