package cw.boardingschoolmanagement.manager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.ejb.Ejb3Configuration;

import cw.boardingschoolmanagement.app.ClassPathHacker;
import cw.boardingschoolmanagement.exception.ManifestException;
import cw.boardingschoolmanagement.module.Module;
import cw.boardingschoolmanagement.persistence.AnnotatedClass;

/**
 * Manages all modules of the application
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public class ModuleManager {

    private static ModuleManager instance = null;

    private ModuleManager() {
    }

    /**
     * Returns an instance of the ModulManager
     * @return ModulManager
     */
    public static ModuleManager getInstance() {
        if (instance == null) {
            instance = new ModuleManager();
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
                    modulName = jarFile.getManifest().getMainAttributes().getValue("Modul-Name");
                    if(modulName == null) {
                        throw new ManifestException("Attribute 'Modul-Name' in Manifest of " + modulDirFile + " is missing.");
                    }
                    modulName = modulName.trim();

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
                    Logger.getLogger(ModuleManager.class.getName()).log(Level.SEVERE, null, ex);
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
                    // Das Jar in den Class-Path hinzufuegen
                    ClassPathHacker.addFile(checkedModulesList.get(i).getFile());

                    System.out.println("  " + checkedModulesList.get(i).getName());

                } catch (IOException ex) {
                    Logger.getLogger(ModuleManager.class.getName()).log(Level.SEVERE, null, ex);
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
        ServiceLoader<Module> modules = ServiceLoader.load(Module.class);
        for (Module m : modules) {
            m.init();
        }
    }

    /**
     * Regitsters all Annotated Classes
     * @param configuration AnnotationConfiguration for Hibernate
     */
    public static void registerAnnotationClasses(Ejb3Configuration configuration) {
        ServiceLoader<AnnotatedClass> annotatedClasses = ServiceLoader.load(AnnotatedClass.class);
        Iterator<AnnotatedClass> iterator = annotatedClasses.iterator();
        while (iterator.hasNext()) {

            AnnotatedClass next = null;
            try {
                next = iterator.next();

                if(next != null) {
                    System.out.println("registerAnnotatedClasses: " + next.getClass());
                    configuration.addAnnotatedClass(next.getClass());
                }
                
            } catch (ServiceConfigurationError e) {
                // If it is an abstract class or an interface

                String classStr = e.getMessage();

                // Get the className
                int from = classStr.indexOf("Provider ");
                classStr = classStr.subSequence(from + "Provider ".length(), classStr.length()).toString();
                int to   = classStr.indexOf(' ');
                classStr = classStr.subSequence(0, to).toString();

                try {
                    // Get the class
                    Class c = Class.forName(classStr);

                    System.out.println("registerAnnotatedClasses: " + c);
                    System.out.println("interface: " + Modifier.isAbstract(c.getModifiers()));
                    configuration.addAnnotatedClass(c);

                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
            }
            
        }
    }

    /**
     * Return all Extentions by a specificated extention class
     * @param specificExtention Class of the specificated extention
     * @return List of the specificated extention class
     */
    public static <T> List<T> getExtentions(Class<T> specificExtention) {

        System.out.println("getExtentions(" + specificExtention.getName() + "): ");

        List<T> spezExList = new ArrayList<T>();
        ServiceLoader<T> exList = (ServiceLoader<T>) ServiceLoader.load(specificExtention);

        // Run throu all extentions
        for (T ex : exList) {
            
            try {
                // Add it to the list
                spezExList.add((T) ex.getClass().newInstance());
                System.out.println("  " + ex.toString());
            } catch (InstantiationException ex1) {
                Logger.getLogger(ModuleManager.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (IllegalAccessException ex1) {
                Logger.getLogger(ModuleManager.class.getName()).log(Level.SEVERE, null, ex1);
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
