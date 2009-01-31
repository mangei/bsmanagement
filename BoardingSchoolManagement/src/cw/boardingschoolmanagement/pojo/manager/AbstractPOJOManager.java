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

    public void save(T obj) {
        try {
            EntityManager em = HibernateUtil.getEntityManager();
            if (!em.contains(obj)) {
                em.persist(obj);
            }
            em.getTransaction().begin();
            em.getTransaction().commit();
        } catch (Exception e) {
        }
    }

    public void save(List<T> list) {
        for(int i=0, l=list.size(); i<l; i++) {
            save(list.get(i));
        }
    }

    public void delete(T obj) {

        cascadeListenerSupport.fireCascadeDelete(obj);

        try {
            EntityManager em = HibernateUtil.getEntityManager();
            if (em.contains(obj)) {
                em.getTransaction().begin();
                em.remove(obj);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
        }
    }

    public void delete(List<T> list) {
        for(int i=0, l=list.size(); i<l; i++) {
            delete(list.get(i));
        }
    }

    public abstract List<T> getAll();
    public abstract int size();
}
