package cw.coursemanagementmodul.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.Format;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWTable;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.helper.CWTableSelectionConverter;
import cw.boardingschoolmanagement.gui.renderer.DateTimeTableCellRenderer;

/**
 *
 * @author André Salmhofer (CreativeWorkers)
 */
public class EditCoursePartView extends CWView
{
    private EditCoursePartPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;
    
    //*********************Komponenten definieren*******************************
    private CWTable courseTable;
    private CWTable activityTable;
    private CWTable subjectTable;
    
    private CWButton courseButton;
    private CWButton activityButton;
    private CWButton subjectButton;
    private CWButton deleteCourseButton;
    private CWButton deleteSubjectButton;
    private CWButton deleteActivityButton;

    private CWLabel soll;
    private CWLabel haben;
    private CWLabel saldo;
    
    private CWLabel vCourseName;
    private CWLabel vBeginDate;
    private CWLabel vEndDate;
    private CWLabel vPrice;

    private CWLabel vSoll;
    private CWLabel vHaben;
    private CWLabel vSaldo;

    private Format currencyFormat;
    //**************************************************************************
            
    public EditCoursePartView(EditCoursePartPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }
    
    //**************************************************************************
    //Initialisieren der oben definierten Komponenten
    //Bei den meisten Komponenten kann man den PropertyName als Paramenter
    //mitgeben. Bei Datum-Komponenten ist dies jedoch nicht möglich. Hierzu
    //wird die Methode connect() von der Klasse PropertyConnector benötigt.
    //**************************************************************************
    private void initComponents(){
        currencyFormat = DecimalFormat.getCurrencyInstance();

        courseTable = CWComponentFactory.createTable("Kein Kurs zugewiesen!");
        courseTable.setPreferredScrollableViewportSize(new Dimension(10, 70));
        courseTable.setModel(model.createCourseTableModel(model.getCourseSelection()));
        courseTable.setSelectionModel(
                new SingleListSelectionAdapter(
                    new CWTableSelectionConverter(
                        model.getCourseSelection().getSelectionIndexHolder(),
                        courseTable)));

        activityTable = CWComponentFactory.createTable("Keine Aktivität zugewiesen!");
        activityTable.setPreferredScrollableViewportSize(new Dimension(10, 50));
        activityTable.setModel(model.createActivityTableModel(model.getActivitySelection()));//TODO-mit ValueModel
        activityTable.setSelectionModel(
                new SingleListSelectionAdapter(
                    new CWTableSelectionConverter(
                        model.getActivitySelection().getSelectionIndexHolder(),
                        activityTable)));
        
        subjectTable = CWComponentFactory.createTable("Kein Gegenstand zugewiesen!");
        subjectTable.setPreferredScrollableViewportSize(new Dimension(10, 50));
        subjectTable.setModel(model.createSubjectTableModel(model.getSubjectSelection()));
        subjectTable.setSelectionModel(
                new SingleListSelectionAdapter(
                    new CWTableSelectionConverter(
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

        courseButton.setToolTipText(CWComponentFactory.createToolTip(
                "Kurs hinzufügen",
                "Hier können Sie einen Kurs hinzufügen!",
                "cw/coursemanagementmodul/images/course.png"));
        activityButton.setToolTipText(CWComponentFactory.createToolTip(
                "Aktivität hinzufügen",
                "Hier können Sie zum selektierten Kurs eine Aktivität hinzufügen!",
                "cw/coursemanagementmodul/images/activity_add.png"));
        subjectButton.setToolTipText(CWComponentFactory.createToolTip(
                "Gegenstand hinzufügen",
                "Hier können Sie zum selektierten Kurs einen Kursgegenstand hinzufügen!",
                "cw/coursemanagementmodul/images/subject_add.png"));

        deleteCourseButton.setToolTipText(CWComponentFactory.createToolTip(
                "Kurs löschen",
                "Hier können Sie den selektierten Kurs löschen!",
                "cw/coursemanagementmodul/images/course_delete.png"));
        deleteSubjectButton.setToolTipText(CWComponentFactory.createToolTip(
                "Gegenstand löschen",
                "Hier können Sie zum selektierten Kurs einen Kursgegenstand löschen!",
                "cw/coursemanagementmodul/images/subject_delete.png"));
        deleteActivityButton.setToolTipText(CWComponentFactory.createToolTip(
                "Aktivität löschen",
                "Hier können Sie zum selektierten Kurs eine Aktivität löschen!",
                "cw/coursemanagementmodul/images/activity_delete.png"));

        soll = CWComponentFactory.createLabel("Sollbetrag:");
        haben = CWComponentFactory.createLabel("Habenbetrag:");
        saldo = CWComponentFactory.createLabel("Saldo:");
        
        vCourseName = CWComponentFactory.createLabel(model.getNameVM());
        vBeginDate = CWComponentFactory.createLabel(model.getBisVM());
        vEndDate = CWComponentFactory.createLabel(model.getVonVM());
        vPrice = CWComponentFactory.createLabel(model.getPriceVM());

        vSoll = CWComponentFactory.createLabel(model.getSollVM(), currencyFormat);
        vHaben = CWComponentFactory.createLabel(model.getHabenVM(), currencyFormat);
        vSaldo = CWComponentFactory.createLabel(model.getSaldoVM(), currencyFormat);

        vCourseName.setFont(new Font(null, Font.BOLD, 11));
        vBeginDate.setFont(new Font(null, Font.BOLD, 11));
        vEndDate.setFont(new Font(null, Font.BOLD, 11));
        vPrice.setFont(new Font(null, Font.BOLD, 11));

        vSoll.setFont(new Font(null, Font.BOLD, 11));
        vHaben.setFont(new Font(null, Font.BOLD, 11));
        vSaldo.setFont(new Font(null, Font.BOLD, 11));

        componentContainer = CWComponentFactory.createComponentContainer()
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
    }
    //**************************************************************************

    private void initEventHandling() {

    }

    //**************************************************************************
    //Diese Methode gibt die Maske des EditPanels in Form einse JPanels zurück
    //**************************************************************************
    private void buildView(){

        this.setHeaderInfo(model.getHeaderInfo());
        
        JPanel subjectButtonPanel = CWComponentFactory.createPanel(new FormLayout("pref, 4dlu, pref", "pref"));
        JPanel activityButtonPanel = CWComponentFactory.createPanel(new FormLayout("pref, 4dlu, pref", "pref"));
        
        JPanel accountingPanel = CWComponentFactory.createPanel();

        CWButtonPanel buttonPanel = this.getButtonPanel();
        
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

        PanelBuilder panelBuilder = new PanelBuilder(layout, this.getContentPanel());

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
    }

    @Override
    public void dispose() {
        componentContainer.dispose();
        model.dispose();
    }
    //**************************************************************************
}
