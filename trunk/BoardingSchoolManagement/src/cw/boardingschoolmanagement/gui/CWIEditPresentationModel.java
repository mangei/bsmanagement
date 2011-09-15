package cw.boardingschoolmanagement.gui;

import java.util.List;

import cw.boardingschoolmanagement.gui.CWErrorMessage;

/**
 *
 * @author Manuel Geier
 */
public interface CWIEditPresentationModel
	extends CWIPresentationModel {
	
	public boolean validate(List<CWErrorMessage> errorMessages);
	public void save();
	public void cancel();
}
