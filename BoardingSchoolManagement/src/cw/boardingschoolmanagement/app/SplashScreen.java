package cw.boardingschoolmanagement.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 * The start-splashscreen of the application
 * 
 * @author Manuel Geier (CreativeWorkers)
 */
public class SplashScreen extends JFrame {

    private int defaultScreenWidthMargin = 0;
    private int defaultScreenHeightMargin = 0;
//    private int defaultScreenWidthMargin = 50;
//    private int defaultScreenHeightMargin = 37;
//    private Image capture;
    private Image picture;
    private String text = "";

    /**
     * @param file
     */
    public SplashScreen(Image picture, int w, int h) {
        int newW = w + defaultScreenWidthMargin;
        int newH = h + defaultScreenHeightMargin;
        setSize(newW, newH);
        setUndecorated(true);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int frmX = ((int) d.getWidth() - (w + defaultScreenWidthMargin)) / 2;
        int frmY = ((int) d.getHeight() - (h + defaultScreenHeightMargin)) / 2;
        setLocation(frmX, frmY);

        this.picture = picture;

        setTitle("Loading...");
        setIconImage(CWUtils.loadImage("cw/boardingschoolmanagement/images/building.png"));
    }
    
    public void start() {
        setVisible(true);
    }
    
    public void close() {
        setVisible(false);
        dispose();
    }

    public void setText(String text) {
        this.text = text;
        repaint();
    }
    
    @Override
    public void paint(Graphics g) {
            g.drawImage(picture,
                    0 + defaultScreenWidthMargin / 2,
                    0 + defaultScreenHeightMargin / 2, this);
            g.setColor(Color.WHITE);
            g.setFont(new Font("SansSerif", Font.BOLD, 11));
            g.drawString(text, 0 + defaultScreenWidthMargin / 2 + 35, 0 + defaultScreenHeightMargin / 2 + 240);
    }
}
