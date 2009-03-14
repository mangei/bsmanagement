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
import cw.boardingschoolmanagement.gui.component.JButtonPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.gui.helper.JXTableSelectionConverter;
import cw.boardingschoolmanagement.gui.renderer.DateTimeTableCellRenderer;
import cw.boardingschoolmanagement.interfaces.Disposable;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author André Salmhofer
 */
public class EditCoursePartView implements Disposable{
    private EditCoursePartPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;
    
    //*********************Komponenten definieren*******************************
    private JXTable courseTable;
    private JXTable activityTable;
    private JXTable subjectTable;
    
    private JButton courseButton;
    private JButton activityButton;
    private JButton subjectButton;
    private JButton deleteCourseButton;
    private JButton deleteSubjectButton;
    private JButton deleteActivityButton;

    private JLabel soll;
    private JLabel haben;
    private JLabel saldo;
    
    private JLabel vCourseName;
    private JLabel vBeginDate;
    private JLabel vEndDate;
    private JLabel vPrice;

    private JLabel vSoll;
    private JLabel vHaben;
    private JLabel vSaldo;

    private JViewPanel view;
    
    //**************************************************************************
            
    public EditCoursePartView(EditCoursePartPresentationModel model) {
        this.model = model;
    }
    
    //**************************************************************************
    //Initialisieren der oben definierten Komponenten
    //Bei den meisten Komponenten kann man den PropertyName als Paramenter
    //mitgeben. Bei Datum-Komponenten ist dies jedoch nicht möglich. Hierzu
    //wird die Methode connect() von der Klasse PropertyConnector benötigt.
    //**************************************************************************
    public void initComponents(){
        courseTable = CWComponentFactory.createTable("Kein Kurs zugewiesen!");
        courseTable.setPreferredScrollableViewportSize(new Dimension(10, 70));
        courseTable.setModel(model.createCourseTableModel(model.getCourseSelection()));
        courseTable.setSelectionModel(
                new SingleListSelectionAdapter(
                    new JXTableSelectionConverter(
                        model.getCourseSelection().getSelectionIndexHolder(),
                        courseTable)));

        activityTable = CWComponentFactory.createTable("Keine Aktivität zugewiesen!");
        activityTable.setPreferredScrollableViewportSize(new Dimension(10, 30));
        activityTable.setModel(model.createActivityTableModel(model.getActivitySelection()));//TODO-mit ValueModel
        activityTable.setSelectionModel(
                new SingleListSelectionAdapter(
                    new JXTableSelectionConverter(
                        model.getActivitySelection().getSelectionIndexHolder(),
                        activityTable)));
        
        subjectTable = CWComponentFactory.createTable("Kein Gegenstand zugewiesen!");
        subjectTable.setPreferredScrollableViewportSize(new Dimension(10, 30));
        subjectTable.setModel(model.createSubjectTableModel(model.getSubjectSelection()));
        subjectTable.setSelectionModel(
                new SingleListSelectionAdapter(
                    new JXTableSelectionConverter(
                        model.getSubjectSelection().getSelectionIndexHolder(),
                        subjectTable)));

        courseTable.getColumns(true).get(1).setCellRenderer(new DateTimeTableCellRenderer("dd.MM.yyyy"));
        courseTable.getColumns(true).get(2).setCellRenderer(new DateTimeTableCellRenderer("dd.MM.yyyy"));


        courseButton = CWComponentFactory.createButton(model.getCourseChooserButtonAction());
        activityButton = CWComponentFactory.createButton(model.getActivityButtonAction());
        subjectButton = CWComponentFactory.createButton(model.getSubjectButtonAction());
        deleteCourseButton = CWComponentFactory.createButton(model.getRemoveCourseButtonAction());
        deleteSubjectButton = CWComponentFactory.createButton(model.getRemoveSubjectButtonAction());
        deleteActivityButton = CWComponentFactory.createButton(model.getRemoveActivityButtonAction());

        soll = CWComponentFactory.createLabel("Sollbetrag:");
        haben = CWComponentFactory.createLabel("Habenbetrag:");
        saldo = CWComponentFactory.createLabel("Saldo:");
        
        vCourseName = CWComponentFactory.createLabel(model.getNameVM());
        vBeginDate = CWComponentFactory.createLabel(model.getBisVM());
        vEndDate = CWComponentFactory.createLabel(model.getVonVM());
        vPrice = CWComponentFactory.createLabel(model.getPriceVM());

        vSoll = CWComponentFactory.createLabel(model.getSollVM());
        vHaben = CWComponentFactory.createLabel(model.getHabenVM());
        vSaldo = CWComponentFactory.createLabel(model.getSaldoVM());

        vCourseName.setFont(new Font(null, Font.BOLD, 11));
        vBeginDate.setFont(new Font(null, Font.BOLD, 11));
        vEndDate.setFont(new Font(null, Font.BOLD, 11));
        vPrice.setFont(new Font(null, Font.BOLD, 11));

        vSoll.setFont(new Font(null, Font.BOLD, 11));
        vHaben.setFont(new Font(null, Font.BOLD, 11));
        vSaldo.setFont(new Font(null, Font.BOLD, 11));

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(activityButton)
                .addComponent(activityTable)
                .addComponent(courseButton)
                .addComponent(courseTable)
                .addComponent(deleteActivityButton)
                .addComponent(deleteCourseButton)
                .addComponent(deleteSubjectButton)
                .addComponent(haben)
                .addComponent(saldo)
                .addComponent(soll)
                .addComponent(subjectButton)
                .addComponent(subjectTable)
                .addComponent(vBeginDate)
                .addComponent(vCourseName)
                .addComponent(vEndDate)
                .addComponent(vHaben)
                .addComponent(vPrice)
                .addComponent(vSaldo)
                .addComponent(vSoll);

        view = CWComponentFactory.createViewPanel(model.getHeaderInfo());
    }
    //**************************************************************************
    
