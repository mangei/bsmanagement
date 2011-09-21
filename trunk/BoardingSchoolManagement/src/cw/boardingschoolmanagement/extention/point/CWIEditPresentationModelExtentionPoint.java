package cw.boardingschoolmanagement.extention.point;

import cw.boardingschoolmanagement.gui.CWIEditPresentationModel;

/**
 *
 * @author Manuel Geier
 */
public interface CWIEditPresentationModelExtentionPoint<TBasePresentationModel extends CWIEditPresentationModel>
        extends CWIPresentationModelExtentionPoint<TBasePresentationModel> {
	
	public CWIEditPresentationModel getModel();
}
