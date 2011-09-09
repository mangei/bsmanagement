/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.pojo.manager;

import cw.boardingschoolmanagement.persistence.AbstractPersistenceManager;
import cw.boardingschoolmanagement.persistence.HibernateUtil;
import java.util.Date;
import java.util.List;

import cw.roommanagementmodul.pojo.Bewohner;
import cw.roommanagementmodul.pojo.BewohnerHistory;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Dominik
 */
public class BewohnerHistoryManager extends AbstractPersistenceManager<BewohnerHistory> {

    private static BewohnerHistoryManager instance;
    private static Logger logger = Logger.getLogger(BewohnerHistoryManager.class.getName());

    private BewohnerHistoryManager() {
    }

    public static BewohnerHistoryManager getInstance() {
        if (instance == null) {
            instance = new BewohnerHistoryManager();
        }
        return instance;
    }

    public void saveBewohnerHistory(Bewohner b) {
        String zimmerName = b.getZimmer().toString();
        String bereichsName;
        if (b.getZimmer().getBereich() != null) {
            bereichsName = b.getZimmer().getBereich().toString();
        } else {
            bereichsName = "-";
        }

        BewohnerHistory bh = new BewohnerHistory(zimmerName, bereichsName, b.getVon(), b.getBis(), b, new Date());
         EntityManager entityManager = HibernateUtil.getEntityManager();
        EntityTransaction tran = entityManager.getTransaction();
        tran.begin();
        entityManager.persist(bh);
        tran.commit();

    }

 
    public void removeBewohnerHistory(Bewohner b) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        List<BewohnerHistory> l = this.getBewohnerHistory(b);
        EntityTransaction tran = entityManager.getTransaction();
        tran.begin();
        for (int i = 0; i < l.size(); i++) {
            entityManager.remove(l.get(i));
        }

        tran.commit();
    }

    public List<BewohnerHistory> getBewohnerHistory(Bewohner b) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        List list = entityManager.createQuery("SELECT b FROM BewohnerHistory b where b.bewohner = " + b.getId()).getResultList();
        return list;
    }

    @Override
    public List<BewohnerHistory> getAll() {
        return HibernateUtil.getEntityManager().createQuery("FROM BewohnerHistory").getResultList();
    }

    @Override
    public int size() {
        return ((Long) HibernateUtil.getEntityManager().createQuery("SELECT COUNT(*) FROM BewohnerHistory").getResultList().iterator().next()).intValue();
    }
}
