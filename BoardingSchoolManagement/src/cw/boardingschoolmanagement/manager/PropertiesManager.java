package cw.boardingschoolmanagement.manager;

import cw.boardingschoolmanagement.exception.PropertyElementDoesNotExistException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages the properties of the application.
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public class PropertiesManager {

    private static final String PROPERTIES_FILENAME = "config.properties";
    private static PropertiesManager instance;
    private Properties properties;

    private PropertiesManager() {
        properties = new Properties();
    }

    private static PropertiesManager getInstance() {
        if (instance == null) {
            instance = new PropertiesManager();
        }
        return instance;
    }

    /**
     * Returns the value of a property name
     * @param name Name of the property
     * @return Property value
     */
    public static String getProperty(String name) {
        PropertiesManager pM = getInstance();

        String property = pM.properties.getProperty(name);

        return property;
    }

    /**
     * Returns the value of a property name
     * @param name Name of the property
     * @param defaultValue Default value if the proptery is not found
     * @return Property value
     */
    public static String getProperty(String name, String defaultValue) {
        PropertiesManager pM = getInstance();

        if(containsProperty(name)) {
            return pM.properties.getProperty(name);
        } else {
            pM.properties.setProperty(name, defaultValue);
            return defaultValue;
        }
    }

    /**
     * Sets an property. If the property doesn't exist, it will be created.
     * @param name Name of the property
     * @param value Value of the property
     */
    public static void setProperty(String name, String value) {
        PropertiesManager pM = getInstance();
        pM.properties.setProperty(name, value);
    }

    /**
     * Check if the property exists or not
     * @param name Name of the property
     * @return Boolean
     */
    public static boolean containsProperty(String name) {
        PropertiesManager pM = getInstance();
        return pM.properties.containsKey(name);
    }

    /**
     * Loads all the properties from the properties file
     */
    public static void loadProperties() {
        PropertiesManager pM = getInstance();

        FileInputStream pIStream = null;
        try {
            File pFile = new File(PROPERTIES_FILENAME);
            if(!pFile.exists()) {
                saveProperties();
            }
            pIStream = new FileInputStream(pFile);
            pM.properties.load(pIStream);
        } catch (IOException ex) {
            Logger.getLogger(PropertiesManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (pIStream != null) {
                try {
                    pIStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(PropertiesManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Stores all properties to the properties file
     */
    public static void saveProperties() {
        PropertiesManager pM = getInstance();
        
        FileOutputStream pOStream = null;
        try {
            File pFile = new File(PROPERTIES_FILENAME);
            if(!pFile.exists()) {
                pFile.createNewFile();
            }
            pOStream = new FileOutputStream(pFile);
            pM.properties.store(pOStream, "Properties");
        } catch (IOException ex) {
            Logger.getLogger(PropertiesManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (pOStream != null) {
                try {
                    pOStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(PropertiesManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
