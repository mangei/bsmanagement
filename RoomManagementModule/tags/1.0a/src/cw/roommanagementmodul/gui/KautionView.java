package cw.roommanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWList;
import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 *
 * @author Dominik
 */
public class KautionView extends CWView {

    private KautionPresentationModel model;
    private CWButton bNew;
    private CWButton bEdit;
    private CWButton bDelete;
    private CWButton bBack;
    private CWList lKautionen;
    private CWComponentFactory.CWComponentContainer componentContainer;

    public KautionView(KautionPresentationModel m) {
        this.model = m;

        initComponents();
        initEventHandling();
        buildView();

    }

    private void initComponents() {


        bNew = CWComponentFactory.createButton(model.getNewAction());
        bNew.setText("Neu");
        bEdit = CWComponentFactory.createButton(model.getEditAction());
        bEdit.setText("Bearbeiten");
        bDelete = CWComponentFactory.createButton(model.getDeleteAction());
        bDelete.setText("Löschen");
        bBack = CWComponentFactory.createButton(model.getBackAction());
        bBack.setText("Zurück");

        lKautionen = CWComponentFactory.createList(model.getKautionSelection());
        lKautionen.setSelectionModel(
                new SingleListSelectionAdapter(
                model.getKautionSelection().getSelectionIndexHolder()));
        componentContainer = CWComponentFactory.createComponentContainer().addComponent(bNew).addComponent(bEdit).addComponent(bDelete).addComponent(bBack).addComponent(lKautionen);
    }

    private void initEventHandling() {
        lKautionen.addMouseListener(model.getDoubleClickHandler());
    }

    private void buildView() {

        this.setHeaderInfo(model.getHeaderInfo());
        this.getButtonPanel().add(bNew);
        this.getButtonPanel().add(bEdit);
        this.getButtonPanel().add(bDelete);
        this.getButtonPanel().add(bBack);

        this.getContentPanel().add(lKautionen, BorderLayout.CENTER);
    }

    @Override
    public void dispose() {
        lKautionen.removeMouseListener(model.getDoubleClickHandler());
        componentContainer.dispose();
        model.dispose();
    }
}
