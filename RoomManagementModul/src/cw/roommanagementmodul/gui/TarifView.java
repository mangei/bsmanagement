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
public class TarifView {

    private TarifPresentationModel model;
    private JButton bNew;
    private JButton bDelete;
    private JButton bEdit;
    private JButton bBack;
    private JXTable tTarif;
    
    
    public TarifView(TarifPresentationModel m) {
        this.model = m;
    }

    private void initComponents() {


        bNew = new JButton(model.getNewAction());
        bNew.setText("Neu");
        bDelete = new JButton(model.getDeleteAction());
        bDelete.setText("Löschen");
        bEdit = new JButton(model.getEditAction());
        bEdit.setText("Bearbeiten");
        bBack = new JButton(model.getBackAction());
        bBack.setText("Zurück");


        tTarif = new JXTable();
        tTarif.setColumnControlVisible(true);
        tTarif.setAutoCreateRowSorter(true);
        tTarif.setPreferredScrollableViewportSize(new Dimension(10, 10));

        tTarif.setModel(model.createZuordnungTableModel(model.getTarifSelection()));
        tTarif.setSelectionModel(
                new SingleListSelectionAdapter(
                model.getTarifSelection().getSelectionIndexHolder()));
    }

    private void initEventHandling() {
        tTarif.addMouseListener(model.getDoubleClickHandler());
    }

    public JPanel buildPanel() {
        initComponents();
        initEventHandling();

        JViewPanel panel = new JViewPanel();
        panel.setHeaderInfo(new HeaderInfo(model.getHeaderText()));

        panel.getButtonPanel().add(bNew);
        panel.getButtonPanel().add(bEdit);
        panel.getButtonPanel().add(bDelete);
        panel.getButtonPanel().add(bBack);


        FormLayout layout = new FormLayout("pref, 2dlu, 50dlu:grow, 2dlu, pref", "pref");
        panel.getTopPanel().setLayout(layout);
        CellConstraints cc = new CellConstraints();
//
//        ArrayListModel list = new ArrayListModel();
//        Object sel = new Object();
//        list.add(sel);
//
//        panel.getTopPanel().add(new JObjectChooser(list, null), cc.xy(1, 1));
        panel.getContentPanel().add(new JScrollPane(tTarif), BorderLayout.CENTER);

        return panel;
    }
}
