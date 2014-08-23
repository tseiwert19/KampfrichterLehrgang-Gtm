package src.main.videoplayer;

import src.main.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Mappt einen Datensatz aus der Datenbank auf ein Video Objekt.
 * @author michael
 *
 */
public class VideoParser {
		private DatenbankController dbController;
		
		public VideoParser(){
			dbController = new DatenbankController();
		}
		
		public ArrayList<Video> mappeVideosVonGeraet(String geraet){
			ResultSet ergebnis = dbController.getAllByGeraet(geraet);
			return parseVideos(ergebnis);
		}
		
		public ArrayList<Video> mappeVideosVonName(String name){
            ResultSet ergebnis = dbController.getAllByName(name);
            return parseVideos(ergebnis);
		}
		
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
        }else{
            videos = mappeVideosVonGeraet(geraet);
        }
            

        return videos;
    }
		
		private ArrayList<Video> parseVideos(ResultSet ergebnis){
            int id;
            String name;
            String pfad;
            String beschreibung;
            String geraet;
            String schwierigkeitsgrad;
            String elementgruppe;
            Video video;
            ArrayList<Video> videos = new ArrayList<Video>();
            
            try {
                while(ergebnis.next()){
                    id = ergebnis.getInt("id");
                    name = ergebnis.getString("name");
                    geraet = ergebnis.getString("geraet");
                    pfad = ergebnis.getString("pfad");
                    beschreibung = ergebnis.getString("beschreibung");
                    schwierigkeitsgrad = ergebnis.getString("schwierigkeitsgrad");
                    elementgruppe = ergebnis.getString("elementgruppe");
                    
                    video = new Video(id, name, pfad, geraet, beschreibung, schwierigkeitsgrad, elementgruppe);
                    videos.add(video);
                    
                }
            } catch (SQLException e) {
                System.err.println("Fehler bei Datenbankabfrage!");
                e.printStackTrace();
            }
            return videos;
		}
}
