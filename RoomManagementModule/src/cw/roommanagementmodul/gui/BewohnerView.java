package cw.roommanagementmodul.gui;

import javax.swing.JScrollPane;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWTable;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.helper.CWTableSelectionConverter;
import cw.boardingschoolmanagement.gui.renderer.DateTimeDataFieldRenderer;

/**
 *
 * @author Dominik
 */
public class BewohnerView
	extends CWView<BewohnerPresentationModel>
{

    private CWButton bGeb;
    private CWButton bDelete;
    private CWButton bGebZuordnung;
    private CWButton bEditBewohner;
    private CWButton bDetail;
    private CWButton bKaution;
    private CWTable tBewohner;

    public BewohnerView(BewohnerPresentationModel model) {
        super(model);
    }

    public void initComponents() {
    	super.initComponents();

        bGeb = CWComponentFactory.createButton(getModel().getGebAction());
        bGeb.setText("Neue Gebuehr");
        bDelete = CWComponentFactory.createButton(getModel().getDeleteAction());
        bDelete.setText("Loeschen");
        bGebZuordnung = CWComponentFactory.createButton(getModel().getGebuehrZuordnungAction());
        bGebZuordnung.setText("Uebersicht");
        bDetail = CWComponentFactory.createButton(getModel().getDetailAction());
        bDetail.setText("Bearbeiten");
        bKaution = CWComponentFactory.createButton(getModel().getKautionAction());
        bKaution.setText("Kautionen");

        String bewohnerTableStateName = "cw.roommanagementmodul.BewohnerView.bewohnerTableState";
        tBewohner = CWComponentFactory.createTable(getModel().createBewohnerTableModel(getModel().getBewohnerSelection()), "keine Bewohner vorhanden", bewohnerTableStateName);

        tBewohner.setSelectionModel(new SingleListSelectionAdapter(new CWTableSelectionConverter(
        		getModel().getBewohnerSelection().getSelectionIndexHolder(),
                tBewohner)));


        tBewohner.getColumnModel().getColumn(4).setCellRenderer(new DateTimeDataFieldRenderer(true));
        tBewohner.getColumnModel().getColumn(5).setCellRenderer(new DateTimeDataFieldRenderer(true));


        getComponentContainer()
                .addComponent(bGeb)
                .addComponent(bDelete)
                .addComponent(bGebZuordnung)
                .addComponent(bDetail)
                .addComponent(bKaution)
                .addComponent(tBewohner);

        initEventHandling();
    }

    private void initEventHandling() {
        tBewohner.addMouseListener(getModel().getDoubleClickHandler());
    }

    public void buildView() {
    	super.buildView();

        this.setHeaderInfo(getModel().getHeaderInfo());

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
        addToContentPanel(new JScrollPane(tBewohner));
    }

    @Override
    public void dispose() {
        tBewohner.removeMouseListener(getModel().getDoubleClickHandler());
        
        super.dispose();
    }
}
