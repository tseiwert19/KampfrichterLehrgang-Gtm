package src.main;

import java.awt.*;
import java.awt.image.*;

import javax.swing.*;
import javax.imageio.*;

import java.io.*;
import java.util.jar.JarInputStream;

public class NavigationPanel extends JPanel {


	private static final long serialVersionUID = -7451396862135969124L;

	// Der RESOURCEPATH sollte noch geaendert werden nach noch zu
	// beschliessender
	// konvention
	private static final String DTB_LOGO = "../../img/Logo/dtb-logo.jpg";
//	private static final String ZURUECK_LOGO = "../../img/Logo/zurueck_button.png";
	private static final String ZURUECK_LOGO = "../../img/Logo/rot_zurueck.jpg";

	private JPanel navigationPanel;

	public NavigationPanel() {
			    
		setBackground(Color.WHITE);

		setLayout(new BorderLayout());

		try {
			BufferedImage zurueck = ImageIO.read(getClass().getResource(
					ZURUECK_LOGO));
			JButton tempBackButton = new KariButton();
			tempBackButton.setIcon(new ImageIcon(zurueck));
            tempBackButton.setFocusPainted(false);
            tempBackButton.setBorder(BorderFactory.createEmptyBorder());
			tempBackButton.setOpaque(false);
			tempBackButton.setPreferredSize(new Dimension(60,60));

			add(tempBackButton, BorderLayout.WEST);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/*
		 * Bin ganz ehrlich hier, die Loesung is von Stackoverflow und ist sehr
		 * kurz und einfach gehalten. Keine Performance test gemacht und der try
		 * block sollte vielleicht auch woanderst hingesetzt werden. Ich lass es
		 * mal drinne : >
		 */
		try {
			BufferedImage logo = ImageIO.read(getClass().getResource(DTB_LOGO));
			JLabel logoLabel = new JLabel(new ImageIcon(logo));
			add(logoLabel, BorderLayout.CENTER);
		} catch (Exception e) {
			System.out.print(e);
		}

		JPanel m = new JPanel();
		m.setBackground(Color.WHITE);
		JLabel l = new JLabel();
		m.add(l, BorderLayout.WEST);

		JTextField sucheFeld = new SucheTextfeld();
		m.add(sucheFeld, BorderLayout.CENTER);
		
		add(m, BorderLayout.SOUTH);
		
		setVisible(true);
	}
}
