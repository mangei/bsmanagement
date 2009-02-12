/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author Dominik
 */
public class BewohnerView {

    private BewohnerPresentationModel model;
    private JButton bGeb;
    private JButton bDelete;
    private JButton bGebZuordnung;
    private JButton bEditBewohner;
    private JButton bHistory;
    private JButton bInaktive;
    private JButton bDetail;
    private JButton bKaution;
    private JXTable tBewohner;

    public BewohnerView(BewohnerPresentationModel m) {
        this.model = m;
    }

    private void initComponents() {


        bGeb = new JButton(model.getGebAction());
        bGeb.setText("Neue Gebühr");
        bDelete = new JButton(model.getDeleteAction());
        bDelete.setText("Austreten");
        bGebZuordnung = new JButton(model.getGebuehrZuordnungAction());
        bGebZuordnung.setText("Übersicht");
        bHistory= new JButton(model.getHistoryAction());
        bHistory.setText("History");
        bInaktive = new JButton(model.getInaktiveAction());
        bInaktive.setText("Inaktive");
        bDetail= new JButton(model.getDetailAction());
        bDetail.setText("Bearbeiten");
        bKaution = new JButton(model.getKautionAction());
        bKaution.setText("Kautionen");
        
        tBewohner = new JXTable();
        tBewohner.setColumnControlVisible(true);
        tBewohner.setAutoCreateRowSorter(true);
        tBewohner.setPreferredScrollableViewportSize(new Dimension(10, 10));
        tBewohner.setModel(model.createBewohnerTableModel(model.getBewohnerSelection()));
        tBewohner.setSelectionModel(
                new SingleListSelectionAdapter(
                model.getBewohnerSelection().getSelectionIndexHolder()));
        

    }

    private void initEventHandling() {
        tBewohner.addMouseListener(model.getDoubleClickHandler());
    }

    public JPanel buildPanel() {
        initComponents();
        initEventHandling();

        JViewPanel panel = new JViewPanel();
        
        panel.setHeaderInfo(new HeaderInfo(model.getHeaderText()));


        panel.getButtonPanel().add(bDetail);
        panel.getButtonPanel().add(bGebZuordnung);
        panel.getButtonPanel().add(bGeb);
        panel.getButtonPanel().add(bHistory);
        panel.getButtonPanel().add(bDelete);
        panel.getButtonPanel().add(bKaution);
        panel.getButtonPanel().add(bInaktive);
        

        FormLayout layout = new FormLayout("pref, 2dlu, 50dlu:grow, 2dlu, pref", "pref");
        panel.getTopPanel().setLayout(layout);
        CellConstraints cc = new CellConstraints();

//        ArrayListModel list = new ArrayListModel();
//        Object sel = new Object();
//        list.add(sel);
//
//        panel.getTopPanel().add(new JObjectChooser(list, null), cc.xy(1, 1));
        panel.getContentPanel().add(new JScrollPane(tBewohner), BorderLayout.CENTER);

        return panel;
    }
}
