/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.coursemanagementmodul.gui;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JDateChooser;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JButtonPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.manager.GUIManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import cw.coursemanagementmodul.pojo.Course;

/**
 *
 * @author André Salmhofer
 */
public class EditCourseView {
    private EditCoursePresentationModel model;
    
    //*********************Komponenten definieren*******************************
    private JLabel nameLabel;
    private JLabel beginLabel;
    private JLabel endLabel;
    private JLabel valueLabel;
    private JTextField nameTextField;
    private JTextField valueTextField;
    private JButton saveButton;
    private JButton saveAndCloseButton;
    private JButton rollbackButton;
    private JButton cancelButton;
    private JDateChooser beginDate;
    private JDateChooser endDate;
    //**************************************************************************
            
    public EditCourseView(EditCoursePresentationModel model) {
        this.model = model;
    }
    
    //**************************************************************************
    //Initialisieren der oben definierten Komponenten
    //Bei den meisten Komponenten kann man den PropertyName als Paramenter
    //mitgeben. Bei Datum-Komponenten ist dies jedoch nicht möglich. Hierzu
    //wird die Methode connect() von der Klasse PropertyConnector benötigt.
    //**************************************************************************
    public void initComponents(){
        nameLabel = CWComponentFactory.createLabel("Kursname");
        beginLabel = CWComponentFactory.createLabel("Beginn");
        endLabel = CWComponentFactory.createLabel("Ende");
        valueLabel = CWComponentFactory.createLabel("Betrag");
        
        nameTextField = CWComponentFactory.createTextField(model.getBufferedModel(Course.PROPERTYNAME_NAME), false);
        valueTextField = CWComponentFactory.createCurrencyTextField(model.getBufferedModel(Course.PROPERTYNAME_PRICE));
        saveButton = CWComponentFactory.createButton(model.getSaveButtonAction());
        saveAndCloseButton = CWComponentFactory.createButton(model.getSaveCancelButtonAction());
        rollbackButton = CWComponentFactory.createButton(model.getResetButtonAction());
        cancelButton = CWComponentFactory.createButton(model.getCancelButtonAction());
        
        cancelButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                GUIManager.changeToLastView();
            }
        });
        
        beginDate = CWComponentFactory.createDateChooser(model.getBufferedModel(Course.PROPERTYNAME_BEGINDATE));
        
        endDate = CWComponentFactory.createDateChooser(model.getBufferedModel(Course.PROPERTYNAME_ENDDATE));
    }
    //**************************************************************************
    
    //**************************************************************************
    //Diese Methode gibt die Maske des EditPanels in Form einse JPanels zurück
    //**************************************************************************
    public JPanel buildPanel(){
        initComponents();
        
        JViewPanel view = CWComponentFactory.createViewPanel(model.getHeaderInfo());
        
        JButtonPanel buttonPanel = view.getButtonPanel();
        
        buttonPanel.add(saveButton);
        buttonPanel.add(saveAndCloseButton);
        buttonPanel.add(rollbackButton);
        buttonPanel.add(cancelButton);
        
        FormLayout layout = new FormLayout("pref, 4dlu, pref, 150dlu, pref",
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref"); // rows
        
        CellConstraints cc = new CellConstraints();
        view.getContentPanel().setLayout(layout);
        
        view.getContentPanel().add(nameLabel, cc.xy (1, 1));
        view.getContentPanel().add(nameTextField, cc.xyw(3, 1, 3));
        view.getContentPanel().add(beginLabel, cc.xy (1, 3));
        view.getContentPanel().add(beginDate, cc.xy (3, 3));
        view.getContentPanel().add(endLabel, cc.xy (1, 5));
        view.getContentPanel().add(endDate, cc.xy (3, 5));
        view.getContentPanel().add(valueLabel, cc.xy(1, 7));
        view.getContentPanel().add(valueTextField, cc.xy(3, 7));
        
        return view;
    }
    //**************************************************************************
}
