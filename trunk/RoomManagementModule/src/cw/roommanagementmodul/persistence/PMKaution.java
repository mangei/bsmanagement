package cw.roommanagementmodul.persistence;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import cw.boardingschoolmanagement.persistence.CWPersistenceManager;
import cw.boardingschoolmanagement.persistence.HibernateUtil;
import cw.customermanagementmodul.customer.persistence.Customer;

/**
 *
 * @author Dominik
 */
public class PMKaution
	extends CWPersistenceManager<Kaution> {

    private static PMKaution instance;
    private static Logger logger = Logger.getLogger(PMKaution.class.getName());

    private PMKaution() {
    	super(Kaution.class);
    }

    public static PMKaution getInstance() {
        if (instance == null) {
            instance = new PMKaution();
        }
        return instance;
    }
    
    public Kaution create(EntityManager entityManager) {
    	Kaution kaution = new Kaution(entityManager);
    	entityManager.persist(kaution);
    	return kaution;
    }

    public List<Kaution> getAll(EntityManager entityManager) {
        return setEntityManager(entityManager.createQuery(
        		"FROM " + 
        			Kaution.ENTITY_NAME)
        .getResultList(), entityManager);
    }

    public int countAll(EntityManager entityManager) {
        return ( (Long) entityManager.createQuery(
        		"SELECT " +
        			"COUNT(*)" +
        		" FROM " + 
        			Kaution.ENTITY_NAME)
        .getResultList().iterator().next() ).intValue();
    }

}
