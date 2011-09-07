package cw.boardingschoolmanagement.perstistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;
import org.hibernate.ejb.Ejb3Configuration;

/**
 * This is an helperclass for Hibernate with some basic and necessary methods.
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public class HibernateUtil {
//
//    private static EntityManagerFactory entityManagerFactory;
//    private static EntityManager entityManager;
//    private static Ejb3Configuration configuration;
//    
//
//    static {
//
//        configuration = new Ejb3Configuration().
//
//            // HSQLDB
////            setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect").
////            setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver").
////            setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:employeemanagement").
////            setProperty("hibernate.connection.username", "sa").
////            setProperty("hibernate.connection.password", "").
//
//            // MYSQL
//            setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect").
//            setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver").
//            setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/internat").
//            setProperty("hibernate.connection.username", "root").
//            setProperty("hibernate.connection.password", "").
//
//            // H2
////            setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect").
////            setProperty("hibernate.connection.driver_class", "org.h2.Driver").
////            setProperty("hibernate.connection.url", "jdbc:h2:internat").
////            setProperty("hibernate.connection.username", "sa").
////            setProperty("hibernate.connection.password", "").
//
////            setProperty("hibernate.connection.pool_size", "1").
//            setProperty("hibernate.connection.autocommit", "true").
////            setProperty("hibernate.cache.provider_class", "org.hibernate.cache.HashtableCacheProvider").
//            setProperty("hibernate.hbm2ddl.auto", "update").
//            setProperty("hibernate.show_sql", "true");
////            setProperty("hibernate.format_sql", "true").
////            setProperty("hibernate.use_sql_comments", "true");
//    }
//
//    public static Ejb3Configuration getConfiguration() {
//        return configuration;
//    }
//
//    public static void configure() {
//
//        try {
//
//            entityManagerFactory = configuration.buildEntityManagerFactory();
//            entityManager = entityManagerFactory.createEntityManager();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "<html><b>Datenbankverbindung konnte nicht aufgebaut werden.</b><br>"+e.getMessage()+"</hml>", "Fehler", JOptionPane.ERROR_MESSAGE);
//            System.exit(0);
//        }
//
//    }
//
//    public static EntityManager getEntityManager() {
//        if(entityManager == null || !entityManager.isOpen()) {
//            JOptionPane.showMessageDialog(null, "EntityManager is null or not open.", "Fehler", JOptionPane.ERROR_MESSAGE);
////            System.exit(0);
//        }
//        return entityManager;
//    }
//
//    public static void close() {
//        entityManager.close();
//        entityManagerFactory.close();
//    }

}
