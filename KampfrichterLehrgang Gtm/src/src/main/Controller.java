package src.main;

import src.main.panel.*;
import src.main.videoplayer.*;
import src.main.pdfviewer.*;

// Bei getern Null ueberpruefung machen
public class Controller {

  private static Kampfrichterlehrgang main;

  private static DatenbankController db;
  private static ImpressumPanel imppan;
  private static ResultPanel respan;
  private static WelcomePanel welcpan;
  private static NavigationPanel navpan;

  private static MediaPlayer player;
  private static Video video;
  private static VideoParser vidparser;

  private static PDFViewer pdfviewer;

  private static KariButton kari;
  private static SucheTextfeld textfeld;

  public static void setKampfrichterlehrgang(Kampfrichterlehrgang k) {
    main = k;
    System.out.println("wootwoot");
  }

  public static Kampfrichterlehrgang getKampfrichterlehrgang() {
    return main;
  }

  public static void setDatenbankController(DatenbankController d) {
    db = d;
  }

  public static DatenbankController getDatenbankcontroller() {
    return db;
  }

  public static void setImpressumPanel (ImpressumPanel i) {
    imppan = i;
  }

  public static ImpressumPanel getImpressumPanel () {
    return imppan;
  }

  public static void setResultPanel (ResultPanel r) {
    respan = r;
  }

  public static ResultPanel getResultpanel () {
    return respan;
  }

  public static void setWelcomePanel (WelcomePanel w) {
    welcpan = w;
  }

  public static WelcomePanel getWelcomePanel () {
    return welcpan;
  }

  public static void setNavigationPanel (NavigationPanel n) {
    navpan = n;
  }

  public static NavigationPanel getNavigationPanel () {
    return navpan;
  }

  public static void setMediaPlayer (MediaPlayer m) {
    player = m;
  }

  public static MediaPlayer getMediaPlayer () {
    return player;
  }

  public static void setPDFViewer (PDFViewer p) {
    pdfviewer = p;
  }

  public static PDFViewer getPDFViewer () {
    return pdfviewer;
  }
}
