package src.main.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.main.Controller;
import src.main.components.KariButton;
import src.main.panel.TestPanel;

public class TestActionListener implements ActionListener
{

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String command = e.getActionCommand();
        KariButton button = (KariButton) e.getSource();
        String name = button.getText();
        TestPanel testPanel = Controller.getTestPanel();
        
        switch (name) {
            case    "Start":
                    testPanel.versteckeModusPanel();
                    testPanel.starteTest();
                break;
            case    "Nächste Aufgabe":
                    testPanel.naechstesVideo();
                break;
            default:
                if(command.equals(name)){
                    testPanel.zeigeRichtigesErgebnis(command);
                }else
                    testPanel.zeigeFalschesErgebnis(command);
                break;
        
        }
        
    }

}
