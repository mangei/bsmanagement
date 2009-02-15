/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import cw.roommanagementmodul.pojo.BuchungsLaufZuordnung;
import cw.roommanagementmodul.pojo.GebLauf;
import java.util.List;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;

/**
 *
 * @author Dominik
 */
public class GebLaufManager extends AbstractPOJOManager<GebLauf> {

    private static GebLaufManager instance;
    private static Logger logger = Logger.getLogger(GebLaufManager.class);

    private GebLaufManager() {
    }

    public static GebLaufManager getInstance() {
        if (instance == null) {
            instance = new GebLaufManager();
        }
        return instance;
    }

    public boolean existGebLauf(long abrMonat) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        List gebLauf = entityManager.createQuery("SELECT gl FROM GebLauf gl where gl.abrMonat =" + abrMonat).getResultList();
        if (gebLauf.size() == 0) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void delete(GebLauf gebLauf) {

        BuchungsLaufZuordnungManager blzManager = BuchungsLaufZuordnungManager.getInstance();
        List<BuchungsLaufZuordnung> list = blzManager.getBuchungsLaufZuordnung(gebLauf);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setGebLauf(null);
        }

        try {
            EntityManager em = HibernateUtil.getEntityManager();
            if (em.contains(gebLauf)) {
                em.getTransaction().begin();
                em.remove(gebLauf);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
        }
    }

    @Override
    public List<GebLauf> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM GebLauf").getResultList();
    }

    public List<GebLauf> getAllOrdered() {
        return HibernateUtil.getEntityManager().createQuery("Select g FROM GebLauf g order by g.abrMonat asc").getResultList();
    }

    @Override
    public int size() {
        return ((Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM GebLauf").getResultList().iterator().next()).intValue();
    }
}
