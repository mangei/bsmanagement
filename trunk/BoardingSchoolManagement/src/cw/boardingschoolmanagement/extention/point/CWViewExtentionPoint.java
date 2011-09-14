package cw.boardingschoolmanagement.extention.point;

import cw.boardingschoolmanagement.extention.CWIExtention;

/**
 *
 * @author Manuel Geier
 */
public interface CWViewExtentionPoint<TView>
        extends CWIExtention {

	public Class<?> getViewExtentionClass();
    public void initComponents(TView baseView);
    public void buildView();
    public void dispose();
}
