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
public class GebuehrenView {

    private GebuehrenPresentationModel model;
    private JButton bNew;
    private JButton bEdit;
    private JButton bDelete;
    private JButton bKategorie;
    private JButton bTarif;
    private JXTable tGebuehr;

    public GebuehrenView(GebuehrenPresentationModel m) {
        this.model = m;
    }

    private void initComponents() {

        bNew = new JButton(model.getNewAction());
        bNew.setText("Neu");
        bEdit = new JButton(model.getEditAction());
        bEdit.setText("Bearbeiten");
        bDelete = new JButton(model.getDeleteAction());
        bDelete.setText("Löschen");
        bKategorie = new JButton(model.getKategorieAction());
        bKategorie.setText("Kategorien");
        bTarif = new JButton(model.getTarifAction());
        bTarif.setText("Tarif");


        tGebuehr = new JXTable();
        tGebuehr.setColumnControlVisible(true);
        tGebuehr.setAutoCreateRowSorter(true);
        tGebuehr.setPreferredScrollableViewportSize(new Dimension(10, 10));

        tGebuehr.setModel(model.createGebuehrenTableModel(model.getGebuehrenSelection()));
        tGebuehr.setSelectionModel(
                new SingleListSelectionAdapter(
                model.getGebuehrenSelection().getSelectionIndexHolder()));
    }

    private void initEventHandling() {
        tGebuehr.addMouseListener(model.getDoubleClickHandler());
    }

    public JPanel buildPanel() {
        initComponents();
        initEventHandling();

        JViewPanel panel = new JViewPanel();
        
        panel.setHeaderInfo(new HeaderInfo(model.getHeaderText()));

        panel.getButtonPanel().add(bTarif);
        panel.getButtonPanel().add(bKategorie);
        panel.getButtonPanel().add(bNew);
        panel.getButtonPanel().add(bEdit);
        panel.getButtonPanel().add(bDelete);
        

        FormLayout layout = new FormLayout("pref, 2dlu, 50dlu:grow, 2dlu, pref", "pref");
        panel.getTopPanel().setLayout(layout);
        CellConstraints cc = new CellConstraints();

//        ArrayListModel list = new ArrayListModel();
//        Object sel = new Object();
//        list.add(sel);
//
//        panel.getTopPanel().add(new JObjectChooser(list, null), cc.xy(1, 1));
        panel.getContentPanel().add(new JScrollPane(tGebuehr), BorderLayout.CENTER);

        return panel;
    }
}
