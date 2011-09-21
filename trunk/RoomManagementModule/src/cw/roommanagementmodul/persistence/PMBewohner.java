package cw.roommanagementmodul.persistence;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import cw.boardingschoolmanagement.persistence.CWPersistenceManager;
import cw.customermanagementmodul.customer.persistence.Customer;

/**
 *
 * @author Dominik
 */
public class PMBewohner
	extends CWPersistenceManager<Bewohner> {

    private static PMBewohner instance;
    private static Logger logger = Logger.getLogger(PMBewohner.class.getName());

    private PMBewohner() {
    	super(Bewohner.class);
    }

    public static PMBewohner getInstance() {
        if (instance == null) {
            instance = new PMBewohner();
        }
        return instance;
    }
    
    public Bewohner create(EntityManager entityManager) {
    	Bewohner bewohner = new Bewohner(entityManager);
    	entityManager.persist(bewohner);
    	return bewohner;
    }

    public Bewohner getBewohner(Customer customer, EntityManager entityManager) {
        Bewohner bewohner=null;
        try {
            bewohner = (Bewohner)entityManager.createQuery("SELECT b FROM Bewohner b where b.customer.id = " + customer.getId()).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
        return bewohner;
    }

    public List<Bewohner> getBewohner(boolean activ, EntityManager entityManager) {
        List<Bewohner> list = entityManager.createQuery("SELECT b FROM Bewohner b where b.active = " + activ + " and b.customer.active ="+true+" order by b.customer.surname asc, b.customer.forename asc").getResultList();
        return list;
    }

    public boolean existKaution(Kaution kaution, EntityManager entityManager){
        List<Bewohner> list= entityManager.createQuery("SELECT b From Bewohner b where b.kaution.id = "+ kaution.getId()).getResultList();
        return !list.isEmpty();
    }

    public List<Bewohner> getAll(EntityManager entityManager) {
        return setEntityManager(entityManager.createQuery(
        		"FROM " + 
        			Bewohner.ENTITY_NAME)
        .getResultList(), entityManager);
    }

    public int countAll(EntityManager entityManager) {
        return ( (Long) entityManager.createQuery(
        		"SELECT " +
        			"COUNT(*)" +
        		" FROM " + 
        			Bewohner.ENTITY_NAME)
        .getResultList().iterator().next() ).intValue();
    }
}
