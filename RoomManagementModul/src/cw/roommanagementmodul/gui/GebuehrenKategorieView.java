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
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 *
 * @author Dominik
 */
public class GebuehrenKategorieView {

    private GebuehrenKategoriePresentationModel model;
    private JButton bNew;
    private JButton bEdit;
    private JButton bDelete;
    private JButton bBack;
    private JList lGebuehrenKat;
    
    public GebuehrenKategorieView(GebuehrenKategoriePresentationModel m) {
        this.model = m;
    }

    private void initComponents() {

        
        bNew = new JButton(model.getNewAction());
        bNew.setText("Neu");
        bEdit = new JButton(model.getEditAction());
        bEdit.setText("Bearbeiten");
        bDelete = new JButton(model.getDeleteAction());
        bDelete.setText("Löschen");
        bBack= new JButton(model.getBackAction());
        bBack.setText("Zurück");
        
        lGebuehrenKat= new JList();
        lGebuehrenKat.setModel(model.getGebuehrenKatSelection());
        lGebuehrenKat.setSelectionModel(
                new SingleListSelectionAdapter(
                model.getGebuehrenKatSelection().getSelectionIndexHolder()));
    }
     private void initEventHandling() {
        lGebuehrenKat.addMouseListener(model.getDoubleClickHandler());
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

//        ArrayListModel list = new ArrayListModel();
//        Object sel = new Object();
//        list.add(sel);
//
//        panel.getTopPanel().add(new JObjectChooser(list, null), cc.xy(1, 1));

        panel.getContentPanel().add(lGebuehrenKat, BorderLayout.CENTER);

        return panel;
    }

}
