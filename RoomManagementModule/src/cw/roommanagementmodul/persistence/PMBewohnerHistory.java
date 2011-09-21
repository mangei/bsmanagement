package cw.roommanagementmodul.persistence;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import cw.boardingschoolmanagement.persistence.CWPersistenceManager;
import cw.boardingschoolmanagement.persistence.HibernateUtil;
import cw.customermanagementmodul.customer.persistence.Customer;

/**
 *
 * @author Dominik
 */
public class PMBewohnerHistory
	extends CWPersistenceManager<BewohnerHistory> {

    private static PMBewohnerHistory instance;
    private static Logger logger = Logger.getLogger(PMBewohnerHistory.class.getName());

    private PMBewohnerHistory() {
    	super(BewohnerHistory.class);
    }

    public static PMBewohnerHistory getInstance() {
        if (instance == null) {
            instance = new PMBewohnerHistory();
        }
        return instance;
    }
    
    public BewohnerHistory create(EntityManager entityManager) {
    	BewohnerHistory bewohnerHistory = new BewohnerHistory(entityManager);
    	entityManager.persist(bewohnerHistory);
    	return bewohnerHistory;
    }

    public void saveBewohnerHistory(Bewohner b, EntityManager entityManager) {
        String zimmerName = b.getZimmer().toString();
        String bereichsName;
        if (b.getZimmer().getBereich() != null) {
            bereichsName = b.getZimmer().getBereich().toString();
        } else {
            bereichsName = "-";
        }

        BewohnerHistory bh = new BewohnerHistory(zimmerName, bereichsName, b.getVon(), b.getBis(), b, new Date());
        EntityTransaction tran = entityManager.getTransaction();
        tran.begin();
        entityManager.persist(bh);
        tran.commit();

    }

 
    public void removeBewohnerHistory(Bewohner b, EntityManager entityManager) {
        List<BewohnerHistory> l = this.getBewohnerHistory(b, entityManager);
        EntityTransaction tran = entityManager.getTransaction();
        tran.begin();
        for (int i = 0; i < l.size(); i++) {
            entityManager.remove(l.get(i));
        }

        tran.commit();
    }

    public List<BewohnerHistory> getBewohnerHistory(Bewohner b, EntityManager entityManager) {
        List list = entityManager.createQuery("SELECT b FROM BewohnerHistory b where b.bewohner = " + b.getId()).getResultList();
        return list;
    }

    public List<BewohnerHistory> getAll(EntityManager entityManager) {
        return setEntityManager(entityManager.createQuery(
        		"FROM " + 
        			BewohnerHistory.ENTITY_NAME)
        .getResultList(), entityManager);
    }

    @Override
    public int countAll(EntityManager entityManager) {
        return ( (Long) entityManager.createQuery(
        		"SELECT " +
        			"COUNT(*)" +
        		" FROM " + 
        			BewohnerHistory.ENTITY_NAME)
        .getResultList().iterator().next() ).intValue();
    }
}
