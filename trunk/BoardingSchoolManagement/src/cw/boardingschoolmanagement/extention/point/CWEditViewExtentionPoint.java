package cw.boardingschoolmanagement.extention.point;

import cw.boardingschoolmanagement.gui.CWEditPresentationModel;

/**
 *
 * @author Manuel Geier
 */
public interface CWEditViewExtentionPoint<TView>
        extends CWViewExtentionPoint<TView> {

    public CWEditPresentationModel getModel();
}
