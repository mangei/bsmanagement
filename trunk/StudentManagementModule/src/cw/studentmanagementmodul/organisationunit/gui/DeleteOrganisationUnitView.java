package cw.studentmanagementmodul.organisationunit.gui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWButtonGroup;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWRadioButton;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 *
 * @author ManuelG
 */
public class DeleteOrganisationUnitView
	extends CWView<DeleteOrganisationUnitPresentationModel>
{

    private CWButton bOk;
    private CWButton bCancel;
    private CWRadioButton rbDeleteAll;
    private CWRadioButton rbMoveAll;
    private CWButtonGroup bg;
    private JComboBox cbOrganisationUnits;

    private ItemListener itemListener;

    public DeleteOrganisationUnitView(DeleteOrganisationUnitPresentationModel model) {
        super(model);
    }

    public void initComponents() {
    	super.initComponents();
    	
        bOk                     = CWComponentFactory.createButton(getModel().getOkAction());
        bCancel                 = CWComponentFactory.createButton(getModel().getCancelAction());

        rbDeleteAll             = CWComponentFactory.createRadioButton(getModel().getDeleteAllAction());
        rbMoveAll               = CWComponentFactory.createRadioButton(getModel().getMoveAllAction());
        cbOrganisationUnits     = CWComponentFactory.createComboBox(getModel().getOrganisationUnitSelection());

        bg = CWComponentFactory.createButtonGroup(rbDeleteAll, rbMoveAll);

        getComponentContainer()
                .addComponent(bOk)
                .addComponent(bCancel)
                .addComponent(rbDeleteAll)
                .addComponent(rbMoveAll)
                .addComponent(cbOrganisationUnits);
        
        initEventHandling();
    }

    private void initEventHandling() {
        rbDeleteAll.setSelected(true);
        rbMoveAll.addItemListener(itemListener = new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                cbOrganisationUnits.setEnabled(rbMoveAll.isSelected());
            }
        });
        cbOrganisationUnits.setEnabled(false);
    }

    public void buildView() {
    	super.buildView();

        this.setHeaderInfo(new CWHeaderInfo("Bereich loeschen"));

        CWButtonPanel buttonPanel = this.getButtonPanel();
        buttonPanel.add(bOk);
        buttonPanel.add(bCancel);

        FormLayout layout = new FormLayout(
                "0dlu, pref, 4dlu, pref:grow",
                "pref, 4dlu, pref, 4dlu, pref"
        );
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();

        builder.add(rbDeleteAll, cc.xy(2, 1));
        builder.addLabel("Bereich mit allen Unterbereichen/Klassen loeschen.", cc.xy(4, 1));
        builder.add(rbMoveAll, cc.xy(2, 3));
        builder.addLabel("Unterbereiche/Klassen in anderen Bereich verschieben:", cc.xy(4, 3));
        builder.add(cbOrganisationUnits, cc.xy(4, 5));
        
        addToContentPanel(builder.getPanel());
    }

    @Override
    public void dispose() {
        rbMoveAll.removeItemListener(itemListener);
        super.dispose();
    }
}
