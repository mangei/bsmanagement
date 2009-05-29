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
public class GebZuordnunglBewohnerView implements Disposable{

    private GebZuordnungBewohnerPresentationModel model;
    private JButton bNew;
    private JButton bDelete;
    private JButton bEdit;
    private JButton bBack;
    private JXTable tZuordnung;
      private CWComponentFactory.CWComponentContainer componentContainer;
    private JViewPanel mainPanel;

    public GebZuordnunglBewohnerView(GebZuordnungBewohnerPresentationModel m) {
        this.model = m;
    }

    private void initComponents() {

        bNew = CWComponentFactory.createButton(model.getNewAction());
        bNew.setText("Neue Gebühr");
        bDelete = CWComponentFactory.createButton(model.getDeleteAction());
        bDelete.setText("Löschen");
        bEdit = CWComponentFactory.createButton(model.getEditAction());
        bEdit.setText("Bearbeiten");
        bBack = CWComponentFactory.createButton(model.getBackAction());
        bBack.setText("Zurück");


        String zuordnungenTableStateName = "cw.roommanagementmodul.GebZuordnunglBewohnerView.zuordnungTableState";
        tZuordnung = CWComponentFactory.createTable(model.createZuordnungTableModel(model.getGebuehrZuordnungSelection()), "keine Gebühr Zuordnungen vorhanden",zuordnungenTableStateName);


        tZuordnung.setSelectionModel(new SingleListSelectionAdapter(new CWTableSelectionConverter(
                model.getGebuehrZuordnungSelection().getSelectionIndexHolder(),
                tZuordnung)));

        tZuordnung.getColumnModel().getColumn(1).setCellRenderer(new DateTimeTableCellRenderer(true));
        tZuordnung.getColumnModel().getColumn(2).setCellRenderer(new DateTimeTableCellRenderer(true));

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(bNew)
                .addComponent(bDelete)
                .addComponent(bEdit)
                .addComponent(bBack)
                .addComponent(tZuordnung);
    }

    private void initEventHandling() {
        tZuordnung.addMouseListener(model.getDoubleClickHandler());
    }

    public JPanel buildPanel() {
        initComponents();
        initEventHandling();

        mainPanel = new JViewPanel(model.getHeaderInfo());

        mainPanel.getButtonPanel().add(bNew);
        mainPanel.getButtonPanel().add(bEdit);
        mainPanel.getButtonPanel().add(bDelete);
        mainPanel.getButtonPanel().add(bBack);


        FormLayout layout = new FormLayout("pref, 2dlu, 50dlu:grow, 2dlu, pref", "pref");
        mainPanel.getTopPanel().setLayout(layout);
        CellConstraints cc = new CellConstraints();

//        ArrayListModel list = new ArrayListModel();
//        Object sel = new Object();
//        list.add(sel);
//
//        panel.getTopPanel().add(new JObjectChooser(list, null), cc.xy(1, 1));
        mainPanel.getContentPanel().add(new JScrollPane(tZuordnung), BorderLayout.CENTER);
        mainPanel.addDisposableListener(this);
        return mainPanel;
    }

    public void dispose() {
        tZuordnung.removeMouseListener(model.getDoubleClickHandler());
        mainPanel.removeDisposableListener(this);
        componentContainer.dispose();
        model.dispose();
    }
}
