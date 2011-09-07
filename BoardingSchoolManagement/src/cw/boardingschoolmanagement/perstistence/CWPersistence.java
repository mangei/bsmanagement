package cw.boardingschoolmanagement.perstistence;

import javax.persistence.EntityManager;

import cw.boardingschoolmanagement.app.CWModel;
import cw.boardingschoolmanagement.app.adaptable.IAdaptable;
import cw.boardingschoolmanagement.app.adaptable.IAdapterManager;
import cw.boardingschoolmanagement.app.adaptable.ITypedAdaptable;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;

/**
 * Basic class for all persistence objects
 * 
 * @author Manuel Geier
 */
public class CWPersistence
	extends CWModel
	implements ITypedAdaptable, AnnotatedClass {

	private EntityManager entityManager;
	
	protected CWPersistence(EntityManager entityManager) {
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
