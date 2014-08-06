package src.main;

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Kampfrichterlehrgang extends JFrame {

  private static final String NAME = "Kampfrichter Lehrgang";

  private JFrame mainFrame;

  private NavigationPanel navigationPanel;
  private JPanel impressumPanel;
  private JPanel welcomePanel;

  // Fuer die "Zurueck" Button funktionalitaet ist eine Stackloesung angedacht.
  private Stack<JPanel> backStack;

  public Kampfrichterlehrgang () {
    backStack = new Stack<JPanel>();

    // Weil mainFrame der TopLevel Container ist werden die methoden hier
    // weitergeleitet an das contentPane.
    // Steht im Oracle Java turorial zu Top-Level Containers
    mainFrame = new JFrame(NAME);
    mainFrame.setLayout(new BorderLayout());

    // insert navigationPanel to top
    add(buildNavigationPanel(), BorderLayout.NORTH);

    // insert welcomePanel to center
    add(buildWelcomePanel(), BorderLayout.CENTER);

    // insert impressumPanel to bottom
    add(buildImpressumPanel(), BorderLayout.SOUTH);

    setVisible(true);
  }

  /**
   * Wurzelfunktion zum wechseln des CenterPanels.
   * Beachte changeToVideo(), changeToWelcome() und changeToResult().
   */
  public void changeCenterPanel(String changeArg) {}

  private void changeToVideo() {}
  private void changeToWelcome() {}
  private void changeToResult() {}

  /**
   * Baut ein Navigations JPanel.
   * Das Panel enthaelt ein Logo sowie einen Zurueck Button dessen Funktion
   * mit dem in dieser Klasse definierten Stack implementiert wird.
   */
  private NavigationPanel buildNavigationPanel() {
    navigationPanel = new NavigationPanel();
    return navigationPanel;
  }

  /**
   * Baut eine Impressums JPanel nach VOrlage einer noch anzufertigen Klasse
   * und gibt dieses zurueck.
   */
  private JPanel buildImpressumPanel() {
    impressumPanel = new JPanel();
    return impressumPanel;
  }

  /**
   * Baut das Welcome JPanel nach Vorlage einer noch anzufertigen Klasse
   * und gibt dieses zurueck.
   */
  private JPanel buildWelcomePanel() {
    welcomePanel = new JPanel();
    return welcomePanel;
  }

  public static void main (String args[]) {
    Kampfrichterlehrgang k = new Kampfrichterlehrgang();
  }
}
