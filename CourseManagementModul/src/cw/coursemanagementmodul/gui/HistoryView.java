/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import java.awt.Dimension;
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
public class HistoryView {
    private HistoryPresentationModel model;

    //Tabelle zur Darstellung der angelegten Kurse
    private JXTable coursePartTable;
    private JComboBox courseComboBox;
    private JLabel courseLabel;
    //********************************************

    private JButton detailButton;

    public HistoryView(HistoryPresentationModel model) {
        this.model = model;
    }

    private void initEventHandling() {
        coursePartTable.addMouseListener(model.getDoubleClickHandler());
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

        detailButton = CWComponentFactory.createButton(model.getDetailAction());
        courseLabel = CWComponentFactory.createLabel("Kurs:");
    }
    //**************************************************************************


    //**************************************************************************
    //Diese Methode gibt die Maske des StartPanels in Form einse JPanels zurück
    //**************************************************************************
    public JPanel buildPanel(){
        initComponents();
        JViewPanel panel = CWComponentFactory.createViewPanel(model.getHeaderInfoComboBox());
        JPanel coursePartPanel = CWComponentFactory.createPanel();
        JPanel extendPanel = CWComponentFactory.createPanel();

        panel.getButtonPanel().add(detailButton);

        FormLayout layout = new FormLayout("pref, 2dlu, pref:grow, 2dlu, right:pref","pref, 10dlu, fill:pref:grow, 2dlu, pref, 5dlu, pref");
        FormLayout panelLayout = new FormLayout("fill:pref:grow", "fill:pref:grow");

        panel.getContentPanel().setLayout(layout);
        coursePartPanel.setLayout(panelLayout);

        CellConstraints cc = new CellConstraints();

        coursePartPanel.add(new JScrollPane(coursePartTable), cc.xy(1, 1));

        extendPanel.setLayout(new FormLayout("pref, 4dlu, pref", "pref"));
        extendPanel.add(courseLabel, cc.xy(1, 1));
        extendPanel.add(courseComboBox, cc.xy(3, 1));
        
        panel.getContentPanel().add(extendPanel, cc.xy(1, 1));
        panel.getContentPanel().add(coursePartPanel, cc.xyw(1, 3, 3));

        panel.setOpaque(false);
        coursePartPanel.setOpaque(false);

        initEventHandling();
        return panel;
    }
    //**************************************************************************
}
