package cw.boardingschoolmanagement.app;

import java.beans.PropertyChangeListener;

import javax.persistence.EntityManager;

import cw.boardingschoolmanagement.app.adaptable.ITypedAdaptable;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;

/**
 * Basic class for all persistence objects
 * 
 * @author Manuel Geier
 */
public interface CWPersistence extends ITypedAdaptable, AnnotatedClass {

	public EntityManager getEntityManager();
	
	public void addPropertyChangeListener(String property, PropertyChangeListener listener);
	public void removePropertyChangeListener(String property, PropertyChangeListener listener);
}
