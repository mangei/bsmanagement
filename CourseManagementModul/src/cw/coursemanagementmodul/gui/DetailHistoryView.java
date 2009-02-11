/*
 * Die Course View representiert das Startfenster,
 * dass angezeigt wird, wenn man auf 'Kurs' in der
 * linken Auswahlliste klickt.
 */

package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

/**
 *
 * @author André Salmhofer
 */
public class DetailHistoryView{
    private DetailHistoryPresentationModel model;

    //Tabelle zur Darstellung der angelegten Kurse
    private JXTable accountingTable;
    private JTextField searchTextField;
    private JButton back;
    //********************************************

    private JLabel vSoll;
    private JLabel vHaben;
    private JLabel vSaldo;

    private JLabel soll;
    private JLabel haben;
    private JLabel saldo;

    public DetailHistoryView(DetailHistoryPresentationModel model) {
        this.model = model;
    }

    private void initEventHandling() {
    }

    //******************************************************************
    //In dieser Methode werden alle oben definierten Objekte instanziert
    //******************************************************************
    public void initComponents(){
        accountingTable = CWComponentFactory.createTable("");
        accountingTable.setColumnControlVisible(true);
        accountingTable.setAutoCreateRowSorter(true);
        accountingTable.setPreferredScrollableViewportSize(new Dimension(10,10));
        accountingTable.setHighlighters(HighlighterFactory.createSimpleStriping());

        accountingTable.setModel(model.createCoursePostingTableModel(model.getPostings()));
        accountingTable.setSelectionModel(
                new SingleListSelectionAdapter(
                model.getPostings().getSelectionIndexHolder()));

        back = CWComponentFactory.createButton(model.getBackAction());

        soll =  CWComponentFactory.createLabel("Sollbetrag:");
        haben = CWComponentFactory.createLabel("Habenbetrag:");
        saldo = CWComponentFactory.createLabel("Saldo:");

        vSoll = CWComponentFactory.createLabel(model.getTotalSoll() + "");
        vHaben = CWComponentFactory.createLabel(model.getTotalHaben() + "");
        vSaldo = CWComponentFactory.createLabel(model.getTotalSaldo() + "");

        vSoll.setFont(new Font(null, Font.BOLD, 11));
        vHaben.setFont(new Font(null, Font.BOLD, 11));
        vSaldo.setFont(new Font(null, Font.BOLD, 11));
    }
    //**************************************************************************

    //**************************************************************************
    //Diese Methode gibt die Maske des StartPanels in Form einse JPanels zurück
    //**************************************************************************
    public JPanel buildPanel(){
        initComponents();
        initEventHandling();
        JViewPanel panel = CWComponentFactory.createViewPanel(model.getHeaderInfo());
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
        
        panel.getContentPanel().setLayout(layout);

        panel.getButtonPanel().add(back);

        panel.getContentPanel().add(new JScrollPane(accountingTable), cc.xyw(1, 1, 5));
        panel.getContentPanel().add(new JLabel("Gesamtübersicht:"), cc.xy(1, 3));
        panel.getContentPanel().add(new JSeparator(), cc.xy(3, 3));
        panel.getContentPanel().add(accountingPanel, cc.xy(1, 5));

        return panel;
    }
    //********************************************
}
