package src.main;

import javax.swing.JButton;
import java.awt.Dimension;

public class ProtGridButton extends JButton {

  ProtGridButton(String title) {
    super(title);

    setPreferredSize(new Dimension(200,60));
  }
}
