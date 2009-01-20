package cw.customermanagementmodul.gui;

import com.jgoodies.binding.list.SelectionInList;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.event.ListDataEvent;
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.Group;
import cw.customermanagementmodul.pojo.manager.GroupManager;
import cw.boardingschoolmanagement.app.CWUtils;
import com.jgoodies.binding.value.ValueModel;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.Icon;
import javax.swing.event.ListDataListener;

/**
 *
 * @author ManuelG
 */
public class GroupEditCustomerTabGUIExtentionPresentationModel {

    private Customer customer;
    private ValueModel unsaved;
    
    private SelectionInList<Group> selectionCustomerGroups;
    private SelectionInList<Group> selectionGroups;
    private Action addGroupAction;
    private Action removeGroupAction;

    public GroupEditCustomerTabGUIExtentionPresentationModel(Customer customer, ValueModel unsaved) {
        this.customer = customer;
        this.unsaved = unsaved;

        initModels();
        initEventHandling();
    }

    private void initModels() {
        selectionCustomerGroups = new SelectionInList<Group>(customer.getGroups());
        
        List<Group> otherGroups = GroupManager.getInstance().getAll();
        otherGroups.removeAll(selectionCustomerGroups.getList());
        selectionGroups = new SelectionInList<Group>(otherGroups);

        addGroupAction = new AddGroupAction("Hinzufügen", CWUtils.loadIcon("cw/customermanagementmodul/images/arrow_left.png"));
        removeGroupAction = new RemoveGroupAction("Entfernen", CWUtils.loadIcon("cw/customermanagementmodul/images/arrow_right.png"));
    }

    private void initEventHandling() {
        selectionCustomerGroups.addValueChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                removeGroupAction.setEnabled(selectionCustomerGroups.hasSelection());
            }
        });
        removeGroupAction.setEnabled(selectionCustomerGroups.hasSelection());
        
        selectionGroups.addValueChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                addGroupAction.setEnabled(selectionGroups.hasSelection());
            }
        });
        addGroupAction.setEnabled(selectionGroups.hasSelection());

        SaveListener saveListener = new SaveListener();
        selectionCustomerGroups.addListDataListener(saveListener);
        selectionGroups.addListDataListener(saveListener);
    }
    

    ////////////////////////////////////////////////////////////////////////////
    // Action classes
    ////////////////////////////////////////////////////////////////////////////

    private class AddGroupAction extends AbstractAction {

        public AddGroupAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            if(selectionGroups.hasSelection()) {
                Group g = selectionGroups.getSelection();

                // Remove from the available group list
                int index = selectionGroups.getList().indexOf(g);
                selectionGroups.getList().remove(index);
                selectionGroups.fireIntervalRemoved(index, index);

                // Add to the customer group list
                selectionCustomerGroups.getList().add(g);
                selectionCustomerGroups.fireIntervalAdded(selectionCustomerGroups.getSize()-1, selectionCustomerGroups.getSize()-1);

                // Add the customer to the group
                g.getCustomers().add(customer);
            }
        }
    }

    private class RemoveGroupAction extends AbstractAction {

        public RemoveGroupAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            if(selectionCustomerGroups.hasSelection()) {
                Group g = selectionCustomerGroups.getSelection();

                // Remove from the customer groups list
                int index = selectionCustomerGroups.getList().indexOf(g);
                selectionCustomerGroups.getList().remove(index);
                selectionCustomerGroups.fireIntervalRemoved(index, index);

                // Add to the available groups list
                selectionGroups.getList().add(g);
                selectionGroups.fireIntervalAdded(selectionGroups.getSize()-1, selectionGroups.getSize()-1);

                // Remove customer from the group
                g.getCustomers().remove(customer);
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // Other classes
    ////////////////////////////////////////////////////////////////////////////

    private class SaveListener implements PropertyChangeListener, ListDataListener {
        public void propertyChange(PropertyChangeEvent evt) {
            setUnsaved();
        }

        public void intervalAdded(ListDataEvent e) {
            setUnsaved();
        }

        public void intervalRemoved(ListDataEvent e) {
            setUnsaved();
        }

        public void contentsChanged(ListDataEvent e) {
            setUnsaved();
        }

        private void setUnsaved() {
            unsaved.setValue(true);
        }
    }


    ////////////////////////////////////////////////////////////////////////////
    // Getter methods for the model
    ////////////////////////////////////////////////////////////////////////////

    public Action getAddGroupAction() {
        return addGroupAction;
    }

    public Action getRemoveGroupAction() {
        return removeGroupAction;
    }

    public SelectionInList<Group> getSelectionCustomerGroups() {
        return selectionCustomerGroups;
    }

    public SelectionInList<Group> getSelectionGroups() {
        return selectionGroups;
    }

}
