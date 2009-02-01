/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;


import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;
import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.manager.GUIManager;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventObject;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;
import cw.roommanagementmodul.pojo.manager.GebuehrenManager;
import cw.roommanagementmodul.pojo.Gebuehr;
import cw.roommanagementmodul.pojo.manager.GebuehrenKatManager;

/**
 *
 * @author Dominik
 */
public class GebuehrenPresentationModel extends PresentationModel<GebuehrenManager> {

    private GebuehrenManager gebuehrenManager;
    private Action newAction;
    private Action editAction;
    private Action deleteAction;
    private Action kategorieAction;
    private Action tarifAction;
    private String headerText;
    private SelectionInList<Gebuehr> gebuehrenSelection;

    public GebuehrenPresentationModel(GebuehrenManager gebuehrenManager) {
        super(gebuehrenManager);
        this.gebuehrenManager = gebuehrenManager;
        initModels();
        this.initEventHandling();

    }

    public GebuehrenPresentationModel(GebuehrenManager gebuehrenManager, String header) {
        super(gebuehrenManager);
        this.gebuehrenManager = gebuehrenManager;
        this.headerText = header;
        initModels();
        this.initEventHandling();
    }

    private void initEventHandling() {
        gebuehrenSelection.addPropertyChangeListener(
                SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
                new SelectionEmptyHandler());
    }

    private void initModels() {
        newAction = new NewAction();
        editAction = new EditAction();
        deleteAction = new DeleteAction();
        kategorieAction = new KategorieAction();
        tarifAction = new TarifAction();


        gebuehrenSelection = new SelectionInList<Gebuehr>(gebuehrenManager.getAll());
        updateActionEnablement();
    }

    private void updateActionEnablement() {
        boolean hasSelection = gebuehrenSelection.hasSelection();
        getEditAction().setEnabled(hasSelection);
        getDeleteAction().setEnabled(hasSelection);
        getTarifAction().setEnabled(hasSelection);
    }

    public Action getNewAction() {
        return newAction;
    }

    public Action getKategorieAction() {
        return kategorieAction;
    }

    public Action getEditAction() {
        return editAction;
    }

    public Action getTarifAction() {
        return tarifAction;
    }

    public Action getDeleteAction() {
        return deleteAction;
    }

    public SelectionInList<Gebuehr> getGebuehrenSelection() {
        return gebuehrenSelection;
    }

    public String getHeaderText() {
        return headerText;
    }

