package src.main.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Erstellt eine ComboBox mit abgerundeten Ecken
 * @author michael
 *
 */
@SuppressWarnings("rawtypes")
public class RoundCorneredComboBox extends JComboBox
{

	private static final long serialVersionUID = -4566456127419518856L;
	private JLabel itemLabel;
    private BufferedImage leftImage = null;
    private BufferedImage rightImage = null;
    private BufferedImage centerImage = null;

    //Konstanten fuer die Imagepfade
    private static final String LEFT_IMAGE = "/img/ComboBox/leftComboBox.png";
    private static final String RIGHT_IMAGE = "/img/ComboBox/rightComboBox.png";
    private static final String CENTER_IMAGE = "/img/ComboBox/centerComboBox.png";
    //Konstanten fuer Sizes
    private static final int MIN_WIDTH = 100;
    private static final int MAX_WIDTH = 2000;
    private static final int MIN_HEIGHT = 31;
    private static final int MAX_HEIGHT = 31;
    private static final int PREF_WIDTH = 300;
    private static final int PREF_HEIGHT = 31;

    @SuppressWarnings("unchecked")
    public RoundCorneredComboBox(String[] items)
    {
        super(items);
        itemLabel = new JLabel();
        itemLabel.setText(items[0]);
        loadImages();
        createLayoutAndPanels();
        createSizes();
    }

    /**
     * Erstellt die ImagePanels und positioniert diese
     */
    private void createLayoutAndPanels()
    {
        ImagePanel leftPanel = new ImagePanel(leftImage, false);
        ImagePanel rightPanel = new ImagePanel(rightImage, false);
        ImagePanel centerPanel = new ImagePanel(centerImage, true);

        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        add(centerPanel, BorderLayout.CENTER);

        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(itemLabel, BorderLayout.CENTER);
    }

    /**
     * Legt die Groessen fest
     */
    private void createSizes()
    {
        Dimension minSize = new Dimension(MIN_WIDTH, MIN_HEIGHT);
        Dimension maxSize = new Dimension(MAX_WIDTH, MAX_HEIGHT);
        Dimension prefSize = new Dimension(PREF_WIDTH, PREF_HEIGHT);

        setMinimumSize(minSize);
        setMaximumSize(maxSize);
        setPreferredSize(prefSize);
    }

    /**
     * Laedt die Bilder
     */
    private void loadImages()
    {
        try
        {
            leftImage = ImageIO.read(getClass().getResource(LEFT_IMAGE));
            rightImage = ImageIO.read(getClass().getResource(RIGHT_IMAGE));
            centerImage = ImageIO.read(getClass().getResource(CENTER_IMAGE));
        }
        catch (IOException e)
        {
            System.err.println("Fehler beim Einlesen der ComboBoxBilder!");
            e.printStackTrace();
        }
    }

    /**
     * Aktualisiert das JLabel, damit das momentane Item angezeigt wird
     * @param newText
     */
    public void refreshItemLabel(String newText)
    {
        itemLabel.setText(newText);
        validate();
        repaint();
    }

}

/**
 * Ein Panel das ein Bild als Hintergrund hat
 * @author michael
 *
 */
class ImagePanel extends JPanel
{

	private static final long serialVersionUID = -8895117283536671123L;
	private Image img;
    private boolean repeat;

    /**
     * Konstruktor
     * @param img wird als Hintergrund gesetzt
     * @param repeat ob Bild endlos wiederholt werden soll
     */
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

    /**
     * Zeichnet Komponente
     * Wenn repeat = true --> Image wiederholt sich 
     */
    public void paintComponent(Graphics g)
    {
        if (repeat)
        {
            int iw = img.getWidth(this);
            int ih = img.getHeight(this);
            if (iw > 0 && ih > 0)
            {
                for (int x = 0; x < getWidth(); x += iw)
                {
                    for (int y = 0; y < getHeight(); y += ih)
                    {
                        g.drawImage(img, x, y, iw, ih, this);
                    }
                }
            }
        }
        else
        {
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
    }

}
