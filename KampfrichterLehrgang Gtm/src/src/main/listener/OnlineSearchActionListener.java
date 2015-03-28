package src.main.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import server.Connection;
import server.Video;
import src.main.Controller;
import src.main.DatenbankController;
import src.main.videoplayer.VideoParser;

public class OnlineSearchActionListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
        
		
		List<Video> video =  Connection.findVideo(e.getActionCommand());
		ArrayList<Video> videos = new ArrayList<Video>(video);
		writeVideoToDatabase(videos);
		//Videos müssen neu aus der DB geholt werden, damit die Pfade aktuell sind
		VideoParser parser = new VideoParser();
		ArrayList<Video> videoNeu = parser.mappeVideosVonName(e.getActionCommand());
		Controller.getKampfrichterlehrgang().changeToSearchResult(videoNeu);
	}
	
	private void writeVideoToDatabase(ArrayList<Video> videos) {
		DatenbankController db = new DatenbankController();
		String name = null;
		String pfad = null;
		String geraet = null;
		String beschreibung = null;
		String schwierigkeitsgrad = null;
		String elementgruppe = null;
		int clientId = 999;
		int serverId = 0;
		int ampel = 0;
		for(int i=0; i < videos.size(); i++){
			Video video = videos.get(i);
			
			name = video.getName();
			pfad = video.getPfad();
		    geraet = video.getGeraet();
		    beschreibung = video.getBeschreibung();
		    schwierigkeitsgrad = video.getSchwierigkeitsgrad();
		    elementgruppe = video.getElementgruppe();
		    ampel = video.getAmpel();
		    video.createVideoFile();
		    serverId = video.getId(); 			
		    clientId = db.addVideo(name, video.getPfad(), geraet, beschreibung, schwierigkeitsgrad, elementgruppe, ampel);
		    db.connectIds(serverId, clientId);
		    System.out.println("ClientID: " + clientId + " ServerId: " + serverId);
		}
		
	}

}
