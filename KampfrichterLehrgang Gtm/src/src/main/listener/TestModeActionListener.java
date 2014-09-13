package src.main.listener;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.main.Controller;
import src.main.components.KariButton;
import src.main.panel.TestModePanel;

public class TestModeActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		KariButton button = (KariButton) e.getSource();
		String name = button.getText();
		TestModePanel testPanel = Controller.getTestModePanel();

		switch (command) {
		case "start":
			testPanel.versteckeModusPanel();
			testPanel.starteTest();
			break;
		case "next":
			testPanel.naechstesVideo();
			break;
		case "new":
			System.out.println("NEUER TEST");
			testPanel.neuerTestVorbereiten();
			break;
		default:
			button.setBackground(Color.ORANGE);;
			if (command.equals(name)) {
				testPanel.zeigeRichtigesErgebnis(command);
				testPanel.addTabelleZeile(name, command, true);
			} else{
				testPanel.zeigeFalschesErgebnis(command);
				testPanel.addTabelleZeile(name, command, false);
			}
		}
	}

}
