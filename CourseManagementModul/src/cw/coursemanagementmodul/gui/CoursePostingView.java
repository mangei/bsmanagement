/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JDateChooser;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.gui.helper.JXTableSelectionConverter;
import cw.boardingschoolmanagement.interfaces.Disposable;
import cw.customermanagementmodul.pojo.Posting;
import java.awt.Dimension;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
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
public class CoursePostingView implements Disposable{
    
    private CoursePostingPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;
    
    //Tabelle zur Darstellung der angelegten Kurse
    private JXTable coursePartTable;
    private JComboBox courseComboBox;
    private ButtonGroup buttonGroup;

    private JRadioButton normal;
    private JRadioButton test;

    private JDateChooser accountingDate;

    private JLabel accountingDateLabel;

    private JButton accountingButton;
    private JButton postingRunsButton;

    private JLabel courseLabel;

    private JLabel courseName;
    private JLabel beginDate;
    private JLabel endDate;
    private JLabel price;

    private JLabel vCourseName;
    private JLabel vBeginDate;
    private JLabel vEndDate;
    private JLabel vPrice;
    //********************************************

    private JPanel detailPanel;

    private JViewPanel panel;

    public CoursePostingView(CoursePostingPresentationModel model) {
        this.model = model;
    }

    private void initEventHandling() {
        detailPanel.setVisible(false);
        model.getCourseSelection().addValueChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if(model.getCourseSelection().isSelectionEmpty()){
                    detailPanel.setVisible(false);
                }
                else{
                    detailPanel.setVisible(true);
                }
            }
        });
    }

    //******************************************************************
    //In dieser Methode werden alle oben definierten Objekte instanziert
    //******************************************************************
    public void initComponents(){
        coursePartTable = CWComponentFactory.createTable("Keine Kursteilnehmer ausgewählt!");
        coursePartTable.setColumnControlVisible(true);
        coursePartTable.setAutoCreateRowSorter(true);
        coursePartTable.setPreferredScrollableViewportSize(new Dimension(10,10));
        coursePartTable.setHighlighters(HighlighterFactory.createSimpleStriping());

        coursePartTable.setModel(model.createCoursePartTableModel(model.getCoursePartSelection()));
        coursePartTable.setSelectionModel(
                new SingleListSelectionAdapter(
                    new JXTableSelectionConverter(
                        model.getCoursePartSelection().getSelectionIndexHolder(),
                        coursePartTable)));

        courseComboBox = CWComponentFactory.createComboBox(model.getCourseSelection());
        
        courseLabel = new JLabel("Kurs:");//CWComponentFactory.createLabel("Kurs:");

        normal = CWComponentFactory.createRadioButton(model.getNormalRadioButtonAction());
        
        test = CWComponentFactory.createRadioButton(model.getTestRadioButtonAction());

        test.setSelected(true);

        accountingButton = CWComponentFactory.createButton(model.getPostingAction());

        accountingDate = CWComponentFactory.createDateChooser(model.getPostingPresentationModel().getBufferedModel(Posting.PROPERTYNAME_POSTINGDATE));

        accountingDateLabel = CWComponentFactory.createLabel("Buchungsdatum:");

        accountingDate.setDate(new Date());

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

        postingRunsButton = CWComponentFactory.createButton(model.getRunsAction());

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(courseComboBox)
                .addComponent(accountingButton)
                .addComponent(accountingDate)
                .addComponent(accountingDateLabel)
                .addComponent(beginDate)
                .addComponent(courseComboBox)
                .addComponent(courseLabel)
                .addComponent(courseName)
                .addComponent(coursePartTable)
                .addComponent(detailPanel)
                .addComponent(endDate)
                .addComponent(normal)
                .addComponent(postingRunsButton)
                .addComponent(price)
                .addComponent(test)
                .addComponent(vBeginDate)
                .addComponent(vCourseName)
                .addComponent(vEndDate)
                .addComponent(vPrice);

        panel = CWComponentFactory.createViewPanel(model.getHeaderInfo());
    }
    //**************************************************************************


    //**************************************************************************
    //Diese Methode gibt die Maske des StartPanels in Form einse JPanels zurück
    //**************************************************************************
    public JPanel buildPanel(){
        initComponents();
        JPanel coursePartPanel = CWComponentFactory.createPanel();
        detailPanel = CWComponentFactory.createPanel();

        FormLayout layout = new FormLayout("pref, 2dlu, pref, 2dlu, pref:grow",
                "pref, 4dlu, pref, 10dlu, pref, 10dlu, pref, 4dlu," +
                "fill:pref:grow, 10dlu, pref, 10dlu, pref, 5dlu, pref");
        FormLayout panelLayout = new FormLayout("fill:pref:grow", "fill:pref:grow");
        FormLayout detailLayout = new FormLayout("pref, 4dlu, pref, 10dlu, pref, 4dlu, pref",
                "pref, 5dlu, pref, 5dlu, pref");

        panel.getButtonPanel().add(accountingButton);
        panel.getButtonPanel().add(postingRunsButton);

        panel.getContentPanel().setLayout(layout);
        coursePartPanel.setLayout(panelLayout);

        detailPanel.setLayout(detailLayout);

        CellConstraints cc = new CellConstraints();

        detailPanel.add(courseName, cc.xy(1, 1));
        detailPanel.add(price, cc.xy(1, 3));
        detailPanel.add(beginDate, cc.xy(5, 1));
        detailPanel.add(endDate, cc.xy(5, 3));
        
        detailPanel.add(vCourseName, cc.xy(3, 1));
        detailPanel.add(vPrice, cc.xy(3, 3));
        detailPanel.add(vBeginDate, cc.xy(7, 1));
        detailPanel.add(vEndDate, cc.xy(7, 3));
        
        coursePartPanel.add(new JScrollPane(coursePartTable), cc.xy(1, 1));

        PanelBuilder panelBuilder = new PanelBuilder(layout, panel.getContentPanel());

        panelBuilder.addSeparator("<html><b>Zu buchende Kurse</b></html>", cc.xyw(1, 1, 5));
        panelBuilder.add(courseLabel, cc.xy(1, 3));
        panelBuilder.add(courseComboBox, cc.xy(3, 3));

        panelBuilder.add(detailPanel, cc.xyw(1, 5, 5));

        panelBuilder.addSeparator("<html><b>Kursteilnehmer</b></html>", cc.xyw(1, 7, 5));
        panelBuilder.add(coursePartPanel, cc.xyw(1, 9, 5));

        buttonGroup = CWComponentFactory.createButtonGroup(normal, test);
        
        //RadioButtons
        panelBuilder.addSeparator("<html><b>Buchungsart</b></html>", cc.xyw(1, 13, 5));
        panelBuilder.add(normal, cc.xy(1, 15));
        panelBuilder.add(test, cc.xy(3, 15));

        //Daten
        panelBuilder.add(accountingDateLabel, cc.xy(1, 11));
        panelBuilder.add(accountingDate, cc.xy(3, 11));

        panel.setOpaque(false);
        coursePartPanel.setOpaque(false);

        initEventHandling();
        panel.addDisposableListener(this);
        return panel;
    }

    public void dispose() {
        panel.removeDisposableListener(this);
        componentContainer.dispose();
        model.dispose();
    }
    //**************************************************************************

//    public double getTotalValue(){
//
//    }
}
