package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.CWUtils;
import com.jgoodies.binding.list.SelectionInList;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.Group;
import cw.customermanagementmodul.pojo.manager.GroupManager;
import javax.swing.Icon;

/**
 *
 * @author ManuelG
 */
public class GroupManagementPresentationModel {

    private Action newGroupAction;
    private Action editGroupAction;
    private Action removeGroupAction;
    private SelectionInList<Group> groupSelection;
    private SelectionInList<Customer> customerSelection;

    private String headerText;

    public GroupManagementPresentationModel() {
        this("");
    }
    
    public GroupManagementPresentationModel(String headerText) {
        this.headerText = headerText;

        initModels();
        initEventHandling();
    }

    private void initModels() {
        newGroupAction = new NewGroupAction("Neue Gruppe", CWUtils.loadIcon("cw/customermanagementmodul/images/group_add.png"));
        editGroupAction = new EditGroupAction("Gruppe bearbeiten", CWUtils.loadIcon("cw/customermanagementmodul/images/group_edit.png"));
        removeGroupAction = new RemoveGroupAction("Gruppe löschen", CWUtils.loadIcon("cw/customermanagementmodul/images/group_remove.png"));

        groupSelection = new SelectionInList<Group>(GroupManager.getInstance().getAll());
        customerSelection = new SelectionInList<Customer>();
    }

    private void initEventHandling() {
        groupSelection.addPropertyChangeListener(
                SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
                new SelectionEmptyHandler());

        groupSelection.addValueChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                int size = customerSelection.getSize();
                if(size > 0) {
                    customerSelection.getList().clear();
                    customerSelection.fireIntervalRemoved(0, size-1);
                }
                if(groupSelection.hasSelection()) {
                    if(groupSelection.getSelection().getCustomers().size() > 0) {
                        customerSelection.getList().addAll(groupSelection.getSelection().getCustomers());
                        customerSelection.fireIntervalAdded(0, customerSelection.getSize()-1);
                    }
                }
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////
    // Action classes
    ////////////////////////////////////////////////////////////////////////////

    private class NewGroupAction extends AbstractAction {

        public NewGroupAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.setLoadingScreenText("Formular wird geladen...");
            GUIManager.setLoadingScreenVisible(true);

            new Thread(new Runnable() {

                public void run() {

                    final Group group = new Group();

                    final EditGroupPresentationModel model = new EditGroupPresentationModel(group, "Gruppe erstellen");
                    final EditGroupView editView = new EditGroupView(model);
                    model.addButtonListener(new ButtonListener() {

                        boolean customerAlreadyCreated = false;

                        public void buttonPressed(ButtonEvent evt) {
                            if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                                GroupManager.getInstance().save(group);
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
                            if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                                model.removeButtonListener(this);
                                GUIManager.changeToLastView();
                            }
                        }
                        
                    });

                    GUIManager.changeView(editView.buildPanel(), true);
                    GUIManager.setLoadingScreenVisible(false);

                }
            }).start();
        }
    }

    private class EditGroupAction extends AbstractAction {

        public EditGroupAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.setLoadingScreenText("Gruppe wird geladen...");
            GUIManager.setLoadingScreenVisible(true);

            new Thread(new Runnable() {

                public void run() {

                    final Group group = groupSelection.getSelection();

                    final EditGroupPresentationModel model = new EditGroupPresentationModel(group, "Gruppe bearbeiten");
                    final EditGroupView editView = new EditGroupView(model);
                    model.addButtonListener(new ButtonListener() {

                        public void buttonPressed(ButtonEvent evt) {
                            if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                                GroupManager.getInstance().save(group);
                                GUIManager.getStatusbar().setTextAndFadeOut("Gruppe wurde aktualisiert.");
                            }
                            if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                                model.removeButtonListener(this);
                                GUIManager.changeToLastView();
                            }
                        }
                        
                    });

                    GUIManager.changeView(editView.buildPanel(), true);
                    GUIManager.setLoadingScreenVisible(false);

                }
            }).start();
        }
    }

    private class RemoveGroupAction extends AbstractAction {

        public RemoveGroupAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.setLoadingScreenText("Gruppe wird gelöscht...");
            GUIManager.setLoadingScreenVisible(true);

            new Thread(new Runnable() {

                public void run() {
                    Group group = groupSelection.getSelection();

                    String name = group.getName();

                    groupSelection.getList().remove(group);
                    GroupManager.getInstance().remove(group);

                    GUIManager.setLoadingScreenVisible(false);
                    GUIManager.getStatusbar().setTextAndFadeOut("Gruppe '" + name + "' wurde gelöscht.");
                }
            }).start();
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

    public SelectionInList<Customer> getCustomerSelection() {
        return customerSelection;
    }

    public String getHeaderText() {
        return headerText;
    }

}
