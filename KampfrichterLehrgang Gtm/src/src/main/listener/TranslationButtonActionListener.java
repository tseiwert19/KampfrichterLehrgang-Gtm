package src.main.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;

import src.main.Controller;
import src.main.DatenbankController;
import src.main.components.KariButton;
import src.main.panel.TranslationOverview;
import src.main.videoplayer.Video;
import src.main.videoplayer.VideoParser;

public class TranslationButtonActionListener implements ActionListener{
	private static final int TRANSLATION_COLUMN = 2;
	private static final int VIDEO_ID_COLUMN = 0;
	@Override
	public void actionPerformed(ActionEvent e) {
		KariButton button = (KariButton) e.getSource();
		int row = Integer.valueOf(button.getName());
		TranslationOverview panel = Controller.getTranslationOverview();
		JTable table = panel.getTabelle();
		String translation = (String) table.getModel().getValueAt(row, TRANSLATION_COLUMN);
		int id = (int) table.getModel().getValueAt(row, VIDEO_ID_COLUMN);
		DatenbankController dbController = new DatenbankController();
		VideoParser parser = new VideoParser();
		Video video = parser.mappeEinVideo(id);
		video.setAmpel(0);
		video.setName(translation);
		dbController.updateEntry(video);
		//Test
		Video video2 = parser.mappeEinVideo(id);
		System.out.println(video2);
	}

}
