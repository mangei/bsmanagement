package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JDateChooser;
import cw.boardingschoolmanagement.gui.component.JButtonPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.interfaces.Disposable;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import cw.customermanagementmodul.pojo.Posting;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditCourseHabenPostingView implements Disposable{

    private EditCourseHabenPostingPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;

    private JTextField tfDescription;
    private JTextField tfValue;
    private JDateChooser dcPostingEntryDate;
    
    private JButton bSave;
    private JButton bCancel;

    private JXTable activityTable;
    private JXTable subjectTable;

    private JLabel courseLabel;
    private JLabel customerNameLabel;
    private JLabel customerStreetLabel;
    private JLabel customerPLZLabel;

    private JViewPanel panel;

    public EditCourseHabenPostingView(EditCourseHabenPostingPresentationModel model) {
        this.model = model;
    }

    private void initComponents() {
        tfDescription           = CWComponentFactory.createTextField(model.getPostingModel().getBufferedModel(Posting.PROPERTYNAME_DESCRIPTION),false);
        tfValue                 = CWComponentFactory.createCurrencyTextField(model.getPostingModel().getBufferedModel(Posting.PROPERTYNAME_AMOUNT));
        dcPostingEntryDate      = CWComponentFactory.createDateChooser(model.getPostingModel().getBufferedModel(Posting.PROPERTYNAME_POSTINGENTRYDATE));

        tfValue.setEditable(false);

        bSave       = CWComponentFactory.createButton(model.getSaveButtonAction());
        bCancel     = CWComponentFactory.createButton(model.getCancelButtonAction());


        // MANGEI: hallo
        activityTable = CWComponentFactory.createTable("");
        activityTable.setModel(model.createActivityTableModel(model.getActivitySelection()));//TODO-mit ValueModel
        activityTable.setSelectionModel(
                new SingleListSelectionAdapter(
                model.getActivitySelection().getSelectionIndexHolder()));

        activityTable.setSelectionModel(new SingleListSelectionAdapter(model.getActivitySelection().getSelectionIndexHolder()));

        subjectTable = CWComponentFactory.createTable("");
        subjectTable.setModel(model.createSubjectTableModel(model.getSubjectSelection()));
        subjectTable.setSelectionModel(
                new SingleListSelectionAdapter(
                model.getSubjectSelection().getSelectionIndexHolder()));

        subjectTable.setSelectionModel(new SingleListSelectionAdapter(model.getSubjectSelection().getSelectionIndexHolder()));

        courseLabel = CWComponentFactory.createLabel(model.getCourseAddition().getCourse().getName());
        courseLabel.setFont(new Font(null, Font.BOLD, 11));

        customerNameLabel = CWComponentFactory.createLabel(model.getCoursePart().getCustomer().getForename()
                + " " + model.getCoursePart().getCustomer().getSurname());
        customerNameLabel.setFont(new Font(null, Font.BOLD, 11));

        customerStreetLabel = CWComponentFactory.createLabel(model.getCoursePart().getCustomer().getStreet());
        customerStreetLabel.setFont(new Font(null, Font.BOLD, 11));

        customerPLZLabel = CWComponentFactory.createLabel(model.getCoursePart().getCustomer().getPostOfficeNumber()
                + " " + model.getCoursePart().getCustomer().getCity());
        customerPLZLabel.setFont(new Font(null, Font.BOLD, 11));

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(activityTable)
                .addComponent(bCancel)
                .addComponent(bSave)
                .addComponent(courseLabel)
                .addComponent(customerNameLabel)
                .addComponent(customerPLZLabel)
                .addComponent(customerStreetLabel)
                .addComponent(dcPostingEntryDate)
                .addComponent(subjectTable)
                .addComponent(tfDescription)
                .addComponent(tfValue);

        panel = new JViewPanel(model.getHeaderInfo());
    }

    private void initEventHandling() {
        // Nothing to do
    }
    
    public JPanel buildPanel() {
        initComponents();
        
        JButtonPanel buttonPanel = panel.getButtonPanel();
        
        buttonPanel.add(bSave);
        buttonPanel.add(bCancel);

        FormLayout layout = new FormLayout(
                "pref, 4dlu, pref, 4dlu, 300dlu",
                "pref, 4dlu, pref, 4dlu, pref, 20dlu," +
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 20dlu, pref, 4dlu, fill:pref:grow," +
                "20dlu, pref, 4dlu, fill:pref:grow");
        
        PanelBuilder builder = new PanelBuilder(layout, panel.getContentPanel());
        builder.setDefaultDialogBorder();

        CellConstraints cc = new CellConstraints();
        builder.addLabel("Bezeichnung:",        cc.xy(1, 1));
        builder.add(tfDescription,              cc.xyw(3, 1, 3));
        builder.addLabel("Betrag:",             cc.xy(1, 3));
        builder.add(tfValue,                    cc.xy(3, 3));
        builder.addLabel("€",                   cc.xy(5, 3));
        builder.addLabel("Eingangsdatum:",      cc.xy(1, 5));
        builder.add(dcPostingEntryDate,         cc.xy(3, 5));

        builder.addSeparator("Daten des Kurteilnehmers", cc.xyw(1, 7, 5));
        builder.addLabel("Kursname:",        cc.xy(1, 9));
        builder.add(courseLabel, cc.xy(3, 9));
        builder.addLabel("Kundendaten:",        cc.xy(1, 11));
        builder.add(customerNameLabel, cc.xy(3, 11));
        builder.add(customerStreetLabel, cc.xy(3, 13));
        builder.add(customerPLZLabel, cc.xy(3, 15));

        builder.addSeparator("Aktivitäten zu "
                + model.getCourseAddition().getCourse().getName(), cc.xyw(1, 17, 5));
        builder.add(new JScrollPane(activityTable), cc.xyw(1, 19, 5));

         builder.addSeparator("Gegenstände zu "
                + model.getCourseAddition().getCourse().getName(), cc.xyw(1, 21, 5));
        builder.add(new JScrollPane(subjectTable), cc.xyw(1, 23, 5));

        initEventHandling();
        panel.addDisposableListener(this);
        return panel;
    }

    public void dispose() {
        panel.removeDisposableListener(this);
        componentContainer.dispose();
        model.dispose();
    }
}
