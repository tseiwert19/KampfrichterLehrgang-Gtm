package src.main;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;

public class NavigationPanel extends JPanel {

  // Der RESOURCEPATH sollte noch geaendert werden nach noch zu beschliessender
  // konvention
  private static final String RESOURCEPATH = "../resources/img/logo/logo.png";

  private JPanel navigationP;

  public NavigationPanel () {

    setLayout(new BorderLayout());

    JButton tempBackButton = new JButton("Back");
    add(tempBackButton, BorderLayout.WEST);

    /* Bin ganz ehrlich hier, die Loesung is von Stackoverflow und ist
     * sehr kurz und einfach gehalten. Keine Performance test gemacht und
     * der try block sollte vielleicht auch woanderst hingesetzt werden.
     * Ich lass es mal drinne : >
     */
    try {
      BufferedImage logo = ImageIO.read(new File(RESOURCEPATH));
      JLabel logoLabel = new JLabel(new ImageIcon(logo));
      add(logoLabel, BorderLayout.CENTER);
    } catch (Exception e) {
      System.out.print(e);
    }

    setVisible(true);
  }
}
