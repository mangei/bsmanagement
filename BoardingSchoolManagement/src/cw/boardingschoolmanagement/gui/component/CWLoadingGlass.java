package cw.boardingschoolmanagement.gui.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

import cw.boardingschoolmanagement.app.CWUtils;


/**
 * Represents a glasspane which shows a loading icon and a text.
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public class CWLoadingGlass extends JComponent {

    private String text;
    private ImageIcon iconImage;
    private boolean drawLoadingIcon;

    public CWLoadingGlass(Container contentPane, boolean drawLoadingIcon) {

        if(drawLoadingIcon) {
            iconImage = CWUtils.loadIcon("cw/boardingschoolmanagement/images/loadingicon.gif");
        }

        text = "";
        setOpaque(false);

        CBListener listener = new CBListener(this, contentPane);
        addMouseListener(listener);
        addMouseMotionListener(listener);

        this.drawLoadingIcon = drawLoadingIcon;
    }

    private static Color GRAY = new Color(200, 200, 200, 100);
    private static Color LIGHT_GRAY = new Color(217, 217, 217);
    private static Color BLACK = new Color(0, 0, 0, 0.5f);

    @Override
    protected void paintComponent(Graphics g) {
        int w = getWidth();
        int h = getHeight();
        int arc = 10;
        int strWidth = g.getFontMetrics().stringWidth(text);
        int verticalTextOffset = 20;
        int verticalIconOffset = -20;
        int iconRadius = 8;

        // If ther is no loadingIcon, then draw the text in the center
        if(!drawLoadingIcon) {
            verticalTextOffset = 0;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background
        g2d.setColor(GRAY);
        g2d.fillRect(0, 0, w, h);

        // Text Background
        Shape backgroundText = new RoundRectangle2D.Double(
                (w - strWidth) / 2 - 10,
                h/2+verticalTextOffset-15,
                strWidth + 20,
                20,
                arc,arc
        );
        g2d.setColor(LIGHT_GRAY);
        g2d.fill(backgroundText);
        g2d.setColor(BLACK);
        g2d.draw(backgroundText);

        // Draw the Loading icon if it should be drawn
        if(drawLoadingIcon) {

            // Loading Icon Background
            Shape backgroundIcon = new Ellipse2D.Double(
                    w/2 - iconImage.getIconWidth()/2 - iconRadius/2 - 1,
                    h/2 - iconImage.getIconHeight()/2 - iconRadius/2 + verticalIconOffset + 7,
                    iconImage.getIconWidth()+iconRadius,
                    iconImage.getIconHeight()+iconRadius
            );
            g2d.setColor(LIGHT_GRAY);
            g2d.fill(backgroundIcon);
            g2d.setColor(BLACK);
            g2d.draw(backgroundIcon);

            // Loading Icon
            g.drawImage(iconImage.getImage(), (w - iconImage.getIconWidth()) / 2, h / 2 + verticalIconOffset, this);
        }

        // Draw Text
        g.setColor(BLACK);
        g.setFont(g.getFont().deriveFont(Font.BOLD));
        g.drawString(text, (w - strWidth) / 2, h / 2 + verticalTextOffset);
    }

    public void setText(String text) {
        this.text = text;
        repaint();
    }

    public String getText() {
        return text;
    }
    
}
/**
 * Listen for all events that our check box is likely to be
 * interested
 */
class CBListener extends MouseInputAdapter {

    Toolkit toolkit;
    CWLoadingGlass glassPane;
    Container contentPane;

    public CBListener(
            CWLoadingGlass glassPane, Container contentPane) {
        toolkit = Toolkit.getDefaultToolkit();
        this.glassPane = glassPane;
        this.contentPane = contentPane;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        redispatchMouseEvent(e, false);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        redispatchMouseEvent(e, false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        redispatchMouseEvent(e, false);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        redispatchMouseEvent(e, false);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        redispatchMouseEvent(e, false);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        redispatchMouseEvent(e, false);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        redispatchMouseEvent(e, true);
    }

    //A basic implementation of redispatching events.
    private void redispatchMouseEvent(MouseEvent e,
            boolean repaint) {
        Point glassPanePoint = e.getPoint();
        Container container = contentPane;
        Point containerPoint = SwingUtilities.convertPoint(
                glassPane,
                glassPanePoint,
                contentPane);
        if (containerPoint.y < 0) { //we're not in the content pane
//            if (containerPoint.y + menuBar.getHeight() >= 0) {
//                //The mouse event is over the menu bar.
//                //Could handle specially.
//            } else {
//                //The mouse event is over non-system window
//                //decorations, such as the ones provided by
//                //the Java look and feel.
//                //Could handle specially.
//            }
        } else {
            //The mouse event is probably over the content pane.
            //Find out exactly which component it's over.
            Component component =
                    SwingUtilities.getDeepestComponentAt(
                    container,
                    containerPoint.x,
                    containerPoint.y);

//            if ((component != null)
//                && (component.equals(liveButton))) {
//                //Forward events over the check box.
//                Point componentPoint = SwingUtilities.convertPoint(
//                                            glassPane,
//                                            glassPanePoint,
//                                            component);
//                component.dispatchEvent(new MouseEvent(component,
//                                                     e.getID(),
//                                                     e.getWhen(),
//                                                     e.getModifiers(),
//                                                     componentPoint.x,
//                                                     componentPoint.y,
//                                                     e.getClickCount(),
//                                                     e.isPopupTrigger()));
//            }
        }

        //Update the glass pane if requested.
        if (repaint) {
            glassPane.repaint();
        }
    }
}