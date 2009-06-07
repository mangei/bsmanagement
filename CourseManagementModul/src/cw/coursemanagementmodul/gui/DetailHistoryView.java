package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWTable;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.helper.CWTableSelectionConverter;
import cw.boardingschoolmanagement.gui.renderer.DateTimeTableCellRenderer;
import java.awt.Font;
import java.text.Format;
import java.text.NumberFormat;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Die Course View representiert das Startfenster,
 * dass angezeigt wird, wenn man auf 'Kurs' in der
 * linken Auswahlliste klickt.
 * 
 * @author André Salmhofer (CreativeWorkers)
 */
public class DetailHistoryView extends CWView
{
    private DetailHistoryPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;

    //Tabelle zur Darstellung der angelegten Kurse
    private CWTable accountingTable;
    private CWButton back;
    //********************************************

    private CWLabel vSoll;
    private CWLabel vHaben;
    private CWLabel vSaldo;

    private CWLabel soll;
    private CWLabel haben;
    private CWLabel saldo;

    private Format currencyFormat;

    public DetailHistoryView(DetailHistoryPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    //******************************************************************
    //In dieser Methode werden alle oben definierten Objekte instanziert
    //******************************************************************
    private void initComponents(){
        currencyFormat = NumberFormat.getCurrencyInstance();

        accountingTable = CWComponentFactory.createTable("Es wurden noch keine Buchungen zum Kursteilnehmer getätigt!");
        accountingTable.setModel(model.createCoursePostingTableModel(model.getPostings()));
        accountingTable.setSelectionModel(
                new SingleListSelectionAdapter(
                    new CWTableSelectionConverter(
                        model.getPostings().getSelectionIndexHolder(),
                        accountingTable)));
        accountingTable.getColumns(true).get(3).setCellRenderer(new DateTimeTableCellRenderer());

        back = CWComponentFactory.createButton(model.getBackAction());

        back.setToolTipText(CWComponentFactory.createToolTip(
                "Zurück",
                "Hier kehren Sie zur Kurshistorie zurück!",
                "cw/coursemanagementmodul/images/back.png"));

        soll =  CWComponentFactory.createLabel("Sollbetrag:");
        haben = CWComponentFactory.createLabel("Habenbetrag:");
        saldo = CWComponentFactory.createLabel("Saldo:");

        vSoll = CWComponentFactory.createLabel(new ValueHolder(model.getTotalSoll()), currencyFormat);
        vHaben = CWComponentFactory.createLabel(new ValueHolder(model.getTotalHaben()), currencyFormat);
        vSaldo = CWComponentFactory.createLabel(new ValueHolder(model.getTotalSaldo()), currencyFormat);

        vSoll.setFont(new Font(null, Font.BOLD, 11));
        vHaben.setFont(new Font(null, Font.BOLD, 11));
        vSaldo.setFont(new Font(null, Font.BOLD, 11));

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(accountingTable)
                .addComponent(back)
                .addComponent(haben)
                .addComponent(saldo)
                .addComponent(soll)
                .addComponent(vHaben)
                .addComponent(vSaldo)
                .addComponent(vSoll);

    }
    //**************************************************************************

    private void initEventHandling() {

    }

    //**************************************************************************
    //Diese Methode gibt die Maske des StartPanels in Form einse JPanels zurück
    //**************************************************************************
    private void buildView(){

        this.setHeaderInfo(model.getHeaderInfo());

        JPanel accountingPanel = CWComponentFactory.createPanel();

        FormLayout layout = new FormLayout("pref, 2dlu, 50dlu:grow, 2dlu, pref, 2dlu, pref","fill:pref:grow, 4dlu, pref, 4dlu, pref");
        FormLayout accountingLayout = new FormLayout("pref, 4dlu, pref", "pref, 4dlu," +
                "pref, 4dlu, pref, 4dlu, pref");
        CellConstraints cc = new CellConstraints();

        accountingPanel.setLayout(accountingLayout);
        
        accountingPanel.add(soll, cc.xy(1, 3));
        accountingPanel.add(haben, cc.xy(1, 5));
        accountingPanel.add(saldo, cc.xy(1, 7));

        accountingPanel.add(vSoll, cc.xy(3, 3));
        accountingPanel.add(vHaben, cc.xy(3, 5));
        accountingPanel.add(vSaldo, cc.xy(3, 7));

        accountingPanel.setOpaque(false);

        this.getContentPanel().setLayout(layout);

        PanelBuilder builder = new PanelBuilder(layout, this.getContentPanel());

        this.getButtonPanel().add(back);
        builder.add(new JScrollPane(accountingTable), cc.xyw(1, 1, 5));
        builder.addSeparator("Gesamtübersicht", cc.xyw(1, 3, 3));
        builder.add(accountingPanel, cc.xy(1, 5));

    }

    @Override
    public void dispose() {
        componentContainer.dispose();
        model.dispose();
    }
    //********************************************
}
