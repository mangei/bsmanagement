/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.coursemanagementmodul.gui.model;

import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import com.jgoodies.binding.list.SelectionInList;

import cw.customermanagementmodul.pojo.Customer;

/**
 *
 * @author André Salmhofer
 */
public class CostumerComboBoxModel implements ComboBoxModel {
    private Customer selectedItem;
    private List<Customer> customerList;
    
    public CostumerComboBoxModel(List<Customer> customerList){
        this.customerList = customerList;
    }
    
    public void setSelectedItem(Object anItem) {
        selectedItem = (Customer)anItem;
    }

    public Object getSelectedItem() {
        return selectedItem;
    }

    public int getSize() {
        return customerList.size();
    }

    public Object getElementAt(int index) {
        return customerList.get(index);
    }

    public void addListDataListener(ListDataListener l) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeListDataListener(ListDataListener l) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public SelectionInList<Customer> getSelectionInList(){
        return new SelectionInList(customerList);
    }
}
