package src.main.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.main.Controller;
import src.main.components.KariButton;
import src.main.panel.CenterPanel;

/**
 * Faengt und kontrolliert die ActionEvents vom Vorwaerts Button im 
 * NavigationsPanel.
 */
public class ForwardActionListener implements ActionListener {

  /**
   * Gibt einenButton zurueck der bereits den Actionlistener implementiert hat.
   */
  public KariButton getButton() {
    KariButton newButton = new KariButton();
    newButton.addActionListener(this);
    return newButton;
  }

  /**
   * Holt sich das oberste (falls vorhanden) Panel im ForwardStack der Software
   * und wechselt das CenterPanel.
   */
  public void actionPerformed (ActionEvent e) {
    if (!Controller.getKampfrichterlehrgang().getForwardStack().empty()) {
      Controller.getKampfrichterlehrgang().changeCenterPanelForward(null);
    } else {
      //Debug
      System.out.println("Error Forward Button: Empty ForwardStack.");
    }
  }
}
