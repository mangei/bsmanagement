/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import cw.roommanagementmodul.pojo.BuchungsLaufZuordnung;
import cw.roommanagementmodul.pojo.GebLauf;
import cw.roommanagementmodul.pojo.GebuehrZuordnung;
import java.util.List;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;

/**
 *
 * @author Dominik
 */
public class BuchungsLaufZuordnungManager extends AbstractPOJOManager<BuchungsLaufZuordnung> {

    private static BuchungsLaufZuordnungManager instance;
    private static Logger logger = Logger.getLogger(BuchungsLaufZuordnungManager.class);

    private BuchungsLaufZuordnungManager() {
    }

    public static BuchungsLaufZuordnungManager getInstance() {
        if (instance == null) {
            instance = new BuchungsLaufZuordnungManager();
        }
        return instance;
    }

    public List<BuchungsLaufZuordnung> getBuchungsLaufZuordnung(GebLauf gebLauf) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        List<BuchungsLaufZuordnung> list = entityManager.createQuery("Select b From BuchungsLaufZuordnung b where b.gebLauf.id = " + gebLauf.getId()).getResultList();
        return list;
    }

 

    @Override
    public List<BuchungsLaufZuordnung> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM BuchungsLaufZuordnung").getResultList();
    }

    @Override
    public int size() {
        return ((Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM BuchungsLaufZuordnung").getResultList().iterator().next()).intValue();
    }
}
