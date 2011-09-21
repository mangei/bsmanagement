package cw.customermanagementmodul.group.gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWList;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.customermanagementmodul.customer.gui.EditCustomerView;

/**
 *
 * @author Manuel Geier
 */
public class EditGroupEditCustomerView
	extends CWView<EditGroupEditCustomerPresentationModel>
{

	private EditCustomerView baseView;
    private CWList liCustomerGroups;
    private CWList liGroups;
    private CWButton bAdd;
    private CWButton bRemove;

    public EditGroupEditCustomerView(EditGroupEditCustomerPresentationModel model, EditCustomerView baseView) {
        super(model);
        this.baseView = baseView;
    }

    public void initComponents() {
    	super.initComponents();
    	
        liCustomerGroups    = CWComponentFactory.createList(getModel().getSelectionCustomerGroups(), "Keine Gruppen vorhanden");
        liGroups            = CWComponentFactory.createList(getModel().getSelectionGroups(), "Keine Gruppen vorhanden");
        bAdd                = CWComponentFactory.createButton(getModel().getAddGroupAction());
        bRemove             = CWComponentFactory.createButton(getModel().getRemoveGroupAction());

        getComponentContainer()
                .addComponent(bAdd)
                .addComponent(bRemove)
                .addComponent(liCustomerGroups)
                .addComponent(liGroups);
    }

    public void buildView() {
    	super.buildView();
    	
        this.setHeaderInfo(getModel().getHeaderInfo());
        this.setName("Gruppen");

        FormLayout layout = new FormLayout(
                "fill:pref:grow, 4dlu, pref, 4dlu, fill:pref:grow",
                "pref:grow, pref, 4dlu, pref, fill:pref:grow"
        );

        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();

        CWPanel panelRight = CWComponentFactory.createPanel();
        CWPanel panelLeft = CWComponentFactory.createPanel();

        PanelBuilder panelBuilderRight = new PanelBuilder(new FormLayout("pref:grow","pref, fill:pref:grow"), panelRight);
        PanelBuilder panelBuilderLeft = new PanelBuilder(new FormLayout("pref:grow","pref, fill:pref:grow"), panelLeft);
        
        panelBuilderRight.add(new JLabel("<html><b>Aktive Gruppen:</b></html>"), cc.xy(1, 1));
        panelBuilderRight.add(liCustomerGroups,  cc.xy(1, 2));
        panelBuilderLeft.add(new JLabel("<html><b>Andere Gruppen:</b></html>"),  cc.xy(1, 1));
        panelBuilderLeft.add(liGroups,  cc.xy(1, 2));
        
        builder.add(panelRight, cc.xywh(1, 1, 1, 5));
        builder.add(bAdd,               cc.xy(3, 2));
        builder.add(bRemove,            cc.xy(3, 4));
        builder.add(panelLeft, cc.xywh(5, 1, 1, 5));
    
        this.addToContentPanel(builder.getPanel(), true);
        
        baseView.addToContentPanel(this, true);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

}
