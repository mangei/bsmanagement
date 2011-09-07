package cw.boardingschoolmanagement.app;

import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.button.StandardButtonShaper;
import org.jvnet.substance.skin.SubstanceOfficeSilver2007LookAndFeel;
import org.jvnet.substance.utils.SubstanceConstants;

import cw.boardingschoolmanagement.gui.ConfigurationPresentationModel;
import cw.boardingschoolmanagement.gui.ConfigurationView;
import cw.boardingschoolmanagement.gui.HomePresentationModel;
import cw.boardingschoolmanagement.gui.HomeView;
import cw.boardingschoolmanagement.gui.component.CWMenuPanel;
import cw.boardingschoolmanagement.gui.component.SplashScreen;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.boardingschoolmanagement.manager.MenuManager;
import cw.boardingschoolmanagement.manager.ModulManager;
import cw.boardingschoolmanagement.manager.PropertiesManager;


/**
 * BoardingSchoolManagement - Mainprogramm. <br />
 * Hauptklasse des Programmes welches das Programm systematisch aufbaut, die
 * grafischen Elemente initalisiert, einzelne Module initalisiert und die
 * Konfiguration laed.
 * Implementiert als Singleton
 * 
 * @author Manuel Geier (CreativeWorkers)
 */
public class Application {

    private static Application instance;
    private static Logger logger = Logger.getLogger(Application.class.getName());
    private ApplicationListenerSupport applicationListenerSupport = new ApplicationListenerSupport();

    private static boolean applicationStarted = false;

    public static void main(String[] args) {
        Application.getInstance().start();
    }

    /**
     * Liefert die Instanz der Klasse BoardingShcoolManagement zurueck, bzw.
     * initalisiert sie.
     * @return BoardingSchoolManagement
     */
    public static Application getInstance() {
        if (instance == null) {
            instance = new Application();
        }
        return instance;
    }

    /**
     * Constructor
     */
    private Application() {
    }

    /**
     * Startet die Application, laed das look'n Feel, die Konfiguration , laed und initalisiert die Module
     * , laed und initalisiert die grafische Oberflaeche, stellt die Verbindung zur Datenbank her.
     *
     */
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
        // Load configuration settings
        ////////////////////////////////////////////////////////////////////
            ss.setText("Einstellungen geladen...");
            PropertiesManager.loadProperties();
            
        ////////////////////////////////////////////////////////////////////
        // Load logging settings
        ////////////////////////////////////////////////////////////////////
//            ss.setText("Logging wird gestartet...");
//            if(Boolean.parseBoolean(PropertiesManager.getProperty("application.logging", "true")) == true) {
//                try {
//                    String logDirectoryName = PropertiesManager.getProperty("application.logDirectory", "logs");
//                    File logDirectory = new File(logDirectoryName);
//                    if(!logDirectory.exists()) {
//                        logDirectory.mkdir();
//                    }
//
//                    String logFileName = PropertiesManager.getProperty("application.logFile", "log.txt");
//                    File logFile = new File(logFileName);
//                    if(!logFile.exists()) {
//                        try {
//                            logFile.createNewFile();
//                        } catch (IOException ex) {
//                            Logger.getLogger(BoardingSchoolManagement.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    }
//
//                    String completeFileName = "";
//                    if(!logDirectoryName.isEmpty()) {
//                        completeFileName = logDirectoryName + System.getProperty("file.separator");
//                    }
//                    completeFileName = completeFileName + logFileName;
//
//                    FileOutputStream fileOutputStream = new FileOutputStream(logFile);
//                    PrintStream printStream = new PrintStream(fileOutputStream);
//                    System.setOut(printStream);
//                    System.setErr(printStream);
//                } catch (FileNotFoundException ex) {
//                    java.util.logging.Logger.getLogger(BoardingSchoolManagement.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }

        ////////////////////////////////////////////////////////////////////
        // Load modules
        ////////////////////////////////////////////////////////////////////
            ss.setText("Module werden geladen...");
            ModulManager.loadModules();
            ModulManager.registerAnnotationClasses(CWEntityManager.getConfiguration());

        ////////////////////////////////////////////////////////////////////
        // Connect to database
        ////////////////////////////////////////////////////////////////////
            ss.setText("Datenbankverbindung herstellen...");
            CWEntityManager.init();

        ////////////////////////////////////////////////////////////////////
        // Load the GUI
        ////////////////////////////////////////////////////////////////////
            ss.setText("Oberflaeche wird geladen...");
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
            java.util.logging.Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

