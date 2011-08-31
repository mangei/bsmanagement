package cw.boardingschoolmanagement.extention.point;

import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.interfaces.Extention;

/**
 *
 * @author Manuel Geier
 */
public interface CWViewExtentionPoint
        extends Extention {

	public Class getExtentionViewClass();
    public void execute(CWView view);
}
