package cw.customermanagementmodul.gui;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JOptionPane;

import com.jgoodies.binding.list.SelectionInList;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.CWPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.customermanagementmodul.persistence.Group;
import cw.customermanagementmodul.persistence.GroupPM;

/**
 *
 * @author ManuelG
 */
public class GroupManagementPresentationModel
	extends CWPresentationModel {

    private Action newGroupAction;
    private Action editGroupAction;
    private Action removeGroupAction;
    private SelectionInList<Group> groupSelection;
    private CustomerSelectorPresentationModel customerSelectorPresentationModel;

    private CWHeaderInfo headerInfo;

    private SelectionEmptyHandler selectionEmptyHandler;
    private PropertyChangeListener groupChangeListener;

    public GroupManagementPresentationModel(EntityManager entityManager) {
    	super(entityManager);
        initModels();
        initEventHandling();
    }

    private void initModels() {
        newGroupAction = new NewGroupAction("Neue Gruppe", CWUtils.loadIcon("cw/customermanagementmodul/images/group_add.png"));
        editGroupAction = new EditGroupAction("Gruppe bearbeiten", CWUtils.loadIcon("cw/customermanagementmodul/images/group_edit.png"));
        removeGroupAction = new RemoveGroupAction("Gruppe löschen", CWUtils.loadIcon("cw/customermanagementmodul/images/group_remove.png"));

        groupSelection = new SelectionInList<Group>(GroupPM.getInstance().getAll(getEntityManager()));

        customerSelectorPresentationModel = new CustomerSelectorPresentationModel(
                new ArrayList(),
                false,
                "cw.customerboardingmanagement.GroupManangementView.customerTableState",
                getEntityManager()
                );

        headerInfo = new CWHeaderInfo(
                "Gruppen verwalten",
                "Sie befinden sich Gruppenverwaltungsbereich. Hier haben Sie einen Ueberblick ueber alle Gruppen und den zugewiesenen Kunden.",
                CWUtils.loadIcon("cw/customermanagementmodul/images/group.png"),
                CWUtils.loadIcon("cw/customermanagementmodul/images/group.png")
        );
    }

    private void initEventHandling() {
        groupSelection.addPropertyChangeListener(
                SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
                selectionEmptyHandler = new SelectionEmptyHandler());

        groupSelection.addValueChangeListener(groupChangeListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                
//                int size = customerSelection.getSize();
//                if(size > 0) {
//                    customerSelection.getList().clear();
//                    customerSelection.fireIntervalRemoved(0, size-1);
//                }
                if(groupSelection.hasSelection()) {
                    if(groupSelection.getSelection().getCustomers().size() > 0) {
                        customerSelectorPresentationModel.setCustomers(groupSelection.getSelection().getCustomers());
//                        customerSelection.getList().addAll(groupSelection.getSelection().getCustomers());
//                        customerSelection.fireIntervalAdded(0, customerSelection.getSize()-1);
                    } else {
                        customerSelectorPresentationModel.setCustomers(null);
                    }
                }  else {
                    customerSelectorPresentationModel.setCustomers(null);
                }

            }
        });
        updateActionEnablement();
    }

    public void dispose() {
        groupSelection.removePropertyChangeListener(selectionEmptyHandler);
        groupSelection.removeValueChangeListener(groupChangeListener);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Action classes
    ////////////////////////////////////////////////////////////////////////////

    private class NewGroupAction extends AbstractAction {

        public NewGroupAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.getInstance().lockMenu();
            GUIManager.setLoadingScreenText("Formular wird geladen...");
            GUIManager.setLoadingScreenVisible(true);

            final Group group = GroupPM.getInstance().create(getEntityManager());

            final EditGroupPresentationModel model = new EditGroupPresentationModel(
                    group,
                    new CWHeaderInfo("Gruppe erstellen"),
                    getEntityManager());
            final EditGroupView editView = new EditGroupView(model);

            model.addButtonListener(new ButtonListener() {
                boolean customerAlreadyCreated = false;

                public void buttonPressed(ButtonEvent evt) {
                    if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        
                        if (customerAlreadyCreated) {
                            GUIManager.getStatusbar().setTextAndFadeOut("Gruppe wurde aktualisiert.");
                        } else {
                            GUIManager.getStatusbar().setTextAndFadeOut("Gruppe wurde erstellt.");
                            customerAlreadyCreated = true;
                            groupSelection.getList().add(group);
                            int idx = groupSelection.getList().indexOf(group);
                            groupSelection.fireIntervalAdded(idx, idx);
                        }
                    }
                    if(evt.getType() == ButtonEvent.EXIT_BUTTON) {
                    	getEntityManager().getTransaction().begin();
                    	GroupPM.getInstance().remove(group, getEntityManager());
                    	getEntityManager().getTransaction().commit();
                    }
                    if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        model.removeButtonListener(this);
                        GUIManager.changeToLastView();
                        GUIManager.getInstance().unlockMenu();
                    }
                }

            });

            GUIManager.changeView(editView, true);
            GUIManager.setLoadingScreenVisible(false);

        }
    }

    private class EditGroupAction extends AbstractAction {

        public EditGroupAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.getInstance().lockMenu();
            GUIManager.setLoadingScreenText("Gruppe wird geladen...");
            GUIManager.setLoadingScreenVisible(true);

            final Group group = groupSelection.getSelection();

            final EditGroupPresentationModel model = new EditGroupPresentationModel(
                    group,
                    new CWHeaderInfo("Gruppe bearbeiten"),
                    getEntityManager()
            );
            final EditGroupView editView = new EditGroupView(model);
            model.addButtonListener(new ButtonListener() {

                public void buttonPressed(ButtonEvent evt) {
                    if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {

                        GUIManager.getStatusbar().setTextAndFadeOut("Gruppe wurde aktualisiert.");
                    }
                    if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        model.removeButtonListener(this);
                        GUIManager.changeToLastView();
                        GUIManager.getInstance().unlockMenu();
                    }
                }

            });

            GUIManager.changeView(editView, true);
            GUIManager.setLoadingScreenVisible(false);

        }
    }

    private class RemoveGroupAction extends AbstractAction {

        public RemoveGroupAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.setLoadingScreenText("Gruppe löschen...");
            GUIManager.setLoadingScreenVisible(true);

            Group group = groupSelection.getSelection();

            int i = JOptionPane.showConfirmDialog(null, "Wollen Sie wirklich die Gruppe '" + group.getName() + "' löschen?", "Gruppe löschen", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (i == JOptionPane.OK_OPTION) {
                
                String name = group.getName();

                groupSelection.getList().remove(group);
                customerSelectorPresentationModel.getCustomerSelection().getList().clear();
                
                getEntityManager().getTransaction().begin();
                
                GroupPM.getInstance().remove(group, getEntityManager());
                
                getEntityManager().getTransaction().commit();


                GUIManager.getStatusbar().setTextAndFadeOut("Gruppe '" + name + "' wurde gelöscht.");
            }

            GUIManager.setLoadingScreenVisible(false);
        }
    }


    private final class SelectionEmptyHandler implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateActionEnablement();
        }
    }

    private void updateActionEnablement() {
        boolean hasSelection = groupSelection.hasSelection();
        editGroupAction.setEnabled(hasSelection);
        removeGroupAction.setEnabled(hasSelection);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Getter methods for the model
    ////////////////////////////////////////////////////////////////////////////

    public Action getEditGroupAction() {
        return editGroupAction;
    }

    public Action getNewGroupAction() {
        return newGroupAction;
    }

    public Action getRemoveGroupAction() {
        return removeGroupAction;
    }

    public SelectionInList<Group> getGroupSelection() {
        return groupSelection;
    }

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public CustomerSelectorPresentationModel getCustomerSelectorPresentationModel() {
        return customerSelectorPresentationModel;
    }

}
