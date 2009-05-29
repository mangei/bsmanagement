/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.gui.helper.CWTableSelectionConverter;
import cw.boardingschoolmanagement.interfaces.Disposable;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author Dominik
 */
public class GebuehrenView implements Disposable{

    private GebuehrenPresentationModel model;
    private JButton bNew;
    private JButton bEdit;
    private JButton bDelete;
    private JButton bKategorie;
    private JButton bTarif;
    private JXTable tGebuehr;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private JViewPanel mainPanel;

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

        tGebuehr.setSelectionModel(new SingleListSelectionAdapter(new CWTableSelectionConverter(
                model.getGebuehrenSelection().getSelectionIndexHolder(),
                tGebuehr)));

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(bNew)
                .addComponent(bEdit)
                .addComponent(bDelete)
                .addComponent(bKategorie)
                .addComponent(bTarif)
                .addComponent(tGebuehr);
    }

    private void initEventHandling() {
        tGebuehr.addMouseListener(model.getDoubleClickHandler());
    }

    public JPanel buildPanel() {
        initComponents();
        initEventHandling();

        mainPanel = new JViewPanel(model.getHeaderInfo());
        

        mainPanel.getButtonPanel().add(bTarif);
        mainPanel.getButtonPanel().add(bKategorie);
        mainPanel.getButtonPanel().add(bNew);
        mainPanel.getButtonPanel().add(bEdit);
        mainPanel.getButtonPanel().add(bDelete);
        

        FormLayout layout = new FormLayout("pref, 2dlu, 50dlu:grow, 2dlu, pref", "pref");
        mainPanel.getTopPanel().setLayout(layout);
        CellConstraints cc = new CellConstraints();

//        ArrayListModel list = new ArrayListModel();
//        Object sel = new Object();
//        list.add(sel);
//
//        panel.getTopPanel().add(new JObjectChooser(list, null), cc.xy(1, 1));
        mainPanel.getContentPanel().add(new JScrollPane(tGebuehr), BorderLayout.CENTER);
        mainPanel.addDisposableListener(this);
        return mainPanel;
    }

    public void dispose() {
        mainPanel.removeDisposableListener(this);
        componentContainer.dispose();
        model.dispose();
    }
}
