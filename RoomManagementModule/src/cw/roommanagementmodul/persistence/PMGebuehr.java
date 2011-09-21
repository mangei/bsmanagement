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
public class PMGebuehr
	extends CWPersistenceManager<Gebuehr> {

    private static PMGebuehr instance;
    private static Logger logger = Logger.getLogger(PMGebuehr.class.getName());

    private PMGebuehr() {
    	super(Gebuehr.class);
    }

    public static PMGebuehr getInstance() {
        if (instance == null) {
            instance = new PMGebuehr();
        }
        return instance;
    }
    
    public Gebuehr create(EntityManager entityManager) {
    	Gebuehr gebuehr = new Gebuehr(entityManager);
    	entityManager.persist(gebuehr);
    	return gebuehr;
    }

    public List<Gebuehr> getAll(EntityManager entityManager) {
        return setEntityManager(entityManager.createQuery(
        		"FROM " + 
        			Gebuehr.ENTITY_NAME)
        .getResultList(), entityManager);
    }

    public List<Gebuehr> getGebuehr(GebuehrenKategorie k, EntityManager entityManager) {
        return entityManager.createQuery("Select g From Gebuehr g where g.gebKat.id = " + k.getId()).getResultList();
    }

    @Override
    public int countAll(EntityManager entityManager) {
        return ( (Long) entityManager.createQuery(
        		"SELECT " +
        			"COUNT(*)" +
        		" FROM " + 
        			Gebuehr.ENTITY_NAME)
        .getResultList().iterator().next() ).intValue();
    }
}
