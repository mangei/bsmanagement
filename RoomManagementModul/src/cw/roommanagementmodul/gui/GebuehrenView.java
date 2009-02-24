/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.gui.helper.JXTableSelectionConverter;
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


         String gebuehrenTableStateName = "cw.roommanagementmodul.GebuehrenView.gebuehrenTableState";
        tGebuehr = CWComponentFactory.createTable(model.createGebuehrenTableModel(model.getGebuehrenSelection()), "keine Gebühren vorhanden",gebuehrenTableStateName);

        tGebuehr.setSelectionModel(new SingleListSelectionAdapter(new JXTableSelectionConverter(
                model.getGebuehrenSelection().getSelectionIndexHolder(),
                tGebuehr)));
    }

    private void initEventHandling() {
        tGebuehr.addMouseListener(model.getDoubleClickHandler());
    }

    public JPanel buildPanel() {
        initComponents();
        initEventHandling();

        JViewPanel panel = new JViewPanel(model.getHeaderInfo());
        

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
