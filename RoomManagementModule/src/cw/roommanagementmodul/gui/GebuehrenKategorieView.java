package cw.roommanagementmodul.gui;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWList;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 *
 * @author Dominik
 */
public class GebuehrenKategorieView
	extends CWView<GebuehrenKategoriePresentationModel>
{

    private CWButton bNew;
    private CWButton bEdit;
    private CWButton bDelete;
    private CWButton bBack;
    private CWList lGebuehrenKat;

    public GebuehrenKategorieView(GebuehrenKategoriePresentationModel model) {
        super(model);
    }

    public void initComponents() {
    	super.initComponents();
    	
        bNew = CWComponentFactory.createButton(getModel().getNewAction());
        bNew.setText("Neu");
        bEdit = CWComponentFactory.createButton(getModel().getEditAction());
        bEdit.setText("Bearbeiten");
        bDelete = CWComponentFactory.createButton(getModel().getDeleteAction());
        bDelete.setText("Loeschen");
        bBack = CWComponentFactory.createButton(getModel().getBackAction());
        bBack.setText("Zurueck");

        lGebuehrenKat = CWComponentFactory.createList(getModel().getGebuehrenKatSelection());

        getComponentContainer()
        	.addComponent(bNew)
        	.addComponent(bEdit)
        	.addComponent(bDelete)
        	.addComponent(bBack)
        	.addComponent(lGebuehrenKat);
        
        initEventHandling();
    }

    private void initEventHandling() {
        lGebuehrenKat.addMouseListener(getModel().getDoubleClickHandler());
    }

    public void buildView() {
    	super.buildView();

        this.setHeaderInfo(getModel().getHeaderInfo());
        this.getButtonPanel().add(bNew);
        this.getButtonPanel().add(bEdit);
        this.getButtonPanel().add(bDelete);
        this.getButtonPanel().add(bBack);

        addToContentPanel(lGebuehrenKat);
    }

    @Override
    public void dispose() {
        lGebuehrenKat.removeMouseListener(getModel().getDoubleClickHandler());
        
        super.dispose();
    }
}
