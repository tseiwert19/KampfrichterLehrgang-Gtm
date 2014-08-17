package src.main.panel;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.image.*;

import javax.imageio.*;

import java.io.*;

public class WelcomePanel extends JPanel {

	// getClass().getResource() beachten !!!
	private static final String RESOURCEPATH = "../../../img/GeraeteLogos/";
	private static final String BARREN = "Barren";
	private static final String BODEN = "Boden";
	private static final String PAUSCHENPFERD = "Pauschenpferd";
	private static final String RECK = "Reck";
	private static final String RINGE = "Ringe";
	private static final String SPRUNG = "Sprung";
        private static final String FILESUFFIX = ".png";

	private static final String IMAGES[] = new String[] { BODEN, PAUSCHENPFERD,
			RINGE, SPRUNG, BARREN, RECK };

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
						RESOURCEPATH + IMAGES[indexIMAGES] + FILESUFFIX));
				buttonIcon = new ImageIcon(buttonImage);

				BorderLayout borderLayout = new BorderLayout();
				geraeteButton[indexIMAGES] = new KariButton();
				geraeteButton[indexIMAGES].setText(IMAGES[indexIMAGES]);
				geraeteButton[indexIMAGES].setIcon(buttonIcon);
				geraeteButton[indexIMAGES].setVerticalTextPosition(SwingConstants.BOTTOM);
				geraeteButton[indexIMAGES].setHorizontalTextPosition(SwingConstants.CENTER);
				geraeteButton[indexIMAGES].setFont(new Font("Arial",
						Font.ITALIC, 25));

				Color myRot = Color.decode("#b92d2e");

				geraeteButton[indexIMAGES].setBackground(myRot);
				geraeteButton[indexIMAGES].setForeground(Color.WHITE);

                                geraeteButton[indexIMAGES].setActionCommand(IMAGES[indexIMAGES]);
                                System.out.println(geraeteButton[indexIMAGES].getActionCommand());

				add(geraeteButton[indexIMAGES]);
				indexIMAGES++;
			}
		} catch (Exception e) {
			System.out.print(e);
		}

	}
}
