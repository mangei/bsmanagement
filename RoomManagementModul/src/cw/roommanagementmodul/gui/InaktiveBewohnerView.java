/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
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
public class InaktiveBewohnerView {

    private InaktiveBewohnerPresentationModel model;
    private JButton bDelete;
    private JButton bGebZordnung;
    private JButton bHistory;
    private JButton bBack;
    private JButton bEintreten;
    private JButton bDetail;
    private JXTable tBewohner;

    public InaktiveBewohnerView(InaktiveBewohnerPresentationModel m) {
        this.model = m;
    }

    private void initComponents() {


        bDelete = new JButton(model.getDeleteAction());
        bDelete.setText("Löschen");
        bGebZordnung = new JButton(model.getGebuehrZuordnungAction());
        bGebZordnung.setText("Übersicht");
        bHistory = new JButton(model.getHistoryAction());
        bHistory.setText("History");
        bBack= new JButton(model.getBackAction());
        bBack.setText("Zurück");
        bEintreten= new JButton(model.getEintretenAction());
        bEintreten.setText("Eintreten");
        bDetail= new JButton(model.getDetailAction());
        bDetail.setText("Detail");

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
        panel.getButtonPanel().add(bGebZordnung);
        panel.getButtonPanel().add(bHistory);
        panel.getButtonPanel().add(bEintreten);
        panel.getButtonPanel().add(bDelete);
        panel.getButtonPanel().add(bBack);

        FormLayout layout = new FormLayout("pref, 2dlu, 50dlu:grow, 2dlu, pref", "pref");
        panel.getTopPanel().setLayout(layout);
       

        panel.getContentPanel().add(new JScrollPane(tBewohner), BorderLayout.CENTER);

        return panel;
    }
}
