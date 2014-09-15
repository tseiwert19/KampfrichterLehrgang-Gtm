package src.main.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.AbstractBorder;

public class SucheTextfeld extends JTextField implements MouseListener, KeyListener
{

    private static final long serialVersionUID = -575653543070324305L;

    private static final String STARTTEXT = "Nach Element suchen ... ";
    private static final String LUPE_LOGO = "/img/Logo/lupe.jpg";

    private KariButton lupeButton;

    public SucheTextfeld()
    {
        setText(STARTTEXT);
        setPreferredSize(new Dimension(700, 40));
        setLayout(new BorderLayout());
        setBorder(new RoundedCornerBorder());

        try
        {
            BufferedImage lupe = ImageIO.read(getClass().getResource(LUPE_LOGO));

            lupeButton = new KariButton();
            lupeButton.setBackground(Color.WHITE);
            lupeButton.setIcon(new ImageIcon(lupe));
            lupeButton.setBorder(null);
            lupeButton.setPreferredSize(new Dimension(25, 30));
           

            add(lupeButton, BorderLayout.EAST);

        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        addMouseListener(this);
        addKeyListener(this);
    }

    public JButton getLupeButton()
    {
        return lupeButton;
    }

    //  protected void paintComponent(Graphics g) {
    //      g.setColor(getBackground());
    //      g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
    //      super.paintComponent(g);
    //  }
    //
    //  protected void paintBorder(Graphics g) {
    //      g.setColor(getForeground());
    //      g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
    //  }

    public void mouseClicked(MouseEvent e)
    {
        if (getText().equals(STARTTEXT))
        {
            setText("");
        }
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        setToolTipText("<html>Geben Sie hier bitte den Begriff ein, nach dem Sie suchen wollen. <br /><br /> Mit Enter startet die Suche. </html>");
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        if (getText().equals(STARTTEXT))
        {
            setText("");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        if (getText().equals(STARTTEXT))
        {
            setText("");
        }
    }

    @Override
    public void keyPressed(KeyEvent keyEvent)
    {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER)
        {
            System.out.println("ENTER key pressed");
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent)
    {
    }

    @Override
    public void keyTyped(KeyEvent keyEvent)
    {
    }
}

/**
 * Zeichnet einen Rahmen mit abgerundeten Ecken
 * Quelle: http://java-swing-tips.blogspot.com.ar/2012/03/rounded-border-for-jtextfield.html
 *
 */
class RoundedCornerBorder extends AbstractBorder
{
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
    {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int r = height - 1;
        RoundRectangle2D round = new RoundRectangle2D.Float(x, y, width - 1, height - 1, r, r);
        Container parent = c.getParent();
        if (parent != null)
        {
            g2.setColor(parent.getBackground());
            Area corner = new Area(new Rectangle2D.Float(x, y, width, height));
            corner.subtract(new Area(round));
            g2.fill(corner);
        }
        g2.setColor(Color.GRAY);
        g2.draw(round);
        g2.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c)
    {
        return new Insets(4, 8, 4, 8);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets)
    {
        insets.left = insets.right = 8;
        insets.top = insets.bottom = 4;
        return insets;
    }
}
