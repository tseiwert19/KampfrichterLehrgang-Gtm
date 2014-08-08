package src.main;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.image.*;

import javax.imageio.*;

import java.io.*;

public class WelcomePanel extends JPanel {

	// getClass().getResource() beachten !!!
	private static final String RESOURCEPATH = "../../img/GeraeteLogos/";
	private static final String BARREN = "Barren.png";
	private static final String BODEN = "Boden.png";
	private static final String PAUSCHENPFERD = "Pauschenpferd.png";
	private static final String RECK = "Reck.png";
	private static final String RINGE = "Ringe.png";
	private static final String SPRUNG = "Sprung.png";

	private static final String IMAGES[] = new String[] { BARREN, BODEN,
			PAUSCHENPFERD, RECK, RINGE, SPRUNG };

	public WelcomePanel() {
		GridLayout gridLayout = new GridLayout(2, 3);
		gridLayout.setVgap(20);
		gridLayout.setHgap(20);

		setLayout(gridLayout);

		setBorder(new EmptyBorder(20, 20, 20, 20));
		setBackground(Color.WHITE);

		try {
			BufferedImage buttonImage;
			Icon buttonIcon;
			JButton[] geraeteButton = new JButton[IMAGES.length];
			int indexIMAGES = 0;

			while (indexIMAGES <= (IMAGES.length - 1)) {
				System.out.println(indexIMAGES);
				buttonImage = ImageIO.read(getClass().getResource(
						RESOURCEPATH + IMAGES[indexIMAGES]));
				buttonIcon = new ImageIcon(buttonImage);
				geraeteButton[indexIMAGES] = new JButton("  "
						+ IMAGES[indexIMAGES].substring(0,
								IMAGES[indexIMAGES].length() - 4), buttonIcon);
				geraeteButton[indexIMAGES].setFont(new Font("Arial",
						Font.ITALIC, 25));

				Color myRot = Color.decode("#b92d2e");

				geraeteButton[indexIMAGES].setBackground(myRot);
				geraeteButton[indexIMAGES].setForeground(Color.WHITE);

				add(geraeteButton[indexIMAGES]);
				indexIMAGES++;
			}
		} catch (Exception e) {
			System.out.print(e);
		}

	}
}
