package cw.boardingschoolmanagement.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author ManuelG
 */
public abstract class AbstractPOJOManager<T> {

    public void save(T obj) {
        EntityManager em = HibernateUtil.getEntityManager();
        if(!em.contains(obj)) {
            em.persist(obj);
        }
        em.getTransaction().begin();
        em.getTransaction().commit();
    }

    public void remove(T obj) {
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
