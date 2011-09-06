package cw.boardingschoolmanagement.gui;

import javax.persistence.EntityManager;

public abstract class CWPresentationModel {

	private EntityManager entityManager;
	
	public CWPresentationModel(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
}
