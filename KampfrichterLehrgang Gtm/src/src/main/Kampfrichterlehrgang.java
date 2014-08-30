package src.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Stack;

import src.main.listener.WelcomeActionListener;
import src.main.panel.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Kampfrichterlehrgang extends JFrame {

	private static final long serialVersionUID = 1L;

        // TODO Das wird nicht immer in der Mitte sein
        // dynamische Loesung suchen oder einigen
	private static final String NAME = "                                                              "
			+ "                                   "
			+ "      KampfrichterLehrgang Gtm";

	private NavigationPanel navigationPanel;
	private ImpressumPanel impressumPanel;
	private WelcomePanel welcomePanel;
        private ResultPanel resultPanel;

        private WelcomeActionListener welcomeActionListener;

	protected String curLF = "javax.swing.plaf.metal.MetalLookAndFeel";

	// Fuer die "Zurueck" Button funktionalitaet ist eine Stackloesung
	// angedacht.
	@SuppressWarnings("unused")
	private Stack<JPanel> backStack;

	public Kampfrichterlehrgang() {
		setBackground(Color.WHITE);
		backStack = new Stack<JPanel>();

		setTitle(NAME);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setSize(900, 700);
		setMinimumSize(new Dimension(900,700));
		setLocation(300, 100);

		// insert navigationPanel to top
		getContentPane().add(buildNavigationPanel(), BorderLayout.NORTH);

		// insert welcomePanel to center
                Controller.setCurrentCenterPanel(buildWelcomePanel());
		getContentPane().add(Controller.getCurrentCenterPanel(), BorderLayout.CENTER);

		// insert impressumPanel to bottom
                getContentPane().add(buildImpressumPanel(), BorderLayout.SOUTH);

		

                Controller.setKampfrichterlehrgang(this);
		setVisible(true);
	}

        // Angedacht fall noch ein Home Button eingebaut wird
        @SuppressWarnings("unused")
	public void changeToWelcome() {
	}

        /**
         * Wechselt auf das ein uebergebenes, vorher erzeugtes ResultPanel.
         */
	public void changeToResult(String search) {
          // Debug
          System.out.println("Start creating ResultPanel: " + search);
          resultPanel = new ResultPanel(search);
          Controller.setResultPanel(resultPanel);
          changeCenterPanel(resultPanel);
	}

        /**
         * TODO Diese Methode funktioniert bisher NUR mit dem resultpanel.
         * um die Funktion zu erweitern muss man irgendwie nen generic
         * uebergeben und es dann nach einem kriterium casten.
         * Alternativ muss man mehrfach den gleichen code mit verschiedenem
         * uebergabeparameter schreiben.
         * Beachte dass dann auch das zu entfernende Panel dynamisch
         * gefunden werden muss.
         */
        private void changeCenterPanel(CenterPanel newCenterPanel) {
          //Debug
          System.out.println("Changing center panel.");
          this.getContentPane().remove(Controller.getCurrentCenterPanel());
          // Hier war mal das ScrollPane, raugeschmissen weils noch nicht richtig
          // implementiert war >.> Reimsi
          this.getContentPane().add(newCenterPanel, BorderLayout.CENTER);
          Controller.setCurrentCenterPanel(newCenterPanel);
          this.getContentPane().validate();
          this.getContentPane().repaint();
        }


	/**
	 * Baut ein Navigations JPanel. Das Panel enthaelt ein Logo sowie einen
	 * Zurueck Button dessen Funktion mit dem in dieser Klasse definierten Stack
	 * implementiert wird.
	 */
	private NavigationPanel buildNavigationPanel() {
		navigationPanel = new NavigationPanel();
                Controller.setNavigationPanel(navigationPanel);
		return navigationPanel;
	}

	/**
	 * TODO Text aktualisieren
         * Baut eine Impressums JPanel nach VOrlage einer noch anzufertigen Klasse
	 * und gibt dieses zurueck.
	 */
	private JPanel buildImpressumPanel() {
                impressumPanel = new ImpressumPanel();
                Controller.setImpressumPanel(impressumPanel);
		add(impressumPanel, BorderLayout.SOUTH);

                JPanel k = new JPanel();
		k.setBackground(Color.WHITE);
		k.add(impressumPanel, BorderLayout.NORTH);

		JPanel l = new JPanel();
		l.setBackground(Color.WHITE);
		l.setPreferredSize(new Dimension(200, 30));
		k.add(l, BorderLayout.SOUTH);

                return k;
	}

	/**
         * TODO Text Aktualisieren
	 * Baut das Welcome JPanel nach Vorlage einer noch anzufertigen Klasse und
	 * gibt dieses zurueck.
	 */
	private WelcomePanel buildWelcomePanel() {
                welcomeActionListener = new WelcomeActionListener();
                Controller.setWelcomeActionListener(welcomeActionListener);

		welcomePanel = new WelcomePanel();
                Controller.setWelcomePanel(welcomePanel);

		return welcomePanel;
	}

	public static void main(String args[]) {
                // Wieso schmeisst eclipse da ne unused warning? Oo
		@SuppressWarnings("unused")
		Kampfrichterlehrgang k = new Kampfrichterlehrgang();
	}
}
