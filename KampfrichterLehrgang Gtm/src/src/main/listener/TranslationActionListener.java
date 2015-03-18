package src.main.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.main.Controller;

public class TranslationActionListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Controller.getKampfrichterlehrgang().changeToTranslationPanel();
		
	}

}
