package cw.boardingschoolmanagement.manager;

import cw.boardingschoolmanagement.app.ClassPathHacker;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import cw.boardingschoolmanagement.extentions.interfaces.Extention;
import cw.boardingschoolmanagement.interfaces.Modul;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.ejb.Ejb3Configuration;

/**
 * Manages all modules of the application
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public class ModulManager {

    private static ModulManager instance = null;

    private ModulManager() {
    }

    /**
     * Returns an instance of the ModulManager
     * @return ModulManager
     */
    public static ModulManager getInstance() {
        if (instance == null) {
            instance = new ModulManager();
        }
        return instance;
    }

    /**
     * Loads all modules
     */
    public static void loadModules() {
        JarFile jarFile;
        String modulDependencies;
        List<ModulWithDependencies> modulesList = new ArrayList<ModulWithDependencies>();
        List<ModulWithDependencies> checkedModulesList = new ArrayList<ModulWithDependencies>();
        ModulWithDependencies modulWithDependencies;
        String modulName;

        try {

            File modulesDir = new File(PropertiesManager.getProperty("application.modulesDirectory", "modules"));

            // If the pluginsfolder doesn't exist, create it
            if (!modulesDir.exists()) {
                modulesDir.mkdir();
            }

            // Alle Modul-Ordner
            File[] modulDirFiles = modulesDir.listFiles();


            for (File modulDirFile : modulDirFiles) {

                // If the file is not a .jar-file continue
                if (!modulDirFile.getName().endsWith(".jar")) {
                    continue;
                }
                try {
                    // Load Jar-File
                    jarFile = new JarFile(modulDirFile.toString());

                    // Read Modul-Name
                    modulName = jarFile.getManifest().getMainAttributes().getValue("Modul-Name").trim();

                    System.out.println("Modul: " + modulName);

                    // Read Modul-Dependencies
                    modulDependencies = jarFile.getManifest().getMainAttributes().getValue("Modul-Dependencies");

                    // Create new ModulWithDependecies
                    modulWithDependencies = new ModulWithDependencies(modulName, modulDirFile);

                    // Wenn das Attribut fehlt
                    if (modulDependencies != null) {
                        System.out.println("Modul '" + modulDirFile.getName() + " requires: " + modulDependencies);

                        // Split the dependencies
                        String[] dependenciesArgs = modulDependencies.split(",");

                        // Add all depencencies and trim (remove spaces) them before
                        for (String dependency : dependenciesArgs) {
                            modulWithDependencies.addDependency(dependency.trim());
                        }

                    } else {
                        System.out.println("Modul '" + modulDirFile.getName() + " requires nothing");
                    }

                    modulesList.add(modulWithDependencies);

                } catch (IOException ex) {
                    Logger.getLogger(ModulManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            // Check the dependencies
            List<String> dependencies;
            boolean inList;
            for (int i = 0, l = modulesList.size(); i < l; i++) {

                // Modul
                modulWithDependencies = modulesList.get(i);

                // Load the dependencies
                dependencies = modulWithDependencies.dependencies;

                // If there are no dependencies, add it immediatly
                if (dependencies.size() == 0) {
                    checkedModulesList.add(modulWithDependencies);
                }

                // Else check the dependencies
                else {

                    // Check them
                    for (int j = 0, k = dependencies.size(); j < k; j++) {

                        // Check if the dependency is in the list, add it to the loadModulList
                        inList = false;

                        for (int h = 0, m = modulesList.size(); h < m; h++) {

                            if (modulesList.get(h).getName().equals(dependencies.get(j))) {
                                inList = true;
                                break;
                            }
                        }

                        if (inList) {
                            checkedModulesList.add(modulWithDependencies);
                        }
                    }
                }
            }

            System.out.println("Checked Modules: ");

            // Load all check modules
            for (int i = 0, l = checkedModulesList.size(); i < l; i++) {
                try {
                    // Das Jar in den Class-Path hinzufügen
                    ClassPathHacker.addFile(checkedModulesList.get(i).getFile());

                    System.out.println("  " + checkedModulesList.get(i).getName());

                } catch (IOException ex) {
                    Logger.getLogger(ModulManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } catch (UnsupportedOperationException unsupportedOperationException) {
            unsupportedOperationException.printStackTrace();
        }
    }

    /**
     * Initializes all modules and calls the init-method
     */
    public static void initModules() {
        ServiceLoader<Modul> modules = ServiceLoader.load(Modul.class);
        for (Modul m : modules) {
            m.init();
        }
    }

    /**
     * Regitsters all Annotated Classes
     * @param configuration AnnotationConfiguration for Hibernate
     */
    public static void registerAnnotationClasses(Ejb3Configuration configuration) {
        ServiceLoader<AnnotatedClass> annotatedClasses = ServiceLoader.load(AnnotatedClass.class);
        for (AnnotatedClass a : annotatedClasses) {
            configuration.addAnnotatedClass(a.getClass());
        }
    }

    /**
     * Return all Extentions by a specificated extention class
     * @param specificExtention Class of the specificated extention
     * @return List of the specificated extention class
     */
    public static List<? extends Extention> getExtentions(Class specificExtention) {

        System.out.println("getExtentions(" + specificExtention.getName() + "): ");

        List<Extention> spezExList = new ArrayList<Extention>();
        ServiceLoader<Extention> exList = ServiceLoader.load(specificExtention);

        // Run throu all extentions
        for (Extention ex : exList) {
            
            try {
                // Add it to the list
                spezExList.add(ex.getClass().newInstance());
                System.out.println("  " + ex.toString());
            } catch (InstantiationException ex1) {
                Logger.getLogger(ModulManager.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (IllegalAccessException ex1) {
                Logger.getLogger(ModulManager.class.getName()).log(Level.SEVERE, null, ex1);
            }

        }

        return spezExList;
    }

    static private class ModulWithDependencies {

        private String modulname;
        private File modulfile;
        private List<String> dependencies;

        public ModulWithDependencies(String modulname, File modulfile) {
            this.modulname = modulname;
            this.modulfile = modulfile;
            this.dependencies = new ArrayList<String>();
        }

        public void addDependency(String dependency) {
            dependencies.add(dependency);
        }

        public String getName() {
            return modulname;
        }

        public File getFile() {
            return modulfile;
        }

        public List<String> getDependencies() {
            return dependencies;
        }
    }
}
