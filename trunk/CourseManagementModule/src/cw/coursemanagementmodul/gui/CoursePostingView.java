package cw.coursemanagementmodul.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jdesktop.swingx.decorator.HighlighterFactory;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.accountmanagementmodul.pojo.AccountPosting;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWButtonGroup;
import cw.boardingschoolmanagement.gui.component.CWComboBox;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWDateChooser;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.gui.component.CWRadioButton;
import cw.boardingschoolmanagement.gui.component.CWTable;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.helper.CWTableSelectionConverter;
/**
 *
 * @author André Salmhofer
 */
public class CoursePostingView extends CWView
{
    
    private CoursePostingPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;
    
    //Tabelle zur Darstellung der angelegten Kurse
    private CWTable coursePartTable;
    private CWComboBox courseComboBox;
    private CWButtonGroup buttonGroup;

    private CWRadioButton normal;
    private CWRadioButton test;

    private CWDateChooser accountingDate;

    private CWLabel accountingDateLabel;

    private CWButton accountingButton;
    private CWButton postingRunsButton;

    private CWLabel courseLabel;

    private CWLabel courseName;
    private CWLabel beginDate;
    private CWLabel endDate;
    private CWLabel price;

    private CWLabel vCourseName;
    private CWLabel vBeginDate;
    private CWLabel vEndDate;
    private CWLabel vPrice;
    //********************************************

    private CWPanel detailPanel;

    private Format dateFormat;
    private Format currencyFormat;

    public CoursePostingView(CoursePostingPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }


    //******************************************************************
    //In dieser Methode werden alle oben definierten Objekte instanziert
    //******************************************************************
    private void initComponents(){
        dateFormat = DateFormat.getDateInstance();
        currencyFormat = NumberFormat.getCurrencyInstance();

        coursePartTable = CWComponentFactory.createTable("Keine Kursteilnehmer ausgewaehlt!");
        coursePartTable.setColumnControlVisible(true);
        coursePartTable.setAutoCreateRowSorter(true);
        coursePartTable.setPreferredScrollableViewportSize(new Dimension(10,10));
        coursePartTable.setHighlighters(HighlighterFactory.createSimpleStriping());

        coursePartTable.setModel(model.createCoursePartTableModel(model.getCoursePartSelection()));
        coursePartTable.setSelectionModel(
                new SingleListSelectionAdapter(
                    new CWTableSelectionConverter(
                        model.getCoursePartSelection().getSelectionIndexHolder(),
                        coursePartTable)));

        courseComboBox = CWComponentFactory.createComboBox(model.getCourseSelection());
        
        courseLabel = CWComponentFactory.createLabel("Kurs:");

        normal = CWComponentFactory.createRadioButton(model.getNormalRadioButtonAction());
        
        test = CWComponentFactory.createRadioButton(model.getTestRadioButtonAction());

        test.setSelected(true);

        accountingButton = CWComponentFactory.createButton(model.getPostingAction());

        accountingDate = CWComponentFactory.createDateChooser(model.getPostingPresentationModel().getBufferedModel(AccountPosting.PROPERTYNAME_POSTINGDATE));

        accountingDateLabel = CWComponentFactory.createLabel("Buchungsdatum:");

        accountingDate.setDate(new Date());

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

        postingRunsButton = CWComponentFactory.createButton(model.getRunsAction());

        accountingButton.setToolTipText(CWComponentFactory.createToolTip(
                "Buchen",
                "Hier koenne Sie eine Buchung durchfuehren!",
                "cw/coursemanagementmodul/images/start.png"));
        postingRunsButton.setToolTipText(CWComponentFactory.createToolTip(
                "Gebuehrenlaeufe",
                "Hier sehen Sie alle bereits gebuchten Gebuehrenlaeufe!",
                "cw/coursemanagementmodul/images/postingRuns.png"));

        componentContainer = CWComponentFactory.createComponentContainer();
                componentContainer.addComponent(courseComboBox);
                componentContainer.addComponent(accountingButton);
                componentContainer.addComponent(accountingDate);
                componentContainer.addComponent(accountingDateLabel);
                componentContainer.addComponent(beginDate);
                componentContainer.addComponent(courseComboBox);
                componentContainer.addComponent(courseLabel);
                componentContainer.addComponent(courseName);
                componentContainer.addComponent(coursePartTable);
                componentContainer.addComponent(endDate);
                componentContainer.addComponent(normal);
                componentContainer.addComponent(postingRunsButton);
                componentContainer.addComponent(price);
                componentContainer.addComponent(test);
                componentContainer.addComponent(vBeginDate);
                componentContainer.addComponent(vCourseName);
                componentContainer.addComponent(vEndDate);
                componentContainer.addComponent(vPrice);
    }
    //**************************************************************************

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

    //**************************************************************************
    //Diese Methode gibt die Maske des StartPanels in Form einse JPanels zurueck
    //**************************************************************************
    private void buildView(){

        this.setHeaderInfo(model.getHeaderInfo());

        JPanel coursePartPanel = CWComponentFactory.createPanel();
        detailPanel = CWComponentFactory.createPanel();

        FormLayout layout = new FormLayout("pref, 2dlu, pref, 2dlu, pref:grow",
                "pref, 4dlu, pref, 10dlu, pref, 10dlu, pref, 4dlu," +
                "fill:pref:grow, 10dlu, pref, 10dlu, pref, 5dlu, pref");
        FormLayout panelLayout = new FormLayout("fill:pref:grow", "fill:pref:grow");
        FormLayout detailLayout = new FormLayout("pref, 4dlu, pref, 10dlu, pref, 4dlu, pref",
                "pref, 5dlu, pref, 5dlu, pref");

        this.getButtonPanel().add(accountingButton);
        this.getButtonPanel().add(postingRunsButton);

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

        PanelBuilder panelBuilder = new PanelBuilder(layout, this.getContentPanel());

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

        this.setOpaque(false);
        coursePartPanel.setOpaque(false);
    }

    @Override
    public void dispose() {
        componentContainer.dispose();
        model.dispose();
    }
    //**************************************************************************
}
