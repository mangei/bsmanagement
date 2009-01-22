package cw.boardingschoolmanagement.manager;

import cw.boardingschoolmanagement.app.ApplicationListener;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.app.BoardingSchoolManagement;
import cw.boardingschoolmanagement.gui.LoadingGlass;
import cw.boardingschoolmanagement.exception.NotInitializedException;
import cw.boardingschoolmanagement.gui.component.JHeader;
import cw.boardingschoolmanagement.gui.component.JMenuPanel;
import cw.boardingschoolmanagement.gui.component.StatusBar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

/**
 * Manages the Graphic User Interface (GUI) of the application.
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public class GUIManager
extends JFrame
{
    private static GUIManager instance;
            
    /**
     * Eine Referenz auf die Haupt-Kompenente die angezeigt wird
     */
    private JComponent shownComponent;
    private JScrollPane shownComponentScrollPane;
    private LoadingGlass glassPane;
    
    private LinkedList<JComponent> lastComponents;

    private JHeader header;
    private StatusBar statusbar;
    private JSplitPane splitPane;
    private JPanel mainView;

    /**
     * @param title Titel der Applikation
     * @param comp Komponente die am Start angezeigt wird
     */
    private GUIManager(String title) {
        super(title);

        setIconImage(CWUtils.loadImage("cw/boardingschoolmanagement/images/building.png"));
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                BoardingSchoolManagement.getInstance().close();
            }
        });

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT) {
            public final Color COLOR1 = new Color(215, 220, 228);
            public final Color COLOR2 = new Color(178, 187, 200);

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int x = getDividerLocation();
                int size = getDividerSize();
                g.setColor(COLOR1);
                g.drawLine(x, 0, x + size, 0);
                g.drawLine(x + size-1, 0, x + size-1, getHeight());
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
        shownComponent = null;
        lastComponents = new LinkedList<JComponent>();

        // View on the right side
        mainView = new JPanel(new BorderLayout());

        // Rahmen geben
//        shownComponent.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, new Color(178,187,200)));
        
        // GlassPane for the loading screen
        setGlassPane(glassPane = new LoadingGlass(rootPane));
        
        // Um den richtigen Mauszeiger anzuzeigen, da beim Look'n&Feel nach dem 
        // ändern der Größe ein falscher Cursor angezeigt wird.
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                GUIManager.this.setCursor(null);
            }
        });

        
        generateGUI();


        // Set the size of the window
        setSize(new Dimension(
                Integer.parseInt(PropertiesManager.getProperty("application.gui.width", "800")),
                Integer.parseInt(PropertiesManager.getProperty("application.gui.height", "650"))
        ));

        // Set the state of the window
        setExtendedState(Integer.parseInt(PropertiesManager.getProperty("application.gui.extendedState", Integer.toString(JFrame.NORMAL))));

        // Save the size if the application closes and the window is not maximized
        BoardingSchoolManagement.getInstance().addApplicationListener(new ApplicationListener() {

            public void applicationClosing() {
                if(GUIManager.this.getExtendedState() == JFrame.MAXIMIZED_BOTH || GUIManager.this.getExtendedState() != JFrame.ICONIFIED) {
                    PropertiesManager.setProperty("application.gui.extendedState", Integer.toString(GUIManager.this.getExtendedState()));
                }
                
                if(GUIManager.this.getExtendedState() != JFrame.MAXIMIZED_BOTH) {
                    PropertiesManager.setProperty("application.gui.width", Integer.toString(GUIManager.this.getWidth()));
                    PropertiesManager.setProperty("application.gui.height", Integer.toString(GUIManager.this.getHeight()));
                }
            }
        });

        // Center the window
        CWUtils.centerWindow(this);
    }
    
    public static GUIManager getInstance() {
        if(instance == null) {
            throw new NotInitializedException();
        }
        return instance;
    }
    
    /**
     * Initializes the GUIManager
     * @param title Title of the application
     */
    public static void initGUIManager(String title) {
        if(instance == null) {
            instance = new GUIManager(title);
        }
    }

    private static JScrollPane createScrollPane(JComponent shownComponent) {
        JScrollPane p = new JScrollPane(shownComponent);
        p.setBorder(new EmptyBorder(0,0,0,0));
        return p;
    }
    
    /**
     * Generiert die Oberfläche
     */
    private void generateGUI() {
        setLayout(new BorderLayout());
        add(header = new JHeader(), BorderLayout.NORTH);

        splitPane.add(createSidePanel(), JSplitPane.LEFT);
//        splitPane.add(mainView, JSplitPane.RIGHT);

//        mainView.add(pathPanel, BorderLayout.NORTH);
//        mainView.add(shownComponentScrollPane = createScrollPane(shownComponent), BorderLayout.CENTER);
        splitPane.add(shownComponentScrollPane = createScrollPane(shownComponent), JSplitPane.RIGHT);

        add(splitPane, BorderLayout.CENTER);

//        add(createSidePanel(), BorderLayout.WEST);
//        add(shownComponentScrollPane = createScrollPane(shownComponent), BorderLayout.CENTER);

//        add(MenuManager.getToolBar(),BorderLayout.PAGE_START);
        add(statusbar = new StatusBar(), BorderLayout.SOUTH);
    }
    
    /**
     * Liefert das SidePanel -> ButtonBar
     * @return JComponent
     */
    private JComponent createSidePanel() {
        JMenuPanel sideBar = MenuManager.getSideMenu();

        sideBar.setPreferredSize(new Dimension(115,80));
        sideBar.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                e.getComponent().setPreferredSize(e.getComponent().getSize());
            }
        });
        return sideBar;
    }

    /**
     * Changes the shown component
     * @param comp New View
     */
    public static void changeView(JComponent comp) {
        changeView(comp, false);
    }
    
    /**
     * Changes the shown component
     * @param comp New View
     * @param saveOldView Whether the old view will be saved or not
     */
    public static void changeView(JComponent comp, boolean saveOldView) {
        changeView(comp, saveOldView, true);
    }

        /**
     * Changes the shown component
     * @param comp New View
     * @param saveOldView Whether the old view will be saved or not
     * @param deleteOldOnes Whether the old ones will be deleted or not
     */
    private static void changeView(JComponent comp, boolean saveOldView, boolean deleteOldOnes) {
        GUIManager gM = getInstance();

        // Wenn die alten nicht gespeichert werden sollen, diese löschen
        if(!saveOldView && deleteOldOnes) {
            removeAllLastViews();
        }
        
        // Nur ändern, wenn es nicht schon angezeigt wird
        if(gM.shownComponent == null || ! gM.shownComponent.equals(comp) ) {
            // Entfernen der alten Komponente
            
            if(gM.shownComponent != null && gM.shownComponentScrollPane != null) {
                gM.mainView.remove(gM.shownComponentScrollPane);
            }

            // Save old component?
            if(saveOldView/* && gM.shownComponent != null*/) {
                gM.lastComponents.push(gM.shownComponent);
            } else {
                // TODO manage Views in GUIManager
//                removeAllLastViews();
            }

            // Zuweisen der neuen Komponente
            gM.shownComponent = comp;
            
            // Rahmen geben
            gM.shownComponent.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, new Color(178,187,200)));
            
            // Neue Komponente einfügen
