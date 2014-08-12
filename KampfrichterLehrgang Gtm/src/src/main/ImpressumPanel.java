package src.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImpressumPanel extends JPanel {

	private static final long serialVersionUID = -9115669597556271814L;

	public ImpressumPanel() {
		setPreferredSize(new Dimension(700, 50));

		Color myRot = Color.decode("#b92d2e");
		setBackground(myRot);

		JButton cdptext = new KariButton();
		cdptext.setText("<html><a style='text-decoration:underline'>Code de Pointage</a></html>");
		cdptext.setFont(new Font("Arial", Font.BOLD, 33));
		cdptext.setBorder(null);
		cdptext.setForeground(Color.white);

		cdptext.setBackground(myRot);
		cdptext.setBorderPainted(false);
		cdptext.setOpaque(true);

		JPanel m = new JPanel();
		m.setBackground(Color.WHITE);
		JLabel l = new JLabel();
		m.add(l, BorderLayout.WEST);
		m.add(this);
		
		
		
		add(cdptext);
	}
}
