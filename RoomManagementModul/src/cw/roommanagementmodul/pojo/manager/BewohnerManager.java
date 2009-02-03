/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.app.HibernateUtil;

import cw.boardingschoolmanagement.pojo.manager.AbstractPOJOManager;
import cw.customermanagementmodul.pojo.Customer;
import cw.roommanagementmodul.pojo.Bewohner;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.apache.log4j.Logger;

/**
 *
 * @author Dominik
 */
public class BewohnerManager extends AbstractPOJOManager<Bewohner> {

    private static BewohnerManager instance;
    private static Logger logger = Logger.getLogger(BewohnerManager.class);

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
        Bewohner bewohner = (Bewohner)entityManager.createQuery("SELECT b FROM Bewohner b where b.customer.id = " + customer.getId()).getSingleResult();
        return bewohner;
    }

    public List<Bewohner> getBewohner(boolean activ) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        List<Bewohner> list = entityManager.createQuery("SELECT b FROM Bewohner b where b.active = " + activ).getResultList();
        return list;
    }


    @Override
    public List<Bewohner> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM Bewohner").getResultList();
    }

    @Override
    public int size() {
        return ((Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM Bewohner").getResultList().iterator().next()).intValue();
    }
}
