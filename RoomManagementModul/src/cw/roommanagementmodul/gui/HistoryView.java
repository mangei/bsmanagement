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
public class HistoryView {

    private HistoryPresentationModel model;
    private JButton bDelete;
    private JButton bBack;
    private JXTable tHistory;

     public HistoryView(HistoryPresentationModel m) {
        this.model = m;
    }

      private void initComponents() {

        bDelete = new JButton(model.getDeleteAction());
        bDelete.setText("Löschen");
        bBack = new JButton(model.getBackAction());
        bBack.setText("Zurück");


        tHistory = new JXTable();
        tHistory.setColumnControlVisible(true);
        tHistory.setAutoCreateRowSorter(true);
        tHistory.setPreferredScrollableViewportSize(new Dimension(10, 10));

        tHistory.setModel(model.createHistoryTableModel(model.getHistorySelection()));
        tHistory.setSelectionModel(
                new SingleListSelectionAdapter(
                model.getHistorySelection().getSelectionIndexHolder()));
    }


    public JPanel buildPanel() {
        initComponents();

        JViewPanel panel = new JViewPanel();
        panel.setHeaderInfo(new HeaderInfo("History: "+model.getHeaderText()));

        panel.getButtonPanel().add(bDelete);
        panel.getButtonPanel().add(bBack);

        FormLayout layout = new FormLayout("pref, 2dlu, 50dlu:grow, 2dlu, pref", "pref");
        panel.getTopPanel().setLayout(layout);
        CellConstraints cc = new CellConstraints();


        panel.getContentPanel().add(new JScrollPane(tHistory), BorderLayout.CENTER);

        return panel;
    }


}
