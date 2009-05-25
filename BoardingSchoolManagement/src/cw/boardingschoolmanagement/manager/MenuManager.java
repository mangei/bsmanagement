package cw.boardingschoolmanagement.manager;

import cw.boardingschoolmanagement.gui.component.CWMenuPanel;
import javax.swing.AbstractAction;

/**
 * Manages the main menues in the application
 * 
 * @author Manuel Geier (CreativeWorkers)
 */
public class MenuManager {
    
    /**
     * Instance of the MenuManagers
     */
    private static MenuManager instance = null;
    
    /**
     * Sidemenu
     */
    private CWMenuPanel  sideMenu;
    
    
    private MenuManager() {
        sideMenu = new CWMenuPanel();
    }
    
    /**
     * Returns an instance of the MenuManager
     * @return MenuManager
     */
    public static MenuManager getInstance() {
        if(instance == null) {
            instance = new MenuManager();
        }
        return instance;
    }
    
    /**
     * Returns the SideMenu
     * @return JMenuPanel
     */
    public static CWMenuPanel getSideMenu() {
        MenuManager mM = getInstance();
        return mM.sideMenu;
    }

    /**
     * Adds an Button to the Header Menu
     */
    public static void addHeaderMenuItem(AbstractAction action) {
        GUIManager.getInstance().getHeader().addHeaderMenuItem(action);
    }
    
}
