package cw.boardingschoolmanagement.logic;

import javax.persistence.EntityManager;

import cw.boardingschoolmanagement.app.adaptable.CWIAdaptable;
import cw.boardingschoolmanagement.app.adaptable.IAdaptable;
import cw.boardingschoolmanagement.app.adaptable.ITypedAdapterSupport;

/**
 * Basis Business Object class
 * 
 * @author Manuel Geier
 */
public abstract class CWBo
	implements CWIAdaptable {

	private ITypedAdapterSupport iTypedAdaptableSupport = new ITypedAdapterSupport();
	private CWIAdaptable baseClass;
	
	public CWBo(CWIAdaptable baseClass) {
		this.baseClass = baseClass;
	}
	
	public IAdaptable getAdapter(Class adaptableClass) {
		return iTypedAdaptableSupport.getAdapter(this, adaptableClass);
	}
	
	public <T> T getTypedAdapter(Class<T> adaptableClass) {
		return iTypedAdaptableSupport.getTypedAdapter(this, adaptableClass);
	}
	
	public CWIAdaptable getBaseClass() {
		return baseClass;
	}
	
	public EntityManager getEntityManager(){
		return baseClass.
			getEntityManager();
	}
}
