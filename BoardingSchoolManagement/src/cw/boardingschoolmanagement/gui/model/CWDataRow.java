package cw.boardingschoolmanagement.gui.model;


/**
 * Base class for a data row inside the data model.
 * 
 * @author Manuel Geier
 *
 * @param <T> base class inside the data row
 */
public class CWDataRow<T> {

	private T element;
	private CWDataModel<T> dataModel;
	
	public CWDataRow(T element, CWDataModel<T> dataModel) {
		this.element = element;
		this.dataModel = dataModel;
	}
	
	public Object getValue(int columnIndex) {
		return dataModel.getDataFields().get(columnIndex).getValue(this);
	}
	
	public T getElement() {
		return element;
	}
}
