package src.main.listener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import src.main.components.RoundCorneredComboBox;

public class ItemChangeListener implements ItemListener {
    
    public void itemStateChanged(ItemEvent evt) {
      RoundCorneredComboBox cb = (RoundCorneredComboBox) evt.getSource();
      String newText = (String) evt.getItem();
      cb.refreshItemLabel(newText);
    }
}
