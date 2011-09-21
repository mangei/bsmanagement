package cw.boardingschoolmanagement.gui;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import cw.boardingschoolmanagement.extention.point.CWIPresentationModelExtentionPoint;

public abstract class CWPresentationModel
	implements CWIPresentationModel {

	private EntityManager entityManager;
	List<CWIPresentationModelExtentionPoint> extentionList = new ArrayList<CWIPresentationModelExtentionPoint>();
	
	public CWPresentationModel(EntityManager entityManager) {
		this.entityManager = entityManager;
		
//		loadExtentions();
	}
	
	/**
	 * To inject extentions
	 * @param extentionList
	 */
	public void setExtentions(List<CWIPresentationModelExtentionPoint> extentionList) {
		this.extentionList = extentionList;
		for(CWIPresentationModelExtentionPoint ex : extentionList) {
			ex.initPresentationModel(this);
		}
	}
	
// Can't be loaded, because this would create a new instance of the extention
//	private void loadExtentions() {
//		extentionList = (List<CWIPresentationModelExtentionPoint>) ModuleManager.getExtentions(CWIPresentationModelExtentionPoint.class, this.getClass());
//		for(CWIPresentationModelExtentionPoint ex : extentionList) {
//			ex.initPresentationModel(this);
//		}
//	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
}
