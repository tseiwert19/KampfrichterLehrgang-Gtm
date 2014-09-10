package src.main.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import src.main.Controller;
import src.main.components.KariButton;
import src.main.listener.TestModeActionListener;
import src.main.videoplayer.MediaPlayer;
import src.main.videoplayer.Video;
import src.main.videoplayer.VideoParser;

public class TestModePanel extends JPanel
{
    private static final long serialVersionUID = -3560834471579393469L;
    private static final Color MY_RED = Color.decode("#b92d2e");
    private static final String RICHTIG = "/img/TestModus/richtig.png";
    private static final String FALSCH = "/img/TestModus/falsch.png";
    private static final String[] SCHWIERIGKEITEN =  {"A", "B", "C", "D", "E", "F"};
    private static final String[] ELEMENTGRUPPEN = {"I", "II", "III", "IV", "V"};
    private MediaPlayer mediaPlayer;
    private ArrayList<Video> videos;
    private JRadioButton elementgruppeRb;
    private JRadioButton schwierigkeitsgradRb;
    private JPanel mediaPanel;
    private int aktuellesVideo;
    private TestModeActionListener testActionListener;
    private JPanel modusPanel;
    private JPanel ergebnisPanel;
    private ImageIcon richtigImg;
    private ImageIcon falschImg;
    private JPanel antwortPanel;
    private int richtigeAntworten;
    private ArrayList<KariButton> antwortButtons;
    KariButton nextButton;

