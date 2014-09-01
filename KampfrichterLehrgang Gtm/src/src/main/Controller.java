package src.main;

import src.main.components.KariButton;
import src.main.components.SucheTextfeld;
import src.main.listener.WelcomeActionListener;
import src.main.panel.*;
import src.main.videoplayer.*;
import src.main.pdfviewer.*;

//TODO Alles auf NULL ueberpruefen
/**
 * Dieser Controller beinhaltet alle Laufenden Objekte der 
 * Kampfrichterlehrgangssoftware im Betrieb. Jedes Objekt muss sich bei dem
 * Controller anmelden damit darauf zugegriffen werden kann. Dient der 
 * vereinfachung der Kommunikation unter den Objekten.
 */
public class Controller
{

    private static Kampfrichterlehrgang main;

    private static DatenbankController db;
    private static ImpressumPanel imppan;
    private static ResultPanel respan;
    private static WelcomePanel welcpan;
    private static NavigationPanel navpan;
    private static SearchResultPanel searchResultPanel;

    private static CenterPanel currentCenterPanel;

    private static WelcomeActionListener welcAction;

    private static MediaPlayer player;
    private static Video video;
    private static VideoParser vidparser;

    private static PDFViewer pdfviewer;

    private static KariButton kari;
    private static SucheTextfeld textfeld;

    public static void setKampfrichterlehrgang(Kampfrichterlehrgang k)
    {
        main = k;
    }

    public static Kampfrichterlehrgang getKampfrichterlehrgang()
    {
        return main;
    }

    public static void setDatenbankController(DatenbankController d)
    {
        db = d;
    }

    public static DatenbankController getDatenbankcontroller()
    {
        return db;
    }

    public static void setImpressumPanel(ImpressumPanel i)
    {
        imppan = i;
    }

    public static ImpressumPanel getImpressumPanel()
    {
        return imppan;
    }

    public static void setResultPanel(ResultPanel r)
    {
        respan = r;
    }

    public static ResultPanel getResultpanel()
    {
        return respan;
    }

    public static void setWelcomePanel(WelcomePanel w)
    {
        welcpan = w;
    }

    public static WelcomePanel getWelcomePanel()
    {
        return welcpan;
    }

    public static void setNavigationPanel(NavigationPanel n)
    {
        navpan = n;
    }

    public static NavigationPanel getNavigationPanel()
    {
        return navpan;
    }

    public static void setMediaPlayer(MediaPlayer m)
    {
        player = m;
    }

    public static MediaPlayer getMediaPlayer()
    {
        return player;
    }

    public static void setPDFViewer(PDFViewer p)
    {
        pdfviewer = p;
    }

    public static PDFViewer getPDFViewer()
    {
        return pdfviewer;
    }

    public static void setWelcomeActionListener(WelcomeActionListener wal)
    {
        welcAction = wal;
    }

    public static WelcomeActionListener getWelcomeActionListener()
    {
        return welcAction;
    }

    public static void setCurrentCenterPanel(CenterPanel newCurrentCenterPanel)
    {
        currentCenterPanel = newCurrentCenterPanel;
    }

    public static CenterPanel getCurrentCenterPanel()
    {
        return currentCenterPanel;
    }

    public static void setSearchResultPanel(SearchResultPanel s)
    {
        searchResultPanel = s;
    }

    public static SearchResultPanel getSearchResultPanel()
    {
        return searchResultPanel;
    }
}
