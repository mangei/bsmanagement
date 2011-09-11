package cw.boardingschoolmanagement.gui;

import javax.persistence.EntityManager;

public abstract class CWPresentationModel
	implements CWIPresentationModel {

	private EntityManager entityManager;
	
	public CWPresentationModel(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
}
