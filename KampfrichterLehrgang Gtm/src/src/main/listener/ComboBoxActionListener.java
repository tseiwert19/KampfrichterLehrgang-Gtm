package src.main.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.main.Controller;
import src.main.components.RoundCorneredComboBox;
import src.main.panel.ResultPanel;
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
        RoundCorneredComboBox schwierigkeitsgradCb = panel.getSchwierigkeitsgradCb();
        RoundCorneredComboBox elementgruppeCb = panel.getElementgruppeCb();
        
        String schwierigkeit = (String) schwierigkeitsgradCb.getSelectedItem();
        String elementgruppe = (String) elementgruppeCb.getSelectedItem();
        
        panel.filterVideos(schwierigkeit, elementgruppe);
        }


}
