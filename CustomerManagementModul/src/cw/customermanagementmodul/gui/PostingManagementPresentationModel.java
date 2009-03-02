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
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
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
    private HeaderInfo headerInfo;

    private Action newAction;
    private Action editAction;
    private Action reversePostingAction;
    private Action balancePostingAction;
//    private Action deleteAction;
    private Action managePostingCategoriesAction;
    private SelectionInList<Posting> postingSelection;
    private ValueModel saldoValue;
    private ValueModel liabilitiesValue;
    private ValueModel assetsValue;
    private ValueModel saldoTotalValue;
    private ValueModel liabilitiesTotalValue;
    private ValueModel assetsTotalValue;
    
    private SelectionInList<String> filterYearSelection;
    private SelectionInList<String> filterMonthSelection;
    private SelectionInList<PostingCategory> filterPostingCategorySelection;

    private SelectionHandler selectionHandler;
    private PropertyChangeListener filterYearSelectionListener;
    private PropertyChangeListener filterMonthSelectionListener;
    private PropertyChangeListener filterPostingCategorySelectionListener;

    public PostingManagementPresentationModel() {
        this(null);
    }

    public PostingManagementPresentationModel(Customer customer) {
        this.customer = customer;

        initModels();
        initEventHandling();
    }

    public void initModels() {
        newAction = new NewAction("Neu", CWUtils.loadIcon("cw/customermanagementmodul/images/posting_add.png"));
        editAction = new EditAction("Bearbeiten", CWUtils.loadIcon("cw/customermanagementmodul/images/posting_edit.png"));
        reversePostingAction = new ReversePostingAction("Stornieren", CWUtils.loadIcon("cw/customermanagementmodul/images/posting_delete.png"));
        balancePostingAction = new BalancePostingAction("Ausgleichen", CWUtils.loadIcon("cw/customermanagementmodul/images/posting_go.png"));
//        deleteAction = new DeleteAction("Löschen", CWUtils.loadIcon("cw/customermanagementmodul/images/posting_delete.png"));
        managePostingCategoriesAction = new ManagePostingCategoriesAction("Kategorien", CWUtils.loadIcon("cw/customermanagementmodul/images/posting_category.png"));

        postingSelection = new SelectionInList<Posting>(PostingManager.getInstance().getAll(customer));

        headerInfo = new HeaderInfo(
                "Buchungsübersicht",
                "Hier sehen sie eine Übersicht über alle Buchungen für Ihren Kunden.",
                CWUtils.loadIcon("cw/customermanagementmodul/images/posting.png"),
                CWUtils.loadIcon("cw/customermanagementmodul/images/posting.png")
        );

        saldoValue = new ValueHolder();
        liabilitiesValue = new ValueHolder();
        assetsValue = new ValueHolder();
        saldoTotalValue = new ValueHolder();
        liabilitiesTotalValue = new ValueHolder();
        assetsTotalValue = new ValueHolder();

        filterMonthSelection = new SelectionInList<String>();
        loadFilterMonths();
        filterMonthSelection.setSelectionIndex(0);
        filterYearSelection = new SelectionInList<String>();
        loadFilterYears();
        filterYearSelection.setSelectionIndex(0);
        filterPostingCategorySelection = new SelectionInList<PostingCategory>();
        loadFilterPostingCategory();
        filterPostingCategorySelection.setSelectionIndex(0);

    }

    private void initEventHandling() {
        postingSelection.addValueChangeListener(selectionHandler = new SelectionHandler());
        updateActionEnablement();

        filterYearSelection.addValueChangeListener(filterYearSelectionListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                updateFilters();
                calculateValues();
            }
        });

        filterMonthSelection.addValueChangeListener(filterMonthSelectionListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                updateFilters();
                calculateValues();
            }
        });

        filterPostingCategorySelection.addValueChangeListener(filterPostingCategorySelectionListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                updateFilters();
                calculateValues();
            }
        });
        
        updateActionEnablement();
        updateEvents();
    }

    void dispose() {
        postingSelection.removeValueChangeListener(selectionHandler);

        filterYearSelection.removeValueChangeListener(filterYearSelectionListener);
        filterMonthSelection.removeValueChangeListener(filterMonthSelectionListener);
        filterPostingCategorySelection.removeValueChangeListener(filterPostingCategorySelectionListener);
    }

    public void updateEvents() {
        loadFilterYears();
        updateFilters();
        calculateValues();
        calculateTotalValues();
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

    private void calculateTotalValues() {
        List<Posting> list = PostingManager.getInstance().getAll(customer);
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
        liabilitiesTotalValue.setValue(Double.toString(liabilities));
        assetsTotalValue.setValue(Double.toString(assets));
        saldoTotalValue.setValue(Double.toString(saldo));
    }

    private void loadFilterYears() {
        String oldSelection = filterYearSelection.getSelection();

        int size = filterYearSelection.getList().size();
        if(size > 0) {
            filterYearSelection.getList().clear();
            filterYearSelection.fireIntervalRemoved(0, size-1);
        }

        List<String> years = PostingManager.getInstance().getYears(customer);
        years.add(0, "-");
        
        filterYearSelection.getList().addAll(years);
        
        size = filterYearSelection.getList().size();
        if(size > 0) {
            filterYearSelection.fireIntervalAdded(0, size-1);
        }

        filterYearSelection.setSelection(oldSelection);
    }

    private void loadFilterMonths() {
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

        filterMonthSelection.setList(months);
    }

    private void loadFilterPostingCategory() {
        filterPostingCategorySelection.setList(PostingCategoryManager.getInstance().getAll());
        PostingCategory c = new PostingCategory("-");
        c.setId(-1l);
        filterPostingCategorySelection.getList().add(0, c);
        c = new PostingCategory("Ohne Kategorie");
        c.setId(-2l);
        filterPostingCategorySelection.getList().add(1, c);
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

        if((filterYearSelection.getSelectionIndex() == 0 
                && filterMonthSelection.getSelectionIndex() == 0
                && filterPostingCategorySelection.getSelectionIndex() == 0)) {
            postingSelection.getList().addAll(all);
        } else {

            for(int i=0, l=all.size(); i<l; i++) {

                filterPassed = true;
                p = all.get(i);

                // Check PostingCategory
                
                if(filterPostingCategorySelection.getSelectionIndex() != 0) {
                    System.out.println("XXX: " + filterPostingCategorySelection.getSelectionIndex());
                    if(filterPostingCategorySelection.getSelectionIndex() == 1) {
                        System.out.println("ZZZ: " + p.getPostingCategory());
                        if(p.getPostingCategory() != null) {
                            filterPassed = false;
                        }
                    } else {
                        if(p.getPostingCategory() == null || !p.getPostingCategory().equals(filterPostingCategorySelection.getSelection())) {
                            filterPassed = false;
                        }
                    }
                }

                System.out.println("passed: " + filterPassed);

                // Check Date
                if(filterPassed
                        && ( filterYearSelection.getSelectionIndex() != 0
                        ||  filterMonthSelection.getSelectionIndex() != 0 ) ) {

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

            final Posting posting = new Posting(customer);
            final EditPostingPresentationModel model = new EditPostingPresentationModel(
                    posting,
                    new HeaderInfo(
                        "Buchung hinzufügen",
                        "<html>Fügen Sie eine neue Buchung für '<b>" + customer.getForename() + " " + customer.getSurname() + "</b>' hinzu.</html>",
                        CWUtils.loadIcon("cw/customermanagementmodul/images/posting_add.png"),
                        CWUtils.loadIcon("cw/customermanagementmodul/images/posting_add.png")
                    ));
            final EditPostingView editView = new EditPostingView(model);
            model.addButtonListener(new ButtonListener() {

                public void buttonPressed(ButtonEvent evt) {

                    if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        PostingManager.getInstance().save(posting);
                        GUIManager.getStatusbar().setTextAndFadeOut("Buchung wurde erstellt.");
                        postingSelection.getList().add(posting);
                        int idx = postingSelection.getList().indexOf(posting);
                        postingSelection.fireIntervalAdded(idx, idx);
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
    }

    private class EditAction
            extends AbstractAction {

        public EditAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            editPosting(postingSelection.getSelection());
        }
    }

    private class ReversePostingAction
            extends AbstractAction {

        public ReversePostingAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            reversePosting(postingSelection.getSelection());
        }
    }

    private class BalancePostingAction
            extends AbstractAction {

        public BalancePostingAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            balancePosting(postingSelection.getSelection());
        }
    }

    private void reversePosting(Posting posting) {
        GUIManager.setLoadingScreenText("Buchung wird storniert...");
        GUIManager.setLoadingScreenVisible(true);
        GUIManager.getInstance().lockMenu();

        final Posting reversePosting = new Posting(posting.getCustomer());

        reversePosting.setAmount(posting.getAmount() * -1);
        reversePosting.setLiabilitiesAssets(posting.isLiabilitiesAssets());
        reversePosting.setDescription(posting.getDescription() + " - Storno");
        reversePosting.setPostingCategory(posting.getPostingCategory());

        HeaderInfo reversePostingHeaderInfo = new HeaderInfo(
            "Buchung stornieren",
            "<html>Stornieren Sie eine Buchung von '<b>" + posting.getCustomer().getForename() + " " + posting.getCustomer().getSurname() + "</b>'.</html>",
            CWUtils.loadIcon("cw/customermanagementmodul/images/posting_add.png"),
            CWUtils.loadIcon("cw/customermanagementmodul/images/posting_add.png")
        );

        final EditReversePostingPresentationModel model = new EditReversePostingPresentationModel(posting, reversePosting, reversePostingHeaderInfo);
        final EditReversePostingView editView = new EditReversePostingView(model);
        model.addButtonListener(new ButtonListener() {

            boolean postingAlreadyCreated = false;

            public void buttonPressed(ButtonEvent evt) {

                if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    PostingManager.getInstance().save(reversePosting);
                    GUIManager.getStatusbar().setTextAndFadeOut("Stornobuchung wurde erstellt.");
                    postingAlreadyCreated = true;
                    postingSelection.getList().add(reversePosting);
                    int idx = postingSelection.getList().indexOf(reversePosting);
                    postingSelection.fireIntervalAdded(idx, idx);
                    updateEvents();
                }
                if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    model.removeButtonListener(this);
                    GUIManager.getInstance().unlockMenu();
                    GUIManager.changeToLastView();
                }
            }
        });
        GUIManager.changeView(editView.buildPanel(), true);
        GUIManager.setLoadingScreenVisible(false);
    }

    private void balancePosting(Posting posting) {
        GUIManager.setLoadingScreenText("Buchung wird ausgeglichen...");
        GUIManager.setLoadingScreenVisible(true);
        GUIManager.getInstance().lockMenu();

        final Posting reversePosting = new Posting(posting.getCustomer());

        reversePosting.setAmount(posting.getAmount());
        reversePosting.setLiabilitiesAssets(!posting.isLiabilitiesAssets());
        reversePosting.setDescription(posting.getDescription() + " - Ausgleich");
        reversePosting.setPostingCategory(posting.getPostingCategory());

        HeaderInfo reversePostingHeaderInfo = new HeaderInfo(
                "Buchung ausgleichen",
                "<html>Gleichen Sie die Buchung von '<b>" + posting.getCustomer().getForename() + " " + posting.getCustomer().getSurname() + "</b>' aus.</html>",
                CWUtils.loadIcon("cw/customermanagementmodul/images/posting_go.png"),
                CWUtils.loadIcon("cw/customermanagementmodul/images/posting_go.png")
        );

        final EditReversePostingPresentationModel model = new EditReversePostingPresentationModel(posting, reversePosting, reversePostingHeaderInfo);
        final EditReversePostingView editView = new EditReversePostingView(model);
        model.addButtonListener(new ButtonListener() {

            boolean postingAlreadyCreated = false;

            public void buttonPressed(ButtonEvent evt) {

                if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    PostingManager.getInstance().save(reversePosting);
                    GUIManager.getStatusbar().setTextAndFadeOut("Buchung wurde ausgeglichen.");
                    postingAlreadyCreated = true;
                    postingSelection.getList().add(reversePosting);
                    int idx = postingSelection.getList().indexOf(reversePosting);
                    postingSelection.fireIntervalAdded(idx, idx);
                    updateEvents();
                }
                if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    model.removeButtonListener(this);
                    GUIManager.getInstance().unlockMenu();
                    GUIManager.changeToLastView();
                }
            }
        });
        GUIManager.changeView(editView.buildPanel(), true);
        GUIManager.setLoadingScreenVisible(false);
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
//                    Posting a = postingSelection.getSelection();
//
//                    int idx = postingSelection.getList().indexOf(a);
//                    postingSelection.getList().remove(a);
//                    postingSelection.fireIntervalRemoved(idx, idx);
//                    PostingManager.getInstance().delete(a);
//                    updateEvents();
//
//                    GUIManager.setLoadingScreenVisible(false);
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

    public Action getReversePostingAction() {
        return reversePostingAction;
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

    public ValueModel getAssetsTotalValue() {
        return assetsTotalValue;
    }

    public ValueModel getLiabilitiesTotalValue() {
        return liabilitiesTotalValue;
    }

    public ValueModel getSaldoTotalValue() {
        return saldoTotalValue;
    }

    public SelectionInList<Posting> getPostingSelection() {
        return postingSelection;
    }

    public Action getNewAction() {
        return newAction;
    }

    public Action getBalancePostingAction() {
        return balancePostingAction;
    }

    public SelectionInList<String> getFilterMonthSelection() {
        return filterMonthSelection;
    }

    public SelectionInList<String> getFilterYearSelection() {
        return filterYearSelection;
    }

    public SelectionInList<PostingCategory> getFilterPostingCategorySelection() {
        return filterPostingCategorySelection;
    }

    public HeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public void editPosting(final Posting posting) {
        GUIManager.setLoadingScreenText("Buchung wird geladen...");
        GUIManager.setLoadingScreenVisible(true);
        GUIManager.getInstance().lockMenu();

        final EditPostingPresentationModel model = new EditPostingPresentationModel(
                posting,
                true,
                new HeaderInfo(
                        "Buchung bearbeiten",
                        "Hier können Sie die Buchung bearbeiten.",
                        CWUtils.loadIcon("cw/customermanagementmodul/images/posting_edit.png"),
                        CWUtils.loadIcon("cw/customermanagementmodul/images/posting_edit.png")
                ));
        final EditPostingView editView = new EditPostingView(model);
        model.addButtonListener(new ButtonListener() {

            public void buttonPressed(ButtonEvent evt) {
                if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    PostingManager.getInstance().save(posting);
                    int idx = postingSelection.getList().indexOf(posting);
                    postingSelection.fireContentsChanged(idx, idx);
                    GUIManager.getStatusbar().setTextAndFadeOut("Buchung wurde aktualisiert.");
                    updateEvents();
                }
                if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    model.removeButtonListener(this);
                    GUIManager.getInstance().unlockMenu();
                    GUIManager.changeToLastView();
                }
                if (evt.getType() == ButtonEvent.OWN_BUTTON && evt.getOwnButtonText().equals("reversePostingButton")) {
                    model.removeButtonListener(this);
                    GUIManager.getInstance().unlockMenu();
                    GUIManager.changeToLastView();
                    reversePosting(posting);
                }
            }
        });
        GUIManager.changeView(editView.buildPanel(), true);
        GUIManager.setLoadingScreenVisible(false);
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
                editPosting(postingSelection.getSelection());
            }
        }
    }

    private void updateActionEnablement() {
        boolean hasSelection = postingSelection.hasSelection();
        editAction.setEnabled(hasSelection);
        balancePostingAction.setEnabled(hasSelection);
        reversePostingAction.setEnabled(hasSelection);
//        deleteAction.setEnabled(hasSelection);
    }

    private final class SelectionHandler implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateActionEnablement();
        }
    }
}
