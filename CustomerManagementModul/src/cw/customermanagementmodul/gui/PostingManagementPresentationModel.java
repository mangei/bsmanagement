package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.customermanagementmodul.pojo.manager.PostingManager;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.app.CalendarUtil;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventObject;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.customermanagementmodul.pojo.Posting;
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.ReversePosting;
import cw.customermanagementmodul.pojo.manager.ReversePostingManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.Icon;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Manuel Geier
 */
public class PostingManagementPresentationModel {

    private Customer customer;

    private Action newAction;
    private Action editAction;
    private Action cancelAction;
//    private Action deleteAction;
    private Action managePostingCategoriesAction;
    private SelectionInList<Posting> postingSelection;
    private ValueModel saldoValue;
    private ValueModel liabilitiesValue;
    private ValueModel assetsValue;
    
    private SelectionInList<String> filterYearSelection;
    private SelectionInList<String> filterMonthSelection;

    public PostingManagementPresentationModel() {
        this(null);
    }

    public PostingManagementPresentationModel(Customer customer) {
        this.customer = customer;

        initModels();
        initEventHandling();
    }

    public void initModels() {
        newAction = new NewAction("Neu", CWUtils.loadIcon("cw/customermanagementmodul/images/money_add.png"));
        editAction = new EditAction("Bearbeiten", CWUtils.loadIcon("cw/customermanagementmodul/images/money_edit.png"));
        cancelAction = new CancelAction("Stornieren", CWUtils.loadIcon("cw/customermanagementmodul/images/money_delete.png"));
//        deleteAction = new DeleteAction("Löschen", CWUtils.loadIcon("cw/customermanagementmodul/images/money_delete.png"));
        managePostingCategoriesAction = new ManagePostingCategoriesAction("Kategorien", CWUtils.loadIcon("cw/customermanagementmodul/images/posting_category.png"));

        postingSelection = new SelectionInList<Posting>(PostingManager.getInstance().getAll(customer));

        saldoValue = new ValueHolder();
        liabilitiesValue = new ValueHolder();
        assetsValue = new ValueHolder();

        List<String> months = new ArrayList<String>();
        months.add("-");
        months.add(CalendarUtil.getMonth(Calendar.JANUARY));
        months.add(CalendarUtil.getMonth(Calendar.FEBRUARY));
        months.add(CalendarUtil.getMonth(Calendar.MARCH));
        months.add(CalendarUtil.getMonth(Calendar.APRIL));
        months.add(CalendarUtil.getMonth(Calendar.MAY));
        months.add(CalendarUtil.getMonth(Calendar.JUNE));
        months.add(CalendarUtil.getMonth(Calendar.JULY));
        months.add(CalendarUtil.getMonth(Calendar.AUGUST));
        months.add(CalendarUtil.getMonth(Calendar.SEPTEMBER));
        months.add(CalendarUtil.getMonth(Calendar.OCTOBER));
        months.add(CalendarUtil.getMonth(Calendar.NOVEMBER));
        months.add(CalendarUtil.getMonth(Calendar.DECEMBER));

        filterYearSelection = new SelectionInList<String>();
        filterMonthSelection = new SelectionInList<String>(months);
        filterMonthSelection.setSelectionIndex(0);
        loadFilterYears();
        filterYearSelection.setSelectionIndex(0);
    }

