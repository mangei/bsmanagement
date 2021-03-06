/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.pojo.manager;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import cw.accountmanagementmodul.pojo.AccountPosting;
import cw.boardingschoolmanagement.persistence.AbstractPersistenceManager;
import cw.boardingschoolmanagement.persistence.HibernateUtil;
import cw.roommanagementmodul.pojo.BuchungsLaufZuordnung;
import cw.roommanagementmodul.pojo.GebLauf;
import cw.roommanagementmodul.pojo.Gebuehr;

/**
 *
 * @author Dominik
 */
public class BuchungsLaufZuordnungManager extends AbstractPersistenceManager<BuchungsLaufZuordnung> {

    private static BuchungsLaufZuordnungManager instance;
    private static Logger logger = Logger.getLogger(BuchungsLaufZuordnungManager.class.getName());

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

    public List<BuchungsLaufZuordnung> getBuchungsLaufZuordnung(Gebuehr g) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        List<BuchungsLaufZuordnung> list = entityManager.createQuery("Select b From BuchungsLaufZuordnung b where b.gebuehr.id = " + g.getId()).getResultList();
        return list;
    }

    public BuchungsLaufZuordnung getBuchungsLaufZuordnung(AccountPosting p) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        BuchungsLaufZuordnung blz=null;
        try {
            blz = (BuchungsLaufZuordnung) entityManager.createQuery("Select b From BuchungsLaufZuordnung b where b.posting.id = " + p.getId()).getSingleResult();
        }catch(javax.persistence.NoResultException ex){
            return null;
        }
        return blz;
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
