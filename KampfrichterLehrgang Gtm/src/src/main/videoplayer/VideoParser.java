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
//dient nur zum Testen des ResultPanels
			dbController.addVideo(1, "Salto", "/videos/salto", "boden" ,"salto", 3, "Boden");
			dbController.addVideo(2, "Rolle", "/videos/rolle", "boden", "rolle", 1, "Boden");
			dbController.addVideo(3, "Rolle", "/videos/rolle", "boden", "rolle", 1, "Boden");
			dbController.addVideo(4, "Rolle", "/videos/rolle", "boden", "rolle", 1, "Boden");
			dbController.addVideo(5, "Rolle", "/videos/rolle", "boden", "rolle", 1, "Boden");
		}
		
		public ArrayList<Video> mappeVideosVonGeraet(String geraet){
			ArrayList<Video> videos = new ArrayList<Video>();
			ResultSet ergebnis = dbController.getAllByGeraet(geraet);
		    int id;
			String name;
			String pfad;
			String beschreibung;
			int schwierigkeitsgrad;
			String elementgruppe;
			Video video;
			
			try {
				while(ergebnis.next()){
					id = ergebnis.getInt("id");
					name = ergebnis.getString("name");
					pfad = ergebnis.getString("pfad");
					beschreibung = ergebnis.getString("beschreibung");
					schwierigkeitsgrad = ergebnis.getInt("schwierigkeitsgrad");
					elementgruppe = ergebnis.getString("elementgruppe");
					
					video = new Video(id, name, pfad, geraet, beschreibung, schwierigkeitsgrad, elementgruppe);
					videos.add(video);
					
				}
			} catch (SQLException e) {
				System.err.println("Fehler bei Datenbankabfrage!");
				e.printStackTrace();
			}
//Dient nur zum Testen des ResultPanels
			dbController.deleteVideo(1);
			dbController.deleteVideo(2);
			dbController.deleteVideo(3);
			dbController.deleteVideo(4);
			dbController.deleteVideo(5);
			
			return videos;
		}
}
