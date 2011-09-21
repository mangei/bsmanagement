package cw.boardingschoolmanagement.extention.point;

import cw.boardingschoolmanagement.extention.CWITypedExtention;
import cw.boardingschoolmanagement.gui.CWIPresentationModel;

/**
 *
 * @author Manuel Geier
 */
public interface CWIPresentationModelExtentionPoint<TBasePresentationModel extends CWIPresentationModel>
        extends CWITypedExtention {

	public void initPresentationModel(TBasePresentationModel baseModel);
	
}
