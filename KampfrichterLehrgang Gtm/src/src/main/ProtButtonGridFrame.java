package src.main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.JTextField;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.SwingUtilities;

public class ProtButtonGridFrame extends JFrame {

  private static final String GERAET01 = "Boden";
  private static final String GERAET02 = "Pauschenpferd";
  private static final String GERAET03 = "Ringe";
  private static final String GERAET04 = "Sprung";
  private static final String GERAET05 = "Barren";
  private static final String GERAET06 = "Reck";

  private Container contentPane;
  private JPanel textField;
  private JTextField searchField;
  private JPanel buttonGrid;

  private Prototyp controller;

  protected ProtButtonGridFrame(Prototyp controllingObject) {
    controller = controllingObject;

    setDefaultCloseOperation(EXIT_ON_CLOSE);

    contentPane = getContentPane();

    textField = new JPanel(new FlowLayout());
    searchField = new JTextField("Suchen...", 30);
    searchField.addActionListener(new ActionButtonGeraet());
    textField.add(searchField);

    buttonGrid = new JPanel(new ProtGridLayout());

    // Folgender Teil ist fuer Jeden Button zu erstellen ---
    ProtGridButton buttonGeraet01 = new ProtGridButton(GERAET01);
    buttonGeraet01.addActionListener(new ActionButtonGeraet());
    buttonGrid.add(buttonGeraet01);
    // bis hier --------------------------------------------
    
    // Es muessen (glaube ich) jeweils unterschiedliche ActionListener
    // fuer jeden Button implementiert werden. Vielleicht gehts aber auch
    // mit einer Abfrage welcher Button die Action ausgeloest hat.
    // UPDATE:
    // mit getSource() wird das object zurueck gegeben welches das actionEvent
    // uasgeloest hat. Muss zurueck gecastet werden
    // UPDATE02:
    // mit getActionCommand() erhaelt man die BUttonbezeichnung (bei textfeldern)
    // den inhalt) ausser er wurde mit setActionCommand() veraendert
    
    ProtGridButton buttonGeraet02 = new ProtGridButton(GERAET02);
    buttonGeraet02.addActionListener(new ActionButtonGeraet());
    buttonGrid.add(buttonGeraet02);

    ProtGridButton buttonGeraet03 = new ProtGridButton(GERAET03);
    buttonGeraet03.addActionListener(new ActionButtonGeraet());
    buttonGrid.add(buttonGeraet03);

    buttonGrid.add(new ProtGridButton(GERAET04));
    buttonGrid.add(new ProtGridButton(GERAET05));
    buttonGrid.add(new ProtGridButton(GERAET06));

    contentPane.add(textField, BorderLayout.NORTH);
    contentPane.add(buttonGrid, BorderLayout.CENTER);
    pack();
    setVisible(true);
  }

  public ProtButtonGridFrame getButtonGridFrame() {
    return this;
  }

  public Prototyp getController() {
    return controller;
  }

  private class ActionButtonGeraet implements ActionListener {
    public void actionPerformed (ActionEvent e) {
      String button = e.getActionCommand();
      ProtGridButton causeButton = (ProtGridButton) e.getSource();
      ProtButtonGridFrame causeFrame = (ProtButtonGridFrame) SwingUtilities.getRoot(causeButton);
      Prototyp controller = causeFrame.getController();
      if (button.equals(GERAET01)) {
          System.out.println(GERAET01);
          causeFrame.setVisible(false);
          controller.getVideoFrameByID(0).setVisible(true);
      } else if (button.equals(GERAET02)) {
         System.out.println(GERAET02);
          causeFrame.setVisible(false);
          controller.getVideoFrameByID(1).setVisible(true);
      } else if (button.equals(GERAET03)) {
        System.out.println(GERAET03);
          causeFrame.setVisible(false);
          controller.getVideoFrameByID(2).setVisible(true);
      } else
        System.out.println(button);

      // Hier kommt Logik fuer die Darstellung des naechsten Frames
    }
  }
}
