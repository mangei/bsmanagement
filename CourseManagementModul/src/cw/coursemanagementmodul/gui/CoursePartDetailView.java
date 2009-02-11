/*
 * Die Course View representiert das Startfenster,
 * dass angezeigt wird, wenn man auf 'Kurs' in der
 * linken Auswahlliste klickt.
 */

package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

/**
 *
 * @author André Salmhofer
 */
public class CoursePartDetailView{
    private CoursePartDetailPresentationModel detailModel;
    
    //Definieren der Objekte in der oberen Leiste
    private JButton printButton;
    private JButton cancelButton;
    
    private JLabel info0;
    private JLabel info1;
    private JLabel info2;
    private JLabel info3;
    private JLabel info4;
    private JLabel info5;
    private JLabel info6;
    private JLabel info7;
    private JLabel info8;
    private JLabel info9;
    private JLabel info10;
    private JLabel info11;
    private JLabel info12;
    private JLabel info13;
    private JLabel info14;
    private JLabel info15;
    private JLabel info16;
    private JLabel info17;
    
    private JLabel vInfo1;
    private JLabel vInfo2;
    private JLabel vInfo3;
    private JLabel vInfo4;
    private JLabel vInfo5;
    private JLabel vInfo6;
    private JLabel vInfo7;
    private JLabel vInfo8;
    private JLabel vInfo9;
    private JLabel vInfo10;
    private JLabel vInfo11;
    private JLabel vInfo12;
    private JLabel vInfo13;
    private JLabel vInfo14;
    private JLabel vInfo15;
    private JLabel vInfo16;
    private JLabel vInfo17;
    
    private JLabel vCourseName;
    private JLabel vBeginDate;
    private JLabel vEndDate;
    private JLabel vPrice;
    
    private JLabel descLabel;
    //********************************************
    
    //Tabelle zur Darstellung der angelegten Kurse
    private JXTable courseTable;//Kursteilnehmer eines Kurses
    private JTextField searchTextField;
    //********************************************
    
    public CoursePartDetailView(CoursePartDetailPresentationModel detailModel) {
        this.detailModel = detailModel;
    }
    
    private void initEventHandling() {
    }
    
    //******************************************************************
    //In dieser Methode werden alle oben definierten Objekte instanziert
    //******************************************************************
    public void initComponents(){
        cancelButton = new JButton(detailModel.getCancelAction());
        printButton = new JButton(detailModel.getPrintAction());
        
        courseTable = new JXTable();
        courseTable.setColumnControlVisible(true);
        courseTable.setAutoCreateRowSorter(true);
        courseTable.setPreferredScrollableViewportSize(new Dimension(10,10));
        courseTable.setHighlighters(HighlighterFactory.createSimpleStriping());
        
        courseTable.setModel(detailModel.createCoursePartTableModel(detailModel.getCoursePartSelection()));
        courseTable.setSelectionModel(
                new SingleListSelectionAdapter(
                detailModel.getCourseSelection().getSelectionIndexHolder()));
        
        searchTextField = new JTextField();
        
        descLabel = new JLabel("Alle Kursteilnehmer des Kurses:");
        
        
        
        vInfo1 = new JLabel(detailModel.getSelectedCourseParticipant().getCostumer().getTitle());
        vInfo2 = new JLabel(detailModel.getSelectedCourseParticipant().getCostumer().getForename());
        vInfo3 = new JLabel(detailModel.getSelectedCourseParticipant().getCostumer().getForename2());
        vInfo4 = new JLabel(detailModel.getSelectedCourseParticipant().getCostumer().getSurname());
        vInfo5 = new JLabel(detailModel.getSelectedCourseParticipant().getCostumer().getBirthday() + "");
        vInfo6 = new JLabel(detailModel.getSelectedCourseParticipant().getCostumer().getCountry());
        vInfo7 = new JLabel(detailModel.getSelectedCourseParticipant().getCostumer().getCity());
        vInfo8 = new JLabel(detailModel.getSelectedCourseParticipant().getCostumer().getStreet());
        vInfo9 = new JLabel(detailModel.getSelectedCourseParticipant().getCostumer().getProvince());
        vInfo10 = new JLabel(detailModel.getSelectedCourseParticipant().getCostumer().getPostOfficeNumber() + "");
        vInfo11 = new JLabel(detailModel.getSelectedCourseParticipant().getCostumer().getGender() + "");
        
        vInfo12 = new JLabel(detailModel.getSelectedCourseParticipant().getCostumer().getEmail());
        vInfo13 = new JLabel(detailModel.getSelectedCourseParticipant().getCostumer().getFax());
        vInfo14 = new JLabel(detailModel.getSelectedCourseParticipant().getCostumer().getMobilephone());
        vInfo15 = new JLabel(detailModel.getSelectedCourseParticipant().getCostumer().getLandlinephone());

        vInfo16 = new JLabel(detailModel.getSelectedCourseParticipant().getCostumer().getCreationdate() + "");
        vInfo17 = new JLabel(detailModel.getSelectedCourseParticipant().getCostumer().getComment());
        
//        vInfo1 = new JLabel("NO_DATA");
//        vInfo2 = new JLabel("NO_DATA");
//        vInfo3 = new JLabel("NO_DATA");
//        vInfo4 = new JLabel("NO_DATA");
//        vInfo5 = new JLabel("NO_DATA");
//        vInfo6 = new JLabel("NO_DATA");
//        vInfo7 = new JLabel("NO_DATA");
//        vInfo8 = new JLabel("NO_DATA");
//        vInfo9 = new JLabel("NO_DATA");
//        vInfo10 = new JLabel("NO_DATA");
//        vInfo11 = new JLabel("NO_DATA");
//        
//        vInfo12 = new JLabel("NO_DATA");
//        vInfo13 = new JLabel("NO_DATA");
//        vInfo14 = new JLabel("NO_DATA");
//        vInfo15 = new JLabel("NO_DATA");

        vInfo16 = new JLabel("NO_DATA");
        vInfo17 = new JLabel("NO_DATA");
        
        info0 = new JLabel("Titel:");
        info1 = new JLabel("Vorname:");
        info2 = new JLabel("2. Vorname:");
        info3 = new JLabel("Nachname:");
        info4 = new JLabel("Geburtstag:");
        info5 = new JLabel("Staatsbürgerschaft:");
        info6 = new JLabel("Stadt:");
        info7 = new JLabel("Straße:");
        info8 = new JLabel("Bundesland:");
        info9 = new JLabel("PLZ:");
        info10 = new JLabel("Geschlecht:");
        info11 = new JLabel("E-Mail:");
        info12 = new JLabel("Fax:");
        info13 = new JLabel("Handy:");
        info14 = new JLabel("Telefon:");
        info15 = new JLabel("Erstelldatum:");
        info16 = new JLabel("Zusatz:");
        
    }
    //**************************************************************************
    
