package cw.boardingschoolmanagement.app.adaptable;

import javax.persistence.EntityManager;

public interface CWIAdaptable extends ITypedAdaptable {

	public EntityManager getEntityManager();
	
}
