package cw.coursemanagementmodul.gui;

import java.awt.Font;

import javax.swing.JScrollPane;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.accountmanagementmodul.pojo.AccountPosting;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWCurrencyTextField;
import cw.boardingschoolmanagement.gui.component.CWDateChooser;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWTable;
import cw.boardingschoolmanagement.gui.component.CWTextField;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 *
 * @author André Salmhofer (CreativeWorkers)
 */
public class EditCourseHabenPostingView extends CWView
{

    private EditCourseHabenPostingPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;

    private CWTextField tfDescription;
    private CWCurrencyTextField tfValue;
    private CWDateChooser dcPostingEntryDate;
    
    private CWButton bSave;
    private CWButton bCancel;

    private CWTable activityTable;
    private CWTable subjectTable;

    private CWLabel courseLabel;
    private CWLabel customerNameLabel;
    private CWLabel customerStreetLabel;
    private CWLabel customerPLZLabel;

    public EditCourseHabenPostingView(EditCourseHabenPostingPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        tfDescription           = CWComponentFactory.createTextField(model.getPostingModel().getBufferedModel(AccountPosting.PROPERTYNAME_DESCRIPTION),false);
        tfValue                 = CWComponentFactory.createCurrencyTextField(model.getPostingModel().getBufferedModel(AccountPosting.PROPERTYNAME_AMOUNT));
        dcPostingEntryDate      = CWComponentFactory.createDateChooser(model.getPostingModel().getBufferedModel(AccountPosting.PROPERTYNAME_POSTINGENTRYDATE));

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

        componentContainer = CWComponentFactory.createComponentContainer()
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
    }

    private void initEventHandling() {
        // Nothing to do
    }
    
    private void buildView() {
        this.setHeaderInfo(model.getHeaderInfo());
        
        CWButtonPanel buttonPanel = this.getButtonPanel();
        
        buttonPanel.add(bSave);
        buttonPanel.add(bCancel);

        FormLayout layout = new FormLayout(
                "pref, 4dlu, pref, 4dlu, 300dlu",
                "pref, 4dlu, pref, 4dlu, pref, 20dlu," +
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 20dlu, pref, 4dlu, fill:pref:grow," +
                "20dlu, pref, 4dlu, fill:pref:grow");
        
        PanelBuilder builder = new PanelBuilder(layout, this.getContentPanel());
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

        builder.addSeparator("Aktivitaeten zu "
                + model.getCourseAddition().getCourse().getName(), cc.xyw(1, 17, 5));
        builder.add(new JScrollPane(activityTable), cc.xyw(1, 19, 5));

         builder.addSeparator("Gegenstaende zu "
                + model.getCourseAddition().getCourse().getName(), cc.xyw(1, 21, 5));
        builder.add(new JScrollPane(subjectTable), cc.xyw(1, 23, 5));
    }

    @Override
    public void dispose() {
        componentContainer.dispose();
        model.dispose();
    }
}
