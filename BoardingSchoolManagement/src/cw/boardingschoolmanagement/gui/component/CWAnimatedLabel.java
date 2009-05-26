package cw.boardingschoolmanagement.gui.component;

import java.awt.Graphics;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author ManuelG
 */
public class CWAnimatedLabel extends JLabel implements Runnable {

    protected Icon[] icons;
    protected int index = 0;
    protected boolean isRunning;

    CWAnimatedLabel(String gifName, int numGifs) {
        icons = new Icon[numGifs];
        for (int i = 0; i < numGifs; i++) {
            icons[i] = new ImageIcon(gifName + i + ".gif");
        }
        setIcon(icons[0]);

        Thread tr = new Thread(this);
        tr.setPriority(Thread.MAX_PRIORITY);
        tr.start();
    }

    public void setRunning(boolean r) {
        isRunning = r;
    }

    public boolean getRunning() {
        return isRunning;
    }

    public void run() {
        while (true) {
            if (isRunning) {
                index++;
                if (index >= icons.length) {
                    index = 0;
                }
                setIcon(icons[index]);
                Graphics g = getGraphics();
                icons[index].paintIcon(this, g, 0, 0);
            } else {
                if (index > 0) {
                    index = 0;
                    setIcon(icons[0]);
                }
            }
            try {
                Thread.sleep(500);
            } catch (Exception ex) {
            }
        }
    }
}
