package cw.boardingschoolmanagement.app;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.hibernate.ejb.Ejb3Configuration;

/**
 * This is an helperclass for Hibernate with some basic and necessary methods.
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public class HibernateUtil {

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
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
            setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect").
            setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver").
            setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/internat").
            setProperty("hibernate.connection.username", "scott").
            setProperty("hibernate.connection.password", "tiger").

            setProperty("hibernate.connection.pool_size", "1").
            setProperty("hibernate.connection.autocommit", "true").
            setProperty("hibernate.cache.provider_class", "org.hibernate.cache.HashtableCacheProvider").
            setProperty("hibernate.hbm2ddl.auto", "update").
            setProperty("hibernate.show_sql", "true").
            setProperty("hibernate.format_sql", "true").
            setProperty("hibernate.use_sql_comments", "true");
    }

    public static Ejb3Configuration getConfiguration() {
        return configuration;
    }

    public static void configure() {
        entityManagerFactory = configuration.buildEntityManagerFactory();
        entityManager = entityManagerFactory.createEntityManager();
    }

    public static EntityManager getEntityManager() {
        return entityManager;
    }

}
