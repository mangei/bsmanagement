package cw.boardingschoolmanagement.manager;

import cw.boardingschoolmanagement.app.ApplicationListener;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.app.BoardingSchoolManagement;
import cw.boardingschoolmanagement.gui.component.CWLoadingGlass;
import cw.boardingschoolmanagement.exception.NotInitializedException;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.component.CWHeader;
import cw.boardingschoolmanagement.gui.component.CWMenuPanel;
import cw.boardingschoolmanagement.gui.component.CWPathPanel;
import cw.boardingschoolmanagement.gui.component.CWStatusBar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ToolTipManager;
import javax.swing.border.EmptyBorder;
import org.jdesktop.application.SingleFrameApplication;

/**
 * Manages the Graphic User Interface (GUI) of the application.
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public class GUIManager {

    private static GUIManager instance;

    private static SingleFrameApplication application;

    /**
     * Eine Referenz auf die Haupt-Kompenente die angezeigt wird
     */
    private CWView shownView;
    private CWLoadingGlass glassPane;
    private LinkedList<CWView> lastViews;
    private CWHeader header;
    private CWStatusBar statusbar;
    private JSplitPane splitPane;
    private JPanel viewPanel;
    private JPanel componentView;
    private CWPathPanel pathPanel;
    private JFrame frame;

    public enum PathPanelPositions {
        NORTH, SOUTH
    }
    private PathPanelPositions pathPanelPosition;

    // Colors
    public static final Color BORDER_COLOR = new Color(215, 220, 228);

    /**
     * @param title Titel der Applikation
     * @param comp Komponente die am Start angezeigt wird
     */
    private GUIManager(String title) {

        application = new SingleFrameApplication() {
            @Override
            protected void startup() {
                // Do nothing
            }
        };

        frame = application.getMainFrame();

        frame.setTitle(title);

        frame.setIconImage(CWUtils.loadImage("cw/boardingschoolmanagement/images/building.png"));
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                BoardingSchoolManagement.getInstance().close();
            }
        });

        // The Panel of the Path
        pathPanel = new CWPathPanel();

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true) {

            public final Color COLOR1 = new Color(215, 220, 228);
            public final Color COLOR2 = new Color(178, 187, 200);

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int x = getDividerLocation();
                int size = getDividerSize();
                g.setColor(COLOR1);
                g.drawLine(x, 0, x + size, 0);
                g.drawLine(x + size - 1, 0, x + size - 1, getHeight());
            }
        };
        splitPane.setOpaque(false);
        splitPane.setDividerSize(5);

        // Set the divider location
        splitPane.setDividerLocation(Integer.parseInt(PropertiesManager.getProperty("application.gui.divider.location", "120")));

        // Save the divider location if it changes
        splitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                PropertiesManager.setProperty("application.gui.divider.location", Integer.toString(GUIManager.this.splitPane.getDividerLocation()));
            }
        });


        // Angezeigte Komponente
        shownView = null;
        lastViews = new LinkedList<CWView>();

        // View on the right side
        componentView = new JPanel(new BorderLayout());

        // Rahmen geben
