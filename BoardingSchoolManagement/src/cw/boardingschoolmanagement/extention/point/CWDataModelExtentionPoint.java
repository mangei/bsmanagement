package cw.boardingschoolmanagement.extention.point;

import java.util.List;

import cw.boardingschoolmanagement.extention.CWIExtention;
import cw.boardingschoolmanagement.gui.model.CWDataField;

/**
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public interface CWDataModelExtentionPoint<T> extends CWIExtention {

	public Class<T> getBaseClass();
    public List<CWDataField> getFieldList();
	
}
