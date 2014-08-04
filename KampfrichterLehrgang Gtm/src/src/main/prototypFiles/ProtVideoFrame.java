package src.main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import java.awt.Container;
import java.awt.BorderLayout;

public class ProtVideoFrame extends JFrame {
  private Container contentPane;
  private JLabel geraet;
  private JPanel videoGrid;

  private ProtVideoPlaceholder video1;
  private ProtVideoPlaceholder video2;
  private ProtVideoPlaceholder video3;
  private ProtVideoPlaceholder video4;
  private ProtVideoPlaceholder video5;

  private int frameID;

  public ProtVideoFrame (String newFrameLabel) {

    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    contentPane = getContentPane();
    
    geraet = new JLabel(newFrameLabel);

    videoGrid = new JPanel(new ProtVideoGridLayout());

    video1 = new ProtVideoPlaceholder("video1");
    video2 = new ProtVideoPlaceholder("video2");
    video3 = new ProtVideoPlaceholder("video3");
    video4 = new ProtVideoPlaceholder("video4");
    video5 = new ProtVideoPlaceholder("video5");

    videoGrid.add(video1);
    videoGrid.add(video2);
    videoGrid.add(video3);
    videoGrid.add(video4);
    videoGrid.add(video5);
    
    if (geraet != null)
      contentPane.add(geraet, BorderLayout.NORTH);
    contentPane.add(videoGrid, BorderLayout.CENTER);

    pack();
    //setVisible(true);
  }

  public ProtVideoFrame(int newFrameID, String newFrameLabel) {
    this(newFrameLabel);
    frameID = newFrameID;
  }

  public int getFrameID() {
    return this.frameID;
  }
  public ProtVideoFrame getVideoFrame() {
    return this;
  }
}
