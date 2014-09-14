package src.main.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.main.Controller;
/**
 * ActionListener, der zum TestMode wechselt
 * Für den TestMode Button im KampfrichterLehrgang
 * @author Michael
 *
 */
public class TestModeButtonActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Controller.getKampfrichterlehrgang().changeToTestModePanel();
		
	}

}
