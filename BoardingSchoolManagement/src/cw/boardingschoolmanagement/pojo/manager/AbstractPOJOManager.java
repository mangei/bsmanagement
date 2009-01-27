package cw.boardingschoolmanagement.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.app.POJOManagerEvent;
import cw.boardingschoolmanagement.app.POJOManagerListener;
import cw.boardingschoolmanagement.app.POJOManagerListenerSupport;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author ManuelG
 */
public abstract class AbstractPOJOManager<T>{

    protected POJOManagerListenerSupport pojoManagerListenerSupport;

    public AbstractPOJOManager() {
        pojoManagerListenerSupport = new POJOManagerListenerSupport();
    }

    public void removePOJOManagerListener(POJOManagerListener listener) {
        pojoManagerListenerSupport.removePOJOManagerListener(listener);
    }

    public void addPOJOManagerListener(POJOManagerListener listener) {
        pojoManagerListenerSupport.addPOJOManagerListener(listener);
    }

    public void save(T obj) {
        EntityManager em = HibernateUtil.getEntityManager();
        if(!em.contains(obj)) {
            em.persist(obj);
        }
        em.getTransaction().begin();
        em.getTransaction().commit();
    }

    public void delete(T obj) {

        pojoManagerListenerSupport.firePOJOManagerDelete(obj);

        EntityManager em = HibernateUtil.getEntityManager();
        if(em.contains(obj)) {
            em.getTransaction().begin();
            em.remove(obj);
            em.getTransaction().commit();
        }
    }

    public abstract List<T> getAll();
    public abstract int size();
}
