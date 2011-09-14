package cw.customermanagementmodul.customer.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import com.jidesoft.swing.JideTabbedPane;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.customermanagementmodul.customer.extention.point.EditCustomerTabExtentionPoint;
import cw.customermanagementmodul.customer.persistence.Customer;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditCustomerView
	extends CWView<EditCustomerPresentationModel>
{

//    private JideTabbedPane tabs;
    private CWButton bSave;
    private CWButton bCancel;

    private PropertyChangeListener tabsEnableListener;

    public EditCustomerView(EditCustomerPresentationModel model) {
        super(model, false);
    }

    public void initComponents() {
    	super.initComponents();
    	
        bSave               = CWComponentFactory.createButton(getModel().getSaveAction());
        bCancel             = CWComponentFactory.createButton(getModel().getCancelAction());

        getComponentContainer()
                .addComponent(bSave)
                .addComponent(bCancel);

//        tabs = new JideTabbedPane();
        
        initEventhandling();
    }

    private void initEventhandling() {
//        // If it is a new Customer and there is no id, disable the tabs for the extentions
//        // because the id is null
//        // If the customer is saved, then enable the tabs
//        if(getModel().getBean().getId() == null) {
//        	getModel().getBufferedModel(Customer.PROPERTYNAME_ID).addPropertyChangeListener(tabsEnableListener = new PropertyChangeListener() {
//                public void propertyChange(PropertyChangeEvent evt) {
//                    setTabsEnabled(true);
//                }
//            });
//            setTabsEnabled(false);
//        }

        getModel().getUnsaved().setValue(false);
    }
    
    public void buildView() {
    	super.buildView();
    	
        this.setHeaderInfo(getModel().getHeaderInfo());
   
        CWButtonPanel buttonPanel = this.getButtonPanel();
        
        buttonPanel.add(bSave);
        buttonPanel.add(bCancel);
    }

    @Override
    public void dispose() {
    	getModel().getBufferedModel(Customer.PROPERTYNAME_ID).removePropertyChangeListener(tabsEnableListener);
//        tabsEnableListener = null;

        super.dispose();
    }
}
