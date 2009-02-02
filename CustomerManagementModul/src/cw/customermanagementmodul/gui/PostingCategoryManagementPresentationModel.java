package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.CWUtils;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
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
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.customermanagementmodul.pojo.Posting;
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.PostingCategory;
import cw.customermanagementmodul.pojo.manager.PostingCategoryManager;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Manuel Geier
 */
public class PostingCategoryManagementPresentationModel {

    private Customer customer;

    private Action newAction;
    private Action editAction;
    private Action deleteAction;
    private SelectionInList<PostingCategory> postingCategorySelection;
    private HeaderInfo headerInfo;

    public PostingCategoryManagementPresentationModel() {
        this(null);
    }

    public PostingCategoryManagementPresentationModel(Customer customer) {
        this.customer = customer;

        initModels();
        initEventHandling();
    }

    public void initModels() {
        newAction = new NewAction("Neu", CWUtils.loadIcon("cw/customermanagementmodul/images/posting_category_add.png"));
        editAction = new EditAction("Bearbeiten", CWUtils.loadIcon("cw/customermanagementmodul/images/posting_category_edit.png"));
        deleteAction = new DeleteAction("Löschen", CWUtils.loadIcon("cw/customermanagementmodul/images/posting_category_delete.png"));

        postingCategorySelection = new SelectionInList<PostingCategory>(PostingCategoryManager.getInstance().getAll());

        headerInfo = new HeaderInfo(
                "Buchungskategorien verwalten",
                "Hier können Sie Kategorien die sie für die Buchen benötigen einsehen und verwalten.",
                CWUtils.loadIcon("cw/customermanagementmodul/images/posting_category.png"),
                CWUtils.loadIcon("cw/customermanagementmodul/images/posting_category.png")
                );
    }

    private void initEventHandling() {
        postingCategorySelection.addPropertyChangeListener(
                SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
                new SelectionEmptyHandler());
        updateActionEnablement();
    }


    public void save() {
    }

    public void reset() {
    }

    private class NewAction
            extends AbstractAction {

        public NewAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.getInstance().lockMenu();
            GUIManager.setLoadingScreenText("Formular wird geladen...");
            GUIManager.setLoadingScreenVisible(true);

            new Thread(new Runnable() {

                public void run() {

                    final PostingCategory pc = new PostingCategory();
                    final EditPostingCategoryPresentationModel model = new EditPostingCategoryPresentationModel(
                            pc,
                            new HeaderInfo(
                                "Buchungskategorie erstellen",
                                "Hier können Sie eine neue Buchungskategorie erstellen.",
                                CWUtils.loadIcon("cw/customermanagementmodul/images/posting_category_add.png"),
                                CWUtils.loadIcon("cw/customermanagementmodul/images/posting_category_add.png")
                            ));
                    final EditPostingCategoryView editView = new EditPostingCategoryView(model);
                    model.addButtonListener(new ButtonListener() {

                        boolean postingCategoryAlreadyCreated = false;

                        public void buttonPressed(ButtonEvent evt) {

                            if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                                PostingCategoryManager.getInstance().save(pc);
                                if (postingCategoryAlreadyCreated) {
                                    GUIManager.getStatusbar().setTextAndFadeOut("Kategorie wurde aktualisiert.");
                                } else {
                                    GUIManager.getStatusbar().setTextAndFadeOut("Kategorie wurde erstellt.");
                                    postingCategoryAlreadyCreated = true;
                                    postingCategorySelection.getList().add(pc);
                                }
                            }
                            if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                                model.removeButtonListener(this);
                                GUIManager.changeToLastView();
                                GUIManager.getInstance().unlockMenu();
                            }
                        }
                    });
                    GUIManager.changeView(editView.buildPanel(), true);
                    GUIManager.setLoadingScreenVisible(false);

                }
            }).start();
        }
    }

    private class EditAction
            extends AbstractAction {

        public EditAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            editSelectedItem(e);
        }
    }

    private class DeleteAction
            extends AbstractAction {

        public DeleteAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.setLoadingScreenText("Kategorie wird gelöscht...");
            GUIManager.setLoadingScreenVisible(true);

            new Thread(new Runnable() {

                public void run() {
                    
                    // Securityquestion
                    int i = JOptionPane.showConfirmDialog(null, "Wollen Sie wirklich die ausgewählte Kategorie löschen?", "Buchungskategorie löschen", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (i == JOptionPane.OK_OPTION) {

                        // Get the selected category
                        PostingCategory pc = postingCategorySelection.getSelection();

                        // Remove the category from the list
                        postingCategorySelection.getList().remove(pc);

                        // Delete the category
                        PostingCategoryManager.getInstance().delete(pc);
                    }

                    GUIManager.setLoadingScreenVisible(false);
                }
            }).start();
        }
    }

    public TableModel createPostingCategoryTableModel(ListModel listModel) {
        return new PostingCategoryTableModel(listModel);
    }

    public TableColumnModel createPostingTableColumnModel() {
        return new PostingTableColumnModel();
    }

    public Action getDeleteAction() {
        return deleteAction;
    }

    public Action getEditAction() {
        return editAction;
    }

    public SelectionInList<PostingCategory> getPostingCategorySelection() {
        return postingCategorySelection;
    }

    public Action getNewAction() {
        return newAction;
    }

    public HeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    private void editSelectedItem(EventObject e) {
        GUIManager.getInstance().lockMenu();
        GUIManager.setLoadingScreenText("Kategorie wird geladen...");
        GUIManager.setLoadingScreenVisible(true);

        new Thread(new Runnable() {

            public void run() {
                final PostingCategory pc = postingCategorySelection.getSelection();
                final EditPostingCategoryPresentationModel model = new EditPostingCategoryPresentationModel(
                        pc,
                        new HeaderInfo(
                            "Buchungskategorie bearbeiten",
                            "Hier können Sie eine Buchungskategorie bearbeiten.",
                            CWUtils.loadIcon("cw/customermanagementmodul/images/posting_category_edit.png"),
                            CWUtils.loadIcon("cw/customermanagementmodul/images/posting_category_edit.png")
                        ));
                final EditPostingCategoryView editView = new EditPostingCategoryView(model);
                model.addButtonListener(new ButtonListener() {

                    public void buttonPressed(ButtonEvent evt) {
                        if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                            PostingCategoryManager.getInstance().save(pc);
                            GUIManager.getStatusbar().setTextAndFadeOut("Kategorie wurde aktualisiert.");
                        }
                        if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                            model.removeButtonListener(this);
                            GUIManager.changeToLastView();
                            GUIManager.getInstance().unlockMenu();
                        }
                    }
                });
                GUIManager.changeView(editView.buildPanel(), true);
                GUIManager.setLoadingScreenVisible(false);
            }
        }).start();
    }

    private class PostingCategoryTableModel extends AbstractTableAdapter<Posting> {

        private ListModel listModel;

        public PostingCategoryTableModel(ListModel listModel) {
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
                    return "Key";
                default:
                    return "";
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            PostingCategory pc = (PostingCategory) listModel.getElementAt(rowIndex);
            switch (columnIndex) {
                case 0:
                    return pc.getName();
                case 1:
                    return pc.getKey();

                default:
                    return "";
            }
        }
    }

    private class PostingTableColumnModel extends DefaultTableColumnModel {
        
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

    private void updateActionEnablement() {
        boolean hasSelection = postingCategorySelection.hasSelection();
        editAction.setEnabled(hasSelection);
        deleteAction.setEnabled(hasSelection);
    }

    private final class SelectionEmptyHandler implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateActionEnablement();
        }
    }
}
