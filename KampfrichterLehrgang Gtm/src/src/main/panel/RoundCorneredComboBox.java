package src.main.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * Erstellt eine ComboBox mit abgerundeten Ecken
 * @author michael
 *
 */
public class RoundCorneredComboBox extends JComboBox
{
    @SuppressWarnings("unchecked")
    public RoundCorneredComboBox(String[] items)
    {
        super(items);
        
        BufferedImage leftImage = null;
        BufferedImage rightImage = null;
        BufferedImage centerImage = null;
        
        try
        {
            leftImage = ImageIO
                    .read(getClass().getResource("../../../img/ComboBox/leftComboBox.png"));
            rightImage = ImageIO.read(getClass().getResource(
                    "../../../img/ComboBox/rightComboBox.png"));
            centerImage = ImageIO.read(getClass().getResource(
                    "../../../img/ComboBox/centerComboBox.png"));
        }
        catch (IOException e)
        {
            System.err.println("Fehler beim Einlesen der ComboBoxBilder!");
            e.printStackTrace();
        }
        ImagePanel leftPanel = new ImagePanel(leftImage, false);
        ImagePanel rightPanel = new ImagePanel(rightImage, false);
        ImagePanel centerPanel = new ImagePanel(centerImage, true);

        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        add(centerPanel, BorderLayout.CENTER);
        
        Dimension minSize = new Dimension (100, 31);
        Dimension maxSize = new Dimension(2000, 31);
        Dimension prefSize = new Dimension(400, 31);
 
        
        setMinimumSize(minSize);
        setMaximumSize(maxSize);
        setPreferredSize(prefSize);
    }

}

class ImagePanel extends JPanel
{

    private Image img;
    private boolean repeat;

    public ImagePanel(Image img, boolean repeat)
    {
        this.img = img;
        this.repeat = repeat;
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }

    public void paintComponent(Graphics g)
    {
        if (repeat) {
            int iw = img.getWidth(this);
            int ih = img.getHeight(this);
            if (iw > 0 && ih > 0) {
                for (int x = 0; x < getWidth(); x += iw) {
                    for (int y = 0; y < getHeight(); y += ih) {
                        g.drawImage(img, x, y, iw, ih, this);
                    }
                }
            }
        } else {
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
    }

}

