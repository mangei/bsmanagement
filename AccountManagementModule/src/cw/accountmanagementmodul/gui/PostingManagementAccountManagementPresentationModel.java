package cw.accountmanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.CWUtils;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.accountmanagementmodul.comparator.AbstractPostingDateComparator;
import cw.accountmanagementmodul.gui.model.PostingTreeTableModel;
import cw.accountmanagementmodul.pojo.Posting;
import cw.accountmanagementmodul.pojo.Account;
import cw.boardingschoolmanagement.app.CalendarUtil;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.SwingUtilities;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.accountmanagementmodul.pojo.AccountPosting;
import cw.accountmanagementmodul.pojo.PostingGroup;
import cw.accountmanagementmodul.pojo.manager.AbstractPostingManager;
import cw.accountmanagementmodul.pojo.manager.PostingGroupManager;
import cw.accountmanagementmodul.pojo.manager.PostingManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import javax.swing.Icon;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumnModel;
import org.jdesktop.swingx.treetable.TreeTableModel;

/**
 *
 * @author Manuel Geier
 */
public class PostingManagementAccountManagementPresentationModel {

    private Account account;
    private CWHeaderInfo headerInfo;

    private Action newAction;
    private Action editAction;
    private Action reversePostingAction;
    private Action balancePostingAction;
//    private Action deleteAction;
    private SelectionInList<Posting> postingSelection;
    private ValueModel filterActive;
    private ValueModel saldoValue;
    private ValueModel totalSaldoValue;
    
    private SelectionInList<String> filterYearSelection;
    private SelectionInList<String> filterMonthSelection;
    private SelectionHandler selectionHandler;
    private PropertyChangeListener filterYearSelectionListener;
    private PropertyChangeListener filterMonthSelectionListener;

    public PostingManagementAccountManagementPresentationModel() {
        this(null);
    }

    public PostingManagementAccountManagementPresentationModel(Account account) {
        this.account = account;

        initModels();
        initEventHandling();
    }

    public void initModels() {
        newAction = new NewAction("Neu", CWUtils.loadIcon("cw/accountmanagementmodul/images/posting_add.png"));
        editAction = new EditAction("Bearbeiten", CWUtils.loadIcon("cw/accountmanagementmodul/images/posting_edit.png"));
        reversePostingAction = new ReversePostingAction("Stornieren", CWUtils.loadIcon("cw/accountmanagementmodul/images/posting_delete.png"));
        balancePostingAction = new BalancePostingAction("Ausgleichen", CWUtils.loadIcon("cw/accountmanagementmodul/images/posting_go.png"));
//        deleteAction = new DeleteAction("Löschen", CWUtils.loadIcon("cw/accountmanagementmodul/images/posting_delete.png"));

        postingSelection = new SelectionInList<Posting>();
        loadPostings();

        headerInfo = new CWHeaderInfo(
                "Buchungen",
                "Hier sehen sie eine Uebersicht ueber alle Buchungen fuer Ihren Kunden.",
                CWUtils.loadIcon("cw/accountmanagementmodul/images/posting.png"),
                CWUtils.loadIcon("cw/accountmanagementmodul/images/posting.png")
        );

        saldoValue = new ValueHolder();
        totalSaldoValue = new ValueHolder();

        filterActive = new ValueHolder(false);

        filterMonthSelection = new SelectionInList<String>();
        loadFilterMonths();
        filterMonthSelection.setSelectionIndex(0);
        filterYearSelection = new SelectionInList<String>();
        loadFilterYears();
        filterYearSelection.setSelectionIndex(0);
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

        updateActionEnablement();
        updateEvents();
    }

    void dispose() {
        postingSelection.removeValueChangeListener(selectionHandler);

        filterYearSelection.removeValueChangeListener(filterYearSelectionListener);
        filterYearSelectionListener = null;
        filterMonthSelection.removeValueChangeListener(filterMonthSelectionListener);
        filterMonthSelectionListener = null;
    }

