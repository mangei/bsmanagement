/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.coursemanagementmodul.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.interfaces.Disposable;
import cw.coursemanagementmodul.pojo.CourseAddition;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author André Salmhofer
 */
public class TestRunView implements Disposable{
    private TestRunPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;
    
    private JButton backButton;

    private JViewPanel mainPanel;
    private DecimalFormat numberFormat;

    public TestRunView(TestRunPresentationModel model) {
        this.model = model;
        initComponents();
    }

    public void initComponents(){
        backButton = CWComponentFactory.createButton(model.getBackButtonAction());

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(backButton);

        mainPanel = CWComponentFactory.createViewPanel(model.getHeaderInfo());

        numberFormat = new DecimalFormat("#0.00");
    }

    public JComponent buildPanel(){
        JViewPanel view = CWComponentFactory.createViewPanel();
        
        mainPanel.getButtonPanel().add(backButton);

        int vert = 1;
        String layoutVerticalString = "";

        for(int i = 0; i < model.getCoursePartSelection().getSize(); i++){
            layoutVerticalString += "pref,4dlu,pref,20dlu,pref,20dlu,";
        }

        FormLayout layout = new FormLayout("pref, 50dlu, pref, 50dlu, pref, 50dlu, pref",
                layoutVerticalString);


        view.getContentPanel().setLayout(layout);

        CellConstraints cc = new CellConstraints();

        CourseAddition courseAddition;

        PanelBuilder panelBuilder = new PanelBuilder(layout, view.getContentPanel());

        for(int i = 0; i < model.getCoursePartSelection().getSize(); i++){
            String forename = model.getCoursePartSelection().getList().get(i).getCustomer().getForename();
            String surname = model.getCoursePartSelection().getList().get(i).getCustomer().getSurname();
            String street = model.getCoursePartSelection().getList().get(i).getCustomer().getStreet();
            String postal = model.getCoursePartSelection().getList().get(i).getCustomer().getPostOfficeNumber()
                    + " " + model.getCoursePartSelection().getList().get(i).getCustomer().getCity();
            
            courseAddition = model.getSelectedCourseAddition(model.getCoursePartSelection().getList().get(i));
            JPanel activityPanel = CWComponentFactory.createPanel();
            JPanel subjectPanel = CWComponentFactory.createPanel();
            JPanel namePanel = CWComponentFactory.createPanel();
            JPanel calculationPanel = CWComponentFactory.createPanel();

            calculationPanel.setLayout(new FormLayout("pref, 4dlu, pref", "pref, 4dlu, pref, 4dlu, pref"));
            namePanel.setLayout(new FormLayout("pref", "pref, 4dlu, pref, 4dlu, pref"));

            namePanel.add(new JLabel(forename + " " + surname), cc.xy(1, 1));
            namePanel.add(new JLabel(street), cc.xy(1, 3));
            namePanel.add(new JLabel(postal), cc.xy(1, 5));

            calculationPanel.add(new JLabel("Aktivitäten:"), cc.xy(1, 3));
            calculationPanel.add(new JLabel(courseAddition.getCourse().getName()), cc.xy(1, 1));
            calculationPanel.add(new JLabel("Gesamtpreis:"), cc.xy(1, 5));
            calculationPanel.add(new JLabel("€ " + numberFormat.format(getTotalActivityAmount(courseAddition))), cc.xy(3, 3, CellConstraints.RIGHT, CellConstraints.TOP));
            calculationPanel.add(new JLabel("€ " + numberFormat.format(courseAddition.getCourse().getPrice())), cc.xy(3, 1, CellConstraints.RIGHT, CellConstraints.TOP));
            calculationPanel.add(new JLabel("<html><u><b>€ " + numberFormat.format(getTotalAmount(courseAddition)) + "</b></u></html>"), cc.xy(3, 5, CellConstraints.RIGHT, CellConstraints.TOP));
            
            String panelLayoutActivity = "pref,4dlu,";
            for(int l = 0; l < courseAddition.getActivities().size(); l++){
                panelLayoutActivity += "pref,4dlu,";
            }

            String panelLayoutSubject = "";
            for(int m = 0; m < courseAddition.getSubjects().size(); m++){
                panelLayoutSubject += "pref,4dlu,";
            }

            activityPanel.setLayout(new FormLayout("pref, 10dlu, pref", panelLayoutActivity));
            subjectPanel.setLayout(new FormLayout("pref", panelLayoutSubject));
            int vertActivity = 1;

            JLabel priceTest = new JLabel();

            for(int j = 0; j < courseAddition.getActivities().size(); j++){
                priceTest.setText("€ " + numberFormat.format(courseAddition.getActivities().get(j).getPrice()));

                activityPanel.add(new JLabel(courseAddition.getActivities().get(j).getName()), cc.xy(1, vertActivity));
                activityPanel.add(priceTest, cc.xy(3, vertActivity, CellConstraints.RIGHT, CellConstraints.TOP));
                vertActivity += 2;
            }

            int vertSubject = 1;
            for(int k = 0; k < courseAddition.getSubjects().size(); k++){
                subjectPanel.add(new JLabel(courseAddition.getSubjects().get(k).getName()), cc.xy(1, vertSubject));
                vertSubject += 2;
            }

            activityPanel.add(new JLabel("Gesamtaktivitäspreis: "), cc.xy(1, vertActivity));
            activityPanel.add(new JLabel("<html><b>€ " + numberFormat.format(getTotalActivityAmount(courseAddition)) + "</b></html>"), cc.xy(3, vertActivity));

            panelBuilder.add(new JLabel("<html><b>Anschrift</b></html>"), cc.xy(1, vert));
            panelBuilder.add(new JLabel("<html><b>Aktivitäten</b></html>"), cc.xy(3, vert));
            panelBuilder.add(new JLabel("<html><b>Kursgegenstände</b></html>"), cc.xy(5, vert));
            panelBuilder.add(new JLabel("<html><b>Gesamtkalkulation</b></html>"), cc.xy(7, vert));

            vert += 2;
            
            panelBuilder.add(namePanel, cc.xy(1, vert, CellConstraints.LEFT, CellConstraints.TOP));
            panelBuilder.add(activityPanel, cc.xy(3, vert, CellConstraints.LEFT, CellConstraints.TOP));
            panelBuilder.add(subjectPanel, cc.xy(5, vert, CellConstraints.LEFT, CellConstraints.TOP));
            panelBuilder.add(calculationPanel, cc.xy(7, vert, CellConstraints.LEFT, CellConstraints.TOP));
            
            vert += 2;
            
            panelBuilder.addSeparator("", cc.xyw(1, vert, 7));

            vert += 2;
        }
        mainPanel.add(CWComponentFactory.createScrollPane(view));
        mainPanel.addDisposableListener(this);
        return mainPanel;
    }

    public double getTotalActivityAmount(CourseAddition courseAddition){
        double amount = 0;
        for(int i = 0; i < courseAddition.getActivities().size(); i++){
            amount += courseAddition.getActivities().get(i).getPrice();
        }
        return amount;
    }

    public double getTotalAmount(CourseAddition courseAddition){
        return (courseAddition.getCourse().getPrice() + getTotalActivityAmount(courseAddition));
    }

    public void dispose() {
        mainPanel.removeDisposableListener(this);
        componentContainer.dispose();
        model.dispose();
    }
}
