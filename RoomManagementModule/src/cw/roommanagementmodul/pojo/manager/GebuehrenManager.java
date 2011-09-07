/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.persistence.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPersistenceManager;
import cw.roommanagementmodul.pojo.Gebuehr;
import cw.roommanagementmodul.pojo.GebuehrenKategorie;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 *
 * @author Dominik
 */
public class GebuehrenManager extends AbstractPersistenceManager<Gebuehr> {

    private static GebuehrenManager instance;
    private static Logger logger = Logger.getLogger(GebuehrenManager.class.getName());

    private GebuehrenManager() {
    }

    public static GebuehrenManager getInstance() {
        if (instance == null) {
            instance = new GebuehrenManager();
        }
        return instance;
    }

    @Override
    public List<Gebuehr> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM Gebuehr").getResultList();
    }

    public List<Gebuehr> getGebuehr(GebuehrenKategorie k) {
        return HibernateUtil.getEntityManager().createQuery("Select g From Gebuehr g where g.gebKat.id = " + k.getId()).getResultList();
    }

    @Override
    public int size() {
        return ((Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM Gebuehr").getResultList().iterator().next()).intValue();
    }
}