    private void loadPostings() {
        List<AccountPosting> accountPostings = PostingManager.getInstance().getAll(account);
        List<PostingGroup> postingGroups = PostingGroupManager.getInstance().getAll();
        System.out.println("Gruppen: " + postingGroups.size());
        // Alle doppelte Buchungen löschen, die bereits in den Buchungsgruppen vorhanden sind
        for(int i=0, l=postingGroups.size(); i<l; i++) {
            System.out.println("Gruppe: " + postingGroups.get(i).getName());
            accountPostings.removeAll(postingGroups.get(i).getPostings());
        }

        // Alles zusammenfuegen
        List<Posting> pIList = new ArrayList<Posting>(accountPostings);
        pIList.addAll(postingGroups);

        // Sortieren
        Collections.sort(pIList, new AbstractPostingDateComparator());

        postingSelection.setList(pIList);
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

            saldo = saldo + p.getAmount();
            
        }
        saldoValue.setValue(saldo);
    }

    private void calculateTotalValues() {
        List<Posting> list = AbstractPostingManager.getInstance().getAll(account);
        double liabilities=0, assets=0, saldo=0;
        for(int i=0,l=list.size(); i<l; i++) {
            Posting p = list.get(i);
            saldo = saldo + p.getAmount();
        }
        totalSaldoValue.setValue(saldo);
    }

    private void loadFilterYears() {
        String oldSelection = filterYearSelection.getSelection();

        int size = filterYearSelection.getList().size();
        if(size > 0) {
            filterYearSelection.getList().clear();
            filterYearSelection.fireIntervalRemoved(0, size-1);
        }

        List<String> years = PostingManager.getInstance().getYears(account);
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

    public void updateFilters() {
//        int size = postingSelection.getList().size();
//        if(size > 0) {
//            postingSelection.getList().clear();
//            postingSelection.fireIntervalRemoved(0, size-1);
//        }
//
//        boolean filterPassed;
//        Posting p;
//        int year;
//        GregorianCalendar calendar = new GregorianCalendar();
//        List<Posting> all = PostingManager.getInstance().getAll(customer);
//
//        if((filterYearSelection.getSelectionIndex() == 0
//                && filterMonthSelection.getSelectionIndex() == 0
//                && filterPostingCategorySelection.getSelectionIndex() == 0)) {
//            postingSelection.getList().addAll(all);
//
//            // Filter is not activated
//            filterActive.setValue(false);
//
//        } else {
//
//            // Filter is activated
//            filterActive.setValue(true);
//
//            for(int i=0, l=all.size(); i<l; i++) {
//
//                filterPassed = true;
//                p = all.get(i);
//
//                // Check PostingCategory
//
//                if(filterPostingCategorySelection.getSelectionIndex() != 0) {
//                    System.out.println("XXX: " + filterPostingCategorySelection.getSelectionIndex());
//                    if(filterPostingCategorySelection.getSelectionIndex() == 1) {
//                        System.out.println("ZZZ: " + p.getPostingCategory());
//                        if(p.getPostingCategory() != null) {
//                            filterPassed = false;
//                        }
//                    } else {
//                        if(p.getPostingCategory() == null || !p.getPostingCategory().equals(filterPostingCategorySelection.getSelection())) {
//                            filterPassed = false;
//                        }
//                    }
//                }
//
//                System.out.println("passed: " + filterPassed);
//
//                // Check Date
//                if(filterPassed
//                        && ( filterYearSelection.getSelectionIndex() != 0
//                        ||  filterMonthSelection.getSelectionIndex() != 0 ) ) {
//
//                    if(p.getPostingEntryDate() != null) {
//
//                        calendar.setTimeInMillis(p.getPostingEntryDate().getTime());
//
//                        if(filterYearSelection.getSelectionIndex() > 0) {
//
//                            String yearStr = filterYearSelection.getSelection();
//                            year = Integer.parseInt(yearStr);
//
//                            if(calendar.get(Calendar.YEAR) != year) {
//                                filterPassed = false;
//                            }
//                        }
//                        if(filterPassed && filterMonthSelection.getSelectionIndex() > 0) {
//                            int monthIdx = filterMonthSelection.getSelectionIndex() - 1;
//
//                            if(calendar.get(Calendar.MONTH) != monthIdx) {
//                                filterPassed = false;
//                            }
//                        }
//                    } else {
//                        filterPassed = false;
//                    }
//                }
//
//                if(filterPassed) {
//                    postingSelection.getList().add(p);
//                }
//            }
//        }
//
//        size = postingSelection.getList().size();
//        if(size > 0) {
//            postingSelection.fireIntervalAdded(0, size-1);
//        }
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

            final AccountPosting accountPosting = new AccountPosting(account);
            final EditPostingPresentationModel model = new EditPostingPresentationModel(
                    accountPosting,
                    new CWHeaderInfo(
                        "Buchung hinzufuegen",
                        "<html>Fuegen Sie eine neue Buchung fuer '<b>" + account.getCustomer().getForename() + " " + account.getCustomer().getSurname() + "</b>' hinzu.</html>",
                        CWUtils.loadIcon("cw/customermanagementmodul/images/posting_add.png"),
                        CWUtils.loadIcon("cw/customermanagementmodul/images/posting_add.png")
                    ));
            final EditPostingView editView = new EditPostingView(model);
            model.addButtonListener(new ButtonListener() {

                public void buttonPressed(ButtonEvent evt) {

                    if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        PostingManager.getInstance().save(accountPosting);
                        GUIManager.getStatusbar().setTextAndFadeOut("Buchung wurde erstellt.");
                        postingSelection.getList().add(accountPosting);
                        int idx = postingSelection.getList().indexOf(accountPosting);
                        postingSelection.fireIntervalAdded(idx, idx);
                        updateEvents();
                    }
                    if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        model.removeButtonListener(this);
                        GUIManager.changeToLastView();
                    }
                }
            });

            GUIManager.changeView(editView, true);
            GUIManager.setLoadingScreenVisible(false);
        }
    }

    private class EditAction
            extends AbstractAction {

        public EditAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            Posting pSelection = postingSelection.getSelection();
            if(pSelection instanceof AccountPosting) {
                editPosting((AccountPosting) pSelection);
            }
        }
    }

    private class ReversePostingAction
            extends AbstractAction {

        public ReversePostingAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
//            reversePosting(postingSelection.getSelection());
        }
    }

    private class BalancePostingAction
            extends AbstractAction {

        public BalancePostingAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
//            balancePosting(postingSelection.getSelection());
        }
    }

