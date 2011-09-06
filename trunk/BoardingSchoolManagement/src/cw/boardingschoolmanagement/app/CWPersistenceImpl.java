package cw.boardingschoolmanagement.app;

import javax.persistence.EntityManager;

import cw.boardingschoolmanagement.app.adaptable.IAdaptable;
import cw.boardingschoolmanagement.app.adaptable.IAdapterManager;

/**
 * Basic class for all persistence objects
 * 
 * @author Manuel Geier
 */
public class CWPersistenceImpl
	extends CWModel
	implements CWPersistence {

	private EntityManager entityManager;
	
	public CWPersistenceImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public IAdaptable getAdapter(Class adaptableClass) {
		return IAdapterManager.getAdapter(this, adaptableClass);
	}
	
	public <T> T getTypedAdapter(Class<T> adaptableClass) {
		return (T) getAdapter(adaptableClass);
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
}
