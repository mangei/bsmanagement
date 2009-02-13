/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import cw.roommanagementmodul.pojo.Gebuehr;
import java.util.List;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;

/**
 *
 * @author Dominik
 */
public class GebuehrenManager extends AbstractPOJOManager<Gebuehr> {

    private static GebuehrenManager instance;
    private static Logger logger = Logger.getLogger(GebuehrenManager.class);

    private GebuehrenManager() {
    }

    public static GebuehrenManager getInstance() {
        if (instance == null) {
            instance = new GebuehrenManager();
        }
        return instance;
    }

    @Override
    public void delete(Gebuehr obj) {

        //cascadeListenerSupport.fireCascadeDelete(obj);



        EntityManager em = HibernateUtil.getEntityManager();
        if (em.contains(obj)) {
            System.out.println("gebuehr löschen");
            GebuehrZuordnungManager gebZuordnungManager = GebuehrZuordnungManager.getInstance();
            gebZuordnungManager.removeGebuehrZuordnung(obj);
            em.getTransaction().begin();
            em.remove(obj);
            em.getTransaction().commit();
        }

    }

    @Override
    public List<Gebuehr> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM Gebuehr").getResultList();
    }

    @Override
    public int size() {
        return ((Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM Gebuehr").getResultList().iterator().next()).intValue();
    }
}
