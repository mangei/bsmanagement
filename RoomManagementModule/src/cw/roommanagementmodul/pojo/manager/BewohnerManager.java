/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import cw.customermanagementmodul.pojo.Customer;
import cw.roommanagementmodul.pojo.Bewohner;
import cw.roommanagementmodul.pojo.Kaution;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 *
 * @author Dominik
 */
public class BewohnerManager extends AbstractPOJOManager<Bewohner> {

    private static BewohnerManager instance;
    private static Logger logger = Logger.getLogger(BewohnerManager.class.getName());

    private BewohnerManager() {
    }

    public static BewohnerManager getInstance() {
        if (instance == null) {
            instance = new BewohnerManager();
        }
        return instance;
    }

    public Bewohner getBewohner(Customer customer) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        Bewohner bewohner=null;
        try {
            bewohner = (Bewohner)entityManager.createQuery("SELECT b FROM Bewohner b where b.customer.id = " + customer.getId()).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
        return bewohner;
    }

    public List<Bewohner> getBewohner(boolean activ) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        List<Bewohner> list = entityManager.createQuery("SELECT b FROM Bewohner b where b.active = " + activ + " and b.customer.active ="+true+" order by b.customer.surname asc, b.customer.forename asc").getResultList();
        return list;
    }

    public boolean existKaution(Kaution kaution){
        EntityManager entityManager = HibernateUtil.getEntityManager();
        List<Bewohner> list= entityManager.createQuery("SELECT b From Bewohner b where b.kaution.id = "+ kaution.getId()).getResultList();
        return !list.isEmpty();
    }

    @Override
    public List<Bewohner> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM Bewohner order by b.customer.surname asc, b.customer.forename asc").getResultList();
    }

    @Override
    public int size() {
        return ((Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM Bewohner").getResultList().iterator().next()).intValue();
    }
}
