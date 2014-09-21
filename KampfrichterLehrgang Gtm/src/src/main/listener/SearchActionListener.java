package src.main.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.main.Controller;
import src.main.components.SucheTextfeld;
/**
 * ActionListener fuer die Suchfunktion
 * @author michael
 *
 */
public class SearchActionListener implements ActionListener
{

    @Override
    public void actionPerformed(ActionEvent e)
    {
        SucheTextfeld sucheTextfeld = Controller.getNavigationPanel().getSucheFeld();
        String suchString = sucheTextfeld.getText();
        Controller.getKampfrichterlehrgang().changeToSearchResult(suchString);

    }

}