//    private void reversePosting(Posting posting) {
//        GUIManager.setLoadingScreenText("Buchung wird storniert...");
//        GUIManager.setLoadingScreenVisible(true);
//        GUIManager.getInstance().lockMenu();
//
//        final Posting reversePosting = new Posting(posting.getAccount());
//
//        reversePosting.setAmount(posting.getAmount() * -1);
//        reversePosting.setName(posting.getName() + " - Storno");
//        reversePosting.setPreviousPosting(posting);
//        reversePosting.setReversePosting(true);
//
//        CWHeaderInfo reversePostingHeaderInfo = new CWHeaderInfo(
//            "Buchung stornieren",
//            "<html>Stornieren Sie eine Buchung von '<b>" + posting.getAccount().getCustomer().getForename() + " " + posting.getAccount().getCustomer().getSurname() + "</b>'.</html>",
//            CWUtils.loadIcon("cw/customermanagementmodul/images/posting_add.png"),
//            CWUtils.loadIcon("cw/customermanagementmodul/images/posting_add.png")
//        );
//
//        final EditReversePostingPresentationModel model = new EditReversePostingPresentationModel(posting, reversePosting, reversePostingHeaderInfo);
//        final EditReversePostingView editView = new EditReversePostingView(model);
//        model.addButtonListener(new ButtonListener() {
//
//            boolean postingAlreadyCreated = false;
//
//            public void buttonPressed(ButtonEvent evt) {
//
//                if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
//                    PostingManager.getInstance().save(reversePosting);
//                    GUIManager.getStatusbar().setTextAndFadeOut("Stornobuchung wurde erstellt.");
//                    postingAlreadyCreated = true;
//                    postingSelection.getList().add(reversePosting);
//                    int idx = postingSelection.getList().indexOf(reversePosting);
//                    postingSelection.fireIntervalAdded(idx, idx);
//                    updateEvents();
//                }
//                if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
//                    model.removeButtonListener(this);
//                    GUIManager.getInstance().unlockMenu();
//                    GUIManager.changeToLastView();
//                }
//            }
//        });
//
//        GUIManager.changeView(editView, true);
//        GUIManager.setLoadingScreenVisible(false);
//    }

