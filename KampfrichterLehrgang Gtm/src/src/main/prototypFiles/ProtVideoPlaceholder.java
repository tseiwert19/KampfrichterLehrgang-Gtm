package src.main.prototypFiles;

import javax.swing.JButton;
import java.awt.Dimension;

public class ProtVideoPlaceholder extends JButton {

  ProtVideoPlaceholder(String title) {
    super(title);

    setPreferredSize(new Dimension(200,200));
  }
}