    public TestModePanel()
    {
        setBackground(Color.WHITE);
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxLayout);
        createAllComponents();

    }
    
    private void createAllComponents(){
        testActionListener = new TestModeActionListener();
        try
        {
            richtigImg = new ImageIcon(ImageIO.read(getClass().getResource(RICHTIG)));
            falschImg = new ImageIcon(ImageIO.read(getClass().getResource(FALSCH)));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.err.println("Fehler beim Einlesen der Icons!");
        }
        createModusPanel();
        createMediaPlayer("");
        mediaPanel.setVisible(false);
        antwortPanel = new JPanel();
        antwortPanel.setVisible(false);
        add(antwortPanel);
        createErgebnisPanel();
        createNextButton();
        nextButton.setVisible(false);
    }

    private void createErgebnisPanel()
    {
        ergebnisPanel = new JPanel();
        // ergebnisPanel.removeAll();
        ergebnisPanel.setBackground(Color.WHITE);
        ergebnisPanel.setLayout(new FlowLayout());
        JLabel ergebnis = new JLabel("Ihre Antworten: ");
        ergebnisPanel.add(ergebnis);
        add(ergebnisPanel);

    }
    
    
    private void createNextButton(){
        nextButton = new KariButton("Nächste Aufgabe");
        nextButton.addActionListener(testActionListener);
        nextButton.setBackground(MY_RED);
        nextButton.setForeground(Color.WHITE);
        nextButton.setActionCommand("next");
        nextButton.setAlignmentX(CENTER_ALIGNMENT);
        add(nextButton);
    }
    private void resetErgebnisPanel(){
        ergebnisPanel.removeAll();
        JLabel ergebnis = new JLabel("Ihre Antworten: ");
        ergebnisPanel.add(ergebnis);
    }

    private void createMediaPlayer(String videoPfad)
    {
        mediaPanel = new JPanel();
        mediaPanel.setLayout(new BorderLayout());
        mediaPanel.setBackground(Color.WHITE);
       // mediaPlayer = new MediaPlayer(videoPfad);
       // mediaPanel.add(mediaPlayer, BorderLayout.CENTER);
        add(mediaPanel);
    }

    private void createModusPanel()
    {
        modusPanel = new JPanel();
        BorderLayout borderLayout = new BorderLayout();
        modusPanel.setLayout(new BoxLayout(modusPanel, BoxLayout.Y_AXIS));
        modusPanel.setBackground(Color.WHITE);

        elementgruppeRb = new JRadioButton("Elementgruppen abfragen");
        elementgruppeRb.setSelected(true);
        elementgruppeRb.setBackground(Color.WHITE);
        schwierigkeitsgradRb = new JRadioButton("Schwierigkeitsgrad abfragen");
        schwierigkeitsgradRb.setBackground(Color.WHITE);
        ButtonGroup radioGroup = new ButtonGroup();

        radioGroup.add(elementgruppeRb);
        radioGroup.add(schwierigkeitsgradRb);

        JLabel label = new JLabel("Wählen Sie den Testmodus:");
        label.setAlignmentX(CENTER_ALIGNMENT);
        modusPanel.add(label);
        JPanel rbPanel = new JPanel();
        rbPanel.setBackground(Color.WHITE);
        rbPanel.add(elementgruppeRb);
        rbPanel.add(schwierigkeitsgradRb);
        rbPanel.setAlignmentX(CENTER_ALIGNMENT);
        rbPanel.setMaximumSize(new Dimension(700, 40));

        modusPanel.add(rbPanel);

        KariButton startButton = new KariButton("Start");
        startButton.setBackground(MY_RED);
        startButton.setForeground(Color.WHITE);
        startButton.addActionListener(testActionListener);
        startButton.setAlignmentX(CENTER_ALIGNMENT);
        modusPanel.add(startButton);

        add(modusPanel);

    }

    public void starteTest()
    {
        aktuellesVideo = 0;
        richtigeAntworten = 0;
        resetErgebnisPanel();
        videos = sucheVideos(10);
        mediaPlayer = new MediaPlayer(videos.get(0).getPfad());
        mediaPanel.add(mediaPlayer);
        mediaPanel.setVisible(true);
        mediaPlayer.run();
        antwortPanel.setVisible(true);
        naechstesVideo();
    }

    public void naechstesVideo()
    {
        nextButton.setVisible(false);
        if (aktuellesVideo == 10)
        {
            beendeTest();
        }
        else
        {
            Video video = videos.get(aktuellesVideo);
            mediaPlayer = new MediaPlayer(video.getPfad());
            antwortPanel.removeAll();
            createAntwortPanel(video);
            aktuellesVideo++;
            validate();
            repaint();
        }

    }

    private void beendeTest()
    {
        System.out.println("Ende");
        JLabel endeErgebnis = new JLabel("Sie haben " + richtigeAntworten
                + " von 10 Übungen richtig erkannt!");
        mediaPanel.setVisible(false);
        antwortPanel.setVisible(false);
        modusPanel.setVisible(true);
        endeErgebnis.setAlignmentX(CENTER_ALIGNMENT);
        add(endeErgebnis);
        validate();
        repaint();

    }

    private void createAntwortPanel(Video video)
    {
        antwortPanel.setPreferredSize(new Dimension(600, 100));
        antwortPanel.setBackground(Color.WHITE);
        antwortPanel.add(new JLabel("Antwort Wählen:"));
        createAntwortButtons(antwortPanel, video);
        //add(antwortPanel);

    }

    private void createAntwortButtons(JPanel antwortPanel, Video video)
    {

        if (schwierigkeitsgradRb.isSelected())
        {
            createSchwierigkeitsgradButtons(antwortPanel, video);
        }
        else if (elementgruppeRb.isSelected())
        {
            createElementgruppeButtons(antwortPanel, video);
        }
    }
    

    private void createElementgruppeButtons(JPanel antwortPanel, Video video)
    {
        antwortButtons = new ArrayList<KariButton>();
        antwortButtons.clear();
        String antwort = video.getElementgruppe();
        KariButton i = new KariButton("I");
        i.setBackground(MY_RED);
        i.setForeground(Color.WHITE);
        i.addActionListener(testActionListener);
        i.setActionCommand(antwort);
        antwortButtons.add(i);
        antwortPanel.add(i);

        KariButton ii = new KariButton("II");
        ii.setBackground(MY_RED);
        ii.setForeground(Color.WHITE);
        ii.addActionListener(testActionListener);
        ii.setActionCommand(antwort);
        antwortButtons.add(ii);
        antwortPanel.add(ii);

        KariButton iii = new KariButton("III");
        iii.setBackground(MY_RED);
        iii.setForeground(Color.WHITE);
        iii.addActionListener(testActionListener);
        iii.setActionCommand(antwort);
        antwortButtons.add(iii);
        antwortPanel.add(iii);

        KariButton iv = new KariButton("IV");
        iv.setBackground(MY_RED);
        iv.setForeground(Color.WHITE);
        iv.addActionListener(testActionListener);
        iv.setActionCommand(antwort);
        antwortButtons.add(iv);
        antwortPanel.add(iv);

        KariButton v = new KariButton("V");
        v.setBackground(MY_RED);
        v.setForeground(Color.WHITE);
        v.addActionListener(testActionListener);
        v.setActionCommand(antwort);
        antwortButtons.add(v);
        antwortPanel.add(v);
    }

    private void createSchwierigkeitsgradButtons(JPanel antwortPanel, Video video)
    {
        antwortButtons = new ArrayList<KariButton>();
        antwortButtons.clear();
        String antwort = video.getSchwierigkeitsgrad();
        KariButton a = new KariButton("A");
        a.setBackground(MY_RED);
        a.setForeground(Color.WHITE);
        a.addActionListener(testActionListener);
        a.setActionCommand(antwort);
        antwortButtons.add(a);
        antwortPanel.add(a);

        KariButton b = new KariButton("B");
        b.setBackground(MY_RED);
        b.setForeground(Color.WHITE);
        b.addActionListener(testActionListener);
        b.setActionCommand(antwort);
        antwortButtons.add(b);
        antwortPanel.add(b);

        KariButton c = new KariButton("C");
        c.setBackground(MY_RED);
        c.setForeground(Color.WHITE);
        c.addActionListener(testActionListener);
        c.setActionCommand(antwort);
        antwortButtons.add(c);
        antwortPanel.add(c);

        KariButton d = new KariButton("D");
        d.setBackground(MY_RED);
        d.setForeground(Color.WHITE);
        d.addActionListener(testActionListener);
        d.setActionCommand(antwort);
        antwortButtons.add(d);
        antwortPanel.add(d);

        KariButton e = new KariButton("E");
        e.setBackground(MY_RED);
        e.setForeground(Color.WHITE);
        e.addActionListener(testActionListener);
        e.setActionCommand(antwort);
        antwortButtons.add(e);
        antwortPanel.add(e);

        KariButton f = new KariButton("F");
        f.setBackground(MY_RED);
        f.setForeground(Color.WHITE);
        f.addActionListener(testActionListener);
        f.setActionCommand(antwort);
        antwortButtons.add(f);
        antwortPanel.add(f);
    }

    private ArrayList<Video> sucheVideos(int anzahl)
    {
        ArrayList<Video> videoListe = new ArrayList<Video>();
        VideoParser parser = new VideoParser();
        Integer id;

        for (int i = 0; i < anzahl; i++)
        {
            id = (int) (Math.random() * 343);
            videoListe.add(parser.mappeEinVideo(id));
        }

        return videoListe;

    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        frame.setSize(800, 800);
        TestModePanel panel = new TestModePanel();
        frame.getContentPane().add(new JScrollPane(panel));
        Controller.setTestPanel(panel);
        frame.setVisible(true);
    }

    public void zeigeRichtigesErgebnis(String richtigeAntwort)
    {
        richtigeAntworten++;
        remove(ergebnisPanel);
        JLabel richtig = new JLabel(richtigImg);
        ergebnisPanel.add(richtig);
        System.out.println("richtig");
        add(ergebnisPanel);
        aendereFarbeRichtigerButton(richtigeAntwort);
        nextButton.setVisible(true);
        disableAntwortButtons();
        //naechstesVideo();

    }

    private void aendereFarbeRichtigerButton(String richtigeAntwort)
    {
        for (KariButton aktuellerButton : antwortButtons)
        {
            if (aktuellerButton.getText().equals(richtigeAntwort))
                    aktuellerButton.setBackground(Color.GREEN);
        }

    }


    public void zeigeFalschesErgebnis(String richtigeAntwort)
    {
        remove(ergebnisPanel);
        JLabel falsch = new JLabel(falschImg);
        ergebnisPanel.add(falsch);
        System.out.println("falsch");
        add(ergebnisPanel);
        aendereFarbeRichtigerButton(richtigeAntwort);
        nextButton.setVisible(true);
        disableAntwortButtons();
       // naechstesVideo();

    }
    
    private void disableAntwortButtons(){
        for(KariButton aktuellerButton : antwortButtons){
            aktuellerButton.setEnabled(false);
        }
    }

    public void versteckeModusPanel()
    {
        modusPanel.setVisible(false);
    }
}
