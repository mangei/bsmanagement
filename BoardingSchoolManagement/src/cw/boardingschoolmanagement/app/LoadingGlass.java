package cw.boardingschoolmanagement.app;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;


/**
 * Represents a glasspane which shows a loading icon and a text.
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public class LoadingGlass extends JComponent {

    private String text;
    ImageIcon img;

    public LoadingGlass(Container contentPane) {
        img = new ImageIcon("images/loadingicon.gif");
        text = "";
        setOpaque(false);

        CBListener listener = new CBListener(this, contentPane);
        addMouseListener(listener);
        addMouseMotionListener(listener);

    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(200, 200, 200, 100));
//        g.fillRect(getX(), getY(), getWidth(), getHeight());
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(img.getImage(), (getWidth() - img.getIconWidth()) / 2, getHeight() / 2 - 20, this);
        g.setColor(new Color(0, 0, 0, 0.5f));
        g.setFont(g.getFont().deriveFont(Font.BOLD));
        int strl = g.getFontMetrics().stringWidth(text);
        g.drawString(text, (getWidth() - strl) / 2, getHeight() / 2 + 20);
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
    LoadingGlass glassPane;
    Container contentPane;

    public CBListener(
            LoadingGlass glassPane, Container contentPane) {
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