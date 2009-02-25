package cw.boardingschoolmanagement.app;

import cw.boardingschoolmanagement.gui.component.SplashScreen;
import cw.boardingschoolmanagement.gui.HomeView;
import cw.boardingschoolmanagement.gui.HomePresentationModel;
import cw.boardingschoolmanagement.gui.component.JMenuPanel;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.boardingschoolmanagement.manager.MenuManager;
import cw.boardingschoolmanagement.manager.ModulManager;
import cw.boardingschoolmanagement.manager.PropertiesManager;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.button.StandardButtonShaper;
import org.jvnet.substance.skin.SubstanceOfficeSilver2007LookAndFeel;
import org.jvnet.substance.utils.SubstanceConstants;

/**
 * BoardingSchoolManagement - Mainprogramm. <br />
 * (Implementiert als Singleton)
 * 
 * @author Manuel Geier (CreativeWorkers)
 */
public class BoardingSchoolManagement {

    private static BoardingSchoolManagement instance;
    private static Logger logger = Logger.getLogger(BoardingSchoolManagement.class);
    private ApplicationListenerSupport applicationListenerSupport = new ApplicationListenerSupport();

    private static boolean applicationStarted = false;

    public static void main(String[] args) {
        BoardingSchoolManagement.getInstance().start();
    }

    /**
     * Get an instance of the BoardingSchoolManagement
     * @return BoardingSchoolManagement
     */
    public static BoardingSchoolManagement getInstance() {
        if (instance == null) {
            instance = new BoardingSchoolManagement();
        }
        return instance;
    }

    private BoardingSchoolManagement() {
    }

    private void start() {
        // Start the application, if it hadn't been started yet
        if(applicationStarted == true) {
            return;
        }
        applicationStarted = true;

        SplashScreen ss = new SplashScreen(CWUtils.loadImage("cw/boardingschoolmanagement/images/SplashScreen.png"), 440, 298);
        ss.start();
        ss.setText("Initialisieren...");


//        new Thread(new Runnable() {
//            public void run() {
//                while(true) {
//                    try {
//                        Runtime.getRuntime().gc();
//                        Thread.sleep(1000);
//                    } catch (InterruptedException ex) {
//                        java.util.logging.Logger.getLogger(BoardingSchoolManagement.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            }
//        }).start();

        try {
        ////////////////////////////////////////////////////////////////////
        // Look'n'Feel
        ////////////////////////////////////////////////////////////////////
            
            UIManager.put("Synthetica.window.decoration", Boolean.FALSE);
            UIManager.put("Synthetica.table.useSynthHeaderRenderer", Boolean.FALSE);
            UIManager.put("Synthetica.textField.border.opaqueBackground", Boolean.TRUE);
            UIManager.put("Synthetica.animation.enabled", Boolean.FALSE);

//            UIManager.setLookAndFeel(new SyntheticaStandardLookAndFeel());
//            UIManager.setLookAndFeel(new SyntheticaSimple2DLookAndFeel());

//            UIManager.setLookAndFeel(new MetalLookAndFeel());
            
            UIManager.setLookAndFeel(new SubstanceOfficeSilver2007LookAndFeel());

            // Properties for the LnF
            // The properties are listed here: https://substance.dev.java.net/docs/clientprops/all.html
            UIManager.put(SubstanceLookAndFeel.BUTTON_NO_MIN_SIZE_PROPERTY, Boolean.TRUE);
            UIManager.put(SubstanceLookAndFeel.TABBED_PANE_CONTENT_BORDER_KIND, SubstanceConstants.TabContentPaneBorderKind.SINGLE_FULL);
//            UIManager.put(SubstanceLookAndFeel.CORNER_RADIUS, Float.valueOf(5.0f));
            UIManager.put(SubstanceLookAndFeel.BUTTON_SHAPER_PROPERTY, new StandardButtonShaper());

            // Change the title of the frames and dialogs
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
            System.setProperty("sun.awt.noerasebackground", "true");

            
        ////////////////////////////////////////////////////////////////////
        // Load logging settings
        ////////////////////////////////////////////////////////////////////
            ss.setText("Logging wird gestartet...");
            PropertyConfigurator.configureAndWatch( "log4j.properties", 60*1000 );

        ////////////////////////////////////////////////////////////////////
        // Load configuration settings
        ////////////////////////////////////////////////////////////////////
            ss.setText("Einstellungen geladen...");
            PropertiesManager.loadProperties();

        ////////////////////////////////////////////////////////////////////
        // Load modules
        ////////////////////////////////////////////////////////////////////
            ss.setText("Module werden geladen...");
            ModulManager.loadModules();
            ModulManager.registerAnnotationClasses(HibernateUtil.getConfiguration());

        ////////////////////////////////////////////////////////////////////
        // Connect to database
        ////////////////////////////////////////////////////////////////////
            ss.setText("Datenbankverbindung herstellen...");
            HibernateUtil.configure();
            Logger.getLogger(BoardingSchoolManagement.class).debug("Datenbankverbindung herstellen...");

        ////////////////////////////////////////////////////////////////////
        // Load the GUI
        ////////////////////////////////////////////////////////////////////
            ss.setText("Oberfläche wird geladen...");
            GUIManager.initGUIManager("Internatsverwaltung");

        ////////////////////////////////////////////////////////////////////
        // Initialize the standard GUI
        ////////////////////////////////////////////////////////////////////
            initGUI();

        ////////////////////////////////////////////////////////////////////
        // Initialize modules
        ////////////////////////////////////////////////////////////////////
            ss.setText("Module werden initialisiert...");
            ModulManager.initModules();

        ////////////////////////////////////////////////////////////////////
        // Close the splashscreen and show the application
        ////////////////////////////////////////////////////////////////////
            ss.close();
            MenuManager.getSideMenu().loadStartItem();
            GUIManager.getInstance().getMainFrame().setVisible(true);

        } catch (UnsupportedLookAndFeelException ex) {
            logger.error(null, ex);
        }
    }