    private void initEventHandling() {
        postingSelection.addPropertyChangeListener(
                SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
                new SelectionEmptyHandler());
//        postingSelection.addListDataListener(new ListDataListener() {
//
//            public void intervalAdded(ListDataEvent e) {
//                change(e);
//            }
//
//            public void intervalRemoved(ListDataEvent e) {
//                change(e);
//            }
//
//            public void contentsChanged(ListDataEvent e) {
//                change(e);
//            }
//
//            private void change(ListDataEvent e) {
//                calculateValues();
//            }
//        });

        filterYearSelection.addValueChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                updateFilters();
            }
        });

        filterMonthSelection.addValueChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                updateFilters();
            }
        });
        
        updateActionEnablement();
        
        updateEvents();
    }

    public void updateEvents() {
        loadFilterYears();
        updateFilters();
        calculateValues();
    }

    private void calculateValues() {
        List<Posting> list = postingSelection.getList();
        double liabilities=0, assets=0, saldo=0;
        for(int i=0,l=list.size(); i<l; i++) {
            Posting p = list.get(i);
            if(p.isAssets()) {
                assets = assets + p.getAmount();
                saldo = saldo + p.getAmount();
            } else {
                liabilities = liabilities + p.getAmount();
                saldo = saldo - p.getAmount();
            }
        }
        liabilitiesValue.setValue(Double.toString(liabilities));
        assetsValue.setValue(Double.toString(assets));
        saldoValue.setValue(Double.toString(saldo));
    }

    private void loadFilterYears() {
        String oldSelection = filterYearSelection.getSelection();

        int size = filterYearSelection.getList().size();
        if(size > 0) {
            filterYearSelection.getList().clear();
            filterYearSelection.fireIntervalRemoved(0, size-1);
        }

        List<String> years = PostingManager.getInstance().getYears();
        years.add(0, "-");
        
        filterYearSelection.getList().addAll(years);
        
        size = filterYearSelection.getList().size();
        if(size > 0) {
            filterYearSelection.fireIntervalAdded(0, size-1);
        }

        filterYearSelection.setSelection(oldSelection);
    }

    public void updateFilters() {
        int size = postingSelection.getList().size();
        if(size > 0) {
            postingSelection.getList().clear();
            postingSelection.fireIntervalRemoved(0, size-1);
        }

        boolean filterPassed;
        Posting p;
        int year;
        GregorianCalendar calendar = new GregorianCalendar();
        List<Posting> all = PostingManager.getInstance().getAll(customer);

        if((filterYearSelection.getSelectionIndex() == 0 && filterMonthSelection.getSelectionIndex() == 0)) {
            postingSelection.getList().addAll(all);
        } else {

            for(int i=0, l=all.size(); i<l; i++) {

                filterPassed = true;
                p = all.get(i);

                if(p.getPostingEntryDate() != null) {

                    calendar.setTimeInMillis(p.getPostingEntryDate().getTime());


                    if(filterYearSelection.getSelectionIndex() > 0) {

                        String yearStr = filterYearSelection.getSelection();
                        year = Integer.parseInt(yearStr);

                        if(calendar.get(Calendar.YEAR) != year) {
                            filterPassed = false;
                        }
                    }
                    if(filterPassed && filterMonthSelection.getSelectionIndex() > 0) {
                        int monthIdx = filterMonthSelection.getSelectionIndex() - 1;

                        if(calendar.get(Calendar.MONTH) != monthIdx) {
                            filterPassed = false;
                        }
                    }
                } else {
                    filterPassed = false;
                }

                if(filterPassed) {
                    postingSelection.getList().add(p);
                }
            }
        }

        size = postingSelection.getList().size();
        if(size > 0) {
            postingSelection.fireIntervalAdded(0, size-1);
        }
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
            GUIManager.setLoadingScreenText("Formular wird geladen...");
            GUIManager.setLoadingScreenVisible(true);

            new Thread(new Runnable() {

                public void run() {

                    final Posting a = new Posting(customer);
                    final EditPostingPresentationModel model = new EditPostingPresentationModel(a);
                    final EditPostingView editView = new EditPostingView(model);
                    model.addButtonListener(new ButtonListener() {

                        boolean postingAlreadyCreated = false;

                        public void buttonPressed(ButtonEvent evt) {

                            if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                                PostingManager.getInstance().save(a);
                                if (postingAlreadyCreated) {
                                    GUIManager.getStatusbar().setTextAndFadeOut("Buchung wurde aktualisiert.");
                                } else {
                                    GUIManager.getStatusbar().setTextAndFadeOut("Buchung wurde erstellt.");
                                    postingAlreadyCreated = true;
                                    postingSelection.getList().add(a);
                                    int idx = postingSelection.getList().indexOf(a);
                                    postingSelection.fireIntervalAdded(idx, idx);
                                    updateEvents();
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

    private class EditAction
            extends AbstractAction {

        public EditAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            editSelectedItem(e);
        }
    }

    private class CancelAction
            extends AbstractAction {

        public CancelAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.setLoadingScreenText("Buchung wird storniert...");
            GUIManager.setLoadingScreenVisible(true);

            new Thread(new Runnable() {

                public void run() {

                    final ReversePosting reversePosting = new ReversePosting();
                    
                    Posting posting = postingSelection.getSelection();
                    Posting posting2 = new Posting(posting.getCustomer());

                    posting2.setAmount(posting.getAmount());
                    posting2.setLiabilitiesAssets(!posting.isLiabilitiesAssets());
                    posting2.setPostingCategory(posting.getPostingCategory());

                    reversePosting.setPosting(posting);
                    reversePosting.setReversePosting(posting2);


                    final EditReversePostingPresentationModel model = new EditReversePostingPresentationModel(reversePosting);
                    final EditReversePostingView editView = new EditReversePostingView(model);
                    model.addButtonListener(new ButtonListener() {

                        boolean postingAlreadyCreated = false;

                        public void buttonPressed(ButtonEvent evt) {

                            if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                                ReversePostingManager.getInstance().save(reversePosting);
                                if (postingAlreadyCreated) {
                                    GUIManager.getStatusbar().setTextAndFadeOut("Storno wurde aktualisiert.");
                                } else {
                                    GUIManager.getStatusbar().setTextAndFadeOut("Storno wurde erstellt.");
                                    postingAlreadyCreated = true;
                                    postingSelection.getList().add(reversePosting.getReversePosting());
                                    int idx = postingSelection.getList().indexOf(reversePosting.getReversePosting());
                                    postingSelection.fireIntervalAdded(idx, idx);
                                    updateEvents();
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
                    


//                    Posting a = postingSelection.getSelection();
//
//                    a = PostingManager.getInstance().cancelAnAccounting(a);
//                    postingSelection.getList().add(a);
//
//                    GUIManager.getStatusbar().setTextAndFadeOut("Buchung wurde storniert...");
//                    GUIManager.setLoadingScreenVisible(false);
                }
            }).start();
        }
    }

//    private class DeleteAction
//            extends AbstractAction {
//
//        public DeleteAction(String name, Icon icon) {
//            super(name, icon);
//        }
//
//        public void actionPerformed(ActionEvent e) {
//            GUIManager.setLoadingScreenText("Buchung wird gelöscht...");
//            GUIManager.setLoadingScreenVisible(true);
//
//            new Thread(new Runnable() {
//
//                public void run() {
//                    Posting a = postingSelection.getSelection();
//
//                    int idx = postingSelection.getList().indexOf(a);
//                    postingSelection.getList().remove(a);
//                    postingSelection.fireIntervalRemoved(idx, idx);
//                    PostingManager.getInstance().delete(a);
//                    updateEvents();
//
//                    GUIManager.setLoadingScreenVisible(false);
//                }
//            }).start();
//        }
//    }

    private class ManagePostingCategoriesAction
            extends AbstractAction {

        public ManagePostingCategoriesAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.setLoadingScreenText("Buchungskategorien verwalten...");
            GUIManager.setLoadingScreenVisible(true);

            new Thread(new Runnable() {

                public void run() {
                    
                    final PostingCategoryManagementPresentationModel model = new PostingCategoryManagementPresentationModel();
                    final PostingCategoryManagementView view = new PostingCategoryManagementView(model);

//                    final JDialog d = new JDialog(GUIManager.getInstance(), "Kategorien verwalten",false);
//                    d.getContentPane().add(view.buildPanel());
//
//                    d.addWindowListener(new WindowAdapter() {
//                        @Override
//                        public void windowClosing(WindowEvent e) {
//                            d.setVisible(false);
//                            d.dispose();
//                        }
//                    });
//a
//                    d.setVisible(true);


                    GUIManager.changeView(CWComponentFactory.createBackPanel(view.buildPanel()).getPanel(), true);
                    GUIManager.setLoadingScreenVisible(false);
                }
            }).start();
        }
    }

    public TableModel createPostingTableModel(ListModel listModel) {
        return new PostingTableModel(listModel);
    }

    public TableColumnModel createPostingTableColumnModel() {
        return new PostingTableColumnModel();
    }

//    public Action getDeleteAction() {
//        return deleteAction;
//    }

    public Action getEditAction() {
        return editAction;
    }

    public Action getCancelAction() {
        return cancelAction;
    }
    public Action getManagePostingCategoriesAction() {
        return managePostingCategoriesAction;
    }

    public ValueModel getAssetsValue() {
        return assetsValue;
    }

    public ValueModel getLiabilitiesValue() {
        return liabilitiesValue;
    }

    public ValueModel getSaldoValue() {
        return saldoValue;
    }

    public SelectionInList<Posting> getPostingSelection() {
        return postingSelection;
    }

    public Action getNewAction() {
        return newAction;
    }

    public SelectionInList<String> getFilterMonthSelection() {
        return filterMonthSelection;
    }

    public SelectionInList<String> getFilterYearSelection() {
        return filterYearSelection;
    }

    private void editSelectedItem(EventObject e) {
        GUIManager.setLoadingScreenText("Buchung wird geladen...");
        GUIManager.setLoadingScreenVisible(true);

        new Thread(new Runnable() {

            public void run() {
                final Posting a = postingSelection.getSelection();
                final EditPostingPresentationModel model = new EditPostingPresentationModel(a, true);
                final EditPostingView editView = new EditPostingView(model);
                model.addButtonListener(new ButtonListener() {

                    public void buttonPressed(ButtonEvent evt) {
                        if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                            PostingManager.getInstance().save(a);
                            int idx = postingSelection.getList().indexOf(a);
                            postingSelection.fireContentsChanged(idx, idx);
                            GUIManager.getStatusbar().setTextAndFadeOut("Buchung wurde aktualisiert.");
                            updateEvents();
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

    private class PostingTableModel extends AbstractTableAdapter<Posting> {

        private ListModel listModel;

        public PostingTableModel(ListModel listModel) {
            super(listModel);
            this.listModel = listModel;
        }

        @Override
        public int getColumnCount() {
            return 7;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Bezeichnung";
                case 1:
                    return "Betrag";
                case 2:
                    return "Soll";
                case 3:
                    return "Haben";
                case 4:
                    return "Eingangsdatum";
                case 5:
                    return "Buchungsdatum";
                case 6:
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
            Posting a = (Posting) listModel.getElementAt(rowIndex);
            switch (columnIndex) {
                case 0:
                    return a.getDescription();
                case 1:
                    return a.getAmount();
                case 2:
                    return a.isLiabilities() ? a.getAmount() : "";
                case 3:
                    return a.isAssets() ? a.getAmount() : "";
                case 4:
                    return a.getPostingEntryDate();
                case 5:
                    return a.getPostingDate();
                case 6:
                    if(a.getPostingCategory() != null) {
                        return a.getPostingCategory();
                    } else {
                        return "";
                    }
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
        boolean hasSelection = postingSelection.hasSelection();
        editAction.setEnabled(hasSelection);
        cancelAction.setEnabled(hasSelection);
//        deleteAction.setEnabled(hasSelection);
    }

    private final class SelectionEmptyHandler implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateActionEnablement();
        }
    }
}
