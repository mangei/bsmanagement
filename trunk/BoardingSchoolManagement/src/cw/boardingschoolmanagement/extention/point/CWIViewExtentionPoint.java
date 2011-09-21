package cw.boardingschoolmanagement.extention.point;

import cw.boardingschoolmanagement.extention.CWIMultiTypedExtention;
import cw.boardingschoolmanagement.gui.CWIPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 *
 * @author Manuel Geier
 */
public interface CWIViewExtentionPoint<TBaseView extends CWView<?>, TBasePresentationModel extends CWIPresentationModel>
        extends CWIPresentationModelExtentionPoint<TBasePresentationModel>,
        CWIMultiTypedExtention {

	public void initView(TBaseView baseView);
    public CWView<?> getView();
}
