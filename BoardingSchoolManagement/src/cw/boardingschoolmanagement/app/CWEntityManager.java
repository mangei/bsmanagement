package cw.boardingschoolmanagement.app;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.swing.JOptionPane;

import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.loader.custom.EntityFetchReturn;

public class CWEntityManager {

	private static EntityManagerFactory entityManagerFactory;
    private static Ejb3Configuration configuration;
	
    static {

        configuration = new Ejb3Configuration().

            // HSQLDB
//            setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect").
//            setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver").
//            setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:employeemanagement").
//            setProperty("hibernate.connection.username", "sa").
//            setProperty("hibernate.connection.password", "").

            // MYSQL
//            setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect").
//            setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver").
//            setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/internat").
//            setProperty("hibernate.connection.username", "root").
//            setProperty("hibernate.connection.password", "").

            // H2
            setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect").
            setProperty("hibernate.connection.driver_class", "org.h2.Driver").
            setProperty("hibernate.connection.url", "jdbc:h2:database/internat;AUTO_SERVER=TRUE ").
            setProperty("hibernate.connection.username", "sa").
            setProperty("hibernate.connection.password", "").

//            setProperty("hibernate.connection.pool_size", "1").
            setProperty("hibernate.connection.autocommit", "false").
//            setProperty("hibernate.cache.provider_class", "org.hibernate.cache.HashtableCacheProvider").
            setProperty("hibernate.hbm2ddl.auto", "update").
            setProperty("hibernate.show_sql", "true");
//            setProperty("hibernate.format_sql", "true").
//            setProperty("hibernate.use_sql_comments", "true");
    }

    /**
     * Returns the configuration to modify/extend it before creating an EntityManagerFactory
     * @return Ejb3Configuration configuration
     */
    static Ejb3Configuration getConfiguration() {
        return configuration;
    }

    /**
     * Initiates the entityFactory and entityManager
     */
    static void init() {

        try {

            entityManagerFactory = configuration.buildEntityManagerFactory();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "<html><b>Datenbankverbindung konnte nicht aufgebaut werden.</b><br>"+e.getMessage()+"</hml>", "Fehler", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

    }
    
    /**
     * Close entityManager and entityFactory
     */
    static void close() {
        entityManagerFactory.close();
    }

//    public static EntityManager getEntityManager() {
//        if(entityManager == null || !entityManager.isOpen()) {
//            JOptionPane.showMessageDialog(null, "EntityManager is null or not open.", "Fehler", JOptionPane.ERROR_MESSAGE);
////            System.exit(0);
//        }
//        return entityManager;
//    }
    
    /**
     * Creates a new EntityManager
     * @return EntityManager
     */
    public static EntityManager createEntityManager() {
        if(entityManagerFactory == null || !entityManagerFactory.isOpen()) {
            JOptionPane.showMessageDialog(null, "EntityManagerFactory is null or not open.", "Fehler", JOptionPane.ERROR_MESSAGE);
//            System.exit(0);
            return null;
        } else {
        	EntityManager newEntityManager = entityManagerFactory.createEntityManager();
			newEntityManager.setFlushMode(FlushModeType.COMMIT);
			newEntityManager.getTransaction().begin();
        	return newEntityManager;
        }
    }
    
    /**
     * Flushes and closes an entityManager
     * @param entityManager EntityManager
     */
    public static void closeEntityManager(EntityManager entityManager) {
    	if(entityManager != null && !entityManager.isOpen()) {
    		entityManager.flush();
    		entityManager.close();
    	}
    }
	
    /**
     * Commits the current transactions and begins a new one
     * @param entityManager EntityManager
     */
    public static void commit(EntityManager entityManager) {
    	entityManager.getTransaction().commit();
    	entityManager.getTransaction().begin();
    }
}