    private class NewAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/money_add.png"));
        }

        public void actionPerformed(ActionEvent e) {
            final Gebuehr g = new Gebuehr();
            final EditGebuehrenPresentationModel model = new EditGebuehrenPresentationModel(g, "Gebühr erstellen");
            final EditGebuehrenView editView = new EditGebuehrenView(model);
            model.addButtonListener(new ButtonListener() {

                public void buttonPressed(ButtonEvent evt) {
                    if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        gebuehrenManager.save(g);
                    }
                    if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        model.removeButtonListener(this);
                        getGebuehrenSelection().setList(gebuehrenManager.getAll());
                        GUIManager.changeToLastView();
                        GUIManager.getStatusbar().setTextAndFadeOut("Gebuehr wurde erstellt.");
                    }
                }
            });
            GUIManager.changeView(editView.buildPanel(), true);


        }
    }

    private class KategorieAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/category.png"));
        }

        public void actionPerformed(ActionEvent e) {
            final GebuehrenKatManager gebKatManager =GebuehrenKatManager.getInstance();
            final GebuehrenKategoriePresentationModel model = new GebuehrenKategoriePresentationModel(gebKatManager, "Kategorien verwalten");
            final GebuehrenKategorieView editView = new GebuehrenKategorieView(model);
            model.addButtonListener(new ButtonListener() {

                public void buttonPressed(ButtonEvent evt) {
                    if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        //gebuehrenManager.saveGebuehr(g);
                    }
                    if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
//                        model.removeButtonListener(this);
//                        getGebuehrenSelection().setList(gebuehrenManager.getGebuehr());
//                        GUIManager.changeToLastView();
//                        GUIManager.getStatusbar().setTextAndFadeOut("Gebuehr wurde erstellt.");
                    }
                }
            });
            GUIManager.changeView(editView.buildPanel(), true);


        }
    }

    private class TarifAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/coins.png"));
        }

        public void actionPerformed(ActionEvent e) {
            Gebuehr g = gebuehrenSelection.getSelection();
            final TarifPresentationModel model = new TarifPresentationModel(g, "Tarif Übersicht: " + g.getName());
            final TarifView editView = new TarifView(model);
            model.addButtonListener(new ButtonListener() {

                public void buttonPressed(ButtonEvent evt) {
                    if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        //gebuehrenManager.saveGebuehr(g);
                    }
                    if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
//                        model.removeButtonListener(this);
//                        getGebuehrenSelection().setList(gebuehrenManager.getGebuehr());
//                        GUIManager.changeToLastView();
//                        GUIManager.getStatusbar().setTextAndFadeOut("Gebuehr wurde erstellt.");
                    }
                }
            });
            GUIManager.changeView(editView.buildPanel(), true);


        }
    }

    private class EditAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/money_edit.png"));
        }

        public void actionPerformed(ActionEvent e) {
            editSelectedItem(e);
        }
    }

    private class DeleteAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/money_delete.png"));
        }

        public void actionPerformed(ActionEvent e) {
            Gebuehr g = getGebuehrenSelection().getSelection();

//            if (g.getTarifList() != null || g.getTarifList().size() != 0) {

//                int i = JOptionPane.showConfirmDialog(null, "Gebühr  löschen?", "Löschen", JOptionPane.OK_CANCEL_OPTION);
//                if (i == JOptionPane.OK_OPTION) {
//                }

                gebuehrenManager.delete(g);
                gebuehrenSelection.setList(gebuehrenManager.getAll());
            
        }
    }

        private final class SelectionEmptyHandler implements PropertyChangeListener {

            public void propertyChange(PropertyChangeEvent evt) {
                updateActionEnablement();
            }
        }

        // Event Handling *********************************************************
        public MouseListener getDoubleClickHandler() {
            return new DoubleClickHandler();
        }

        private final class DoubleClickHandler extends MouseAdapter {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                    editSelectedItem(e);
                }
            }
        }

        private void editSelectedItem(EventObject e) {
            final Gebuehr g = getGebuehrenSelection().getSelection();
            final EditGebuehrenPresentationModel model = new EditGebuehrenPresentationModel(g, "Gebühr bearbeiten");
            final EditGebuehrenView editView = new EditGebuehrenView(model);
            model.addButtonListener(new ButtonListener() {

                public void buttonPressed(ButtonEvent evt) {
                    if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        gebuehrenManager.save(g);

                        GUIManager.getStatusbar().setTextAndFadeOut("Zimmer wurde aktualisiert.");
                    }
                    if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        model.removeButtonListener(this);
                        GUIManager.changeToLastView();
                    }
                }
            });
            GUIManager.changeView(editView.buildPanel(), true);
        }

        public TableModel createGebuehrenTableModel(ListModel listModel) {
            return new GebuehrenTableModel(listModel);
        }

        private class GebuehrenTableModel extends AbstractTableAdapter<Gebuehr> {

            private ListModel listModel;

            public GebuehrenTableModel(ListModel listModel) {
                super(listModel);
                this.listModel = listModel;
            }

            @Override
            public int getColumnCount() {
                return 2;
            }

            @Override
            public String getColumnName(int column) {
                switch (column) {
                    case 0:
                        return "Name";
                    case 1:
                        return "Kategorie";
                    default:
                        return "";
                }
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                Gebuehr g = (Gebuehr) listModel.getElementAt(rowIndex);
                switch (columnIndex) {
                    case 0:
                        return g.getName();
                    case 1:
                        if (g.getGebKat() != null) {
                            return g.getGebKat().getName();
                        }
                    default:
                        return "";
                }

            }
        }
    }