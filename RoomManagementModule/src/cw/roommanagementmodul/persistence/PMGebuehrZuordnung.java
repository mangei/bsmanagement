package cw.roommanagementmodul.persistence;

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
public class PMGebuehrZuordnung
	extends CWPersistenceManager<GebuehrZuordnung> {

    private static PMGebuehrZuordnung instance;
    private static Logger logger = Logger.getLogger(PMGebuehrZuordnung.class.getName());

    private PMGebuehrZuordnung() {
    	super(GebuehrZuordnung.class);
    }

    public static PMGebuehrZuordnung getInstance() {
        if (instance == null) {
            instance = new PMGebuehrZuordnung();
        }
        return instance;
    }
    
    public GebuehrZuordnung create(EntityManager entityManager) {
    	GebuehrZuordnung gebuehrZuordnung = new GebuehrZuordnung(entityManager);
    	entityManager.persist(gebuehrZuordnung);
    	return gebuehrZuordnung;
    }

    public void removeGebuehrZuordnung(Bewohner b, EntityManager entityManager) {
        List<GebuehrZuordnung> l = this.getGebuehrZuordnung(b);
        for(GebuehrZuordnung gz: l) {
            super.delete(gz);
        }
    }

    public void removeGebuehrZuordnung(Gebuehr g, EntityManager entityManager) {
        List<GebuehrZuordnung> l = this.getGebuehrZuordnung(g);
        EntityTransaction tran = entityManager.getTransaction();
        tran.begin();
        for (int i = 0; i < l.size(); i++) {
            l.get(i).setGebuehr(null);
            l.get(i).setBewohner(null);
            super.delete(l.get(i));

        }
        tran.commit();
    }

    public List<GebuehrZuordnung> getGebuehrZuordnung(Gebuehr g, EntityManager entityManager) {
        List<GebuehrZuordnung> list = entityManager.createQuery("Select g From GebuehrZuordnung g where g.gebuehr.id = " + g.getId()).getResultList();
        return list;
    }

    public List getGebuehrZuordnung(Bewohner b, EntityManager entityManager) {
        List<GebuehrZuordnung> list = entityManager.createQuery("Select g From GebuehrZuordnung g where g.bewohner.id = " + b.getId()).getResultList();
        return list;
    }

    public List<GebuehrZuordnung> getAll(EntityManager entityManager) {
        return setEntityManager(entityManager.createQuery(
        		"FROM " + 
        			GebuehrZuordnung.ENTITY_NAME)
        .getResultList(), entityManager);
    }

    @Override
    public int countAll(EntityManager entityManager) {
        return ( (Long) entityManager.createQuery(
        		"SELECT " +
        			"COUNT(*)" +
        		" FROM " + 
        			GebuehrZuordnung.ENTITY_NAME)
        .getResultList().iterator().next() ).intValue();
    }
}
