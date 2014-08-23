package src.main.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import src.main.Controller;
/**
 * ActionListener fuer die Comboboxen im ResultPanel
 * Uebernehmen das Filtern der Ergebnisse
 * @author michael
 *
 */
public class ComboBoxActionListener implements ActionListener
{

    @Override
    public void actionPerformed(ActionEvent e)
    {
        ResultPanel panel = Controller.getResultpanel();
        JComboBox<String> schwierigkeitsgradCb = panel.getSchwierigkeitsgradCb();
        JComboBox<String> elementgruppeCb = panel.getElementgruppeCb();
        
        String schwierigkeit = (String) schwierigkeitsgradCb.getSelectedItem();
        String elementgruppe = (String) elementgruppeCb.getSelectedItem();
        
        panel.filterVideos(schwierigkeit, elementgruppe);
        }


}
