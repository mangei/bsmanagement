/*
 * Die Course View representiert das Startfenster,
 * dass angezeigt wird, wenn man auf 'Kurs' in der
 * linken Auswahlliste klickt.
 */

package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.interfaces.Disposable;
import java.awt.BorderLayout;
import java.awt.Font;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author André Salmhofer
 */
public class CourseDetailView implements Disposable{
    private CourseDetailPresentationModel detailModel;
    private CWComponentFactory.CWComponentContainer componentContainer;
    
    //Definieren der Objekte in der oberen Leiste
    private JButton printButton;
    private JButton cancelButton;
    
    private JLabel courseName;
    private JLabel beginDate;
    private JLabel endDate;
    private JLabel price;
    
    private JLabel vCourseName;
    private JLabel vBeginDate;
    private JLabel vEndDate;
    private JLabel vPrice;
    
    private JLabel descLabel;

    private Format dateFormat;
    //********************************************
    
    //Tabelle zur Darstellung der angelegten Kurse
    private JXTable coursePartTable;//Kursteilnehmer eines Kurses
    //********************************************

    private JViewPanel panel;

    private Format numberFormat;
    
    public CourseDetailView(CourseDetailPresentationModel detailModel) {
        this.detailModel = detailModel;
        numberFormat = DecimalFormat.getCurrencyInstance();
        dateFormat = DateFormat.getDateInstance();
    }
    
    private void initEventHandling() {
    }
    
    //******************************************************************
    //In dieser Methode werden alle oben definierten Objekte instanziert
    //******************************************************************
    public void initComponents(){
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

        panel = CWComponentFactory.createViewPanel(detailModel.getHeaderInfo());
    }
    //**************************************************************************
    
    //**************************************************************************
    //Diese Methode gibt die Maske des StartPanels in Form einse JPanels zurück
    //**************************************************************************
    public JPanel buildPanel(){
        initComponents();
        initEventHandling();
        
        panel.getButtonPanel().add(cancelButton);
        panel.getButtonPanel().add(printButton);
        
        FormLayout layout = new FormLayout("pref, 10dlu, pref", 
                "pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 10dlu, pref");
        panel.getTopPanel().setLayout(layout);
        CellConstraints cc = new CellConstraints();
        
        panel.getTopPanel().add(courseName, cc.xy(1, 1));
        panel.getTopPanel().add(beginDate, cc.xy(1, 3));
        panel.getTopPanel().add(endDate, cc.xy(1, 5));
        panel.getTopPanel().add(price, cc.xy(1, 7));
        
        panel.getTopPanel().add(vCourseName, cc.xy(3, 1));
        panel.getTopPanel().add(vBeginDate, cc.xy(3, 3));
        panel.getTopPanel().add(vEndDate, cc.xy(3, 5));
        panel.getTopPanel().add(vPrice, cc.xy(3, 7));
        
        panel.getTopPanel().add(descLabel, cc.xy(1, 9));
        
        panel.getContentPanel().add(new JScrollPane(coursePartTable), BorderLayout.CENTER);
        panel.addDisposableListener(this);
        return panel;
    }

    public void dispose() {
        panel.removeDisposableListener(this);
        componentContainer.dispose();
        detailModel.dispose();
    }
    //********************************************
}
