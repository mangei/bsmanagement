package cw.boardingschoolmanagement.gui.model;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueModel;

import cw.boardingschoolmanagement.extention.point.CWIDataModelExtentionPoint;
import cw.boardingschoolmanagement.manager.ModuleManager;

/**
 * Base data model
 * 
 * @author Manuel Geier
 *
 * @param <T> base class inside the data model
 */
public class CWDataModel<T> extends AbstractTableModel
	implements ListModel<T> {

	private Class<T> baseClass;
	private List<CWDataRow<T>> rows = new ArrayList<CWDataRow<T>>();
	private List<CWDataField> fields = new ArrayList<CWDataField>();
	private SelectionInList<T> selectionInList;
	private List<ListDataListener> listDataListenerList = new ArrayList<ListDataListener>();
	
	public CWDataModel(List<T> list, Class<T> baseClass) {
		this.baseClass = baseClass;

		selectionInList = new SelectionInList<T>(this);
		
		loadDataFields();
		
		if(list != null) {
			for(T t: list) {
				rows.add(new CWDataRow<T>(t, this));
			}
		}
	}
	
	public List<CWDataField> getDataFields() {
		return fields;
	}
	
//	public List<T> getList() {
//		return list;
//	}
	
	public CWDataRow<T> get(int index) {
		return rows.get(index);
	}
	
	@Override
	public int getRowCount() {
		return rows.size();
	}

	@Override
	public int getColumnCount() {
		return fields.size();
	}
	
	@Override
	public String getColumnName(int column) {
		return fields.get(column).getFieldName();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return rows.get(rowIndex).getValue(columnIndex);
	}
	
	private void loadDataFields() {
		
		List<CWIDataModelExtentionPoint> exList = ModuleManager.getExtentions(CWIDataModelExtentionPoint.class);
		for(CWIDataModelExtentionPoint ex : exList) {
			if(ex.getBaseClass().equals(baseClass)) {
				fields.addAll(ex.getFieldList());
			}
		}
	}
	
	public synchronized void add(T t) {
		int size = rows.size();
		
		rows.add(new CWDataRow<T>(t, this));
		
		fireDataAdded(size, size);
	}
	
	public synchronized void addAll(List<T> list) {
		
	}
	
	public synchronized void remove(T t) {
		
		int index = rows.indexOf(t);
		if(index != -1) {
			
			rows.add(new CWDataRow<T>(t, this));
			
			fireDataRemoved(index, index);
		}
	}
	
	public synchronized void clear() {
		int size = rows.size();
		if(size > 0) {
		
			rows.clear();
			
			fireDataRemoved(0, size-1);
		}
	}
	
	private void fireDataAdded(int from, int to) {
		ListDataEvent listDataEvent = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, from, to);
		for(ListDataListener l: listDataListenerList) {
			l.intervalAdded(listDataEvent);
		}
		
		TableModelEvent tableModelEvent = new TableModelEvent(this, from, to, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);
		fireTableChanged(tableModelEvent);
	}
	
	private void fireDataRemoved(int from, int to) {
		ListDataEvent listDataEvent = new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, from, to);
		for(ListDataListener l: listDataListenerList) {
			l.intervalAdded(listDataEvent);
		}
		
		TableModelEvent tableModelEvent = new TableModelEvent(this, from, to, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
		fireTableChanged(tableModelEvent);
	}

	@Override
	public int getSize() {
		return rows.size();
	}

	@Override
	public T getElementAt(int index) {
		return rows.get(index).getElement();
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listDataListenerList.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listDataListenerList.remove(l);
	}

	/**
	 * @return
	 * @see com.jgoodies.binding.list.SelectionInList#getSelection()
	 */
	public T getSelection() {
		return selectionInList.getSelection();
	}

	/**
	 * @return
	 * @see com.jgoodies.binding.list.SelectionInList#getSelectionHolder()
	 */
	public ValueModel getSelectionHolder() {
		return selectionInList.getSelectionHolder();
	}

	/**
	 * @return
	 * @see com.jgoodies.binding.list.SelectionInList#getSelectionIndex()
	 */
	public int getSelectionIndex() {
		return selectionInList.getSelectionIndex();
	}

	/**
	 * @return
	 * @see com.jgoodies.binding.list.SelectionInList#getSelectionIndexHolder()
	 */
	public ValueModel getSelectionIndexHolder() {
		return selectionInList.getSelectionIndexHolder();
	}

	/**
	 * @param newSelection
	 * @see com.jgoodies.binding.list.SelectionInList#setSelection(java.lang.Object)
	 */
	public void setSelection(T newSelection) {
		selectionInList.setSelection(newSelection);
	}

	/**
	 * @param newSelectionHolder
	 * @see com.jgoodies.binding.list.SelectionInList#setSelectionHolder(com.jgoodies.binding.value.ValueModel)
	 */
	public void setSelectionHolder(ValueModel newSelectionHolder) {
		selectionInList.setSelectionHolder(newSelectionHolder);
	}

	/**
	 * @param newSelectionIndex
	 * @see com.jgoodies.binding.list.SelectionInList#setSelectionIndex(int)
	 */
	public void setSelectionIndex(int newSelectionIndex) {
		selectionInList.setSelectionIndex(newSelectionIndex);
	}

	/**
	 * @param newSelectionIndexHolder
	 * @see com.jgoodies.binding.list.SelectionInList#setSelectionIndexHolder(com.jgoodies.binding.value.ValueModel)
	 */
	public void setSelectionIndexHolder(ValueModel newSelectionIndexHolder) {
		selectionInList.setSelectionIndexHolder(newSelectionIndexHolder);
	}

	/**
	 * @return
	 * @see com.jgoodies.binding.list.SelectionInList#hasSelection()
	 */
	public boolean hasSelection() {
		return selectionInList.hasSelection();
	}

	/**
	 * @return
	 * @see com.jgoodies.binding.list.SelectionInList#isSelectionEmpty()
	 */
	public boolean isSelectionEmpty() {
		return selectionInList.isSelectionEmpty();
	}

	/**
	 * @param l
	 * @see com.jgoodies.binding.list.SelectionInList#addValueChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addValueChangeListener(PropertyChangeListener l) {
		selectionInList.addValueChangeListener(l);
	}

	/**
	 * @param l
	 * @see com.jgoodies.binding.list.SelectionInList#removeValueChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removeValueChangeListener(PropertyChangeListener l) {
		selectionInList.removeValueChangeListener(l);
	}
	
}
