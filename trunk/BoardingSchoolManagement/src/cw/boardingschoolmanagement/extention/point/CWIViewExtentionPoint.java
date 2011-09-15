package cw.boardingschoolmanagement.extention.point;

import cw.boardingschoolmanagement.extention.CWITypedExtention;
import cw.boardingschoolmanagement.gui.HomeView;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 *
 * @author Manuel Geier
 */
public interface CWIViewExtentionPoint<TBaseView extends CWView<?>>
        extends CWITypedExtention {

	public void init(TBaseView baseView);
    public CWView<?> getView();
//    public void initComponents(TView baseView);
//    public void buildView();
//    public void dispose();
}
