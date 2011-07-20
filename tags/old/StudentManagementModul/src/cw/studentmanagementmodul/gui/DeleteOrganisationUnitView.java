package cw.studentmanagementmodul.gui;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWButtonGroup;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.CWRadioButton;
import cw.boardingschoolmanagement.gui.component.CWView;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;

/**
 *
 * @author ManuelG
 */
public class DeleteOrganisationUnitView extends CWView
{

    private DeleteOrganisationUnitPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWButton bOk;
    private CWButton bCancel;
    private CWRadioButton rbDeleteAll;
    private CWRadioButton rbMoveAll;
    private CWButtonGroup bg;
    private JComboBox cbOrganisationUnits;

    private ItemListener itemListener;

    public DeleteOrganisationUnitView(DeleteOrganisationUnitPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        bOk                     = CWComponentFactory.createButton(model.getOkAction());
        bCancel                 = CWComponentFactory.createButton(model.getCancelAction());

        rbDeleteAll             = CWComponentFactory.createRadioButton(model.getDeleteAllAction());
        rbMoveAll               = CWComponentFactory.createRadioButton(model.getMoveAllAction());
        cbOrganisationUnits     = CWComponentFactory.createComboBox(model.getOrganisationUnitSelection());

        bg = CWComponentFactory.createButtonGroup(rbDeleteAll, rbMoveAll);

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(bOk)
                .addComponent(bCancel)
                .addComponent(rbDeleteAll)
                .addComponent(rbMoveAll)
                .addComponent(cbOrganisationUnits);
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

    private void buildView() {

        this.setHeaderInfo(new CWHeaderInfo("Bereich löschen"));

        CWButtonPanel buttonPanel = this.getButtonPanel();
        buttonPanel.add(bOk);
        buttonPanel.add(bCancel);

        FormLayout layout = new FormLayout(
                "0dlu, pref, 4dlu, pref:grow",
                "pref, 4dlu, pref, 4dlu, pref"
        );
        PanelBuilder builder = new PanelBuilder(layout, this.getContentPanel());
        CellConstraints cc = new CellConstraints();

        builder.add(rbDeleteAll, cc.xy(2, 1));
        builder.addLabel("Bereich mit allen Unterbereichen/Klassen löschen.", cc.xy(4, 1));
        builder.add(rbMoveAll, cc.xy(2, 3));
        builder.addLabel("Unterbereiche/Klassen in anderen Bereich verschieben:", cc.xy(4, 3));
        builder.add(cbOrganisationUnits, cc.xy(4, 5));
    }

    @Override
    public void dispose() {
        rbMoveAll.removeItemListener(itemListener);

        componentContainer.dispose();

        model.dispose();
    }
}
