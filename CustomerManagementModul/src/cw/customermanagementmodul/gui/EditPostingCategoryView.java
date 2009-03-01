package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.JButtonPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.interfaces.Disposable;
import cw.customermanagementmodul.pojo.PostingCategory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditPostingCategoryView
    implements Disposable
{

    private EditPostingCategoryPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;
    private JViewPanel panel;
    private JTextField tfName;
    
    private JButton bSave;
    private JButton bSaveCancel;
    private JButton bCancel;
    

    public EditPostingCategoryView(EditPostingCategoryPresentationModel model) {
        this.model = model;
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
    
    public JPanel buildPanel() {
        initComponents();
        
        panel = new JViewPanel(model.getHeaderInfo());
        
        JButtonPanel buttonPanel = panel.getButtonPanel();
        buttonPanel.add(bSave);
        buttonPanel.add(bSaveCancel);
//        buttonPanel.add(bReset);
        buttonPanel.add(bCancel);

        FormLayout layout = new FormLayout(
                "right:pref, 4dlu, pref:grow",
                "pref");
        
        PanelBuilder builder = new PanelBuilder(layout,panel.getContentPanel());
        builder.setDefaultDialogBorder();

        CellConstraints cc = new CellConstraints();
        builder.addLabel("Name:",        cc.xy(1, 1));
        builder.add(tfName,              cc.xy(3, 1));

        panel.addDisposableListener(this);

        initEventHandling();

        return panel;
    }

    public void dispose() {
        panel.removeDisposableListener(this);

        componentContainer.dispose();

        model.dispose();
    }
}
