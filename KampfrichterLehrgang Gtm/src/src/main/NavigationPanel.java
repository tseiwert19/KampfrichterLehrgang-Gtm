package src.main;

import java.awt.*;
import java.awt.image.*;

import javax.swing.*;
import javax.imageio.*;

import java.io.*;

public class NavigationPanel extends JPanel {

	// Der RESOURCEPATH sollte noch geaendert werden nach noch zu
	// beschliessender
	// konvention
	private static final String DTB_LOGO = "../../img/Logo/dtb-logo.jpg";
	private static final String ZURUECK_LOGO = "../../img/Logo/zurueck_button.png";

	private JPanel navigationPanel;

	public NavigationPanel() {

		setLayout(new BorderLayout());

		try {
			BufferedImage zurueck = ImageIO.read(getClass().getResource(
					ZURUECK_LOGO));
			JButton tempBackButton = new JButton(new ImageIcon(zurueck));
			tempBackButton.setBackground(Color.WHITE);
			tempBackButton.setPreferredSize(new Dimension(120, 90) );
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

		setVisible(true);
	}
}
