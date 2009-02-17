package cw.boardingschoolmanagement.app;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.net.URL;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.ImageIcon;
import org.apache.log4j.Logger;

/**
 * Helperclass with helpful functions
 * 
 * @author Manuel Geier (CreativeWorkers)
 */
public class CWUtils {

    private static Logger logger = Logger.getLogger(CWUtils.class);

    /**
     * Berechnet die Koordinate, damit das Bild zentriert ist
     * @param container
     * @param img
     * @return
     */
    private static int centerValue(int container, int img) {
        return (container - img) / 2;
    }

    private static int centerValue(double container, int img) {
        return centerValue((int) container, img);
    }

    private static int centerValue(double container, double img) {
        return centerValue((int) container, (int) img);
    }

    public static void centerWindow(Window window) {
        if (window == null) {
            throw new NullPointerException();
        }

        int x = centerValue(Toolkit.getDefaultToolkit().getScreenSize().getWidth(), window.getWidth());
        int y = centerValue(Toolkit.getDefaultToolkit().getScreenSize().getHeight(), window.getHeight());

        window.setLocation(x, y);
    }

    public static void centerWindow(Window child, Window parent) {
        if (parent == null) {
            centerWindow(child);
            return;
        }

        int x = (int) parent.getLocation().getX() + centerValue(parent.getWidth(), child.getWidth());
        int y = (int) parent.getLocation().getY() + centerValue(parent.getHeight(), child.getHeight());

        child.setLocation(x, y);
    }

    public static Calendar date2Calendar(Date d) {
        Calendar c = new GregorianCalendar();
        c.setTimeInMillis(d.getTime());
        return c;
    }

    public static Date calendar2Date(Calendar c) {
        return new Date(c.getTimeInMillis());
    }

    public static Image loadImage(String path) {
        URL imgURL = Thread.currentThread().getContextClassLoader().getResource(path);
        Image img = Toolkit.getDefaultToolkit().createImage(imgURL);
        return img;
    }

    public static ImageIcon loadIcon(String path) {
        URL imgURL = null;
        try {
            imgURL = Thread.currentThread().getContextClassLoader().getResource(path);
            if (imgURL == null) {
//            logger.debug("imgURL is null; Image is missing; path=" + path);
                imgURL = Thread.currentThread().getContextClassLoader().getResource("cw/boardingschoolmanagement/images/missing_image.png");
            } else {
            }
        } catch (Exception e) {
        }
        Image img = Toolkit.getDefaultToolkit().createImage(imgURL);
        if (img == null) {
            throw new NullPointerException("image is null");
        }
        return new ImageIcon(img);
    }

    public static URL getURL(String path) {
        URL url = null;
        try {
            url = Thread.currentThread().getContextClassLoader().getResource(path);
        } catch (Exception e) {}
        return url;
    }
}
