package cw.boardingschoolmanagement.gui;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.jgoodies.binding.PresentationModel;

import cw.boardingschoolmanagement.extention.point.CWEditViewExtentionPoint;
import cw.boardingschoolmanagement.manager.ModulManager;

public abstract class CWEditPresentationModel<TPersistence>
	extends PresentationModel<TPersistence> {

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
	
	public abstract boolean validate(List<CWErrorMessage> errorMessages);
	public abstract void save();
	public abstract void cancel();
	
	public boolean validateExtentions(List<CWErrorMessage> errorMessages) {
		List<CWEditViewExtentionPoint> exList = (List<CWEditViewExtentionPoint>) ModulManager.getExtentions(CWEditViewExtentionPoint.class);
		
		boolean valid = true;
		
		for(CWEditViewExtentionPoint ex : exList) {
			if(viewClass.equals(ex.getViewExtentionClass().getClass())) {
				if(ex.getModel().validate(errorMessages) == false) {
					valid = false;
				}
			}
		}
		
		return valid;
	}
	
	public void saveExtentions() {
		List<CWEditViewExtentionPoint> exList = (List<CWEditViewExtentionPoint>) ModulManager.getExtentions(CWEditViewExtentionPoint.class);
		
		for(CWEditViewExtentionPoint ex : exList) {
			if(viewClass.equals(ex.getViewExtentionClass().getClass())) {
				ex.getModel().save();
			}
		}
	}
	
	public void cancelExtentions() {
		List<CWEditViewExtentionPoint> exList = (List<CWEditViewExtentionPoint>) ModulManager.getExtentions(CWEditViewExtentionPoint.class);
		
		for(CWEditViewExtentionPoint ex : exList) {
			if(viewClass.equals(ex.getViewExtentionClass().getClass())) {
				ex.getModel().cancel();
			}
		}
	}
	
}
