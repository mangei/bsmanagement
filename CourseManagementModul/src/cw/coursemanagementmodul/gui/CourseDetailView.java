package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWTable;
import cw.boardingschoolmanagement.gui.component.CWView;
import java.awt.BorderLayout;
import java.awt.Font;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import javax.swing.JScrollPane;

/**
 * Die Course View representiert das Startfenster,
 * dass angezeigt wird, wenn man auf 'Kurs' in der
 * linken Auswahlliste klickt.
 *
 * @author André Salmhofer (CreativeWorkers)
 */
public class CourseDetailView extends CWView
{
    private CourseDetailPresentationModel detailModel;
    private CWComponentFactory.CWComponentContainer componentContainer;
    
    //Definieren der Objekte in der oberen Leiste
    private CWButton printButton;
    private CWButton cancelButton;
    
    private CWLabel courseName;
    private CWLabel beginDate;
    private CWLabel endDate;
    private CWLabel price;
    
    private CWLabel vCourseName;
    private CWLabel vBeginDate;
    private CWLabel vEndDate;
    private CWLabel vPrice;
    
    private CWLabel descLabel;

    private Format dateFormat;
    //********************************************
    
    //Tabelle zur Darstellung der angelegten Kurse
    private CWTable coursePartTable;//Kursteilnehmer eines Kurses
    //********************************************

    private Format numberFormat;
    
    public CourseDetailView(CourseDetailPresentationModel detailModel) {
        this.detailModel = detailModel;

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

        cancelButton = CWComponentFactory.createButton(detailModel.getCancelAction());
        printButton =  CWComponentFactory.createButton(detailModel.getPrintAction());
        
        printButton.setToolTipText(CWComponentFactory.createToolTip(
                "Drucken",
                "Druckt die in der Tabelle angezeigten Kursteilnehmer!",
                "cw/coursemanagementmodul/images/print.png"));
        cancelButton.setToolTipText(CWComponentFactory.createToolTip(
                "Zurück",
                "Zurück zur Kursübersicht!",
                "cw/coursemanagementmodul/images/back.png"));
        
        coursePartTable = CWComponentFactory.createTable("Keine Kursteilnehmer zum selektierten Kurs vorhanden!");
        coursePartTable.setModel(detailModel.createCoursePartTableModel(detailModel.getCoursePartSelection()));
        coursePartTable.setSelectionModel(
                new SingleListSelectionAdapter(
                detailModel.getCoursePartSelection().getSelectionIndexHolder()));
        
        courseName = CWComponentFactory.createLabel("Kursname:");
        beginDate = CWComponentFactory.createLabel("Beginn:");
        endDate = CWComponentFactory.createLabel("Ende:");
        price = CWComponentFactory.createLabel("Preis:");
        descLabel = CWComponentFactory.createLabel("Alle Kursteilnehmer des Kurses:");
        
        vCourseName = CWComponentFactory.createLabel(detailModel.getSelectedCourse().getName());
        vBeginDate = CWComponentFactory.createLabel(new ValueHolder(detailModel.getSelectedCourse().getBeginDate()), dateFormat);
        vEndDate = CWComponentFactory.createLabel(new ValueHolder(detailModel.getSelectedCourse().getEndDate()), dateFormat);
        vPrice = CWComponentFactory.createLabel(new ValueHolder(detailModel.getSelectedCourse().getPrice()), numberFormat);

        vCourseName.setFont(new Font(null, Font.BOLD, 11));
        vBeginDate.setFont(new Font(null, Font.BOLD, 11));
        vEndDate.setFont(new Font(null, Font.BOLD, 11));
        vPrice.setFont(new Font(null, Font.BOLD, 11));

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(cancelButton)
                .addComponent(printButton)
                .addComponent(coursePartTable)
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


    private void initEventHandling() {
    }

    //**************************************************************************
    //Diese Methode gibt die Maske des StartPanels in Form einse JPanels zurück
    //**************************************************************************
    private void buildView(){
        this.setHeaderInfo(detailModel.getHeaderInfo());
        
        this.getButtonPanel().add(cancelButton);
        this.getButtonPanel().add(printButton);
        
        FormLayout layout = new FormLayout("pref, 10dlu, pref", 
                "pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 10dlu, pref");
        this.getTopPanel().setLayout(layout);
        CellConstraints cc = new CellConstraints();
        
        this.getTopPanel().add(courseName, cc.xy(1, 1));
        this.getTopPanel().add(beginDate, cc.xy(1, 3));
        this.getTopPanel().add(endDate, cc.xy(1, 5));
        this.getTopPanel().add(price, cc.xy(1, 7));
        
        this.getTopPanel().add(vCourseName, cc.xy(3, 1));
        this.getTopPanel().add(vBeginDate, cc.xy(3, 3));
        this.getTopPanel().add(vEndDate, cc.xy(3, 5));
        this.getTopPanel().add(vPrice, cc.xy(3, 7));
        
        this.getTopPanel().add(descLabel, cc.xy(1, 9));
        
        this.getContentPanel().add(new JScrollPane(coursePartTable), BorderLayout.CENTER);
    }

    public void dispose() {
        componentContainer.dispose();
        detailModel.dispose();
    }
    //********************************************
}
