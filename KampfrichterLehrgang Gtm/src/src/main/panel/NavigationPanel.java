package src.main.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import src.main.Controller;
import src.main.components.KariButton;
import src.main.components.SucheTextfeld;
import src.main.listener.HomeActionListener;
import src.main.listener.VideoAddActionListener;
import src.main.listener.NavigationButtonMouseListener;
import src.main.listener.SearchActionListener;
import src.main.listener.TestModeButtonActionListener;

public class NavigationPanel extends JPanel {


	private static final long serialVersionUID = -7451396862135969124L;

	// Der RESOURCEPATH sollte noch geaendert werden nach noch zu
	// beschliessender
	// konvention
	//private static final String DTB_LOGO = "/img/Logo/dtb-logo.jpg";
	//http://www.dtb-online.de/portal/verband/leitbild-design/logowelt.html
	private static final String DTB_LOGO = "/img/Logo/DTB_Logo_alt.jpg";
//	private static final String ZURUECK_LOGO = "/img/Logo/zurueck_button.png";
	private static final String ZURUECK_LOGO = "/img/Logo/zurueck_button.png";
	private static final String VORWAERTS_LOGO = "/img/Logo/vor_button.png";
	private static final String HOME_LOGO = "/img/Logo/home_button.png";
	private static final String TESTMODE_LOGO = "/img/Logo/testModebutton.png";
	
	private SucheTextfeld sucheFeld;

        private KariButton backButton;
        private KariButton homeButton;
        private KariButton testModeButton;
        private KariButton fwButton;
        private KariButton videoAddButton;


	public NavigationPanel() {
			    
		setBackground(Color.WHITE);
		
		setLayout(new BorderLayout());
		
		SearchActionListener searchListener = new SearchActionListener();
		NavigationButtonMouseListener mouseListener = new NavigationButtonMouseListener();
		try {
			BufferedImage zurueck = ImageIO.read(getClass().getResource(
					ZURUECK_LOGO));
			backButton = Controller.getBackActionListener().getButton();
			backButton.setIcon(new ImageIcon(zurueck));
                        backButton.setFocusPainted(false);
                        backButton.setBorder(BorderFactory.createEmptyBorder());
			backButton.setOpaque(false);
			backButton.setPreferredSize(new Dimension(60,60));
			backButton.setActionCommand("back");
			backButton.addMouseListener(mouseListener);
            backButton.setVisible(false);
			
			BufferedImage home = ImageIO.read(getClass().getResource(HOME_LOGO));
			homeButton = new KariButton();
			homeButton.setIcon(new ImageIcon(home));
			homeButton.addActionListener(new HomeActionListener());
			homeButton.setPreferredSize(new Dimension(60, 60));
			homeButton.setFocusPainted(false);
			homeButton.setBorder(BorderFactory.createEmptyBorder());
			homeButton.setOpaque(false);
			homeButton.setActionCommand("home");
			homeButton.addMouseListener(mouseListener);
//			
			videoAddButton = new KariButton();
			videoAddButton.setText("videos einfügen");
			Color myRot = Color.decode("#b92d2e");
			videoAddButton.setBackground(myRot);
			videoAddButton.setForeground(Color.black);
			videoAddButton.addActionListener(new VideoAddActionListener());
			videoAddButton.setPreferredSize(new Dimension(60, 60));
			videoAddButton.setFocusPainted(false);
			videoAddButton.setBorder(BorderFactory.createEmptyBorder());
			videoAddButton.setOpaque(false);
			videoAddButton.setActionCommand("videoAdd");
			videoAddButton.addMouseListener(mouseListener);
			videoAddButton.setVisible(true);
			
			BufferedImage testMode = ImageIO.read(getClass().getResource(TESTMODE_LOGO));
			testModeButton = new KariButton();
			testModeButton.addActionListener(new TestModeButtonActionListener());
			testModeButton.setIcon(new ImageIcon(testMode));
			testModeButton.setPreferredSize(new Dimension(60, 60));
			testModeButton.setFocusPainted(false);
			testModeButton.setBorder(BorderFactory.createEmptyBorder());
			testModeButton.setOpaque(false);
			testModeButton.setActionCommand("testMode");
			testModeButton.addMouseListener(mouseListener);
			
			BufferedImage vorwaerts = ImageIO.read(getClass().getResource(
					VORWAERTS_LOGO));
			fwButton = Controller.getForwardActionListener().getButton();
			fwButton.setIcon(new ImageIcon(vorwaerts));
                        fwButton.setFocusPainted(false);
                        fwButton.setBorder(BorderFactory.createEmptyBorder());
			fwButton.setOpaque(false);
			fwButton.setPreferredSize(new Dimension(60,60));
			fwButton.setActionCommand("forward");
			fwButton.addMouseListener(mouseListener);
                        fwButton.setVisible(false);
                     
            JPanel leftButtonPanel = new JPanel();           
            leftButtonPanel.setBackground(Color.WHITE);
            leftButtonPanel.setLayout(new FlowLayout());
            leftButtonPanel.add(backButton);
            leftButtonPanel.add(homeButton);
            
            JPanel rightButtonPanel = new JPanel();
            rightButtonPanel.setBackground(Color.WHITE);
            rightButtonPanel.setLayout(new FlowLayout());
            rightButtonPanel.add(testModeButton);
            rightButtonPanel.add(fwButton);
            rightButtonPanel.add(videoAddButton);

			add(leftButtonPanel, BorderLayout.WEST);
			add(rightButtonPanel, BorderLayout.EAST);

			
		} catch (IOException e1) {
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
			logoLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
			logoLabel.setMaximumSize(new Dimension(142, 41));
			add(logoLabel, BorderLayout.CENTER);
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.print(e);
		}

		JPanel m = new JPanel();
		m.setBackground(Color.WHITE);
		JLabel l = new JLabel();
		m.add(l, BorderLayout.WEST);

		sucheFeld = new SucheTextfeld();
	    sucheFeld.addActionListener(searchListener);
	    sucheFeld.getLupeButton().addActionListener(searchListener);
		m.add(sucheFeld, BorderLayout.CENTER);
		
		add(m, BorderLayout.SOUTH);
		
		setVisible(true);
	}
	
	public SucheTextfeld getSucheFeld(){
	    return sucheFeld;
	}

        public KariButton getFwButton() {
          return fwButton;
        }

        public KariButton getBackButton() {
          return backButton;
        }

	public void enterFullScreen()
	{
		this.setVisible(false);
	}

	public void leaveFullScreen()
	{
		this.setVisible(true);
	}
}
