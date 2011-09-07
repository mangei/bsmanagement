package cw.studentmanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import com.jgoodies.binding.list.SelectionInList;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import cw.studentmanagementmodul.pojo.OrganisationUnit;
import cw.studentmanagementmodul.pojo.manager.OrganisationUnitManager;

/**
 *
 * @author ManuelG
 */
public class DeleteOrganisationUnitPresentationModel
{

    public static final int DELETE_ALL = 0;
    public static final int MOVE_ALL = 1;

    private OrganisationUnit organisationUnit;
    private ButtonListenerSupport buttonListenerSupport;

    private Action okAction;
    private Action cancelAction;
    private Action deleteAllAction;
    private Action moveAllAction;
    private SelectionInList<OrganisationUnit> organisationUnitSelection;
    private int choice;

    public DeleteOrganisationUnitPresentationModel(OrganisationUnit organisationUnit) {
        this.organisationUnit = organisationUnit;

        initModels();
        initEventHandling();
    }

    private void initModels() {
        buttonListenerSupport = new ButtonListenerSupport();
        okAction = new OkAction("Bestaetigen", CWUtils.loadIcon("cw/studentmanagementmodul/images/accept.png"));
        cancelAction = new CancelAction("Abbrechen", CWUtils.loadIcon("cw/studentmanagementmodul/images/cancel.png"));
        deleteAllAction = new DeleteAllAction();
        moveAllAction = new MoveAllAction();
        List<OrganisationUnit> organisationUnitList = OrganisationUnitManager.getInstance().getAll();
        // Remove self
        organisationUnitList.remove(organisationUnit);
        // Remove children
        removeChildren(organisationUnitList, organisationUnit);
        organisationUnitSelection = new SelectionInList<OrganisationUnit>(organisationUnitList);
        choice = DELETE_ALL;

        if(organisationUnitList.size() == 0) {
            moveAllAction.setEnabled(false);
        }
    }

    private void initEventHandling() {
        // Nothing to do
    }

    public void dispose() {
        // Nothing to do
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // Useful methods
    ////////////////////////////////////////////////////////////////////////////

    private void removeChildren(List<OrganisationUnit> list, OrganisationUnit org) {
        if(org == null) {
            return;
        }

        OrganisationUnit child;
        for(int i=0,l=org.getChildren().size(); i<l; i++) {
            child = org.getChildren().get(i);
            list.remove(child);
            removeChildren(list, child);
        }
    }


    ////////////////////////////////////////////////////////////////////////////
    // Action classes
    ////////////////////////////////////////////////////////////////////////////

    private class DeleteAllAction extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            choice = DELETE_ALL;
        }
    }

    private class MoveAllAction extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            choice = MOVE_ALL;
        }
    }

    private class OkAction extends AbstractAction {

        public OkAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            buttonListenerSupport.fireButtonPressed(new ButtonEvent(ButtonEvent.OK_BUTTON));
        }
    }

    private class CancelAction extends AbstractAction {

        public CancelAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            buttonListenerSupport.fireButtonPressed(new ButtonEvent(ButtonEvent.CANCEL_BUTTON));
        }
    }


    ////////////////////////////////////////////////////////////////////////////
    // ButtonListenerSupport
    ////////////////////////////////////////////////////////////////////////////

    public void removeButtonListener(ButtonListener listener) {
        buttonListenerSupport.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        buttonListenerSupport.addButtonListener(listener);
    }


    ////////////////////////////////////////////////////////////////////////////
    // Getter methods for the model
    ////////////////////////////////////////////////////////////////////////////

    public Action getCancelAction() {
        return cancelAction;
    }

    public Action getOkAction() {
        return okAction;
    }

    public Action getDeleteAllAction() {
        return deleteAllAction;
    }

    public Action getMoveAllAction() {
        return moveAllAction;
    }

    public SelectionInList<OrganisationUnit> getOrganisationUnitSelection() {
        return organisationUnitSelection;
    }

    public int getChoice() {
        return choice;
    }

}
