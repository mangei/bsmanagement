package cw.boardingschoolmanagement.app;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.HashMap;
import javax.swing.ImageIcon;

/**
 *
 * @author ManuelG
 */
public class CWResourceManager {

//    private static HashMap<String,Image> imageMap       = new HashMap<String,Image>();
    private static HashMap<String,ImageIcon> iconMap    = new HashMap<String,ImageIcon>();

    /**
     * Loads an image
     * @param path absolute packagepath to the image
     * @return Image
     */
    public static Image loadImage(String path) {
//        Image img = imageMap.get(path);
//        if(img != null) {
//            return img;
//        }

        URL imgURL = Thread.currentThread().getContextClassLoader().getResource(path);
        Image img = Toolkit.getDefaultToolkit().createImage(imgURL);

//        imageMap.put(path, img);

        return img;
    }

    /**
     * Loads an icon
     * @param path absolute packagepath to the image
     * @return ImageIcon
     */
    public static ImageIcon loadIcon(String path) {
        ImageIcon imgIcon = iconMap.get(path);
        if(imgIcon != null) {
            return imgIcon;
        }

        URL imgURL = null;
        try {
            imgURL = Thread.currentThread().getContextClassLoader().getResource(path);
            if (imgURL == null) {
//            logger.debug("imgURL is null; Image is missing; path=" + path);
                imgURL = Thread.currentThread().getContextClassLoader().getResource("cw/boardingschoolmanagement/images/missing_image.png");
            } else {
            }
        } catch (Exception e) { e.printStackTrace(); }

        Image img = Toolkit.getDefaultToolkit().createImage(imgURL);
        if (img == null) {
            throw new NullPointerException("image is null");
        }

        imgIcon = new ImageIcon(img);

        iconMap.put(path, imgIcon);

        return imgIcon;
    }

}
