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
public class ZimmerView {

    private ZimmerPresentationModel model;
    private JButton bNew;
    private JButton bEdit;
    private JButton bDelete;
    private JButton bBack;
    private JButton bPrint;
    private JXTable tZimmer;

    public ZimmerView(ZimmerPresentationModel m) {
        this.model = m;
    }

    private void initComponents() {


        bNew = new JButton(model.getNewAction());
        bNew.setText("Neu");
        bEdit = new JButton(model.getEditAction());
        bEdit.setText("Bearbeiten");
        bDelete = new JButton(model.getDeleteAction());
        bDelete.setText("Löschen");

        bBack = new JButton(model.getBackAction());
        bBack.setText("Zurück");
        bPrint= new JButton(model.getPrintAction());
        bPrint.setText("Drucken");


         String zimmerTableStateName = "cw.roommanagementmodul.ZimmerView.zimmerTableState";
        tZimmer = CWComponentFactory.createTable(model.createZimmerTableModel(model.getZimmerSelection()), "keine Zimmer vorhanden",zimmerTableStateName);


        tZimmer.setSelectionModel(new SingleListSelectionAdapter(new JXTableSelectionConverter(
                model.getZimmerSelection().getSelectionIndexHolder(),
                tZimmer)));

    }

    private void initEventHandling() {
        tZimmer.addMouseListener(model.getDoubleClickHandler());
    }

    public JPanel buildPanel() {
        initComponents();
        initEventHandling();

        JViewPanel panel = new JViewPanel(model.getHeaderInfo());
        panel.getButtonPanel().add(bNew);
        panel.getButtonPanel().add(bEdit);
        panel.getButtonPanel().add(bDelete);
        panel.getButtonPanel().add(bPrint);
        panel.getButtonPanel().add(bBack);

        FormLayout layout = new FormLayout("pref, 2dlu, 50dlu:grow, 2dlu, pref", "pref");
        panel.getTopPanel().setLayout(layout);
        CellConstraints cc = new CellConstraints();
//        panel.getTopPanel().add(lSuche, cc.xy(1,1));
//        panel.getTopPanel().add(new org.jdesktop.swingx.JXDatePicker(), cc.xy(1,1));

//        ArrayListModel list = new ArrayListModel();
//        Object sel = new Object();
//        list.add(sel);
//        list.add(new Object());
//        list.add(new Object());
//        list.add(new Object());
//        list.add(new Object());
//
//        panel.getTopPanel().add(new JObjectChooser(list, null), cc.xy(1, 1));
////        topActions.add(new JButton(new ImageIcon("images" + System.getProperty("file.separator") + "link_go.png")), cc.xy(1,1));
//        panel.getTopPanel().add(tfSuche, cc.xy(3,1));
//        panel.getTopPanel().add(bSuche, cc.xy(5,1));

        panel.getContentPanel().add(new JScrollPane(tZimmer), BorderLayout.CENTER);

        return panel;
    }
}
