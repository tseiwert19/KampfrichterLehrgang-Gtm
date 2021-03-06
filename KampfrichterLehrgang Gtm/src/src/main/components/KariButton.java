package src.main.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import src.main.videoplayer.Video;

public class KariButton extends JButton implements MouseListener{

	private static final long serialVersionUID = 1L;
	private Video video;

	public KariButton() {
		setBackground(Color.WHITE);
		addMouseListener(this);
	}

	public KariButton(String string)
    {
        super(string);
        setBackground(Color.WHITE);
        addMouseListener(this);
    }
	
	public void setVideo(Video v){
	    this.video = v;
	}
	
	public Video getVideo(){
	    return video;
	}

    @Override
	public void mouseClicked(MouseEvent e) {		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));					
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		setCursor(new Cursor(Cursor.HAND_CURSOR));					
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
