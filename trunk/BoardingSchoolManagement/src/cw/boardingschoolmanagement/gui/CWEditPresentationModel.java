package cw.boardingschoolmanagement.gui;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.ValidationResultModel;
import com.jgoodies.validation.util.DefaultValidationResultModel;

import cw.boardingschoolmanagement.extention.point.CWIEditPresentationModelExtentionPoint;

public abstract class CWEditPresentationModel<TPersistence>
	extends PresentationModel<TPersistence>
	implements CWIEditPresentationModel {

	public enum Mode {
		NEW, EDIT
	}
	
	private EntityManager entityManager;
	private Mode mode;
	private Class viewClass;
	private List<CWIEditPresentationModelExtentionPoint> extentionList = new ArrayList<CWIEditPresentationModelExtentionPoint>();
	private ValidationResultModel validationResultModel = new DefaultValidationResultModel();
	
	public CWEditPresentationModel(EntityManager entityManager, Class viewClass) {
		this(null, entityManager, viewClass);
	}

	public CWEditPresentationModel(TPersistence persistence, EntityManager entityManager, Class viewClass) {
		super(persistence);
		this.entityManager = entityManager;
		this.viewClass = viewClass;
		
	}
	
	/**
	 * To inject extentions
	 * @param extentionList
	 */
	public void setExtentions(List<CWIEditPresentationModelExtentionPoint> extentionList) {
		this.extentionList = extentionList;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	/**
	 * Returns the validationResultModel
	 * @return
	 */
	public ValidationResultModel getValidationResultModel() {
		return validationResultModel;
	}
	
	/**
	 * 
	 */
	public void clearValidationResultModel() {
		validationResultModel.setResult(ValidationResult.EMPTY);
	}
	
	/**
	 * Returns the mode of the presentation model. Either Mode.NEW or Mode.EDIT
	 * @return edit mode
	 */
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
	
    /**
     * 
     */
	public ValidationResult validate() {
		
		ValidationResult validationResult = new ValidationResult();
		
		// Validate extentions
		for(CWIEditPresentationModelExtentionPoint ex : extentionList) {
			validationResult.addAll(ex.getModel().validate().getMessages());
		}
		
		return validationResult;
	}
	
// Not required
//	public void validateAndAppend(ValidationResult validationResult) {
//		validationResult.addAll(validate().getMessages());
//	}
	
	/**
	 * 
	 */
	public void save() {
		
		// Save extentions
		for(CWIEditPresentationModelExtentionPoint ex : extentionList) {
			ex.getModel().save();
		}
	}
	
	/**
	 * 
	 */
	public void cancel() {
		
		// Cancel extentions
		for(CWIEditPresentationModelExtentionPoint ex : extentionList) {
			ex.getModel().cancel();
		}
	}
	
}
