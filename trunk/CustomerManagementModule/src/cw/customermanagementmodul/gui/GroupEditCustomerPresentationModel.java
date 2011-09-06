package cw.customermanagementmodul.gui;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.persistence.EntityManager;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueModel;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.CWEditPresentationModel;
import cw.boardingschoolmanagement.gui.CWErrorMessage;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.customermanagementmodul.persistence.Customer;
import cw.customermanagementmodul.persistence.Group;
import cw.customermanagementmodul.persistence.GroupPM;

/**
 *
 * @author ManuelG
 */
public class GroupEditCustomerPresentationModel
	extends CWEditPresentationModel<Group> {

    private Customer customer;
    private ValueModel unsaved;
    private CWHeaderInfo headerInfo;
    
    private SelectionInList<Group> selectionCustomerGroups;
    private SelectionInList<Group> selectionGroups;
    private Action addGroupAction;
    private Action removeGroupAction;

    private SaveListener saveListener;
    private PropertyChangeListener addGroupActionListener;
    private PropertyChangeListener removeGroupActionListener;

    public GroupEditCustomerPresentationModel(Customer customer, ValueModel unsaved, EntityManager entityManager) {
    	super(entityManager);
        this.customer = customer;
        this.unsaved = unsaved;

        initModels();
        initEventHandling();
    }

    private void initModels() {
        selectionCustomerGroups = new SelectionInList<Group>(GroupPM.getInstance().getAllGroupsByCustomer(customer, getEntityManager()));
        
        List<Group> otherGroups = GroupPM.getInstance().getAll(getEntityManager());
        otherGroups.removeAll(selectionCustomerGroups.getList());
        selectionGroups = new SelectionInList<Group>(otherGroups);

        addGroupAction = new AddGroupAction("Hinzufuegen", CWUtils.loadIcon("cw/customermanagementmodul/images/arrow_left.png"));
        removeGroupAction = new RemoveGroupAction("Entfernen", CWUtils.loadIcon("cw/customermanagementmodul/images/arrow_right.png"));
    
        headerInfo = new CWHeaderInfo(
                "Gruppenzugehörigkeit",
                "Hier können Sie die Gruppenzugehörigkeiten fuer den Kunden einstellen.",
                CWUtils.loadIcon("cw/customermanagementmodul/images/group.png"),
                CWUtils.loadIcon("cw/customermanagementmodul/images/group.png")
        );
    }

    private void initEventHandling() {
        selectionCustomerGroups.addValueChangeListener(removeGroupActionListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                removeGroupAction.setEnabled(selectionCustomerGroups.hasSelection());
            }
        });
        removeGroupAction.setEnabled(selectionCustomerGroups.hasSelection());
        
        selectionGroups.addValueChangeListener(addGroupActionListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                addGroupAction.setEnabled(selectionGroups.hasSelection());
            }
        });
        addGroupAction.setEnabled(selectionGroups.hasSelection());

        saveListener = new SaveListener();
        selectionCustomerGroups.addListDataListener(saveListener);
        selectionGroups.addListDataListener(saveListener);
    }

    public void dispose() {
        selectionCustomerGroups.removeListDataListener(saveListener);
        selectionGroups.removeListDataListener(saveListener);
        selectionCustomerGroups.removeValueChangeListener(removeGroupActionListener);
        selectionGroups.removeValueChangeListener(addGroupActionListener);
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

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

	public boolean validate(List<CWErrorMessage> errorMessages) {
		return true;
	}

	public boolean save() {
		return true;
	}

	public void cancel() {		
	}

}
