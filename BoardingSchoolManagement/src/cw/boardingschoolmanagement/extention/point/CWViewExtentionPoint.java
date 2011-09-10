package cw.boardingschoolmanagement.extention.point;

import cw.boardingschoolmanagement.extention.CWIExtention;

/**
 *
 * @author Manuel Geier
 */
public interface CWViewExtentionPoint<TView>
        extends CWIExtention {

	public Class getViewExtention();
    public void execute(TView view);
}
