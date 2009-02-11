package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JDateChooser;
import cw.boardingschoolmanagement.gui.component.JButtonPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import cw.customermanagementmodul.pojo.Posting;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditCourseHabenPostingView {

    private EditCourseHabenPostingPresentationModel model;
    
    private JTextField tfDescription;
    private JComboBox cbPostingCategory;
    private JTextField tfValue;
    private JDateChooser dcPostingEntryDate;
    
    private JButton bSave;
    private JButton bReset;
    private JButton bCancel;
    private JButton bSaveCancel;

    private JXTable activityTable;
    private JXTable subjectTable;

    private JLabel courseLabel;
    private JLabel customerNameLabel;
    private JLabel customerStreetLabel;
    private JLabel customerPLZLabel;

    public EditCourseHabenPostingView(EditCourseHabenPostingPresentationModel model) {
        this.model = model;
    }

    private void initComponents() {
        tfDescription           = CWComponentFactory.createTextField(model.getBufferedModel(Posting.PROPERTYNAME_DESCRIPTION),false);
        cbPostingCategory       = CWComponentFactory.createComboBox(model.getPostingCategorySelection());
        tfValue                 = CWComponentFactory.createCurrencyTextField(model.getBufferedModel(Posting.PROPERTYNAME_AMOUNT));
        dcPostingEntryDate      = CWComponentFactory.createDateChooser(model.getBufferedModel(Posting.PROPERTYNAME_POSTINGENTRYDATE));

        bSave       = CWComponentFactory.createButton(model.getSaveButtonAction());
        bReset      = CWComponentFactory.createButton(model.getResetButtonAction());
        bCancel     = CWComponentFactory.createButton(model.getCancelButtonAction());
        bSaveCancel = CWComponentFactory.createButton(model.getSaveCancelButtonAction());

        activityTable = CWComponentFactory.createTable("");
        activityTable.setColumnControlVisible(true);
        activityTable.setAutoCreateRowSorter(true);
        activityTable.setPreferredScrollableViewportSize(new Dimension(10,10));
        activityTable.setHighlighters(HighlighterFactory.createSimpleStriping());

        activityTable.setModel(model.createActivityTableModel(model.getActivitySelection()));//TODO-mit ValueModel
        activityTable.setSelectionModel(
                new SingleListSelectionAdapter(
                model.getActivitySelection().getSelectionIndexHolder()));

        activityTable.setSelectionModel(new SingleListSelectionAdapter(model.getActivitySelection().getSelectionIndexHolder()));

        subjectTable = CWComponentFactory.createTable("");
        subjectTable.setColumnControlVisible(true);
        subjectTable.setAutoCreateRowSorter(true);
        subjectTable.setPreferredScrollableViewportSize(new Dimension(10,10));
        subjectTable.setHighlighters(HighlighterFactory.createSimpleStriping());

        subjectTable.setModel(model.createSubjectTableModel(model.getSubjectSelection()));
        subjectTable.setSelectionModel(
                new SingleListSelectionAdapter(
                model.getSubjectSelection().getSelectionIndexHolder()));

        subjectTable.setSelectionModel(new SingleListSelectionAdapter(model.getSubjectSelection().getSelectionIndexHolder()));

        courseLabel = CWComponentFactory.createLabel(model.getCourseAddition().getCourse().getName());
        courseLabel.setFont(new Font(null, Font.BOLD, 11));

        customerNameLabel = CWComponentFactory.createLabel(model.getPosting().getCustomer().getForename()
                + " " + model.getPosting().getCustomer().getSurname());
        customerNameLabel.setFont(new Font(null, Font.BOLD, 11));

        customerStreetLabel = CWComponentFactory.createLabel(model.getPosting().getCustomer().getStreet());
        customerStreetLabel.setFont(new Font(null, Font.BOLD, 11));

        customerPLZLabel = CWComponentFactory.createLabel(model.getPosting().getCustomer().getPostOfficeNumber()
                + " " + model.getPosting().getCustomer().getCity());
        customerPLZLabel.setFont(new Font(null, Font.BOLD, 11));
    }

    private void initEventHandling() {
        // Nothing to do
    }
    
    public JPanel buildPanel() {
        initComponents();
        
        JViewPanel panel = new JViewPanel(model.getHeaderInfo());
        JButtonPanel buttonPanel = panel.getButtonPanel();
        
        buttonPanel.add(bSave);
        buttonPanel.add(bSaveCancel);
        buttonPanel.add(bReset);
        buttonPanel.add(bCancel);

        FormLayout layout = new FormLayout(
                "pref, 4dlu, pref, 4dlu, 300dlu",
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 20dlu," +
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 20dlu, pref, 4dlu, fill:pref:grow," +
                "20dlu, pref, 4dlu, fill:pref:grow");
        
        PanelBuilder builder = new PanelBuilder(layout, panel.getContentPanel());
        builder.setDefaultDialogBorder();

        CellConstraints cc = new CellConstraints();
        builder.addLabel("Bezeichnung:",        cc.xy(1, 1));
        builder.add(tfDescription,              cc.xyw(3, 1, 3));
        builder.addLabel("Kategorie:",          cc.xy(1, 3));
        builder.add(cbPostingCategory,          cc.xyw(3, 3, 3));
        builder.addLabel("Betrag:",             cc.xy(1, 5));
        builder.add(tfValue,                    cc.xy(3, 5));
        builder.addLabel("€",                   cc.xy(5, 5));
        builder.addLabel("Eingangsdatum:",      cc.xy(1, 7));
        builder.add(dcPostingEntryDate,         cc.xy(3, 7));

        builder.addSeparator("Daten des Kurteilnehmers", cc.xyw(1, 9, 5));
        builder.addLabel("Kursname:",        cc.xy(1, 11));
        builder.add(courseLabel, cc.xy(3, 11));
        builder.addLabel("Kundendaten:",        cc.xy(1, 13));
        builder.add(customerNameLabel, cc.xy(3, 13));
        builder.add(customerStreetLabel, cc.xy(3, 15));
        builder.add(customerPLZLabel, cc.xy(3, 17));

        builder.addSeparator("Aktivitäten zu "
                + model.getCourseAddition().getCourse().getName(), cc.xyw(1, 19, 5));
        builder.add(new JScrollPane(activityTable), cc.xyw(1, 21, 5));

         builder.addSeparator("Gegenstände zu "
                + model.getCourseAddition().getCourse().getName(), cc.xyw(1, 23, 5));
        builder.add(new JScrollPane(subjectTable), cc.xyw(1, 25, 5));

        initEventHandling();

        return panel;
    }
}