	/**
	 * Laed die grundlegenten Komponenten der GUI(Sidebar mit Startseite,
	 * Topmenue mit den Mennuepunkten: Beenden, Konfiguration).
	 *
	 */
    private void initGUI() {
        CWMenuPanel sideMenu = MenuManager.getSideMenu();

        sideMenu.addCategory("Startseite", "home", 10);
        sideMenu.addItem(new JButton(new AbstractAction(
                "Startseite", CWUtils.loadIcon("cw/boardingschoolmanagement/images/house.png")) {

            {
                putValue(Action.SHORT_DESCRIPTION, "Startseite anzeigen");
            }
            
            public void actionPerformed(ActionEvent e) {
//                GUIManager.setLoadingScreenText("Startseite werden geladen...");
//                GUIManager.setLoadingScreenVisible(true);
                GUIManager.changeView(new HomeView(new HomePresentationModel()));
//                GUIManager.setLoadingScreenVisible(false);
            }
        }), "home", true);


        // Add an closeButton to the HeaderMenu
        GUIManager.getInstance().getHeader().addHeaderMenuItem(new AbstractAction(
                "Beenden",
                CWUtils.loadIcon("cw/boardingschoolmanagement/images/exit.png"
                )) {

            public void actionPerformed(ActionEvent e) {
                Application.getInstance().close();
            }
        });

        GUIManager.getInstance().getHeader().addHeaderMenuItem(new AbstractAction(
                "Einstellungen", CWUtils.loadIcon("cw/boardingschoolmanagement/images/configuration.png")) {

            {
                putValue(Action.SHORT_DESCRIPTION, "Einstellungen");
            }

            public void actionPerformed(ActionEvent e) {
                GUIManager.setLoadingScreenText("Einstellungen...");
                GUIManager.setLoadingScreenVisible(true);

                final ConfigurationPresentationModel model = new ConfigurationPresentationModel();
                ConfigurationView view = new ConfigurationView(model);

                
                final JDialog d = new JDialog(GUIManager.getInstance().getMainFrame(), true);
                d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                d.setTitle(view.getName());
                d.add(view);
                d.pack();
                CWUtils.centerWindow(d, GUIManager.getInstance().getMainFrame());

                model.addButtonListener(new ButtonListener() {
                    public void buttonPressed(ButtonEvent evt) {
                        model.removeButtonListener(this);
                        // TODO Testen ob Dialog noch disposed werden muss
                        d.setVisible(false);
                    }
                });

                d.setVisible(true);

                view.dispose();
                d.dispose();

                GUIManager.setLoadingScreenVisible(false);
            }
        });
//
//        // Add an About-Button to the HeaderMenu
//        GUIManager.getInstance().getHeader().addHeaderMenuItem(new AbstractAction(
//                "Ueber",
//                CWUtils.loadIcon("cw/boardingschoolmanagement/images/about.png")
//                ) {
//
//            public void actionPerformed(ActionEvent e) {
//                BoardingSchoolManagement.getInstance().close();
//            }
//        });
    }

    /**
     * Schießt die Anwendung, speichert die Einstellung und trennt die
     * Datenbankverbindung.
     */
    public void close() {
        // Close only, if the application has started
        if(applicationStarted == false) {
            return;
        }

        // Check if the user really wants to close the application
        String[] options = {
            "Minimieren", "Beenden", "Abbrechen"
        };

        int opt = JOptionPane.showOptionDialog(
                GUIManager.getInstance().getMainFrame(),
                "Wollen Sie die Internatsverwaltung wirklich beenden?",
                "Beenden",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                options,
                "Beenden"
        );

        // If he doesn't want, do nothing
        if(opt == 0) {
            // Hide the application
            GUIManager.getInstance().hideMainFrameToTray();
            GUIManager.getInstance().getTray().showDisplayInfoMessage("Minimiert", "Die Internatsverwaltung wurde minimiert.");
            return;
        } else if(opt == 1) {
            // Do nothing and continue closing the application
        } else {
            return;
        }
        
        // Fire the Listeners
        applicationListenerSupport.fireApplicationClosing();

        // Save the properties
        PropertiesManager.saveProperties();

        // Close hibernate
        CWEntityManager.close();

        // Dispose the GUI
        GUIManager.getInstance().getMainFrame().dispose();

        // Exit the application
        System.exit(0);
    }

    /**
     * Entfernen der ApplicationListener
     * @param applicationListener Aktionlistern eines bestimmten Swing-Objekt
     */
    public void removeApplicationListener(ApplicationListener applicationListener) {
        applicationListenerSupport.removeApplicationListener(applicationListener);
    }
    /**
     * Hinzufuegen der ApplicationListener
     * @param applicationListener Aktionlistern eines bestimmten Swing-Objekt
     */
    public void addApplicationListener(ApplicationListener applicationListener) {
        applicationListenerSupport.addApplicationListener(applicationListener);
    }

}
