package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JDateChooser;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import java.beans.PropertyChangeEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import cw.customermanagementmodul.pojo.Posting;
import java.beans.PropertyChangeListener;
import javax.swing.JComboBox;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditPostingView extends CWView
{

    private EditPostingPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private JTextField tfDescription;
    private JComboBox cbPostingCategory;
    private JTextField tfAmount;
    private JDateChooser dcPostingEntryDate;
    private JPanel pLiabilitiesAssets;
    
    private JButton bCancel;
    private JButton bSaveCancel;
    private JPanel pPostingCategoryExtention;

    private PropertyChangeListener postingCategoryChangeListener;

    public EditPostingView(EditPostingPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        tfDescription           = CWComponentFactory.createTextField(model.getBufferedModel(Posting.PROPERTYNAME_DESCRIPTION),false);
        cbPostingCategory       = CWComponentFactory.createComboBox(model.getPostingCategorySelection());
        tfAmount                = CWComponentFactory.createCurrencyTextField(model.getBufferedModel(Posting.PROPERTYNAME_AMOUNT));
        dcPostingEntryDate      = CWComponentFactory.createDateChooser(model.getBufferedModel(Posting.PROPERTYNAME_POSTINGENTRYDATE));

        pLiabilitiesAssets = CWComponentFactory.createTrueFalsePanel(
                model.getBufferedModel(Posting.PROPERTYNAME_LIABILITIESASSETS),
                "Soll",
                "Haben",
                (Boolean)(model.getModel(Posting.PROPERTYNAME_LIABILITIESASSETS).getValue())
        );

        bCancel     = CWComponentFactory.createButton(model.getCancelAction());
        bSaveCancel = CWComponentFactory.createButton(model.getSaveCancelAction());

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(tfDescription)
                .addComponent(cbPostingCategory)
                .addComponent(tfAmount)
                .addComponent(dcPostingEntryDate)
                .addComponent(pLiabilitiesAssets)
                .addComponent(bCancel)
                .addComponent(bSaveCancel);

        pPostingCategoryExtention = CWComponentFactory.createPanel();
        FormLayout pPostingCategoryExtentionLayout = new FormLayout(
                "fill:pref:grow:",
                "4dlu, fill:pref"
        );
        pPostingCategoryExtention.setLayout(pPostingCategoryExtentionLayout);
    }

    private void initEventHandling() {
        model.getPostingCategorySelection().addValueChangeListener(postingCategoryChangeListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                // Change the extention component
                changePostingCategoryExtentionComponent();
            }
        });
    }

    private void changePostingCategoryExtentionComponent() {
        pPostingCategoryExtention.removeAll();
        JComponent comp = model.getPostingCategoryExtentionComponent();
        if(comp != null) {
            CellConstraints cc = new CellConstraints();
            pPostingCategoryExtention.add(comp, cc.xy(1, 2));
        }
        this.validate();
    }
    
    private void buildView() {
        this.setHeaderInfo(model.getHeaderInfo());
        CWButtonPanel buttonPanel = this.getButtonPanel();

        buttonPanel.add(bSaveCancel);
        buttonPanel.add(bCancel);

        FormLayout layout = new FormLayout(
                "right:pref, 4dlu, fill:pref, 4dlu, left:200dlu, 4dlu, pref",
                "pref, 4dlu, pref, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");
        
        PanelBuilder builder = new PanelBuilder(layout, this.getContentPanel());
        builder.setDefaultDialogBorder();

        CellConstraints cc = new CellConstraints();
        builder.addLabel("Bezeichnung:",        cc.xy(1, 1));
        builder.add(tfDescription,              cc.xyw(3, 1, 3));
        builder.addLabel("Kategorie:",          cc.xy(1, 3));
        builder.add(cbPostingCategory,          cc.xyw(3, 3, 3));
        builder.add(pPostingCategoryExtention,  cc.xyw(3, 4, 3));
        builder.addLabel("Betrag:",             cc.xy(1, 6));
        builder.add(tfAmount,                   cc.xy(3, 6));
        builder.addLabel("€",                   cc.xy(5, 6));
        builder.addLabel("Art:",                cc.xy(1, 8));
        builder.add(pLiabilitiesAssets,         cc.xyw(3, 8, 3));
        builder.addLabel("Eingangsdatum:",      cc.xy(1, 10));
        builder.add(dcPostingEntryDate,         cc.xy(3, 10));
    }

    @Override
    public void dispose() {
        System.out.println("7777777777777777777777777777777777777777777777777777");

        model.getPostingCategorySelection().removeValueChangeListener(postingCategoryChangeListener);

        componentContainer.dispose();

        model.dispose();
    }
}
