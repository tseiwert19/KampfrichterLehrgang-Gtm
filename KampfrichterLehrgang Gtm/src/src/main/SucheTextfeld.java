package src.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SucheTextfeld extends JTextField implements MouseListener {

	private static final long serialVersionUID = -575653543070324305L;

	private Shape shape;

	private static final String STARTTEXT = "Suche ... ";
	private static final String LUPE_LOGO = "../../img/Logo/lupe.jpg";

	public SucheTextfeld() {
		setText(STARTTEXT);
		setPreferredSize(new Dimension(700, 40));
		setLayout(new BorderLayout());

		try {
			BufferedImage lupe = ImageIO
					.read(getClass().getResource(LUPE_LOGO));

			JLabel label = new JLabel(new ImageIcon(lupe));
			add(label, BorderLayout.EAST);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addMouseListener(this);
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

	public boolean contains(int x, int y) {
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1,
					getHeight() - 1, 15, 15);
		}
		return shape.contains(x, y);
	}

	public void mouseClicked(MouseEvent e) {
		if (getText().equals(STARTTEXT)) {
			setText("");
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
