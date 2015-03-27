package src.main.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JTable;

import server.Video;
import src.main.Controller;
import src.main.DatenbankController;
import src.main.components.KariButton;
import src.main.panel.TranslationOverview;
import src.main.videoplayer.VideoParser;

public class TranslationButtonActionListener implements ActionListener{
	private static final int TRANSLATION_COLUMN = 2;
	private static final int VIDEO_ID_COLUMN = 0;
	@Override
	public void actionPerformed(ActionEvent e) {
		KariButton button = (KariButton) e.getSource();
		int row = Integer.valueOf(button.getName());
		String buttonText = button.getText();
		TranslationOverview panel = Controller.getTranslationOverview();
		JTable table = panel.getTabelle();
		String translation = (String) table.getModel().getValueAt(row, TRANSLATION_COLUMN);
		int id = (int) table.getModel().getValueAt(row, VIDEO_ID_COLUMN);
		DatenbankController dbController = new DatenbankController();
		VideoParser parser = new VideoParser();
		Video video = parser.mappeEinVideo(id);
		if(buttonText.equals("Neue Übersetzung übernehmen")){
			video.setAmpel(1);
		}else{
			video.setAmpel(0);
			translation = (String) table.getModel().getValueAt(row, TRANSLATION_COLUMN - 1);
		}
		
		video.setName(translation);
		dbController.updateEntry(video);
		int serverId = dbController.findServerId(id);
		server.Connection.newTranslation(serverId, translation, video.getAmpel());
		//Test
		Video video2 = parser.mappeEinVideo(id);
		System.out.println(video2);
	}

}
