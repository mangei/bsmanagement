package cw.boardingschoolmanagement.logic;

import cw.boardingschoolmanagement.app.adaptable.IAdaptable;
import cw.boardingschoolmanagement.app.adaptable.ITypedAdaptable;
import cw.boardingschoolmanagement.app.adaptable.ITypedAdapterSupport;

/**
 * Basis Business Object class
 * 
 * @author Manuel Geier
 */
public class CWBo<TBaseClass>
	implements ITypedAdaptable {

	private ITypedAdapterSupport iTypedAdaptableSupport = new ITypedAdapterSupport();
	private TBaseClass baseClass;
	
	public CWBo(TBaseClass baseClass) {
		this.baseClass = baseClass;
	}
	
	public IAdaptable getAdapter(Class adaptableClass) {
		return iTypedAdaptableSupport.getAdapter(adaptableClass);
	}
	
	public <T> T getTypedAdapter(Class<T> adaptableClass) {
		return iTypedAdaptableSupport.getTypedAdapter(adaptableClass);
	}
	
	public TBaseClass getBaseClass() {
		return baseClass;
	}
}