//            gM.mainView.add(gM.shownComponentScrollPane = createScrollPane(gM.shownComponent), BorderLayout.CENTER);
            gM.splitPane.add(gM.shownComponentScrollPane = createScrollPane(gM.shownComponent), JSplitPane.RIGHT);

//            gM.pathPanel.reloadPath(gM.lastComponents, gM.shownComponent);

            // Neu zeichnen
            gM.mainView.validate();
        }
    }

    /**
     * Shows the last compontent
     */
    public static void changeToLastView() {
        GUIManager gM = getInstance();
        if(!gM.lastComponents.isEmpty()) {
            changeView(gM.lastComponents.pop(), false, false);
        }
    }
    
    /**
     * Removes the last component, when it was saved
     */
    public static void removeLastView() {
        getInstance().lastComponents.pop();
    }


    /**
     * Removes all components
     */
    public static void removeAllLastViews() {
        getInstance().lastComponents.clear();
    }

    /**
     * Liefert die Statusleiste
     * @return StatusBar
     */
    public static StatusBar getStatusbar() {
        return getInstance().statusbar;
    }
    
    public static void setLoadingScreenVisible(boolean visible) {
        getInstance().glassPane.setVisible(visible);
    }
    
    public static void setLoadingScreenText(String text) {
        getInstance().glassPane.setText(text);
    }
    
    public JHeader getHeader() {
        return header;
    }

    public void lockMenu() {
        MenuManager.getSideMenu().lock();
    }

    public void unlockMenu() {
        MenuManager.getSideMenu().unlock();
    }
}
