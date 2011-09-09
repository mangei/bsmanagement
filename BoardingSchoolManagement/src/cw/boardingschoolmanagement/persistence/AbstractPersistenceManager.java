package cw.boardingschoolmanagement.persistence;

import java.util.List;

import javax.persistence.EntityManager;


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
    
    protected TPersistence setEntityManager(TPersistence persistence, EntityManager entityManager) {
    	persistence.setEntityManager(entityManager);
    	return persistence;
    }
    
    protected List<TPersistence> setEntityManager(List<TPersistence> persistencList, EntityManager entityManager) {
    	for(TPersistence persistence: persistencList) {
    		persistence.setEntityManager(entityManager);
    	}
    	return persistencList;
    }
    
    public TPersistence get(Long id, EntityManager entityManager) {
    	return setEntityManager(entityManager.find(persistenceClass, id), entityManager);
    }
    
    public void remove(TPersistence persistence) {
    	if(persistence != null && persistence.getEntityManager() != null) {
	    	cascadeListenerSupport.fireCascadeDelete(persistence);
	    	persistence.getEntityManager().remove(persistence);
    	}
    }
}
