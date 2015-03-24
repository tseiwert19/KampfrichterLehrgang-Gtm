package src.main.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import src.main.Connection;
import src.main.Controller;
import src.main.videoplayer.Video;

public class OnlineSearchActionListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
        
		
		List<Video> video =  Connection.findVideo(e.getActionCommand());
		ArrayList<Video> videos = new ArrayList<Video>(video);
		Controller.getKampfrichterlehrgang().changeToSearchResult(videos);
	}

}
