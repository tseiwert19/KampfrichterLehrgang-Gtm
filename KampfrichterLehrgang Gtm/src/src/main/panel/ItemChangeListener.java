package src.main.panel;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ItemChangeListener implements ItemListener {
    
    public void itemStateChanged(ItemEvent evt) {
      RoundCorneredComboBox cb = (RoundCorneredComboBox) evt.getSource();
      String newText = (String) evt.getItem();
      cb.refreshItemLabel(newText);
    }
}