    private void initGUI() {
        JMenuPanel sideMenu = MenuManager.getSideMenu();

        sideMenu.addCategory("Startseite", "home", 10);
        sideMenu.addItem(new JButton(new AbstractAction(
                "Startseite", CWUtils.loadIcon("cw/boardingschoolmanagement/images/house.png")) {

            {
                putValue(Action.SHORT_DESCRIPTION, "Startseite anzeigen");
            }
            
            public void actionPerformed(ActionEvent e) {
//                GUIManager.setLoadingScreenText("Startseite werden geladen...");
//                GUIManager.setLoadingScreenVisible(true);
                GUIManager.changeView(new HomeView(new HomePresentationModel()).buildPanel());
//                GUIManager.setLoadingScreenVisible(false);
            }
        }), "home", true);


        // Add an closeButton to the HeaderMenu
        GUIManager.getInstance().getHeader().addHeaderMenuItem(new AbstractAction(
                "Beenden",
                CWUtils.loadIcon("cw/boardingschoolmanagement/images/cross_16.png"
                )) {

            public void actionPerformed(ActionEvent e) {
                BoardingSchoolManagement.getInstance().close();
            }
        });

        GUIManager.getInstance().getHeader().addHeaderMenuItem(new AbstractAction(
                "Einstellungen", CWUtils.loadIcon("cw/boardingschoolmanagement/images/wrench_16.png")) {

            {
                putValue(Action.SHORT_DESCRIPTION, "Einstellungen");
            }

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Einstellungen...");
            }
        });
    }

    /**
     * Close the application
     */
    public void close() {
        // Close only, if the application has started
        if(applicationStarted == false) {
            return;
        }

        // Check if the user really wants to close the application
        int opt = JOptionPane.showConfirmDialog(
                GUIManager.getInstance().getMainFrame(),
                "Internatsverwaltung wirklich beenden?",
                "Beenden",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        // If he doesn't want, do nothing
        if(opt != JOptionPane.OK_OPTION) {
            return;
        }

        // Fire the Listeners
        applicationListenerSupport.fireApplicationClosing();

        // Save the properties
        PropertiesManager.saveProperties();

        // Close hibernate
        HibernateUtil.close();

        // Dispose the GUI
        GUIManager.getInstance().getMainFrame().dispose();

        // Exit the application
        System.exit(0);
    }

    public void removeApplicationListener(ApplicationListener applicationListener) {
        applicationListenerSupport.removeApplicationListener(applicationListener);
    }

    public void addApplicationListener(ApplicationListener applicationListener) {
        applicationListenerSupport.addApplicationListener(applicationListener);
    }

}
