package src.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SucheTextfeld extends JTextField implements MouseListener{

	private static final long serialVersionUID = -575653543070324305L;

	private static final String STARTTEXT = "Suche ... ";
	private static final String LUPE_LOGO = "../../img/Logo/lupe.jpg";
	
	public SucheTextfeld() {
		setText(STARTTEXT);
		setPreferredSize(new Dimension(30, 40));
		setLayout(new BorderLayout());
		
		try {
			BufferedImage lupe = ImageIO.read(getClass().getResource(
					LUPE_LOGO));
		
			JLabel label = new JLabel(new ImageIcon(lupe));
	        add(label, BorderLayout.EAST);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		addMouseListener(this);	
	}
	
	public void mouseClicked(MouseEvent e) {
        if(getText().equals(STARTTEXT)) {
        	setText("");        	
        }
    }

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {		
	}
}
