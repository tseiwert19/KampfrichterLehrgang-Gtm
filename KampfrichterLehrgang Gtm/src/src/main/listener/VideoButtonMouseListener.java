package src.main.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import src.main.components.KariButton;
import src.main.videoplayer.Video;

/**
 * MouseoverListener fuer VideoButtons.
 * Faehrt der Nutzer mit der Maus ueber einen Button, wird ihm ein Tooltip mit dem Namen des Videos angezeigt.
 * @author michael
 *
 */
public class VideoButtonMouseListener implements MouseListener
{

    @Override
    public void mouseClicked(MouseEvent e)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        KariButton button = (KariButton) e.getSource();
        Video video = button.getVideo();
        button.setToolTipText("<html><b>Vollständiger Name:</b> <br>" + video.getName() + "</html>");

    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        // TODO Auto-generated method stub

    }

}
