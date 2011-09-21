package cw.roommanagementmodul.gui;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventObject;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.CWEditPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.roommanagementmodul.persistence.Bewohner;
import cw.roommanagementmodul.persistence.GebuehrZuordnung;
import cw.roommanagementmodul.persistence.PMGebuehrZuordnung;

/**
 *
 * @author Dominik
 */
public class GebZuordnungBewohnerPresentationModel
	extends CWEditPresentationModel<Bewohner>
{
	
    private PMGebuehrZuordnung gebuehrZuordnungManager;
    private Action newAction;
    private Action editAction;
    private Action deleteAction;
    private Action backAction;
    private Bewohner bewohner;
    private String headerText;
    private ButtonListenerSupport support;
    private SelectionInList<GebuehrZuordnung> gebuehrZuordnungSelection;
    private CWHeaderInfo headerInfo;
    private SelectionEmptyHandler selectionEmptyHandler;
    private DoubleClickHandler doubleClickHandler;


    public GebZuordnungBewohnerPresentationModel(Bewohner bewohner) {
        super(bewohner);
        doubleClickHandler= new DoubleClickHandler();
        selectionEmptyHandler = new SelectionEmptyHandler();
        this.bewohner = bewohner;
        this.gebuehrZuordnungManager = PMGebuehrZuordnung.getInstance();
        initModels();
        this.initEventHandling();

    }

    GebZuordnungBewohnerPresentationModel(Bewohner bewohner, CWHeaderInfo header) {
        super(bewohner);
        doubleClickHandler= new DoubleClickHandler();
        selectionEmptyHandler = new SelectionEmptyHandler();
        this.headerText = header.getHeaderText();
        this.headerInfo=header;
        this.bewohner = bewohner;
        this.gebuehrZuordnungManager = PMGebuehrZuordnung.getInstance();
        initModels();
        initEventHandling();
    }

    private void initModels() {
        support = new ButtonListenerSupport();
        newAction = new NewAction();
        editAction = new EditAction();
        deleteAction = new DeleteAction();
        backAction = new BackAction();

        //nur vom bestimmten Bewohner!!!
        gebuehrZuordnungSelection = new SelectionInList<GebuehrZuordnung>(gebuehrZuordnungManager.getGebuehrZuordnung(getBewohner()));
        updateActionEnablement();
    }

    private void initEventHandling() {
        getGebuehrZuordnungSelection().addPropertyChangeListener(
                SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
                selectionEmptyHandler);
    }

    private void updateActionEnablement() {
        boolean hasSelection = getGebuehrZuordnungSelection().hasSelection();
        getEditAction().setEnabled(hasSelection);
        getDeleteAction().setEnabled(hasSelection);
    }

    public SelectionInList<GebuehrZuordnung> getGebuehrZuordnungSelection() {
        return gebuehrZuordnungSelection;
    }

    public void removeButtonListener(ButtonListener listener) {
        support.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        support.addButtonListener(listener);
    }

    public Action getNewAction() {
        return newAction;
    }

    public Action getEditAction() {
        return editAction;
    }

    public Action getDeleteAction() {
        return deleteAction;
    }

    public Action getBackAction() {
        return backAction;
    }

    public Bewohner getBewohner() {
        return bewohner;
    }

    public String getHeaderText() {
        return headerText;
    }

    /**
     * @return the headerInfo
     */
    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public void dispose() {
        getGebuehrZuordnungSelection().removeValueChangeListener(selectionEmptyHandler);
        release();
    }

    private class NewAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/add.png"));
        }

        public void actionPerformed(ActionEvent e) {
            gebSelectedItem(e);
        }
    }

    private class EditAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/pencil.png"));
        }

        public void actionPerformed(ActionEvent e) {
            editSelectedItem(e);
        }
    }

    private class DeleteAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/delete.png"));
        }

        public void actionPerformed(ActionEvent e) {

            int check = JOptionPane.showConfirmDialog(null, "Gebuehren Zuordnung wirklich loeschen?", "Loeschen", JOptionPane.YES_NO_OPTION);
            if (check == JOptionPane.YES_OPTION) {
                final GebuehrZuordnung gb = gebuehrZuordnungSelection.getSelection();
                gebuehrZuordnungManager.delete(gb);
                gebuehrZuordnungSelection.setList(gebuehrZuordnungManager.getGebuehrZuordnung(bewohner));
            }
        }
    }

    private class BackAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/arrow_left.png"));
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.changeToPreviousView();  // Zur Uebersicht wechseln

        }
    }

    private void gebSelectedItem(EventObject e) {
        //final Bewohner b = getBewohnerSelection().getSelection();
        final GebuehrZuordnung gb = new GebuehrZuordnung();
        CustomerModel c = bewohner.getCustomer();
        gb.setBewohner(bewohner);
        final GebBewohnerPresentationModel model = new GebBewohnerPresentationModel(gb, new CWHeaderInfo("Bewohner: "+c.getSurname() + " " + c.getForename(),"Hier koennen Sie einem Bewohner eine Gebuehr zuordnen."));
        final GebBewohnerView gebView = new GebBewohnerView(model);
        model.addButtonListener(new ButtonListener() {

            public void buttonPressed(ButtonEvent evt) {
                if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    gebuehrZuordnungManager.save(gb);
                    gebuehrZuordnungSelection.getList().add(gb);
                    GUIManager.getStatusbar().setTextAndFadeOut("Zuordnung wurde aktualisiert.");
                }
                if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    model.removeButtonListener(this);
                    GUIManager.changeToPreviousView();
                }
            }
        });
        GUIManager.changeViewTo(gebView, true);
    }

    private void editSelectedItem(EventObject e) {
        final GebuehrZuordnung gb = gebuehrZuordnungSelection.getSelection();
        CustomerModel c = bewohner.getCustomer();
        final GebBewohnerPresentationModel model = new GebBewohnerPresentationModel(gb, new CWHeaderInfo("Bewohner: "+c.getSurname() + " " + c.getForename(),"Hier koennen Sie alle Gebuehren verwalten, die zu einem Bewohner zugeordnet sind."));
        final GebBewohnerView editView = new GebBewohnerView(model);
        model.addButtonListener(new ButtonListener() {

            public void buttonPressed(ButtonEvent evt) {
                if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    gebuehrZuordnungManager.save(gb);

                    GUIManager.getStatusbar().setTextAndFadeOut("Zuordnung wurde aktualisiert.");
                }
                if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    model.removeButtonListener(this);
                    GUIManager.changeToPreviousView();
                }
            }
        });
        GUIManager.changeViewTo(editView, true);
    }

    public TableModel createZuordnungTableModel(ListModel listModel) {
        return new ZuordnungTableModel(listModel);
    }

    private final class SelectionEmptyHandler implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateActionEnablement();
        }
    }

    // Event Handling *********************************************************
    public MouseListener getDoubleClickHandler() {
        return doubleClickHandler;
    }

    private final class DoubleClickHandler extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                editSelectedItem(e);
            }
        }
    }

    private class ZuordnungTableModel extends AbstractTableAdapter<GebuehrZuordnung> {

        private ListModel listModel;

        public ZuordnungTableModel(ListModel listModel) {
            super(listModel);
            this.listModel = listModel;
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Gebuehr";
                case 1:
                    return "Von";
                case 2:
                    return "Bis";
                case 3:
                    return "Anmerkung";
                default:
                    return "";
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            GebuehrZuordnung gz = (GebuehrZuordnung) listModel.getElementAt(rowIndex);
            switch (columnIndex) {
                case 0:
                    return gz.getGebuehr();
                case 1:
                    return gz.getVon();
                case 2:
                    return gz.getBis();
                case 3:
                    return gz.getAnmerkung();
                default:
                    return "";
            }

        }
    }
}
