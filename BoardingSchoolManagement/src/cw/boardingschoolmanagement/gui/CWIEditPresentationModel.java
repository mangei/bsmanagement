package cw.boardingschoolmanagement.gui;

import com.jgoodies.validation.ValidationResult;

/**
 *
 * @author Manuel Geier
 */
public interface CWIEditPresentationModel
	extends CWIPresentationModel {
	
	public ValidationResult validate();
	public void save();
	public void cancel();
}
