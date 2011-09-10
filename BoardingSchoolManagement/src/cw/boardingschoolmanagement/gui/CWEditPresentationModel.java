package cw.boardingschoolmanagement.gui;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.jgoodies.binding.PresentationModel;

public abstract class CWEditPresentationModel<T>
	extends PresentationModel<T> {

	public enum Mode {
		NEW, EDIT
	}
	
	private EntityManager entityManager;
	private List<CWErrorMessage> errorMessages = new ArrayList<CWErrorMessage>();
	private Mode mode;
	
	public CWEditPresentationModel(EntityManager entityManager) {
		this(null, entityManager);
	}

	public CWEditPresentationModel(T persistence, EntityManager entityManager) {
		super(persistence);
		this.entityManager = entityManager;
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
	
	public abstract boolean validate(List<CWErrorMessage> errorMessages);
	public abstract boolean save();
	public abstract void cancel();
	
}
