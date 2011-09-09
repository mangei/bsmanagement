package cw.boardingschoolmanagement.pojo.manager;

import javax.persistence.EntityManager;

import cw.boardingschoolmanagement.persistence.CWPersistence;
import cw.boardingschoolmanagement.persistence.CascadeListener;
import cw.boardingschoolmanagement.persistence.CascadeListenerSupport;
import cw.boardingschoolmanagement.persistence.PersistenceManager;

/**
 * Basic class for all persistence managers.
 *
 * @author Manuel Geier
 */
public abstract class AbstractPersistenceManager<TPersistence extends CWPersistence>
	implements PersistenceManager<TPersistence> {

    protected CascadeListenerSupport cascadeListenerSupport;
    protected Class<TPersistence> persistenceClass;

    public AbstractPersistenceManager(Class<TPersistence> clazz) {
    	persistenceClass = clazz;
    	
        cascadeListenerSupport = new CascadeListenerSupport();
    }

    public void removeCascadeListener(CascadeListener listener) {
    	if(listener != null) {
    		cascadeListenerSupport.removeCascadeListener(listener);
    	}
    }

    public void addCascadeListener(CascadeListener listener) {
    	if(listener != null) {
    		cascadeListenerSupport.addCascadeListener(listener);
    	}
    }
    
    public TPersistence get(Long id, EntityManager entityManager) {
    	return entityManager.find(persistenceClass, id);
    }
    
    public void remove(TPersistence persistence) {
    	if(persistence != null && persistence.getEntityManager() != null) {
	    	cascadeListenerSupport.fireCascadeDelete(persistence);
	    	persistence.getEntityManager().remove(persistence);
    	}
    }
}
