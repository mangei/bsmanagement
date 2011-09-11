package cw.boardingschoolmanagement.extention.point;

import cw.boardingschoolmanagement.extention.CWIExtention;
import cw.boardingschoolmanagement.gui.CWEditPresentationModel;

/**
 *
 * @author Manuel Geier
 */
public interface CWViewExtentionPoint<TView>
        extends CWIExtention {

	public Class getViewExtentionClass();
    public void execute(TView view);
}
