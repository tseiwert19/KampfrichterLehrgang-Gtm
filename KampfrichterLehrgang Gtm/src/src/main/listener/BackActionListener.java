package src.main.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.main.Controller;
import src.main.components.KariButton;
import src.main.panel.CenterPanel;

/**
 * Faengt und kontrolliert die ActionEvents vom Zurueck Button im 
 * NavigationsPanel.
 */
public class BackActionListener implements ActionListener {

  /**
   * Gibt einenButton zurueck der bereits den Actionlistener implementiert hat.
   */
  public KariButton getButton() {
    KariButton newButton = new KariButton();
    newButton.addActionListener(this);
    return newButton;
  }

  /**
   * Holt sich das oberste (falls vorhanden) Panel im BackStack der Software
   * und wechselt das CenterPanel.
   */
  public void actionPerformed (ActionEvent e) {
    if (!Controller.getKampfrichterlehrgang().getBackStack().empty()) {
      Controller.getKampfrichterlehrgang().changeCenterPanelBackward();
    } else {
      //Debug
      System.out.println("Error Back Button: Empty BackStack.");
    }
  }
}
