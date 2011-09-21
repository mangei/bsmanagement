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
public class PMTarif
	extends CWPersistenceManager<Tarif> {

    private static PMTarif instance;
    private static Logger logger = Logger.getLogger(PMTarif.class.getName());

    private PMTarif() {
    	super(Tarif.class);
    }
    
    public static PMTarif getInstance() {
        if (instance == null) {
            instance = new PMTarif();
        }
        return instance;
    }
    
    public Tarif create(EntityManager entityManager) {
    	Tarif tarif = new Tarif(entityManager);
    	entityManager.persist(tarif);
    	return tarif;
    }

    public List getTarif(Gebuehr g, EntityManager entityManager){
        List<Tarif> list = entityManager.createQuery("Select t From Tarif t where t.gebuehr.id = " + g.getId()).getResultList();
        return list;
    }

    public List<Tarif> getAll(EntityManager entityManager) {
        return setEntityManager(entityManager.createQuery(
        		"FROM " + 
        			Tarif.ENTITY_NAME)
        .getResultList(), entityManager);
    }
   
    public List<Tarif> getAllOrderd(Gebuehr g, EntityManager entityManager) {
        return entityManager.createQuery("Select t FROM Tarif t where t.gebuehr.id="+g.getId()+" order by t.ab asc, t.bis asc").getResultList();
    }

    public int countAll(EntityManager entityManager) {
        return ( (Long) entityManager.createQuery(
        		"SELECT " +
        			"COUNT(*)" +
        		" FROM " + 
        			Tarif.ENTITY_NAME)
        .getResultList().iterator().next() ).intValue();
    }

}