//        shownComponent.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, new Color(178,187,200)));

        // GlassPane for the loading screen
        frame.setGlassPane(glassPane = new CWLoadingGlass(frame.getRootPane(), false));

        // Um den richtigen Mauszeiger anzuzeigen, da beim Look'n&Feel nach dem 
        // ändern der Größe ein falscher Cursor angezeigt wird.
        frame.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                frame.setCursor(null);
            }
        });

        pathPanelPosition = PathPanelPositions.valueOf(PropertiesManager.getProperty("configuration.general.pathPanelPosition"));


        generateGUI();


        // Set the size of the window
        frame.setSize(new Dimension(
                Integer.parseInt(PropertiesManager.getProperty("application.gui.width", "800")),
                Integer.parseInt(PropertiesManager.getProperty("application.gui.height", "650"))));

        // Set the state of the window
        frame.setExtendedState(Integer.parseInt(PropertiesManager.getProperty("application.gui.extendedState", Integer.toString(JFrame.NORMAL))));

        // Save the size if the application closes and the window is not maximized
        BoardingSchoolManagement.getInstance().addApplicationListener(new ApplicationListener() {

            public void applicationClosing() {
                if (frame.getExtendedState() == JFrame.MAXIMIZED_BOTH || frame.getExtendedState() != JFrame.ICONIFIED) {
                    PropertiesManager.setProperty("application.gui.extendedState", Integer.toString(frame.getExtendedState()));
                }

                if (frame.getExtendedState() != JFrame.MAXIMIZED_BOTH) {
                    PropertiesManager.setProperty("application.gui.width", Integer.toString(frame.getWidth()));
                    PropertiesManager.setProperty("application.gui.height", Integer.toString(frame.getHeight()));
                }
            }
        });

        // Set the dismissdelay for all tooltips very high
        ToolTipManager.sharedInstance().setDismissDelay(1000000);

        // Center the window
        CWUtils.centerWindow(frame);
    }

    public static GUIManager getInstance() {
        if (instance == null) {
            throw new NotInitializedException();
        }
        return instance;
    }

    /**
     * Initializes the GUIManager
     * @param title Title of the application
     */
    public static void initGUIManager(String title) {
        if (instance == null) {
            instance = new GUIManager(title);
        }
    }

    private static JScrollPane createScrollPane(JComponent shownComponent) {
        JScrollPane p = new JScrollPane(shownComponent);
        p.setBorder(new EmptyBorder(0, 0, 0, 0));
        return p;
    }

    /**
     * Generiert die Oberfläche
     */
    private void generateGUI() {
        frame.setLayout(new BorderLayout());
        frame.add(header = new CWHeader(), BorderLayout.NORTH);


        // Init ViewPanel
        viewPanel = new JPanel(new BorderLayout());

        // Init PathPanel
        switch(pathPanelPosition) {
            case NORTH: viewPanel.add(pathPanel, BorderLayout.NORTH); break;
            case SOUTH: viewPanel.add(pathPanel, BorderLayout.SOUTH); break;
        }
        viewPanel.setVisible(Boolean.parseBoolean(PropertiesManager.getProperty("configuration.general.pathPanelActive")));

        viewPanel.add(componentView, BorderLayout.CENTER);

        splitPane.add(createSidePanel(), JSplitPane.LEFT);
        splitPane.add(viewPanel, JSplitPane.RIGHT);

//        componentView.add(shownView, BorderLayout.CENTER);
//        splitPane.add(shownComponentScrollPane = createScrollPane(shownComponent), JSplitPane.RIGHT);

        frame.add(splitPane, BorderLayout.CENTER);

//        add(createSidePanel(), BorderLayout.WEST);
//        add(shownComponentScrollPane = createScrollPane(shownComponent), BorderLayout.CENTER);

//        add(MenuManager.getToolBar(),BorderLayout.PAGE_START);
        frame.add(statusbar = new CWStatusBar(), BorderLayout.SOUTH);
    }

    /**
     * Creates the SidePanel
     * @return JComponent
     */
    private JComponent createSidePanel() {
        CWMenuPanel sideBar = MenuManager.getSideMenu();

        sideBar.setPreferredSize(new Dimension(115, 80));
//        sideBar.addComponentListener(new ComponentAdapter() {
//
//            @Override
//            public void componentResized(ComponentEvent e) {
//                e.getComponent().setPreferredSize(e.getComponent().getSize());
//            }
//        });

        JScrollPane scrollPane = new JScrollPane(sideBar);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        return scrollPane;
    }

    public void setPathPanelPosition(PathPanelPositions position) {
        if(position != pathPanelPosition) {

            // Remove from the old position
            switch(pathPanelPosition) {
                case NORTH: viewPanel.remove(pathPanel); break;
                case SOUTH: viewPanel.remove(pathPanel); break;
            }

            // Add to the new position
            switch(position) {
                case NORTH: viewPanel.add(pathPanel, BorderLayout.NORTH); break;
                case SOUTH: viewPanel.add(pathPanel, BorderLayout.SOUTH); break;
            }

            pathPanelPosition = position;

            viewPanel.repaint();
        }
    }

    public void setPathPanelVisible(boolean visible) {
        pathPanel.setVisible(visible);
    }

    /**
     * Changes the shown view
     * @param view New View
     */
    public static void changeView(CWView view) {
        changeView(view, false);
    }

    /**
     * Changes the shown view
     * @param view New View
     * @param saveOldView Whether the old view will be saved or not
     */
    public static void changeView(CWView view, boolean saveOldView) {
//        changeView(comp, saveOldView, true);
        changeView2(view, saveOldView);
    }

    /**
     * Reloads the pathview
     */
    private void reloadPath() {
        pathPanel.reloadPath(lastViews, shownView);
    }

    private static void changeView2(CWView view, boolean saveOldView) {
        GUIManager gM = getInstance();

        // Prüfen ob es nicht leer ist.. zb beim 1. Mal
        if (gM.shownView != null) {

            // Nur ändern, wenn es nicht schon angezeigt wird
            if (gM.shownView.equals(view)) {
                return;
            }

            // Alte Speichern
            if (saveOldView) {
                // Alte Speichern
                gM.lastViews.push(gM.shownView);
            } else {
                // Die alten löschen, wenn sie nicht gespeichert werden sollen

                // Pop the old ones and dispose them if they are Disposable
                for(int i=0, l=gM.lastViews.size(); i<l; i++) {
                    CWView oldView = gM.lastViews.pop();
                    oldView.dispose();
                }

                // Dispose the current view
                gM.shownView.dispose();
                
//              not necessary
//                gM.lastComponents.clear();
            }

            // Von der Ansicht entfernen
            gM.componentView.removeAll();

        }

        // Neue Ansicht
        gM.shownView = view;

        // Hinzufügen
        gM.componentView.add(gM.shownView, BorderLayout.CENTER);

        gM.componentView.revalidate();
        gM.componentView.repaint();

        gM.reloadPath();
    }

