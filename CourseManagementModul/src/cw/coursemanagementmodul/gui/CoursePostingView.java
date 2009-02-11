/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JDateChooser;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.customermanagementmodul.pojo.Posting;
import java.awt.Dimension;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

/**
 *
 * @author André Salmhofer
 */
public class CoursePostingView {
    
    private CoursePostingPresentationModel model;
    
    //Tabelle zur Darstellung der angelegten Kurse
    private JXTable coursePartTable;
    private JComboBox courseComboBox;
    private ButtonGroup buttonGroup;

    private JRadioButton normal;
    private JRadioButton test;

    private JDateChooser accountingDate;

    private JLabel accountingDateLabel;

    private JButton accountingButton;

    private JLabel courseLabel;
    private JLabel coursePartLabel;
    //********************************************

    public CoursePostingView(CoursePostingPresentationModel model) {
        this.model = model;
    }

    private void initEventHandling() {
    }

    //******************************************************************
    //In dieser Methode werden alle oben definierten Objekte instanziert
    //******************************************************************
    public void initComponents(){
        coursePartTable = CWComponentFactory.createTable("");
        coursePartTable.setColumnControlVisible(true);
        coursePartTable.setAutoCreateRowSorter(true);
        coursePartTable.setPreferredScrollableViewportSize(new Dimension(10,10));
        coursePartTable.setHighlighters(HighlighterFactory.createSimpleStriping());

        coursePartTable.setModel(model.createCoursePartTableModel(model.getCoursePartSelection()));
        coursePartTable.setSelectionModel(
                new SingleListSelectionAdapter(
                model.getCoursePartSelection().getSelectionIndexHolder()));

        courseComboBox = CWComponentFactory.createComboBox(model.getCourseSelection());

        courseLabel = CWComponentFactory.createLabel("Kurs:");

        normal = CWComponentFactory.createRadioButton(model.getNormalRadioButtonAction());
        
        test = CWComponentFactory.createRadioButton(model.getTestRadioButtonAction());

        accountingButton = CWComponentFactory.createButton(model.getPostingAction());

        accountingDate = CWComponentFactory.createDateChooser(model.getPostingPresentationModel().getBufferedModel(Posting.PROPERTYNAME_POSTINGDATE));

        accountingDateLabel = CWComponentFactory.createLabel("Buchungsdatum:");

        coursePartLabel = CWComponentFactory.createLabel("Kursteilnehmer:");
    }
    //**************************************************************************


    //**************************************************************************
    //Diese Methode gibt die Maske des StartPanels in Form einse JPanels zurück
    //**************************************************************************
    public JPanel buildPanel(){
        initComponents();
        JViewPanel panel = CWComponentFactory.createViewPanel(model.getHeaderInfo());
        JPanel coursePartPanel = CWComponentFactory.createPanel();

        FormLayout layout = new FormLayout("pref, 2dlu, pref:grow, 2dlu, right:pref","pref, 10dlu, pref, 4dlu, fill:pref:grow, 2dlu, pref, 5dlu, pref");
        FormLayout panelLayout = new FormLayout("fill:pref:grow", "fill:pref:grow");

        panel.getButtonPanel().add(accountingButton);

        panel.getContentPanel().setLayout(layout);
        coursePartPanel.setLayout(panelLayout);

        CellConstraints cc = new CellConstraints();

        coursePartPanel.add(new JScrollPane(coursePartTable), cc.xy(1, 1));

        panel.getContentPanel().add(coursePartLabel, cc.xy(1, 3));
        panel.getContentPanel().add(courseLabel, cc.xy(1, 1));
        panel.getContentPanel().add(courseComboBox, cc.xy(3, 1));
        panel.getContentPanel().add(coursePartPanel, cc.xyw(1, 5, 3));

        buttonGroup = CWComponentFactory.createButtonGroup(normal, test);
        
        //RadioButtons
        panel.getContentPanel().add(normal, cc.xy(1, 7));
        panel.getContentPanel().add(test, cc.xy(3, 7));

        //Daten
        panel.getContentPanel().add(accountingDateLabel, cc.xy(1, 9));
        panel.getContentPanel().add(accountingDate, cc.xy(3, 9));

        panel.setOpaque(false);
        coursePartPanel.setOpaque(false);

        initEventHandling();
        return panel;
    }
    //**************************************************************************
}