//    private void balancePosting(Posting posting) {
//        GUIManager.setLoadingScreenText("Buchung wird ausgeglichen...");
//        GUIManager.setLoadingScreenVisible(true);
//        GUIManager.getInstance().lockMenu();
//
//        final Posting reversePosting = new Posting(posting.getAccount());
//
//        reversePosting.setAmount(posting.getAmount() * -1);
//        reversePosting.setName(posting.getName() + " - Ausgleich");
//
//        // Wenn der vorherige ein teil war, dann muss man die Grundbuchung heraussuchen
//        reversePosting.setBalancePosting(true);
//        if(posting.isBalancePosting()) {
//            reversePosting.setPreviousPosting(posting.getPreviousPosting());
//        } else {
//            reversePosting.setPreviousPosting(posting);
//        }
//
//        CWHeaderInfo reversePostingHeaderInfo = new CWHeaderInfo(
//                "Buchung ausgleichen",
//                "<html>Gleichen Sie die Buchung von '<b>" + posting.getAccount().getCustomer().getForename() + " " + posting.getAccount().getCustomer().getSurname() + "</b>' aus.</html>",
//                CWUtils.loadIcon("cw/customermanagementmodul/images/posting_go.png"),
//                CWUtils.loadIcon("cw/customermanagementmodul/images/posting_go.png")
//        );
//
//        final EditReversePostingPresentationModel model = new EditReversePostingPresentationModel(posting, reversePosting, reversePostingHeaderInfo);
//        final EditReversePostingView editView = new EditReversePostingView(model);
//        model.addButtonListener(new ButtonListener() {
//
//            boolean postingAlreadyCreated = false;
//
//            public void buttonPressed(ButtonEvent evt) {
//
//                if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
//                    PostingManager.getInstance().save(reversePosting);
//                    GUIManager.getStatusbar().setTextAndFadeOut("Buchung wurde ausgeglichen.");
//                    postingAlreadyCreated = true;
//                    postingSelection.getList().add(reversePosting);
//                    int idx = postingSelection.getList().indexOf(reversePosting);
//                    postingSelection.fireIntervalAdded(idx, idx);
//                    updateEvents();
//                }
//                if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
//                    model.removeButtonListener(this);
//                    GUIManager.getInstance().unlockMenu();
//                    GUIManager.changeToLastView();
//                }
//            }
//        });
//
//        GUIManager.changeView(editView, true);
//        GUIManager.setLoadingScreenVisible(false);
//    }

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
//
//                    d.setVisible(true);

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

    public ValueModel getSaldoValue() {
        return saldoValue;
    }

    public ValueModel getTotalSaldoValue() {
        return totalSaldoValue;
    }

    public ValueModel getFilterActive() {
        return filterActive;
    }

    public TreeTableModel getPostingTreeTableModel() {
        return new PostingTreeTableModel(new PostingGroup());
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

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public void editPosting(final AccountPosting accountPosting) {
        GUIManager.setLoadingScreenText("Buchung wird geladen...");
        GUIManager.setLoadingScreenVisible(true);
        GUIManager.getInstance().lockMenu();

        final EditPostingPresentationModel model = new EditPostingPresentationModel(
                accountPosting,
                true,
                new CWHeaderInfo(
                        "Buchung bearbeiten",
                        "Hier können Sie die Buchung bearbeiten.",
                        CWUtils.loadIcon("cw/customermanagementmodul/images/posting_edit.png"),
                        CWUtils.loadIcon("cw/customermanagementmodul/images/posting_edit.png")
                ));
        final EditPostingView editView = new EditPostingView(model);
        model.addButtonListener(new ButtonListener() {

            public void buttonPressed(ButtonEvent evt) {
                if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    PostingManager.getInstance().save(accountPosting);
                    int idx = postingSelection.getList().indexOf(accountPosting);
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
//                    reversePosting(posting);
                }
            }
        });

        GUIManager.changeView(editView, true);
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
                Posting pSelection = postingSelection.getSelection();
                if(pSelection instanceof AccountPosting) {
                    editPosting((AccountPosting) pSelection);
                }
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
