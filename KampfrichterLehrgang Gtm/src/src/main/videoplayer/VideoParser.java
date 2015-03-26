package src.main.videoplayer;

import server.Video;
import src.main.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Mappt einen Datensatz aus der Datenbank auf ein Video Objekt.
 * @author michael
 *
 */
public class VideoParser
{
    private DatenbankController dbController;

    public VideoParser()
    {
        dbController = new DatenbankController();
    }
    /**
     * Mappt alle Datensaetze aus der Datenbank, die mit geraet uebereinstimmen, auf Video-Objekte
     * @param geraet
     * @return Liste mit Videos
     */
    public ArrayList<Video> mappeVideosVonGeraet(String geraet)
    {
        ResultSet ergebnis = dbController.getAllByGeraet(geraet);
        return parseVideos(ergebnis);
    }
    /**
     * Mappt alle Datensaetze aus der Datenbank, die mit name uebereinstimmen, auf Video-Objekte
     * @param name
     * @return Liste mit Videos
     */
    public ArrayList<Video> mappeVideosVonName(String name)
    {
        ResultSet ergebnis = dbController.getAllByName(name);
        return parseVideos(ergebnis);
    }
    /**
     * Mappt alle Datensaetze aus der Datenbank, die mit i uebereinstimmen, auf Video-Objekte
     * @param i = Ampelfarbe (0=grün, 1=gelb, 2=rot)
     * @return Liste mit Videos
     */
    public ArrayList<Video> mappeVideosVonAmpel(int i){
    	ResultSet ergebnis = dbController.getAllByAmpel(i);
    	return parseVideos(ergebnis);
    }
    /**
     * Mappt alle Datensaetze aus der Datenbank, die mit geraet, schwierigkeitsgrad und elementgruppe uebereinstimmen, auf Video-Objekte
     * @param geraet
     * @param schwierigkeitsgrad
     * @param elementgruppe
     * @return Liste mit Videos
     */
    public ArrayList<Video> mappeGefilterteVideos(String geraet, String schwierigkeitsgrad,
            String elementgruppe)
    {
        ArrayList<Video> videos = null;
        if (!schwierigkeitsgrad.equals("Alle Schwierigkeitsgrade anzeigen")
                && !elementgruppe.equals("Alle Elementgruppen anzeigen"))
        {
            ResultSet ergebnis = dbController.getAllByGeraetSchwierigkeitsgradElementgruppe(geraet,
                    schwierigkeitsgrad, elementgruppe);
            videos = parseVideos(ergebnis);
        }
        else if (!schwierigkeitsgrad.equals("Alle Schwierigkeitsgrade anzeigen")
                && elementgruppe.equals("Alle Elementgruppen anzeigen"))
        {
            ResultSet ergebnis = dbController.getAllByGeraetSchwierigkeitsgrad(geraet,
                    schwierigkeitsgrad);
            videos = parseVideos(ergebnis);
        }
        else if (schwierigkeitsgrad.equals("Alle Schwierigkeitsgrade anzeigen")
                && !elementgruppe.equals("Alle Elementgruppen anzeigen"))
        {
            ResultSet ergebnis = dbController.getAllByGeraetElementgruppe(geraet, elementgruppe);
            videos = parseVideos(ergebnis);
        }
        else
        {
            videos = mappeVideosVonGeraet(geraet);
        }

        return videos;
    }
    /**
     * Mappt einen Datensatz auf ein Video-Objekt
     * @param id
     * @return Video
     */
    public Video mappeEinVideo(int id)
    {
        Video video;
        ResultSet ergebnis = dbController.getEntry(id);
        ArrayList<Video> videoListe = parseVideos(ergebnis);
        if (videoListe.size() == 1)
        {
            video = videoListe.get(0);
        }
        else
        {
            video = null;
        }

        return video;
    }
    /**
     * Uebernimmt das Parsen eines ResultSets
     * @param ergebnis
     * @return Liste mit Videos
     */
    private ArrayList<Video> parseVideos(ResultSet ergebnis)
    {
        int id;
        String name;
        String pfad;
        String beschreibung;
        String geraet;
        String schwierigkeitsgrad;
        String elementgruppe;
        Video video;
        ArrayList<Video> videos = new ArrayList<Video>();


        try
        {
            while (ergebnis.next())
            {
                id = ergebnis.getInt("id");
                name = ergebnis.getString("name");
                geraet = ergebnis.getString("geraet");
                pfad = ergebnis.getString("pfad");
                beschreibung = ergebnis.getString("beschreibung");
                schwierigkeitsgrad = ergebnis.getString("schwierigkeitsgrad");
                elementgruppe = ergebnis.getString("elementgruppe");

                video = new Video(id, name, pfad, geraet, beschreibung, schwierigkeitsgrad,
                        elementgruppe);
                videos.add(video);

            }
        }
        catch (SQLException e)
        {
            System.err.println("Fehler bei Datenbankabfrage!");
            e.printStackTrace();
        }
        return videos;
    }
    /**
     * Mappt alle Datensaetze aus der Datenbank, die mit name, geraet und elementgruppe uebereinstimmen, auf Video-Objekte
     * @param name
     * @param geraet
     * @param elementgruppe
     * @return Liste mit Videos
     */
    public ArrayList<Video> mappeGefilterteSucheVideos(String name, String geraet,
            String elementgruppe)
    {
        ArrayList<Video> videos = null;
        if (!geraet.equals("Alle Geräte anzeigen")
                && !elementgruppe.equals("Alle Elementgruppen anzeigen"))
        {
            ResultSet ergebnis = dbController.getAllByNameGeraetElementgruppe(name, geraet,
                    elementgruppe);
            videos = parseVideos(ergebnis);
        }
        else if (!geraet.equals("Alle Geräte anzeigen")
                && elementgruppe.equals("Alle Elementgruppen anzeigen"))
        {
            ResultSet ergebnis = dbController.getAllByNameGeraet(name, geraet);
            videos = parseVideos(ergebnis);
        }
        else if (geraet.equals("Alle Geräte anzeigen")
                && !elementgruppe.equals("Alle Elementgruppen anzeigen"))
        {
            ResultSet ergebnis = dbController.getAllByNameElementgruppe(name, elementgruppe);
            videos = parseVideos(ergebnis);
        }
        else
        {
            ResultSet ergebnis = dbController.getAllByName(name);
            videos = parseVideos(ergebnis);
        }

        return videos;
    }
}
