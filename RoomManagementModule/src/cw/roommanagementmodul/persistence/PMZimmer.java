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
public class PMZimmer
	extends CWPersistenceManager<Zimmer> {

    private static PMZimmer instance;
    private static Logger logger = Logger.getLogger(PMZimmer.class.getName());

    private PMZimmer() {
    	super(Zimmer.class);
    }

    public static PMZimmer getInstance() {
        if (instance == null) {
            instance = new PMZimmer();
        }
        return instance;
    }
    
    public Zimmer create(EntityManager entityManager) {
    	Zimmer zimmer = new Zimmer(entityManager);
    	entityManager.persist(zimmer);
    	return zimmer;
    }

    public List<Zimmer> getAll(EntityManager entityManager) {
        return setEntityManager(entityManager.createQuery(
        		"FROM " + 
        			Zimmer.ENTITY_NAME)
        .getResultList(), entityManager);
    }
    
    public int countAll(EntityManager entityManager) {
        return ( (Long) entityManager.createQuery(
        		"SELECT " +
        			"COUNT(*)" +
        		" FROM " + 
        			Zimmer.ENTITY_NAME)
        .getResultList().iterator().next() ).intValue();
    }

}
