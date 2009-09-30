package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.coursemanagementmodul.pojo.CourseAddition;
import cw.coursemanagementmodul.pojo.CourseParticipant;
import cw.coursemanagementmodul.pojo.CoursePosting;
import cw.coursemanagementmodul.pojo.PostingRun;
import cw.coursemanagementmodul.pojo.manager.CourseParticipantManager;
import cw.coursemanagementmodul.pojo.manager.CoursePostingManager;
import cw.coursemanagementmodul.pojo.manager.PostingRunManager;
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.Posting;
import cw.customermanagementmodul.pojo.PostingCategory;
import cw.customermanagementmodul.pojo.manager.PostingCategoryManager;
import cw.customermanagementmodul.pojo.manager.PostingManager;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.ListModel;

/**
 *
 * @author André Salmhofer
 */
public class PostingRunsPresentationModel
{
    private SelectionInList<PostingRun> postingRunList;

    private Action backButtonAction;
    private Action stornoAction;
    private Action detailAction;

    private CWHeaderInfo headerInfo;

    private SelectionHandler selectionHandler;

    public PostingRunsPresentationModel() {
        postingRunList = new SelectionInList<PostingRun>(PostingRunManager.getInstance().getAll());
        initModels();
        initEventHandling();
    }

    public void initModels() {

        headerInfo = new CWHeaderInfo(
                "Gebührenlaufübersicht",
                "<html>Sie befinden in der Übersichtsmaske der bereits gebuchten Gebührenläufe!<br/>"
                + "Hier können Sie einen Buchungslauf wieder stornieren!<html>",
                CWUtils.loadIcon("cw/coursemanagementmodul/images/posting.png"),
                CWUtils.loadIcon("cw/coursemanagementmodul/images/posting.png"));

        //Anlegen der Aktionen, für die Buttons
        backButtonAction = new BackButtonAction("Zurück");
        stornoAction = new StornoAction("Stornieren");
        detailAction = new DetailAction("Detailansicht");
    }

    private void initEventHandling() {
        postingRunList.addValueChangeListener(selectionHandler = new SelectionHandler());
        updateActionEnablement();
    }

    public void dispose() {
        postingRunList.removeValueChangeListener(selectionHandler);
        postingRunList.release();
    }

    private void updateActionEnablement() {
        boolean hasSelection = postingRunList.hasSelection();
        stornoAction.setEnabled(hasSelection);
        detailAction.setEnabled(hasSelection);
    }

    private class SelectionHandler implements PropertyChangeListener{
        public void propertyChange(PropertyChangeEvent evt) {
                updateActionEnablement();
            }
    }