    //**************************************************************************
    //Diese Methode gibt die Maske des EditPanels in Form einse JPanels zurück
    //**************************************************************************
    public JPanel buildPanel(){
        initComponents();
        
        JPanel subjectButtonPanel = CWComponentFactory.createPanel(new FormLayout("pref, 4dlu, pref", "pref"));
        JPanel activityButtonPanel = CWComponentFactory.createPanel(new FormLayout("pref, 4dlu, pref", "pref"));
        
        JPanel accountingPanel = CWComponentFactory.createPanel();

        JButtonPanel buttonPanel = view.getButtonPanel();
        
        buttonPanel.add(courseButton);
        buttonPanel.add(deleteCourseButton);
        
        FormLayout layout = new FormLayout("pref:grow, 15dlu, fill:pref:grow",
                "pref, 4dlu, fill:pref:grow, 20dlu, pref, 4dlu," +
                "pref, 4dlu, pref, 20dlu, pref, 4dlu, fill:pref:grow");

        FormLayout accountingLayout = new FormLayout("pref, 4dlu, pref", "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");
        
        CellConstraints cc = new CellConstraints();

        //ButtonPanels
        subjectButtonPanel.add(subjectButton, cc.xy(1, 1));
        subjectButtonPanel.add(deleteSubjectButton, cc.xy(3, 1));
        activityButtonPanel.add(activityButton, cc.xy(1, 1));
        activityButtonPanel.add(deleteActivityButton, cc.xy(3, 1));
        //****************
        
        accountingPanel.setOpaque(false);

        accountingPanel.setLayout(accountingLayout);

        accountingPanel.add(soll, cc.xy(1, 1));
        accountingPanel.add(haben, cc.xy(1, 3));
        accountingPanel.add(saldo, cc.xy(1, 5));

        accountingPanel.add(vSoll, cc.xy(3, 1));
        accountingPanel.add(vHaben, cc.xy(3, 3));
        accountingPanel.add(vSaldo, cc.xy(3, 5));

        PanelBuilder panelBuilder = new PanelBuilder(layout, view.getContentPanel());

        panelBuilder.addSeparator("Ferienkurse", cc.xyw(1, 1, 3));
        panelBuilder.add(new JScrollPane(courseTable), cc.xyw(1, 3, 3));
        panelBuilder.addSeparator("Aktivitäten", cc.xy(1, 5));
        panelBuilder.add(new JScrollPane(activityTable), cc.xy(1, 9));
        panelBuilder.addSeparator("Gegenstände", cc.xy(3, 5));
        panelBuilder.add(subjectButtonPanel, cc.xy(3, 7));
        panelBuilder.add(activityButtonPanel, cc.xy(1, 7));
        panelBuilder.add(new JScrollPane(subjectTable), cc.xy(3, 9));
        panelBuilder.addSeparator("Konto des Kursteilnehmers", cc.xyw(1, 11, 3));
        panelBuilder.add(accountingPanel, cc.xy(1, 13));
        panelBuilder.setOpaque(false);
        view.addDisposableListener(this);
        return view;
    }

    public void dispose() {
        view.removeDisposableListener(this);
        componentContainer.dispose();
        model.dispose();
    }
    //**************************************************************************
}
