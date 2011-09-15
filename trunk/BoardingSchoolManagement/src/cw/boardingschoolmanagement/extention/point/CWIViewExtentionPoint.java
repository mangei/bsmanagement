package cw.boardingschoolmanagement.extention.point;

import cw.boardingschoolmanagement.extention.CWITypedExtention;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 *
 * @author Manuel Geier
 */
public interface CWIViewExtentionPoint<TBaseView extends CWView<?>>
        extends CWITypedExtention {

	public void initView(TBaseView baseView);
    public CWView<?> getView();
}