//    /**
//     * Changes the shown component
//     * @param comp New View
//     * @param saveOldView Whether the old view will be saved or not
//     * @param deleteOldOnes Whether the old ones will be deleted or not
//     */
//    private static void changeView(JComponent comp, boolean saveOldView, boolean deleteOldOnes) {
//        GUIManager gM = getInstance();
//
//        // Wenn die alten nicht gespeichert werden sollen, diese löschen
//        if (!saveOldView && deleteOldOnes) {
//            removeAllLastViews();
//        }
//
//        // Nur ändern, wenn es nicht schon angezeigt wird
//        if (gM.shownView == null || !gM.shownView.equals(comp)) {
//            // Entfernen der alten Komponente
//
//            if (gM.shownView != null && gM.shownComponentScrollPane != null) {
//                gM.componentView.remove(gM.shownComponentScrollPane);
////                gM.splitPane.remove(gM.shownComponentScrollPane);
//            }
//
//            // Save old component?
//            if (saveOldView/* && gM.shownComponent != null*/) {
//                gM.lastViews.push(gM.shownView);
//            } else {
//                // TODO manage Views in GUIManager
////                removeAllLastViews();
//            }
//
//            // Zuweisen der neuen Komponente
//            gM.shownView = comp;
//
//            // Rahmen geben
//            gM.shownView.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, new Color(178, 187, 200)));
//
//            // Neue Komponente einfügen
//            gM.componentView.add(gM.shownComponentScrollPane = createScrollPane(gM.shownView), BorderLayout.CENTER);
////            gM.splitPane.add(gM.shownComponentScrollPane = createScrollPane(gM.shownComponent), JSplitPane.RIGHT);
//
////            gM.pathPanel.reloadPath(gM.lastComponents, gM.shownComponent);
//
//            // Neu zeichnen
//            gM.componentView.validate();
//            gM.componentView.repaint();
//        }
//    }

    /**
     * Shows the last compontent
     */
    public static void changeToLastView() {
        GUIManager gM = getInstance();
        if (!gM.lastViews.isEmpty()) {

            // Von der Ansicht entfernen
            gM.componentView.removeAll();

            gM.shownView.dispose();
            System.gc();

            // Neue Ansicht
            gM.shownView = gM.lastViews.pop();

            // Hinzufügen
            gM.componentView.add(gM.shownView, BorderLayout.CENTER);

            // Neu zeichnen lassen
            gM.componentView.validate();
            gM.componentView.repaint();

            // Pfadanzeige aktualisieren
            gM.reloadPath();
        }
    }
//
//    /**
//     * Shows the last compontent
//     */
//    public static void changeToLastView() {
//        GUIManager gM = getInstance();
//        if(!gM.lastComponents.isEmpty()) {
//            changeView(gM.lastComponents.pop(), false, false);
//        }
//    }

    /**
     * Removes the last component, when it was saved
     */
    public static void removeLastView() {
        CWView view = getInstance().lastViews.pop();
        view.dispose();
        System.gc();
    }

    /**
     * Removes all components
     */
    public static void removeAllLastViews() {
        LinkedList<CWView> views = getInstance().lastViews;

        for(CWView view : views) {
            view.dispose();
        }
        System.gc();

        getInstance().lastViews.clear();
    }

    /**
     * Liefert die Statusleiste
     * @return StatusBar
     */
    public static CWStatusBar getStatusbar() {
        return getInstance().statusbar;
    }

    public static void setLoadingScreenVisible(boolean visible) {
        getInstance().glassPane.setVisible(visible);
    }

    public static void setLoadingScreenText(String text) {
        getInstance().glassPane.setText(text);
    }

    public CWHeader getHeader() {
        return header;
    }

    private int lockCount = 0;

    public void lockMenu() {
        if(lockCount == 0) {
            MenuManager.getSideMenu().lock();
        }
        lockCount++;
    }

    public void unlockMenu() {
        if(lockCount == 1) {
            MenuManager.getSideMenu().unlock();
        }
        if(lockCount > 0) {
            lockCount--;
        }
    }

    public final JFrame getMainFrame() {
        return application.getMainFrame();
    }

}
