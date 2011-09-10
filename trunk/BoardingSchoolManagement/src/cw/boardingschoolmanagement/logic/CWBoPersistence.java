package cw.boardingschoolmanagement.logic;

import cw.boardingschoolmanagement.app.adaptable.CWIAdaptable;
import cw.boardingschoolmanagement.persistence.CWPersistence;

/**
 * Basis Business Object class for persistence
 * 
 * @author Manuel Geier
 */
public abstract class CWBoPersistence<TPersistence extends CWPersistence> 
	extends CWBo {

	private TPersistence persistence;
	
	public CWBoPersistence(CWIAdaptable baseClass) {
		super(baseClass);
	}
	
	/**
	 * Set the persistence
	 */
	public TPersistence getPersistence() {
		return persistence;
	}
	
	/**
	 * Returns the persistence
	 */
	protected final void setPersistence(TPersistence persistence) {
		this.persistence = persistence;
	}
}
