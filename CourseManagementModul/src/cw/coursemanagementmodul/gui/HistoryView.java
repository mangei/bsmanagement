/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.gui.helper.JXTableSelectionConverter;
import cw.boardingschoolmanagement.interfaces.Disposable;
import cw.coursemanagementmodul.renderer.PostingHistoryTableCellRenderer;
import java.awt.Font;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author André Salmhofer
 */
public class HistoryView implements Disposable{
    private HistoryPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;

    //Tabelle zur Darstellung der angelegten Kurse
    private JXTable coursePartTable;
    private JComboBox courseComboBox;
    private JLabel courseLabel;

    private JViewPanel panel;

    private JLabel courseName;
    private JLabel beginDate;
    private JLabel endDate;
    private JLabel price;

    private JLabel vCourseName;
    private JLabel vBeginDate;
    private JLabel vEndDate;
    private JLabel vPrice;

    private JLabel totalValue;
    private JLabel coursePartLabel;

    private JLabel vSollLabel;
    private JLabel vHabenLabel;
    private JLabel vSaldoLabel;
    private JLabel anzLabel;

    private Format numberFormat;
    private Format dateFormat;
    //********************************************

    private JButton detailButton;
    
    public HistoryView(HistoryPresentationModel model) {
        this.model = model;
        numberFormat = DecimalFormat.getCurrencyInstance();
        dateFormat = DateFormat.getDateInstance();
    }

    private void initEventHandling() {
    }

    //******************************************************************
    //In dieser Methode werden alle oben definierten Objekte instanziert
    //******************************************************************
    public void initComponents(){
        coursePartTable = CWComponentFactory.createTable("Keine Kursteilnehmer zum selektierten Kurs!");
        coursePartTable.setModel(model.createCoursePartTableModel(model.getCoursePartSelection()));
        coursePartTable.setSelectionModel(
                new SingleListSelectionAdapter(
                    new JXTableSelectionConverter(
                        model.getCoursePartSelection().getSelectionIndexHolder(),
                        coursePartTable)));

        coursePartTable.getColumns(true).get(7).setCellRenderer(new PostingHistoryTableCellRenderer());

        courseComboBox = CWComponentFactory.createComboBox(model.getCourseSelection());
        
        detailButton = CWComponentFactory.createButton(model.getDetailAction());
        
        courseLabel = CWComponentFactory.createLabel("Kurs:");

        componentContainer = CWComponentFactory.createCWComponentContainer()
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
                .addComponent(vSaldoLabel);

        panel = CWComponentFactory.createViewPanel(model.getHeaderInfoComboBox());

        courseName = CWComponentFactory.createLabel("Kursname:");
        beginDate = CWComponentFactory.createLabel("Beginn:");
        endDate = CWComponentFactory.createLabel("Ende:");
        price = CWComponentFactory.createLabel("Preis:");

        vCourseName = CWComponentFactory.createLabel(model.getNameVM());
        vBeginDate = CWComponentFactory.createLabel(model.getBisVM(), dateFormat);
        vEndDate = CWComponentFactory.createLabel(model.getVonVM(), dateFormat);
        vPrice = CWComponentFactory.createLabel(model.getPriceVM(), numberFormat);
        
        vCourseName.setFont(new Font(null, Font.BOLD, 11));
        vBeginDate.setFont(new Font(null, Font.BOLD, 11));
        vEndDate.setFont(new Font(null, Font.BOLD, 11));
        vPrice.setFont(new Font(null, Font.BOLD, 11));
        
        totalValue = new JLabel("Gesamtbetrag:");
        coursePartLabel = new JLabel("Anzahl der Teilnehmer:");
        totalValue = new JLabel("Soll:");

        vSollLabel = CWComponentFactory.createLabel(model.getCourseSollValueHolder(), numberFormat);
        anzLabel = CWComponentFactory.createLabel(model.getSizeValueHolder());
        vHabenLabel = CWComponentFactory.createLabel(model.getCourseHabenValueHolder(), numberFormat);
        vSaldoLabel = CWComponentFactory.createLabel(model.getCourseSaldoValueHolder(), numberFormat);

        vSollLabel.setFont(new Font(null, Font.BOLD, 11));
        vHabenLabel.setFont(new Font(null, Font.BOLD, 11));
        vSaldoLabel.setFont(new Font(null, Font.BOLD, 11));
        anzLabel.setFont(new Font(null, Font.BOLD, 11));
    }
    //**************************************************************************


    //**************************************************************************
    //Diese Methode gibt die Maske des StartPanels in Form einse JPanels zurück
    //**************************************************************************
    public JPanel buildPanel(){
        initComponents();

        JPanel totalPanel = CWComponentFactory.createPanel();
        JPanel coursePartPanel = CWComponentFactory.createPanel();
        JPanel extendPanel = CWComponentFactory.createPanel();
        JPanel detailPanel = CWComponentFactory.createPanel();

        panel.getButtonPanel().add(detailButton);

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

        panel.getContentPanel().setLayout(layout);
        coursePartPanel.setLayout(panelLayout);

        coursePartPanel.add(new JScrollPane(coursePartTable), cc.xy(1, 1));

        extendPanel.setLayout(new FormLayout("pref, 4dlu, pref", "pref"));
        extendPanel.add(courseLabel, cc.xy(1, 1));
        extendPanel.add(courseComboBox, cc.xy(3, 1));

        PanelBuilder panelBuilder = new PanelBuilder(layout, panel.getContentPanel());

        panelBuilder.add(extendPanel, cc.xy(1, 1));
        panelBuilder.addSeparator("Kursteilnehmer", cc.xyw(1, 3, 3));
        panelBuilder.add(coursePartPanel, cc.xyw(1, 5, 3));

        panelBuilder.addSeparator("Kursdetails", cc.xyw(1, 7, 3));
        panelBuilder.add(detailPanel, cc.xy(1, 9));
        panelBuilder.addSeparator("Buchungsdetails", cc.xyw(1, 11, 3));
        panelBuilder.add(totalPanel, cc.xy(1, 13));

        panel.setOpaque(false);
        coursePartPanel.setOpaque(false);

        initEventHandling();
        panel.addDisposableListener(this);
        return panel;
    }

    public void dispose() {
        panel.removeDisposableListener(this);
        componentContainer.dispose();
        model.dispose();
    }
    //**************************************************************************
}
