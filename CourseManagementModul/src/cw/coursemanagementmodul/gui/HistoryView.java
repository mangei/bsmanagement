package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComboBox;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.gui.component.CWTable;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.helper.CWTableSelectionConverter;
import cw.coursemanagementmodul.renderer.PostingHistoryTableCellRenderer;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

/**
 *
 * @author André Salmhofer
 */
public class HistoryView extends CWView
{
    private HistoryPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;

    //Tabelle zur Darstellung der angelegten Kurse
    private CWTable coursePartTable;
    private CWComboBox courseComboBox;
    private CWLabel courseLabel;

    private CWLabel courseName;
    private CWLabel beginDate;
    private CWLabel endDate;
    private CWLabel price;

    private CWLabel vCourseName;
    private CWLabel vBeginDate;
    private CWLabel vEndDate;
    private CWLabel vPrice;

    private CWLabel totalValue;
    private CWLabel coursePartLabel;

    private CWLabel vSollLabel;
    private CWLabel vHabenLabel;
    private CWLabel vSaldoLabel;
    private CWLabel anzLabel;

    private Format numberFormat;
    private Format dateFormat;

    private CWPanel detailPanel;
    //********************************************

    private CWButton detailButton;
    private CWButton printButton;
    
    public HistoryView(HistoryPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    //******************************************************************
    //In dieser Methode werden alle oben definierten Objekte instanziert
    //******************************************************************
    public void initComponents(){

        numberFormat = DecimalFormat.getCurrencyInstance();
        dateFormat = DateFormat.getDateInstance();

        coursePartTable = CWComponentFactory.createTable("Keine Kursteilnehmer zum selektierten Kurs!");
        coursePartTable.setModel(model.createCoursePartTableModel(model.getCoursePartSelection()));
        coursePartTable.getColumns(true).get(7).setCellRenderer(new PostingHistoryTableCellRenderer());
        coursePartTable.setSelectionModel(
                new SingleListSelectionAdapter(
                    new CWTableSelectionConverter(
                        model.getCoursePartSelection().getSelectionIndexHolder(),
                        coursePartTable)));
        
        courseComboBox = CWComponentFactory.createComboBox(model.getCourseSelection());
        
        detailButton = CWComponentFactory.createButton(model.getDetailAction());
        printButton = CWComponentFactory.createButton(model.getPrintAction());

        detailButton.setToolTipText(CWComponentFactory.createToolTip(
                "Anzeigen",
                "Zeigt den selektierten Kursteilnehmer in der Detailansicht an!",
                "cw/coursemanagementmodul/images/lupe.png"));
        printButton.setToolTipText(CWComponentFactory.createToolTip(
                "Drucken",
                "Druckt eine detailierte Abrechnung des gesamten Kurses!",
                "cw/coursemanagementmodul/images/print.png"));
        
        courseLabel = CWComponentFactory.createLabel("Kurs:");

        courseName = CWComponentFactory.createLabel("Kursname:");
        beginDate = CWComponentFactory.createLabel("Beginn:");
        endDate = CWComponentFactory.createLabel("Ende:");
        price = CWComponentFactory.createLabel("Preis:");

        vCourseName = CWComponentFactory.createLabel(model.getNameVM());
        vBeginDate = CWComponentFactory.createLabel(model.getVonVM(), dateFormat);
        vEndDate = CWComponentFactory.createLabel(model.getBisVM(), dateFormat);
        vPrice = CWComponentFactory.createLabel(model.getPriceVM(), numberFormat);
        
        vCourseName.setFont(new Font(null, Font.BOLD, 11));
        vBeginDate.setFont(new Font(null, Font.BOLD, 11));
        vEndDate.setFont(new Font(null, Font.BOLD, 11));
        vPrice.setFont(new Font(null, Font.BOLD, 11));
        
        totalValue = CWComponentFactory.createLabel("Gesamtbetrag:");
        coursePartLabel = CWComponentFactory.createLabel("Anzahl der Teilnehmer:");
        totalValue = CWComponentFactory.createLabel("Soll:");

        vSollLabel = CWComponentFactory.createLabel(model.getCourseSollValueHolder(), numberFormat);
        anzLabel = CWComponentFactory.createLabel(model.getSizeValueHolder());
        vHabenLabel = CWComponentFactory.createLabel(model.getCourseHabenValueHolder(), numberFormat);
        vSaldoLabel = CWComponentFactory.createLabel(model.getCourseSaldoValueHolder(), numberFormat);

        vSollLabel.setFont(new Font(null, Font.BOLD, 11));
        vHabenLabel.setFont(new Font(null, Font.BOLD, 11));
        vSaldoLabel.setFont(new Font(null, Font.BOLD, 11));
        anzLabel.setFont(new Font(null, Font.BOLD, 11));

        detailPanel = CWComponentFactory.createPanel();
        detailPanel.setVisible(false);

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(courseComboBox)
                .addComponent(courseLabel)
                .addComponent(coursePartTable)
                .addComponent(detailButton)
                .addComponent(vBeginDate)
                .addComponent(vCourseName)
                .addComponent(vEndDate)
                .addComponent(vPrice)
                .addComponent(vSollLabel)
                .addComponent(beginDate)
                .addComponent(courseName)
                .addComponent(endDate)
                .addComponent(price)
                .addComponent(vHabenLabel)
                .addComponent(anzLabel)
                .addComponent(vSaldoLabel);
    }
    //**************************************************************************


    private void initEventHandling() {
        model.getCourseSelection().addValueChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if(model.getCourseSelection().isSelectionEmpty()){
                    detailPanel.setVisible(false);
                    coursePartTable.updateUI();
                }
                else{
                    detailPanel.setVisible(true);
                    coursePartTable.updateUI();
                }
            }
        });
    }

    //**************************************************************************
    //Diese Methode gibt die Maske des StartPanels in Form einse JPanels zurück
    //**************************************************************************
    private void buildView(){

        this.setHeaderInfo(model.getHeaderInfoComboBox());

        CWPanel totalPanel = CWComponentFactory.createPanel();
        CWPanel coursePartPanel = CWComponentFactory.createPanel();
        CWPanel extendPanel = CWComponentFactory.createPanel();

        this.getButtonPanel().add(detailButton);
        this.getButtonPanel().add(printButton);

        FormLayout layout = new FormLayout("pref, 2dlu, pref:grow, 2dlu, right:pref","pref, 10dlu, pref, 2dlu, fill:pref:grow, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref");
        FormLayout panelLayout = new FormLayout("fill:pref:grow", "fill:pref:grow");
        FormLayout totalLayout = new FormLayout("pref, 4dlu, pref", "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");
        FormLayout detailLayout = new FormLayout("pref, 4dlu, pref, 10dlu, pref, 4dlu, pref",
                "pref, 5dlu, pref, 5dlu, pref");

        detailPanel.setLayout(detailLayout);

        CellConstraints cc = new CellConstraints();

        detailPanel.add(courseName, cc.xy(1, 1));
        detailPanel.add(price, cc.xy(1, 3));
        detailPanel.add(beginDate, cc.xy(5, 1));
        detailPanel.add(endDate, cc.xy(5, 3));

        detailPanel.add(vCourseName, cc.xy(3, 1));
        detailPanel.add(vPrice, cc.xy(3, 3));
        detailPanel.add(vBeginDate, cc.xy(7, 1));
        detailPanel.add(vEndDate, cc.xy(7, 3));

        totalPanel.setLayout(totalLayout);

        totalPanel.add(totalValue, cc.xy(1, 3));
        totalPanel.add(coursePartLabel, cc.xy(1, 1));

        totalPanel.add(anzLabel, cc.xy(3, 1));
        totalPanel.add(vSollLabel, cc.xy(3, 3, CellConstraints.RIGHT, CellConstraints.TOP));

        totalPanel.add(new JLabel("Haben:"), cc.xy(1, 5));
        totalPanel.add(vHabenLabel, cc.xy(3, 5, CellConstraints.RIGHT, CellConstraints.TOP));

        totalPanel.add(new JLabel("Saldo:"), cc.xy(1, 7));
        totalPanel.add(vSaldoLabel, cc.xy(3, 7, CellConstraints.RIGHT, CellConstraints.TOP));

        this.getContentPanel().setLayout(layout);
        coursePartPanel.setLayout(panelLayout);

        coursePartPanel.add(new JScrollPane(coursePartTable), cc.xy(1, 1));

        extendPanel.setLayout(new FormLayout("pref, 4dlu, pref", "pref"));
        extendPanel.add(courseLabel, cc.xy(1, 1));
        extendPanel.add(courseComboBox, cc.xy(3, 1));

        PanelBuilder panelBuilder = new PanelBuilder(layout, this.getContentPanel());

        panelBuilder.add(extendPanel, cc.xy(1, 1));
        panelBuilder.addSeparator("Kursteilnehmer", cc.xyw(1, 3, 3));
        panelBuilder.add(coursePartPanel, cc.xyw(1, 5, 3));

        panelBuilder.addSeparator("Kursdetails", cc.xyw(1, 7, 3));
        panelBuilder.add(detailPanel, cc.xy(1, 9));
        panelBuilder.addSeparator("Buchungsdetails", cc.xyw(1, 11, 3));
        panelBuilder.add(totalPanel, cc.xy(1, 13));

        this.setOpaque(false);
        coursePartPanel.setOpaque(false);
    }

    @Override
    public void dispose() {
        super.dispose();
        componentContainer.dispose();
        model.dispose();
    }
    //**************************************************************************
}
