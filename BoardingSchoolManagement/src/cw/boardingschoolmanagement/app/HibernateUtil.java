package cw.boardingschoolmanagement.app;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * This is an helperclass for Hibernate with some basic and necessary methods.
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory;
    private static AnnotationConfiguration annotationConfiguration;
    private static Session session;
    

    static {
        try {
            annotationConfiguration = new AnnotationConfiguration();
        } catch (Throwable ex) {
            // Log exception!
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static AnnotationConfiguration getAnnotationConfiguration() {
        return annotationConfiguration;
    }

    public static void configure() {
        annotationConfiguration.configure();
        while(sessionFactory == null) {
            try {
                sessionFactory = annotationConfiguration.buildSessionFactory();
            } catch (Exception hibernateException) {
                System.out.println("Exception:" + hibernateException);
            }
        }
        getSession();
    }

    public static Session getSession() throws HibernateException {
        if(session == null) {
            session = sessionFactory.openSession();
        }
        return session;
    }
}
