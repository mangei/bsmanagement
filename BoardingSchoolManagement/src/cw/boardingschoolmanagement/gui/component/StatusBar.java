package cw.boardingschoolmanagement.gui.component;

/**
 *
 * @author Manuel Geier
 */
import com.jidesoft.swing.JideSwingUtilities;
import cw.boardingschoolmanagement.manager.PropertiesManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jdesktop.swingx.JXStatusBar;

public class StatusBar
        extends JPanel
{

    public static final int NORMAL = 0;
    private static final long DURATION = 600;
    private static final long DURABLE;
    private JLabel statusText;
    private Timer statusTextTimer = null;
    private JXStatusBar statusBar;

    static {
        DURABLE = Integer.parseInt(PropertiesManager.getProperty("application.gui.statusbar.durable", "5000"));
    }

    public StatusBar() {
//        setPreferredSize(new Dimension(10, 23));
        setLayout(new BorderLayout());
//        setPreferredSize(new Dimension(10, 50));
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(new Color(178, 187, 200)));

//        Font font = getFont();


//        JPanel rightPanel = new JPanel(new BorderLayout());
//        rightPanel.add(new JLabel(new AngledLinesWindowsCornerIcon()), BorderLayout.SOUTH);
//        rightPanel.setOpaque(false);

//        statusText2 = new AnimatedLabel();
////        statusText.setOpaque(false);
////        add(statusText2);
//
//        System.out.println("components: " + statusText2.getComponentCount());
//
//        if (statusText2.getComponentCount() > 0) {
//            statusText2.getComponent(0).setFont(font);
//            if (statusText2.getComponentCount() > 1) {
//                statusText2.getComponent(1).setFont(font);
//            }
//        }
//
//        System.out.println("bef font-name: " + statusText2.getFont().getFontName());
//        System.out.println("bef font-size: " + statusText2.getFont().getSize());
////        statusText.setFont(new Font("Courier New", Font.PLAIN, 10));
//
//        System.out.println("aft font-name: " + statusText2.getFont().getFontName());
//        System.out.println("aft font-size: " + statusText2.getFont().getSize());

        statusText = new JLabel();
        add(statusText, BorderLayout.CENTER);

        statusBar = new JXStatusBar();
        statusBar.setOpaque(false);
        add(statusBar, BorderLayout.EAST);

        final JLabel lGc = new JLabel("0/0");
        statusBar.add(lGc);

         new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                lGc.setText( "availableProcessor:"
                        + Runtime.getRuntime().availableProcessors()
                        + " / maxMemory: "
                        + Runtime.getRuntime().maxMemory() / 1024
                        + "kb / freeMemory: "
                        + Runtime.getRuntime().freeMemory() / 1024
                        + "kb / totalMemory: "
                        + Runtime.getRuntime().totalMemory() / 1024
                        + "kb");
//                Runtime.getRuntime().gc();
//                Runtime.getRuntime().runFinalization();
            }
        }, 1000, 1000);

        statusBar.add(new JLabel("Test"));

        setTextAndFadeOut("Willkommen");

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                StatusBar.this.setTextAndFadeOut("Test222222222222222222222222222");
            }
        }, 5000);

//        setBackground(SystemColor.control);
    }

    /**
     * Zeigt einen Text in der Statusbar an
     */   
    public void setText(String text) {
        stopStatusTextTimer();
        statusText.setText(text);
    }

    /**
     * Zeigt einen Text in der Statusbar und lässt ihn wieder verschwinden
     */
    public void setTextAndFadeOut(String text) {
        stopStatusTextTimer();

        // text anzeigen lassen, warten, dann ausblenden
        statusText.setText(text);

//        System.out.println("font-name: " + statusText2.getFont().getFontName());
//        System.out.println("font-size: " + statusText2.getFont().getSize());

        statusTextTimer = new Timer();
        statusTextTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                statusText.setText("");
                statusTextTimer = null;
            }
        }, DURABLE);

    }

    private void stopStatusTextTimer() {
        if (statusTextTimer != null) {
            statusTextTimer.cancel();
            statusTextTimer = null;
        }
    }
    private static final Color LIGHT_GRAY_COLOR = new Color(214, 219, 229);

    @Override
    protected void paintComponent(Graphics g) {
        //        super.paintComponent(g);

        //        int y = 0;
        //        g.setColor(new Color(156, 154, 140));
        //        g.drawLine(0, y, getWidth(), y);
        //        y++;
        //        g.setColor(new Color(196, 194, 183));
        //        g.drawLine(0, y, getWidth(), y);
        //        y++;
        //        g.setColor(new Color(218, 215, 201));
        //        g.drawLine(0, y, getWidth(), y);
        //        y++;
        //        g.setColor(new Color(233, 231, 217));
        //        g.drawLine(0, y, getWidth(), y);
        //
        //        y = getHeight() - 3;
        //        g.setColor(new Color(233, 232, 218));
        //        g.drawLine(0, y, getWidth(), y);
        //        y++;
        //        g.setColor(new Color(233, 231, 216));
        //        g.drawLine(0, y, getWidth(), y);
        //        y = getHeight() - 1;
        //        g.setColor(new Color(221, 221, 220));
        //        g.drawLine(0, y, getWidth(), y);

        // Background Gradient
        Rectangle rect = new Rectangle(0, 0, getWidth(), getHeight());
        JideSwingUtilities.fillGradient((Graphics2D) g, rect, Color.WHITE, LIGHT_GRAY_COLOR, true);
        //        // Top Line
        //        g.setColor(new Color(69,101,152));
        //        g.drawLine(0, 0, getWidth(), 0);
        //        // Bottom Line
        //        g.setColor(new Color(68,101,152));
        //        g.drawLine(0, getHeight(), getWidth(), getHeight());
        super.paintComponent(g);

    }
}

class AngledLinesWindowsCornerIcon
        implements Icon {

    private static final Color WHITE_LINE_COLOR = new Color(255, 255, 255);
    private static final Color GRAY_LINE_COLOR = new Color(172, 168, 153);
    private static final int WIDTH = 13;
    private static final int HEIGHT = 13;

    public int getIconHeight() {
        return WIDTH;
    }

    public int getIconWidth() {
        return HEIGHT;
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {

        g.setColor(WHITE_LINE_COLOR);
        g.drawLine(0, 12, 12, 0);
        g.drawLine(5, 12, 12, 5);
        g.drawLine(10, 12, 12, 10);

        g.setColor(GRAY_LINE_COLOR);
        g.drawLine(1, 12, 12, 1);
        g.drawLine(2, 12, 12, 2);
        g.drawLine(3, 12, 12, 3);

        g.drawLine(6, 12, 12, 6);
        g.drawLine(7, 12, 12, 7);
        g.drawLine(8, 12, 12, 8);

        g.drawLine(11, 12, 12, 11);
        g.drawLine(12, 12, 12, 12);

    }
}