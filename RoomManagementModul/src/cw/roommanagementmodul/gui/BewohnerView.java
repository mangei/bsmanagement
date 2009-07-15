package cw.roommanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWTable;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.helper.CWTableSelectionConverter;
import cw.roommanagementmodul.component.DateTimeTableCellRenderer;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;

/**
 *
 * @author Dominik
 */
public class BewohnerView extends CWView
{

    private BewohnerPresentationModel model;
    private CWButton bGeb;
    private CWButton bDelete;
    private CWButton bGebZuordnung;
    private CWButton bEditBewohner;
    private CWButton bDetail;
    private CWButton bKaution;
    private CWTable tBewohner;
    private CWComponentFactory.CWComponentContainer componentContainer;

    public BewohnerView(BewohnerPresentationModel m) {
        this.model = m;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {

        bGeb = CWComponentFactory.createButton(model.getGebAction());
        bGeb.setText("Neue Gebühr");
        bDelete = CWComponentFactory.createButton(model.getDeleteAction());
        bDelete.setText("Löschen");
        bGebZuordnung = CWComponentFactory.createButton(model.getGebuehrZuordnungAction());
        bGebZuordnung.setText("Übersicht");
        bDetail = CWComponentFactory.createButton(model.getDetailAction());
        bDetail.setText("Bearbeiten");
        bKaution = CWComponentFactory.createButton(model.getKautionAction());
        bKaution.setText("Kautionen");

        String bewohnerTableStateName = "cw.roommanagementmodul.BewohnerView.bewohnerTableState";
        tBewohner = CWComponentFactory.createTable(model.createBewohnerTableModel(model.getBewohnerSelection()), "keine Bewohner vorhanden", bewohnerTableStateName);

        tBewohner.setSelectionModel(new SingleListSelectionAdapter(new CWTableSelectionConverter(
                model.getBewohnerSelection().getSelectionIndexHolder(),
                tBewohner)));


        tBewohner.getColumnModel().getColumn(4).setCellRenderer(new DateTimeTableCellRenderer(true));
        tBewohner.getColumnModel().getColumn(5).setCellRenderer(new DateTimeTableCellRenderer(true));


        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(bGeb)
                .addComponent(bDelete)
                .addComponent(bGebZuordnung)
                .addComponent(bDetail)
                .addComponent(bKaution)
                .addComponent(tBewohner);

    }

    private void initEventHandling() {
        tBewohner.addMouseListener(model.getDoubleClickHandler());
    }

    private void buildView() {

        this.setHeaderInfo(model.getHeaderInfo());

        this.getButtonPanel().add(bDetail);
        this.getButtonPanel().add(bGebZuordnung);
        this.getButtonPanel().add(bGeb);
        //this.getButtonPanel().add(bHistory);
        this.getButtonPanel().add(bDelete);
        this.getButtonPanel().add(bKaution);
        //this.getButtonPanel().add(bInaktive);


        FormLayout layout = new FormLayout("pref, 2dlu, 50dlu:grow, 2dlu, pref", "pref");
        this.getTopPanel().setLayout(layout);
        CellConstraints cc = new CellConstraints();

//        ArrayListModel list = new ArrayListModel();
//        Object sel = new Object();
//        list.add(sel);
//
//        panel.getTopPanel().add(new JObjectChooser(list, null), cc.xy(1, 1));
        this.getContentPanel().add(new JScrollPane(tBewohner), BorderLayout.CENTER);
    }

    @Override
    public void dispose() {
        tBewohner.removeMouseListener(model.getDoubleClickHandler());
        componentContainer.dispose();
        model.dispose();
    }
}
