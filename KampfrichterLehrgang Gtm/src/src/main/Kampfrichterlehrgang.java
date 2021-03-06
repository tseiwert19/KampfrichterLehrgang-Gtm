package src.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import src.main.listener.BackActionListener;
import src.main.listener.ForwardActionListener;
import src.main.listener.WelcomeActionListener;
import src.main.panel.CenterPanel;
import src.main.panel.ImpressumPanel;
import src.main.panel.NavigationPanel;
import src.main.panel.ResultPanel;
import src.main.panel.SearchResultPanel;
import src.main.panel.TestModePanel;
import src.main.panel.VideoInfoPanel;
import src.main.panel.WelcomePanel;
import src.main.videoplayer.Video;
import src.main.videoplayer.VideoParser;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.NativeLibrary;

public class Kampfrichterlehrgang extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final String NAME = "KampfrichterLehrgang Gtm";

	private NavigationPanel navigationPanel;
	private ImpressumPanel impressumPanel;
	private WelcomePanel welcomePanel;
	private ResultPanel resultPanel;
	private SearchResultPanel searchResultPanel;

	private WelcomeActionListener welcomeActionListener;
	private BackActionListener backActionListener;
	private ForwardActionListener forwardActionListener;

	protected String curLF = "javax.swing.plaf.metal.MetalLookAndFeel";

	private Stack<CenterPanel> backStack;
	private Stack<CenterPanel> forwardStack;

	public Kampfrichterlehrgang() {

		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),
				"c:/Programme/VideoLan/VLC");

		setTitle(NAME);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setExtendedState(JFrame.MAXIMIZED_BOTH);

		Controller.setScrollPane(new JScrollPane());
		// insert navigationPanel to top
		getContentPane().add(buildNavigationPanel(), BorderLayout.NORTH);

		// insert welcomePanel to center
		Controller.setCurrentCenterPanel(buildWelcomePanel());
		getContentPane().add(Controller.getCurrentCenterPanel(),
				BorderLayout.CENTER);

		// insert impressumPanel to bottom
		getContentPane().add(buildImpressumPanel(), BorderLayout.SOUTH);

		Controller.setKampfrichterlehrgang(this);
		setVisible(true);
	}

	/**
	 * Wechselt das CenterPanel auf das WelcomePanel (Home)
	 */
	public void changeToWelcome() {
		if (Controller.getWelcomePanel() == null) {
			welcomePanel = new WelcomePanel();
			Controller.setWelcomePanel(welcomePanel);
		} else {
			welcomePanel = Controller.getWelcomePanel();
		}
		changeCenterPanelForward(welcomePanel);
		backStack.clear();
		Controller.getNavigationPanel().getBackButton().setVisible(false);
	}

	/**
	 * Wechselt auf das ein uebergebenes, vorher erzeugtes ResultPanel.
	 */
	public void changeToResult(String search) {
		// Debug
		System.out.println("Start creating ResultPanel: " + search);
		resultPanel = new ResultPanel(search);
		Controller.setResultPanel(resultPanel);
		changeCenterPanelForward(resultPanel);
	}

	/**
	 * Wechselt zu einem SearchResultPanel (Fuer die Suchfunktion)
	 * 
	 * @param search
	 *            Suchwort
	 */
	public void changeToSearchResult(String search) {
		System.out.println("Start creating SearchResultPanel: " + search);
		searchResultPanel = new SearchResultPanel(search);
		Controller.setSearchResultPanel(searchResultPanel);
		changeCenterPanelForward(searchResultPanel);
	}

	/**
	 * Wechselt zu einem VideoInfoPanel
	 */
	public void changeToVideoInfoPanel(int id) {
		VideoParser parser = new VideoParser();
		Video video = parser.mappeEinVideo(id);
		System.out.println(video == null);
		VideoInfoPanel videoInfoPanel = new VideoInfoPanel(video);
		Controller.setVideoInfoPanel(videoInfoPanel);
		changeCenterPanelForward(videoInfoPanel);
		// videoInfoPanel.run();
	}

	/**
	 * Wechselt zum TestMode
	 */
	public void changeToTestModePanel() {
		TestModePanel testMode = new TestModePanel();
		Controller.setTestModePanel(testMode);
		changeCenterPanel(testMode);
	}

	/**
	 * Wechselt das CenterPanel zu dem uebergebenen und meldet das neue beim
	 * Controller an.
	 */
	private void changeCenterPanel(CenterPanel newCenterPanel) {
		// Debug
		System.out.println("Changing center panel.");

		CenterPanel removingPanel = Controller.getCurrentCenterPanel();

		this.getContentPane().remove(removingPanel);
		this.getContentPane().remove(Controller.getScrollPane());
		JScrollPane scrollPane = new JScrollPane(newCenterPanel);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		// Scrollgeschwindigkeit erhoehen
		scrollPane.getVerticalScrollBar().setUnitIncrement(15);
		Controller.setScrollPane(scrollPane);
		this.getContentPane().add(scrollPane, BorderLayout.CENTER);
		Controller.setCurrentCenterPanel(newCenterPanel);
		this.getContentPane().validate();
		this.getContentPane().repaint();
	}

	/**
	 * Wechselt das CenterPanel "rueckwaerts" so dass der Back und ForwardStack
	 * beruecksichtigt werden.
	 */
	public void changeCenterPanelBackward() {
		forwardStack.push(Controller.getCurrentCenterPanel());
		if (!Controller.getNavigationPanel().getFwButton().isVisible()) {
			Controller.getNavigationPanel().getFwButton().setVisible(true);
		}
		changeCenterPanel(backStack.pop());
		if (backStack.empty()) {
			Controller.getNavigationPanel().getBackButton().setVisible(false);
		}
	}

	/**
	 * Wechselt das CenterPanel "vorwaerts" so, dass der Back und ForwardStack
	 * bereucksichtigt werden. Wenn das uebergeben Objekt NULL ist wird ein
	 * objekt aus dem FowardStack uebergeben.
	 */
	public void changeCenterPanelForward(CenterPanel newCenterPanel) {
		backStack.push(Controller.getCurrentCenterPanel());
		if (!Controller.getNavigationPanel().getBackButton().isVisible())
			Controller.getNavigationPanel().getBackButton().setVisible(true);
		if (newCenterPanel == null) {
			changeCenterPanel(forwardStack.pop());
			if (forwardStack.empty())
				Controller.getNavigationPanel().getFwButton().setVisible(false);
		} else {
			changeCenterPanel(newCenterPanel);
			// Wenn das CenterPanel hier gewechselt wird wurde die
			// Reihenfolge zerstoert und der ForwardStack kann
			// geloescht werden
			forwardStack.clear();
			// Verstecke den Vorwaerts Button wenn der Stack leer ist
			Controller.getNavigationPanel().getFwButton().setVisible(false);
		}
	}

	/**
	 * Baut ein Navigations JPanel. Das Panel enthaelt ein Logo sowie einen
	 * Zurueck Button dessen Funktion mit dem in dieser Klasse definierten Stack
	 * implementiert wird.
	 */
	private NavigationPanel buildNavigationPanel() {
		backStack = new Stack<CenterPanel>();
		backActionListener = new BackActionListener();
		Controller.setBackActionListener(backActionListener);
		forwardStack = new Stack<CenterPanel>();
		forwardActionListener = new ForwardActionListener();
		Controller.setForwardActionListener(forwardActionListener);

		navigationPanel = new NavigationPanel();
		Controller.setNavigationPanel(navigationPanel);
		return navigationPanel;
	}

	/**
	 * Baut eine Impressums JPanel nach VOrlage einer noch anzufertigen Klasse
	 * und gibt dieses zurueck.
	 */
	private JPanel buildImpressumPanel() {
		GridLayout gridLayout = new GridLayout(1, 3);

		JPanel k = new JPanel();
		k.setBackground(Color.WHITE);

		JPanel m = new JPanel();
		m.setBackground(Color.WHITE);
		k.add(m);

		impressumPanel = new ImpressumPanel();
		Controller.setImpressumPanel(impressumPanel);
		k.add(impressumPanel);
		return k;
	}

	/**
	 * TODO Text Aktualisieren Baut das Welcome JPanel nach Vorlage einer noch
	 * anzufertigen Klasse und gibt dieses zurueck.
	 */
	private WelcomePanel buildWelcomePanel() {
		welcomeActionListener = new WelcomeActionListener();
		Controller.setWelcomeActionListener(welcomeActionListener);

		welcomePanel = new WelcomePanel();
		Controller.setWelcomePanel(welcomePanel);

		return welcomePanel;
	}

	public Stack<CenterPanel> getBackStack() {
		return backStack;
	}

	public Stack<CenterPanel> getForwardStack() {
		return forwardStack;
	}

	public static void main(String args[]) {
		// Wieso schmeisst eclipse da ne unused warning? Oo
		@SuppressWarnings("unused")
		Kampfrichterlehrgang k = new Kampfrichterlehrgang();
	}

}
