/*
 * Die Course View representiert das Startfenster,
 * dass angezeigt wird, wenn man auf 'Kurs' in der
 * linken Auswahlliste klickt.
 */

package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jidesoft.swing.CheckBoxList;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.gui.helper.JXTableSelectionConverter;
import cw.boardingschoolmanagement.gui.renderer.DateTimeTableCellRenderer;
import cw.boardingschoolmanagement.interfaces.Disposable;
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
public class CourseChooserView implements Disposable{
    private CourseChooserPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;
    
    //Definieren der Objekte in der oberen Leiste
    private JButton addButton;
    private JButton cancelButton;
    //********************************************
    
    private JLabel courseName;
    private JLabel beginDate;
    private JLabel endDate;
    private JLabel price;
    
    private JLabel vCourseName;
    private JLabel vBeginDate;
    private JLabel vEndDate;
    private JLabel vPrice;
    
    //Tabelle zur Darstellung der angelegten Kurse
    private JXTable courseTable;

    private CheckBoxList activityList;
    private CheckBoxList subjectList;
    //********************************************

    private JViewPanel mainPanel;
    
    public CourseChooserView(CourseChooserPresentationModel model) {
        this.model = model;
        initEventHandling();
    }
    
    private void initEventHandling() {
    }
    
    //******************************************************************
    //In dieser Methode werden alle oben definierten Objekte instanziert
    //******************************************************************
    public void initComponents(){
        addButton = CWComponentFactory.createButton(model.getAddButtonAction());
        cancelButton = CWComponentFactory.createButton(model.getCancelButtonAction());

        courseTable = CWComponentFactory.createTable("Keine Kurse angelegt!");
        courseTable.setModel(model.createCourseTableModel(model.getCourseSelection()));
        courseTable.setSelectionModel(
                new SingleListSelectionAdapter(
                    new JXTableSelectionConverter(
                        model.getCourseSelection().getSelectionIndexHolder(),
                        courseTable)));

        courseTable.getColumns(true).get(1).setCellRenderer(new DateTimeTableCellRenderer("dd.MM.yyyy"));
        courseTable.getColumns(true).get(2).setCellRenderer(new DateTimeTableCellRenderer("dd.MM.yyyy"));

        
        courseName = CWComponentFactory.createLabel("Kursname:");
        beginDate = CWComponentFactory.createLabel("Beginn:");
        endDate = CWComponentFactory.createLabel("Ende:");
        price = CWComponentFactory.createLabel("Preis:");
        
        vCourseName = CWComponentFactory.createLabel(model.getNameVM());
        vBeginDate = CWComponentFactory.createLabel(model.getBisVM());
        vEndDate = CWComponentFactory.createLabel(model.getVonVM());
        vPrice = CWComponentFactory.createLabel(model.getPriceVM());

        vCourseName.setFont(new Font(null, Font.BOLD, 11));
        vBeginDate.setFont(new Font(null, Font.BOLD, 11));
        vEndDate.setFont(new Font(null, Font.BOLD, 11));
        vPrice.setFont(new Font(null, Font.BOLD, 11));
        
        activityList = new CheckBoxList(model.getActivityModel());
        activityList.setCheckBoxListSelectionModel(model.getActivitySelectionModel());
        activityList.setCellRenderer(model.createActivityListCellRenderer());


        subjectList = new CheckBoxList(model.getSubjectModel());
        subjectList.setCheckBoxListSelectionModel(model.getSubjectSelection());
        subjectList.setCellRenderer(model.createSubjectListCellRenderer());
        
        courseTable.setOpaque(false);

        mainPanel = CWComponentFactory.createViewPanel(model.getHeaderInfo());

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(addButton)
                .addComponent(cancelButton)
                .addComponent(courseTable)
                .addComponent(activityList)
                .addComponent(courseName)
                .addComponent(beginDate)
                .addComponent(endDate)
                .addComponent(price)
                .addComponent(vBeginDate)
                .addComponent(vCourseName)
                .addComponent(vEndDate)
                .addComponent(vPrice)
                .addComponent(subjectList);
    }
    //**************************************************************************
    
    //**************************************************************************
    //Diese Methode gibt die Maske des StartPanels in Form einse JPanels zurück
    //**************************************************************************
    public JPanel buildPanel(){
        initComponents();
        initEventHandling();
        JPanel detailPanel = CWComponentFactory.createPanel();
        
        mainPanel.getButtonPanel().add(addButton);
        mainPanel.getButtonPanel().add(cancelButton);
        
        FormLayout mainLayout = new FormLayout("pref:grow, 10dlu, pref:grow, 10dlu, pref:grow",
                "pref, 4dlu, fill:pref:grow, 35dlu, pref, 4dlu, fill:pref:grow");
        FormLayout layout = new FormLayout("pref, 10dlu, pref", 
                "pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 10dlu, pref");
        
        mainPanel.getContentPanel().setLayout(mainLayout);
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

        PanelBuilder panelBuilder = new PanelBuilder(mainLayout, mainPanel.getContentPanel());
        panelBuilder.addSeparator("Kurse des Kursteilnehmers:", cc.xyw(1, 1, 5));
        panelBuilder.add(new JScrollPane(courseTable), cc.xyw(1, 3, 5));
        panelBuilder.addSeparator("Detailansicht des Kurses", cc.xy(1, 5));
        panelBuilder.add(detailPanel, cc.xy(1, 7));
        panelBuilder.addSeparator("Aktivitäten des Kurses", cc.xy(3, 5));
        panelBuilder.add(activityList, cc.xy(3, 7));
        panelBuilder.addSeparator("Gegenstände des Kurses", cc.xy(5, 5));
        panelBuilder.add(subjectList, cc.xy(5, 7));
        mainPanel.addDisposableListener(this);
        return mainPanel;
    }

    public void dispose() {
        mainPanel.removeDisposableListener(this);
        componentContainer.dispose();
        model.dispose();
    }
    //********************************************
}
