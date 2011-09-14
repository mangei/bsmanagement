package cw.boardingschoolmanagement.gui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWCheckBox;
import cw.boardingschoolmanagement.gui.component.CWComboBox;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 *
 * @author Manuel Geier
 */
public class GeneralConfigurationView
	extends CWView<GeneralConfigurationPresentationModel>
{

    private CWCheckBox cPathActive;
    private CWComboBox cbPathPosition;
    private ItemListener cbPathActiveListener;

    public GeneralConfigurationView(GeneralConfigurationPresentationModel model) {
        super(model);
    }
    
	/**
	 * Initalisiert die grafischen Elemente der Konfigurationsmaske
	 *
	 */
    public void initComponents() {
    	super.initComponents();
    	
        cPathActive = CWComponentFactory.createCheckBox(getModel().getPathPanelActiveModel(), "Anzeigepfad aktivieren");
        cbPathPosition = CWComponentFactory.createComboBox(getModel().getPathPanelPositionSelection());

        getComponentContainer()
                .addComponent(cPathActive)
                .addComponent(cbPathPosition);
        
        cPathActive.addItemListener(cbPathActiveListener = new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
//                if(e.getStateChange() == ItemEvent.SELECTED) {
//                    cbPathPosition.setEnabled(true);
//                } else {
//                    cbPathPosition.setEnabled(false);
//                }
                cbPathPosition.setEnabled(cPathActive.isSelected());
            }
        });
        cbPathPosition.setEnabled(cPathActive.isSelected());
    }

    public void buildView() {
    	super.buildView();
    	
        this.setHeaderInfo(getModel().getHeaderInfo());

        FormLayout layout = new FormLayout(
                "20dlu, pref, pref, 1dlu:grow",
                "pref, 2dlu, pref, 2dlu, pref"
        );
        CellConstraints cc = new CellConstraints();
        PanelBuilder builder = new PanelBuilder(layout);
        builder.addSeparator("Benutzeroberflaeche", cc.xyw(1, 1, 4));
        builder.add(cPathActive, cc.xyw(1, 3, 4));
        builder.addLabel("Position: ", cc.xy(2, 5));
        builder.add(cbPathPosition, cc.xy(3, 5));
        
        addToContentPanel(builder.getPanel());
    }

    @Override
    public void dispose() {
    	cPathActive.removeItemListener(cbPathActiveListener);
        super.dispose();
    }
}
