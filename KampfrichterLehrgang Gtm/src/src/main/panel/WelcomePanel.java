package src.main.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import src.main.Controller;
import src.main.components.KariButton;

public class WelcomePanel extends CenterPanel {

	private static final long serialVersionUID = -1882830821523886193L;
	private static final String RESOURCEPATH = "/img/GeraeteLogos/";
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
			KariButton newGeraeteButton;
			int indexIMAGES = 0;

			while (indexIMAGES <= (IMAGES.length - 1)) {
				buttonImage = ImageIO.read(getClass().getResource(
						RESOURCEPATH + IMAGES[indexIMAGES] + FILESUFFIX));
				buttonIcon = new ImageIcon(buttonImage);

				newGeraeteButton = Controller.getWelcomeActionListener()
						.getButton();
				newGeraeteButton.setText(IMAGES[indexIMAGES]);
				newGeraeteButton.setIcon(buttonIcon);
				newGeraeteButton.setVerticalTextPosition(SwingConstants.BOTTOM);
				newGeraeteButton
						.setHorizontalTextPosition(SwingConstants.CENTER);
				newGeraeteButton.setFont(new Font("Arial", Font.ITALIC, 25));

				Color myRot = Color.decode("#b92d2e");

				newGeraeteButton.setBackground(myRot);
				newGeraeteButton.setForeground(Color.WHITE);

				newGeraeteButton.setActionCommand(IMAGES[indexIMAGES]);

				add(newGeraeteButton);
				indexIMAGES++;
			}
		} catch (Exception e) {
			System.out.print(e);
		}

	}
}
