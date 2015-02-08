package src.main.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;

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
			"Alle Schwierigkeitsgrade anzeigen", "A", "B", "C", "D", "E", "F" };
	private static final String[] ELEMENTGRUPPEN = {
			"Alle Elementgruppen anzeigen", "I", "II", "III", "IV", "V" };

	private static String ausgewaehltesVideo = "";

	RoundCorneredComboBox schwierigkeitsgradCb = null;
	RoundCorneredComboBox elementgruppeCb = null;

	public VideoAddPanel() {
		Dimension maximumSize = new Dimension(700, 100);
		setMaximumSize(maximumSize);
		GridLayout gridlayout = new GridLayout(0, 3, 20, 20);
		setBorder(new EmptyBorder(20, 20, 20, 20));
		setLayout(gridlayout);
		setBackground(Color.WHITE);
		JLabel ueberschriftLabel = new JLabel("<html><font size='8'><b><i>"
				+ UEBERSCHRIFT + "</b></i></font>");
		ueberschriftLabel.setVerticalAlignment(JLabel.CENTER);
		ueberschriftLabel.setHorizontalAlignment(JLabel.CENTER);
		add(new JLabel(""));
		add(ueberschriftLabel);
		add(new JLabel(""));

		JLabel videoAddLabel = new JLabel("Video auswählen");
		final JLabel videoAusgewaehlt = new JLabel("ausgewähltes Video: "
				+ ausgewaehltesVideo);
		KariButton button = new KariButton("Video auswählen");
		button.setBackground(myRot);
		button.setForeground(Color.WHITE);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					ausgewaehltesVideo = selectedFile.getName();
					videoAusgewaehlt.setText("ausgewähltes Video: "
							+ ausgewaehltesVideo);
				}
			}
		});

		JLabel videoName = new JLabel("Videoname angeben");
		JLabel l1 = new JLabel();
		final JTextField videonameEingabe = new JTextField();

		createComboBoxes();
		
		JLabel elementgruppe = new JLabel("Elementgruppe angeben");
		JLabel l2 = new JLabel();
		
		JLabel schwierigkeitsgrad = new JLabel("Schwierigkeitsgrad angeben");
		JLabel l3 = new JLabel();

		add(videoAddLabel);
		add(videoAusgewaehlt);
		add(button);

		add(videoName);
		add(l1);
		add(videonameEingabe);

		add(elementgruppe);
		add(l2);
		add(elementgruppeCb);

		add(schwierigkeitsgrad);
		add(l3);
		add(schwierigkeitsgradCb);

		add(new JLabel());
		final JLabel fehler = new JLabel();
		add(fehler);
		KariButton speichernButton = new KariButton("eingaben speichern");
		speichernButton.setBackground(myRot);
		speichernButton.setForeground(Color.WHITE);
		speichernButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (ausgewaehltesVideo.isEmpty() || videonameEingabe.getText().isEmpty() || elementgruppeCb.getSelectedItem() == null || schwierigkeitsgradCb.getSelectedItem() == null) {
					
					fehler.setText("mindestens eine eingabe nicht richtig");
					return;
				}
				fehler.setText(ausgewaehltesVideo + "  "
						+ videonameEingabe.getText() + "  "
						+ elementgruppeCb.getSelectedItem() + "  "
						+ schwierigkeitsgradCb.getSelectedItem());
			}
		});
		add(speichernButton);
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
