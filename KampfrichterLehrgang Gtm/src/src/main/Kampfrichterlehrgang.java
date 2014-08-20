package src.main;

import java.awt.*;

import javax.swing.*;

import java.util.*;

public class Kampfrichterlehrgang extends JFrame {

	private static final String NAME = "                                                              "
			+ "                                   "
			+ "      KampfrichterLehrgang Gtm";

	private NavigationPanel navigationPanel;
	private JPanel impressumPanel;
	private WelcomePanel welcomePanel;

	protected String curLF = "javax.swing.plaf.metal.MetalLookAndFeel";

	// Fuer die "Zurueck" Button funktionalitaet ist eine Stackloesung
	// angedacht.
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
		getContentPane().add(buildWelcomePanel(), BorderLayout.CENTER);

		// insert impressumPanel to bottom
		JPanel k = new JPanel();
		k.setBackground(Color.WHITE);
		k.add(buildImpressumPanel(), BorderLayout.NORTH);

		JPanel l = new JPanel();
		l.setBackground(Color.WHITE);
		k.add(l, BorderLayout.SOUTH);

		add(k, BorderLayout.SOUTH);

		setVisible(true);
	}

	/**
	 * Wurzelfunktion zum wechseln des CenterPanels. Beachte changeToVideo(),
	 * changeToWelcome() und changeToResult().
	 */
	public void changeCenterPanel(String changeArg) {
	}

	private void changeToVideo() {
	}

	private void changeToWelcome() {
	}

	private void changeToResult() {
	}

	/**
	 * Baut ein Navigations JPanel. Das Panel enthaelt ein Logo sowie einen
	 * Zurueck Button dessen Funktion mit dem in dieser Klasse definierten Stack
	 * implementiert wird.
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

		impressumPanel = new ImpressumPanel();
		add(impressumPanel, BorderLayout.SOUTH);
		return impressumPanel;
	}

	/**
	 * Baut das Welcome JPanel nach Vorlage einer noch anzufertigen Klasse und
	 * gibt dieses zurueck.
	 */
	private WelcomePanel buildWelcomePanel() {
		welcomePanel = new WelcomePanel();
		return welcomePanel;
	}

	public static void main(String args[]) {
		Kampfrichterlehrgang k = new Kampfrichterlehrgang();
	}
}
