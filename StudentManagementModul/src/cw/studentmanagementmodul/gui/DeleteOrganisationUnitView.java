package cw.studentmanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.JButtonPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.interfaces.Disposable;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author ManuelG
 */
public class DeleteOrganisationUnitView
    implements Disposable
{

    private DeleteOrganisationUnitPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private JViewPanel panel;
    private JButton bOk;
    private JButton bCancel;
    private JRadioButton rbDeleteAll;
    private JRadioButton rbMoveAll;
    private ButtonGroup bg;
    private JComboBox cbOrganisationUnits;

    private ItemListener itemListener;

    public DeleteOrganisationUnitView(DeleteOrganisationUnitPresentationModel model) {
        this.model = model;
    }

    private void initComponents() {
        bOk                     = CWComponentFactory.createButton(model.getOkAction());
        bCancel                 = CWComponentFactory.createButton(model.getCancelAction());

        rbDeleteAll             = CWComponentFactory.createRadioButton(model.getDeleteAllAction());
        rbMoveAll               = CWComponentFactory.createRadioButton(model.getMoveAllAction());
        cbOrganisationUnits     = CWComponentFactory.createComboBox(model.getOrganisationUnitSelection());

        bg = CWComponentFactory.createButtonGroup(rbDeleteAll, rbMoveAll);

        componentContainer = CWComponentFactory.createCWComponentContainer()
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

    public JPanel buildPanel() {
        initComponents();

        panel = new JViewPanel("Bereich löschen");

        JButtonPanel buttonPanel = panel.getButtonPanel();
        buttonPanel.add(bOk);
        buttonPanel.add(bCancel);

        FormLayout layout = new FormLayout(
                "0dlu, pref, 4dlu, pref:grow",
                "pref, 4dlu, pref, 4dlu, pref"
        );
        PanelBuilder builder = new PanelBuilder(layout, panel.getContentPanel());
        CellConstraints cc = new CellConstraints();

        builder.add(rbDeleteAll, cc.xy(2, 1));
        builder.addLabel("Bereich mit allen Unterbereichen/Klassen löschen.", cc.xy(4, 1));
        builder.add(rbMoveAll, cc.xy(2, 3));
        builder.addLabel("Unterbereiche/Klassen in anderen Bereich verschieben:", cc.xy(4, 3));
        builder.add(cbOrganisationUnits, cc.xy(4, 5));

        panel.addDisposableListener(this);

        initEventHandling();

        return panel;
    }

    public void dispose() {
        panel.removeDisposableListener(this);

        rbMoveAll.removeItemListener(itemListener);

        componentContainer.dispose();

        model.dispose();
    }
}
