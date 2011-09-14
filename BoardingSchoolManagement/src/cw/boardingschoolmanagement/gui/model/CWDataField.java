package cw.boardingschoolmanagement.gui.model;

/**
 * Basic data field to show a field.
 * 
 * @author Manuel Geier
 */
public abstract class CWDataField {

	public String fieldName;
	
	public CWDataField() {
	}
	
	public CWDataField(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	
	public abstract Object getValue(CWDataRow<?> cwDataRow);
	
	public CWDataFieldRenderer getFieldRenderer() {
		return null;
	}
	
}
