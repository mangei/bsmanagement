package cw.boardingschoolmanagement.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.app.CascadeListener;
import cw.boardingschoolmanagement.app.CascadeListenerSupport;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author ManuelG
 */
public abstract class AbstractPOJOManager<T>{

    protected CascadeListenerSupport cascadeListenerSupport;

    public AbstractPOJOManager() {
        cascadeListenerSupport = new CascadeListenerSupport();
    }

    public void removeCascadeListener(CascadeListener listener) {
        cascadeListenerSupport.removeCascadeListener(listener);
    }

    public void addCascadeListener(CascadeListener listener) {
        cascadeListenerSupport.addCascadeListener(listener);
    }

    public boolean save(T obj) {
        if(obj == null) {
            return false;
        }

        try {
            EntityManager em = HibernateUtil.getEntityManager();
            if (!em.contains(obj)) {
                em.persist(obj);
            }
            em.getTransaction().begin();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();

            // Something went wrong
            return false;
        }

        // Everythink worked
        return true;
    }

    public boolean delete(T obj) {
        if(obj == null) {
            return false;
        }

        cascadeListenerSupport.fireCascadeDelete(obj);

        try {
            EntityManager em = HibernateUtil.getEntityManager();
            if (em.contains(obj)) {
                em.getTransaction().begin();
                em.remove(obj);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();

            // Something went wrong
            return false;
        }

        // Everything worked
        return true;
    }

    public abstract List<T> getAll();
    public abstract int size();
}
