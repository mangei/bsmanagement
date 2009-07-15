package cw.boardingschoolmanagement.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWCheckBox;
import cw.boardingschoolmanagement.gui.component.CWComboBox;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWView;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 *
 * @author ManuelG
 */
public class GeneralConfigurationView extends CWView
{

    private GeneralConfigurationPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWCheckBox cPathActive;
    private CWComboBox cbPathPosition;
    private ItemListener cbPathActiveListener;

    public GeneralConfigurationView(GeneralConfigurationPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    public void initComponents() {
        cPathActive = CWComponentFactory.createCheckBox(model.getPathPanelActiveModel(), "Anzeigepfad aktivieren");
        cbPathPosition = CWComponentFactory.createComboBox(model.getPathPanelPositionSelection());

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(cPathActive)
                .addComponent(cbPathPosition);
    }

    public void initEventHandling() {
        cPathActive.addItemListener(cbPathActiveListener = new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    cbPathPosition.setEnabled(true);
                } else {
                    cbPathPosition.setEnabled(false);
                }
            }
        });
        cbPathPosition.setEnabled(cPathActive.isSelected());
    }

    private void buildView() {
        this.setHeaderInfo(model.getHeaderInfo());

        FormLayout layout = new FormLayout(
                "20dlu, pref, pref, 1dlu:grow",
                "pref, 2dlu, pref, 2dlu, pref"
        );
        CellConstraints cc = new CellConstraints();
        PanelBuilder builder = new PanelBuilder(layout, this.getContentPanel());
        builder.addSeparator("Benutzeroberfläche", cc.xyw(1, 1, 4));
        builder.add(cPathActive, cc.xyw(1, 3, 4));
        builder.addLabel("Position: ", cc.xy(2, 5));
        builder.add(cbPathPosition, cc.xy(3, 5));
    }

    @Override
    public void dispose() {
        componentContainer.dispose();

        cPathActive.removeItemListener(cbPathActiveListener);

        model.dispose();
    }
}
