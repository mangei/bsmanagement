package cw.roommanagementmodul.gui;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWView;
import java.awt.BorderLayout;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWList;

/**
 *
 * @author Dominik
 */
public class GebuehrenKategorieView extends CWView {

    private GebuehrenKategoriePresentationModel model;
    private CWButton bNew;
    private CWButton bEdit;
    private CWButton bDelete;
    private CWButton bBack;
    private CWList lGebuehrenKat;
    private CWComponentFactory.CWComponentContainer componentContainer;

    public GebuehrenKategorieView(GebuehrenKategoriePresentationModel m) {
        this.model = m;
        initComponents();
        buildView();
        initEventHandling();
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

        lGebuehrenKat = CWComponentFactory.createList(model.getGebuehrenKatSelection());

        componentContainer = CWComponentFactory.createComponentContainer().addComponent(bNew).addComponent(bEdit).addComponent(bDelete).addComponent(bBack).addComponent(lGebuehrenKat);

    }

    private void initEventHandling() {
        lGebuehrenKat.addMouseListener(model.getDoubleClickHandler());
    }

    private void buildView() {

        this.setHeaderInfo(model.getHeaderInfo());
        this.getButtonPanel().add(bNew);
        this.getButtonPanel().add(bEdit);
        this.getButtonPanel().add(bDelete);
        this.getButtonPanel().add(bBack);

        this.getContentPanel().add(lGebuehrenKat, BorderLayout.CENTER);
    }

    @Override
    public void dispose() {
        lGebuehrenKat.removeMouseListener(model.getDoubleClickHandler());
        componentContainer.dispose();
        model.dispose();
    }
}
