package cw.boardingschoolmanagement.extention.point;

import cw.boardingschoolmanagement.gui.CWEditPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 *
 * @author Manuel Geier
 */
public interface CWEditViewExtentionPoint<TView>
        extends CWViewExtentionPoint<TView> {

    public CWEditPresentationModel<?> getModel();
    public CWView<?> getView();
}