    //**************************************************************************
    //Diese Methode gibt die Maske des StartPanels in Form einse JPanels zurück
    //**************************************************************************
    public JPanel buildPanel(){
        initComponents();
        initEventHandling();
        JViewPanel panel = new JViewPanel("Kursteilnehmer-Detailansicht");
        JViewPanel costumerPanel = new JViewPanel("Kundendaten");
        JViewPanel coursePanel = new JViewPanel("Kurse");
       
        JViewPanel view = new JViewPanel();
        
        JPanel mainPanel = new JPanel();
        
        view.getButtonPanel().add(cancelButton);
        view.getButtonPanel().add(printButton);
        
        FormLayout mainLayout = new FormLayout("pref:grow", "pref:grow, 10dlu, pref:grow");
        FormLayout layout = new FormLayout("pref, 10dlu, pref", 
                "pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref," +
                "2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref" +
                ",2dlu, pref, 2dlu, pref, 2dlu, pref");
        
        costumerPanel.getTopPanel().setLayout(layout);
        view.getContentPanel().setLayout(mainLayout);
        
        CellConstraints cc = new CellConstraints();
        
        costumerPanel.getTopPanel().add(info0, cc.xy(1, 1));
        costumerPanel.getTopPanel().add(info1, cc.xy(1, 3));
        costumerPanel.getTopPanel().add(info2, cc.xy(1, 5));
        costumerPanel.getTopPanel().add(info3, cc.xy(1, 7));
        costumerPanel.getTopPanel().add(info4, cc.xy(1, 9));
        costumerPanel.getTopPanel().add(info5, cc.xy(1, 11));
        costumerPanel.getTopPanel().add(info6, cc.xy(1, 13));
        costumerPanel.getTopPanel().add(info7, cc.xy(1, 15));
        costumerPanel.getTopPanel().add(info8, cc.xy(1, 17));
        costumerPanel.getTopPanel().add(info9, cc.xy(1, 19));
        costumerPanel.getTopPanel().add(info10, cc.xy(1, 21));
        costumerPanel.getTopPanel().add(info11, cc.xy(1, 23));
        costumerPanel.getTopPanel().add(info12, cc.xy(1, 25));
        costumerPanel.getTopPanel().add(info13, cc.xy(1, 27));
        costumerPanel.getTopPanel().add(info14, cc.xy(1, 29));
        costumerPanel.getTopPanel().add(info15, cc.xy(1, 31));
        costumerPanel.getTopPanel().add(info16, cc.xy(1, 33));
        
        costumerPanel.getTopPanel().add(vInfo1, cc.xy(3, 1));
        costumerPanel.getTopPanel().add(vInfo2, cc.xy(3, 3));
        costumerPanel.getTopPanel().add(vInfo3, cc.xy(3, 5));
        costumerPanel.getTopPanel().add(vInfo4, cc.xy(3, 7));
        costumerPanel.getTopPanel().add(vInfo5, cc.xy(3, 9));
        costumerPanel.getTopPanel().add(vInfo6, cc.xy(3, 11));
        costumerPanel.getTopPanel().add(vInfo7, cc.xy(3, 13));
        costumerPanel.getTopPanel().add(vInfo8, cc.xy(3, 15));
        costumerPanel.getTopPanel().add(vInfo9, cc.xy(3, 17));
        costumerPanel.getTopPanel().add(vInfo10, cc.xy(3, 19));
        costumerPanel.getTopPanel().add(vInfo11, cc.xy(3, 21));
        costumerPanel.getTopPanel().add(vInfo12, cc.xy(3, 23));
        costumerPanel.getTopPanel().add(vInfo13, cc.xy(3, 25));
        costumerPanel.getTopPanel().add(vInfo14, cc.xy(3, 27));
        costumerPanel.getTopPanel().add(vInfo15, cc.xy(3, 29));
        costumerPanel.getTopPanel().add(vInfo16, cc.xy(3, 31));
        costumerPanel.getTopPanel().add(vInfo17, cc.xy(3, 33));
        
        coursePanel.getContentPanel().add(courseTable);
        
        costumerPanel.setOpaque(false);
        coursePanel.setOpaque(false);
        
        view.getContentPanel().add(costumerPanel, cc.xy(1, 1));
        view.getContentPanel().add(coursePanel, cc.xy(1, 3));
        
        return view;
    }
    //********************************************
}
