package cw.boardingschoolmanagement.gui;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.jgoodies.binding.PresentationModel;

import cw.boardingschoolmanagement.extention.point.CWIEditPresentationModelExtentionPoint;
import cw.boardingschoolmanagement.manager.ModuleManager;

public abstract class CWEditPresentationModel<TPersistence>
	extends PresentationModel<TPersistence>
	implements CWIEditPresentationModel {

	public enum Mode {
		NEW, EDIT
	}
	
	private EntityManager entityManager;
	private List<CWErrorMessage> errorMessages = new ArrayList<CWErrorMessage>();
	private Mode mode;
	private Class viewClass;
	
	public CWEditPresentationModel(EntityManager entityManager, Class viewClass) {
		this(null, entityManager, viewClass);
	}

	public CWEditPresentationModel(TPersistence persistence, EntityManager entityManager, Class viewClass) {
		super(persistence);
		this.entityManager = entityManager;
		this.viewClass = viewClass;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public boolean hasErrorMessages() {
		return errorMessages.size() > 0;
	}
	
	public List<CWErrorMessage> getErrorMessages() {
		return errorMessages;
	}
	
	public void clearErrorMessages() {
		errorMessages.clear();
	}
	
	public Mode getMode() {
		return mode;
	}
	
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	
	public boolean isNewMode() {
		return mode == Mode.NEW;
	}
	
	public boolean isEditMode() {
		return mode == Mode.EDIT;
	}
	
    public boolean isValid() {
    	clearErrorMessages();
    	return validate(getErrorMessages());
    }
	
	public boolean validate(List<CWErrorMessage> errorMessages) {
		return true;
	}
	
	public void save() {
		
	}
	
	public void cancel() {
		
	}
	
	public boolean validateExtentions(List<CWErrorMessage> errorMessages) {
		List<CWIEditPresentationModelExtentionPoint> exList = (List<CWIEditPresentationModelExtentionPoint>) ModuleManager.getExtentions(CWIEditPresentationModelExtentionPoint.class);
		
		boolean valid = true;
		
		for(CWIEditPresentationModelExtentionPoint ex : exList) {
			if(ex.getModel().validate(errorMessages) == false) {
				valid = false;
			}
		}
		
		return valid;
	}
	
	public void saveExtentions() {
		List<CWIEditPresentationModelExtentionPoint> exList = (List<CWIEditPresentationModelExtentionPoint>) ModuleManager.getExtentions(CWIEditPresentationModelExtentionPoint.class, this.getClass());
		
		for(CWIEditPresentationModelExtentionPoint ex : exList) {
			ex.getModel().save();
		}
	}
	
	public void cancelExtentions() {
		List<CWIEditPresentationModelExtentionPoint> exList = (List<CWIEditPresentationModelExtentionPoint>) ModuleManager.getExtentions(CWIEditPresentationModelExtentionPoint.class);
		
		for(CWIEditPresentationModelExtentionPoint ex : exList) {
			ex.getModel().cancel();
		}
	}
	
}
