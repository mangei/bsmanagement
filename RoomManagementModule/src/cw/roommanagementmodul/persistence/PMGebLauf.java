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
public class PMGebLauf
	extends CWPersistenceManager<GebLauf> {

    private static PMGebLauf instance;
    private static Logger logger = Logger.getLogger(PMGebLauf.class.getName());

    private PMGebLauf() {
    	super(GebLauf.class);
    }

    public static PMGebLauf getInstance() {
        if (instance == null) {
            instance = new PMGebLauf();
        }
        return instance;
    }
    
    public GebLauf create(EntityManager entityManager) {
    	GebLauf gebLauf = new GebLauf(entityManager);
    	entityManager.persist(gebLauf);
    	return gebLauf;
    }

    public boolean existGebLauf(long abrMonat) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        List gebLauf = entityManager.createQuery("SELECT gl FROM GebLauf gl where gl.abrMonat =" + abrMonat).getResultList();
        if (gebLauf.size() == 0) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public boolean delete(GebLauf gebLauf, EntityManager entityManager) {

        PMBuchungsLaufZuordnung blzManager = PMBuchungsLaufZuordnung.getInstance();
        List<BuchungsLaufZuordnung> list = blzManager.getBuchungsLaufZuordnung(gebLauf);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setGebLauf(null);
        }

        return super.delete(gebLauf);
    }


    public List<GebLauf> getAll(EntityManager entityManager) {
        return setEntityManager(entityManager.createQuery(
        		"FROM " + 
        			GebLauf.ENTITY_NAME)
        .getResultList(), entityManager);
    }

    public List<GebLauf> getAllOrdered(EntityManager entityManager) {
        return entityManager.createQuery("Select g FROM GebLauf g order by g.abrMonat asc").getResultList();
    }

    @Override
    public int countAll(EntityManager entityManager) {
        return ( (Long) entityManager.createQuery(
        		"SELECT " +
        			"COUNT(*)" +
        		" FROM " + 
        			GebLauf.ENTITY_NAME)
        .getResultList().iterator().next() ).intValue();
    }
}
