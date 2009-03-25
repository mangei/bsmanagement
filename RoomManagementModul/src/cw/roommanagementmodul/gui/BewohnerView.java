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
public class BewohnerView implements Disposable {

    private BewohnerPresentationModel model;
    private JButton bGeb;
    private JButton bDelete;
    private JButton bGebZuordnung;
    private JButton bEditBewohner;
    private JButton bHistory;
    private JButton bInaktive;
    private JButton bDetail;
    private JButton bKaution;
    private JXTable tBewohner;
    private CWComponentFactory.CWComponentContainer componentContainer;
    private JViewPanel panel;

    public BewohnerView(BewohnerPresentationModel m) {
        this.model = m;
    }

    private void initComponents() {


        bGeb = CWComponentFactory.createButton(model.getGebAction());
        bGeb.setText("Neue Gebühr");
        bDelete = CWComponentFactory.createButton(model.getDeleteAction());
        bDelete.setText("Löschen");
        bGebZuordnung = CWComponentFactory.createButton(model.getGebuehrZuordnungAction());
        bGebZuordnung.setText("Übersicht");
        bHistory = CWComponentFactory.createButton(model.getHistoryAction());
        bHistory.setText("History");
        bInaktive = CWComponentFactory.createButton(model.getInaktiveAction());
        bInaktive.setText("Inaktive");
        bDetail = CWComponentFactory.createButton(model.getDetailAction());
        bDetail.setText("Bearbeiten");
        bKaution = CWComponentFactory.createButton(model.getKautionAction());
        bKaution.setText("Kautionen");

        String bewohnerTableStateName = "cw.roommanagementmodul.BewohnerView.bewohnerTableState";
        tBewohner = CWComponentFactory.createTable(model.createBewohnerTableModel(model.getBewohnerSelection()), "keine Bewohner vorhanden", bewohnerTableStateName);

        tBewohner.setSelectionModel(new SingleListSelectionAdapter(new JXTableSelectionConverter(
                model.getBewohnerSelection().getSelectionIndexHolder(),
                tBewohner)));


        tBewohner.getColumnModel().getColumn(4).setCellRenderer(new DateTimeTableCellRenderer(true));
        tBewohner.getColumnModel().getColumn(5).setCellRenderer(new DateTimeTableCellRenderer(true));


        componentContainer = CWComponentFactory.createCWComponentContainer().addComponent(bGeb).addComponent(bDelete).addComponent(bGebZuordnung).addComponent(bHistory).addComponent(bInaktive).addComponent(bDetail).addComponent(bInaktive).addComponent(bKaution).addComponent(tBewohner);

    }

    private void initEventHandling() {

        tBewohner.addMouseListener(model.getDoubleClickHandler());
    }

    public JPanel buildPanel() {
        initComponents();
        initEventHandling();

        panel = new JViewPanel(model.getHeaderInfo());


        panel.getButtonPanel().add(bDetail);
        panel.getButtonPanel().add(bGebZuordnung);
        panel.getButtonPanel().add(bGeb);
        //panel.getButtonPanel().add(bHistory);
        panel.getButtonPanel().add(bDelete);
        panel.getButtonPanel().add(bKaution);
        //panel.getButtonPanel().add(bInaktive);


        FormLayout layout = new FormLayout("pref, 2dlu, 50dlu:grow, 2dlu, pref", "pref");
        panel.getTopPanel().setLayout(layout);
        CellConstraints cc = new CellConstraints();

//        ArrayListModel list = new ArrayListModel();
//        Object sel = new Object();
//        list.add(sel);
//
//        panel.getTopPanel().add(new JObjectChooser(list, null), cc.xy(1, 1));
        panel.getContentPanel().add(new JScrollPane(tBewohner), BorderLayout.CENTER);

        panel.addDisposableListener(this);
        return panel;
    }

    public void dispose() {
        tBewohner.removeMouseListener(model.getDoubleClickHandler());
        panel.removeDisposableListener(this);
        componentContainer.dispose();
        model.dispose();
    }
}
