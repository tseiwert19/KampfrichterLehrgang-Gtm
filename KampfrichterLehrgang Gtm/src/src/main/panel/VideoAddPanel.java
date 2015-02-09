package src.main.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFileChooser;
import javax.swing.border.EmptyBorder;

import src.main.components.KariButton;
import src.main.components.RoundCorneredComboBox;
import src.main.listener.ComboBoxActionListener;
import src.main.listener.ItemChangeListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VideoAddPanel extends CenterPanel {

	private static final long serialVersionUID = -9115669597556271814L;
	private static final String UEBERSCHRIFT = "Video hinzufügen";
	private static final Color myRot = Color.decode("#b92d2e");

	private static final String[] SCHWIERIGKEITSGRADE = {
			"Schwierigkeitsgrad auswählen", "A", "B", "C", "D", "E", "F" };
	private static final String[] ELEMENTGRUPPEN = {
			"Elementgruppen auswählen", "I", "II", "III", "IV", "V" };

	private static String ausgewaehltesVideo = "";

	RoundCorneredComboBox schwierigkeitsgradCb = null;
	RoundCorneredComboBox elementgruppeCb = null;

	public VideoAddPanel() {
		Dimension maximumSize = new Dimension(700, 100);
		setMaximumSize(maximumSize);
		BorderLayout borderLayout = new BorderLayout();
		setBorder(new EmptyBorder(20, 20, 20, 20));
		setLayout(borderLayout);
		setBackground(Color.WHITE);
		JLabel ueberschriftLabel = new JLabel("<html><font size='8'><b><i>"
				+ UEBERSCHRIFT + "</b></i></font>");
		ueberschriftLabel.setVerticalAlignment(JLabel.CENTER);
		ueberschriftLabel.setHorizontalAlignment(JLabel.CENTER);
		JPanel northPanel = new JPanel();
		northPanel.add(ueberschriftLabel);
		northPanel.setPreferredSize(new Dimension(200, 200));
		northPanel.setBackground(Color.WHITE);
		
		add(northPanel, BorderLayout.NORTH);


		final JLabel videoAusgewaehlt = new JLabel("Ausgewähltes Video: "
				+ ausgewaehltesVideo);
		KariButton button = new KariButton("Neues Video auswählen");
		button.setBackground(myRot);
		button.setForeground(Color.WHITE);
	
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					ausgewaehltesVideo = selectedFile.getName();
					videoAusgewaehlt.setText("Ausgewähltes Video: "
							+ ausgewaehltesVideo);
				}
			}
		});
		createComboBoxes();
		final JTextField videonameEingabe = new JTextField("Videoname eingeben...", 50);
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.WHITE);

		JPanel compPanel = new JPanel();

		compPanel.setLayout(new GridLayout(0, 1, 60, 20));
		compPanel.add(button);
		compPanel.add(videoAusgewaehlt);
		compPanel.add(videonameEingabe);
		compPanel.setBackground(Color.WHITE);
		compPanel.add(elementgruppeCb);
		compPanel.add(schwierigkeitsgradCb);

		centerPanel.add(compPanel);


		add(centerPanel, BorderLayout.CENTER);

		final JLabel fehler = new JLabel();
		JPanel southPanel = new JPanel();
		southPanel.setBackground(Color.WHITE);
		southPanel.setLayout(new GridLayout(0, 3, 0, 0));
		southPanel.add(new JLabel());
		southPanel.add(fehler);
		southPanel.add(new JLabel());
		
		add(southPanel, BorderLayout.SOUTH);
		KariButton speichernButton = new KariButton("Video speichern");
		speichernButton.setBackground(myRot);
		speichernButton.setForeground(Color.WHITE);
		speichernButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (ausgewaehltesVideo.isEmpty() || videonameEingabe.getText().isEmpty() || elementgruppeCb.getSelectedItem() == null || schwierigkeitsgradCb.getSelectedItem() == null) {
					
					fehler.setText("Mindestens eine Eingabe nicht richtig!");
					return;
				}
				fehler.setText(ausgewaehltesVideo + "  "
						+ videonameEingabe.getText() + "  "
						+ elementgruppeCb.getSelectedItem() + "  "
						+ schwierigkeitsgradCb.getSelectedItem());
			}
		});
		compPanel.add(speichernButton);
	}
	
	 /**
     * Erstellt beide ComboBoxen
     */
    private void createComboBoxes()
    {
     schwierigkeitsgradCb = new RoundCorneredComboBox(SCHWIERIGKEITSGRADE);
    elementgruppeCb = new RoundCorneredComboBox(ELEMENTGRUPPEN);

        schwierigkeitsgradCb.setName("Schwierigkeitsgrad");
        elementgruppeCb.setName("Elementgruppe");

        ComboBoxActionListener actionListener = new ComboBoxActionListener();
        ItemChangeListener itemListener = new ItemChangeListener();

        schwierigkeitsgradCb.addItemListener(itemListener);
        elementgruppeCb.addItemListener(itemListener);
    }
}
