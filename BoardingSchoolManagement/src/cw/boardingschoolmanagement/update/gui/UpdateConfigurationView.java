package cw.boardingschoolmanagement.update.gui;

import java.awt.FlowLayout;

import javax.swing.JLabel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.gui.component.CWTable;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.renderer.UpdateStatusDataFieldRenderer;

/**
 *
 * @author Manuel Geier
 */
public class UpdateConfigurationView
	extends CWView<UpdateConfigurationPresentationModel>
{

    private CWButton bCheck;
    private CWButton bUpdate;
    private CWTable tUpdates;

    public UpdateConfigurationView(UpdateConfigurationPresentationModel model) {
        super(model);
    }

    public void initComponents() {
    	super.initComponents();
    	
        bCheck = CWComponentFactory.createButton(getModel().getCheckAction());
        bUpdate = CWComponentFactory.createButton(getModel().getUpdateAction());
        tUpdates = CWComponentFactory.createTable(getModel().getUpdatesTableModel(), "System pruefen!");
        tUpdates.getColumns(true).get(2).setCellRenderer(new UpdateStatusDataFieldRenderer());

        getComponentContainer()
                .addComponent(bCheck)
                .addComponent(bUpdate)
                .addComponent(tUpdates);
        
//        cPathActive.addItemListener(cbPathActiveListener = new ItemListener() {
//            public void itemStateChanged(ItemEvent e) {
////                if(e.getStateChange() == ItemEvent.SELECTED) {
////                    cbPathPosition.setEnabled(true);
////                } else {
////                    cbPathPosition.setEnabled(false);
////                }
//                cbPathPosition.setEnabled(cPathActive.isSelected());
//            }
//        });
//        cbPathPosition.setEnabled(cPathActive.isSelected());    
    }
    
    public void buildView() {
    	super.buildView();
    	
        this.setHeaderInfo(getModel().getHeaderInfo());

        CWPanel pCheck = CWComponentFactory.createPanel();
        pCheck.add(new JLabel("Ist Ihr System auf dem aktuellen Stand? "));
        pCheck.add(bCheck);

        CWPanel pUpdateText = CWComponentFactory.createPanel(new FlowLayout(FlowLayout.CENTER));
        pUpdateText.add(new JLabel("<html><b>Eine Aktualisierung ist notwendig!</b></html>"));
        CWPanel pUpdateButton = CWComponentFactory.createPanel(new FlowLayout(FlowLayout.CENTER));
        pUpdateButton.add(bUpdate);

        FormLayout layout = new FormLayout(
                "pref:grow",
                "pref, 4dlu, pref, pref, 4dlu, fill:pref:grow"
        );
        CellConstraints cc = new CellConstraints();
        PanelBuilder builder = new PanelBuilder(layout);
        builder.add(pCheck, cc.xy(1, 1));
        builder.add(pUpdateText, cc.xy(1, 3));
        builder.add(pUpdateButton, cc.xy(1, 4));
        builder.add(CWComponentFactory.createScrollPane(tUpdates), cc.xy(1, 6));
//        builder.addLabel("Position: ", cc.xy(2, 5));
//        builder.add(cbPathPosition, cc.xy(3, 5));
        
        addToContentPanel(builder.getPanel());
    }

    @Override
    public void dispose() {
//        cPathActive.removeItemListener(cbPathActiveListener);
    	super.dispose();
    }
}
