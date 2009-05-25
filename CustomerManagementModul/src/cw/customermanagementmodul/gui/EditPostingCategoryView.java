package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.customermanagementmodul.pojo.PostingCategory;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditPostingCategoryView extends CWView
{

    private EditPostingCategoryPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;
    private JTextField tfName;
    
    private JButton bSave;
    private JButton bSaveCancel;
    private JButton bCancel;
    

    public EditPostingCategoryView(EditPostingCategoryPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        tfName           = CWComponentFactory.createTextField(model.getBufferedModel(PostingCategory.PROPERTYNAME_NAME),false);

        bSave       = CWComponentFactory.createButton(model.getSaveButtonAction());
        bSaveCancel = CWComponentFactory.createButton(model.getSaveCancelButtonAction());
        bCancel     = CWComponentFactory.createButton(model.getCancelButtonAction());

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(tfName)
                .addComponent(bSave)
                .addComponent(bSaveCancel)
                .addComponent(bCancel);
    }

    private void initEventHandling() {
        // Nothing to do
    }
    
    private void buildView() {
        this.setHeaderInfo(model.getHeaderInfo());
        
        CWButtonPanel buttonPanel = this.getButtonPanel();
        buttonPanel.add(bSave);
        buttonPanel.add(bSaveCancel);
//        buttonPanel.add(bReset);
        buttonPanel.add(bCancel);

        FormLayout layout = new FormLayout(
                "right:pref, 4dlu, pref:grow",
                "pref");
        
        PanelBuilder builder = new PanelBuilder(layout, this.getContentPanel());
        builder.setDefaultDialogBorder();

        CellConstraints cc = new CellConstraints();
        builder.addLabel("Name:",        cc.xy(1, 1));
        builder.add(tfName,              cc.xy(3, 1));
    }

    public void dispose() {
        componentContainer.dispose();

        model.dispose();
    }
}
