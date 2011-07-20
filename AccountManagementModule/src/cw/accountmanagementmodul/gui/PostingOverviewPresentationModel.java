package cw.accountmanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWUtils;
import com.jgoodies.binding.list.SelectionInList;
import cw.accountmanagementmodul.comparator.AbstractPostingDateComparator;
import cw.accountmanagementmodul.gui.model.PostingTreeTableModel;
import cw.accountmanagementmodul.pojo.AbstractPosting;
import cw.accountmanagementmodul.pojo.Posting;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import java.util.List;
import cw.customermanagementmodul.pojo.Customer;
import cw.accountmanagementmodul.pojo.PostingGroup;
import cw.accountmanagementmodul.pojo.manager.PostingGroupManager;
import java.util.ArrayList;
import java.util.Collections;
import org.jdesktop.swingx.treetable.TreeTableModel;

/**
 *
 * @author Manuel Geier
 */
public class PostingOverviewPresentationModel {

    private Customer customer;
    private CWHeaderInfo headerInfo;

    private SelectionInList<AbstractPosting> postingSelection;

    public PostingOverviewPresentationModel() {
    }

    public void initModels() {

        postingSelection = new SelectionInList<AbstractPosting>();
//        loadPostings();

        headerInfo = new CWHeaderInfo(
                "Buchungsübersicht",
                "Hier sehen sie eine Übersicht über alle Buchungen für Ihren Kunden.",
                CWUtils.loadIcon("cw/accountmanagementmodul/images/posting.png"),
                CWUtils.loadIcon("cw/accountmanagementmodul/images/posting.png")
        );

    }

    private void initEventHandling() {
    }

    void dispose() {
    }

    private void loadPostings() {


//        List<Posting> postings = PostingManager.getInstance().getAll();
        List<PostingGroup> postingGroups = PostingGroupManager.getInstance().getAll();

        // Alle doppelte Buchungen löschen, die bereits in den Buchungsgruppen vorhanden sind
//        for(int i=0, l=postingGroups.size(); i<l; i++) {
//            postings.removeAll(postingGroups.get(i).getPostings());
//        }

        // Alles zusammenfügen
//        List<PostingInterface> pIList = new ArrayList<PostingInterface>(postings);
        List<AbstractPosting> pIList = new ArrayList<AbstractPosting>(postingGroups);
        pIList.addAll(postingGroups);

        // Sortieren
        Collections.sort(pIList, new AbstractPostingDateComparator());

        postingSelection.setList(pIList);
    }

    public void save() {
    }

    public void reset() {
    }

    public SelectionInList<AbstractPosting> getPostingSelection() {
        return postingSelection;
    }

    public TreeTableModel getPostingTreeTableModel() {
        PostingGroup p = new PostingGroup();
        Posting p2 = new Posting();
        p2.setName("Teeksksk");
        p.getPostings().add(p2);
        p.setName("TestGruppe");
        return new PostingTreeTableModel(p);
    }

//    private TreeTableModel createPostingTreeTableModel() {
//
//    }

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

}
