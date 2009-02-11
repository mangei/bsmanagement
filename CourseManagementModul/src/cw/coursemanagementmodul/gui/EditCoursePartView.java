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
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

/**
 *
 * @author André Salmhofer
 */
public class EditCoursePartView {
    private EditCoursePartPresentationModel model;
    
    //*********************Komponenten definieren*******************************
    private JLabel costumerLabel;
    private JLabel courseLabel;
    
    private JXTable courseTable;
    private JXTable activityTable;
    private JXTable subjectTable;
    
    private JButton courseButton;
    private JButton activityButton;
    private JButton subjectButton;
    private JButton accountingButton;
    
    private JLabel courseName;
    private JLabel beginDate;
    private JLabel endDate;
    private JLabel price;

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
        costumerLabel = CWComponentFactory.createLabel("Kunde");
        courseLabel = CWComponentFactory.createLabel("Kurs");
        
        courseTable = CWComponentFactory.createTable("");
        courseTable.setColumnControlVisible(true);
        courseTable.setAutoCreateRowSorter(true);
        courseTable.setPreferredScrollableViewportSize(new Dimension(10, 70));
        courseTable.setHighlighters(HighlighterFactory.createSimpleStriping());
        
        courseTable.setModel(model.createCourseTableModel(model.getCourseSelection()));
        courseTable.setSelectionModel(
                new SingleListSelectionAdapter(
                model.getCourseSelection().getSelectionIndexHolder()));
        
        courseTable.setSelectionModel(new SingleListSelectionAdapter(model.getCourseSelection().getSelectionIndexHolder()));
        
        activityTable = CWComponentFactory.createTable("");
        activityTable.setColumnControlVisible(true);
        activityTable.setAutoCreateRowSorter(true);
        activityTable.setPreferredScrollableViewportSize(new Dimension(10, 30));
        activityTable.setHighlighters(HighlighterFactory.createSimpleStriping());
        
        activityTable.setModel(model.createActivityTableModel(model.getActivitySelection()));//TODO-mit ValueModel
        activityTable.setSelectionModel(
                new SingleListSelectionAdapter(
                model.getActivitySelection().getSelectionIndexHolder()));
        
        activityTable.setSelectionModel(new SingleListSelectionAdapter(model.getActivitySelection().getSelectionIndexHolder()));
        
        subjectTable = CWComponentFactory.createTable("");
        subjectTable.setColumnControlVisible(true);
        subjectTable.setAutoCreateRowSorter(true);
        subjectTable.setPreferredScrollableViewportSize(new Dimension(10, 30));
        subjectTable.setHighlighters(HighlighterFactory.createSimpleStriping());
        
        subjectTable.setModel(model.createSubjectTableModel(model.getSubjectSelection()));
        subjectTable.setSelectionModel(
                new SingleListSelectionAdapter(
                model.getSubjectSelection().getSelectionIndexHolder()));
        
        subjectTable.setSelectionModel(new SingleListSelectionAdapter(model.getSubjectSelection().getSelectionIndexHolder()));

        courseButton = CWComponentFactory.createButton(model.getCourseChooserButtonAction());
        activityButton = CWComponentFactory.createButton(model.getActivityButtonAction());
        subjectButton = CWComponentFactory.createButton(model.getSubjectButtonAction());
        accountingButton = CWComponentFactory.createButton(model.getPostingButtonAction());
        
        courseName = CWComponentFactory.createLabel("Kursname:");
        beginDate = CWComponentFactory.createLabel("Beginn:");
        endDate = CWComponentFactory.createLabel("Ende:");
        price = CWComponentFactory.createLabel("Preis:");

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
    }
    //**************************************************************************
    
    //**************************************************************************
    //Diese Methode gibt die Maske des EditPanels in Form einse JPanels zurück
    //**************************************************************************
    public JPanel buildPanel(){
        initComponents();
        
        JViewPanel view = CWComponentFactory.createViewPanel(model.getHeaderInfo());

        JPanel panel = new JPanel();
        JViewPanel activityPanel = CWComponentFactory.createViewPanel(new HeaderInfo("Aktivitäten"));
        JViewPanel subjectPanel = CWComponentFactory.createViewPanel(new HeaderInfo("Gegenstände"));
        JViewPanel courseDetailPanel = CWComponentFactory.createViewPanel(new HeaderInfo("Kursdetails"));
        JPanel accountingPanel = CWComponentFactory.createPanel();
        
        activityPanel.getContentPanel().add(new JScrollPane(activityTable));
        
        subjectPanel.getContentPanel().add(new JScrollPane(subjectTable));

        JButtonPanel buttonPanel = view.getButtonPanel();
        
        buttonPanel.add(courseButton);
        buttonPanel.add(activityButton);
        buttonPanel.add(subjectButton);
        buttonPanel.add(accountingButton);
        
        FormLayout layout = new FormLayout("pref:grow, 15dlu, fill:pref:grow",
                "pref, 4dlu, fill:pref:grow, 20dlu, pref, 4dlu," +
                "fill:pref:grow, 20dlu, pref, 4dlu, fill:pref:grow, 20dlu, pref, 10dlu, pref");
        FormLayout detailLayout = new FormLayout("pref, 10dlu, pref:grow",
                "pref, 5dlu, pref, 5dlu, pref, 5dlu, pref");
        FormLayout accountingLayout = new FormLayout("pref, 4dlu, pref", "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");
        
        CellConstraints cc = new CellConstraints();
        
        panel.setLayout(layout);
        courseDetailPanel.getContentPanel().setLayout(detailLayout);
        
        activityPanel.setOpaque(false);
        subjectPanel.setOpaque(false);
        courseDetailPanel.setOpaque(false);
        accountingPanel.setOpaque(false);

        courseDetailPanel.getContentPanel().add(courseName, cc.xy(1, 1));
        courseDetailPanel.getContentPanel().add(beginDate, cc.xy(1, 3));
        courseDetailPanel.getContentPanel().add(endDate, cc.xy(1, 5));
        courseDetailPanel.getContentPanel().add(price, cc.xy(1, 7));
        
        courseDetailPanel.getContentPanel().add(vCourseName, cc.xy(3, 1));
        courseDetailPanel.getContentPanel().add(vBeginDate, cc.xy(3, 3));
        courseDetailPanel.getContentPanel().add(vEndDate, cc.xy(3, 5));
        courseDetailPanel.getContentPanel().add(vPrice, cc.xy(3, 7));

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
        panelBuilder.add(new JScrollPane(activityTable), cc.xy(1, 7));
        panelBuilder.addSeparator("Gegenstände", cc.xy(1, 9));
        panelBuilder.add(new JScrollPane(subjectTable), cc.xy(1, 11));
        panelBuilder.add(courseDetailPanel, cc.xywh(3, 5, 1, 7));
        panelBuilder.addSeparator("Konto des Kursteilnehmers", cc.xyw(1, 13, 3));
        panelBuilder.add(accountingPanel, cc.xy(1, 15));
        panelBuilder.setOpaque(false);
        
        return view;
    }
    //**************************************************************************
}