    private class BackButtonAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/back.png"));
        }

        private BackButtonAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.getInstance().unlockMenu();
            GUIManager.changeToLastView();
        }
    }

    private class StornoAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/posting_delete.png"));
        }

        private StornoAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            int ret = JOptionPane.showConfirmDialog(null, "Wollen Sie diesen Buchungslauf "
                    + postingRunList.getSelection().getName() + " wirklich stornieren");

            if(ret == JOptionPane.OK_OPTION){
                doStornoRun();
            }
        }
    }
    
    private class DetailAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/lupe.png"));
        }

        private DetailAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            List<CourseParticipant> courseParticipants = new ArrayList<CourseParticipant>();
            List<CourseAddition> courseAdditions = new ArrayList<CourseAddition>();
            
            for(int i = 0; i < postingRunList.getSelection().getCoursePostings().size(); i++){
                Customer customer = postingRunList.getSelection().getCoursePostings().get(i).getPosting().getCustomer();
                courseAdditions = new ArrayList<CourseAddition>();
                courseAdditions.add(postingRunList.getSelection().getCoursePostings().get(i).getCourseAddition());
                
                CourseParticipant cP = new CourseParticipant();
                cP.setCourseList(courseAdditions);
                cP.setCustomer(customer);
                
                courseParticipants.add(cP);
            }
            GUIManager.getInstance().lockMenu();

            // CWTODO: This could be a problem if we give over a SelectionInList and don't release() it.

            GUIManager.changeView(new TestRunView(new TestRunPresentationModel(new SelectionInList<CourseParticipant>(courseParticipants), courseAdditions.get(0).getCourse())), true);
        }
    }

    public Action getBackButtonAction() {
        return backButtonAction;
    }

    public Action getStornoAction() {
        return stornoAction;
    }

    public SelectionInList<PostingRun> getPostingRunList() {
        return postingRunList;
    }

    private class PostingRunTableModel extends AbstractTableAdapter<PostingRun> {

        private ListModel listModel;
        private DecimalFormat numberFormat;

        public PostingRunTableModel(ListModel listModel) {
            super(listModel);
            this.listModel = listModel;
            numberFormat = new DecimalFormat("#0.00");
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Gebührenlaufname";
                case 1:
                    return "Gesamtbetrag";
                case 2:
                    return "Buchungsdatum";
                case 3:
                    return "Teilnehmeranzahl";
                default:
                    return "";
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            PostingRun runs = (PostingRun) listModel.getElementAt(rowIndex);
            switch (columnIndex) {
                case 0:
                    return runs.getName();
                case 1:
                    return "€ " + numberFormat.format(getTotalAmount(runs));
                case 2:
                    return runs.getCoursePostings().get(0).getPosting().getPostingDate();
                case 3:
                    return runs.getCoursePostings().size();
                default:
                    return "";
            }
        }
    }

    PostingRunTableModel createPostingRunTableModel(SelectionInList<PostingRun> list){
        return new PostingRunTableModel(list);
    }

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public double getTotalAmount(PostingRun run){
        double amount = 0.0;

        for(int i = 0; i < run.getCoursePostings().size(); i++){
            amount += run.getCoursePostings().get(i).getPosting().getAmount();
        }
        return amount;
    }

    public void doStornoRun(){

        CoursePostingManager cM = CoursePostingManager.getInstance();
        PostingManager pM = PostingManager.getInstance();
        PostingCategory cat = PostingCategoryManager.getInstance().get("Kurs-Buchung");
        
        if(postingRunList.getSelection().getAlreadyStorniert()){
                JOptionPane.showMessageDialog(null, "Dieser Buchungslauf wurde bereits storniert!");
        }
        else{
            for(int i = 0; i < postingRunList.getSelection().getCoursePostings().size(); i++){
                Posting old = postingRunList.getSelection().getCoursePostings().get(i).getPosting();
                Posting posting = new Posting();

                posting.setAmount(0-old.getAmount());//Gegenteil
                posting.setAssets(old.isAssets());
                posting.setCustomer(old.getCustomer());
                posting.setDescription(old.getDescription() + " STORO");
                posting.setLiabilities(old.isLiabilities());
                posting.setLiabilitiesAssets(old.isLiabilitiesAssets());
                posting.setPostingCategory(old.getPostingCategory());
                posting.setPostingDate(old.getPostingDate());
                posting.setPostingEntryDate(old.getPostingEntryDate());
                posting.setPostingCategory(cat);

                CoursePosting coursePosting = new CoursePosting();
                coursePosting.setPosting(posting);

                CourseAddition courseAddition = postingRunList.getSelection().
                        getCoursePostings().get(i).getCourseAddition();
                courseAddition.setPosted(false);
                coursePosting.setCourseAddition(courseAddition);
                
                pM.save(posting);
                cM.save(coursePosting);
            }
            postingRunList.getSelection().setAlreadyStorniert(true);

            PostingRun run = postingRunList.getSelection();
            postingRunList.getList().remove(run);
            postingRunList.fireIntervalRemoved(0, postingRunList.getSize());

            for(PostingRun pr: postingRunList.getList()) {
                PostingRunManager.getInstance().save(pr);
            }

        }
    }

    public SelectionInList<CourseParticipant> getCourseParticipants(){
        List<CourseParticipant> list = CourseParticipantManager.getInstance().getAll(
                    postingRunList.getSelection().getCoursePostings().get(0).getCourseAddition().getCourse());
        SelectionInList<CourseParticipant> retList = new SelectionInList<CourseParticipant>(list);
        return retList;
    }

    public Action getDetailAction() {
        return detailAction;
    }
}
