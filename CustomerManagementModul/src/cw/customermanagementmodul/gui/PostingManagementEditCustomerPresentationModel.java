package cw.customermanagementmodul.gui;

import cw.customermanagementmodul.gui.model.PostingTableModel;
import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.customermanagementmodul.pojo.manager.PostingManager;
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
public class PostingManagementEditCustomerPresentationModel {

    private Customer customer;
    private HeaderInfo headerInfo;

    private Action newAction;
    private Action editAction;
    private Action reversePostingAction;
    private Action balancePostingAction;
//    private Action deleteAction;
    private Action managePostingCategoriesAction;
    private SelectionInList<Posting> postingSelection;
    private ValueModel filterActive;
    private ValueModel saldoValue;
    private ValueModel liabilitiesValue;
    private ValueModel assetsValue;
    private ValueModel totalSaldoValue;
    private ValueModel totalLiabilitiesValue;
    private ValueModel totalAssetsValue;
    
    private SelectionInList<String> filterYearSelection;
    private SelectionInList<String> filterMonthSelection;
    private SelectionInList<PostingCategory> filterPostingCategorySelection;

    private SelectionHandler selectionHandler;
    private PropertyChangeListener filterYearSelectionListener;
    private PropertyChangeListener filterMonthSelectionListener;
    private PropertyChangeListener filterPostingCategorySelectionListener;

    public PostingManagementEditCustomerPresentationModel() {
        this(null);
    }

    public PostingManagementEditCustomerPresentationModel(Customer customer) {
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
        totalSaldoValue = new ValueHolder();
        totalLiabilitiesValue = new ValueHolder();
        totalAssetsValue = new ValueHolder();

        filterActive = new ValueHolder(false);

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
        liabilitiesValue.setValue(liabilities);
        assetsValue.setValue(assets);
        saldoValue.setValue(saldo);
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
        totalLiabilitiesValue.setValue(liabilities);
        totalAssetsValue.setValue(assets);
        totalSaldoValue.setValue(saldo);
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
        months.add(CalendarUtil.getMonthName(Calendar.JANUARY));
        months.add(CalendarUtil.getMonthName(Calendar.FEBRUARY));
        months.add(CalendarUtil.getMonthName(Calendar.MARCH));
        months.add(CalendarUtil.getMonthName(Calendar.APRIL));
        months.add(CalendarUtil.getMonthName(Calendar.MAY));
        months.add(CalendarUtil.getMonthName(Calendar.JUNE));
        months.add(CalendarUtil.getMonthName(Calendar.JULY));
        months.add(CalendarUtil.getMonthName(Calendar.AUGUST));
        months.add(CalendarUtil.getMonthName(Calendar.SEPTEMBER));
        months.add(CalendarUtil.getMonthName(Calendar.OCTOBER));
        months.add(CalendarUtil.getMonthName(Calendar.NOVEMBER));
        months.add(CalendarUtil.getMonthName(Calendar.DECEMBER));

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

            // Filter is not activated
            filterActive.setValue(false);

        } else {

            // Filter is activated
            filterActive.setValue(true);

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
        reversePosting.setPreviousPosting(posting);
        reversePosting.setReversePosting(true);

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

        // Wenn der vorherige ein teil war, dann muss man die Grundbuchung heraussuchen
        reversePosting.setBalancePosting(true);
        if(posting.isBalancePosting()) {
            reversePosting.setPreviousPosting(posting.getPreviousPosting());
        } else {
            reversePosting.setPreviousPosting(posting);
        }

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

    public ValueModel getTotalAssetsValue() {
        return totalAssetsValue;
    }

    public ValueModel getTotalLiabilitiesValue() {
        return totalLiabilitiesValue;
    }

    public ValueModel getTotalSaldoValue() {
        return totalSaldoValue;
    }

    public ValueModel getFilterActive() {
        return filterActive;
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
                if (evt.getType() == ButtonEvent.CUSTOM_BUTTON && evt.getCustomButtonText().equals("reversePostingButton")) {
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
