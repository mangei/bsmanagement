package cw.coursemanagementmodul.gui;

import java.awt.Font;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWList;
import cw.boardingschoolmanagement.gui.component.CWTable;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.helper.CWTableSelectionConverter;
import cw.boardingschoolmanagement.gui.renderer.DateTimeTableCellRenderer;

/**
 * Die Course View representiert das Startfenster,
 * dass angezeigt wird, wenn man auf 'Kurs' in der
 * linken Auswahlliste klickt.
 *
 * @author André Salmhofer (CreativeWorkers)
 */
public class CourseChooserView extends CWView
{
    private CourseChooserPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;
    
    //Definieren der Objekte in der oberen Leiste
    private CWButton addButton;
    private CWButton cancelButton;
    //********************************************
    
    private CWLabel courseName;
    private CWLabel beginDate;
    private CWLabel endDate;
    private CWLabel price;
    
    private CWLabel vCourseName;
    private CWLabel vBeginDate;
    private CWLabel vEndDate;
    private CWLabel vPrice;
    
    //Tabelle zur Darstellung der angelegten Kurse
    private CWTable courseTable;

//    private CWCheckBoxList activityList;
//    private CWCheckBoxList subjectList;
    private CWList activityList;
    private CWList subjectList;
    //********************************************

    private Format dateFormat;
    private Format currencyFormat;

    public CourseChooserView(CourseChooserPresentationModel model) {
        this.model = model;
        
        initComponents();
        buildView();
        initEventHandling();
    }
    
    private void initEventHandling() {
    }
    
    //******************************************************************
    //In dieser Methode werden alle oben definierten Objekte instanziert
    //******************************************************************
    private void initComponents() {
        dateFormat = DateFormat.getDateInstance();
        currencyFormat = DecimalFormat.getCurrencyInstance();

        addButton = CWComponentFactory.createButton(model.getAddButtonAction());
        cancelButton = CWComponentFactory.createButton(model.getCancelButtonAction());

        addButton.setToolTipText(CWComponentFactory.createToolTip(
                "Hinzufuegen",
                "Hier koennen Sie den selektierten Kurs mit allen Aktivitaeten und Gegenstaenden hinzufuegen!",
                "cw/coursemanagementmodul/images/course_add.png"));
        
        cancelButton.setToolTipText(CWComponentFactory.createToolTip(
                "Schließen",
                "Hier kehren Sie zur Ferienkursuebersicht zurueck!",
                "cw/coursemanagementmodul/images/cancel.png"));

        courseTable = CWComponentFactory.createTable("Keine Kurse angelegt!");
        courseTable.setModel(model.createCourseTableModel(model.getCourseSelection()));
        courseTable.setSelectionModel(
                new SingleListSelectionAdapter(
                    new CWTableSelectionConverter(
                        model.getCourseSelection().getSelectionIndexHolder(),
                        courseTable)));

        courseTable.getColumns(true).get(1).setCellRenderer(new DateTimeTableCellRenderer("dd.MM.yyyy"));
        courseTable.getColumns(true).get(2).setCellRenderer(new DateTimeTableCellRenderer("dd.MM.yyyy"));

        
        courseName = CWComponentFactory.createLabel("Kursname:");
        beginDate = CWComponentFactory.createLabel("Beginn:");
        endDate = CWComponentFactory.createLabel("Ende:");
        price = CWComponentFactory.createLabel("Preis:");
        
        vCourseName = CWComponentFactory.createLabel(model.getNameVM());
        vBeginDate = CWComponentFactory.createLabel(model.getBisVM(), dateFormat);
        vEndDate = CWComponentFactory.createLabel(model.getVonVM(), dateFormat);
        vPrice = CWComponentFactory.createLabel(model.getPriceVM(), currencyFormat);

        vCourseName.setFont(new Font(null, Font.BOLD, 11));
        vBeginDate.setFont(new Font(null, Font.BOLD, 11));
        vEndDate.setFont(new Font(null, Font.BOLD, 11));
        vPrice.setFont(new Font(null, Font.BOLD, 11));


//        activityList = CWComponentFactory.createCheckBoxList(model.getActivityModel());
//        activityList.setCheckBoxListSelectionModel(model.getActivitySelectionModel());
        activityList = CWComponentFactory.createList(model.getActivityModel());
        activityList.setSelectionModel(model.getActivitySelectionModel());
        activityList.setCellRenderer(model.createActivityListCellRenderer());

//        subjectList = CWComponentFactory.createCheckBoxList(model.getSubjectModel());
//        subjectList.setCheckBoxListSelectionModel(model.getSubjectSelection());
        subjectList = CWComponentFactory.createList(model.getSubjectModel());
        subjectList.setSelectionModel(model.getSubjectSelection());
        subjectList.setCellRenderer(model.createSubjectListCellRenderer());
        
        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(addButton)
                .addComponent(cancelButton)
                .addComponent(courseTable)
                .addComponent(activityList)
                .addComponent(subjectList)
                .addComponent(courseName)
                .addComponent(beginDate)
                .addComponent(endDate)
                .addComponent(price)
                .addComponent(vBeginDate)
                .addComponent(vCourseName)
                .addComponent(vEndDate)
                .addComponent(vPrice);
    }
    //**************************************************************************
    
    //**************************************************************************
    //Diese Methode gibt die Maske des StartPanels in Form einse JPanels zurueck
    //**************************************************************************
    private void buildView(){
        JPanel detailPanel = CWComponentFactory.createPanel();

       this.setHeaderInfo(model.getHeaderInfo());
        
        this.getButtonPanel().add(addButton);
        this.getButtonPanel().add(cancelButton);
        
        FormLayout mainLayout = new FormLayout("pref:grow, 10dlu, pref:grow, 10dlu, pref:grow",
                "pref, 4dlu, fill:pref:grow, 35dlu, pref, 4dlu, fill:pref:grow");
        FormLayout layout = new FormLayout("pref, 10dlu, pref", 
                "pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 10dlu, pref");
        
        detailPanel.setLayout(layout);
        
        CellConstraints cc = new CellConstraints();
        
        detailPanel.add(courseName, cc.xy(1, 1));
        detailPanel.add(beginDate, cc.xy(1, 3));
        detailPanel.add(endDate, cc.xy(1, 5));
        detailPanel.add(price, cc.xy(1, 7));
        
        detailPanel.add(vCourseName, cc.xy(3, 1));
        detailPanel.add(vBeginDate, cc.xy(3, 3));
        detailPanel.add(vEndDate, cc.xy(3, 5));
        detailPanel.add(vPrice, cc.xy(3, 7));

        PanelBuilder panelBuilder = new PanelBuilder(mainLayout, this.getContentPanel());
        panelBuilder.addSeparator("Kurse des Kursteilnehmers:", cc.xyw(1, 1, 5));
        panelBuilder.add(new JScrollPane(courseTable), cc.xyw(1, 3, 5));
        panelBuilder.addSeparator("Detailansicht des Kurses", cc.xy(1, 5));
        panelBuilder.add(detailPanel, cc.xy(1, 7));
        panelBuilder.addSeparator("Aktivitaeten des Kurses", cc.xy(3, 5));
        panelBuilder.add(activityList, cc.xy(3, 7));
        panelBuilder.addSeparator("Gegenstaende des Kurses", cc.xy(5, 5));
        panelBuilder.add(subjectList, cc.xy(5, 7));
    }

    @Override
    public void dispose() {
        componentContainer.dispose();
        model.dispose();
    }
    //********************************************
}
