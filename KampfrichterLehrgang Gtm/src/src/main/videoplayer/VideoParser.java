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
			ArrayList<Video> videos = new ArrayList<Video>();
			ResultSet ergebnis = dbController.getAllByGeraet(geraet);
		    int id;
			String name;
			String pfad;
			String beschreibung;
			String schwierigkeitsgrad;
			String elementgruppe;
			Video video;
			
			try {
				while(ergebnis.next()){
					id = ergebnis.getInt("id");
					name = ergebnis.getString("name");
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
