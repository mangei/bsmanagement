package cw.boardingschoolmanagement.gui.component;

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.TrayIcon;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;

/**
 * Ein Swing TrayIcon welches die regulären AWT-Komponenten überschreibt und
 * durch neue Swing-Komponenten ersetzt.
 */
public class CWTray
{
    private JDialog trayParent;
    private JPopupMenu popupMenu;
    private TrayIcon trayIcon;
    private ActionListener leftClickListener, leftDoubleClickListener, rightClickListener;

    /**
     * Konstruktor
     * Erzeugt eine Dialogbox für das PopupMenu, einPopupMenu, das TrayIcon
     * und erstellt das finale SystemTray.
     *
     * @see quark.ui.tray.JTrayMenu
     * @see quark.ui.tray.JTrayImage
     */
    public CWTray(TrayIcon trayIcon)
    {
        this.trayIcon = trayIcon;

        popupMenu = new JPopupMenu();

        trayParent = new JDialog();
        trayParent.setSize(0, 0);
        trayParent.setUndecorated(true);
        trayParent.setAlwaysOnTop(true);
        trayParent.setVisible(false);

        initListeners();

        //ClassCastException fix
        Toolkit.getDefaultToolkit().getSystemEventQueue().push( new PopupFixQueue(popupMenu) );

//        SystemTray systemTray = SystemTray.getSystemTray();
//        try
//        {
//            systemTray.add(trayIcon);
//        }
//        catch (AWTException e)
//        {
//            e.printStackTrace();
//        }
    }

    /**
     * Setzt ein PopupMenu in die Dialogbox und überschreibt die aktuellen
     * Listenerklassen.
     *
     * @param trayMenu JPopupMenu
     */
    private void initListeners()
    {
        popupMenu.addPopupMenuListener(new PopupMenuListener()
        {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e)
            {
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e)
            {
                trayParent.setVisible(false);
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e)
            {
                trayParent.setVisible(false);
            }
        });

        //Listener registrieren
        trayIcon.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (SwingUtilities.isLeftMouseButton(e))
                {
                    if (e.getClickCount() == 1 && leftClickListener != null)
                        leftClickListener.actionPerformed(null);
                    else if (e.getClickCount() == 2 && leftDoubleClickListener != null)
                        leftDoubleClickListener.actionPerformed(null);
                }

                else if (SwingUtilities.isRightMouseButton(e))
                {
                    if (e.getClickCount() == 1 && rightClickListener != null)
                        rightClickListener.actionPerformed(null);
                    showPopup(e.getPoint());
                }
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                if (e.isPopupTrigger())
                {
                    if (SwingUtilities.isRightMouseButton(e) && rightClickListener != null)
                        rightClickListener.actionPerformed(null);
                    showPopup(e.getPoint());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                if (e.isPopupTrigger())
                {
                    if (SwingUtilities.isRightMouseButton(e) && rightClickListener != null)
                        rightClickListener.actionPerformed(null);
                    showPopup(e.getPoint());
                }
            }
        });
    }

    /**
     * Methode zum zeigen des PopupMenus.
     *
     * @param p Position des Listeners
     */
    private void showPopup(final Point p)
    {
        trayParent.setVisible(true);
        trayParent.toFront();

        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                Point p2 = computeDisplayPoint(p.x, p.y, popupMenu.getPreferredSize());
                popupMenu.show(trayParent, p2.x - trayParent.getLocation().x, p2.y - trayParent.getLocation().y);
            };
        });
    }

    /**
     * Berechnet die optimale Position für die Anzeige des PopupMenus.
     */
    private Point computeDisplayPoint(int x, int y, Dimension dim)
    {
        if (x - dim.width > 0)
            x -= dim.width;
        if (y - dim.height > 0)
            y -= dim.height;
        return new Point(x, y);
    }


    //BaloonTips
    //==========================================================================

    public void showDisplayMessage(String header, String message)
    {
        trayIcon.displayMessage( header,
                                 message,
                                 TrayIcon.MessageType.NONE );
    }

    public void showDisplayErrorMessage(String header, String message)
    {
        trayIcon.displayMessage( header,
                                 message,
                                 TrayIcon.MessageType.ERROR );
    }

    public void showDisplayInfoMessage(String header, String message)
    {
        trayIcon.displayMessage( header,
                                 message,
                                 TrayIcon.MessageType.INFO );
    }

    public void showDisplayWarningMessage(String header, String message)
    {
        trayIcon.displayMessage( header,
                                 message,
                                 TrayIcon.MessageType.WARNING );
    }

    public JPopupMenu getPopupMenu() {
        return popupMenu;
    }

    public void setLeftClickListener(ActionListener leftClickListener) {
        this.leftClickListener = leftClickListener;
    }

    public void setLeftDoubleClickListener(ActionListener leftDoubleClickListener) {
        this.leftDoubleClickListener = leftDoubleClickListener;
    }

    public void setRightClickListener(ActionListener rightClickListener) {
        this.rightClickListener = rightClickListener;
    }

    //Sonstiges
    //==========================================================================

    /**
     * ClassCastException FIX
     */
    private class PopupFixQueue extends EventQueue
    {
        private JPopupMenu popup;

        public PopupFixQueue(JPopupMenu popup)
        {
            this.popup = popup;
        }

        protected void dispatchEvent(AWTEvent event)
        {
            try
            {
                super.dispatchEvent(event);
            }
            catch (Exception ex)
            {
                if (event.getSource() instanceof TrayIcon)
                {
                    popup.setVisible(false);
                }

                // Wenn Exceptions aufgetaucht sind, diese auch ausgeben.
                ex.printStackTrace();
            }
        }
    }
}