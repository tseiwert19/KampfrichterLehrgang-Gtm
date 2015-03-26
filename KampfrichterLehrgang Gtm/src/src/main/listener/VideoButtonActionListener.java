package src.main.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import server.Video;
import src.main.Controller;
import src.main.components.KariButton;
import src.main.panel.VideoInfoPanel;
import src.main.videoplayer.VideoParser;
/**
 * ActionListener fuer "VideoButtons"
 * Leitet von Result/SearchResultPanel zu VideoInfoPanel weiter
 * @author michael
 *
 */
public class VideoButtonActionListener implements ActionListener
{

    @Override
    public void actionPerformed(ActionEvent e)
    {
        KariButton button = (KariButton) e.getSource();
        int id = Integer.valueOf(button.getName());
        Controller.getKampfrichterlehrgang().changeToVideoInfoPanel(id);
        
    }

}
