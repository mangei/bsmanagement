package cw.roommanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWList;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 *
 * @author Dominik
 */
public class KautionView
	extends CWView<KautionPresentationModel>
{

    private CWButton bNew;
    private CWButton bEdit;
    private CWButton bDelete;
    private CWButton bBack;
    private CWList lKautionen;

    public KautionView(KautionPresentationModel model) {
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

        lKautionen = CWComponentFactory.createList(getModel().getKautionSelection());
        lKautionen.setSelectionModel(
                new SingleListSelectionAdapter(
                		getModel().getKautionSelection().getSelectionIndexHolder()));
        
        getComponentContainer()
        	.addComponent(bNew)
        	.addComponent(bEdit)
        	.addComponent(bDelete)
        	.addComponent(bBack)
        	.addComponent(lKautionen);
        
        initEventHandling();
    }

    private void initEventHandling() {
        lKautionen.addMouseListener(getModel().getDoubleClickHandler());
    }

    public void buildView() {
    	super.buildView();

        this.setHeaderInfo(getModel().getHeaderInfo());
        this.getButtonPanel().add(bNew);
        this.getButtonPanel().add(bEdit);
        this.getButtonPanel().add(bDelete);
        this.getButtonPanel().add(bBack);

        addToContentPanel(lKautionen);
    }

    @Override
    public void dispose() {
        lKautionen.removeMouseListener(getModel().getDoubleClickHandler());
        
        super.dispose();
    }
}
