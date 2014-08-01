package src.main;

import javax.swing.*;
import java.awt.event.*;

/**
 * Dies ist nur ein prototyp
 * @author Thomas
 *
 */
public class Prototyp implements WindowListener {

  private static final int VIDFRAMECOUNT = 6;
  private ProtVideoFrame[] videoFrameArr = new ProtVideoFrame[VIDFRAMECOUNT];
  private int videoFrameIDIndex = 0;
  private ProtButtonGridFrame buttonFrame;   

  public Prototyp () {
    for (int i = 0; i < VIDFRAMECOUNT; i++) {
      videoFrameArr[i] = new ProtVideoFrame(videoFrameIDIndex++, Integer.toString(videoFrameIDIndex));
      videoFrameArr[i].addWindowListener(this);
      System.out.println("New VidFrame in Arr ID: " + (videoFrameIDIndex - 1));
    }
    buttonFrame = new ProtButtonGridFrame(this);
  }

  public ProtVideoFrame getVideoFrameByID (int frameID) {
    System.out.print("Looking for Frame ID: " + frameID + "...");
    for (int i = 0; i < videoFrameArr.length; i++) {
      if (frameID == videoFrameArr[i].getFrameID()) {
        System.out.println("Found it");
        return videoFrameArr[i];
      }
    }
    //Exception unso
    System.out.println("Not Found. Return Null.");
    return null;
  }

  public void windowDeactivated(WindowEvent e) {}
  public void windowActivated(WindowEvent e) {}
  public void windowDeiconified(WindowEvent e) {}
  public void windowIconified(WindowEvent e) {}
  public void windowClosed(WindowEvent e) {}
  public void windowOpened(WindowEvent e) {}

  public void windowClosing(WindowEvent e) {
    ((ProtVideoFrame)e.getSource()).setVisible(false);
    buttonFrame.setVisible(true);
  }

  public static void main (String[] args) {
    Prototyp p = new Prototyp();
  }
}
