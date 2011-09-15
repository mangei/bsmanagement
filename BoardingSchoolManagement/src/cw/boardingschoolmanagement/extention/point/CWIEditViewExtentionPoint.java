package cw.boardingschoolmanagement.extention.point;

import cw.boardingschoolmanagement.extention.CWIMultiTypedExtention;
import cw.boardingschoolmanagement.gui.CWIEditPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWView;



/**
 *
 * @author Manuel Geier
 */
public interface CWIEditViewExtentionPoint<TBaseView extends CWView<?>, TPresentationModel extends CWIEditPresentationModel>
        extends 
        	CWIEditPresentationModelExtentionPoint<TPresentationModel>,
        	CWIViewExtentionPoint<TBaseView>,
        	CWIMultiTypedExtention {
	
}
