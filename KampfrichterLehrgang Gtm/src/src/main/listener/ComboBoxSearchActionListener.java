package src.main.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.main.Controller;
import src.main.components.RoundCorneredComboBox;
import src.main.panel.SearchResultPanel;
/**
 * ActionListener fuer RoundCorneredComboBox im SearchResultPanel
 * @author michael
 *
 */
public class ComboBoxSearchActionListener implements ActionListener
{

    @Override
    public void actionPerformed(ActionEvent e)
    {   
        SearchResultPanel panel = Controller.getSearchResultPanel();
        String name = panel.getName();
        RoundCorneredComboBox geraeteCb = panel.getGeraeteCb();
        RoundCorneredComboBox elementgruppeCb = panel.getElementgruppeCb();
        
        String geraet = (String) geraeteCb.getSelectedItem();
        String elementgruppe = (String) elementgruppeCb.getSelectedItem();
        
        panel.filterVideos(name, geraet, elementgruppe);
        
    }

}
