package cw.roommanagementmodul.persistence;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import cw.accountmanagementmodul.pojo.AccountPosting;
import cw.boardingschoolmanagement.persistence.CWPersistenceManager;
import cw.boardingschoolmanagement.persistence.HibernateUtil;
import cw.customermanagementmodul.customer.persistence.Customer;

/**
 *
 * @author Dominik
 */
public class PMBuchungsLaufZuordnung
	extends CWPersistenceManager<BuchungsLaufZuordnung> {

    private static PMBuchungsLaufZuordnung instance;
    private static Logger logger = Logger.getLogger(PMBuchungsLaufZuordnung.class.getName());

    private PMBuchungsLaufZuordnung() {
    	super(BuchungsLaufZuordnung.class);
    }

    public static PMBuchungsLaufZuordnung getInstance() {
        if (instance == null) {
            instance = new PMBuchungsLaufZuordnung();
        }
        return instance;
    }
    
    public BuchungsLaufZuordnung create(EntityManager entityManager) {
    	BuchungsLaufZuordnung buchungsLaufZuordnung = new BuchungsLaufZuordnung(entityManager);
    	entityManager.persist(buchungsLaufZuordnung);
    	return buchungsLaufZuordnung;
    }

    public List<BuchungsLaufZuordnung> getBuchungsLaufZuordnung(GebLauf gebLauf, EntityManager entityManager) {
        List<BuchungsLaufZuordnung> list = entityManager.createQuery("Select b From BuchungsLaufZuordnung b where b.gebLauf.id = " + gebLauf.getId()).getResultList();
        return list;
    }

    public List<BuchungsLaufZuordnung> getBuchungsLaufZuordnung(Gebuehr g, EntityManager entityManager) {
        List<BuchungsLaufZuordnung> list = entityManager.createQuery("Select b From BuchungsLaufZuordnung b where b.gebuehr.id = " + g.getId()).getResultList();
        return list;
    }

    public BuchungsLaufZuordnung getBuchungsLaufZuordnung(AccountPosting p, EntityManager entityManager) {
        BuchungsLaufZuordnung blz=null;
        try {
            blz = (BuchungsLaufZuordnung) entityManager.createQuery("Select b From BuchungsLaufZuordnung b where b.posting.id = " + p.getId()).getSingleResult();
        }catch(javax.persistence.NoResultException ex){
            return null;
        }
        return blz;
    }

    public List<BuchungsLaufZuordnung> getAll(EntityManager entityManager) {
        return setEntityManager(entityManager.createQuery(
        		"FROM " + 
        			BuchungsLaufZuordnung.ENTITY_NAME)
        .getResultList(), entityManager);
    }

    @Override
    public int countAll(EntityManager entityManager) {
        return ( (Long) entityManager.createQuery(
        		"SELECT " +
        			"COUNT(*)" +
        		" FROM " + 
        			BuchungsLaufZuordnung.ENTITY_NAME)
        .getResultList().iterator().next() ).intValue();
    }
}
