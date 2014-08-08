package src.main;

import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class WelcomePanel extends JPanel {

  // getClass().getResource() beachten !!!
  private static final String RESOURCEPATH = "../resources/img/GeraeteLogos/";
  private static final String BARREN = "Barren.png";
  private static final String BODEN = "Boden.png";
  private static final String PAUSCHENPFERD = "Pauschenpferd.png";
  private static final String RECK = "Reck.png";
  private static final String RINGE = "Ringe.png";
  private static final String SPRUNG = "Sprung.png";

  private static final String IMAGES[] = new String[] {BARREN,
                                                        BODEN,
                                                        PAUSCHENPFERD,
                                                        RECK,
                                                        RINGE,
                                                        SPRUNG};
  public WelcomePanel () {
    setLayout(new GridLayout());

    try {
      BufferedImage buttonImage;
      Icon buttonIcon;
      JButton[] geraeteButton = new JButton[IMAGES.length];
      int indexIMAGES = 0;
  
      while (indexIMAGES <= (IMAGES.length - 1)) {
        System.out.println(indexIMAGES);
        buttonImage = ImageIO.read(new File(RESOURCEPATH + IMAGES[indexIMAGES]));
        buttonIcon = new ImageIcon(buttonImage);
        geraeteButton[indexIMAGES] = new JButton(IMAGES[indexIMAGES],buttonIcon);
        add(geraeteButton[indexIMAGES]);
        indexIMAGES++;
      }
    } catch (Exception e) {
      System.out.print(e);
    }

  }
}
