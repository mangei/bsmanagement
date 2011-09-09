package cw.boardingschoolmanagement.persistence;

import javax.persistence.EntityManager;

import cw.boardingschoolmanagement.app.CWModel;
import cw.boardingschoolmanagement.app.adaptable.CWIAdaptable;
import cw.boardingschoolmanagement.app.adaptable.IAdaptable;
import cw.boardingschoolmanagement.app.adaptable.ITypedAdapterSupport;

/**
 * Basic class for all persistence objects
 * 
 * @author Manuel Geier
 */
public class CWPersistence
	extends CWModel
	implements CWIAdaptable, AnnotatedClass {

	private EntityManager entityManager;
	private ITypedAdapterSupport iTypedAdaptableSupport = new ITypedAdapterSupport();
	
	protected CWPersistence(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public IAdaptable getAdapter(Class adaptableClass) {
		return iTypedAdaptableSupport.getAdapter(this, adaptableClass);
	}
	
	public <T> T getTypedAdapter(Class<T> adaptableClass) {
		return iTypedAdaptableSupport.getTypedAdapter(this, adaptableClass);
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
}
