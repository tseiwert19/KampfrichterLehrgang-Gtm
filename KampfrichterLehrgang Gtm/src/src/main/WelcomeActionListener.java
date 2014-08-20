package src.main;

import src.main.panel.*;

import java.awt.event.*;

/**
 * Dieser Actionlistener ist speziel fuer die Buttons im WelcomePanel
 * geschrieben. Er besitzt ausserdem eine Methode ueber die ein 
 * KariButton angefordert werden kann der gleich den ACtionListener
 * beinhaltet.
 */
public class WelcomeActionListener implements ActionListener {

  /**
   * Gibt einenButton zurueck der bereits den Actionlistener implementiert hat.
   */
  public KariButton getButton() {
    KariButton newButton = new KariButton();
    newButton.addActionListener(this);
    return newButton;
  }

  /**
   * Entscheidet je nach gepresstem Button im WelcomePanel welches ResultPanel
   * von der Datenbank abgefragt und erzeugt werden soll.
   */
  public void actionPerformed (ActionEvent e) {
    String command = e.getActionCommand();
    // System.out ist fuer DebugZwecke
    System.out.println(command);

    switch (command) {
      case "Barren":
        System.out.println("Execute ButtonAction for: " + command);
        //do Something
        Controller.getKampfrichterlehrgang().changeToResult(command);
        break;
      
      case "Boden":
        System.out.println("Execute ButtonAction for: " + command);
        //do Something
        Controller.getKampfrichterlehrgang().changeToResult(command);
        break;

      case "Pauschenpferd":
        System.out.println("Execute ButtonAction for: " + command);
        //do something
        Controller.getKampfrichterlehrgang().changeToResult(command);
        break;

      case "Reck":
        System.out.println("Execute ButtonAction for: " + command);
        //do something
        Controller.getKampfrichterlehrgang().changeToResult(command);
        break;

      case "Ringe":
        System.out.println("Execute ButtonAction for: " + command);
        //do something
        Controller.getKampfrichterlehrgang().changeToResult(command);
        break;

      case "Sprung":
        System.out.println("Execute ButtonAction for: " + command);
        //do something
        Controller.getKampfrichterlehrgang().changeToResult(command);
        break;

      default:
        //TODO Exception schmeissen und den Controllflow zurueck zum Welcome
        //Panel leiten
        System.out.println("Diesen Button aus dem Welcome Panel kenne ich noch nicht.");
        break;
    }
  }
}
