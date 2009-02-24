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
import cw.roommanagementmodul.component.DateTimeTableCellRenderer;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author Dominik
 */
public class GebZuordnunglBewohnerView {

    private GebZuordnungBewohnerPresentationModel model;
    private JButton bNew;
    private JButton bDelete;
    private JButton bEdit;
    private JButton bBack;
    private JXTable tZuordnung;

    public GebZuordnunglBewohnerView(GebZuordnungBewohnerPresentationModel m) {
        this.model = m;
    }

    private void initComponents() {


        bNew = new JButton(model.getNewAction());
        bNew.setText("Neue Gebühr");
        bDelete = new JButton(model.getDeleteAction());
        bDelete.setText("Löschen");
        bEdit = new JButton(model.getEditAction());
        bEdit.setText("Bearbeiten");
        bBack = new JButton(model.getBackAction());
        bBack.setText("Zurück");


        String zuordnungenTableStateName = "cw.roommanagementmodul.GebZuordnunglBewohnerView.zuordnungTableState";
        tZuordnung = CWComponentFactory.createTable(model.createZuordnungTableModel(model.getGebuehrZuordnungSelection()), "keine Gebühr Zuordnungen vorhanden",zuordnungenTableStateName);


        tZuordnung.setSelectionModel(new SingleListSelectionAdapter(new JXTableSelectionConverter(
                model.getGebuehrZuordnungSelection().getSelectionIndexHolder(),
                tZuordnung)));

        tZuordnung.getColumnModel().getColumn(1).setCellRenderer(new DateTimeTableCellRenderer(true));
        tZuordnung.getColumnModel().getColumn(2).setCellRenderer(new DateTimeTableCellRenderer(true));
    }

    private void initEventHandling() {
        tZuordnung.addMouseListener(model.getDoubleClickHandler());
    }

    public JPanel buildPanel() {
        initComponents();
        initEventHandling();

        JViewPanel panel = new JViewPanel(model.getHeaderInfo());

        panel.getButtonPanel().add(bNew);
        panel.getButtonPanel().add(bEdit);
        panel.getButtonPanel().add(bDelete);
        panel.getButtonPanel().add(bBack);


        FormLayout layout = new FormLayout("pref, 2dlu, 50dlu:grow, 2dlu, pref", "pref");
        panel.getTopPanel().setLayout(layout);
        CellConstraints cc = new CellConstraints();

//        ArrayListModel list = new ArrayListModel();
//        Object sel = new Object();
//        list.add(sel);
//
//        panel.getTopPanel().add(new JObjectChooser(list, null), cc.xy(1, 1));
        panel.getContentPanel().add(new JScrollPane(tZuordnung), BorderLayout.CENTER);

        return panel;
    }
}
