package src.main.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.main.Controller;

public class VideoAddActionListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
        Controller.getKampfrichterlehrgang().changeToVideoAddPanel();
	}

}